package Entities;

public class Agent {
    private String firstName;
    private String lastName;
    private Login login;
    private int id;
    private String company;
    public Agent(String fName, String lName, Login log, int id, String comp) {
        firstName = fName;
        lastName = lName;
        login = log;
        this.id = id;
        company = comp;
    }
    public Agent(String fName, String lName, Login log, int id) {
        firstName = fName;
        lastName = lName;
        login = log;
        this.id = id;
    }
    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public Login getLogin() {return login;}
    public int getID() {return id;}
    public String getCompany() {return company;}
}
