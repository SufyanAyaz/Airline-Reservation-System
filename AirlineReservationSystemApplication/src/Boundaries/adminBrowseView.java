package Boundaries;

import Controller.adminController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class adminBrowseView extends JFrame {

    private adminView adminPage;

    public adminBrowseView(adminView admin) {
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
        JButton browseFlightButton = createStyledButton("Browse List of Flights");
        JButton browseCrewButton = createStyledButton("Browse Crews in Flights");
        JButton browseAircraftButton = createStyledButton("Browse List of Aircrafts");
        JButton backButton = createStyledButton("Back");
        JButton exiButton = createStyledButton("Exit");

        Dimension buttonSize = new Dimension(150, 40);
        browseFlightButton.setPreferredSize(buttonSize);
        browseCrewButton.setPreferredSize(buttonSize);
        browseAircraftButton.setPreferredSize(buttonSize);
        backButton.setPreferredSize(buttonSize);
        exiButton.setPreferredSize(buttonSize);

        exiButton.setBackground(Color.RED);
        exiButton.setForeground(Color.WHITE);

        // Add buttons and title to panels
        buttonPanel.add(browseFlightButton);
        buttonPanel.add(browseAircraftButton);
        buttonPanel.add(browseCrewButton);

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

        try {
            adminController.createConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQLException, show an error dialog, log, etc.
        }

        // Add action listener to the buttons
        browseFlightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // adminController ac = new adminController();
                    List<String> allFlights = adminController.getAllFlights();

                    Object[][] data = new Object[allFlights.size()][1];
                    for (int i = 0; i < allFlights.size(); i++) {
                        data[i][0] = allFlights.get(i);
                    }

                    // Define column names for the table
                    String[] columnNames = { "Flight Information" };

                    // Create a JTable with the data and column names
                    JTable table = new JTable(data, columnNames);

                    // Create a scroll pane to contain the table
                    JScrollPane scrollPane = new JScrollPane(table);

                    // Display the table in a dialog
                    JOptionPane.showMessageDialog(adminBrowseView.this, scrollPane, "Flights",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    // Handle the SQLException, show an error dialog, log, etc.
                }
            }
        });

        browseAircraftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    adminController ac = new adminController();
                    List<String> allAircraft = ac.getAllAircraft();

                    Object[][] data = new Object[allAircraft.size()][1];
                    for (int i = 0; i < allAircraft.size(); i++) {
                        data[i][0] = allAircraft.get(i);
                    }

                    // Define column names for the table
                    String[] columnNames = { "Aircraft Information" };

                    // Create a JTable with the data and column names
                    JTable table = new JTable(data, columnNames);

                    // Create a scroll pane to contain the table
                    JScrollPane scrollPane = new JScrollPane(table);

                    // Display the table in a dialog
                    JOptionPane.showMessageDialog(adminBrowseView.this, scrollPane, "Aircrafts",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    // Handle the SQLException, show an error dialog, log, etc.
                }
            }
        });

        browseCrewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    adminController ac = new adminController();
                    List<String> allCrew = ac.getAllCrewMembers();

                    Object[][] data = new Object[allCrew.size()][1];
                    for (int i = 0; i < allCrew.size(); i++) {
                        data[i][0] = allCrew.get(i);
                    }

                    // Define column names for the table
                    String[] columnNames = { "Crew Information" };

                    // Create a JTable with the data and column names
                    JTable table = new JTable(data, columnNames);

                    // Create a scroll pane to contain the table
                    JScrollPane scrollPane = new JScrollPane(table);

                    // Display the table in a dialog
                    JOptionPane.showMessageDialog(adminBrowseView.this, scrollPane, "Crew",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    // Handle the SQLException, show an error dialog, log, etc.
                }
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
        setTitle("ENSF 480 - Group 03 - Flight Reservation System - Admin Browse View");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(false);

        adminPage = admin;
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
