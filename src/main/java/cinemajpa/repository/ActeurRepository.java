package cinemajpa.repository;

import cinemajpa.model.Acteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActeurRepository extends JpaRepository<Acteur, String> {

    Optional<Acteur> findByIdentite(String identite);
}
