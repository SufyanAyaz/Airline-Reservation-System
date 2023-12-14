package Entities;

/*
 * This class stores aircraft information
 */
public class Aircraft {
    private String manufact;
    private String model;
    private int ordinaryCap;
    private int buisnessCap;
    private int comfortCap;

    public Aircraft(String manu, String mod, int oC, int bC, int cC) {
        manufact = manu;
        model = mod;
        ordinaryCap = oC;
        buisnessCap = bC;
        comfortCap = cC;
    }

    // Getters
    public String getManufacturer() {
        return manufact;
    }

    public String getModel() {
        return model;
    }

    public int getOrdinaryCapacity() {
        return ordinaryCap;
    }

    public int getBuisnessCapacity() {
        return buisnessCap;
    }

    public int getComfortCapacity() {
        return comfortCap;
    }
}
