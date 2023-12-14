package Boundaries;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class airlineAgentView extends JFrame {

    private startingView startPage;
    private FlightViewer flight;
    private bookingsViewer bookings;
    private cancelationView cancelPage;
    private crewBrowseView passengers;

    public airlineAgentView(startingView start) {
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
        JButton bookingsButton = createStyledButton("View Your Booking");
        JButton cancelButton = createStyledButton("Cancel a Booking");
        JButton browseButton = createStyledButton("Browse Passenger List");
        JButton logOutButton = createStyledButton("Log Out");
        JButton exiButton = createStyledButton("Exit");

        Dimension buttonSize = new Dimension(150, 40);
        bookButton.setPreferredSize(buttonSize);
        bookingsButton.setPreferredSize(buttonSize);
        cancelButton.setPreferredSize(buttonSize);
        browseButton.setPreferredSize(buttonSize);
        logOutButton.setPreferredSize(buttonSize);
        exiButton.setPreferredSize(buttonSize);

        exiButton.setBackground(Color.RED);
        exiButton.setForeground(Color.WHITE);

        // Add buttons and title to panels
        buttonPanel.add(bookButton);
        buttonPanel.add(bookingsButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(browseButton);

        bottomPanel.add(logOutButton);
        bottomPanel.add(exiButton);

        titlePanel.add(createTitleLabel("Welcome to Group 3's Flight Reservation System"));

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

        bookingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookings.setVisible(true);
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

        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passengers.setVisible(true);
                setVisible(false);
            }
        });

        logOutButton.addActionListener(new ActionListener() {
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
        setTitle("ENSF 480 - Group 03 - Flight Reservation System - Airline Agent View");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(false);

        startPage = start;
        flight = new FlightViewer(null, null, null, this);
        bookings = new bookingsViewer(null, null, this);
        cancelPage = new cancelationView(null, null, null, this);
        passengers = new crewBrowseView(null, this);
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
