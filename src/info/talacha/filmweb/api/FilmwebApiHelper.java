package info.talacha.filmweb.api;

import info.talacha.filmweb.models.Film;
import info.talacha.filmweb.models.Person;
import info.talacha.filmweb.models.Series;
import info.talacha.filmweb.settings.Config;
import info.talacha.filmweb.settings.MsgLogger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.logging.Logger;

/**
 * Zbiór metod możliwych do wywołania zdalnie, nie będących częścią API (wywoływane pośrednio)
 * @author Paweł Talacha <pawel@talacha.info>
 */
public class FilmwebApiHelper {
    
    private Connection conn = null;
    
    private final static Logger LOGGER = MsgLogger.setup(Logger.GLOBAL_LOGGER_NAME);
    
    public FilmwebApiHelper(Connection conn) {
        this.conn = conn;
    }

    /**
     * Lista osób danej profesji z danego filmu
     * @param filmId ID filmu
     * @param profession Nazwa profesji
     * @return Lista osób
     */
    public ArrayList<Person> getFilmPersons(int filmId, String profession) {
        if (filmId <= 0) return null;
        
        //ustalenie ID profesji
        Integer professionType = null;
        for (Entry<Integer, String> entry : Person.professionList.entrySet()) {
            if (entry.getValue().equals(profession)) {
                professionType = entry.getKey();
            }
        }
        
        try {
            if (professionType == null) {
                throw new Exception("Nieprawidłowa profesja");
            }
        }
        catch(Exception e) {
            LOGGER.severe(e.getMessage());
            return null;
        }
        
        int pageNo = 0;
        ArrayList<Person> personList = new ArrayList();
        ArrayList<Person> partPersonList = new ArrayList();
        try {
            do {
                if (conn.setMethod("getFilmPersons [" + filmId + "," + professionType + "," + 50*pageNo + "," + 50*(1+pageNo) + "]")) {
                    partPersonList = (ArrayList)conn.prepareResponse();
                    if (partPersonList.size() > 0) {
                        personList.addAll(partPersonList);
                    }
                    pageNo++;
                }
            } while(partPersonList.size() == 50);
        }
        catch (NullPointerException e) {
            String error = "Brak ludzi o profesji "+profession+" związanych z filmem.";
            LOGGER.fine(error);
        }
        return personList;
    }
    
    /**
     * Opis filmu
     * @param filmId ID filmu
     * @return Opis filmu
     */
    public String getFilmDescription(int filmId) {
        if (filmId <= 0) return null;
        String desc = "";
        if (conn.setMethod("getFilmDescription ["+filmId+"]")) {
            try {
                desc = conn.prepareResponse().toString();
            }
            catch(NullPointerException e) {
                LOGGER.severe("Brak danych opisu filmu");
                desc = null;
            }
        }
        return desc;
    }
    
    /**
     * Ustawienie informacji nt filmu
     * @param params Film
     */
    public void getFilmData(Film film) {
        if (conn.setMethod("getFilmInfoFull ["+film.getId()+"]")) {
            ArrayList<String> res = (ArrayList<String>)conn.prepareResponse();

            /*
             * 7 - liczba komentarzy,
             * 8 - adres forum
             * 9 - czy ma zarys fabuły
             * 10 - czy ma opis fabuły
             * 12 - video, 13 - premiera światowa, 14 - premiera polska
             */

            String data;
            Float floatData;
            Integer intData;
            
            //tytuł oryginalny
            try {
                data = res.get(1).toString();
                film.setTitle(data);
            }
            catch (NullPointerException e) {
                LOGGER.finer("Tytuł polski jest identyczny z oryginalnym. Przechowywany jako polski.");
            }
            
            //tytuł polski
            try {
                data = res.get(0).toString();
                film.setPolishTitle(data);
            }
            catch (NullPointerException e) {
                LOGGER.severe("Brak polskiego tytułu.");
            }
            
            //ocena
            try {
                floatData = Float.parseFloat(res.get(2).toString());
                film.setRate(floatData);
            }
            catch(NumberFormatException e) {
                LOGGER.severe("Nieprawidłowy format oceny filmu.");
            }
            catch(NullPointerException e) {
                LOGGER.fine("Brak oceny filmu.");
            }
            
            //liczba głosów
            try {
                intData = Integer.parseInt(res.get(3).toString());
                film.setVotes(intData);
            }
            catch(NumberFormatException e) {
                LOGGER.severe("Nieprawidłowy format liczby głosów filmu.");
            }
            catch(NullPointerException e) {
                LOGGER.fine("Brak głosów na film.");
            }
            
            //gatunek
            try {
                data = res.get(4).toString();
                data = data.replace(",", ", ");
                film.setGenre(data);
            }
            catch(NullPointerException e) {
                LOGGER.fine("Brak informacji na temat gatunku.");
            }
            
            //rok produkcji
            try {
                intData = Integer.parseInt(res.get(5).toString());
                film.setYear(intData);
            }
            catch(NumberFormatException e) {
                LOGGER.severe("Nieprawidłowy format roku produkcji.");
            }
            catch(NullPointerException e) {
                LOGGER.fine("Brak informacji o dacie produkcji.");
            }
            
            //czas trwania
            try {
                intData = Integer.parseInt(res.get(6).toString());
                film.setDuration(intData);
            }
            catch(NumberFormatException e) {
                LOGGER.severe("Nieprawidłowy format czasu trwania filmu.");
            }
            catch(NullPointerException e) {
                LOGGER.fine("Brak informacji o czasie trwania filmu.");
            }
            
            //okładka
            try {
                data = res.get(11).toString();
                if (data != null) {
                    String urlStr = Config.URL_POSTER + data;
                    film.setCoverUrl(new URL(urlStr));
                }
            } catch (MalformedURLException ex) {
                LOGGER.severe("Błąd URL dla okładki filmu.");
            } catch (NullPointerException e) {
                LOGGER.fine("Brak URL okładki filmu.");
            }
            
            //kraj produkcji
            try {
                data = res.get(18).toString();
                film.setCountries(data);
            }
            catch (NullPointerException e) {
                LOGGER.fine("Brak informacji o kraju produkcji.");
            }
                
            //zarys fabuły
            try {
                data = res.get(19).toString();
                film.setPlot(data);
            }
            catch (NullPointerException e) {
                LOGGER.fine("Brak zarysu fabuły.");
            }
            
            //serial posiada kilka dodatkowych pól
            if (film instanceof Series) {
                Series series = (Series)film;
                
                //liczba sezonów
                try {
                    intData = Integer.parseInt(res.get(16).toString());
                    series.setSeasonsCount(intData);
                }
                catch(NumberFormatException e) {
                    LOGGER.severe("Nieprawidłowy format liczby sezonów.");
                }
                catch(NullPointerException e) {
                    LOGGER.fine("Brak informacji o liczbie sezonów.");
                }
                
                //liczba odcinków
                try {
                    intData = Integer.parseInt(res.get(17).toString());
                    series.setEpisodesCount(intData);
                }
                catch(NumberFormatException e) {
                    LOGGER.severe("Nieprawidłowy format liczby odcinków.");
                }
                catch(NullPointerException e) {
                    LOGGER.fine("Brak informacji o liczbie odcinków.");
                }
            }
        }
    }
}