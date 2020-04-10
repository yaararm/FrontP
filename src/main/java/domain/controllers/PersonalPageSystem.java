package domain.controllers;

import domain.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class PersonalPageSystem {
    static HashMap<Footballer, PersonalPage> footballersPersonalPage = new HashMap<>();
    HashMap<Team, PersonalPage> teamsPersonalPage = new HashMap<>();
    static HashMap<Coach, PersonalPage> coachesPersonalPage = new HashMap<>();


    public static boolean addToFootballerList(Footballer footballer, PersonalPage myPersonalPage) {
        footballersPersonalPage.put(footballer,myPersonalPage);
        return true;
    }

    public static boolean addToCoachList(Coach coach, PersonalPage myPersonalPage) {
        coachesPersonalPage.put(coach,myPersonalPage);
        return true;
    }

    //TODO complete
    //Use Case 4.1 5.1
    public boolean updatePersonalPage(SignedUser signedUser, HashMap<String,String> valuesToUpdate){
        if(signedUser instanceof Footballer || signedUser instanceof Coach){
            for (Map.Entry<String, String> entry : valuesToUpdate.entrySet()) {
                switch (entry.getKey()) {
                    case "birthday":
                        break;
                    case "history":
                        break;

                }
            }

        }
        return true;
    }

    //TODO complete
    //Use Case 4.2 5.2
    public boolean addContentToPersonalPage(SignedUser signedUser, HashMap<String,String> valuesToUpdate){
        if(signedUser instanceof Footballer || signedUser instanceof Coach){
            for (Map.Entry<String, String> entry : valuesToUpdate.entrySet()) {
                switch (entry.getKey()) {
                    case "birthday":
                        break;
                    case "history":
                        break;

                }
            }

        }
        return true;
    }

}