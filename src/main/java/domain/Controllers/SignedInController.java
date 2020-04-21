package domain.Controllers;

import domain.Enums.UserStatus;
import domain.Users.SignedUser;

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
            e.printStackTrace();
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

}
