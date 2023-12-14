package Controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.naming.spi.DirStateFactory.Result;

public class FlightInfoController {
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

    public static List<String> getAllFlights(String depart, String arrive, String date) throws SQLException {
        List<String> flightList = new ArrayList<>();

        String query = "SELECT * FROM FLIGHT WHERE Origin=? AND Destination=? AND Departure=?";

        try (PreparedStatement preparedStatement = dbConnect.prepareStatement(query)) {
            preparedStatement.setString(1, depart);
            preparedStatement.setString(2, arrive);
            preparedStatement.setString(3, date);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int flightNumber = resultSet.getInt("Flight_Number");
                    String model = resultSet.getString("Model");
                    String origin = resultSet.getString("Origin");
                    String destination = resultSet.getString("Destination");
                    String departure = resultSet.getString("Departure");

                    String flightString = "Flight Number: " + flightNumber +
                            ", Model: " + model +
                            ", Origin: " + origin +
                            ", Destination: " + destination +
                            ", Departure: " + departure;

                    flightList.add(flightString);
                }
            }
        }

        return flightList;
    }

    public static List<String> getFlights() throws SQLException {
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

            String flightString = "Flight Number: " + flightNumber +
                    ", Model: " + model +
                    ", Origin: " + origin +
                    ", Destination: " + destination +
                    ", Departure: " + departure;

            flightList.add(flightString);

        }

        resultSet.close();
        myStmt.close();

        return flightList;
    }

    public static void seatCreator(String model) {
        String query = "SELECT * FROM AIRCRAFT WHERE Model=?";

        try (PreparedStatement preparedStatement = dbConnect.prepareStatement(query)) {
            preparedStatement.setString(1, model);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int ordinary = resultSet.getInt("Ordinary_Capacity");
                    int comfort = resultSet.getInt("Comfort_Capacity");
                    int business = resultSet.getInt("Business_Capacity");
                    int total = ordinary + comfort + business;

                    System.out.println(ordinary);
                    System.out.println(comfort);
                    System.out.println(business);
                    System.out.println(ordinary + comfort + business);

                    createSeatTable(model, ordinary, "O", total);
                    createSeatTable(model, comfort, "C", total);
                    createSeatTable(model, business, "B", total);

                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void createSeatTable(String model, int capacity, String seatType, int total) {
        String seatTableName = model + "SEATS";
        String bookingTableName = model + "BOOKINGS";

        // Create seats table with Price attribute
        String createSeatsTableQuery = "CREATE TABLE IF NOT EXISTS " + seatTableName + " ("
                + "ID INT AUTO_INCREMENT PRIMARY KEY,"
                + "Model VARCHAR(255),"
                + "SeatType VARCHAR(1),"
                + "Number INT,"
                + "Availability INT,"
                + "Price INT)"; // Add the Price attribute

        // Count the existing rows in seats table
        String countSeatsQuery = "SELECT COUNT(*) FROM " + seatTableName;

        // Insert data into seats table with Price based on Seat Type
        String insertSeatsDataQuery = "INSERT INTO " + seatTableName
                + " (Model, SeatType, Number, Availability, Price) VALUES (?, ?, ?, 1, ?)";

        // Create bookings table
        String createBookingsTableQuery = "CREATE TABLE IF NOT EXISTS " + bookingTableName + " ("
                + "ID INT AUTO_INCREMENT PRIMARY KEY,"
                + "Model VARCHAR(255),"
                + "FirstName VARCHAR(255),"
                + "LastName VARCHAR(255),"
                + "Email VARCHAR(255),"
                + "SeatType VARCHAR(1),"
                + "SeatNumber INT,"
                + "Insurance INT)";

        try (Statement statement = dbConnect.createStatement()) {
            // Create seats table if it doesn't exist
            statement.executeUpdate(createSeatsTableQuery);

            // Count existing rows in seats table
            try (ResultSet resultSet = statement.executeQuery(countSeatsQuery)) {
                if (resultSet.next()) {
                    int existingRowCount = resultSet.getInt(1);

                    // Check if the number of existing rows is less than the total argument
                    if (existingRowCount < total) {
                        // Insert data into seats table with Price
                        try (PreparedStatement insertSeatsStatement = dbConnect
                                .prepareStatement(insertSeatsDataQuery)) {
                            for (int i = 1; i <= capacity; i++) {
                                insertSeatsStatement.setString(1, model);
                                insertSeatsStatement.setString(2, seatType);
                                insertSeatsStatement.setInt(3, i);

                                // Set the Price based on Seat Type
                                int price;
                                switch (seatType) {
                                    case "O":
                                        price = 50;
                                        break;
                                    case "C":
                                        price = 75;
                                        break;
                                    case "B":
                                        price = 100;
                                        break;
                                    default:
                                        price = 0; // Set a default value or handle invalid seat types
                                        break;
                                }

                                insertSeatsStatement.setInt(4, price);

                                insertSeatsStatement.executeUpdate();
                            }
                        }
                    } else {
                        System.out.println(
                                "The number of existing rows in the seats table is equal to or greater than the total argument. No insertion will be performed.");
                    }
                }
            }

            // Create bookings table if it doesn't exist
            statement.executeUpdate(createBookingsTableQuery);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static List<Integer> getSeatsIds(String model) {
        List<Integer> ids = new ArrayList<>();
        try {
            Statement myStmt = dbConnect.createStatement();
            String tableName = model + "SEATS";
            String query = "SELECT * FROM " + tableName;

            ResultSet resultSet = myStmt.executeQuery(query);
            while (resultSet.next()) {
                int seatIds = resultSet.getInt("ID");
                ids.add(seatIds);
            }

            resultSet.close();
            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return ids;
    }

    public static List<String> getSeatsType(String model) {
        List<String> types = new ArrayList<>();
        try {
            Statement myStmt = dbConnect.createStatement();
            String tableName = model + "SEATS";
            String query = "SELECT * FROM " + tableName;

            ResultSet resultSet = myStmt.executeQuery(query);
            while (resultSet.next()) {
                String seatTypes = resultSet.getString("SeatType");
                types.add(seatTypes);
            }

            resultSet.close();
            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return types;
    }

    public static List<Integer> getSeatsNum(String model) {
        List<Integer> nums = new ArrayList<>();
        try {
            Statement myStmt = dbConnect.createStatement();
            String tableName = model + "SEATS";
            String query = "SELECT * FROM " + tableName;

            ResultSet resultSet = myStmt.executeQuery(query);
            while (resultSet.next()) {
                int seatNums = resultSet.getInt("Number");
                nums.add(seatNums);
            }

            resultSet.close();
            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return nums;
    }

    public static List<Integer> getSeatsAvailability(String model) {
        List<Integer> avails = new ArrayList<>();
        try {
            Statement myStmt = dbConnect.createStatement();
            String tableName = model + "SEATS";
            String query = "SELECT * FROM " + tableName;

            ResultSet resultSet = myStmt.executeQuery(query);
            while (resultSet.next()) {
                int seatAvail = resultSet.getInt("Availability");
                avails.add(seatAvail);
            }

            resultSet.close();
            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return avails;
    }

    public static int getSeatCost(String model, int seatId, String seatType, int seatNumber) {
        String seatTableName = model + "SEATS";
        int cost = 0;

        // Select the Price from the seats table based on Seat ID, Type, and Number
        String selectSeatCostQuery = "SELECT Price FROM " + seatTableName
                + " WHERE ID = ? AND SeatType = ? AND Number = ?";

        try (PreparedStatement selectSeatCostStatement = dbConnect.prepareStatement(selectSeatCostQuery)) {
            selectSeatCostStatement.setInt(1, seatId);
            selectSeatCostStatement.setString(2, seatType);
            selectSeatCostStatement.setInt(3, seatNumber);

            try (ResultSet resultSet = selectSeatCostStatement.executeQuery()) {
                if (resultSet.next()) {
                    cost = resultSet.getInt("Price");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return cost;
    }

    public static void makeBooking(String model, String first, String last, String email, String type, int ins,
            int num) {
        String bookingTableName = model + "BOOKINGS";

        // Insert data into bookings table
        String insertBookingDataQuery = "INSERT INTO " + bookingTableName
                + " (Model, FirstName, LastName, Email, SeatType, SeatNumber, Insurance) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement insertBookingStatement = dbConnect.prepareStatement(insertBookingDataQuery)) {

            // Insert data into bookings table
            insertBookingStatement.setString(1, model);
            insertBookingStatement.setString(2, first);
            insertBookingStatement.setString(3, last);
            insertBookingStatement.setString(4, email);
            insertBookingStatement.setString(5, type);
            insertBookingStatement.setInt(6, num);
            insertBookingStatement.setInt(7, ins);
            insertBookingStatement.executeUpdate();

            System.out.println("First:  " + first + "  Last:  " + last + "   Email: " + email);
            System.out.println("Model:  " + model + "  Type:  " + type + "   Num: " + num);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static int cancelBooking(String model, String type, int num, long ccNum, int cost) {
        int cancel = 0;
        String bookingTableName = model + "BOOKINGS";
        String deleteBookingQuery = "DELETE FROM " + bookingTableName
                + " WHERE Model=? AND SeatType=? AND SeatNumber=?";

        try (PreparedStatement deleteBookingStatement = dbConnect.prepareStatement(deleteBookingQuery)) {
            deleteBookingStatement.setString(1, model);
            deleteBookingStatement.setString(2, type);
            deleteBookingStatement.setInt(3, num);
            int rowsAffected = deleteBookingStatement.executeUpdate();

            if (rowsAffected > 0) {
                cancel = 1;
                updateCreditCardBalance(ccNum, cost);
            } else {
                cancel = 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return cancel;
    }

    private static void updateCreditCardBalance(long ccNum, int cost) {
        String updateBalanceQuery = "UPDATE CREDITCARD SET Balance = Balance + ? WHERE Card_Number = ?";
        try (PreparedStatement updateBalanceStatement = dbConnect.prepareStatement(updateBalanceQuery)) {
            updateBalanceStatement.setInt(1, cost);
            updateBalanceStatement.setLong(2, ccNum);
            int rowsUpdated = updateBalanceStatement.executeUpdate();

            if (rowsUpdated == 0) {
                System.out.println("User has too much money. They don't need a refund.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void changeAvailability(String model, String type, int num) {
        String seatTableName = model + "SEATS";
        String updateAvailabilityQuery = "UPDATE " + seatTableName
                + " SET Availability = CASE WHEN Availability = 1 THEN 0 ELSE 1 END WHERE SeatType=? AND Number=?";

        try (PreparedStatement updateAvailabilityStatement = dbConnect.prepareStatement(updateAvailabilityQuery)) {
            updateAvailabilityStatement.setString(1, type);
            updateAvailabilityStatement.setInt(2, num);
            updateAvailabilityStatement.executeUpdate();
            System.out.println("Model:  " + model + "  Type:  " + type + "   Num: " + num);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static int getCreditCardBalance(long cardNumber) {
        int balance = 0;

        // Select the Balance from the CREDITCARD table based on Card Number
        String selectBalanceQuery = "SELECT Balance FROM CREDITCARD WHERE Card_Number = ?";

        try (PreparedStatement selectBalanceStatement = dbConnect.prepareStatement(selectBalanceQuery)) {
            selectBalanceStatement.setLong(1, cardNumber);

            try (ResultSet resultSet = selectBalanceStatement.executeQuery()) {
                if (resultSet.next()) {
                    balance = (int) resultSet.getFloat("Balance");
                } else {
                    balance = 10000;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return balance;
    }

    public static void updateBalance(long ccNum, int total) {
        String selectQuery = "SELECT Balance FROM CREDITCARD WHERE Card_Number = ?";

        try (PreparedStatement selectStatement = dbConnect.prepareStatement(selectQuery)) {
            selectStatement.setLong(1, ccNum);

            try (ResultSet resultSet = selectStatement.executeQuery()) {
                if (resultSet.next()) {
                    double currentBalance = resultSet.getDouble("Balance");

                    double newBalance = currentBalance - total;

                    // Update the balance in the database
                    String updateQuery = "UPDATE CREDITCARD SET Balance = ? WHERE Card_Number = ?";
                    try (PreparedStatement updateStatement = dbConnect.prepareStatement(updateQuery)) {
                        updateStatement.setDouble(1, newBalance);
                        updateStatement.setLong(2, ccNum);
                        updateStatement.executeUpdate();
                        System.out.println("Transaction successful. New balance: " + newBalance);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("This is a guest card. They have too much money!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getFlightNum(String model) {
        int num = 0;
        String query = "SELECT * FROM FLIGHT WHERE Model=?";

        try (PreparedStatement preparedStatement = dbConnect.prepareStatement(query)) {
            preparedStatement.setString(1, model);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    num = resultSet.getInt("Flight_Number");

                    System.out.println(num);

                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return num;
    }

    public static String getFlightDest(String model) {
        String dest = "";
        String query = "SELECT * FROM FLIGHT WHERE Model=?";

        try (PreparedStatement preparedStatement = dbConnect.prepareStatement(query)) {
            preparedStatement.setString(1, model);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    dest = resultSet.getString("Destination");

                    System.out.println(dest);

                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return dest;
    }

    public static String getFlightDate(String model) {
        String date = "";
        String query = "SELECT * FROM FLIGHT WHERE Model=?";

        try (PreparedStatement preparedStatement = dbConnect.prepareStatement(query)) {
            preparedStatement.setString(1, model);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    date = resultSet.getString("Departure");

                    System.out.println(date);

                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return date;
    }
}