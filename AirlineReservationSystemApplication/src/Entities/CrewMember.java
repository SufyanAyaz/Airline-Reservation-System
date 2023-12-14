package Entities;

/*
 * This class stores crew information
 */
public class CrewMember {
    private String firstName;
    private String lastName;
    private Login login;

    public CrewMember(String fName, String lName, Login login) {
        firstName = fName;
        lastName = lName;
        this.login = login;
    }

    // Getters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Login getLogin() {
        return login;
    }
}
