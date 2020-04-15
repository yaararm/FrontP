package domain.Controllers;

import domain.Impl.Team;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Random;

public class ExternalServices {

    public static boolean getNewTeamAuthorization(Team teamToAdd){
        Random rand = new Random();
        if(rand.nextInt() > 0.9)
            return false;
        return true;
    }

    public static boolean sendInviteToTheSystem(String email, String userName, String password, String invited){
        boolean valid = EmailValidator.getInstance().isValid(email);
        if(!valid || userName.length()==0 || password.length()==0)
            return false;
        String content = "Hello! "+ invited + " Havee been invited you to the most amazing football system. Your inital user name is: " +
                userName + " and your password is: "+ password;

        System.out.println("This email have been sent to the mail: "+email +"\n The content: "+ content);
        return true;
    }




}
