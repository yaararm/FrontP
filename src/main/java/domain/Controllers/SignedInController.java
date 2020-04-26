package domain.Controllers;

import domain.Enums.UserStatus;
import domain.Users.Referee;
import domain.Users.SignedUser;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.HashMap;
import java.util.Map;

public class SignedInController {

    //Use Case 2.3
    public boolean signIn (String username, String password) throws Exception {
        if(username== null || password == null || username.length()<4 || password.length()<6){
            throw new Exception("Couldn't be that credentials");
        }
        try{
            String hashPassword = Utils.sha256(password);
            SignedUser user = SystemController.checkCredentials(username,hashPassword);
            if(user==null){
                throw new Exception("Wrong credentials");
            }
            user.changeStatus(UserStatus.LogIn);
        } catch (Exception e) {
          //  e.printStackTrace();
        }
        return true;
    }

    //Use Case 3.1
    public boolean logOut (SignedUser signedUser) throws Exception {
        if(signedUser.getStatus().equals(UserStatus.LogOut))
            return false;
        signedUser.changeStatus(UserStatus.LogOut);
        return true;
    }


    //Use Case 4.1 5.1 10.1
    public static boolean updateDetails(SignedUser signedUser, HashMap<String, String> valuesToUpdate) throws Exception {
        for (Map.Entry<String, String> entry : valuesToUpdate.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key.toLowerCase()) {
                case "password":
                    if(value.length() >= 6) {
                        String hashPassword = Utils.sha256(value);
                        signedUser.setPassword(hashPassword);
                        break;
                    }
                    else{
                        throw new Exception("Password not long enough");
                    }
                case "first name":
                    signedUser.setFirstName(value);
                    break;
                case "last name":
                    signedUser.setLastName(value);
                    break;
            }
        }
        return true;
    }

}
