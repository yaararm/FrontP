package BusinessLayer.SystemFeatures;

import BusinessLayer.Users.Fan;
import BusinessLayer.Users.SignedUser;

import java.util.HashSet;

public abstract class PersonalPage {
    private static int idCounter = 0;
    private int ppID;

    private SignedUser pageOwner;
    private long openDate;
    private HashSet<Fan> fans = new HashSet<>();
    protected String pageName;

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

    @Override
    public String toString() {
        return pageOwner.toString() +" "+pageName +" ";
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

    public static int getIdCounter() {
        return idCounter;
    }

    public HashSet<Fan> getFans() {
        return fans;
    }

    public String getPageName() {
        return pageName;
    }

}
