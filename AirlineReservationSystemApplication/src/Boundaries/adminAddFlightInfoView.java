package Boundaries;

import javax.swing.*;

import Controller.adminController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class adminAddFlightInfoView extends JFrame {

    private JTextField flightNumField;
    private JTextField attributeField;
    private JTextField newInfoField;
    private adminAddView addPage;

    public adminAddFlightInfoView(adminAddView addView) {
        // Set the layout manager
        setLayout(new BorderLayout());

        // Create panels for different sections
        JPanel formPanel = new JPanel(new GridLayout(2, 2));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Create form components
        JLabel flightNumLabel = new JLabel("Flight Number:");
        flightNumField = new JTextField();

        JLabel attributeLabel = new JLabel("Name of Attribute to Add:");
        attributeField = new JTextField();

        JLabel newInfoLabel = new JLabel("New Information:");
        newInfoField = new JTextField();

        // Add components to the form panel
        formPanel.add(flightNumLabel);
        formPanel.add(flightNumField);
        formPanel.add(attributeLabel);
        formPanel.add(attributeField);
        formPanel.add(newInfoLabel);
        formPanel.add(newInfoField);

        // Create buttons
        JButton addButton = createStyledButton("Add", Color.BLUE, Color.BLACK);
        JButton backButton = createStyledButton("Back", Color.RED, Color.WHITE);

        Dimension buttonSize = new Dimension(150, 40);
        addButton.setPreferredSize(buttonSize);
        backButton.setPreferredSize(buttonSize);

        // Add buttons to the button panel
        buttonPanel.add(addButton);
        buttonPanel.add(backButton);

        // Add panels to the frame
        add(createHeaderLabel("Add Information"), BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listener to the Register button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validate fields and collect information
                String flightNumber = flightNumField.getText();
                String attribute = attributeField.getText();
                String info = newInfoField.getText();

                adminController ac = new adminController();
                ac.addFlightInfo(flightNumber, attribute, info);

                JOptionPane.showMessageDialog(adminAddFlightInfoView.this, "Flight Info Added successfully.",
                        "Update Successful",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Add action listener to the Back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPage.setVisible(true);
                setVisible(false);
            }
        });

        // Set frame properties
        setTitle("Add New Information Page");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(false);
        addPage = addView;
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