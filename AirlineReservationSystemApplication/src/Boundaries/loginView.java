package Boundaries;

import Controller.Accounts.LoginSessionController;
import Controller.DatabaseController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class loginView extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;
    private startingView startPage;
    private registeredUserView register;
    private tourismAgentView tourist;
    private airlineAgentView airline;
    private flightAttendantView flight;
    private adminView admin;

    public loginView(startingView start) {
        // Set the layout manager
        setLayout(new BorderLayout());

        // Create panels for different sections
        JPanel formPanel = new JPanel(new GridLayout(2, 2));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Create form components
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        // Add components to the form panel
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        // Create buttons
        JButton loginButton = createStyledButton("Login", Color.BLUE, Color.BLACK);
        JButton backButton = createStyledButton("Back", Color.RED, Color.WHITE);

        Dimension buttonSize = new Dimension(150, 40);
        loginButton.setPreferredSize(buttonSize);
        backButton.setPreferredSize(buttonSize);

        // Add buttons to the button panel
        buttonPanel.add(loginButton);
        buttonPanel.add(backButton);

        // Add panels to the frame
        add(createHeaderLabel("Login"), BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listener to the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validate fields and collect information
                if (validateFields()) {
                    String email = emailField.getText();
                    String password = new String(passwordField.getPassword());

                    // TODO: Better error handling
                    int resultCode = 0;
                    try {
                        System.out.println("Gets Here");
                        LoginSessionController lc = new LoginSessionController();
                        System.out.println("Gets Here");
                        resultCode = lc.loginUser(email, password);
                        System.out.println(resultCode);
                        displayNext(resultCode, email);
                    } catch (SQLException sqlE) {
                        displayFailure("Could not connect to servers. Please try again later.");
                        sqlE.printStackTrace();
                        return;
                    }
                    // displayNext(lc.loginUser(email, password), email);
                }
            }
        });

        // Add action listener to the Back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startPage.setVisible(true);
                setVisible(false);
            }
        });

        // Set frame properties
        setTitle("Login Page");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(false);
        startPage = start;
        register = new registeredUserView(start);
        airline = new airlineAgentView(start);
        admin = new adminView(start);
        tourist = new tourismAgentView(start);
        flight = new flightAttendantView(start);
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
        return true;
    }

    private void displayLoginInfo(String email, String password) {
        // For now, display the collected information in a dialog
        StringBuilder info = new StringBuilder();
        info.append("Email: ").append(email).append("\n");
        info.append("Password: ").append(password).append("\n");

        JOptionPane.showMessageDialog(this, info.toString(), "Login Information",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void displayFailure(String message) {
        JOptionPane.showMessageDialog(this, message, "Unsuccessful", JOptionPane.ERROR_MESSAGE);
    }

    private void displaySuccess(String email) {
        String message = String.format("'%s' logged in!", email);
        JOptionPane.showMessageDialog(this, message, "Success!", JOptionPane.INFORMATION_MESSAGE);
    }

    /*
     * Depending on code received from LoginSessionController,
     * will
     */
    private void displayNext(int code, String email) {
        String fName = LoginSessionController.getSessionFName();
        switch (code) {
            case 0:
                displayFailure("Incorrect username or password.");
                break;
            case 1:
                if (fName == null) {
                    displayFailure("An unknown error occurred. Please try again later.");
                    return;
                }
                displaySuccess(email);
                register.setTitleName(fName);
                register.setVisible(true);
                setVisible(false);
                break;
            case 2:
                displaySuccess(email);
                airline.setVisible(true);
                setVisible(false);
                break;
            case 3:
                displaySuccess(email);
                admin.setVisible(true);
                setVisible(false);
                break;
            case 4:
                displaySuccess(email);
                flight.setVisible(true);
                setVisible(false);
                break;
            case 5:
                displaySuccess(email);
                tourist.setVisible(true);
                setVisible(false);
                break;
            default:
                displayFailure("An unknown error occurred. Please try again later.");
        }
    }
}