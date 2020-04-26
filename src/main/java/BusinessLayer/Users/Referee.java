package BusinessLayer.Users;

import BusinessLayer.Enum.RefereeRole;
import BusinessLayer.Enum.RefereeTraining;
import BusinessLayer.Enum.UserStatus;
import DB.SystemController;
import BusinessLayer.Football.Game;
import BusinessLayer.Football.Season;

import java.util.HashMap;
import java.util.HashSet;

public class Referee extends SignedUser {
    private static int idCounter = 0;
    private int refereeID;

    private int id;
    private String email;
    private RefereeTraining refereeTraining;

    private HashMap<RefereeRole, HashSet<Game>> games;
    private HashSet<Season> seasons;

    // ====== Constructor ============
    public Referee(String userName, String hashPassword, int id, String fName, String lName, String email, RefereeTraining refereeTraining) {
        super(email, hashPassword, fName, lName, email);
        this.id = id;
        this.email = email;
        this.refereeTraining = refereeTraining;
        games = new HashMap<>();
        seasons = new HashSet<>();
        refereeID = idCounter++;

    }

    // ======== Getters and Setters ============

    public void addSeason(Season season) {
        seasons.add(season);
    }

    public void addGame(RefereeRole role, Game game) {
        if (role != null && game != null) {
            if (!games.containsKey(role)) {
                games.put(role, new HashSet<>());
            }
            games.get(role).add(game);
        }
    }

    public RefereeTraining getRefereeTraining() {
        return refereeTraining;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public HashSet<Season> getSeasons() {
        return seasons;
    }

    // ============ to String ===========
    @Override
    public String toString() {
        String string = super.toString();
        string += refereeTraining.toString();
        for (Season season : seasons) {
            string += season.toString();
        }
        return string;
    }

    @Override
    //TODO save the data
    public boolean deleteUser() throws Exception {
        long today = System.currentTimeMillis();
        for (RefereeRole role : games.keySet()) {
            for (Game game : games.get(role)) {
                if (game.getGameDate() >= today) {

                        SystemController.logger.error("Deletion | Can't Delete User; User ID: " + this.getId());
                        return false;

                }
            }
        }
        SystemController.removeUserFromActiveList(userName);
        seasons.forEach(season -> season.removeReferee(refereeTraining, this));
        this.changeStatus(UserStatus.NotActive);
        return true;
    }
}
