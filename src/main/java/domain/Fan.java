package domain;

import java.util.Date;
import java.util.HashSet;

public class Fan extends SignedUser {
    private String email;
    private String firstName;
    private String lastName;

    private Date signedUpDate;
    private HashSet<PersonalPage> followedPersonalPages;
    private HashSet<Complaint> myComplaints;
    private HashSet<Game> observedGames;


    public Fan(String username, String password) {
        super(username, password);
        signedUpDate = new Date();
        followedPersonalPages = new HashSet<>();
        observedGames = new HashSet<>();
        myComplaints = new HashSet<>();
    }

    public boolean checkIfFollowed(PersonalPage personalPage) {
        if(followedPersonalPages.contains(personalPage))
            return true;
        return false;
    }

    public boolean removeFollowed(PersonalPage personalPage) {
        if(followedPersonalPages.remove(personalPage))
            return true;
        return false;
    }

    public boolean addToFollowed(PersonalPage personalPage) {
        if(followedPersonalPages.add(personalPage))
            return true;
        return false;

    }

    public void update() {

    }

    public boolean addToObservedGames(Game game) {
        if(observedGames.add(game))
            return true;
        return false;
    }

    public boolean removeFromObservedGames(Game game) {
        if(observedGames.remove(game))
            return true;
        return false;
    }

    public boolean addToMyComplaints(Complaint complaint) {
        if(myComplaints.add(complaint))
            return true;
        return false;
    }

    public Date getSignedUpDate() {
        return signedUpDate;
    }

}
