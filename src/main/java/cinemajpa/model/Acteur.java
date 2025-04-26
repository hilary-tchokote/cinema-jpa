package cinemajpa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "acteur")
public class Acteur {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty("id")
    private String id;

    @JsonProperty("identite")
    private String identite;

    @Transient
    private Naissance naissance;

    @Column(nullable = true)
    @JsonProperty("height")
    private String taille;

    @JsonProperty("url")
    private String url;

    @OneToMany(mappedBy = "acteur", cascade = CascadeType.ALL)
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(mappedBy = "acteurs")
    private Set<Film> films = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "id_lieu", referencedColumnName = "id")
    private Lieu lieux;


    public Acteur(String identite, Naissance naissance, String taille, String url) {
        this.identite = identite;
        this.naissance = naissance;
        this.taille = taille;
        this.url = url;

    }

    private LocalDate dateNaissance;

    private String lieuNaissance;

    @PostLoad
    @PostPersist
    @PostUpdate
    private void syncNaissanceFromFields() {
        this.naissance = new Naissance(this.dateNaissance, this.lieuNaissance);
    }

    @PrePersist
    @PreUpdate
    private void syncFieldsFromNaissance() {
        if (this.naissance != null) {
            this.dateNaissance = this.naissance.getDateNaissance();
            this.lieuNaissance = this.naissance.getLieuNaissance();

        }
    }

}
