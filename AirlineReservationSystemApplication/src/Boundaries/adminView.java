package Boundaries;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class adminView extends JFrame {

    private startingView startPage;
    private adminBrowseView adminBrowse;
    private adminAddView adminAdd;
    private adminDeleteView adminDelete;
    private adminModifyView adminModify;
    private adminPrintView adminPrint;

    public adminView(startingView start) {
        // Set the layout manager
        setLayout(new BorderLayout());

        // Create panels for different sections
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // Set the background color of the main panel to sky blue
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(135, 206, 250)); // Sky blue color

        // Create buttons and title
        JButton browseButton = createStyledButton("Browse");
        JButton addButton = createStyledButton("Make Additions");
        JButton deleteButton = createStyledButton("Make Deletions");
        JButton modifyButton = createStyledButton("Make Modifications");
        JButton printUserButton = createStyledButton("Print Registered Users");
        JButton logOutButton = createStyledButton("Log Out");
        JButton exiButton = createStyledButton("Exit");

        Dimension buttonSize = new Dimension(150, 40);
        browseButton.setPreferredSize(buttonSize);
        addButton.setPreferredSize(buttonSize);
        deleteButton.setPreferredSize(buttonSize);
        modifyButton.setPreferredSize(buttonSize);
        printUserButton.setPreferredSize(buttonSize);
        logOutButton.setPreferredSize(buttonSize);
        exiButton.setPreferredSize(buttonSize);

        exiButton.setBackground(Color.RED);
        exiButton.setForeground(Color.WHITE);

        // Add buttons and title to panels
        buttonPanel.add(browseButton);
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(printUserButton);

        bottomPanel.add(logOutButton);
        bottomPanel.add(exiButton);

        titlePanel.add(createTitleLabel("Welcome to Group 3's Flight Reservation System"));

        // Add panels to the main panel
        mainPanel.add(Box.createVerticalStrut(20)); // Add space at the top
        mainPanel.add(buttonPanel);
        mainPanel.add(titlePanel);
        mainPanel.add(Box.createVerticalStrut(20)); // Add space at the bottom
        mainPanel.add(bottomPanel);

        // Add the main panel to the frame
        add(mainPanel);

        // Add action listener to the buttons
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminBrowse.setVisible(true);
                setVisible(false);
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminAdd.setVisible(true);
                setVisible(false);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminDelete.setVisible(true);
                setVisible(false);
            }
        });

        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminModify.setVisible(true);
                setVisible(false);
            }
        });

        printUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminPrint.setVisible(true);
                setVisible(false);
            }
        });

        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startPage.setVisible(true);
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
        setTitle("ENSF 480 - Group 03 - Flight Reservation System - Admin View");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(false);

        startPage = start;
        adminBrowse = new adminBrowseView(this);
        adminAdd = new adminAddView(this);
        adminDelete = new adminDeleteView(this);
        adminModify = new adminModifyView(this);
        adminPrint = new adminPrintView(this);

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
