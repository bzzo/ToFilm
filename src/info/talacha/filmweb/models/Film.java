package info.talacha.filmweb.models;

import info.talacha.filmweb.api.FilmwebApiHelper;
import info.talacha.filmweb.settings.MsgLogger;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Film
 * @author Paweł Talacha <pawel@talacha.info>
 */
public class Film {
    
    /**
     * ID w serwisie Filmweb
     */
    private int id = 0;
    
    /**
     * Tytuł oryginalny
     */
    private String title = null;
    
    /**
     * Tytuł polski
     */
    private String polishTitle = null;
    
    /**
     * Rok produkcji
     */
    private Integer year = null;
    
    /**
     * Adres okładki (pliku graficznego)
     */
    private URL coverUrl = null;
    
    /**
     * Adres strony filmu
     */
    private URL filmUrl = null;
    
    /**
     * Opis filmu
     */
    private String description = null;
    
    /**
     * Średnia ocen filmu
     */
    private Float rate = null;
    
    /**
     * Liczba głosów
     */
    private Integer votes = null;
    
    /**
     * Gatunek
     */
    private String genre = null;
    
    /**
     * Czas trwania
     */
    private Integer duration = null;
    
    /**
     * Kraj produkcji
     */
    private String countries = null;
    
    /**
     * Zarys fabuły
     */
    private String plot = null;
    
    /**
     * Lista reżyserów
     */
    private ArrayList<Person> directors = null; 
    
    /**
     * Lista scenarzystów
     */
    private ArrayList<Person> screenwriters = null; 
    
    /**
     * Lista osób odpowiedzialnych za muzykę
     */
    private ArrayList<Person> music = null;

    /**
     * Lista osób odpowiedzialnych za zdjęcia
     */
    private ArrayList<Person> picture = null;
    
    /**
     * Na podstawie
     */
    private ArrayList<Person> basedOn = null;
    
    /**
     * Lista aktorów
     */
    private ArrayList<Person> actors = null;
    
    /**
     * Producenci
     */
    private ArrayList<Person> producers = null;
    
    /**
     * Montaż
     */
    private ArrayList<Person> montage = null;
    
    /**
     * Kostiumy
     */
    private ArrayList<Person> costumes = null;
    
    /**
     * Czy zostały pobrane informacje o filmie z użyciem metody zdalnej
     */
    protected boolean isFilmDataChecked = false;
    
    /**
     * Zbiór metod nie będących częścią API, służących jednak do pobierania danych z serwisu Filmweb
     * (Używane niejawnie)
     */
    protected FilmwebApiHelper fah = null;
    
    private final static Logger LOGGER = MsgLogger.setup(Logger.GLOBAL_LOGGER_NAME);
    
    public Film(FilmwebApiHelper fah) {
        this.fah = fah;
    }

    /**
     * Odczyt ID filmu
     * @return ID filmu
     */
    public int getId() {
        return id;
    }

    /**
     * Zapis ID filmu
     * @param id ID filmu
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Odczyt oryginalnego tytułu filmu
     * @return Tytuł filmu
     */
    public String getTitle() {
        setFilmData();
        return title;
    }

    /**
     * Zapis tytułu filmu
     * @param title Tytuł filmu
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Odczyt polskiego tytułu filmu
     * @return Polski tytuł filmu
     */
    public String getPolishTitle() {
        setFilmData();
        return polishTitle;
    }

    /**
     * Zapis polskiego tytułu filmu
     * @param polishTitle Polski tytuł filmu
     */
    public void setPolishTitle(String polishTitle) {
        this.polishTitle = polishTitle;
    }

    /**
     * Odczyt roku produkcji
     * @return rok produkcji
     */
    public Integer getYear() {
        setFilmData();
        return year;
    }

    /**
     * Zapis roku produkcji
     * @param year Rok produkcji
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * Odczyt adresu URL okładki filmu
     * @return Adres URL okładki filmu
     */
    public URL getCoverUrl() {
        setFilmData();
        return coverUrl;
    }

    /**
     * Zapis adresu URL okładki filmu
     * @param coverUrl Adres URL okładki filmu
     */
    public void setCoverUrl(URL coverUrl) {
        this.coverUrl = coverUrl;
    }

    /**
     * Odczyt adresu URL strony filmu
     * @return Adres URL strony filmu
     */
    public URL getFilmUrl() {
        return filmUrl;
    }

    /**
     * Zapis adresu URL strony filmu
     * @param filmUrl Adres URL strony filmu
     */
    public void setFilmUrl(URL filmUrl) {
        this.filmUrl = filmUrl;
    }

    /**
     * Odczyt średniej oceny filmu
     * @return Średnia ocena filmu
     */
    public Float getRate() {
        setFilmData();
        return rate;
    }

