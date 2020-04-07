package domain.controllers;

import domain.*;

public class guestController {

   //Use Case 2.2
    public boolean singUp (String username, String password) throws Exception {
        if(username== null || password == null || username.length()<4 || password.length()<6){
            throw new Exception("Not long enough");
        }
        else if(!SystemController.UserNameValidation(username)){
            throw new Exception("Not unique user name");
        }
        SystemController.addNewUser(username,password);
        SignedUser newUser = new Fan(username,password);

        return true;
    }

    //Use Case 2.3
    public boolean signIn (Guest g, String username, String password) throws Exception {
        if(username== null || password == null || username.length()<4 || password.length()<6){
            throw new Exception("Couldn't be that credentials");
        }
        if(!SystemController.checkCredentials(username,password)) {
            throw new Exception("Wrong credentials");
        }
        g.changeStatus(UserStatus.LogIn);
        return true;
    }

    //Use Case 2.4 - Should be in the front? who's is responsibly to retrieve information?


    //Use Case 2.5 - Should be in the front? who's is responsibly to retrieve information?



}
