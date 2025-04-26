package cinemajpa.service;

import cinemajpa.util.JsonImporter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cinemajpa.model.Acteur;
import cinemajpa.model.Film;
import cinemajpa.model.Lieu;
import cinemajpa.model.Pays;
import cinemajpa.model.Role;
import cinemajpa.repository.ActeurRepository;
import cinemajpa.repository.FilmRepository;
import cinemajpa.repository.LieuRepository;
import cinemajpa.repository.PaysRepository;
import cinemajpa.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
public class CinemaDataImporter implements CommandLineRunner {

    private final JsonImporter jsonImporter;
    private final PaysRepository   paysRepo;
    private final LieuRepository   lieuRepo;
    private final ActeurRepository acteurRepo;
    private final FilmRepository   filmRepo;
    private final RoleRepository   roleRepo;
    private final ObjectMapper     mapper;

    public CinemaDataImporter(JsonImporter jsonImporter,
                              PaysRepository paysRepo,
                              LieuRepository lieuRepo,
                              ActeurRepository acteurRepo,
                              FilmRepository filmRepo,
                              RoleRepository roleRepo,
                              ObjectMapper mapper) {
        this.jsonImporter = jsonImporter;
        this.paysRepo     = paysRepo;
        this.lieuRepo     = lieuRepo;
        this.acteurRepo   = acteurRepo;
        this.filmRepo     = filmRepo;
        this.roleRepo     = roleRepo;
        this.mapper       = mapper;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        JsonNode root = jsonImporter.getRoot();
        importEtape1();
        importEtape2();
        importEtape3();
    }

    public void importEtape1() throws IOException {
        JsonNode root = jsonImporter.getRoot();
        for (JsonNode filmNode : root) {
            // 1.a) Pays
            JsonNode paysNode = filmNode.get("pays");
            Pays pays = mapper.convertValue(paysNode, Pays.class);
            Pays finalPays = pays;
            pays = paysRepo.findById(String.valueOf(pays.getId()))
                    .orElseGet(() -> paysRepo.save(finalPays));

            // 1.b) Lieu
            JsonNode lieuNode = filmNode.get("lieu");
            Lieu lieu = mapper.convertValue(lieuNode, Lieu.class);
            Lieu finalLieu = lieu;
            lieu = lieuRepo.findById(String.valueOf(lieu.getId()))
                    .orElseGet(() -> lieuRepo.save(finalLieu));

            // 1.c) Acteurs (données principales)
            for (JsonNode actNode : filmNode.withArray("castingPrincipal")) {
                // lieu de naissance
                JsonNode naissance = actNode.get("naissance");
                Lieu lieuNaiss = null;
                if (naissance != null && naissance.has("lieuNaissance")) {
                    lieuNaiss = mapper.convertValue(
                            naissance.get("lieuNaissance"), Lieu.class);
                    Lieu finalLieuNaiss = lieuNaiss;
                    lieuNaiss = lieuRepo.findById(String.valueOf(lieuNaiss.getId()))
                            .orElseGet(() -> lieuRepo.save(finalLieuNaiss));
                }

                Acteur acteur = mapper.convertValue(actNode, Acteur.class);
                acteur.setLieux(lieuNaiss);
                Acteur finalActeur = acteur;
                acteur = acteurRepo.findById(acteur.getId())
                        .orElseGet(() -> acteurRepo.save(finalActeur));
            }
        }
    }

    public void importEtape2() throws IOException {
        JsonNode root = jsonImporter.getRoot();
        for (JsonNode filmNode : root) {
            Film film = mapper.convertValue(filmNode, Film.class);

            // rattacher Pays & Lieu persistés
            Pays pays = paysRepo.findById(String.valueOf(film.getPays().getId())).orElse(null);
            Lieu lieu = lieuRepo.findById(String.valueOf(film.getLieu().getId())).orElse(null);
            film.setPays(pays);
            film.setLieu(lieu);

            Film finalFilm = film;
            film = filmRepo.findById(film.getId())
                    .orElseGet(() -> filmRepo.save(finalFilm));
        }
    }

    public void importEtape3() throws IOException {
        JsonNode root = jsonImporter.getRoot();
        for (JsonNode filmNode : root) {
            String filmId = filmNode.get("id").asText();
            Film film = filmRepo.findById(filmId)
                    .orElseThrow(() -> new IllegalStateException("Film non trouvé: " + filmId));

            for (JsonNode actNode : filmNode.withArray("castingPrincipal")) {
                String acteurId = actNode.get("id").asText();
                Acteur acteur = acteurRepo.findById(acteurId)
                        .orElseThrow(() -> new IllegalStateException("Acteur non trouvé: " + acteurId));

                Role role = new Role();
                role.setFilm(film);
                role.setActeur(acteur);
                role.setCharacterName(actNode.path("character").asText(null));

                roleRepo.save(role);
            }
        }
    }
}
