package Controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class printController {
    private static Connection dbConnect;
    private ResultSet results;

    public static void createConnection() throws SQLException {
        try {
            dbConnect = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/testairline?useSSL=false&serverTimezone=UTC", "root",
                    "MySQL_Root26");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getBookings(String model, String fName, String lName, String email) throws SQLException {
        List<String> bookingList = new ArrayList<>();
        String tableName = model + "BOOKINGS";
        String query = "SELECT * FROM " + tableName + " WHERE Model=? AND FirstName=? AND LastName=? AND Email=?";

        try (PreparedStatement preparedStatement = dbConnect.prepareStatement(query)) {
            preparedStatement.setString(1, model);
            preparedStatement.setString(2, fName);
            preparedStatement.setString(3, lName);
            preparedStatement.setString(4, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String mod = resultSet.getString("Model");
                    String first = resultSet.getString("FirstName");
                    String last = resultSet.getString("LastName");
                    String em = resultSet.getString("Email");
                    String type = resultSet.getString("SeatType");
                    int num = resultSet.getInt("SeatNumber");

                    String bookingString = "Flight Model: " + mod +
                            ", First Name: " + first +
                            ", Last Name: " + last +
                            ", Email: " + em +
                            ", Seat Type: " + type +
                            ", Seat Number: " + num;

                    bookingList.add(bookingString);
                }
            }
        }

        return bookingList;
    }

    public static List<String> getPassengers(String model) throws SQLException {
        List<String> passengerList = new ArrayList<>();
        String tableName = model + "BOOKINGS";
        String query = "SELECT * FROM " + tableName;

        Statement myStmt = dbConnect.createStatement();
        ResultSet resultSet = myStmt.executeQuery(query);

        while (resultSet.next()) {
            String mod = resultSet.getString("Model");
            String first = resultSet.getString("FirstName");
            String last = resultSet.getString("LastName");
            String em = resultSet.getString("Email");
            String type = resultSet.getString("SeatType");
            int num = resultSet.getInt("SeatNumber");

            String passString = "Flight Model: " + mod +
                    ", First Name: " + first +
                    ", Last Name: " + last +
                    ", Email: " + em +
                    ", Seat Type: " + type +
                    ", Seat Number: " + num;

            passengerList.add(passString);
        }
        return passengerList;
    }
}
