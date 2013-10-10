package info.talacha.filmweb.api;

import info.talacha.filmweb.models.Film;
import info.talacha.filmweb.models.Series;
import java.util.ArrayList;

/**
 * Zbiór metod możliwych do wywołania zdalnie
 * @author Paweł Talacha <pawel@talacha.info>
 */
public class FilmwebApi {
    
    private Connection conn = null;
    private ContentAnalyzer ca = null;
    private FilmwebApiHelper fah = null;
    
    public FilmwebApi() {
        conn = new Connection();
        ca = new ContentAnalyzer();
        fah = new FilmwebApiHelper(conn);
    }
    
    /**
     * Lista filmów (+ podstawowe informacje) o danym tytule
     * Pozycje posortowane wg trafności (popularność)
     * @param title Tytuł filmu
     * @return Lista filmów wraz z podstawowymi informacjami
     */
    public ArrayList<Film> getFilmList(String title) {
        return ca.getItemsList(title, "film", false);
    }

    /**
     * Lista filmów (+ podstawowe informacje) o danym tytule i roku produkcji
     * Pozycje posortowane wg trafności (popularność)
     * @param title Tytuł filmu
     * @param year Rok produkcji
     * @return Lista filmów wraz z podstawowymi informacjami
     */
    public ArrayList<Film> getFilmList(String title, int year) {
        return ca.getItemsList(title, "film", false, year);
    }
    
    /**
     * Lista ID filmów o danym tytule
     * Pozycje posortowane wg trafności (popularność)
     * @param title Tytuł filmu
     * @return Lista ID filmów
     */
    public ArrayList<Integer> getFilmIdList(String title) {
        return ca.getItemsList(title, "film", true);
    }
    
    /**
     * Lista ID filmów o danym tytule
     * Pozycje posortowane wg trafności (popularność)
     * @param title Tytuł filmu
     * @param year Rok produkcji
     * @return Lista ID filmów
     */
    public ArrayList<Integer> getFilmIdList(String title, int year) {
        return ca.getItemsList(title, "film", true, year);
    }
    
    /**
     * Lista seriali (+ podstawowe informacje) o danym tytule
     * Pozycje posortowane wg trafności (popularność)
     * @param title Tytuł serialu
     * @return Lista seriali wraz z podstawowymi informacjami
     */
    public ArrayList<Series> getSeriesList(String title) {
        return ca.getItemsList(title, "serial", false);
    }

    /**
     * Lista seriali (+ podstawowe informacje) o danym tytule i roku produkcji
     * Pozycje posortowane wg trafności (popularność)
     * @param title Tytuł serialu
     * @param year Rok produkcji
     * @return Lista seriali wraz z podstawowymi informacjami
     */
    public ArrayList<Series> getSeriesList(String title, int year) {
        return ca.getItemsList(title, "serial", false, year);
    }
    
    /**
     * Lista ID seriali o danym tytule
     * Pozycje posortowane wg trafności (popularność)
     * @param title Tytuł serialu
     * @return Lista ID seriali
     */
    public ArrayList<Integer> getSeriesIdList(String title) {
        return ca.getItemsList(title, "serial", true);
    }
    
    /**
     * Lista ID seriali o danym tytule
     * Pozycje posortowane wg trafności (popularność)
     * @param title Tytuł serialu
     * @param year Rok produkcji
     * @return Lista ID seriali
     */
    public ArrayList<Integer> getSeriesIdList(String title, int year) {
        return ca.getItemsList(title, "serial", true, year);
    }
}