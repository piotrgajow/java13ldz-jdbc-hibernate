package sda;

import com.mysql.cj.jdbc.JdbcConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try(Connection connection = prepareConnection()) {
            Place newPlace = askUserForPlace();
            addPlace(connection, newPlace);
            List<Place> places = getPlaces(connection);
            places.forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
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

    private static void addPlace(Connection connection, Place place) throws SQLException {
        String sqlQuery = "INSERT INTO place (city, address, name, cloak_room, parking) VALUES (?, ?, ?, ?, ?);";

        PreparedStatement statement = connection.prepareStatement(sqlQuery);
        statement.setString(1, place.city);
        statement.setString(2, place.address);
        statement.setString(3, place.name);
        statement.setBoolean(4, place.cloakRoom);
        statement.setBoolean(5, place.parking);
        statement.executeUpdate();

        statement.close();
    }

    private static List<Place> getPlaces(Connection connection) throws SQLException {
        String sqlQuery = "SELECT * FROM place;";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlQuery);

        List<Place> places = new ArrayList<>();
        while (resultSet.next()) {
            Place place = new Place();

            place.id = resultSet.getInt("id");
            place.city = resultSet.getString("city");
            place.address = resultSet.getString("address");
            place.name = resultSet.getString("name");
            place.cloakRoom = resultSet.getBoolean("cloak_room");
            place.parking = resultSet.getBoolean("parking");

            places.add(place);
        }

        resultSet.close();
        statement.close();

        return places;
    }

    private static Connection prepareConnection() throws SQLException {
        String user = "root";
        String password = "";
        String url = "jdbc:mysql://localhost:3306/sda_hibernate?serverTimezone=Europe/Warsaw";
        return DriverManager.getConnection(url, user, password);
    }

}
