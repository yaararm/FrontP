package domain;

import domain.controllers.SystemController;

import java.util.*;

public class Referee extends SignedUser {
    Integer id;
    String firstName;
    String lastName;
    String email;
    RefereeTraining refereeTraining;

    HashMap<RefereeRole, HashSet<Game>> games;
    HashSet<Season> seasons;


    public Referee(String userName, String hashPassword, Integer id, String fName, String lName, String email, RefereeTraining refereeTraining) {
        super(userName, hashPassword);
        this.id=id;
        this.firstName=fName;
        this.lastName=lName;
        this.email=email;
        this.refereeTraining=refereeTraining;
        games = new HashMap<>();
        seasons =new HashSet<>();

    }

    @Override
    //TODO save the data
    public boolean deleteUser() {
        long today = System.currentTimeMillis();
        for (RefereeRole role: games.keySet()) {
            for (Game game: games.get(role)) {
                if(!game.gameDate.before(today)){
                    if(!game.removeReferee(this, role)){
                        //TODO Should we throw exception here?
                    }
                }
            }
        }
        SystemController.IDReferees.remove(this.id);
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


    public HashMap<RefereeRole, HashSet<Game>> getGames() {
        return games;
    }


}
