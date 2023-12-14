package Controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// import Controller.DatabaseController;

public class adminController {

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

    public List<String> registeredUsers() throws SQLException {
        List<String> userList = new ArrayList<>();
        try {
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM REGISTERED_USER");

            while (results.next()) {
                String firstName = results.getString("First_Name");
                String lastName = results.getString("Last_Name");
                String email = results.getString("Email");
                String userAddress = results.getString("User_Address");
                String postalCode = results.getString("Postal_Code");

                String userString = "Name: " + firstName + " " + lastName +
                        ", Email: " + email +
                        ", Address: " + userAddress +
                        ", Postal Code: " + postalCode;

                userList.add(userString);
            }
            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return userList;
    }

    public static List<String> getAllFlights() throws SQLException {
        List<String> flightList = new ArrayList<>();

        Statement myStmt = dbConnect.createStatement();
        String query = "SELECT * FROM FLIGHT";
        ResultSet resultSet = myStmt.executeQuery(query);

        // Iterate over the results
        while (resultSet.next()) {

            int flightNumber = resultSet.getInt("Flight_Number");
            String model = resultSet.getString("Model");
            String origin = resultSet.getString("Origin");
            String destination = resultSet.getString("Destination");
            String departure = resultSet.getString("Departure");

            System.out.println(flightNumber);

            String flightString = "Flight Number: " + flightNumber +
                    ", Model: " + model +
                    ", Origin: " + origin +
                    ", Destination: " + destination +
                    ", Departure: " + departure;

            System.out.println(flightString);
            flightList.add(flightString);

        }

        resultSet.close();
        myStmt.close();

        return flightList;
    }

    public List<String> getAllAircraft() throws SQLException {
        List<String> aircraftList = new ArrayList<>();
        try {
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM AIRCRAFT");

            while (results.next()) {
                String manufacturer = results.getString("Manufacturer");
                String model = results.getString("Model");
                int ordinaryCapacity = results.getInt("Ordinary_Capacity");
                int comfortCapacity = results.getInt("Comfort_Capacity");
                int businessCapacity = results.getInt("Business_Capacity");

                String aircraftString = "Manufacturer: " + manufacturer +
                        ", Model: " + model +
                        ", Ordinary Capacity: " + ordinaryCapacity +
                        ", Comfort Capacity: " + comfortCapacity +
                        ", Business Capacity: " + businessCapacity;

                aircraftList.add(aircraftString);
            }
            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return aircraftList;
    }

    public List<String> getAllCrewMembers() throws SQLException {
        List<String> crewList = new ArrayList<>();
        try {
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM CREW_MEMBER");

            while (results.next()) {
                String firstName = results.getString("First_Name");
                String lastName = results.getString("Last_Name");
                String email = results.getString("Email");

                String crewString = "Name: " + firstName + " " + lastName +
                        ", Email: " + email;

                crewList.add(crewString);
            }
            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return crewList;
    }

    public void updateFlightInfo(String flightNumber, String model, String origin, String destination,
            String departure) {
        try {
            int flightNum = Integer.parseInt(flightNumber);
            String query = "UPDATE FLIGHT SET Model=?, Origin=?, Destination=?, Departure=? WHERE Flight_Number=?";

            PreparedStatement myStmt = dbConnect.prepareStatement(query);
            myStmt.setString(1, model);
            myStmt.setString(2, origin);
            myStmt.setString(3, destination);
            myStmt.setString(4, departure);
            myStmt.setInt(5, flightNum);

            myStmt.executeUpdate();
            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void addDestination(String flightNumber, String flightModel, String dest) {
        try {
            int flightNum = Integer.parseInt(flightNumber);
            String query = "SELECT * FROM FLIGHT WHERE Flight_Number=?";

            try (PreparedStatement selectStatement = dbConnect.prepareStatement(query)) {
                selectStatement.setInt(1, flightNum);
                ResultSet resultSet = selectStatement.executeQuery();

                if (resultSet.next()) {
                    // Create a new copy of the flight with the updated destination

                    Random random = new Random();
                    int rand = random.nextInt();
                    String insertQuery = "INSERT INTO FLIGHT (Flight_Number, Model, Origin, Destination, Departure) VALUES (?, ?, ?, ?, ?)";

                    try (PreparedStatement insertStatement = dbConnect.prepareStatement(insertQuery)) {
                        insertStatement.setInt(1, rand);
                        insertStatement.setString(2, flightModel);
                        insertStatement.setString(3, resultSet.getString("Origin"));
                        insertStatement.setString(4, dest); // Updated destination
                        insertStatement.setString(5, resultSet.getString("Departure"));

                        int rowsInserted = insertStatement.executeUpdate();

                        if (rowsInserted > 0) {
                            System.out.println("Flight duplicated successfully.");
                        } else {
                            System.out.println("Failed to duplicate flight.");
                        }
                    }
                } else {
                    System.out.println("No flight found with Flight Number: " + flightNumber);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed, show an error dialog, log, etc.
        }
    }

    public void addAircraft(String manufacturer, String model, String ordinaryCapacity, String comfortCapacity,
            String businessCapacity) {
        try {
            int ordinary = Integer.parseInt(ordinaryCapacity);
            int comfort = Integer.parseInt(comfortCapacity);
            int business = Integer.parseInt(businessCapacity);

            String query = "INSERT INTO AIRCRAFT (Manufacturer, Model, Ordinary_Capacity, " +
                    "Comfort_Capacity, Business_Capacity) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = dbConnect.prepareStatement(query)) {
                preparedStatement.setString(1, manufacturer);
                preparedStatement.setString(2, model);
                preparedStatement.setInt(3, ordinary);
                preparedStatement.setInt(4, comfort);
                preparedStatement.setInt(5, business);

                int rowsInserted = preparedStatement.executeUpdate();

                if (rowsInserted > 0) {
                    System.out.println("Aircraft added successfully.");
                } else {
                    System.out.println("Failed to add aircraft.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed, show an error dialog, log, etc.
        }

    }

    public void addCrewMember(String identification, String firstName, String lastName, String email) {
        try {
            // Create a new entry in the CREW_MEMBER table
            int id = Integer.parseInt(identification);
            String role = "CREW";
            String pass = "randomPassword";
            String salt = "DynaSalt";
            String firstQuery = "INSERT INTO LOGIN_INFO (Email, Passkey, DynSalt, Login_Role) VALUES (?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = dbConnect.prepareStatement(firstQuery)) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, pass);
                preparedStatement.setString(3, salt);
                preparedStatement.setString(4, role);

                int rowsInserted = preparedStatement.executeUpdate();

                if (rowsInserted > 0) {
                    String query = "INSERT INTO CREW_MEMBER (ID, First_Name, Last_Name, Email) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement insertStatement = dbConnect.prepareStatement(query)) {
                        insertStatement.setInt(1, id);
                        insertStatement.setString(2, firstName);
                        insertStatement.setString(3, lastName);
                        insertStatement.setString(4, email);

                        int rowInserted2 = insertStatement.executeUpdate();

                        if (rowInserted2 > 0) {
                            System.out.println("Crew Inserted successfully.");
                        } else {
                            System.out.println("Failed to insert Crew.");
                        }
                    }
                } else {
                    System.out.println("Failed to add crew member.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed, show an error dialog, log, etc.
        }
    }

    public void addFlightInfo(String flightNumber, String attributeName, String attributeValue) {
        try {
            int flightNum = Integer.parseInt(flightNumber);
            // Check if the flight exists
            if (flightExists(flightNum)) {
                // Update the specified attribute for the given flight number
                String updateQuery = "UPDATE FLIGHT SET " + attributeName + "=? WHERE Flight_Number=?";

                try (PreparedStatement preparedStatement = dbConnect.prepareStatement(updateQuery)) {
                    preparedStatement.setString(1, attributeValue);
                    preparedStatement.setInt(2, flightNum);

                    int rowsUpdated = preparedStatement.executeUpdate();

                    if (rowsUpdated > 0) {
                        System.out.println("Flight attribute updated successfully.");
                    } else {
                        System.out.println("Failed to update flight attribute.");
                    }
                }
            } else {
                System.out.println("No flight found with Flight Number: " + flightNumber);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed, show an error dialog, log, etc.
        }
    }

    private boolean flightExists(int flightNumber) throws SQLException {
        // Check if the flight exists
        String checkQuery = "SELECT * FROM FLIGHT WHERE Flight_Number=?";
        try (PreparedStatement checkStatement = dbConnect.prepareStatement(checkQuery)) {
            checkStatement.setInt(1, flightNumber);
            ResultSet resultSet = checkStatement.executeQuery();
            return resultSet.next();
        }
    }

    public void deleteAircraft(String manufacturer, String model) {
        try {
            // Delete the row from the AIRCRAFT table based on both manufacturer and model
            String query = "DELETE FROM AIRCRAFT WHERE Manufacturer=? AND Model=?";

            try (PreparedStatement preparedStatement = dbConnect.prepareStatement(query)) {
                preparedStatement.setString(1, manufacturer);
                preparedStatement.setString(2, model);

                int rowsDeleted = preparedStatement.executeUpdate();

                if (rowsDeleted > 0) {
                    System.out.println("Aircraft deleted successfully.");
                } else {
                    System.out.println("Failed to delete aircraft. No matching manufacturer and model found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed, show an error dialog, log, etc.
        }
    }

    public void deleteCrewMember(String identification, String firstName, String lastName, String email) {
        try {
            // Delete the row from the CREW_MEMBER table based on all attributes
            int id = Integer.parseInt(identification);
            String query = "DELETE FROM CREW_MEMBER WHERE ID=? AND First_Name=? AND Last_Name=? AND Email=?";

            try (PreparedStatement preparedStatement = dbConnect.prepareStatement(query)) {
                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, firstName);
                preparedStatement.setString(3, lastName);
                preparedStatement.setString(4, email);

                String deleteStr = "DELETE FROM LOGIN_INFO WHERE Email = ?";

                PreparedStatement deleteStatement = dbConnect.prepareStatement(deleteStr);
                deleteStatement.setString(1, email);
                deleteStatement.executeUpdate();

                int rowsDeleted = preparedStatement.executeUpdate();

                if (rowsDeleted > 0) {
                    System.out.println("Crew member deleted successfully.");
                } else {
                    System.out.println("Failed to delete crew member. No matching attributes found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed, show an error dialog, log, etc.
        }
    }

    public void deleteFlightDest(String flightModel, String dest) {
        try {
            // Delete the row from the FLIGHT table based on the flight number
            String query = "DELETE FROM FLIGHT WHERE Model=? AND Destination=?";

            try (PreparedStatement preparedStatement = dbConnect.prepareStatement(query)) {
                preparedStatement.setString(1, flightModel);
                preparedStatement.setString(2, dest);

                int rowsDeleted = preparedStatement.executeUpdate();

                if (rowsDeleted > 0) {
                    System.out.println("Flight deleted successfully.");
                } else {
                    System.out.println("Failed to delete flight. No matching flight number found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed, show an error dialog, log, etc.
        }
    }

    public void removeFlightAttribute(String flightNum, String attributeName) {
        try {
            int flightNumber = Integer.parseInt(flightNum);
            // Update the specified attribute in the FLIGHT table to null
            String query = "UPDATE FLIGHT SET " + attributeName + " = NULL WHERE Flight_Number=?";

            try (PreparedStatement preparedStatement = dbConnect.prepareStatement(query)) {
                preparedStatement.setInt(1, flightNumber);

                int rowsUpdated = preparedStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Attribute '" + attributeName + "' removed successfully.");
                } else {
                    System.out.println("Failed to remove attribute. No matching flight number found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed, show an error dialog, log, etc.
        }
    }
}
