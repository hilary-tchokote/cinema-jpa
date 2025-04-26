package cinemajpa.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cinemajpa.model.Film;
import cinemajpa.repository.FilmRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import org.springframework.core.io.ClassPathResource;
import java.io.UncheckedIOException;
import java.util.Set;

@Component
public class JsonImporter {

    private final FilmRepository filmRepo;
    private final ResourceLoader resourceLoader;
    private final ObjectMapper mapper;
    private JsonNode root;

    public JsonImporter(FilmRepository filmRepo,
                        ResourceLoader resourceLoader,
                        @Lazy ObjectMapper mapper) {
        this.filmRepo       = filmRepo;
        this.resourceLoader = resourceLoader;
        this.mapper         = mapper;
    }

    /**
     * Charge le JSON depuis le classpath, en construit l'arbre,
     * désérialise en Set<Film> puis persiste le tout.
     *
     * @param path Chemin vers le fichier JSON, ex. "classpath:films.jsons"
     * @return la racine JsonNode du document
     */
    public JsonNode importFromJson(String path) throws IOException {
        // Resource resource = resourceLoader.getResource("classpath:" + path);
        ClassPathResource resource = new ClassPathResource("films.json");
        try (InputStream is = resource.getInputStream()) {
            Set<Film> films = mapper.readValue(is, new TypeReference<Set<Film>>() {});
            filmRepo.saveAll(films);
            return mapper.readTree(is);
        }
    }

    /**
     * Renvoie le JsonNode racine, en important le JSON si besoin.
     */
    public JsonNode getRoot() throws IOException {
        if (this.root == null) {
            importFromJson("classpath:films.jsons");
        }
        return this.root;
    }
}
