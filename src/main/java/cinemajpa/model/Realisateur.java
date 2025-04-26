package cinemajpa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "realisateur")
public class Realisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JsonProperty("identite")
    private String identite;

    @JsonProperty("url")
    private String url;

    @ManyToMany(mappedBy = "realisateurs")
    private Set<Film> films = new HashSet<>();

    public Realisateur(String identite, String url) {
        this.identite = identite;
        this.url = url;
    }


}
