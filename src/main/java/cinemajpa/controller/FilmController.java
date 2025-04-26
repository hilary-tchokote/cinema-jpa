package cinemajpa.controller;

import cinemajpa.model.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import cinemajpa.service.FilmService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/films")
public class FilmController {

    @Autowired
    private FilmService filmService;

    @GetMapping
    public List<Film> getAllFilms() {
        return filmService.getAll();
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable String id) {
        return filmService.getById(id);
    }

    @GetMapping("/between")
    public Set<Film> getFilmsBetweenYears(@RequestParam Integer debut, @RequestParam Integer fin) {
        return filmService.getBetweenYears(debut, fin);
    }

    @GetMapping("/between-with-actor")
    public Set<Film> getFilmsBetweenYearsAndActor(
            @RequestParam Integer debut,
            @RequestParam Integer fin,
            @RequestParam String acteur) {
        return filmService.getBetweenYearsWithActor(debut, fin, acteur);
    }

    @GetMapping("/communs")
    public Set<Film> getCommonFilms(
            @RequestParam String acteur1,
            @RequestParam String acteur2) {
        return filmService.getCommonFilms(acteur1, acteur2);
    }
}
