package Client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;


public class ClientController extends Observable implements Observer {

    RestTemplate restTemplate;
    String localhost;
    int sessionid;
    int userType = 0;
    String userEmail;
    String fullName;
    HashMap<String, String> leagues;
    HashMap<String, String> seasons;
    HashMap<String, String> policies;
    HashMap<String, String> Teams;

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

        // build the request
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(toServer, headers);

        // send POST request
        HashMap<String, String> response = restTemplate.postForObject(localhost + "login/", toServer, HashMap.class);
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
        toServer.put("username", email);
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

        // build the request
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(toServer, headers);

        // send POST request
        HashMap<String, String> response = restTemplate.postForObject(localhost + "logout", toServer, HashMap.class);
        if (response.get("status").compareTo("fine") == 0) {
            sessionid = 0;
            userType = 0;
            userEmail = "";
            fullName = "";
            leagues.clear();
            seasons.clear();
            Teams.clear();

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
        toServer.put("teamname", leagueName);
        toServer.put("RefereeTraining", rTraining);

        HashMap<String, String> response = restTemplate.postForObject(localhost + "creatNewLeague", toServer, HashMap.class);
        return response;
    }


    public HashMap <String, String> creatNewSeason(LocalDate date, String leaguename) {
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
        toServer.put("leagueID", getKeyByValue(leagues,leaguename));
        toServer.put("year", String.valueOf(year));
        toServer.put("Day",String.valueOf(day));
        toServer.put("month",String.valueOf(month));
        HashMap<String, String> response = restTemplate.postForObject(localhost + "creatNewSeason", toServer, HashMap.class);
        return response;

    }

    //input: league name, Season year, policy
    //output: status
    public  HashMap<String, String> assignNewPolicy(String leagueName, String season, String policy) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        Map<String, String> toServer = new HashMap<>();
        toServer.put("sid", String.valueOf(sessionid));
        toServer.put("leagueID", getKeyByValue(leagues,leagueName));
        toServer.put("seasonID", getKeyByValue(seasons,season));
        toServer.put("policy", policy);
        HashMap<String, String> response = restTemplate.postForObject(localhost + "assignNewPolicy", toServer, HashMap.class);
        return response;
    }

    //-----------------------------------Fan------------------------------------

    //input: email
    //output: map: status, message
    public HashMap<String, String> checkForUpdates() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        Map<String, String> toServer = new HashMap<>();
        toServer.put("sid", String.valueOf(sessionid));
        HashMap<String, String> response = restTemplate.postForObject(localhost + "checkForUpdates", toServer, HashMap.class);
        return response;

    }

    //input: email
    //output: Teams of owner


    //-----------------------------------referee-------------------------
    //input: referee user name
    //output: all future games
    public HashMap<String, String> getMyUpcomingsGames(String user_email) {
        return null;
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
    public ArrayList<String> getAllPolicies() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        Map<String, String> toServer = new HashMap<>();
        toServer.put("sid", String.valueOf(sessionid));
        //toServer.put("leaugueID",getKeyByValue(leagues,leagueName));

        ArrayList<String> response = restTemplate.postForObject(localhost + "getAllPolicies", toServer, ArrayList.class);
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

    //----------------------------------------------HELPERS--------------------------
    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}