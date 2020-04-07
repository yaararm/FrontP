package domain.controllers;

import domain.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class SystemController {
    static HashMap<String, User> userNameUser= new HashMap<>();
    static HashMap<Fan, HashMap<String, Date>> fanHistory= new HashMap<>();



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


    //TODO complete search and show
    /// 2.5




}
