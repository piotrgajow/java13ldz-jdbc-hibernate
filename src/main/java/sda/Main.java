package sda;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("sda_hibernate");

        EntityManager entityManager = factory.createEntityManager();
        Place place = askUserForPlace();

        entityManager.getTransaction().begin();
        entityManager.persist(place);

        List<Place> places = entityManager.createQuery("FROM Place", Place.class).getResultList();
        places.forEach(System.out::println);

        askUserForDelete(entityManager);

        entityManager.getTransaction().commit();

        places = entityManager.createQuery("FROM Place", Place.class).getResultList();
        places.forEach(System.out::println);

        entityManager.close();
        factory.close();
    }

    private static void askUserForDelete(EntityManager entityManager) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Czy chcesz usunąć jakiś wiersz? (tak):  ");
        if ("tak".equals(scanner.nextLine().toLowerCase())) {
            System.out.println("Który wiersz chcesz usunąć? ");
            Integer id = scanner.nextInt();

            Place place = entityManager.find(Place.class, id);
            entityManager.remove(place);
        } else {
            System.exit(0);
        }
    }

    private static Place askUserForPlace() {
        Place place = new Place();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Podaj miasto: ");
        place.city = scanner.nextLine();
        System.out.print("Podaj adres: ");
        place.address = scanner.nextLine();
        System.out.print("Podaj nazwę: ");
        place.name = scanner.nextLine();
        System.out.print("Czy na miejscu jest szatnia (tak): ");
        place.cloakRoom = "tak".equals(scanner.nextLine().toLowerCase());
        System.out.print("Czy na miejscu jest parking (tak): ");
        place.parking = "tak".equals(scanner.nextLine().toLowerCase());

        return place;
    }

}
