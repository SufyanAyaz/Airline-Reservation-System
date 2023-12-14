package Controller.Accounts;

import Controller.DatabaseController;
import Entities.*;

import java.sql.SQLException;
import java.util.HashMap;

public class LoginSessionController {
    private HashMap<String, UserInfo> users;
    private HashMap<String, Login> logins; // <Username, Password>

    public LoginSessionController() throws SQLException {
        users = new HashMap<>();
        logins = new HashMap<>();
        retrieveUsers();
        System.out.println(users);
        System.out.println(logins);
    }

    /*
     * If email and password are valid, creates new user and sets
     * them as the session user. Returns 0 on failure, and an int
     * representing the user type on success.
     */
    public int loginUser(String email, String password) {
        if (!(users.containsKey(email) && logins.containsKey(email))) {
            return 0;
        }
        // Add salt to provided password and hash
        password = password.trim();
        password = password.concat(RegistrationController.STATIC_SALT);
        password = password.concat(logins.get(email).getSalt());
        password = RegistrationController.superSecureHashingAlgo(password);

        // Compare to stored password
        if (!password.equals(logins.get(email).getPassword())) {
            return 0;
        }

        // Set user as session user
        SessionUser loggedInUser = SessionUser.getInstance();
        loggedInUser.setUser(users.get(email));

        String role = logins.get(email).getRole();

        // Let boundary class know what kind of user logged in.
        if (role.equals("USER")) {
            return 1;
        } else if (role.equals("AGENT")) {
            return 2;
        } else if (role.equals("ADMIN")) {
            return 3;
        } else if (role.equals("CREW")) {
            return 4;
        } else if (role.equals("TOURIST")) {
            return 5;
        } else {
            return 1;
        }
    }

    /*
     * If a user is currently logged in, logs them out.
     */
    public static void logout() {
        if (SessionUser.isInstantiated())
            SessionUser.clearUser();
    }

    public static String getSessionFName() {
        if (!SessionUser.isInstantiated())
            return null;
        SessionUser user = SessionUser.getInstance();
        return user.getFirstName();
    }

    public static String getSessionLName() {
        if (!SessionUser.isInstantiated())
            return null;
        SessionUser user = SessionUser.getInstance();
        return user.getLastName();
    }

    public static String getSessionEmail() {
        if (!SessionUser.isInstantiated())
            return null;
        SessionUser user = SessionUser.getInstance();
        return user.getEmail();
    }

    /*
     * Gets list of registered users and logins from the database, and puts them
     * in a map with their email address as the key.
     */
    private void retrieveUsers() throws SQLException {
        DatabaseController.connect();
        for (UserInfo u : DatabaseController.getRegUsers()) {
            System.out.println(u);
            users.put(u.getEmail(), u);
        }
        for (Login l : DatabaseController.getLogins()) {
            System.out.println(l);
            logins.put(l.getEmail(), l);
        }
        DatabaseController.terminate();
    }
}
