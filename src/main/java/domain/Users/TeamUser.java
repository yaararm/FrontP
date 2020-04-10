package domain.Users;

import domain.Impl.Team;

import java.util.HashMap;

public abstract class TeamUser extends SignedUser {
    protected HashMap<Team,ManagementUser> teams;
    public TeamUser(String username, String password) {
        super(username, password);
        teams = new HashMap<>();
    }

    public boolean addTeam(Team team, ManagementUser managementUser){
        teams.put(team, managementUser);
        return true;
    }

    public boolean removeTeam(Team team){
        teams.remove(team);
        return true;
    }
}
