package Boundaries;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class adminAddView extends JFrame {

    private adminView adminPage;
    private adminAddFlightDestView addFlightDest;
    private adminAddFlightInfoView addFlightInfo;
    private adminAddAircraftView addAircraft;
    private adminAddCrewView addCrew;

    public adminAddView(adminView admin) {
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
        JButton addFlightDestinationButton = createStyledButton("Add Flight Destination");
        JButton addFlightInfoButton = createStyledButton("Add Flight Information");
        JButton addAircraftButton = createStyledButton("Add Aircraft");
        JButton addCrewButton = createStyledButton("Add Crew");
        JButton backButton = createStyledButton("Back");
        JButton exiButton = createStyledButton("Exit");

        Dimension buttonSize = new Dimension(150, 40);
        addFlightDestinationButton.setPreferredSize(buttonSize);
        addFlightInfoButton.setPreferredSize(buttonSize);
        addAircraftButton.setPreferredSize(buttonSize);
        addCrewButton.setPreferredSize(buttonSize);
        backButton.setPreferredSize(buttonSize);
        exiButton.setPreferredSize(buttonSize);

        exiButton.setBackground(Color.RED);
        exiButton.setForeground(Color.WHITE);

        // Add buttons and title to panels
        buttonPanel.add(addFlightDestinationButton);
        buttonPanel.add(addFlightInfoButton);
        buttonPanel.add(addAircraftButton);
        buttonPanel.add(addCrewButton);

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
        addFlightDestinationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addFlightDest.setVisible(true);
                setVisible(false);
            }
        });

        addFlightInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addFlightInfo.setVisible(true);
                setVisible(false);
            }
        });

        addAircraftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAircraft.setVisible(true);
                setVisible(false);
            }
        });

        addCrewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCrew.setVisible(true);
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
        setTitle("ENSF 480 - Group 03 - Flight Reservation System - Admin Add View");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(false);

        adminPage = admin;
        addFlightDest = new adminAddFlightDestView(this);
        addFlightInfo = new adminAddFlightInfoView(this);
        addAircraft = new adminAddAircraftView(this);
        addCrew = new adminAddCrewView(this);
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
