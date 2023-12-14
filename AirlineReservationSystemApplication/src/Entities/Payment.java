package Entities;

import java.util.ArrayList;
import Controller.PaymentController;

/*
 * This class stores payment information
 */
public class Payment {
    // Member variables
    private Booking booking;
    private PaymentController pController;

    // Constructor
    public Payment(Booking booking, PaymentController pC) {
        this.booking = booking;
        this.pController = pC;
    }

    // Getters
    public Booking getBooking() {
        return booking;
    }

    public PaymentController getPaymentController() {
        return this.pController;
    }

}
