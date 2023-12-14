package Controller.Accounts;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailController {
    private String to;
    private String from = "airline49293379@gmail.com";
    private String password = "dowfgevqxvlktgvr";

    /*
     * Generates email sender to given address
     */
    public EmailController(String to) {
        this.to = to;
    }

    /*
     * This class changes the reciever and sends the email
     */
    public boolean sendEmail(String to, String head, String msg) {
        this.to = to;
        return sendEmail(head, msg);
    }

    /*
     * Sends emails from hardcoded address.
     * This is just a new gmail account that I made for this project
     * returns true if the email was sent, false if it failed
     */
    public boolean sendEmail(String head, String msg) {
        // Set the properties for the SMTP server
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Create a Session object with authentication
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(head);
            message.setText(msg);

            Transport.send(message);
            return true;
        } catch (MessagingException mex) {
            return false;
        }
    }
}