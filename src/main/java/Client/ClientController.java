package Client;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;


public class ClientController extends Observable implements Observer {

    private RestTemplate restTemplate;
    private String localhost;
    private int sessionid;
    private int userType = 0;
    private String userEmail;
    private String fullName;
    public String isEditGame;
    private HashMap<String, String> leagues;
    private HashMap<String, String> seasons;
    private HashMap<String, String> policies;
    private HashMap<String, String> Teams;
    private HashMap<String, String> onGoingGames;
    private HashMap<String, String> futureGames;
    private HashMap<String, String> editGames;
    private HashMap<String, String> reportGames;
    private HashMap<String, String> unseenMessage;
    private HashMap<String, String> oldmessage;
    private ArrayList<eventDetails> beforeEdit;

    public int getUserType() {
        return userType;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getFullName() {
        return fullName;
    }

    public ClientController() {

        localhost = "http://localhost:8083/";

    }

    @Override
    public void update(Observable o, Object arg) {


    }

    //input: map: email,password
    //output: map: status, type of user, ssesion id
    // return the number of the users
    //1-owner
    //2-referee
    //3-ARP
    //4-fan
    //5-
    public HashMap<String, String> loginDetails(String password, String email) {
        // create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // request body parameters
        Map<String, String> toServer = new HashMap<>();
        toServer.put("username", email);
        toServer.put("password", password);

        // send POST request
        HashMap<String, String> response = restTemplate.postForObject(localhost + "login", toServer, HashMap.class);
        if (response.get("status").compareTo("fine") == 0) {
            sessionid = Integer.parseInt(response.get("sid"));
            userType = Integer.parseInt(response.get("type"));
            userEmail = response.get("username");
            fullName = response.get("firstname") + " " + response.get("lastname");
        }

        return response;


    }

    //input: map: email,password
    //output: map: status, ssesion id
    public HashMap<String, String> signUp(String first, String last, String email, String passwordEncryped) {
        // create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Map<String, Object> toServer = new HashMap<>();
        // request body parameters
        toServer.put("firstname", first);
        toServer.put("lastname", last);
        toServer.put("email", email);
        toServer.put("password", passwordEncryped);

        HashMap<String, String> ans = restTemplate.postForObject(localhost + "register", toServer, HashMap.class);

        if (ans.get("status").compareTo("fine") == 0) {
            sessionid = Integer.parseInt(ans.get("sid"));
            userType = Integer.parseInt(ans.get("type"));
            userEmail = ans.get("username");
            fullName = ans.get("firstname") + " " + ans.get("lastname");
        }
        return ans;
    }

    //input:
    //output:
    public HashMap<String, String> logout() {
        // create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // request body parameters
        Map<String, String> toServer = new HashMap<>();
        toServer.put("sid", String.valueOf(sessionid));

        // send POST request
        HashMap<String, String> response = restTemplate.postForObject(localhost + "logout", toServer, HashMap.class);
        if (response.get("status").compareTo("fine") == 0) {
            sessionid = 0;
            userType = 0;
            userEmail = "";
            fullName = "";
            leagues.clear();
            seasons.clear();
            policies.clear();
            Teams.clear();
            onGoingGames.clear();
            futureGames.clear();
            editGames.clear();
            reportGames.clear();
            unseenMessage.clear();
            oldmessage.clear();
            isEditGame="";

        }
        return response;


    }

    //-----------------------------------owner------------------------------------
    //input: String: team name
    //output: String: status
    public HashMap<String, String> creatNewTeam(String teamName) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        Map<String, String> toServer = new HashMap<>();
        toServer.put("sid", String.valueOf(sessionid));
        toServer.put("teamname", teamName);

        HashMap<String, String> response = restTemplate.postForObject(localhost + "creatNewTeam", toServer, HashMap.class);
        return response;

    }

    //input:
    //output:
    public HashMap<String, String> reportNewFinanceAction(String Team, String action, String amount, String description) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        Map<String, String> toServer = new HashMap<>();
        toServer.put("sid", String.valueOf(sessionid));
        toServer.put("teamID", getKeyByValue(Teams, Team));
        toServer.put("action", action);
        toServer.put("amount", amount);
        toServer.put("description", description);

