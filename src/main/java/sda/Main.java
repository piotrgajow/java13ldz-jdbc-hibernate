package sda;

import com.mysql.cj.jdbc.JdbcConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try(Connection connection = prepareConnection()) {
            List<Place> places = getPlaces(connection);
            places.forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
