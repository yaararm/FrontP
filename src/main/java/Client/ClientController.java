package Client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;


public class ClientController extends Observable implements Observer {

    HttpHeaders httpHeaders;
    RestTemplate restTemplate;
    HttpEntity<?> httpEntity;
    String localhost;
    int Sessionid;

    public ClientController() {
        httpHeaders = new HttpHeaders();
        httpHeaders.set("headerName","value");
        restTemplate = new RestTemplate();
        httpEntity = new HttpEntity<>(httpHeaders);
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
    //4-
    //5-
    public HashMap<String,String> loginDetails(String password, String email) { // add Sessionid, type of  //map
        HashMap <String,String> toServer = new HashMap<>();
       toServer.put("email",email);
       toServer.put("password",password);
       // HttpEntity<String> req = new HttpEntity<T>(toServer);
       // ResponseEntity<HashMap<String,String>> respond = restTemplate.exchange(localhost+"Login", HttpMethod.POST,req,String.class);
        return null;
    }

    //input: String: email
    //output: String: user name
     public String getUserFullName(String email) { //
        HttpEntity<String> req = new HttpEntity<>(email);
        ResponseEntity<String> respond = restTemplate.exchange(localhost+"getUserFullName", HttpMethod.GET,req,String.class);
        return respond.getBody();
    }

    //input: map: email,password
    //output: map: status, ssesion id
    public String signUp(String first, String last, String email, String passwordEncryped) { //map

        HttpEntity<String> req = new HttpEntity<>(first+","+last+","+email+ "," + passwordEncryped );
        ResponseEntity<String> respond = restTemplate.exchange(localhost+"Login", HttpMethod.POST,req,String.class);
        return respond.getBody();
        // if there is exception- return the text of it
    }

    //input:
    //output:
    public void logout() {
        // do logout
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