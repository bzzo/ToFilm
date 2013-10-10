package info.talacha.filmweb.api;

import info.talacha.filmweb.settings.MsgLogger;
import info.talacha.filmweb.settings.Config;
import info.talacha.filmweb.models.Person;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Hex;

/**
 * Komunikacja z serwerem
 * 
 * @author Paweł Talacha <pawel@talacha.info>
 */
public class Connection {

	/**
	 * Informacje nt wywoływanej metody zdalnej w postaci: nazwa_metody
	 * [parametry]\n
	 */
	private String methods;

	/**
	 * Informacje nt wywoływanej metody zdalnej w postaci: nazwa_metody Używane
	 * do określenia sposobu formatowania odpowiedzi
	 */
	private String methodName;

	/**
	 * Sygnatura dla wywoływanej metody zdalnej
	 */
	private String signature;

	/**
	 * Wersja systemu
	 */
	private String version;

	/**
	 * ID aplikacji
	 */
	private String appId;

	private final static Logger LOGGER = MsgLogger
			.setup(Logger.GLOBAL_LOGGER_NAME);

	public Connection() {
		version = "1.0";
		appId = "android";
		methods = null;
		methodName = null;
		signature = null;
	}

	/**
	 * Ustawienie metody zdalenj, która będzie wywołana
	 * 
	 * @param method
	 *            Dane metody postaci: nazwa_metody [parametry]
	 * @return Powodzenie / niepowodzenie
	 */
	public boolean setMethod(String method) {

		if (this.setSignature(method)) {
			this.methods = method + "\\n";

			// zapis nazwy metody
			String[] methodParts = method.split("\\s");
			methodName = methodParts[0];
			return true;
		}
		return false;
	}

	/**
	 * Przygotowanie sygnatury
	 * 
	 * @param method
	 *            Dane metody zdalnej postaci: nazwa_metody [parametry]
	 * @return Powodzenie / niepowodzenie
	 */
	private boolean setSignature(String method) {

		String sig = method + "\\nandroid" + Config.KEY;

		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException ex) {
			LOGGER.severe(ex.getMessage());
			return false;
		}

		messageDigest.reset();
		messageDigest.update(sig.getBytes(Charset.forName("UTF8")));
		byte[] resultByte = messageDigest.digest();

		String result;
		try {
			result = new String(Hex.encodeHex(resultByte));
		} catch (NoClassDefFoundError ex) {
			LOGGER.severe(ex.getMessage());
			return false;
		}
		this.signature = "1.0," + result;
		return true;
	}

	/**
	 * Przygotowanie parametrów dla zapytania
	 * 
	 * @return Parametry dla url
	 */
	private String prepareParams() {

		if (this.methods == null || this.signature == null)
			return null;

		String qs;
		try {
			qs = "methods=" + URLEncoder.encode(this.methods, "UTF-8");
			qs += "&signature=" + URLEncoder.encode(this.signature, "UTF-8");
			qs += "&version=" + URLEncoder.encode(this.version, "UTF-8");
			qs += "&appId=" + URLEncoder.encode(this.appId, "UTF-8");
		} catch (UnsupportedEncodingException ex) {
			LOGGER.severe(ex.getMessage());
			return null;
		}
		return qs;
	}

	/**
	 * Ustawienie wszystkich elementów otrzymanych w odpowiedzi i zwrócenie tej,
	 * o którą jest zapytanie
	 * 
	 * @return Dane, które zwracane są dla wywołanej metody zdalnej
	 */
	public Object prepareResponse() {

		String response = getResponse();
		if (response == null)
			return null;

		ResponseAnalyzer ra = new ResponseAnalyzer(response);
		Object res = null;

		ArrayList<String> strList;
		ArrayList<ArrayList> list;

		if (methodName.equals("getFilmDescription")) {
			strList = (ArrayList) ra.analyze();
			res = strList.get(0).toString();
		} else if (methodName.equals("getFilmPersons")) {
			list = (ArrayList) ra.analyze();
			ArrayList<Person> personArrayList = new ArrayList();
			for (ArrayList personData : list) {
				Person person = new Person();
				try {
					int id = Integer.parseInt(personData.get(0).toString());
					person.setId(id);
				} catch (NullPointerException e) {
					LOGGER.severe("Brak ID osoby");
				}
				try {
					String role = personData.get(1).toString();
					person.setRole(role);
				} catch (NullPointerException e) {
					LOGGER.finest("Brak roli");
				}

				try {
					String info = personData.get(2).toString();
					person.setInfo(info);
				} catch (NullPointerException e) {
					LOGGER.finest("Brak informacji dodatkowych");
				}

				person.setName(personData.get(3).toString());
				URL photoUrl = null;
				try {
					photoUrl = new URL(Config.URL_PERSON
							+ personData.get(4).toString());
				} catch (MalformedURLException ex) {
					LOGGER.severe("Niepoprawny adres zdjęcia osoby");
				} catch (NullPointerException e) {
					LOGGER.finer("Osoba nie posiada zdjęcia");
				}
				person.setPhotoUrl(photoUrl);
				personArrayList.add(person);
			}

			res = personArrayList;
		} else if (methodName.equals("getFilmInfoFull")) {
			res = (ArrayList) ra.analyze();
		}
		return res;
	}

	/**
	 * Uzyskanie odpowiedzi z serwera
	 * 
	 * @return Odpowiedź serwera
	 */
	private String getResponse() {

		String resp = "";

		String params = this.prepareParams();
		if (params == null)
			return null;

		try {
			URL url = new URL(Config.API_SERVER + params);
			URLConnection con = url.openConnection();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream(), "UTF-8"));

			/**
			 * Pierwsza linia jest komunikatem o stanie zapytania, możliwe
			 * opcje: "ok" - pozostałe linie są wynikiem "err" - pozostała linia
			 * zawiera opis błędu
			 */
			String stateStr = in.readLine();
			boolean state = stateStr.equals("ok") ? true : false;

			String line;
			while ((line = in.readLine()) != null) {
				resp += line;
			}
			in.close();

			if (!state) {
				LOGGER.severe(resp);
				return null;
			} else if (resp.equals("exc NullPointerException")) {
				LOGGER.severe("Brak danych");
				return null;
			}
		} catch (MalformedURLException ex) {
			LOGGER.severe(ex.getMessage());
			return null;
		} catch (IOException ex) {
			LOGGER.severe(ex.getMessage());
			return null;
		}
		LOGGER.finest(resp);
		return resp;
	}
}