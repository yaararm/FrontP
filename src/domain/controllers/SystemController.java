package domain.controllers;

import domain.Team;

import java.util.HashMap;
import java.util.HashSet;

public class SystemController {
    static HashMap<String, String> userNamePassword= new HashMap<>();

    public static HashSet<Team> systemTeams = new HashSet<>();

    public static boolean UserNameValidation(String username) {
        if (userNamePassword.containsKey(username))
            return false;
        return true;
    }

    public static boolean addNewUser(String username, String password) {
        userNamePassword.put(username,password);
        return true;
    }

    public static boolean checkCredentials(String username, String password) {
        return false;
    }
}
