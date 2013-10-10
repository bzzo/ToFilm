package info.talacha.filmweb.api;

import info.talacha.filmweb.settings.Config;
import info.talacha.filmweb.models.Film;
import info.talacha.filmweb.models.Series;
import info.talacha.filmweb.settings.MsgLogger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Zbiór metod wykorzystujących analizę zawartości kodu źródłowego stron
 * @author Paweł Talacha <pawel@talacha.info>
 */
public class ContentAnalyzer {
    
    /**
     * Adres strony, której zawartość będzie analizowana
     */
    private URL url;
    
    /**
     * Czy z treści mają być pobierane tylko ID
     */
    private boolean onlyId;
    
    /**
     * Typ zapytania: film, serial
     */
    private String queryType = null;
    
    private final static Logger LOGGER = MsgLogger.setup(Logger.GLOBAL_LOGGER_NAME);
    
    public ContentAnalyzer() {
        url = null;
        onlyId = false;
    }
    
    /**
     * Ustawianie adresu strony na podstawie przekazanych parametrów
     * @param params Parametry - wymagane: title (tytuł), opcja: year (rok produkcji)
     * @return Powodzenie / niepowodzenie
     */
    private boolean setUrlStr(HashMap params) {
        
        if (this.queryType == null) {
            LOGGER.severe("Brak typu zapytania.");
            return false;
        }
        
        StringBuilder urlStr = new StringBuilder(Config.WWW);
        urlStr.append("/search/");
        urlStr.append(queryType);
        urlStr.append("?q=");
        
        String title;
        try {
            title = URLEncoder.encode((String)params.get("title"), "UTF-8");
            urlStr.append(title);
        } catch (UnsupportedEncodingException ex) {
            LOGGER.severe("Błąd związany z kodowaniem URL.");
            return false;
        }
        
        Integer year = (Integer)params.get("year");
        if (year != null) {
            urlStr.append("&startYear=");
            urlStr.append(year);
            urlStr.append("&endYear=");
            urlStr.append(year);
        }
        try {
            this.url = new URL(urlStr.toString());
            LOGGER.finest(urlStr.toString());
        } catch (MalformedURLException ex) {
            LOGGER.severe("Problem z ustanowieniem adresu URL.");
            return false;
        }
        return true;
    }
    
    /**
     * Lista filmów, których tytuł zawiera dany ciąg znaków
     * @param params
     *      1. (String) Ciąg znaków zawarty w tytule,
     *      2. (String) Typ zapytania (film / serial)
     *      3. (boolean) Czy lista będzie zawierała tylko ID filmu,
     *      4. (int) Rok produkcji - opcjonalnie
     * @return Lista filmów
     */
    public ArrayList getItemsList(Object... params) {
        if (params.length < 3 || params.length > 4) return null;
        this.queryType = (String)params[1];
        this.onlyId = Boolean.getBoolean(params[2].toString());
        HashMap urlParams = new HashMap();
        urlParams.put("title", (String)params[0]);
        if (params.length == 4) urlParams.put("year", Integer.getInteger(params[3].toString()));
        return this.setUrlStr(urlParams) ? this.getFilmList() : new ArrayList();
    }
    
    /**
     * Pobieranie kodu HTML strony o danym adresie
     * @param url Adres strony
     * @param desc Opis strony
     */
    private String getHtmlCode(URL url, String desc) {
        
        desc = desc != null ? desc+": " : "";
        
        StringBuilder html = new StringBuilder();
        try {
            URLConnection conn = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF8"));
            
            String str;
            while ((str = br.readLine()) != null) {
                html.append(str);
            }
            br.close();
        } catch (MalformedURLException ex) {
            desc += "Nieprawidłowy adres URL.";
            LOGGER.severe(desc);
        }
        catch (IOException ex) {
            desc += "Błąd odczytu danych filmu.";
            LOGGER.severe(desc);
        }
        return html.toString();
    }
    
    /**
     * Lista filmów lub ID filmów dla ustawionych wcześniej parametrów
     * @see public ArrayList<Film> getFilmList(String title, int year)
     * @see public ArrayList<Film> getFilmList(String title)
     * @return Lista filmów
     */
    private ArrayList getFilmList() {
        
        String html = getHtmlCode(this.url, "Lista filmów");

        List<String> allMatches = new ArrayList();
        
        String pattern;
        if (this.queryType.equals("film")) {
            pattern = "<a href=\"([^\"]*)\" class=\"fImg125\" title=\"";
        }
        else {
            pattern = "<a class=\"hdr hdr-medium hitTitle\" href=\"([^\"]*)\">";
        }

        Pattern exp = Pattern.compile(pattern);
        Matcher matcher = exp.matcher(html);
        while (matcher.find()) {
            allMatches.add(matcher.group(1));
        }

        if (allMatches.isEmpty()) {
            String gettingObj = "Nie znaleziono pasujących do zapytania adresów URL " + (this.queryType.equals("film") ? "filmów" : "seriali") + ".";
            LOGGER.info(gettingObj);
        }

        Connection conn = new Connection();
        FilmwebApiHelper fah = new FilmwebApiHelper(conn);
        ArrayList<Film> filmList = new ArrayList();
        for (String i : allMatches) {
            URL filmUrl;
            try {
                filmUrl = new URL(Config.WWW + i);
            } catch (MalformedURLException ex) {
                LOGGER.severe("Błąd tworzenia URL");
                filmUrl = null;
            }
            
            Film film;
            if (this.queryType.equals("film")) film = new Film(fah);
            else film = new Series(fah);
            film.setFilmUrl(filmUrl);
            film = getFilmData(film);
            if (this.queryType.equals("film")) filmList.add(film);
            else filmList.add((Series)film);
        }
        
        if (this.onlyId) {
            ArrayList<Integer> filmIdList = new ArrayList();
            for (Film film : filmList) {
                filmIdList.add(film.getId());
            }
            return filmIdList;
        }
        return filmList;
    }
    
    /**
     * Pobranie ze strony filmu podstawowych informacji
     * @param film
     * @return Film z uzupełnionym ID
     */
    private Film getFilmData(Film film) {
        
        String html = getHtmlCode(film.getFilmUrl(), "Film");
        Pattern exp = Pattern.compile("<div id=filmId>(\\d+)</div>");
        Matcher matcher = exp.matcher(html);
        if (matcher.find()) {
            try {
                int id = Integer.parseInt(matcher.group(1));
                film.setId(id);
            }
            catch (NumberFormatException ex) {
                LOGGER.severe("Nieprawidłowy format ID - nie zapisano");
            }
        }
        else LOGGER.severe("Nie znaleziono ID filmu.");
        return film;
    }
}