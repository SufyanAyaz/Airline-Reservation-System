package Entities;

/*
 * This class stores the login information of a user
 */
public class Login {
    private String email;
    private String password;
    private String salt;
    private String role;
    public Login(String email, String password, String salt, String role) {
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.role = role;
    }
    public Login(String email, String password, String salt) {
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.role = null;
    }
    //Getters and Setters
    public String getEmail() {return email;}
    public String getPassword(){return password;}
    public String getSalt() {return salt;}
    public String getRole() {return role;}
    public void setEmail(String newEmail) {email=newEmail;}
    public void setPassword(String newPass) {password=newPass;}
}