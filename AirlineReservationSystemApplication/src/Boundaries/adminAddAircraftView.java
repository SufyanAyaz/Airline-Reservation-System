package Boundaries;

import Controller.adminController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class adminAddAircraftView extends JFrame {

    private JTextField modelField;
    private JTextField manufacturerField;
    private JTextField oCapacityField;
    private JTextField cCapacityField;
    private JTextField bCapacityField;
    private adminAddView addPage;

    public adminAddAircraftView(adminAddView addView) {
        // Set the layout manager
        setLayout(new BorderLayout());

        // Create panels for different sections
        JPanel formPanel = new JPanel(new GridLayout(2, 2));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Create form components
        JLabel modelLabel = new JLabel("Aircraft Model:");
        modelField = new JTextField();

        JLabel manufacturerLabel = new JLabel("Manufacturer:");
        manufacturerField = new JTextField();

        JLabel oCapacityLabel = new JLabel("Ordinary Seat Capacity:");
        oCapacityField = new JTextField();

        JLabel cCapacityLabel = new JLabel("Comfort Seat Capacity:");
        cCapacityField = new JTextField();

        JLabel bCapacityLabel = new JLabel("Business Seat Capacity:");
        bCapacityField = new JTextField();

        // Add components to the form panel
        formPanel.add(modelLabel);
        formPanel.add(modelField);
        formPanel.add(manufacturerLabel);
        formPanel.add(manufacturerField);
        formPanel.add(oCapacityLabel);
        formPanel.add(oCapacityField);
        formPanel.add(cCapacityLabel);
        formPanel.add(cCapacityField);
        formPanel.add(bCapacityLabel);
        formPanel.add(bCapacityField);

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
        add(createHeaderLabel("Add Aircraft"), BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listener to the Register button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validate fields and collect information
                String model = modelField.getText();
                String manufacturer = manufacturerField.getText();
                String ordinary = oCapacityField.getText();
                String comfort = cCapacityField.getText();
                String business = bCapacityField.getText();

                adminController ac = new adminController();
                ac.addAircraft(manufacturer, model, ordinary, comfort, business);

                JOptionPane.showMessageDialog(adminAddAircraftView.this, "Aircraft Added successfully.",
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
        setTitle("Add New Aircraft Page");
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