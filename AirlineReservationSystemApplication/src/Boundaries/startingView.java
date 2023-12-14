package Boundaries;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class startingView extends JFrame {

    private guestView guestPage;
    private registrationView registrationPage;
    private loginView loginPage;

    public startingView() {
        // Set the layout manager
        setLayout(new BorderLayout());

        // Create panels for different sections
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel linkPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // Set the background color of the main panel to sky blue
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(135, 206, 250)); // Sky blue color

        // Create buttons and link
        JButton loginButton = createStyledButton("Login", Color.BLUE);
        JButton signUpButton = createStyledButton("Sign Up", Color.GREEN);
        JButton exitButton = createStyledButton("Exit", Color.RED);
        JLabel guestLink = createGuestLink();

        // Increase the size of the buttons
        Dimension buttonSize = new Dimension(150, 40);
        loginButton.setPreferredSize(buttonSize);
        signUpButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);

        // Add buttons and link to panels
        buttonPanel.add(loginButton);
        buttonPanel.add(signUpButton);
        linkPanel.add(guestLink);
        exitPanel.add(exitButton);

        // Add panels to the frame
        centerPanel.add(Box.createVerticalGlue()); // Add glue to center vertically
        centerPanel.add(buttonPanel);
        centerPanel.add(linkPanel); // Add linkPanel to centerPanel
        centerPanel.add(Box.createVerticalGlue()); // Add glue to center vertically

        add(centerPanel, BorderLayout.CENTER);
        add(exitPanel, BorderLayout.SOUTH);

        // Add action listener to the exit button
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Terminate the application when the exit button is pressed
                System.exit(0);
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Terminate the application when the exit button is pressed
                registrationPage.setVisible(true);
                setVisible(false);
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Terminate the application when the exit button is pressed
                loginPage.setVisible(true);
                setVisible(false);
            }
        });

        // Set frame properties
        setTitle("ENSF 480 - Group 03 - Flight Reservation System");
        setSize(500, 300); // Increase the size of the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);

        loginPage = new loginView(this);
        guestPage = new guestView(this);
        registrationPage = new registrationView(this, null);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false); // Remove border
        return button;
    }

    private JLabel createGuestLink() {
        JLabel guestLink = new JLabel("Continue as Guest");
        guestLink.setForeground(Color.BLACK);
        guestLink.setFont(guestLink.getFont().deriveFont(Font.BOLD | Font.ITALIC));
        guestLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Add action listener to the link
        guestLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Handle guest link click action
                guestPage.setVisible(true);
                setVisible(false);
            }
        });

        return guestLink;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new startingView());
    }
}
