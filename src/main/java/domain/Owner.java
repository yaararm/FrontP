package domain;

import java.util.HashMap;
import java.util.HashSet;

public class Owner extends SignedUser {
    private SignedUser additionalRole;
    private HashMap<Team, HashSet<Owner>> assignedOwners;
    private HashMap<Team, HashSet<TeamManager>> assignedTeamManagers;

    public Owner(String username, String password) {
        super(username, password);
        this.assignedOwners = new HashMap<>();
        this.assignedTeamManagers = new HashMap<>();
    }

    public Owner(SignedUser signedUser) throws Exception {
        //TODO check constructors
        this(signedUser.getUserName(),signedUser.getPassword());
        if (!(additionalRole instanceof Footballer || additionalRole instanceof Coach || additionalRole instanceof TeamManager))
            throw new Exception("Only the following combinations are allowed: teamOwner and (Footballer or Coach or TeamManager)");
        this.additionalRole = signedUser;
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

    public SignedUser getAdditionalRole() {
        return additionalRole;
    }

    public String additionalRole(){
        return this.additionalRole.getClass().getSimpleName();
    }

    public void setAdditionalRole(SignedUser additionalRole) throws Exception {
        if (!(additionalRole instanceof Footballer || additionalRole instanceof Coach || additionalRole instanceof TeamManager))
            throw new Exception("Only the following combinations are allowed: teamOwner and (Footballer or Coach or TeamManager)");
        this.additionalRole = additionalRole;
    }
}
