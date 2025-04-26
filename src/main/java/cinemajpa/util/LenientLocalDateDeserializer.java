package cinemajpa.util;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Désérialiseur permissif pour LocalDate.
 * Si mois ou jour vaut 0, on le ramène à 1.
 */
public class LenientLocalDateDeserializer extends JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String text = p.getText();  // ex. "1975-0-0"
        String[] parts = text.split("\\-");
        int year  = parseOrDefault(parts, 0,  1);
        int month = clamp(parseOrDefault(parts, 1, 1), 1, 12);
        int day   = clamp(parseOrDefault(parts, 2, 1), 1, 28); // 28 pour éviter trop grand
        return LocalDate.of(year, month, day);
    }

    private int parseOrDefault(String[] parts, int idx, int def) {
        if (idx < parts.length) {
            try { return Integer.parseInt(parts[idx]); }
            catch (NumberFormatException e) { /* ignore */ }
        }
        return def;
    }

    private int clamp(int val, int min, int max) {
        return (val < min) ? min : (val > max) ? max : val;
    }
}
