package cinemajpa.service;

import cinemajpa.model.Acteur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cinemajpa.repository.ActeurRepository;

import java.util.Optional;

@Service
public class ActeurService {

    @Autowired
    private ActeurRepository acteurRepo;

    public Optional<Acteur> findByName(String nom) {
        return acteurRepo.findByIdentite(nom);
    }
}
