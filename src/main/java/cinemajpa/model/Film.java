package cinemajpa.model;

import cinemajpa.util.LenientIntegerDeserializer;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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
@Table(name = "film")

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Film {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty("id")
    private String id;

    @JsonProperty("nom")
    private String nom;

    @JsonProperty("url")
    private String url;

    @Column(length = 2000)
    @JsonProperty("plot")
    private String resume;

    @JsonProperty("langue")
    private String langue;

    @JsonDeserialize(using = LenientIntegerDeserializer.class)
    @JsonProperty("anneeSortie")
    private Integer anneeSortie;

    @JsonCreator
    public Film(@JsonProperty("id") String id) {
        this.id = id;

    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_pays", referencedColumnName = "id")
    private Pays pays;

    @JsonManagedReference
    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL)
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Genre_Film",
            joinColumns = @JoinColumn(name = "ID_Film", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ID_Genre", referencedColumnName = "id")
    )
    private Set<Genre> genres = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Realisateur_Film",
            joinColumns = @JoinColumn(name = "ID_Film", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ID_Realisateur", referencedColumnName = "id")
    )
    private Set<Realisateur> realisateurs = new HashSet<>();

    @JsonProperty("castingPrincipal")
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Casting_Principal",
            joinColumns = @JoinColumn(name = "ID_Film", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ID_Acteur", referencedColumnName = "id")
    )
    private Set<Acteur> acteurs = new HashSet<>();

    @JsonProperty("lieuTournage")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_lieu", referencedColumnName = "id")
    private Lieu lieu;

    public Film(String nom, String url, String resume, String langue, Integer anneeSortie) {
        this.nom = nom;
        this.url = url;
        this.resume = resume;
        this.langue = langue;
        this.anneeSortie = anneeSortie;
    }
}
