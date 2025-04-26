package cinemajpa.repository;


import cinemajpa.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface FilmRepository extends JpaRepository<Film, String> {

    // 3. Films entre deux années
    Set<Film> findByAnneeSortieBetween(Integer start, Integer end);

    // 6. Films entre deux années ET avec un acteur
    @Query("SELECT DISTINCT f FROM Film f JOIN f.roles r WHERE f.anneeSortie BETWEEN :start AND :end AND r.acteur.identite = :acteurName")
    Set<Film> findByAnneeSortieAndActeur(Integer start, Integer end, String acteurName);

    // 4. Films communs à deux acteurs
    @Query("SELECT f FROM Film f JOIN f.roles r1 JOIN f.roles r2 " +
            "WHERE r1.acteur.identite = :acteur1 AND r2.acteur.identite = :acteur2")
    Set<Film> findFilmsCommunsADeuxActeurs(String acteur1, String acteur2);

}


