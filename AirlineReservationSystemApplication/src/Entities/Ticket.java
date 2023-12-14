package Entities;

/*
 * This class stores the information of a particular ticket
 */
public class Ticket {
    private int ticketNum;//Need ticket number for database
    private int flightNum;
    private Seat seatInfo;
    private UserInfo owner;
    public Ticket(int tNum, int fNum, Seat sInfo, UserInfo user) {//Changed from Flight to flightnumber to simplify datastructure
        ticketNum = tNum;
        flightNum = fNum;
        seatInfo = sInfo;
        owner = user;
    }
    //Getters
    public int getTickNumber() {return ticketNum;}
    public int getFlight() {return flightNum;}
    public Seat getSeat() {return seatInfo;}
    public UserInfo getUser() {return owner;}
}
