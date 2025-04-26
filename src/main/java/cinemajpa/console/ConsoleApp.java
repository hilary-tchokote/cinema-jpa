package cinemajpa.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cinemajpa.service.ActeurService;
import cinemajpa.service.FilmService;
import cinemajpa.service.RoleService;

import java.util.Scanner;

/**
 * Classe de gestion du menu console pour interagir avec les données cinéma.
 * Utilise Scanner pour lire les choix utilisateur et appelle les services métier.
 */
@Component
public class ConsoleApp {

    @Autowired
    private FilmService filmService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ActeurService acteurService;

    /**
     * Lance le menu interactif console.
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);
        int choix;

        do {
            System.out.println("""
                    \n========= MENU =========
                    1. Filmographie d’un acteur
                    2. Casting d’un film
                    3. Films entre deux années
                    4. Films communs à 2 acteurs
                    5. Acteurs communs à 2 films
                    6. Films entre 2 années avec un acteur
                    7. Quitter
                    ========================
                    Votre choix : """);

            choix = scanner.nextInt();
            scanner.nextLine(); // consomme \n

            switch (choix) {
                case 1 -> {
                    System.out.print("Nom de l'acteur : ");
                    String nom = scanner.nextLine();
                    roleService.getFilmographie(nom).forEach(f -> System.out.println(f.getNom()));
                }
                case 2 -> {
                    System.out.print("Nom du film : ");
                    String nom = scanner.nextLine();
                    roleService.getCasting(nom).forEach(a -> System.out.println(a.getIdentite()));
                }
                case 3 -> {
                    System.out.print("Année début : ");
                    Integer debut = Integer.valueOf(scanner.nextLine());
                    System.out.print("Année fin : ");
                    Integer fin = Integer.valueOf(scanner.nextLine());
                    filmService.getBetweenYears(debut, fin).forEach(f -> System.out.println(f.getNom()));
                }
                case 4 -> {
                    System.out.print("Acteur 1 : ");
                    String a1 = scanner.nextLine();
                    System.out.print("Acteur 2 : ");
                    String a2 = scanner.nextLine();
                    filmService.getCommonFilms(a1, a2).forEach(f -> System.out.println(f.getNom()));
                }
                case 5 -> {
                    System.out.print("Film 1 : ");
                    String f1 = scanner.nextLine();
                    System.out.print("Film 2 : ");
                    String f2 = scanner.nextLine();
                    roleService.getActeursCommuns(f1, f2).forEach(a -> System.out.println(a.getIdentite()));
                }
                case 6 -> {
                    System.out.print("Année début : ");
                    Integer d = Integer.valueOf(scanner.nextLine());
                    System.out.print("Année fin : ");
                    Integer f = Integer.valueOf(scanner.nextLine());
                    System.out.print("Acteur : ");
                    String a = scanner.nextLine();
                    filmService.getBetweenYearsWithActor(d, f, a).forEach(film -> System.out.println(film.getNom()));
                }
                case 7 -> System.out.println("Fin de l'application. Merci !");
                default -> System.out.println("Choix invalide.");
            }

        } while (choix != 7);
    }
}
