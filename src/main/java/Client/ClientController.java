package Client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.util.Observable;
import java.util.Observer;


public class ClientController extends Observable implements Observer {

    RestTemplate template;
    HttpHeaders requestHeaders;
    HttpEntity<?> requestEntity;


    public ClientController() {


    }

    @Override
    public void update(Observable o, Object arg) {


    }

    public boolean loginDetails(String password, String username) {
        return false;
    }

    public String getUserName(String email) {
        return "";
    }

    public String signUp(String first, String last, String email, String password) {
        return "fine";
        // if there is exception- return the text of it
    }

    public void logout() {
        // do logout
    }

    public int getUserInstance(String email) {
        // return the number of the users
        //1-owner
        //2-referee
        //3-ARP
        //4-
        //5-
        return 1;


    }

    public boolean creatNewTeam(String teamName) {

        return true;
    }

    public String creatNewLeague(String leagueName, String rTraining) {
        return "fine";
    }
}