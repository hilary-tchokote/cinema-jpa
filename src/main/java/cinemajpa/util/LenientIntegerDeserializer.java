package cinemajpa.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

/**
 * Désérialiseur permissif pour Integer.
 * Extrait la suite de chiffres d’une chaîne, ou retourne null si impossible.
 */

public class LenientIntegerDeserializer extends JsonDeserializer<Integer> {
    @Override
    public Integer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String text = p.getText();           // ex. "2020/I"
        // Garder uniquement les chiffres
        String digits = text.replaceAll("\\D+", "");
        if (digits.isBlank()) {
            return null;                    // ou une valeur par défaut, ex. 0
        }
        try {
            return Integer.valueOf(digits);
        } catch (NumberFormatException e) {
            return null;                    // ou throw JsonMappingException.from(p, "...", e);
        }
    }

}
