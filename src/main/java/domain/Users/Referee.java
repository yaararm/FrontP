package domain.Users;

import domain.Enums.RefereeRole;
import domain.Enums.RefereeTraining;
import domain.Enums.UserStatus;
import domain.Impl.Game;
import domain.Impl.Season;
import domain.Controllers.SystemController;

import java.util.*;

public class Referee extends SignedUser {
    private static int idCounter = 0;
    private int refereeID;

    private int id;
    private String email;
    private RefereeTraining refereeTraining;

    private HashMap<RefereeRole, HashSet<Game>> games;
    private HashSet<Season> seasons;


    public Referee(String userName, String hashPassword, int id, String fName, String lName, String email, RefereeTraining refereeTraining) {
        super(email, hashPassword, fName, lName, email);
        this.id=id;
        this.email=email;
        this.refereeTraining=refereeTraining;
        games = new HashMap<>();
        seasons =new HashSet<>();
        refereeID = idCounter++;

    }

    @Override
    //TODO save the data
    public boolean deleteUser() {
        long today = System.currentTimeMillis();
        for (RefereeRole role: games.keySet()) {
            for (Game game: games.get(role)) {
                if(game.getGameDate()>=today){
                    if(!game.removeReferee(this, role)){
                        //TODO Should we throw exception here?
                    }
                }
            }
        }
        SystemController.removeUserFromActiveList(userName);
        seasons.forEach(season -> season.removeReferee(refereeTraining, this));
        this.changeStatus(UserStatus.NotActive);
        return true;
    }

    public RefereeTraining getRefereeTraining() {
        return refereeTraining;
    }

    public void addSeason(Season season) {
        seasons.add(season);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRefereeID() {
        return refereeID;
    }

    public int getId() {
        return id;
    }

    public HashMap<RefereeRole, HashSet<Game>> getGames() {
        return games;
    }

    @Override
    public String toString() {
        String string = super.toString();
        string += refereeTraining.toString();
        for (Season season : seasons) {
            string += season.toString();
        }
        return string;
    }
}
