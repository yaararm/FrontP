package domain.Impl;

import domain.Users.*;

import java.util.HashSet;

public class Members {
    private HashSet<Footballer> footballers;
    private HashSet<Coach> coaches;
    private HashSet<Owner> owners;
    private HashSet<TeamManager> teamManagers;

    public Members() {
        this.footballers = new HashSet<>();
        this.coaches = new HashSet<>();
        this.owners = new HashSet<>();
        this.teamManagers = new HashSet<>();
    }

    public boolean addMember(SignedUser userToAdd) throws Exception {
        if(userToAdd instanceof Footballer){
            footballers.add((Footballer) userToAdd);
        } else if (userToAdd instanceof Coach){
            coaches.add((Coach) userToAdd);
        } else if (userToAdd instanceof Owner){
            owners.add((Owner) userToAdd);
        } else if (userToAdd instanceof TeamManager){
            teamManagers.add((TeamManager) userToAdd);
        } else {
            throw new Exception("Team members can be only Footballer, Coach, Owner, TeamManager");
        }
        return true;
    }

    public boolean removeMember(SignedUser userToRemove) throws Exception {
        boolean removed;
        if(userToRemove instanceof Footballer){
            removed = footballers.remove((Footballer) userToRemove);
        } else if (userToRemove instanceof Coach){
            removed = coaches.remove((Coach) userToRemove);
        } else if (userToRemove instanceof Owner){
            removed = owners.remove((Owner) userToRemove);
        } else if (userToRemove instanceof TeamManager){
            removed = teamManagers.remove((TeamManager) userToRemove);
        } else {
            throw new Exception("Team members can be only Footballer, Coach, Owner, TeamManager");
        }
        if (!removed){
            throw new Exception("This user is not related to the team");
        }
        return removed;
    }

    public HashSet<Footballer> getFootballers() {
        return footballers;
    }

    public HashSet<Coach> getCoaches() {
        return coaches;
    }

    public HashSet<Owner> getOwners() {
        return owners;
    }

    public HashSet<TeamManager> getTeamManagers() {
        return teamManagers;
    }
}
