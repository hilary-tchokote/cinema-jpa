package cinemajpa;

import cinemajpa.console.ConsoleApp;
import cinemajpa.service.CinemaDataImporter;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import cinemajpa.util.JsonImporter;

@SpringBootApplication
public class CinemaJpaApplication implements CommandLineRunner {

    @Autowired
    private JsonImporter importer;

    @Autowired
    private ConsoleApp consoleApp;

    @Value("${cinema.json.path}")
    private String jsonPath;

    @Autowired
    private CinemaDataImporter dataImporter;


    public static void main(String[] args) {
        SpringApplication.run(CinemaJpaApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("⏳ Importation des données depuis JSON...");
        importer.importFromJson(jsonPath);
        System.out.println("Importation JSON – étape 1");
        dataImporter.importEtape1();
        System.out.println("Importation JSON – étape 2");
        dataImporter.importEtape2();
        System.out.println("Importation JSON – étape 3");
        dataImporter.importEtape3();
        System.out.println("Importation terminée.");

        consoleApp.run(); // Lancement du menu console ici
    }
}
