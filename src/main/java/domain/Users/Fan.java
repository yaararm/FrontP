package domain.Users;

import domain.Controllers.ComplaintSystemController;
import domain.Impl.Game;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Fan extends SignedUser {
    private static int idCounter = 0;
    private int fanID;


    private long signedUpDate;
    private HashSet<PersonalPage> followedPersonalPages;
    private HashSet<Complaint> myComplaints;
    private HashSet<Game> observedGames;
    private HashMap<Long, String> mySearches;


    public Fan(String username, String password, String firstName, String lastName, String email) {
        super(username, password, firstName, lastName, email);
        signedUpDate = System.currentTimeMillis();
        followedPersonalPages = new HashSet<>();
        observedGames = new HashSet<>();
        myComplaints = new HashSet<>();
        mySearches = new HashMap<>();
        fanID = idCounter++;
    }

    //========== Follow ================
    public boolean checkIfFollowed(PersonalPage personalPage) {
        if (followedPersonalPages.contains(personalPage))
            return true;
        return false;
    }

    public boolean removeFollowed(PersonalPage personalPage) {
        if (followedPersonalPages.remove(personalPage))
            return true;
        return false;
    }

    public boolean addToFollowed(PersonalPage personalPage) {
        if (followedPersonalPages.add(personalPage))
            return true;
        return false;

    }

    //========== Get Notify ================
    public void update() {
        //TODO

    }

    public boolean addToObservedGames(Game game) {
        if (observedGames.add(game))
            return true;
        return false;
    }

    public boolean removeFromObservedGames(Game game) {
        if (observedGames.remove(game))
            return true;
        return false;
    }

    public boolean addToMyComplaints(Complaint complaint) {
        if (myComplaints.add(complaint))
            return true;
        return false;
    }

    public boolean addToMySearches(Long date, String search) {
        mySearches.put(date, search);
        return true;

    }

    //========== Getters and Setters ================
    public long getSignedUpDate() {
        return signedUpDate;
    }

    public HashMap<Long, String> getMySearches() {
        return mySearches;
    }

    public int getFanID() {
        return fanID;
    }

    //========== Delete ================
    @Override
    public boolean deleteUser() {
        for (PersonalPage followedPersonalPage : followedPersonalPages) {
            followedPersonalPage.removeFans(this);
        }
        for (Game observedGame : observedGames) {
            observedGame.removeObserver(this);
        }

        for (Complaint myComplaint : myComplaints) {
            ComplaintSystemController.moveToArchive(myComplaint);
        }
        return true;
    }


}
