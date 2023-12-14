package Entities;

/*
 * This class stores Location information
 */
public class Location {
    private String name;
    private String airportCode;

    public Location(String name, String aCode) {
        this.name = name;
        this.airportCode = aCode;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getAirportCode() {
        return airportCode;
    }
}
