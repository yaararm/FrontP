package domain.controllers;

import domain.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class FanController {
    //Use Case 3.2
    public boolean follow(Fan fan, PersonalPage personalPage) {
        if (fan.checkIfFollowed(personalPage)) {
            fan.removeFollowed(personalPage);
            personalPage.removeFans(fan);
        } else {
            fan.addToFollowed(personalPage);
            personalPage.addFan(fan);
        }
        return true;
    }

    //Use Case 3.3 - observer
    public boolean subscribe(Fan fan, Game game) {
        if (game.checkObserver(fan)) {
            game.removeObserver(fan);
            fan.removeFromObservedGames(game);
        } else {
            game.attachObserver(fan);
            fan.addToObservedGames(game);
        }
        return true;
    }

    //Use Case 3.4
    public boolean createComplaint(Fan fan, String description) {
        if (description.length() > 0) {
            Complaint complaint = new Complaint(fan, description);
            fan.addToMyComplaints(complaint);
            return true;
        }
        return false;
    }

    //Use Case 3.5
    public Map<String, Date> mySearchHistory(Fan fan, Date fromDate, Date toDate) throws Exception {
        if (fromDate.before(fan.getSignedUpDate()) || fromDate.after(toDate)) {
            throw new Exception("Wrong Dates");
        }
        Map<String, Date> searchHistory = SystemController.getSearchHistory(fan, fromDate, toDate);
        if (searchHistory == null || searchHistory.size() == 0) {
            throw new Exception("No Search History");
        }
        return searchHistory;
    }

    //TODO complete
    //Use Case 3.6
    public boolean updateDetails(HashMap<String, String> valuesToUpdate) {
        for (Map.Entry<String, String> entry : valuesToUpdate.entrySet()) {
            switch (entry.getKey()) {
                case "email":
                    break;
                case "firstName":
                    break;

            }

        }
        return true;
    }



}
