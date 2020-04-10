package domain.Controllers;

import domain.Impl.League;
import domain.Impl.Team;
import domain.Users.Fan;
import domain.Users.Referee;
import domain.Users.SignedUser;
import domain.Users.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

public class SystemController {
    static HashMap<String, User> userNameUser= new HashMap<>();
    static HashMap<Fan, HashMap<String, Long>> fanHistory= new HashMap<>();
    static HashMap<String, League> leagueNameLeagues = new HashMap<>();
    public static HashSet<Team> systemTeams = new HashSet<>();
    public static HashMap<Integer, Referee> IDReferees = new HashMap<>();
    static HashSet<User> archiveUsers= new HashSet<>();
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

    public static Map<String, Long> getSearchHistory(Fan fan, final long fromDate, final long toDate) {
        HashMap<String, Long> historyDateHashMap = fanHistory.get(fan);
        if(historyDateHashMap != null) {
            Map<String, Long> collect =
                    historyDateHashMap.entrySet().stream().filter(s -> s.getValue()<toDate && s.getValue()>fromDate).collect(Collectors.toMap(stringDateEntry -> stringDateEntry.getKey(), stringDateEntry -> stringDateEntry.getValue()));
            return collect;
        }
        return null;
    }

    public static boolean leaguesNameValidation(String leagueName) {
        return leagueNameLeagues.containsKey(leagueName);
    }

    public static boolean addNewLeague(String leagueName, League newLeague) {
        leagueNameLeagues.put(leagueName,newLeague);
         return true;
    }


    //TODO complete search and show
    /// 2.5




}
