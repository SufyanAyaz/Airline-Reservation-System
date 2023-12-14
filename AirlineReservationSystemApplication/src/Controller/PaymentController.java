package Controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Controller.Accounts.EmailController;
import Entities.*;

public class PaymentController {
    // Member Variables
    private float totalPrice = 0;
    private String uEmail;
    private String ufName;
    private String ulName;
    private int flightNum;
    private String flightDest;
    private String date;
    private List<Integer> seatNumbers;
    private List<String> seatTypes;

    private ArrayList<Ticket> allTickets;
    private ArrayList<Flight> allFlights;

    private ArrayList<Ticket> sessionUserTickets = new ArrayList<Ticket>();
    private ArrayList<Flight> sessionUserFlights = new ArrayList<Flight>();

    private Booking booking;

    private SessionUser sessionUser = SessionUser.getInstance();
    private UserInfo user = sessionUser.getSessionUser();

    // Constructor
    public PaymentController(int tPrice, String uEmail, String fName, String lName, int fNum, String fDest, String date,
            List<Integer> sNums, List<String> sTypes) throws SQLException {
        this.totalPrice = tPrice;
        this.uEmail = uEmail;
        this.ufName = fName;
        this.ulName = lName;
        this.flightNum = fNum;
        this.flightDest = fDest;
        this.date = date;
        this.seatNumbers = sNums;
        this.seatTypes = sTypes;

        populateTickets();
        populateFlights();
    }

    /*
     * This method sorts the tickets to grab only the tickets that the sessionUser
     * has booked
     */
    public void sortTickets() {

        for (Ticket t : allTickets) {
            UserInfo ticketOwner = t.getUser();

            if (ticketOwner == user) {
                sessionUserTickets.add(t);
                sortFlights(t);
            }
        }

        booking = new Booking(sessionUser, sessionUserTickets);
    }

    /*
     * This method sorts the flights to grab only the flights that the sessionUser
     * has a booked ticket on
     */
    public void sortFlights(Ticket t) {
        int ticketFlightNum = t.getFlight();

        for (Flight f : allFlights) {
            if (ticketFlightNum == f.getFlightNum()) {
                sessionUserFlights.add(f);
            }
        }
    }

    /*
     * This method creates a reciept and sends it to the user
     */
    public void createAndSendReciept() {
        // Create paymentSummary
        Payment paymentSummary = new Payment(booking, this);

        // Create userReciept
        Reciept userReciept = new Reciept(sessionUser, paymentSummary);

        // Make email contents
        String emailContents = userReciept.formatReciept(totalPrice);

        // Send out email
        EmailController eController = new EmailController(uEmail);
        eController.sendEmail("Booking recipt of confirmation", emailContents);
    }

    /*
     * This method grabs all of the tickets from the DB
     */
    private void populateTickets() throws SQLException {
        DatabaseController.connect();
        allTickets = DatabaseController.getTickets();
        DatabaseController.terminate();
    }

    /*
     * This method grabs all of the Flights from the DB
     */
    private void populateFlights() throws SQLException {
        DatabaseController.connect();
        allFlights = DatabaseController.getFlights();
        DatabaseController.terminate();
    }

    public float getTotalPrice() {
        return this.totalPrice;
    }

    public String getUserEmail() {
        return this.uEmail;
    }

    public String getUserFName() {
        return this.ufName;
    }

    public String getUserLName() {
        return this.ulName;
    }

    public int getFlightNum() {
        return this.flightNum;
    }

    public String getFlightDest() {
        return this.flightDest;
    }

    public String getFlightDate() {
        return this.date;
    }

    public List<Integer> getSeatNumbers() {
        return this.seatNumbers;
    }

    public List<String> getSeatTypes() {
        return this.seatTypes;
    }
}
