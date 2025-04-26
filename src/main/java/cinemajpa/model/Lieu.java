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
@Table(name = "lieu")
public class Lieu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

//    @JsonProperty("lieuTournage.ville")
    private String ville;

//    @JsonProperty("lieuTournage.etatDept")
    private String etat;

//    @JsonProperty("lieuTournage.pays")
    private String pays;

//    @JsonProperty("naissance.lieuNaissance")
    private String lieuNaissance;


    @OneToMany(mappedBy = "lieu")
    private Set<Film> films = new HashSet<>();

    @OneToMany(mappedBy = "lieux")
    private Set<Acteur> acteurs = new HashSet<>();


}
