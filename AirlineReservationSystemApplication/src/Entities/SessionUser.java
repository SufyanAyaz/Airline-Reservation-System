package Entities;

/*
 * This class stores user information
 */
public class SessionUser {

    private static SessionUser activeUser = null;//Singleton design pattern
    private UserInfo user = null;

    private SessionUser() {
        activeUser = this;
    }

    /*
     * If single instance is instantiated, return it. Otherwise, create
     * it and return it.
     */
    public static synchronized SessionUser getInstance() {
        if (activeUser == null) activeUser = new SessionUser();
        return activeUser;
    }

    /*
     * For logging out. Single instance of SessionUser is set to null, so
     * that it can be replaced by a new user.
     */
    public static synchronized void clearUser() {
        activeUser = null;
    }

    /*
     * Quick check to see if a user is currently logged in.
     */
    public static synchronized boolean isInstantiated() {
        return activeUser != null;
    }

    //Setters
    public void setUser(UserInfo user) {this.user = user;}

    //Getters
    public UserInfo getSessionUser() {return this.user;}
    public String getFirstName() {return user.getFirstName();}
    public String getLastName() {return user.getLastName();}
    public String getEmail() {return user.getEmail();}
    public String getAddress() {return user.getAddress();}
    public String getPostalCode() {return user.getPostalCode();}
    public CreditCard getCreditCard() {return user.getCreditCard();}
}
