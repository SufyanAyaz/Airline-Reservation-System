package Boundaries;

import Controller.Accounts.RegistrationController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class registrationView extends JFrame {

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField addressField;
    private JTextField postalCodeField;
    private JTextField creditCardField;
    private startingView startPage;
    private guestView guestPage;

    public registrationView(startingView start, guestView guest) {
        // Set the layout manager
        setLayout(new BorderLayout());

        // Create panels for different sections
        JPanel formPanel = new JPanel(new GridLayout(7, 2));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // Create form components
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameField = new JTextField();

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameField = new JTextField();

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        JLabel addressLabel = new JLabel("Address:");
        addressField = new JTextField();

        JLabel postalCodeLabel = new JLabel("Postal Code:");
        postalCodeField = new JTextField();

        JLabel creditCardLabel = new JLabel("Credit Card Number:");
        creditCardField = new JTextField();

        // Add components to the form panel
        formPanel.add(firstNameLabel);
        formPanel.add(firstNameField);
        formPanel.add(lastNameLabel);
        formPanel.add(lastNameField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(addressLabel);
        formPanel.add(addressField);
        formPanel.add(postalCodeLabel);
        formPanel.add(postalCodeField);
        formPanel.add(creditCardLabel);
        formPanel.add(creditCardField);

        // Create buttons
        JButton registerButton = createStyledButton("Register", Color.BLUE, Color.BLACK);
        JButton backButton = createStyledButton("Back", Color.RED, Color.WHITE);

        Dimension buttonSize = new Dimension(150, 40);
        registerButton.setPreferredSize(buttonSize);
        backButton.setPreferredSize(buttonSize);

        // Add buttons to the button panel
        buttonPanel.add(backButton);
        buttonPanel.add(registerButton);

        // Add panels to the frame
        add(createHeaderLabel("Registration Form"), BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listener to the Register button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validate fields and collect information
                if (validateFields()) {
                    String firstName = firstNameField.getText();
                    String lastName = lastNameField.getText();
                    String email = emailField.getText();
                    String password = new String(passwordField.getPassword());
                    String address = addressField.getText();
                    String postalCode = postalCodeField.getText();
                    String creditCard = creditCardField.getText();

                    // Process the collected information as needed
                    // (You may want to store it in variables or send it to another class/method)

                    // For now, display the collected information
                    // displayRegistrationInfo(firstName, lastName, email, password, address,
                    // postalCode, creditCard);

                    int resultCode = 0;
                    try {
                        RegistrationController rc = new RegistrationController();
                        resultCode = rc.registerUser(firstName, lastName, email, password, address, postalCode);
                    } catch (SQLException sqlE) {
                        resultCode = 3;
                    }
                    switch (resultCode) {
                        case 0:
                            displayRegistrationSuccess("Account created!");
                            break;
                        case 1:
                            displayRegistrationFailure("Password length must be in range [10,20].");
                            break;
                        case 2:
                            displayRegistrationFailure("Email can not be longer than 50 characters.");
                            break;
                        case 3:
                            displayRegistrationFailure(
                                    String.format("An account with email '%s' already exists.", email));
                            break;
                        default:
                            displayRegistrationFailure("An unknown error occurred.");
                    }
                }
            }
        });

        // Add action listener to the Back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (startPage != null && guestPage == null) {
                    startPage.setVisible(true);
                    setVisible(false);
                } else if (guestPage != null && startPage == null) {
                    guestPage.setVisible(true);
                    setVisible(false);
                }
            }
        });

        // Set frame properties
        setTitle("Registration Form");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(false);
        startPage = start;
        guestPage = guest;
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

    private boolean validateFields() {
        // Validate email format
        String email = emailField.getText();
        if (!email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(this, "Invalid email format!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate password
        String password = new String(passwordField.getPassword());
        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Password cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate credit card number length
        String creditCard = creditCardField.getText();
        if (creditCard.length() > 16) {
            JOptionPane.showMessageDialog(this, "Credit card number cannot be longer than 16 digits!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Other validations can be added based on your requirements

        return true;
    }

    private void displayRegistrationFailure(String message) {
        JOptionPane.showMessageDialog(this, message, "Registration Error.",
                JOptionPane.ERROR_MESSAGE);
    }

    private void displayRegistrationSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Registration Successful.",
                JOptionPane.INFORMATION_MESSAGE);
    }
}