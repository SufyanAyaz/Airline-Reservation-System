package Boundaries;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class adminDeleteView extends JFrame {

    private adminView adminPage;
    private adminDeleteFlightDestView deleteFlightDest;
    private adminDeleteFlightInfoView deleteFlightInfo;
    private adminDeleteAircraftView deleteAircraft;
    private adminDeleteCrewView deleteCrew;

    public adminDeleteView(adminView admin) {
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
        JButton deleteFlightDestinationButton = createStyledButton("Delete Flight Destination");
        JButton deleteFlightInfoButton = createStyledButton("Delete Flight Information");
        JButton deleteAircraftButton = createStyledButton("Delete Aircraft");
        JButton deleteCrewButton = createStyledButton("Delete Crew");
        JButton backButton = createStyledButton("Back");
        JButton exiButton = createStyledButton("Exit");

        Dimension buttonSize = new Dimension(150, 40);
        deleteFlightDestinationButton.setPreferredSize(buttonSize);
        deleteFlightInfoButton.setPreferredSize(buttonSize);
        deleteAircraftButton.setPreferredSize(buttonSize);
        deleteCrewButton.setPreferredSize(buttonSize);
        backButton.setPreferredSize(buttonSize);
        exiButton.setPreferredSize(buttonSize);

        exiButton.setBackground(Color.RED);
        exiButton.setForeground(Color.WHITE);

        // Add buttons and title to panels
        buttonPanel.add(deleteFlightDestinationButton);
        buttonPanel.add(deleteFlightInfoButton);
        buttonPanel.add(deleteAircraftButton);
        buttonPanel.add(deleteCrewButton);

        bottomPanel.add(backButton);
        bottomPanel.add(exiButton);

        titlePanel.add(createTitleLabel("Browse Flight Reservation System Details"));

        // Add panels to the main panel
        mainPanel.add(Box.createVerticalStrut(20)); // Add space at the top
        mainPanel.add(buttonPanel);
        mainPanel.add(titlePanel);
        mainPanel.add(Box.createVerticalStrut(20)); // Add space at the bottom
        mainPanel.add(bottomPanel);

        // Add the main panel to the frame
        add(mainPanel);

        // Add action listener to the buttons
        deleteFlightDestinationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteFlightDest.setVisible(true);
                setVisible(false);
            }
        });

        deleteFlightInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteFlightInfo.setVisible(true);
                setVisible(false);
            }
        });

        deleteAircraftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteAircraft.setVisible(true);
                setVisible(false);
            }
        });

        deleteCrewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCrew.setVisible(true);
                setVisible(false);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminPage.setVisible(true);
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
        setTitle("ENSF 480 - Group 03 - Flight Reservation System - Admin Delete View");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(false);

        adminPage = admin;
        deleteFlightDest = new adminDeleteFlightDestView(this);
        deleteFlightInfo = new adminDeleteFlightInfoView(this);
        deleteAircraft = new adminDeleteAircraftView(this);
        deleteCrew = new adminDeleteCrewView(this);
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
