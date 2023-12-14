package Boundaries;

import javax.swing.*;

import Controller.adminController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class adminPrintView extends JFrame {

    private adminView adminPage;

    public adminPrintView(adminView admin) {
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
        JButton printButton = createStyledButton("Print List of Registered Users");
        JButton backButton = createStyledButton("Back");
        JButton exiButton = createStyledButton("Exit");

        Dimension buttonSize = new Dimension(150, 40);
        printButton.setPreferredSize(buttonSize);
        backButton.setPreferredSize(buttonSize);
        exiButton.setPreferredSize(buttonSize);

        exiButton.setBackground(Color.RED);
        exiButton.setForeground(Color.WHITE);

        // Add buttons and title to panels
        buttonPanel.add(printButton);

        bottomPanel.add(backButton);
        bottomPanel.add(exiButton);

        titlePanel.add(createTitleLabel("Print a List of Users Registered in our System"));

        // Add panels to the main panel
        mainPanel.add(Box.createVerticalStrut(20)); // Add space at the top
        mainPanel.add(titlePanel);
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalStrut(20)); // Add space at the bottom
        mainPanel.add(bottomPanel);

        // Add the main panel to the frame
        add(mainPanel);

        // Add action listener to the buttons
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    adminController ac = new adminController();
                    List<String> regUsers = ac.registeredUsers();

                    // Convert the ArrayList to a 2D array for the table
                    Object[][] data = new Object[regUsers.size()][1];
                    for (int i = 0; i < regUsers.size(); i++) {
                        data[i][0] = regUsers.get(i);
                    }

                    // Define column names for the table
                    String[] columnNames = { "User Information" };

                    // Create a JTable with the data and column names
                    JTable table = new JTable(data, columnNames);

                    // Create a scroll pane to contain the table
                    JScrollPane scrollPane = new JScrollPane(table);

                    // Display the table in a dialog
                    JOptionPane.showMessageDialog(adminPrintView.this, scrollPane, "Registered Users",
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
        setTitle("ENSF 480 - Group 03 - Flight Reservation System - Admin Print View");
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
