package cinemajpa.service;

import cinemajpa.model.Film;
import cinemajpa.repository.FilmRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class FilmServiceTest {


    private FilmRepository filmRepo;
    private FilmService filmService;

    @BeforeEach
    void setUp() {
        filmRepo = mock(FilmRepository.class);
        filmService = new FilmService();
        filmService.setFilmRepository(filmRepo);
    }

    @Test
    void testGetBetweenYears() {
        // Prépare deux films factices
        Film f1 = new Film("tt123", "Film 1", "Plot", "English", 2022);
        Film f2 = new Film("tt124", "Film 2", "Plot", "English", 2021);

        // On se sert d'un Set pour le mock
        Set<Film> mockSet = Set.of(f1, f2);

        // Quand on appelle le repo, on renvoie ce Set
        when(filmRepo.findByAnneeSortieBetween(2020, 2022)).thenReturn(mockSet);

        // On appelle le service
        Set<Film> result = filmService.getBetweenYears(2020, 2022);

        // Assertions
        assertEquals(2, result.size(), "On doit avoir exactement 2 films");
        assertTrue(result.contains(f1), "Le film f1 doit être présent");
        assertTrue(result.contains(f2), "Le film f2 doit être présent");

        // Vérification que le repo a bien été sollicité
        verify(filmRepo, times(1)).findByAnneeSortieBetween(2020, 2022);
    }
}
