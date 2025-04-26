package cinemajpa.model;
import cinemajpa.util.LenientLocalDateDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Naissance {

    @JsonDeserialize(using = LenientLocalDateDeserializer.class)
    private LocalDate dateNaissance;

    private String lieuNaissance;
}
