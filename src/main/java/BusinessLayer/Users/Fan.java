package BusinessLayer.Users;

import ServiceLayer.Controllers.ComplaintSystemController;
import DB.SystemController;
import BusinessLayer.Football.Game;
import BusinessLayer.SystemFeatures.Complaint;
import BusinessLayer.SystemFeatures.PersonalPage;

import java.util.HashMap;
import java.util.HashSet;

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
        return followedPersonalPages.contains(personalPage);
    }

    public boolean removeFollowed(PersonalPage personalPage) {
        return followedPersonalPages.remove(personalPage);
    }

    public boolean addToFollowed(PersonalPage personalPage) {
        return followedPersonalPages.add(personalPage);

    }

    //========== Get Notify ================
    public void update() {
        //TODO
    }

    public boolean addToObservedGames(Game game) {
        return observedGames.add(game);
    }

    public boolean removeFromObservedGames(Game game) {
        return observedGames.remove(game);
    }

    public boolean addToMyComplaints(Complaint complaint) {
        return myComplaints.add(complaint);
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

    public HashSet<PersonalPage> getFollowedPersonalPages() {
        return followedPersonalPages;
    }

    public HashSet<Complaint> getMyComplaints() {
        return myComplaints;
    }

    public HashSet<Game> getObservedGames() {
        return observedGames;
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
        SystemController.archiveUsers.put(this.getUserName(),this);
        SystemController.userNameUser.remove(this);
        return true;
    }


}
