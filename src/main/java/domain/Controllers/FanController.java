package domain.Controllers;

import domain.Users.Complaint;
import domain.Users.Fan;
import domain.Impl.Game;
import domain.Users.PersonalPage;

import java.util.HashMap;
import java.util.Map;


public class FanController {

    // ============== Follow ============
    //Use Case 3.2
    public boolean follow(Fan fan, PersonalPage personalPage) {
        if (fan.checkIfFollowed(personalPage)) {
            fan.removeFollowed(personalPage);
            personalPage.removeFans(fan);
            return false;
        } else {
            fan.addToFollowed(personalPage);
            personalPage.addFan(fan);
            return true;
        }

    }

    // ============ Subscribe ===========
    //Use Case 3.3 - observer
    public boolean subscribe(Fan fan, Game game) {
        if (game.checkObserver(fan)) {
            game.removeObserver(fan);
            fan.removeFromObservedGames(game);
            return false;
        } else {
            game.attachObserver(fan);
            fan.addToObservedGames(game);
            return true;
        }

    }

    // ============ Complaint ===========
    //Use Case 3.4
    public boolean createComplaint(Fan fan, String description) {
        if (description.length() <= 0) {
            return false;
        }
        Complaint complaint = new Complaint(fan, description);
        fan.addToMyComplaints(complaint);
        SystemController.logger.info("New complaint have been created; Complaint ID: " + complaint.getComplaintID() + "; Fan ID: " + fan.getFanID());
        return true;

    }

    // ============ Search History ==============
    //Use Case 3.5
    public Map<String, Long> mySearchHistory(Fan fan, long fromDate, long toDate) throws Exception {
        if (fromDate < fan.getSignedUpDate() || fromDate > toDate) {
            throw new Exception("Wrong Dates");
        }
        Map<String, Long> searchHistory = SystemController.getSearchHistory(fan, fromDate, toDate);
        if (searchHistory == null || searchHistory.size() == 0) {
            throw new Exception("No Search History");
        }
        return searchHistory;
    }

    // ========= Update ==============
    //Use Case 3.6
    public boolean updateDetails(Fan fan, HashMap<String, String> valuesToUpdate) {
        for (Map.Entry<String, String> entry : valuesToUpdate.entrySet()) {
            switch (entry.getKey().toLowerCase()) {
                case "email":
                    fan.setEmail(entry.getValue());
                    break;
                case "firstname":
                    fan.setFirstName(entry.getValue());
                    break;
                case "lastname":
                    fan.setLastName(entry.getValue());
                    break;
                case "password":
                    String hashPassword = Utils.sha256(entry.getValue());
                    fan.setPassword(hashPassword);
                    break;
            }

        }
        return true;
    }


}
