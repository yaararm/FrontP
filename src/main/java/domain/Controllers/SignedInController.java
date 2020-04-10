package domain.Controllers;

import domain.Users.SignedUser;
import domain.Enums.UserStatus;

import java.security.MessageDigest;

public class SignedInController {

    //Use Case 2.3
    public boolean signIn (String username, String password) throws Exception {
        if(username== null || password == null || username.length()<4 || password.length()<6){
            throw new Exception("Couldn't be that credentials");
        }
        try{
            String hashPassword = sha256(password);
            SignedUser user = SystemController.checkCredentials(username,password);
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

    public static String sha256(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
