package cinemajpa.service;
import cinemajpa.model.Acteur;
import cinemajpa.model.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cinemajpa.repository.RoleRepository;

import java.util.Set;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepo;

    public Set<Film> getFilmographie(String nomActeur) {
        return (Set<Film>) roleRepo.findFilmographieByActeur(nomActeur);
    }

    public Set<Acteur> getCasting(String nomFilm) {
        return (Set<Acteur>) roleRepo.findCastingByFilm(nomFilm);
    }

    public Set<Acteur> getActeursCommuns(String film1, String film2) {
        return (Set<Acteur>) roleRepo.findActeursCommunsEntreDeuxFilms(film1, film2);
    }
}
