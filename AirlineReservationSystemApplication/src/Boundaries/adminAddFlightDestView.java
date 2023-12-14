package Boundaries;

import Controller.adminController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class adminAddFlightDestView extends JFrame {

    private JTextField flightModelField;
    private JTextField flightNumField;
    private JTextField destinationField;
    private adminAddView addPage;

    public adminAddFlightDestView(adminAddView addView) {
        // Set the layout manager
        setLayout(new BorderLayout());

        // Create panels for different sections
        JPanel formPanel = new JPanel(new GridLayout(2, 2));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Create form components
        JLabel flightModelLabel = new JLabel("Flight Model:");
        flightModelField = new JTextField();

        JLabel flightNumLabel = new JLabel("Flight Number:");
        flightNumField = new JTextField();

        JLabel destinationLabel = new JLabel("New Destination:");
        destinationField = new JTextField();

        // Add components to the form panel
        formPanel.add(flightModelLabel);
        formPanel.add(flightModelField);
        formPanel.add(flightNumLabel);
        formPanel.add(flightNumField);
        formPanel.add(destinationLabel);
        formPanel.add(destinationField);

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
        add(createHeaderLabel("Add Destination"), BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listener to the Register button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validate fields and collect information
                String flightNum = flightNumField.getText();
                String flightModel = flightModelField.getText();
                String dest = destinationField.getText();

                adminController ac = new adminController();
                ac.addDestination(flightNum, flightModel, dest);

                JOptionPane.showMessageDialog(adminAddFlightDestView.this, "Flight Destination Added successfully.",
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
        setTitle("Add New Destination Page");
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