    /**
     * Zapis średniej oceny filmu
     * @param rate Średnia ocena filmu
     */
    public void setRate(Float rate) {
        this.rate = rate;
    }
    
    /**
     * Odczyt liczby głosów oddanych na film
     * @return Liczba głosów oddanych na film
     */
    public Integer getVotes() {
        setFilmData();
        return votes;
    }

    /**
     * Zapis liczby głosów oddanych na film
     * @param votes Liczba głosów oddanych na film
     */
    public void setVotes(Integer votes) {
        this.votes = votes;
    }
    
    /**
     * Odczyt listy gatunków filmu
     * @return Lista gatunków filmu
     */
    public String getGenre() {
        setFilmData();
        return genre;
    }

    /**
     * Zapis listy gatunków filmu
     * @param genre Lista gatunków filmu
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    /**
     * Odczyt czasu trwania filmu (minuty)
     * @return Czas trwania filmu w minutach
     */
    public Integer getDuration() {
        setFilmData();
        return duration;
    }

    /**
     * Zapis czasu trwania filmu (minuty)
     * @param duration Czas trwania filmu w minutach
     */
    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    
    /**
     * Odczyt listy krajów produkcji
     * @return Lista krajów produkcji
     */
    public String getCountries() {
        setFilmData();
        return countries;
    }

    /**
     * Zapis listy krajów produkcji
     * @param countries Lista krajów produkcji
     */
    public void setCountries(String countries) {
        this.countries = countries;
    }

    /**
     * Odczyt zarysu fabuły
     * @return Zarys fabyły
     */
    public String getPlot() {
        setFilmData();
        return plot;
    }

    /**
     * Zapis zarysu fabuły
     * @param plot Zarys fabuły
     */
    public void setPlot(String plot) {
        this.plot = plot;
    }
    
    /**
     * Lista osób pełniących funkcję reżysera
     * @return Lista osób
     */
    public ArrayList<Person> getDirectors() {
        if (directors == null) {
            directors = new ArrayList(fah.getFilmPersons(id, "reżyser"));
        }
        return directors;
    }
    
    /**
     * Lista osób pełniących funkcję scenarzysty
     * @return Lista osób
     */
    public ArrayList<Person> getScreenwriters() {
        if (screenwriters == null) {
            screenwriters = new ArrayList(fah.getFilmPersons(id, "scenarzysta"));
        }
        return screenwriters;
    }
    
    /**
     * Lista osób odpowiedzialnych za muzykę
     * @return Lista osób
     */
    public ArrayList<Person> getMusic() {
        if (music == null) {
            music = new ArrayList(fah.getFilmPersons(id, "muzyka"));
        }
        return music;
    }

    /**
     * Lista osób odpowiedzialnych za zdjęcia
     * @return Lista osób
     */
    public ArrayList<Person> getPicture() {
        if (picture == null) {
            picture = new ArrayList(fah.getFilmPersons(id, "zdjęcia"));
        }
        return picture;
    }
    
    /**
     * Lista osób mających wkład w fabułę
     * @return Lista osób
     */
    public ArrayList<Person> getBasedOn() {
        if (basedOn == null) {
            basedOn = new ArrayList(fah.getFilmPersons(id, "na podstawie"));
        }
        return basedOn;
    }
    
    /**
     * Lista aktorów
     * @return Lista osób
     */
    public ArrayList<Person> getActors() {
        if (actors == null) {
            actors = new ArrayList(fah.getFilmPersons(id, "aktor"));
        }
        return actors;
    }
    
    /**
     * Lista producentów
     * @return Lista osób
     */
    public ArrayList<Person> getProducers() {
        if (producers == null) {
            producers = new ArrayList(fah.getFilmPersons(id, "producent"));
        }
        return producers;
    }
    
    /**
     * Lista osób odpowiedzialnych za montaż
     * @return Lista osób
     */
    public ArrayList<Person> getMontage() {
        if (montage == null) {
            montage = new ArrayList(fah.getFilmPersons(id, "montaż"));
        }
        return montage;
    }
    
    /**
     * Lista osób odpowiedzialnych za montaż
     * @return Lista osób
     */
    public ArrayList<Person> getCostumes() {
        if (costumes == null) {
            costumes = new ArrayList(fah.getFilmPersons(id, "kostiumy"));
        }
        return costumes;
    }

    /**
     * Opis filmu
     * @return Opis filmu
     */
    public String getDescription() {
        if (description == null) {
            description = fah.getFilmDescription(id);
        }
        return description;
    }
    
    /**
     * Ustawienie informacji o filmie przy pierwszym rządaniu którejkolwiek brakującej
     */
    protected void setFilmData() {
        if (!isFilmDataChecked) {
            fah.getFilmData(this);
            isFilmDataChecked = true;
        }
    }
}