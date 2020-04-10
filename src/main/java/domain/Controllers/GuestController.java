package domain.Controllers;

import domain.Enums.UserStatus;
import domain.Users.Fan;
import domain.Users.SignedUser;

public class GuestController {

   //Use Case 2.2
    public boolean singUp (String username, String password) throws Exception {
        if(username== null || password == null || username.length()<4 || password.length()<6){
            throw new Exception("Not long enough");
        }
        else if(!SystemController.UserNameValidation(username)){
            throw new Exception("Not unique user name");
        }
        try {
            String hashPassword = Utils.sha256(password);
            SignedUser newUser = new Fan(username,hashPassword);
            newUser.changeStatus(UserStatus.LogIn);
            SystemController.addNewUser(username,newUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}

