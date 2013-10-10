package info.talacha.filmweb.settings;

import java.util.logging.Level;

/**
 * Konfiguracja
 * @author Paweł Talacha <pawel@talacha.info>
 */
public class Config {
    
    /**
     * Adres, z którym wykonywane jest połączenie
     */
    public static final String API_SERVER = "https://ssl.filmweb.pl/api?";
    
    /**
     * Klucz
     */
    public static final String KEY = "qjcGhW2JnvGT9dfCt3uT_jozR3s";
    
    /**
     * Adres serwisu WWW
     */
    public static final String WWW = "http://www.filmweb.pl";
    
    /**
     * Adres początkowy dla zdjęć osób
     */
    public static final String URL_PERSON = "http://1.fwcdn.pl/p";
    
    /**
     * Adres początkowy dla plakatów filmu
     */
    public static final String URL_POSTER = "http://1.fwcdn.pl/po";

    /**
     * Poziom logowanych komunikatów
     */
    public static final Level LOG_LVL = Level.WARNING;
    
    /**
     * Nazwa pliku logowanych komunikatów
     */
    public static final String LOG_FILE = "messages.log";
}