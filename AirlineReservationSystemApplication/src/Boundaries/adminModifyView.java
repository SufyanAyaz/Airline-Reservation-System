package Boundaries;

import Controller.adminController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class adminModifyView extends JFrame {

    private JTextField flightNumberField;
    private JTextField originField;
    private JTextField destinationField;
    private JTextField departureField;
    private JTextField modelField;
    private adminView adminPage;

    public adminModifyView(adminView admin) {
        // Set the layout manager
        setLayout(new BorderLayout());

        // Create panels for different sections
        JPanel formPanel = new JPanel(new GridLayout(7, 2));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // Create form components
        JLabel flightNumberLabel = new JLabel("Flight Number:");
        flightNumberField = new JTextField();

        JLabel originLabel = new JLabel("Origin:");
        originField = new JTextField();

        JLabel destinationLabel = new JLabel("Destination:");
        destinationField = new JTextField();

        JLabel departureLabel = new JLabel("Departure:");
        departureField = new JTextField();

        JLabel modelLabel = new JLabel("Model:");
        modelField = new JTextField();

        // Add components to the form panel
        formPanel.add(flightNumberLabel);
        formPanel.add(flightNumberField);
        formPanel.add(originLabel);
        formPanel.add(originField);
        formPanel.add(destinationLabel);
        formPanel.add(destinationField);
        formPanel.add(departureLabel);
        formPanel.add(departureField);
        formPanel.add(modelLabel);
        formPanel.add(modelField);

        // Create buttons
        JButton modifyButton = createStyledButton("Modify", Color.BLUE, Color.BLACK);
        JButton backButton = createStyledButton("Back", Color.RED, Color.WHITE);

        Dimension buttonSize = new Dimension(150, 40);
        modifyButton.setPreferredSize(buttonSize);
        backButton.setPreferredSize(buttonSize);

        // Add buttons to the button panel
        buttonPanel.add(backButton);
        buttonPanel.add(modifyButton);

        // Add panels to the frame
        add(createHeaderLabel("Enter Modified Flight Details for Flight to Modify"), BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listener to the Register button
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validate fields and collect information

                String flightNum = flightNumberField.getText();
                String origin = originField.getText();
                String dest = destinationField.getText();
                String departure = departureField.getText();
                String model = modelField.getText();

                adminController ac = new adminController();
                ac.updateFlightInfo(flightNum, model, origin, dest, departure);

                JOptionPane.showMessageDialog(adminModifyView.this, "Flight information updated successfully.",
                        "Update Successful",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Add action listener to the Back button
        backButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                adminPage.setVisible(true);
                setVisible(false);
            }

        });

        // Set frame properties
        setTitle("Admin Modification Form");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(false);
        adminPage = admin;
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