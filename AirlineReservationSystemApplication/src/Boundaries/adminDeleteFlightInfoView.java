package Boundaries;

import javax.swing.*;

import Controller.adminController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class adminDeleteFlightInfoView extends JFrame {

    private JTextField flightNumField;
    private JTextField attributeField;
    private adminDeleteView deletePage;

    public adminDeleteFlightInfoView(adminDeleteView deleteView) {
        // Set the layout manager
        setLayout(new BorderLayout());

        // Create panels for different sections
        JPanel formPanel = new JPanel(new GridLayout(2, 2));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Create form components
        JLabel flightNumLabel = new JLabel("Flight Number:");
        flightNumField = new JTextField();

        JLabel attributeLabel = new JLabel("Name of Attribute to Delete:");
        attributeField = new JTextField();

        // Add components to the form panel
        formPanel.add(flightNumLabel);
        formPanel.add(flightNumField);
        formPanel.add(attributeLabel);
        formPanel.add(attributeField);

        // Create buttons
        JButton deleteButton = createStyledButton("Delete", Color.BLUE, Color.BLACK);
        JButton backButton = createStyledButton("Back", Color.RED, Color.WHITE);

        Dimension buttonSize = new Dimension(150, 40);
        deleteButton.setPreferredSize(buttonSize);
        backButton.setPreferredSize(buttonSize);

        // Add buttons to the button panel
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        // Add panels to the frame
        add(createHeaderLabel("Delete Information"), BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listener to the Register button
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validate fields and collect information
                String flightNumber = flightNumField.getText();
                String attribute = attributeField.getText();

                adminController ac = new adminController();
                ac.removeFlightAttribute(flightNumber, attribute);

                JOptionPane.showMessageDialog(adminDeleteFlightInfoView.this,
                        "Flight Information Deleted successfully.",
                        "Delete Successful",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Add action listener to the Back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePage.setVisible(true);
                setVisible(false);
            }
        });

        // Set frame properties
        setTitle("Delete Old Information Page");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(false);
        deletePage = deleteView;
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