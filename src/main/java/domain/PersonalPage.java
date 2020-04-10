package domain;

import java.util.Date;
import java.util.HashSet;

public class PersonalPage {

    //Todo - what to do with the owner it could be Footballer or Coach (both SignedUser) or Team
    SignedUser pageOwner;
    Date openDate;
    HashSet<Fan> fans = new HashSet<>();

    public PersonalPage(SignedUser user) {
        if(user instanceof Footballer || user instanceof Coach)
            pageOwner=user;

        openDate= new Date();
    }

    public boolean removeFans(Fan fan) {
        if(fans.remove(fan)){
            return true;
        }
        return false;
    }

    public boolean addFan(Fan fan) {
        if(fans.add(fan))
            return true;
        return false;
    }
}
