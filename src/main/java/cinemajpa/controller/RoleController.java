package cinemajpa.controller;

import cinemajpa.model.Acteur;
import cinemajpa.model.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import cinemajpa.service.RoleService;

import java.util.Set;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/filmographie")
    public Set<Film> getFilmographie(@RequestParam String acteur) {
        return roleService.getFilmographie(acteur);
    }

    @GetMapping("/casting")
    public Set<Acteur> getCasting(@RequestParam String film) {
        return roleService.getCasting(film);
    }

    @GetMapping("/communs")
    public Set<Acteur> getActeursCommuns(
            @RequestParam String film1,
            @RequestParam String film2) {
        return roleService.getActeursCommuns(film1, film2);
    }
}
