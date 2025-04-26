package cinemajpa.service;
import cinemajpa.model.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cinemajpa.repository.FilmRepository;

import java.util.List;
import java.util.Set;

@Service
public class FilmService {

    @Autowired
    private FilmRepository filmRepo;

    public List<Film> getAll() {
        return filmRepo.findAll();
    }

    public Film getById(String id) {
        return filmRepo.findById(id).orElse(null);
    }

    public Set<Film> getBetweenYears(Integer debut, Integer fin) {
        return filmRepo.findByAnneeSortieBetween(debut, fin);
    }

    public Set<Film> getBetweenYearsWithActor(Integer debut, Integer fin, String acteur) {
        return filmRepo.findByAnneeSortieAndActeur(debut, fin, acteur);
    }

    public Set<Film> getCommonFilms(String acteur1, String acteur2) {
        return filmRepo.findFilmsCommunsADeuxActeurs(acteur1, acteur2);
    }


    public void setFilmRepository(FilmRepository filmRepo) {
        this.filmRepo = filmRepo;
    }
}
