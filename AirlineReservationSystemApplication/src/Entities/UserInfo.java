package Entities;

/*
 * This class is used for storing user information retrieved from the
 * database.
 */
public class UserInfo {
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String postalCode;
    private Login login;
    private CreditCard creditCard;

    public UserInfo(String fName, String lName, String email, String uAddress,
                    String uPostalCode, Login log, CreditCard credit) {
        firstName = fName;
        lastName = lName;
        this.email = email;
        this.address = uAddress;
        this.postalCode = uPostalCode;
        login = log;
        creditCard = credit;
    }

    public UserInfo(String fName, String lName, String email) {
        firstName = fName;
        lastName = lName;
        this.email = email;
    }

    // Getters
    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public String getEmail() {return email;}
    public String getAddress() {return address;}
    public String getPostalCode() {return postalCode;}
    public Login getLogin() {return login;}
    public CreditCard getCreditCard() {return creditCard;}
}
