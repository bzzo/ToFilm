package info.talacha.filmweb.models;

import info.talacha.filmweb.api.FilmwebApiHelper;

/**
 * Serial
 * @author Paweł Talacha <pawel@talacha.info>
 */
public class Series extends Film {
    
    /**
     * Ilość odcinków
     */
    private int episodesCount = 0;
    
    /**
     * Ilość sezonów
     */
    private int seasonsCount = 0;

    public Series(FilmwebApiHelper fah) {
        super(fah);
    }

    /**
     * Odczyt całkowitej liczby odcinków
     * @return Całkowita liczba odcinków
     */
    public int getEpisodesCount() {
        super.setFilmData();
        return episodesCount;
    }

    /**
     * Zapis całkowitej liczby odcinków
     * @param episodesCount Całkowita liczba odcinków
     */
    public void setEpisodesCount(int episodesCount) {
        this.episodesCount = episodesCount;
    }

    /**
     * Odczyt liczby sezonów
     * @return Liczba sezonów
     */
    public int getSeasonsCount() {
        super.setFilmData();
        return seasonsCount;
    }

    /**
     * Zapis liczby sezonów
     * @param seasonsCount Liczba sezonów
     */
    public void setSeasonsCount(int seasonsCount) {
        this.seasonsCount = seasonsCount;
    }
}