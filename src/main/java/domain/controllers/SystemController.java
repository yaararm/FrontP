package domain.controllers;

import domain.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.HashSet;

public class SystemController {
    static HashMap<String, User> userNameUser= new HashMap<>();
    static HashMap<Fan, HashMap<String, Date>> fanHistory= new HashMap<>();
    static HashMap<String, League> leagues = new HashMap<>();
    public static HashSet<Team> systemTeams = new HashSet<>();
    static final Logger logger = LogManager.getLogger(SystemController.class.getName());

    public static boolean UserNameValidation(String username) {
        if (userNameUser.containsKey(username))
            return false;
        return true;
    }

    public static boolean addNewUser(String username, SignedUser newUser) {
        userNameUser.put(username,newUser);
        return true;
    }

    public static SignedUser checkCredentials(String username, String password) {
        SignedUser user = (SignedUser) userNameUser.get(username);
        if(user.getPassword().equals(password))
            return user;
        return null;
    }

    public static Map<String, Date> getSearchHistory(Fan fan, final Date fromDate, final Date toDate) {
        HashMap<String, Date> historyDateHashMap = fanHistory.get(fan);
        if(historyDateHashMap != null) {
            Map<String, Date> collect = historyDateHashMap.entrySet().stream().filter(s -> s.getValue().before(toDate) && s.getValue().after(fromDate)).collect(Collectors.toMap(stringDateEntry -> stringDateEntry.getKey(), stringDateEntry -> stringDateEntry.getValue()));
            return collect;
        }
        return null;
    }

    public static boolean leaguesNameValidation(String leagueName) {
        return leagues.containsKey(leagueName);
    }

    public static boolean addNewLeague(String leagueName, League newLeague) {
        leagues.put(leagueName,newLeague);
         return true;
    }


    //TODO complete search and show
    /// 2.5




}
