package Service;



import java.util.Observable;
import java.util.Observer;

public class ViewModel extends Observable implements Observer {
    Model myModel;

    public ViewModel(Model myModel) {
        this.myModel = myModel;

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
}