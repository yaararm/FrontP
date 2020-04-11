package domain.Controllers;

import domain.Enums.UserStatus;
import domain.Users.Fan;
import domain.Users.SignedUser;
import org.apache.commons.validator.routines.EmailValidator;

public class GuestController {

   //Use Case 2.2
    public boolean singUp (String email, String password, String fName, String lName) throws Exception {
        if(password == null || password.length()<6)
            throw new Exception("Not long enough");
        else if(!SystemController.UserNameValidation(email))
            throw new Exception("Not unique user name");

        boolean valid = EmailValidator.getInstance().isValid(email);
        if(!valid)
            throw new Exception("Not valid email");

        try {
            String hashPassword = Utils.sha256(password);
            Fan newUser = new Fan(email,hashPassword, fName, lName, email);
            newUser.changeStatus(UserStatus.LogIn);
            SystemController.addNewUser(email,newUser);
            SystemController.logger.info("Creation | New Fan sing up to the system; user ID: " + newUser.getFanID());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}

