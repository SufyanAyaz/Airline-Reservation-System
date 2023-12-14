package Boundaries;

import javax.swing.*;

import Controller.adminController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class adminDeleteCrewView extends JFrame {

    private JTextField idField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private adminDeleteView deletePage;

    public adminDeleteCrewView(adminDeleteView deleteView) {
        // Set the layout manager
        setLayout(new BorderLayout());

        // Create panels for different sections
        JPanel formPanel = new JPanel(new GridLayout(2, 2));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Create form components
        JLabel idLabel = new JLabel("Id:");
        idField = new JTextField();

        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameField = new JTextField();

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameField = new JTextField();

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();

        // Add components to the form panel
        formPanel.add(idLabel);
        formPanel.add(idField);
        formPanel.add(firstNameLabel);
        formPanel.add(firstNameField);
        formPanel.add(lastNameLabel);
        formPanel.add(lastNameField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);

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
        add(createHeaderLabel("Delete Crew"), BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listener to the Register button
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validate fields and collect information
                if (validateFields()) {
                    String crewID = idField.getText();
                    String firstName = firstNameField.getText();
                    String lastName = lastNameField.getText();
                    String email = emailField.getText();

                    adminController ac = new adminController();
                    ac.deleteCrewMember(crewID, firstName, lastName, email);

                    JOptionPane.showMessageDialog(adminDeleteCrewView.this, "Crew Member Deleted successfully.",
                            "Delete Successful",
                            JOptionPane.INFORMATION_MESSAGE);
                }
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
        setTitle("Delete Old Crew Page");
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

    private boolean validateFields() {
        // Validate email format
        String email = emailField.getText();
        if (!email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(this, "Invalid email format!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}