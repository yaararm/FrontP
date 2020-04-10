package domain.Users;

import domain.Users.Coach;
import domain.Users.Fan;
import domain.Users.Footballer;
import domain.Users.SignedUser;

import java.util.HashSet;

public class PersonalPage {

    //Todo - what to do with the owner it could be Footballer or Coach (both SignedUser) or Team
    SignedUser pageOwner;
    long openDate;
    HashSet<Fan> fans = new HashSet<>();

    public PersonalPage(SignedUser user) {
        if(user instanceof Footballer || user instanceof Coach)
            pageOwner=user;

        this.openDate= System.currentTimeMillis();
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
