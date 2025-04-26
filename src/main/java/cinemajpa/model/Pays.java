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
@Table(name = "pays")
public class Pays {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JsonProperty("nom")
    private String nom;

    @JsonProperty("url")
    private String url;

    @OneToMany(mappedBy = "pays", cascade = CascadeType.ALL)
    private Set<Film> films = new HashSet<>();

    public Pays(String nom, String url) {
        this.nom = nom;
        this.url = url;
    }

}
