package info.talacha.filmweb.models;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Osoba
 * @author Paweł Talacha <pawel@talacha.info>
 */
public class Person {
    
    /**
     * Lista profesji (ID w serwisie Filmweb -> nazwa)
     */
    public final static Map<Integer, String> professionList;
    
    static {
        HashMap professions = new HashMap();
        professions.put(1, "reżyser");
        professions.put(2, "scenarzysta");
        professions.put(3, "muzyka");
        professions.put(4, "zdjęcia");
        professions.put(5, "na podstawie");
        professions.put(6, "aktor");
        professions.put(9, "producent");
        professions.put(10, "montaż");
        professions.put(11, "kostiumy");        
        professionList = Collections.unmodifiableMap(professions);
    }
    
    /**
     * ID w serwisie Filmweb
     */
    private int id = 0;
    
    /**
     * Rola pełniona w filmie
     */
    private String role = null;
    
    /**
     * Dodatkowe informacje, np. "Niewymieniony w czołówce", "głos" etc.
     */
    private String info = null;
    
    /**
     * Imię i nazwisko
     */
    private String name = null;
    
    /**
     * Adres zdjęcia
     */
    private URL photoUrl = null;

    /**
     * Odczyt ID osoby
     * @return ID osoby
     */
    public int getId() {
        return id;
    }

    /**
     * Zapis ID osoby
     * @param id ID osoby
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Odczyt roli
     * @return Odgrywana rola
     */
    public String getRole() {
        return role;
    }

    /**
     * Zapis roli
     * @param role Odgrywana rola
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Odczyt informacji dodatkowych o osobie (np. "głos", "nie wymieniony w czołówce" itp.)
     * @return Informacje dodatkowe
     */
    public String getInfo() {
        return info;
    }

    /**
     * Zapis informacji dodatkowych o osobie (np. "głos", "nie wymieniony w czołówce" itp.)
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * Odczyt imienia i nazwiska osoby
     * @return Imię i nazwisko
     */
    public String getName() {
        return name;
    }

    /**
     * Zapis imienia i nazwiska osoby
     * @param name Imię i nazwisko
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Odczyt adresu URL zdjęcia osoby
     * @return Adres URL zdjęcia osoby
     */
    public URL getPhotoUrl() {
        return photoUrl;
    }

    /**
     * Zapis adresu URL zdjęcia osoby
     * @param photo Adres URL zdjęcia osoby
     */
    public void setPhotoUrl(URL photo) {
        this.photoUrl = photo;
    }
}