package domain.controllers;

import domain.*;

import java.security.MessageDigest;

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
            String hashPassword = sha256(password);
            SignedUser newUser = new Fan(username,hashPassword);
            newUser.changeStatus(UserStatus.LogIn);
            SystemController.addNewUser(username,newUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

