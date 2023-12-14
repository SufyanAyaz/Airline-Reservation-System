package Controller.Accounts;

import Controller.DatabaseController;
import Entities.CreditCard;
import Entities.Login;
import Entities.UserInfo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class RegistrationController {
    static final String STATIC_SALT = "pepper";

    public int registerUser(String fName, String lName, String email, String password,
                                String addr, String postal) throws SQLException {
        password = password.trim();
        email = email.trim();
        int code;
        if((code = validateLoginInfo(email, password)) > 0) {
            return code;
        }

        String dynaSalt = makeSaltString();
        password = password.concat(STATIC_SALT).concat(dynaSalt);
        password = superSecureHashingAlgo(password);

        Login login = new Login(email, password, dynaSalt);

        UserInfo user = new UserInfo(fName, lName, email, addr, postal, login, null);
        ArrayList<UserInfo> userList = new ArrayList<>();
        userList.add(user);
        DatabaseController.connect();
        DatabaseController.setUsers(userList);
        DatabaseController.terminate();

        return 0;
    }

    private int validateLoginInfo(String email, String password) {
        if(password.length() < 10 || password.length() > 20) {
            return 1;
        }
        if(email.length() < 5 || email.length() > 50) {
            return 2;
        }
        return 0;
    }

    private String makeSaltString() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        char[] saltArr = new char[10];

        Random rand = new Random();
        int saltIndex;
        for (int i = 0; i < 10; i++) { // length of the random string.
            saltIndex = rand.nextInt(characters.length());
            saltArr[i] = characters.charAt(saltIndex);
        }

        return new String(saltArr);
    }

    static String superSecureHashingAlgo(String insecure) {
        // Make insecure string secure.
        String secure = insecure;
        return secure;
    }
}
