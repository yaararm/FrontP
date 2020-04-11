package domain.Users;

import domain.Users.Coach;
import domain.Users.Fan;
import domain.Users.Footballer;
import domain.Users.SignedUser;

import java.util.HashSet;

public abstract class PersonalPage {
    private static int idCounter = 0;
    private int ppID;

    SignedUser pageOwner;
    long openDate;
    HashSet<Fan> fans = new HashSet<>();
    String pageName;

    public PersonalPage(SignedUser user) {
        pageOwner = user;
        this.openDate= System.currentTimeMillis();
        ppID= idCounter++;
    }

    // ============== Fans For Page ==========
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

    // ======== Getters ============
    public SignedUser getPageOwner() {
        return pageOwner;
    }

    public long getOpenDate() {
        return openDate;
    }

    public int getPpID() {
        return ppID;
    }
}
