package domain.controllers;

import domain.*;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class TeamOwnerController {

    public Team addNewTeamToSystem(ManagementUser managementUser, String teamName, Owner teamOwner) throws Exception {
        if(managementUser instanceof Owner) {
            if (SystemController.systemTeams.stream().anyMatch(team -> team.getTeamName().equals(teamName)))
                throw new Exception("There is already team with the exact name");
            Team newTeam = new Team(teamName, TeamState.notActive);
            boolean newTeamAuthorization = ExternalServices.getNewTeamAuthorization(newTeam);
            if (!newTeamAuthorization)
                throw new Exception("There is already team with the exact name");
            newTeam.setStatus(TeamState.active);
            newTeam.addTeamMember(teamOwner);
            return newTeam;
        }
        else
            throw new Exception("The user doesn't have permissions for this one");
    }

    public boolean addAssetToTeam(ManagementUser managementUser, Team team, Asset asset) throws Exception {
        if(managementUser instanceof Owner || (managementUser instanceof TeamManager && ((TeamManager)managementUser).hasPermission(TeamManagerPermissions.AddAsset))) {
            team.addAsset(asset);
            return true;
        }
        else
            throw new Exception("The user doesn't have permissions for this one");
    }

    public boolean removeAssetFromTeam(ManagementUser managementUser, Team team, Asset asset) throws Exception {
        if(managementUser instanceof Owner || (managementUser instanceof TeamManager && ((TeamManager)managementUser).hasPermission(TeamManagerPermissions.RemoveAsset))) {
            team.removeAsset(asset);
            return true;
        }
        else
            throw new Exception("The user doesn't have permissions for this one");
    }

    public boolean editAssetOfTeam(ManagementUser managementUser, Asset asset, HashMap<String, String> changes) throws Exception {
        if(managementUser instanceof Owner || (managementUser instanceof TeamManager && ((TeamManager)managementUser).hasPermission(TeamManagerPermissions.EditAsset))) {
            return asset.editAsset(changes);
        }
        else
            throw new Exception("The user doesn't have permissions for this one");
    }

    public boolean addUserAsTeamOwner(ManagementUser addingOwner, Team team, SignedUser newOwner) throws Exception {
        if(addingOwner instanceof Owner || (addingOwner instanceof TeamManager && ((TeamManager)addingOwner).hasPermission(TeamManagerPermissions.AddOwner))) {
            HashSet<Owner> teamOwners = team.getTeamOwners();
            if (teamOwners.stream().anyMatch(owner -> owner == newOwner))
                throw new Exception("The user is already defined as team owner");
            team.addTeamMember(newOwner);
            //TODO this user need to be changed to Owner
            addingOwner.addOwner(team, (Owner) newOwner);
            return true;
        }
        else
            throw new Exception("The user doesn't have permissions for this one");
    }

    public boolean removeTeamOwner(ManagementUser removingOwner, Team team, Owner ownerToRemove) throws Exception {
        if(removingOwner instanceof Owner || (removingOwner instanceof TeamManager && ((TeamManager)removingOwner).hasPermission(TeamManagerPermissions.RemoveOwner))) {
            HashSet<Owner> teamOwners = team.getTeamOwners();
            if (teamOwners.stream().anyMatch(owner -> owner == ownerToRemove)) {
                team.removeTeamMember(ownerToRemove);
                removingOwner.removeOwner(team, ownerToRemove);
                HashMap<Team, HashSet<Owner>> assignedOwners = ownerToRemove.getAssignedOwners();
                HashMap<Team, HashSet<TeamManager>> assignedTeamManagers = ownerToRemove.getAssignedTeamManagers();
                for (Map.Entry<Team, HashSet<Owner>> teamHashSetEntry : assignedOwners.entrySet()) {
                    for (Owner owner : teamHashSetEntry.getValue()) {
                        removeTeamOwner(ownerToRemove, teamHashSetEntry.getKey(), owner);
                    }
                }
                for (Map.Entry<Team, HashSet<TeamManager>> teamHashSetEntry : assignedTeamManagers.entrySet()) {
                    for (TeamManager teamManager : teamHashSetEntry.getValue()) {
                        removeTeamManager(ownerToRemove, teamHashSetEntry.getKey(), teamManager);
                    }
                }

            } else {
                throw new Exception("The select user is not team Owner");
            }
            //TODO this user need to be changed if he is not owner anymore
            //TODO send alerts to the removed
            return true;
        }
        else
            throw new Exception("The user doesn't have permissions for this one");
    }

    public boolean addUserAsTeamManager(ManagementUser addingOwner, Team team, SignedUser newManager) throws Exception {
        if(addingOwner instanceof Owner || (addingOwner instanceof TeamManager && ((TeamManager)addingOwner).hasPermission(TeamManagerPermissions.AddManager))) {
            HashSet<Owner> teamOwners = team.getTeamOwners();
            HashSet<TeamManager> teamManagers = team.getTeamManagers();
            if (teamManagers.stream().anyMatch(teamManager -> teamManager == newManager) || teamOwners.stream().anyMatch(owner -> owner == newManager))
                throw new Exception("The user is already defined as team owner or manager");
            team.addTeamMember(newManager);
            //TODO this user need to be changed to Owner
            addingOwner.addTeamManager(team, (TeamManager) newManager);
            return true;
        }
        else
            throw new Exception("The user doesn't have permissions for this one");
    }

    private boolean removeTeamManager(ManagementUser removingOwner, Team team, TeamManager managerToRemove) throws Exception {
        if(removingOwner instanceof Owner || (removingOwner instanceof TeamManager && ((TeamManager)removingOwner).hasPermission(TeamManagerPermissions.RemoveManager))) {
            HashSet<TeamManager> teamManagers = team.getTeamManagers();
            if (teamManagers.stream().anyMatch(teamManager -> teamManager == managerToRemove)) {
                team.removeTeamMember(managerToRemove);
                removingOwner.removeTeamManager(team, managerToRemove);
                //TODO this user need to be changed if he is not owner anymore
                //TODO send alerts to the removed
            } else {
                throw new Exception("The select user is not team manager");
            }
            return true;
        }
        else
            throw new Exception("The user doesn't have permissions for this one");
    }

    public boolean closeTeam(ManagementUser managementUser, Team team) throws Exception {
        if(managementUser instanceof Owner || (managementUser instanceof TeamManager && ((TeamManager)managementUser).hasPermission(TeamManagerPermissions.CloseTeam))) {
            if (team.getState() == TeamState.active) {
                team.setStatus(TeamState.notActive);
                //Todo send alerts
                //todo save data on team
            } else {
                throw new Exception("This team is already closed");
            }
            return true;
        }
        else
            throw new Exception("The user doesn't have permissions for this one");
    }

    public boolean openTeam(ManagementUser managementUser, Team team) throws Exception {
        if(managementUser instanceof Owner || (managementUser instanceof TeamManager && ((TeamManager)managementUser).hasPermission(TeamManagerPermissions.OpenTeam))) {
            TeamState state = team.getState();
            if (state == TeamState.notActive) {
                team.setStatus(TeamState.active);
                //Todo send alerts
            } else {
                if (state == TeamState.active)
                    throw new Exception("This team is already active");
                else if (state == TeamState.permanentlyClosed)
                    throw new Exception("This team is permanently closed, please contact the system manager");
            }
            return true;
        }
        else
            throw new Exception("The user doesn't have permissions for this one");
    }

    public boolean addFinanceAction(ManagementUser managementUser, Team team, String kind, double amount, String description, Date date,
                                    ManagementUser reporter) throws Exception {
        if(managementUser instanceof Owner || (managementUser instanceof TeamManager && ((TeamManager)managementUser).hasPermission(TeamManagerPermissions.EditAsset))) {
            FinanceActivity financeActivity = new FinanceActivity(kind,amount,description,date,reporter);
            team.addFinanceActivity(financeActivity);
            return true;
        }
        else
            throw new Exception("The user doesn't have permissions for this one");
    }

}
