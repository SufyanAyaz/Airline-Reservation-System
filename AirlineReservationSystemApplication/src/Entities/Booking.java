package Entities;

import java.util.ArrayList;

public class Booking {
    // Member variables
    private SessionUser user;
    private ArrayList<Ticket> tickets;

    // Constructor
    public Booking(SessionUser user, ArrayList<Ticket> tickets) {
        this.user = user;
        this.tickets = tickets;
    }
    //Getters
    public SessionUser getUser() {return user;}
    public ArrayList<Ticket> getTickets() {return tickets;}
}