        HashMap<String, String> response = restTemplate.postForObject(localhost + "reportNewFinanceAction", toServer, HashMap.class);
        return response;
    }

    //-----------------------------------ARP------------------------------------
    //input: map: leaguname' rtraining
    //output: String: status
    public HashMap<String, String> creatNewLeague(String leagueName, String rTraining) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        Map<String, String> toServer = new HashMap<>();
        toServer.put("sid", String.valueOf(sessionid));
        toServer.put("leagueName", leagueName);
        toServer.put("RefereeTraining", rTraining);

        HashMap<String, String> response = restTemplate.postForObject(localhost + "creatNewLeague", toServer, HashMap.class);
        return response;
    }


    public HashMap<String, String> creatNewSeason(LocalDate date, String leaguename) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        Map<String, String> toServer = new HashMap<>();
        Date startDate = new Date(date.toEpochDay());
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        int year = date.getYear();
        toServer.put("sid", String.valueOf(sessionid));
        toServer.put("leagueID", getKeyByValue(leagues, leaguename));
        toServer.put("year", String.valueOf(year));
        toServer.put("Day", String.valueOf(day));
        toServer.put("month", String.valueOf(month));
        HashMap<String, String> response = restTemplate.postForObject(localhost + "creatNewSeason", toServer, HashMap.class);
        return response;

    }

    //input: league name, Season year, policy
    //output: status
    public HashMap<String, String> assignNewScorePolicy(String leagueName, String season, String policy) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        Map<String, String> toServer = new HashMap<>();
        toServer.put("sid", String.valueOf(sessionid));
        toServer.put("leagueID", getKeyByValue(leagues, leagueName));
        toServer.put("seasonID", getKeyByValue(seasons, season));
        toServer.put("policy", policy);
        HashMap<String, String> response = restTemplate.postForObject(localhost + "assignNewScorePolicy", toServer, HashMap.class);
        return response;
    }

    //input: league name, Season year, policy
    //output: status
    public HashMap<String, String> assignNewGamePolicy(String leagueName, String season, String policy) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        Map<String, String> toServer = new HashMap<>();
        toServer.put("sid", String.valueOf(sessionid));
        toServer.put("leagueID", getKeyByValue(leagues, leagueName));
        toServer.put("seasonID", getKeyByValue(seasons, season));
        toServer.put("policy", policy);
        HashMap<String, String> response = restTemplate.postForObject(localhost + "assignNewGamePolicy", toServer, HashMap.class);
        return response;
    }


    //-----------------------------------Fan------------------------------------

    //input:
    //output: map: status, message
    public HashMap<String, String> checkForOldUpdates() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        Map<String, String> toServer = new HashMap<>();
        toServer.put("sid", String.valueOf(sessionid));
        HashMap<String, String> response = restTemplate.postForObject(localhost + " getSeenAlerts", toServer, HashMap.class);

        oldmessage = response;
        return response;

    }
    //input:
    //output: map: status, message
    public HashMap<String, String> checkForNewUpdates() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        Map<String, String> toServer = new HashMap<>();
        toServer.put("sid", String.valueOf(sessionid));
        HashMap<String, String> response = restTemplate.postForObject(localhost + "  getUnseenAlerts", toServer, HashMap.class);

        unseenMessage = response;
        return response;

    }
    //input: new seen message
    //output: map: status, message
    public void setAlertsToSeen() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        Map<String, String> toServer = unseenMessage;
        toServer.put("sid", String.valueOf(sessionid));
        HashMap<String, String> response = restTemplate.postForObject(localhost + "  setAlertToSeen", toServer, HashMap.class);

        unseenMessage.clear();
        //checkForOldUpdates(); maybe

    }
    //-----------------------------------referee-------------------------
    public HashMap<String, String> addEventToGame(String game, String event, String minute, String description) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        Map<String, String> toServer = new HashMap<>();
        toServer.put("sid", String.valueOf(sessionid));
        toServer.put("gameID", getKeyByValue(onGoingGames, game));
        toServer.put("event", event);
        toServer.put("minute", minute);
        toServer.put("description", description);
        toServer.put("username", userEmail);

        HashMap<String, String> response = restTemplate.postForObject(localhost + "addEventToGame", toServer, HashMap.class);
        return response;


    }

    //input: email
    //output: map: status, message
    public HashMap<String, String> createReport(String game) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        Map<String, String> toServer = new HashMap<>();
        toServer.put("sid", String.valueOf(sessionid));
        toServer.put("gameID", getKeyByValue(editGames, game));
        toServer.put("username", userEmail);

        HashMap<String, String> response = restTemplate.postForObject(localhost + "createReport", toServer, HashMap.class);
        return response;
    }

    //input: event id
    //output:status
    public   HashMap<String, String> deleteEvent(String event, String min, String desc, String num) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        Map<String, String> toServer = new HashMap<>();
        toServer.put("sid", String.valueOf(sessionid));
        toServer.put("event", beforeEdit.get(Integer.parseInt(num)).get_id());
        HashMap<String, String> response = restTemplate.postForObject(localhost + "deleteEvent", toServer, HashMap.class);
        return response;
    }
    //input: event id, details
    //output:status
    public   HashMap<String, String> editEvent(int index, HashMap<String, String> edit) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        edit.put("sid", String.valueOf(sessionid));
        edit.put("event",beforeEdit.get(index).get_id());
       edit.put("game", getKeyByValue(editGames,isEditGame));
        HashMap<String, String> response = restTemplate.postForObject(localhost + "editEvent", edit, HashMap.class);
        return response;
    }

    //--------------------------------------getters---------------------
    //input:
    //output: all leagues name
    public HashMap<String, String> getAllLeagues() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        Map<String, String> toServer = new HashMap<>();
        toServer.put("sid", String.valueOf(sessionid));


        HashMap<String, String> response = restTemplate.postForObject(localhost + "getAllLeagues", toServer, HashMap.class);
        if (response.get("status").compareTo("fine") == 0) {
            leagues = response;
            leagues.remove("status");
        }
        return leagues;
    }

    //input: String league name
    //output: all Seasons of that league
    public HashMap<String, String> getAllSeasons(String leagueName) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        Map<String, String> toServer = new HashMap<>();
        toServer.put("sid", String.valueOf(sessionid));
        toServer.put("leaugueID", getKeyByValue(leagues, leagueName));

        HashMap<String, String> response = restTemplate.postForObject(localhost + "getAllSeasons", toServer, HashMap.class);
        if (response.get("status").compareTo("fine") == 0) {
            seasons = response;
            seasons.remove("status");
        }
        return response;
    }

    //input:
    //output: all policies
    public ArrayList<String> getAllScorePolicies() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        Map<String, String> toServer = new HashMap<>();
        toServer.put("sid", String.valueOf(sessionid));
        //toServer.put("leaugueID",getKeyByValue(leagues,leagueName));

        ArrayList<String> response = restTemplate.postForObject(localhost + "getAllScorePolicies", toServer, ArrayList.class);
        return response;
    }

    public ArrayList<String> getAllGamePolicies() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        Map<String, String> toServer = new HashMap<>();
        toServer.put("sid", String.valueOf(sessionid));
        //toServer.put("leaugueID",getKeyByValue(leagues,leagueName));

        ArrayList<String> response = restTemplate.postForObject(localhost + "getAllGamePolicies", toServer, ArrayList.class);
        return response;
    }

    //input: leaguename, year, start date
    //output: Status;
    public HashMap<String, String> getAllTeams() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        Map<String, String> toServer = new HashMap<>();
        toServer.put("sid", String.valueOf(sessionid));
        toServer.put("username", userEmail);

        HashMap<String, String> response = restTemplate.postForObject(localhost + "getAllTeams", toServer, HashMap.class);
        if (response.get("status").compareTo("fine") == 0) {

            response.remove("status");
            Teams = response;
        }
        return response;
    }


    //input: referee user name
    //output: all future games of that referre
    public HashMap<String, String> getMyUpcomingsGames() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        Map<String, String> toServer = new HashMap<>();
        toServer.put("sid", String.valueOf(sessionid));
        toServer.put("username", userEmail);

        HashMap<String, String> response = restTemplate.postForObject(localhost + "getMyUpcomingsGames", toServer, HashMap.class);
        if (response.get("status").compareTo("fine") == 0) {

            response.remove("status");
            futureGames = response;
        }
        return response;
    }

    //input:
    //output: all possible events in game
    public ArrayList<String> getAllPossibleEvents() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        Map<String, String> toServer = new HashMap<>();
        toServer.put("sid", String.valueOf(sessionid));
        //toServer.put("leaugueID",getKeyByValue(leagues,leagueName));

        ArrayList<String> response = restTemplate.postForObject(localhost + "getAllPossibleEvents", toServer, ArrayList.class);
        return response;
    }

    //input: referee user name
    //output: all ongoing games of that referre
    public HashMap<String, String> getAllOnGoingGames() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        Map<String, String> toServer = new HashMap<>();
        toServer.put("sid", String.valueOf(sessionid));
        toServer.put("username", userEmail);

        HashMap<String, String> response = restTemplate.postForObject(localhost + "getAllOnGoingGames", toServer, HashMap.class);
        if (response.get("status").compareTo("fine") == 0) {

            response.remove("status");
            onGoingGames = response;
        }
        return response;

    }

    //input: referee user name
    //output: all ongoing games of that referre
    public HashMap<String, String> getAllPossibleReportGames() { // we need to discuss what i presentr to the user?
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        Map<String, String> toServer = new HashMap<>();
        toServer.put("sid", String.valueOf(sessionid));
        toServer.put("username", userEmail);

        HashMap<String, String> response = restTemplate.postForObject(localhost + "getAllPossibleReportGames", toServer, HashMap.class);
        if (response.get("status").compareTo("fine") == 0) {

            response.remove("status");
            reportGames = response;
        }
        return response;

    }

    //input: referee user name
    //output: all possible games of for edit- important- i need amount here
    public HashMap<String, String> getGamesForEdit() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        Map<String, String> toServer = new HashMap<>();
        toServer.put("sid", String.valueOf(sessionid));
        toServer.put("username", userEmail);

        HashMap<String, String> response = restTemplate.postForObject(localhost + "getGamesForEdit", toServer, HashMap.class);
        if (response.get("status").compareTo("fine") == 0) {

            response.remove("status");
            editGames = response;
        }
        return response;


    }
    //input: game id
    //output: arraylist<event>
    public ArrayList<eventDetails> getGameEventsToEdit(String gamename) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        Map<String, String> toServer = new HashMap<>();
        toServer.put("sid", String.valueOf(sessionid));
        toServer.put("username", userEmail);
        toServer.put("game", getKeyByValue(editGames,gamename));
        isEditGame = gamename;
        ArrayList<eventDetails> response = restTemplate.postForObject(localhost + "getGameEventsToEdit", toServer, ArrayList.class);

        return response;


    }


    //----------------------------------------------HELPERS--------------------------
    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }




    public static class eventDetails{
        private String eventType;
        private String eventMinute;
        private String description;
        private String referee;
        private String _id;


        public eventDetails(String eventType, String eventMinute, String description, String referee, String id) {
            this.description=description;
            this.eventMinute=eventMinute;
            this.eventType=eventType;
            this.referee=referee;
            this._id = id;
        }
//        public String getGameID() {
//            return gameID;
//        }
//
//        public String getGameName() {
//            return gameName;
//        }

        public String getEventType() {
            return eventType;
        }

        public String getMinute() {
            return eventMinute;
        }

        public String getDescription() {
            return description;
        }

        public String getRefereeID() {
            return referee;
        }

        public String get_id() {
            return _id;
        }

        public void setEventType(String eventType) {
            this.eventType = eventType;
        }

        public void setMinute(String minute) {
            this.eventMinute = minute;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setRefereeID(String refereeID) {
            this.referee = refereeID;
        }
    }


}