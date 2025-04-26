package cinemajpa.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JsonProperty("characterName")
    private String characterName;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_film", referencedColumnName = "id")
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private Film film;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_acteur", referencedColumnName = "id")
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private Acteur acteur;

    public Role(String characterName, Film film, Acteur acteur) {
        this.characterName = characterName;
        this.film = film;
        this.acteur = acteur;
    }
}
