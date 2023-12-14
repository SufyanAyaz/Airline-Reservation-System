package Entities;

/*
 * This class stores seat information
 */
public class Seat {
    private enum SeatType {//Enum for type of seat
        Ordinary, Comfort, Business
    }

    private static float basePrice = (float) 25.00;
    private String row;
    private int seatNum;
    private float price;
    private SeatType type;
    private boolean avail;

    public Seat(String row, int num, String type) {
        this.row = row;
        this.seatNum = num;
        this.avail = true;
        if (type.equals("Comfort")) {
            this.type = SeatType.Comfort;
        } else if (type.equals("Business")) {
            this.type = SeatType.Business;
        } else {
            this.type = SeatType.Ordinary;
        }
        updatePrice();
    }

    private void updatePrice() {
        if (type.equals(SeatType.Ordinary)) {
            price = basePrice;
        } else if (type.equals(SeatType.Comfort)) {
            price = basePrice * (float)1.4;
        } else {
            price = basePrice * (float)2.5;
        }
    }
    //Getters and setters
    public static void setBasePrice(float price) {basePrice=price;}
    public static float getBasePrice() {return basePrice;}
    public String getRow() {return row;}
    public int getSeatNum() {return seatNum;}
    public float getPrice() {return price;}
    public String getType() {
        if (type.equals(SeatType.Ordinary)) {
            return "Ordinary";
        } else if (type.equals(SeatType.Comfort)) {
            return "Comfort";
        }
        return "Business";
    }
    public boolean getAvailability() {return avail;}
    public void setAvailability(boolean a) {avail = a;}
}
