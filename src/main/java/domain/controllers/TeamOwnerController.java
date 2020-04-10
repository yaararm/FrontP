package domain.controllers;

import domain.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class TeamOwnerController {

    public Team addNewTeamToSystem(String teamName, Owner teamOwner) throws Exception {
        if(SystemController.systemTeams.stream().anyMatch(team -> team.getTeamName().equals(teamName)))
            throw new Exception("There is already team with the exact name");
        Team newTeam = new Team(teamName, TeamState.notActive);
        boolean newTeamAuthorization = ExternalServices.getNewTeamAuthorization(newTeam);
        if(!newTeamAuthorization)
            throw new Exception("There is already team with the exact name");
        newTeam.setStatus(TeamState.active);
        newTeam.addTeamMember(teamOwner);
        return newTeam;
    }

    public boolean addAssetToTeam(Team team, Asset asset) throws Exception {
        team.addAsset(asset);
        return true;
    }

    public boolean removeAssetFromTeam(Team team, Asset asset) throws Exception {
        team.removeAsset(asset);
        return true;
    }

    public boolean editAssetOfTeam(Asset asset, HashMap<String, String> changes){
        return asset.editAsset(changes);
    }

    public boolean addUserAsTeamOwner(Owner addingOwner, Team team, SignedUser newOwner) throws Exception {
        HashSet<Owner> teamOwners = team.getTeamOwners();
        if(teamOwners.stream().anyMatch(owner -> owner == newOwner))
            throw new Exception("The user is already defined as team owner");
        team.addTeamMember(newOwner);
        //TODO this user need to be changed to Owner
        addingOwner.addOwner(team, (Owner) newOwner);
        return true;
    }

    public boolean removeTeamOwner(Owner removingOwner, Team team, Owner ownerToRemove) throws Exception {
        HashSet<Owner> teamOwners = team.getTeamOwners();
        if(teamOwners.stream().anyMatch(owner -> owner == ownerToRemove)) {
            team.removeTeamMember(ownerToRemove);
            removingOwner.removeOwner(team,ownerToRemove);
            HashMap<Team, HashSet<Owner>> assignedOwners = ownerToRemove.getAssignedOwners();
            HashMap<Team, HashSet<TeamManager>> assignedTeamManagers = ownerToRemove.getAssignedTeamManagers();
            for (Map.Entry<Team, HashSet<Owner>> teamHashSetEntry : assignedOwners.entrySet()) {
                for (Owner owner : teamHashSetEntry.getValue()) {
                    removeTeamOwner(ownerToRemove, teamHashSetEntry.getKey(), owner);
                }
            }
            for (Map.Entry<Team, HashSet<TeamManager>> teamHashSetEntry : assignedTeamManagers.entrySet()) {
                for (TeamManager teamManager : teamHashSetEntry.getValue()) {
                    removeTeamManager(ownerToRemove, teamHashSetEntry.getKey(),teamManager);
                }
            }

        }
        else{
            throw new Exception("The select user is not team Owner");
        }
        //TODO this user need to be changed if he is not owner anymore
        //TODO send alerts to the removed
        return true;
    }

    public boolean addUserAsTeamManager(Owner addingOwner, Team team, SignedUser newManager) throws Exception {
        HashSet<Owner> teamOwners = team.getTeamOwners();
        HashSet<TeamManager> teamManagers = team.getTeamManagers();
        if(teamManagers.stream().anyMatch(teamManager -> teamManager == newManager) || teamOwners.stream().anyMatch(owner -> owner == newManager))
            throw new Exception("The user is already defined as team owner or manager");
        team.addTeamMember(newManager);
        //TODO this user need to be changed to Owner
        addingOwner.addTeamManager(team, (TeamManager) newManager);
        return true;
    }

    private boolean removeTeamManager(Owner removingOwner, Team team, TeamManager managerToRemove) throws Exception {
        HashSet<TeamManager> teamManagers = team.getTeamManagers();
        if(teamManagers.stream().anyMatch(teamManager -> teamManager == managerToRemove)) {
            team.removeTeamMember(managerToRemove);
            removingOwner.removeTeamManager(team,managerToRemove);
            //TODO this user need to be changed if he is not owner anymore
            //TODO send alerts to the removed
        }
        else{
            throw new Exception("The select user is not team manager");
        }
        return true;
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
