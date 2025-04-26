package cinemajpa.repository;

import cinemajpa.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    // 1. Filmographie d’un acteur
    @Query("SELECT r.film FROM Role r WHERE r.acteur.identite = :nom")
    Set<?> findFilmographieByActeur(String nom);

    // 2. Casting d’un film
    @Query("SELECT r.acteur FROM Role r WHERE r.film.nom = :film")
    Set<?> findCastingByFilm(String film);

    // 5. Acteurs communs à 2 films
    @Query("SELECT r1.acteur FROM Role r1 JOIN Role r2 ON r1.acteur = r2.acteur " +
            "WHERE r1.film.nom = :film1 AND r2.film.nom = :film2")
    Set<?> findActeursCommunsEntreDeuxFilms(String film1, String film2);



}
