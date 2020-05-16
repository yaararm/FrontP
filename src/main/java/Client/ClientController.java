package Client;

import org.json.JSONObject;
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
    int userType=0;
    String userEmail ;
    String fullName;

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
    public HashMap<String,String> loginDetails(String password, String email) { // add Sessionid, type of  //map
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
        toServer.put("username",email);
        toServer.put("password",password);

        // build the request
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(toServer, headers);

        // send POST request
        HashMap<String,String> response = restTemplate.postForObject(localhost+"login/", toServer, HashMap.class);
        if (response.get("status").compareTo("fine")==0){
            sessionid = Integer.parseInt(response.get("sid"));
            userType = Integer.parseInt(response.get("type"));
            userEmail = response.get("username");
            fullName = response.get("firstname") +" "+response.get("lastname") ;
        }

        return response;


    }

    //input: map: email,password
    //output: map: status, ssesion id
    public HashMap<String,String> signUp(String first, String last, String email, String passwordEncryped) { //map
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
        toServer.put("first",first);
        toServer.put("last",last);
        toServer.put("username",email);
        toServer.put("password",passwordEncryped);

        // build the request
       // HttpEntity<Map<String, Object>> entity = new HttpEntity<>(toServer, headers);

        // send POST request
      //  ResponseEntity<HashMap> response = restTemplate.postForEntity(localhost+"register", entity, HashMap.class);
        // build the request
       // HttpEntity<Map<String, String>> entity = new HttpEntity<>(toServer, headers);
        JSONObject json = new JSONObject(toServer);
        System.out.println(json);
        //JSONPObject test = new JSONPObject(toServer);
        // send POST request
        HashMap<String,String> ans = restTemplate.postForObject(localhost+"register", json, HashMap.class);
        //restTemplate.postForObject(localhost+"register", json, HashMap.class);
        //restTemplate.postForObject(localhost+"register",toServer,HashMap.class);
        if (ans.get("status").compareTo("fine")==0){
            sessionid = Integer.parseInt(ans.get("sid"));
            userType = Integer.parseInt(ans.get("type"));
            userEmail = ans.get("username");
            fullName = ans.get("firstname") +" "+ans.get("lastname") ;
        }
        return ans;
    }

    //input:
    //output:
    public HashMap<String,String> logout() {
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
        toServer.put("sid",String.valueOf(sessionid));

        // build the request
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(toServer, headers);

        // send POST request
        HashMap<String,String> response = restTemplate.postForObject(localhost+"logout", toServer, HashMap.class);
        if (response.get("status").compareTo("fine")==0){
            sessionid = 0;
            userType = 0;
            userEmail = "";
            fullName = "" ;
        }
        return response;


    }

    //input: String: team name
    //output: String: status
    public String creatNewTeam(String teamName) {

        return "fine";
    }

    //input: map: leaguname' rtraining
    //output: String: status
    public String creatNewLeague(String leagueName, String rTraining) {
        return "fine";
    }

    //input:
    //output: all leagues name
    public ArrayList<String> getAllLeagues() {
        return null;
    }

    //input: String league name
    //output: all Seasons of that league
    public ArrayList<String> getAllSeasons(String leagueName) {
        return null;
    }

    //input:
    //output: all policies
    public ArrayList<String> getAllPolicies() {
            return null;
    }
    //input: leaguename, year, start date
    //output: Status;
    public String creatNewSeason(LocalDate date, String leaguename) {
      Date startDate = new Date(date.toEpochDay());
      int year = date.getYear();

        return "";
    }
    //input: league name, Season year, policy
    //output: status
    public String assignNewPolicy(String leagueName, String season, String policy) {
        return "";
    }

    //input: email
    //output: map: status, message
    public HashMap<String, String> checkForUpdates(String email) {
        return null;
    }

    //input: email
    //output: Teams of owner
    public ArrayList<String> getAllTeams(String email) {
        return null;
    }

    //input:
    //output:
    public String reportNewFinanceAction(String Team, String action, String amount, String description) {
        return "";
    }

    //input: referee user name
    //output: all future games
    public HashMap<String, String> getMyUpcomingsGames(String user_email) {
        return null;
    }
}