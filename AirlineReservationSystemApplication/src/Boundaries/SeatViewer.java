package Boundaries;

// import java.util.ArrayList;
// import java.util.Vector;

import Controller.FlightInfoController;
// import Entities.Seat;
// import Entities.Flight;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeatViewer extends JFrame {
        private String model;
        private int totalTickets;
        private ArrayList<SeatButton> seatButtons;
        private List<SelectedSeatInfo> selectedSeatsInfo = new ArrayList<>();
        private MakePayment payer;

        public SeatViewer(String model, int total) {
                this.model = model;
                this.totalTickets = total;
                this.seatButtons = new ArrayList<>();

                setTitle("Seat Selection");
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setLayout(new GridLayout(0, 5)); // 5 columns for demonstration purposes

                try {
                        FlightInfoController.createConnection();
                } catch (SQLException e) {
                        e.printStackTrace();
                        // Handle the SQLException, show an error dialog, log, etc.
                }

                List<Integer> seatIDs = FlightInfoController.getSeatsIds(model);
                List<String> seatTypes = FlightInfoController.getSeatsType(model);
                List<Integer> seatNums = FlightInfoController.getSeatsNum(model);
                List<Integer> seatAvailabilitys = FlightInfoController.getSeatsAvailability(model);

                for (int i = 0; i < seatIDs.size(); i++) {
                        int j = seatIDs.get(i);
                        String k = seatTypes.get(i);
                        int l = seatNums.get(i);
                        int m = seatAvailabilitys.get(i);
                        seatButtons.add(new SeatButton(j, k, l, m));
                }

                for (SeatButton seatButton : seatButtons) {
                        // Add two columns of seats
                        add(seatButton);
                }

                JButton nextButton = new JButton("Next");
                nextButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                // Implement logic to handle the selected seats and move to the next page
                                handleNextButtonClick();
                        }
                });
                add(nextButton);

                pack();
                setLocationRelativeTo(null);
                setVisible(false);
        }

        private void handleNextButtonClick() {
                List<SeatButton> selectedSeats = getSelectedSeats();
                if (selectedSeats.size() == totalTickets) {
                        for (SeatButton seatButton : selectedSeats) {
                                SelectedSeatInfo seatInfo = new SelectedSeatInfo(seatButton.id, seatButton.seatType,
                                                seatButton.numOfSeats);
                                selectedSeatsInfo.add(seatInfo);
                                int seatId = seatButton.id;
                                String seatType = seatButton.seatType;
                                int seatNumber = seatButton.numOfSeats;
                        }

                        payer = new MakePayment(this, model, selectedSeatsInfo);
                        payer.setVisible(true);
                        setVisible(false);
                        selectedSeatsInfo.clear();

                        for (SelectedSeatInfo seatInfo : selectedSeatsInfo) {
                                System.out.println("Selected Seat: " + seatInfo);
                        }
                } else {
                        JOptionPane.showMessageDialog(this, "Please select exactly " + totalTickets + " seats.");
                }
        }

        private ArrayList<SeatButton> getSelectedSeats() {
                ArrayList<SeatButton> selectedSeats = new ArrayList<>();
                for (SeatButton seatButton : seatButtons) {
                        if (seatButton.isSelected()) {
                                selectedSeats.add(seatButton);
                        }
                }
                return selectedSeats;
        }

        private class SeatButton extends JButton {
                private int id;
                private String seatType;
                private int numOfSeats;
                private int availability;

                public SeatButton(int id, String type, int num, int avail) {
                        this.id = id;
                        this.seatType = type;
                        this.numOfSeats = num;
                        this.availability = avail;
                        setText(id + type + "-" + num);
                        setForeground(Color.BLACK);
                        setPreferredSize(new Dimension(80, 60));

                        updateButtonState();

                        addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                        if (avail == 1) {
                                                toggleSelection();
                                        }
                                }
                        });
                }

                private void checkSelectedSeats() {
                        // Check if the number of selected seats exceeds the totalTickets
                        int selectedSeatsCount = getSelectedSeats().size();
                        if (selectedSeatsCount > totalTickets) {
                                // Notify the user and reset the selection
                                JOptionPane.showMessageDialog(SeatViewer.this,
                                                "You can only select up to " + totalTickets + " seats.");
                                setSelected(false);
                                updateButtonState();
                        }
                }

                public void toggleSelection() {
                        // If the total selected seats are less than or equal to totalTickets, proceed
                        // with the selection
                        if (getSelectedSeats().size() < totalTickets) {
                                setSelected(!isSelected());
                                updateButtonState();
                        } else if (isSelected()) {
                                // If the user tries to select more than totalTickets, show a pop-up and reset
                                // the selection
                                JOptionPane.showMessageDialog(SeatViewer.this,
                                                "You can only select up to " + totalTickets + " seats.");
                                setSelected(false);
                                updateButtonState();
                        }
                }

                public void updateButtonState() {
                        if (isSelected()) {
                                setBackground(Color.BLUE);
                        } else if (availability == 1) {
                                setBackground(Color.GREEN);
                        } else {
                                setBackground(Color.RED);
                        }
                }
        }

        public static class SelectedSeatInfo {
                private int id;
                private String seatType;
                private int numOfSeats;

                public SelectedSeatInfo(int id, String seatType, int numOfSeats) {
                        this.id = id;
                        this.seatType = seatType;
                        this.numOfSeats = numOfSeats;
                }

                public int getId() {
                        return id;
                }

                public String getSeatType() {
                        return seatType;
                }

                public int getNumOfSeats() {
                        return numOfSeats;
                }

                @Override
                public String toString() {
                        return "Seat ID: " + id + ", Type: " + seatType + ", Number: " + numOfSeats;
                }
        }
}