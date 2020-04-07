package domain;

import java.util.HashMap;
import java.util.HashSet;

public class Owner extends SignedUser {
    public Owner(String username, String password) {
        super(username, password);
      
    private HashMap<Team, HashSet<Owner>> assignedOwners;
    private HashMap<Team, HashSet<TeamManager>> assignedTeamManagers;

    public Owner() {
        this.assignedOwners = new HashMap<>();
    }

    public boolean addOwner(Team team, Owner owner) throws Exception {
        HashSet<Owner> owners = this.assignedOwners.get(team);
        if(owners != null){
            if(owners.contains(owner))
                throw new Exception("The user is already defined as team owner");
            owners.add(owner);
            assignedOwners.put(team,owners);
        }
        else{
            HashSet<Owner> myOwners = new HashSet<>();
            myOwners.add(owner);
            assignedOwners.put(team,myOwners);
        }
        return true;
    }

    public boolean removeOwner(Team team, Owner owner) throws Exception {
        HashSet<Owner> owners = this.assignedOwners.get(team);
        if(owners == null || !owners.contains(owner)){
            throw new Exception("The user isn't assigned by this team owner");
        }
        else{
            owners.remove(owner);
            assignedOwners.put(team,owners);
        }
        return true;
    }


    public boolean addTeamManager(Team team, TeamManager teamManager) throws Exception {
        HashSet<TeamManager> teamManagers = this.assignedTeamManagers.get(team);
        if(teamManagers != null){
            if(teamManagers.contains(teamManager))
                throw new Exception("The user is already defined as team manager");
            teamManagers.add(teamManager);
            assignedTeamManagers.put(team,teamManagers);
        }
        else{
            HashSet<TeamManager> myTeamManagers = new HashSet<>();
            myTeamManagers.add(teamManager);
            assignedTeamManagers.put(team,myTeamManagers);
        }
        return true;
    }


    public boolean removeTeamManager(Team team, TeamManager teamManager) throws Exception {
        HashSet<TeamManager> teamManagers = this.assignedTeamManagers.get(team);
        if(teamManagers == null || !teamManagers.contains(teamManager)){
            throw new Exception("The user isn't assigned by this team owner");
        }
        else{
            teamManagers.remove(teamManager);
            assignedTeamManagers.put(team,teamManagers);
        }
        return true;
    }

    public HashMap<Team, HashSet<Owner>> getAssignedOwners() {
        return assignedOwners;
    }

    public HashMap<Team, HashSet<TeamManager>> getAssignedTeamManagers() {
        return assignedTeamManagers;
    }

    public boolean closeTeam(Team team) throws Exception {
        if(team.getState() == TeamState.active){
            team.setStatus(TeamState.notActive);
            //Todo send alerts
            //todo save data on team
        }
        else{
            throw new Exception("This team is already closed");
        }
        return true;
    }

    public boolean openTeam(Team team) throws Exception {
        TeamState state = team.getState();
        if(state == TeamState.notActive){
            team.setStatus(TeamState.active);
            //Todo send alerts
        }
        else{
            if(state == TeamState.active)
                throw new Exception("This team is already active");
            else if(state == TeamState.permanentlyClosed)
                throw new Exception("This team is permanently closed, please contact the system manager");
        }
        return true;
    }

    public boolean addFinanceAction(Team team){
        //todo what this should contain??
        return false;

    }
}
