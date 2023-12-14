package Boundaries;

import Controller.FlightInfoController;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class cancelationView extends JFrame {

    private JTextField flightNum;
    private JTextField seatNum;
    private JTextField seatType;
    private JTextField ccNum;
    private JTextField cost;
    private guestView guestPage;
    private registeredUserView registeredUserPage;
    private tourismAgentView tourismAgent;
    private airlineAgentView airlineAgent;

    public cancelationView(guestView guest, registeredUserView rUser, tourismAgentView tAgent,
            airlineAgentView aAgent) {
        // Set the layout manager
        setLayout(new BorderLayout());

        try {
            FlightInfoController.createConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQLException, show an error dialog, log, etc.
        }

        // Create panels for different sections
        JPanel formPanel = new JPanel(new GridLayout(7, 2));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // Create form components
        JLabel flightNumLabel = new JLabel("Flight Model:");
        flightNum = new JTextField();

        JLabel seatNumLabel = new JLabel("Seat Number:");
        seatNum = new JTextField();

        JLabel seatTypeLabel = new JLabel("Seat Type:");
        seatType = new JTextField();

        JLabel costLabel = new JLabel("How much did it cost:");
        cost = new JTextField();

        JLabel ccNumLabel = new JLabel("Card Number:");
        ccNum = new JTextField();

        // Add components to the form panel
        formPanel.add(flightNumLabel);
        formPanel.add(flightNum);
        formPanel.add(seatNumLabel);
        formPanel.add(seatNum);
        formPanel.add(seatTypeLabel);
        formPanel.add(seatType);
        formPanel.add(costLabel);
        formPanel.add(cost);
        formPanel.add(ccNumLabel);
        formPanel.add(ccNum);

        // Create buttons
        JButton cancellationButton = createStyledButton("Cancel Booking", Color.RED, Color.WHITE);
        JButton backButton = createStyledButton("Back", Color.RED, Color.WHITE);

        Dimension buttonSize = new Dimension(150, 40);
        cancellationButton.setPreferredSize(buttonSize);
        backButton.setPreferredSize(buttonSize);

        // Add buttons to the button panel
        buttonPanel.add(backButton);
        buttonPanel.add(cancellationButton);

        // Add panels to the frame
        add(createHeaderLabel("Cancellation Form"), BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listener to the Register button
        cancellationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String model = flightNum.getText();
                int sNum = Integer.parseInt(seatNum.getText());
                String sType = seatType.getText();
                long card = Long.parseLong(ccNum.getText());
                int c = Integer.parseInt(cost.getText());

                int check = FlightInfoController.cancelBooking(model, sType, sNum, card, c);
                if (check == 1) {
                    // Booking canceled successfully
                    JOptionPane.showMessageDialog(null, "Flight canceled!");
                    // Update availability after cancellation
                    FlightInfoController.changeAvailability(model, sType, sNum);
                } else if (check == 0) {
                    // No booking found for the specified parameters
                    JOptionPane.showMessageDialog(null, "Flight does not exist.");
                }
            }
        });

        // Add action listener to the Back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (guestPage != null && registeredUserPage == null && tourismAgent == null && airlineAgent == null) {
                    guestPage.setVisible(true);
                    setVisible(false);
                } else if (registeredUserPage != null && guestPage == null && tourismAgent == null
                        && airlineAgent == null) {
                    registeredUserPage.setVisible(true);
                    setVisible(false);
                } else if (tourismAgent != null && guestPage == null && registeredUserPage == null
                        && airlineAgent == null) {
                    tourismAgent.setVisible(true);
                    setVisible(false);
                } else if (airlineAgent != null && guestPage == null && registeredUserPage == null
                        && tourismAgent == null) {
                    airlineAgent.setVisible(true);
                    setVisible(false);
                }
            }
        });

        // Set frame properties
        setTitle("Cancellation Form");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(false);
        guestPage = guest;
        registeredUserPage = rUser;
        tourismAgent = tAgent;
        airlineAgent = aAgent;
    }

    private JButton createStyledButton(String text, Color bgColor, Color textColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(textColor);
        return button;
    }

    private JLabel createHeaderLabel(String text) {
        JLabel headerLabel = new JLabel(text);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        return headerLabel;
    }
}