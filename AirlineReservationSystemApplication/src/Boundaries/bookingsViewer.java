package Boundaries;

import Controller.printController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class bookingsViewer extends JFrame {

    private registeredUserView registered;
    private tourismAgentView tourism;
    private airlineAgentView airlineAgent;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField modelField;

    public bookingsViewer(registeredUserView rUser, tourismAgentView tAgent, airlineAgentView aAgent) {
        // Set the layout manager
        setLayout(new BorderLayout());

        // Create panels for different sections
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // Set the background color of the main panel to sky blue
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(135, 206, 250)); // Sky blue color

        // Create buttons and title
        JButton printButton = createStyledButton("Print List of Bookings");
        JButton backButton = createStyledButton("Back");
        JButton exiButton = createStyledButton("Exit");

        Dimension buttonSize = new Dimension(150, 40);
        printButton.setPreferredSize(buttonSize);
        backButton.setPreferredSize(buttonSize);
        exiButton.setPreferredSize(buttonSize);

        exiButton.setBackground(Color.RED);
        exiButton.setForeground(Color.WHITE);

        // Create text fields for user input
        firstNameField = new JTextField();
        lastNameField = new JTextField();
        emailField = new JTextField();
        modelField = new JTextField();

        // Create form panel
        JPanel formPanel = new JPanel(new GridLayout(3, 2));
        formPanel.setBackground(new Color(135, 206, 250)); // Sky blue color

        formPanel.add(new JLabel("First Name:"));
        formPanel.add(firstNameField);
        formPanel.add(new JLabel("Last Name:"));
        formPanel.add(lastNameField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Plane Model:"));
        formPanel.add(modelField);

        // Add buttons and title to panels
        buttonPanel.add(printButton);

        bottomPanel.add(backButton);
        bottomPanel.add(exiButton);

        titlePanel.add(createTitleLabel("Print a List of Users Bookings in our System"));

        // Add panels to the main panel
        mainPanel.add(Box.createVerticalStrut(20)); // Add space at the top
        mainPanel.add(titlePanel);
        mainPanel.add(formPanel); // Add the form panel
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalStrut(20)); // Add space at the bottom
        mainPanel.add(bottomPanel);

        // Add the main panel to the frame
        add(mainPanel);

        // Add action listener to the buttons
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String email = emailField.getText();
                String model = modelField.getText();
                if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || model.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                } else {
                    // Perform the action with the entered data
                    // For now, display a message indicating the button click and entered data
                    try {
                        printController pc = new printController();
                        List<String> bookings = pc.getBookings(model, firstName, lastName, email);

                        Object[][] data = new Object[bookings.size()][1];
                        for (int i = 0; i < bookings.size(); i++) {
                            data[i][0] = bookings.get(i);
                        }

                        // Define column names for the table
                        String[] columnNames = { "Booking Information" };

                        // Create a JTable with the data and column names
                        JTable table = new JTable(data, columnNames);

                        // Create a scroll pane to contain the table
                        JScrollPane scrollPane = new JScrollPane(table);

                        // Display the table in a dialog
                        JOptionPane.showMessageDialog(bookingsViewer.this, scrollPane, "User Bookings",
                                JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        // Handle the SQLException, show an error dialog, log, etc.
                    }
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (registered != null && tourism == null && airlineAgent == null) {
                    registered.setVisible(true);
                    setVisible(false);
                } else if (registered == null && tourism != null && airlineAgent == null) {
                    tourism.setVisible(true);
                    setVisible(false);
                } else if (registered == null && tourism == null && airlineAgent != null) {
                    airlineAgent.setVisible(true);
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
        setTitle("ENSF 480 - Group 03 - Flight Reservation System - Users Booking Page");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(false);

        registered = rUser;
        tourism = tAgent;
        airlineAgent = aAgent;
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
