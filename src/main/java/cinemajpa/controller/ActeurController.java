package cinemajpa.controller;

import cinemajpa.model.Acteur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import cinemajpa.service.ActeurService;

import java.util.Optional;

@RestController
@RequestMapping("/api/acteurs")
public class ActeurController {

    @Autowired
    private ActeurService acteurService;

    @GetMapping("/{nom}")
    public Optional<Acteur> getActeurByName(@PathVariable String nom) {
        return acteurService.findByName(nom);
    }
}
