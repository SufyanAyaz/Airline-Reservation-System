package Entities;
import java.util.ArrayList;

/*
 * This class stores flight information
 */
public class Flight {
    private int flightNum;
    private Aircraft aircraft;
    private Location origin;
    private Location destination;
    private Date departure;
    private ArrayList<Seat> seats;
    public Flight(int fNum, Aircraft airC, Location orig, Location dest, Date depart) {
        flightNum = fNum;
        aircraft = airC;
        origin = orig;
        destination = dest;
        departure = depart;
        seats = new ArrayList<Seat>();
    }
    public void addSeat(Seat s) {seats.add(s);}
    //Getters
    public int getFlightNum() {return flightNum;}
    public Aircraft getAircraft() {return aircraft;}
    public Location getOrigin() {return origin;}
    public Location getDestination() {return destination;}
    public Date getDeparture() {return departure;}
    public ArrayList<Seat> getSeats() {return seats;}
}
