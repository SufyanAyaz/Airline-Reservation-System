package Boundaries;

import javax.swing.*;

import Controller.printController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class crewBrowseView extends JFrame {

    private flightAttendantView flight;
    private airlineAgentView airline;
    private JTextField planeModelField;

    public crewBrowseView(flightAttendantView f, airlineAgentView a) {
        flight = f;
        airline = a;
        // Set the layout manager
        setLayout(new BorderLayout());

        // Create panels for different sections
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        planeModelField = new JTextField();

        // Set the background color of the main panel to sky blue
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(135, 206, 250)); // Sky blue color

        // Create buttons and title
        JButton printButton = createStyledButton("Print List of Passengers");
        JButton backButton = createStyledButton("Back");
        JButton exiButton = createStyledButton("Exit");

        Dimension buttonSize = new Dimension(150, 40);
        printButton.setPreferredSize(buttonSize);
        backButton.setPreferredSize(buttonSize);
        exiButton.setPreferredSize(buttonSize);

        exiButton.setBackground(Color.RED);
        exiButton.setForeground(Color.WHITE);

        JPanel formPanel = new JPanel(new GridLayout(1, 2));
        formPanel.setBackground(new Color(135, 206, 250)); // Sky blue color

        formPanel.add(new JLabel("Plane Model:"));
        formPanel.add(planeModelField);

        // Add buttons and title to panels
        buttonPanel.add(printButton);

        bottomPanel.add(backButton);
        bottomPanel.add(exiButton);

        titlePanel.add(createTitleLabel("Print a List of Passengers in our Flights"));

        // Add panels to the main panel
        mainPanel.add(Box.createVerticalStrut(20)); // Add space at the top
        mainPanel.add(titlePanel);
        mainPanel.add(formPanel); // Add the form panel
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalStrut(20)); // Add space at the bottom
        mainPanel.add(bottomPanel);

        // Add the main panel to the frame
        add(mainPanel);

        try {
            printController.createConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQLException, show an error dialog, log, etc.
        }

        // Add action listener to the buttons
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String model = planeModelField.getText();
                try {
                    printController pc = new printController();
                    List<String> passengers = pc.getPassengers(model);

                    // Convert the ArrayList to a 2D array for the table
                    Object[][] data = new Object[passengers.size()][1];
                    for (int i = 0; i < passengers.size(); i++) {
                        data[i][0] = passengers.get(i);
                    }

                    // Define column names for the table
                    String[] columnNames = { "Passenger Information" };

                    // Create a JTable with the data and column names
                    JTable table = new JTable(data, columnNames);

                    // Create a scroll pane to contain the table
                    JScrollPane scrollPane = new JScrollPane(table);

                    // Display the table in a dialog
                    JOptionPane.showMessageDialog(crewBrowseView.this, scrollPane, "Passengers",
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
                if (flight != null && airline == null) {
                    flight.setVisible(true);
                    setVisible(false);
                } else if (flight == null && airline != null) {
                    airline.setVisible(true);
                    setVisible(false);
                }
            }
        });

        exiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Set frame properties
        setTitle("ENSF 480 - Group 03 - Flight Reservation System - Admin Print View");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(false);

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
