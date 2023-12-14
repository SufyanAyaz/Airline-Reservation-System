package Boundaries;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class guestView extends JFrame {

    private registrationView registrationPage;
    private startingView startPage;
    private FlightViewer flight;
    private cancelationView cancelPage;

    public guestView(startingView start) {
        // Set the layout manager
        setLayout(new BorderLayout());

        // Create panels for different sections
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // Set the background color of the main panel to sky blue
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(135, 206, 250)); // Sky blue color

        // Create buttons and title
        JButton bookButton = createStyledButton("Book a Flight");
        JButton cancelButton = createStyledButton("Cancel a Booking");
        JButton signUpButton = createStyledButton("Sign-Up");
        JButton backButton = createStyledButton("Back");
        JButton exiButton = createStyledButton("Exit");

        Dimension buttonSize = new Dimension(150, 40);
        bookButton.setPreferredSize(buttonSize);
        cancelButton.setPreferredSize(buttonSize);
        signUpButton.setPreferredSize(buttonSize);
        backButton.setPreferredSize(buttonSize);
        exiButton.setPreferredSize(buttonSize);

        exiButton.setBackground(Color.RED);
        exiButton.setForeground(Color.WHITE);

        // Add buttons and title to panels
        buttonPanel.add(bookButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(signUpButton);

        bottomPanel.add(backButton);
        bottomPanel.add(exiButton);

        titlePanel.add(createTitleLabel("Welcome Guest, to Group 3's Flight Reservation System"));

        // Add panels to the main panel
        mainPanel.add(Box.createVerticalStrut(20)); // Add space at the top
        mainPanel.add(buttonPanel);
        mainPanel.add(titlePanel);
        mainPanel.add(Box.createVerticalStrut(20)); // Add space at the bottom
        mainPanel.add(bottomPanel);

        // Add the main panel to the frame
        add(mainPanel);

        // Add action listener to the buttons
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flight.setVisible(true);
                setVisible(false);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelPage.setVisible(true);
                setVisible(false);
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrationPage.setVisible(true);
                setVisible(false);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startPage.setVisible(true);
                setVisible(false);
            }
        });

        exiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Set frame properties
        setTitle("ENSF 480 - Group 03 - Flight Reservation System - Guest View");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(false);

        startPage = start;
        flight = new FlightViewer(this, null, null, null);
        registrationPage = new registrationView(null, this);
        cancelPage = new cancelationView(this, null, null, null);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(135, 206, 250)); // Sky blue color
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK)); // Thin black border underneath
        button.setFocusPainted(false); // Remove border
        return button;
    }

    private JLabel createTitleLabel(String text) {
        JLabel titleLabel = new JLabel(text);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        return titleLabel;
    }
}
