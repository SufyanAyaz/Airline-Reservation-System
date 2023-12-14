package Controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

import Entities.*;
import Entities.Date;

/*
 * This class handles connecting to the MySQL database
 */
public class DatabaseController {
    private static Connection connection;

    /*
     * Must connect to initalize connection
     */
    public static void connect() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/testairline?useSSL=false&serverTimezone=UTC", "root",
                "MySQL_Root26");
    }

    public static void terminate() throws SQLException {
        if (connection != null)
            connection.close();
    }

    /* GETTERS */
    /*
     * Returns an arraylist of aircraft
     */
    public static ArrayList<Aircraft> getAircraft() throws SQLException {
        ArrayList<Aircraft> result = new ArrayList<Aircraft>();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM AIRCRAFT";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            // Retrieve data here
            String manu = resultSet.getString("Manufacturer");
            String model = resultSet.getString("Model");
            int oCap = resultSet.getInt("Ordinary_Capacity");
            int cCap = resultSet.getInt("Comfort_Capacity");
            int bCap = resultSet.getInt("Business_Capacity");
            result.add(new Aircraft(manu, model, oCap, cCap, bCap));
        }
        resultSet.close();
        statement.close();

        return result;
    }

    /*
     * Returns an arraylist of locations
     */
    public static ArrayList<Location> getLocations() throws SQLException {
        ArrayList<Location> result = new ArrayList<Location>();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM AIRPORT_LOCATION";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            // Retrieve data here
            String name = resultSet.getString("Airport_Name");
            String code = resultSet.getString("Airport_Code");
            result.add(new Location(name, code));
        }
        resultSet.close();
        statement.close();

        return result;
    }

    /*
     * Returns an arraylist of login info
     */
    public static ArrayList<Login> getLogins() throws SQLException {
        ArrayList<Login> result = new ArrayList<Login>();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM LOGIN_INFO";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            // Retrieve data here
            String email = resultSet.getString("Email");
            String pass = resultSet.getString("Passkey");
            String salt = resultSet.getString("DynSalt");
            String role = resultSet.getString("Login_Role");
            result.add(new Login(email, pass, salt, role));
        }
        resultSet.close();
        statement.close();

        return result;
    }

    /*
     * Returns an arraylist of agent info
     */
    public static ArrayList<Agent> getAgents() throws SQLException {
        ArrayList<Agent> result = new ArrayList<Agent>();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM AGENTS";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            // Retrieve data here
            String fName = resultSet.getString("First_Name");
            String LName = resultSet.getString("Last_Name");
            String email = resultSet.getString("Email");
            int id = resultSet.getInt("ID");
            String company = resultSet.getString("Company_Name");
            query = "SELECT * FROM LOGIN_INFO WHERE Email = ?";
            PreparedStatement LogStatement = connection.prepareStatement(query);
            LogStatement.setString(1, email);
            ResultSet LogResult = LogStatement.executeQuery();
            Login log = new Login(email, LogResult.getString("Passkey"), LogResult.getString("DynSalt"), "AGENT");
            if (company == null) {
                result.add(new Agent(fName, LName, log, id));
            } else {
                result.add(new Agent(fName, LName, log, id, company));
            }
        }
        resultSet.close();
        statement.close();

        return result;
    }

    /*
     * Returns an arraylist of crew
     */
    public static ArrayList<CrewMember> getCrew() throws SQLException {
        ArrayList<CrewMember> result = new ArrayList<CrewMember>();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM CREW_MEMBER";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            // Retrieve data here
            String fName = resultSet.getString("First_Name");
            String LName = resultSet.getString("Last_Name");
            String Email = resultSet.getString("Email");
            query = "SELECT * FROM LOGIN_INFO WHERE Email = ?";
            PreparedStatement LogStatement = connection.prepareStatement(query);
            LogStatement.setString(1, Email);
            ResultSet LogResult = LogStatement.executeQuery();
            Login log = new Login(Email, LogResult.getString("Passkey"), LogResult.getString("DynSalt"), "CREW");
            result.add(new CrewMember(fName, LName, log));
        }
        resultSet.close();
        statement.close();

        return result;
    }

    /*
     * Returns an arraylist of registered users
     */
    public static ArrayList<UserInfo> getRegUsers() throws SQLException {
        ArrayList<UserInfo> result = new ArrayList<UserInfo>();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM REGISTERED_USER";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            // Retrieve data here
            String fName = resultSet.getString("First_Name");
            String LName = resultSet.getString("Last_Name");
            String Email = resultSet.getString("Email");
            String Address = resultSet.getString("User_Address");
            String pCode = resultSet.getString("Postal_Code");
            long cCardnum = resultSet.getLong("CreditCard");
            query = "SELECT * FROM LOGIN_INFO WHERE Email = ?";
            PreparedStatement LogStatement = connection.prepareStatement(query);
            LogStatement.setString(1, Email);
            ResultSet LogResult = LogStatement.executeQuery();
            LogResult.next();
            Login log = new Login(Email, LogResult.getString("Passkey"), LogResult.getString("DynSalt"), "USER");
            query = "SELECT * FROM CREDITCARD WHERE Card_Number = ?";
            PreparedStatement CCStatement = connection.prepareStatement(query);
            CCStatement.setLong(1, cCardnum);
            ResultSet CCResult = CCStatement.executeQuery();
            CreditCard cc;
            if (CCResult.next()) {
                cc = new CreditCard(cCardnum, CCResult.getInt("Expiry"), CCResult.getInt("CVV"),
                        CCResult.getFloat("Balance"));
            } else {
                cc = null;
            }
            result.add(new UserInfo(fName, LName, Email, Address, pCode, log, cc));
        }
        resultSet.close();
        statement.close();

        return result;
    }

    /*
     * Returns an arraylist of tickets
     */
    public static ArrayList<Ticket> getTickets() throws SQLException {
        ArrayList<Ticket> result = new ArrayList<Ticket>();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM TICKET";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            // Retrieve data here
            int tNum = resultSet.getInt("Ticket_Num");
            int fNum = resultSet.getInt("Flight_Num");
            String sRow = resultSet.getString("Seat_Row");
            int sNum = resultSet.getInt("Seat_Num");
            String sType = resultSet.getString("Seat_Type");
            String owner = resultSet.getString("Customer");
            query = "SELECT * FROM REGISTERED_USER WHERE Email = ?";
            PreparedStatement UStatement = connection.prepareStatement(query);
            UStatement.setString(1, owner);
            ResultSet UResult = UStatement.executeQuery();
            UserInfo u;
            if (UResult.next()) {
                u = new UserInfo(UResult.getString("First_Name"), UResult.getString("Last_Name"), owner);
            } else {
                u = new UserInfo(null, null, owner);
            }

            result.add(new Ticket(tNum, fNum, new Seat(sRow, sNum, sType), u));
        }
        resultSet.close();
        statement.close();

        return result;
    }

    /*
     * Returns an arraylist of flight
     */
    public static ArrayList<Flight> getFlights() throws SQLException {
        ArrayList<Location> Loc = getLocations();
        ArrayList<Aircraft> AirC = getAircraft();
        ArrayList<Ticket> Tick = getTickets();
        ArrayList<Flight> result = new ArrayList<Flight>();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM FLIGHT";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            // Retrieve data here
            int flightNum = resultSet.getInt("Flight_Number");
            String model = resultSet.getString("Model");
            String orig = resultSet.getString("Origin");
            String dest = resultSet.getString("Destination");
            String dep = resultSet.getString("Departure");

            // Picking aircraft from arraylist
            Aircraft aircraft = null;
            for (int i = 0; i < AirC.size(); i++) {
                if (AirC.get(i).getModel().equals(model)) {
                    aircraft = AirC.get(i);
                    break;
                }
            }
            // Picking Locations from arraylist
            Location o = null;
            Location d = null;
            for (int i = 0; i < Loc.size(); i++) {
                if (Loc.get(i).getAirportCode().equals(orig)) {
                    o = Loc.get(i);
                }
                if (Loc.get(i).getAirportCode().equals(dest)) {
                    d = Loc.get(i);
                }
                if (o != null && d != null)
                    break;
            }

            Flight flight = new Flight(flightNum, aircraft, o, d, new Date(dep));

            // Getting seat list from tickets
            for (int i = 0; i < Tick.size(); i++) {
                if (Tick.get(i).getFlight() == flightNum)
                    flight.addSeat(Tick.get(i).getSeat());
            }
            result.add(flight);
        }
        resultSet.close();
        statement.close();

        return result;
    }

    /* SETTERS */
    /*
     * Updates aircraft table with inputted arraylist
     */
    public static void setAircraft(ArrayList<Aircraft> aircrafts) throws SQLException {
        PreparedStatement insertStatement = null;
        for (int i = 0; i < aircrafts.size(); i++) {
            Aircraft aCraft = aircrafts.get(i);
            try {
                String insertStr = "INSERT INTO AIRCRAFT (Manufacturer, Model, Ordinary_Capacity, Comfort_Capacity, Business_Capacity) VALUES (?, ?, ?, ?, ?)";
                insertStatement = connection.prepareStatement(insertStr);
                insertStatement.setString(1, aCraft.getManufacturer());
                insertStatement.setString(2, aCraft.getModel());
                insertStatement.setInt(3, aCraft.getOrdinaryCapacity());
                insertStatement.setInt(4, aCraft.getComfortCapacity());
                insertStatement.setInt(5, aCraft.getBuisnessCapacity());
                System.out.println("Executing query1: " + insertStatement.toString());
                insertStatement.executeUpdate();
            } catch (SQLException e) {

            }
        }
        if (insertStatement != null) {
            insertStatement.close();
        }
    }

    /*
     * Updates locations table with inputted arraylist
     */
    public static void setLocations(ArrayList<Location> locations) throws SQLException {
        PreparedStatement insertStatement = null;
        for (int i = 0; i < locations.size(); i++) {
            Location loc = locations.get(i);
            try {
                String insertStr = "INSERT INTO AIRPORT_LOCATION (Airport_Name, Airport_Code) VALUES (?, ?)";
                insertStatement = connection.prepareStatement(insertStr);
                insertStatement.setString(1, loc.getName());
                insertStatement.setString(2, loc.getAirportCode());
                insertStatement.executeUpdate();
            } catch (SQLException e) {

            }
        }
        if (insertStatement != null) {
            insertStatement.close();
        }
    }

    /*
     * Updates Agents table with inputted arraylist
     */
    public static void setAgents(ArrayList<Agent> agents) throws SQLException {
        PreparedStatement insertStatement = null;
        for (int i = 0; i < agents.size(); i++) {
            Agent agen = agents.get(i);
            try {
                String role = "AGENT";
                String insertStr = "INSERT INTO LOGIN_INFO (Email, Passkey, DynSalt, Login_Role) VALUES (?, ?, ?, ?)";
                insertStatement = connection.prepareStatement(insertStr);
                insertStatement.setString(1, agen.getLogin().getEmail());
                insertStatement.setString(2, agen.getLogin().getPassword());
                insertStatement.setString(3, agen.getLogin().getSalt());
                insertStatement.setString(4, role);
                insertStatement.executeUpdate();
                insertStr = "INSERT INTO AGENTS (First_Name, Last_Name, Email, ID, Company_Name) VALUES (?, ?, ?, ?, ?)";
                insertStatement = connection.prepareStatement(insertStr);
                insertStatement.setString(1, agen.getFirstName());
                insertStatement.setString(2, agen.getLastName());
                insertStatement.setString(3, agen.getLogin().getEmail());
                insertStatement.setInt(4, agen.getID());
                insertStatement.setString(5, agen.getCompany());
                insertStatement.executeUpdate();
            } catch (SQLException e) {

            }
        }
        if (insertStatement != null) {
            insertStatement.close();
        }
    }

    /*
     * Updates crew member table with inputted arraylist
     */
    public static void setCrew(ArrayList<CrewMember> crew) throws SQLException {
        PreparedStatement insertStatement = null;
        for (int i = 0; i < crew.size(); i++) {
            CrewMember crewMem = crew.get(i);
            try {
                Random random = new Random();
                int randNum = random.nextInt();
                String role = "CREW";
                String insertStr = "INSERT INTO LOGIN_INFO (Email, Passkey, DynSalt, Login_Role) VALUES (?, ?, ?, ?)";
                insertStatement = connection.prepareStatement(insertStr);
                insertStatement.setString(1, crewMem.getLogin().getEmail());
                insertStatement.setString(2, crewMem.getLogin().getPassword());
                insertStatement.setString(3, crewMem.getLogin().getSalt());
                insertStatement.setString(4, role);
                insertStatement.executeUpdate();
                insertStr = "INSERT INTO CREW_MEMBER (ID, First_Name, Last_Name, Email) VALUES (?, ?, ?)";
                insertStatement = connection.prepareStatement(insertStr);
                insertStatement.setInt(1, randNum);
                insertStatement.setString(2, crewMem.getFirstName());
                insertStatement.setString(3, crewMem.getLastName());
                insertStatement.setString(4, crewMem.getLogin().getEmail());
                insertStatement.executeUpdate();
            } catch (SQLException e) {

            }
        }
        if (insertStatement != null) {
            insertStatement.close();
        }
    }

    /*
     * Updates registered user table with inputted arraylist
     */
    public static void setUsers(ArrayList<UserInfo> rUsers) throws SQLException {
        PreparedStatement insertStatement = null;
        for (int i = 0; i < rUsers.size(); i++) {
            UserInfo rUser = rUsers.get(i);
            try {
                String role = "USER";
                String insertStr = "INSERT INTO LOGIN_INFO (Email, Passkey, DynSalt, Login_Role) VALUES (?, ?, ?, ?)";
                insertStatement = connection.prepareStatement(insertStr);
                insertStatement.setString(1, rUser.getEmail());
                insertStatement.setString(2, rUser.getLogin().getPassword());
                insertStatement.setString(3, rUser.getLogin().getSalt());
                insertStatement.setString(4, role);
                insertStatement.executeUpdate();
                insertStr = "INSERT INTO REGISTERED_USER (First_Name, Last_Name, Email, User_Address, Postal_Code, CreditCard)"
                        + " VALUES (?, ?, ?, ?, ?, ?)";
                insertStatement = connection.prepareStatement(insertStr);
                insertStatement.setString(1, rUser.getFirstName());
                insertStatement.setString(2, rUser.getLastName());
                insertStatement.setString(3, rUser.getEmail());
                insertStatement.setString(4, rUser.getAddress());
                insertStatement.setString(5, rUser.getPostalCode());
                if (rUser.getCreditCard() != null) {
                    insertStatement.setLong(6, rUser.getCreditCard().getCardNumber());
                } else {
                    insertStatement.setNull(6, Types.INTEGER);
                }
                insertStatement.executeUpdate();
            } catch (SQLException e) {
                throw e;
            }
        }
        if (insertStatement != null) {
            insertStatement.close();
        }
    }

    /*
     * Updates flight table with inputted arraylist
     */
    public static void setFlights(ArrayList<Flight> flights) throws SQLException {
        PreparedStatement insertStatement = null;
        for (int i = 0; i < flights.size(); i++) {
            Flight flight = flights.get(i);
            try {
                String insertStr = "INSERT INTO FLIGHT (Flight_Number, Model, Origin, Destination, Departure," +
                        "Crew_Member_1, Crew_Member_2, Crew_Member_3, Crew_Member_4, Crew_Member_5, Crew_Member_6, Crew_Member_7, Crew_Member_8)"
                        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                insertStatement = connection.prepareStatement(insertStr);
                insertStatement.setInt(1, flight.getFlightNum());
                insertStatement.setString(2, flight.getAircraft().getModel());
                insertStatement.setString(3, flight.getOrigin().getAirportCode());
                insertStatement.setString(4, flight.getDestination().getAirportCode());
                insertStatement.setString(5, flight.getDeparture().getString());
                /*
                 * for (int j=6; j<14; j++) {
                 * if (j-6 < flight.getCrewMembers().size()) {
                 * insertStatement.setString(j,
                 * flight.getCrewMembers().get(j-6).getLogin().getEmail());
                 * } else {
                 * insertStatement.setString(j, null);
                 * }
                 * }
                 */
                insertStatement.executeUpdate();

                // Update Locations aswell
                ArrayList<Location> locations = new ArrayList<Location>();
                locations.add(flight.getOrigin());
                locations.add(flight.getDestination());
                setLocations(locations);
            } catch (SQLException e) {

            }
        }
        if (insertStatement != null) {
            insertStatement.close();
        }
    }

    /*
     * Updates ticket table with inputted arraylist
     */
    public static void setTickets(ArrayList<Ticket> tickets) throws SQLException {
        PreparedStatement insertStatement = null;
        for (int i = 0; i < tickets.size(); i++) {
            Ticket ticket = tickets.get(i);
            try {
                String insertStr = "INSERT INTO TICKET (Ticket_Num, Flight_Num, Seat_Row, Seat_Num, Seat_Type, Customer)"
                        +
                        " VALUES (?, ?, ?, ?, ?, ?)";
                insertStatement = connection.prepareStatement(insertStr);
                insertStatement.setInt(1, ticket.getTickNumber());
                insertStatement.setInt(2, ticket.getFlight());
                insertStatement.setString(3, ticket.getSeat().getRow());
                insertStatement.setInt(4, ticket.getSeat().getSeatNum());
                insertStatement.setString(5, ticket.getSeat().getType());
                insertStatement.setString(6, ticket.getUser().getEmail());
                insertStatement.executeUpdate();
            } catch (SQLException e) {

            }
        }
        if (insertStatement != null) {
            insertStatement.close();
        }
    }

    /* DELETE */
    /*
     * Deletes from aircraft table with inputted arraylist
     */
    public static void delAircraft(ArrayList<Aircraft> aircrafts) throws SQLException {
        PreparedStatement deleteStatement = null;
        for (int i = 0; i < aircrafts.size(); i++) {
            Aircraft aCraft = aircrafts.get(i);
            String deleteStr = "DELETE FROM AIRCRAFT WHERE Model = ?";
            deleteStatement = connection.prepareStatement(deleteStr);
            deleteStatement.setString(1, aCraft.getModel());
            deleteStatement.executeUpdate();
        }
        if (deleteStatement != null) {
            deleteStatement.close();
        }
    }

    /*
     * Deletes from locations table with inputted arraylist
     */
    public static void delLocations(ArrayList<Location> locations) throws SQLException {
        PreparedStatement deleteStatement = null;
        for (int i = 0; i < locations.size(); i++) {
            Location loc = locations.get(i);
            String deleteStr = "DELETE FROM AIRPORT_LOCATION WHERE Airport_Code = ?";
            deleteStatement = connection.prepareStatement(deleteStr);
            deleteStatement.setString(1, loc.getAirportCode());
            deleteStatement.executeUpdate();
        }
        if (deleteStatement != null) {
            deleteStatement.close();
        }
    }

    /*
     * Deletes from Agents table with inputted arraylist
     */
    public static void delAgents(ArrayList<Agent> agents) throws SQLException {
        PreparedStatement deleteStatement = null;
        for (int i = 0; i < agents.size(); i++) {
            Agent agen = agents.get(i);
            String deleteStr = "DELETE FROM LOGIN_INFO WHERE Email = ?";
            deleteStatement = connection.prepareStatement(deleteStr);
            deleteStatement.setString(1, agen.getLogin().getEmail());
            deleteStatement.executeUpdate();
            deleteStr = "DELETE FROM AGENTS WHERE ID = ?";
            deleteStatement = connection.prepareStatement(deleteStr);
            deleteStatement.setInt(1, agen.getID());
            deleteStatement.executeUpdate();
        }
        if (deleteStatement != null) {
            deleteStatement.close();
        }
    }

    /*
     * Deletes from crew member table with inputted arraylist
     */
    public static void delCrew(ArrayList<CrewMember> crew) throws SQLException {
        PreparedStatement deleteStatement = null;
        for (int i = 0; i < crew.size(); i++) {
            CrewMember crewMem = crew.get(i);
            String deleteStr = "DELETE FROM LOGIN_INFO WHERE Email = ?";
            deleteStatement = connection.prepareStatement(deleteStr);
            deleteStatement.setString(1, crewMem.getLogin().getEmail());
            deleteStatement.executeUpdate();
            deleteStr = "DELETE FROM CREW_MEMBER WHERE Email = ?";
            deleteStatement = connection.prepareStatement(deleteStr);
            deleteStatement.setString(1, crewMem.getLogin().getEmail());
            deleteStatement.executeUpdate();
        }
        if (deleteStatement != null) {
            deleteStatement.close();
        }
    }

    /*
     * Deletes from registered user table with inputted arraylist
     */
    public static void delUsers(ArrayList<UserInfo> rUsers) throws SQLException {
        PreparedStatement deleteStatement = null;
        for (int i = 0; i < rUsers.size(); i++) {
            UserInfo rUser = rUsers.get(i);
            String deleteStr = "DELETE FROM LOGIN_INFO WHERE Email = ?";
            deleteStatement = connection.prepareStatement(deleteStr);
            deleteStatement.setString(1, rUser.getLogin().getEmail());
            deleteStatement.executeUpdate();
            if (rUser.getCreditCard() != null) {
                deleteStr = "DELETE FROM CREDITCARD WHERE Card_Number = ?";
                deleteStatement = connection.prepareStatement(deleteStr);
                deleteStatement.setLong(1, rUser.getCreditCard().getCardNumber());
                deleteStatement.executeUpdate();
            }
            deleteStr = "DELETE FROM REGISTERED_USER WHERE Email = ?";
            deleteStatement = connection.prepareStatement(deleteStr);
            deleteStatement.setString(1, rUser.getLogin().getEmail());
            deleteStatement.executeUpdate();
        }
        if (deleteStatement != null) {
            deleteStatement.close();
        }
    }

    /*
     * Deletes from flight table with inputted arraylist
     */
    public static void delFlights(ArrayList<Flight> flights) throws SQLException {
        PreparedStatement deleteStatement = null;
        for (int i = 0; i < flights.size(); i++) {
            Flight flight = flights.get(i);
            String deleteStr = "DELETE FROM TICKET WHERE Flight_Num = ?";
            deleteStatement = connection.prepareStatement(deleteStr);
            deleteStatement.setInt(1, flight.getFlightNum());
            deleteStatement.executeUpdate();
            deleteStr = "DELETE FROM FLIGHT WHERE Flight_Number = ?";
            deleteStatement = connection.prepareStatement(deleteStr);
            deleteStatement.setInt(1, flight.getFlightNum());
            deleteStatement.executeUpdate();
        }
        if (deleteStatement != null) {
            deleteStatement.close();
        }
    }

    /*
     * Deletes from ticket table with inputted arraylist
     */
    public static void delTickets(ArrayList<Ticket> tickets) throws SQLException {
        PreparedStatement deleteStatement = null;
        for (int i = 0; i < tickets.size(); i++) {
            Ticket ticket = tickets.get(i);
            String deleteStr = "DELETE FROM TICKET WHERE Ticket_Num = ?";
            deleteStatement = connection.prepareStatement(deleteStr);
            deleteStatement.setInt(1, ticket.getTickNumber());
            deleteStatement.executeUpdate();
        }
        if (deleteStatement != null) {
            deleteStatement.close();
        }
    }

    public String getRole(String email, String pass) {
        String role = "";
        try {

            Statement myStmt = connection.createStatement();
            ResultSet results = myStmt
                    .executeQuery("SELECT * FROM AIRCRAFT WHERE Email=" + email + " AND Passkey=" + pass);

            while (results.next()) {
                role = results.getString("Login_Role");
            }
            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return role.toUpperCase();
    }
}
