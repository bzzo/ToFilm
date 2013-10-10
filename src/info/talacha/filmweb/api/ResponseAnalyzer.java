
package info.talacha.filmweb.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;
import java.util.ArrayList;

/**
 * Analiza odpowiedzi wywołania metod zdalnych
 * @author Paweł Talacha <pawel@talacha.info>
 */
public class ResponseAnalyzer {
    
    /**
     * Odpowiedź serwera w postaci elementu Json przeznaczona do analizy
     */
    private JsonElement reposnse;
    
    ResponseAnalyzer(String response) {
        
        //informacja o czasie generowania + spacja
        String timeInfo = response.substring(response.lastIndexOf(" "));
        response = response.replace(timeInfo, "");
        
        JsonStreamParser parser = new JsonStreamParser(response);
        if (parser.hasNext()) {
            this.reposnse = parser.next();
        }
    }
    
    /**
     * Analizuje element Json budując na jego podstawie obiekt
     * @param elementStr Analizowany element
     * @return Obiekt String, ArrayList, ew. null
     */
    private Object getData(JsonElement element) {
        if (element.isJsonNull()) return null;
        else if (element.isJsonArray()) {
            ArrayList data = new ArrayList();
            JsonArray ja = element.getAsJsonArray();
            for (JsonElement e : ja) {
                data.add(this.getData(e));
            }
            return data;
        }
        else return element.getAsString();
    }

    /**
     * Rozpoczęcie analizy
     * @return Lista obiektów (String, ArrayList, ew. null)
     */
    public Object analyze() {
        return this.getData(this.reposnse);
    }
}