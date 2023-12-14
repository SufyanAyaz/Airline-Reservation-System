package Entities;

import Controller.*;

import java.util.ArrayList;
import java.util.List;

/*
 * This class stores recipt information
 */
public class Reciept {
    // Member variables
    private SessionUser userInfo;
    private Payment payment;

    public Reciept(SessionUser uInfo, Payment payment) {
        userInfo = uInfo;
        this.payment = payment;
    }

    // Getters
    public SessionUser getUser() {
        return userInfo;
    }

    public Payment getPayment() {
        return payment;
    }

    /*
     * This method returns a formatted reciept to email to the customer
     */
    public String formatReciept(float totalPrice) {
        PaymentController genPC = this.payment.getPaymentController();

        List<Integer> seatNumbersList = genPC.getSeatNumbers();
        List<String> seatTypesList = genPC.getSeatTypes();

        // Email body
        String emailBody = String.format(
                "These tickets are for %s %s.\n\nTicket Details:\n\n",
                genPC.getUserFName(), genPC.getUserLName());

        // Add each ticket
        for (int i = 0; i < seatNumbersList.size(); i++) {
            emailBody = emailBody + String.format(
                    "Destination: \t\t%s\nDeparture Date: \t%s\nFlight Number:  \t%d\nSeat Number: \t\t%s\nClass: \t\t\t%s\n\n",
                    genPC.getFlightDest(), genPC.getFlightDate(), genPC.getFlightNum(), seatNumbersList.get(i),
                    seatTypesList.get(i));
        }

        emailBody = emailBody
                + String.format("Total: $%.2f\n\nThank you for choosing to fly with us! We hope to see you again soon!",
                        totalPrice);

        return emailBody;
    }
}
