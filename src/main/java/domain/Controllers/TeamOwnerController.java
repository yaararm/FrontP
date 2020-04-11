package domain.Controllers;

import domain.Enums.CoachPosition;
import domain.Enums.FootballerPosition;
import domain.Enums.TeamManagerPermissions;
import domain.Enums.TeamState;
import domain.Impl.Field;
import domain.Impl.FinanceActivity;
import domain.Impl.Team;
import domain.Interfaces.Asset;
import domain.Users.*;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class TeamOwnerController {
    //Wasn't in UC
    public Footballer signUpNewFootballer(ManagementUser teamOwner, String firstName, String lastName, String email,
                                          FootballerPosition footballerPosition) throws Exception {
        boolean valid = EmailValidator.getInstance().isValid(email);
        if(!valid)
            throw new Exception("Not valid Email");

        String username = lastName+"_"+firstName;
        String password = lastName+"_"+firstName+"_123";

        if(SystemController.userNameUser.containsKey(username))
            throw new Exception("This user name already exist in the system");

        //TODO Send Email

        String hashPassword = Utils.sha256(password);

        Footballer footballer = new Footballer(username, hashPassword, firstName, lastName, email, footballerPosition);
        SystemController.userNameUser.put(username,footballer);
        return footballer;
    }

    public Coach signUpNewCoach(ManagementUser teamOwner, String firstName, String lastName, String email,
                                          CoachPosition coachPosition) throws Exception {
        boolean valid = EmailValidator.getInstance().isValid(email);
        if(!valid)
            throw new Exception("Not valid Email");

        String username = lastName+"_"+firstName;
        String password = lastName+"_"+firstName+"_123";

        if(SystemController.userNameUser.containsKey(username))
            throw new Exception("This user already exist in the system");

        //TODO Send Email

        String hashPassword = Utils.sha256(password);

        Coach coach = new Coach(username, hashPassword, firstName, lastName, email, coachPosition);
        SystemController.userNameUser.put(username,coach);
        return coach;
    }


    //UC 6.1
    public Team addNewTeamToSystem(ManagementUser teamOwner, String teamName) throws Exception {
        if(teamOwner instanceof Owner) {
            if (SystemController.systemTeams.stream().anyMatch(team -> team.getTeamName().equals(teamName)))
                throw new Exception("There is already team with the exact name");
            Team newTeam = new Team(teamName, TeamState.notActive);
            boolean newTeamAuthorization = ExternalServices.getNewTeamAuthorization(newTeam);
            if (!newTeamAuthorization)
                throw new Exception("There is already team with the exact name");
            newTeam.setStatus(TeamState.active);
            newTeam.addTeamMember(null,teamOwner);
            return newTeam;
        }
        else
            throw new Exception("The user doesn't have permissions for this one");
    }

    //UC 6.1.1
    public boolean addFieldToTeam(ManagementUser managementUser, Team team, Field field) throws Exception {
        if(managementUser instanceof Owner || (managementUser instanceof TeamManager && ((TeamManager)managementUser).hasPermission(TeamManagerPermissions.AddAsset))) {
            team.addField(field);
            return true;
        }
        else
            throw new Exception("The user doesn't have permissions for this one");
    }

    public boolean addMemberToTeam(ManagementUser managementUser, Team team, TeamUser teamUser) throws Exception {
        if(managementUser instanceof Owner || (managementUser instanceof TeamManager && ((TeamManager)managementUser).hasPermission(TeamManagerPermissions.AddAsset))) {
            team.addTeamMember(managementUser,teamUser);
            return true;
        }
        else
            throw new Exception("The user doesn't have permissions for this one");
    }

    //UC 6.1.2
    public boolean removeFieldFromTeam(ManagementUser managementUser, Team team, Field field) throws Exception {
        if(managementUser instanceof Owner || (managementUser instanceof TeamManager && ((TeamManager)managementUser).hasPermission(TeamManagerPermissions.RemoveAsset))) {
            team.removeField(field);
            return true;
        }
        else
            throw new Exception("The user doesn't have permissions for this one");
    }

    public boolean removeMemberFromTeam(ManagementUser managementUser, Team team, TeamUser teamUser) throws Exception {
        if(managementUser instanceof Owner || (managementUser instanceof TeamManager && ((TeamManager)managementUser).hasPermission(TeamManagerPermissions.AddAsset))) {
            team.removeTeamMember(managementUser,teamUser);
            return true;
        }
        else
            throw new Exception("The user doesn't have permissions for this one");
    }

    //UC 6.1.3
    public boolean editAssetOfTeam(ManagementUser managementUser, Asset asset, HashMap<String, String> changes) throws Exception {
        if(managementUser instanceof Owner || (managementUser instanceof TeamManager && ((TeamManager)managementUser).hasPermission(TeamManagerPermissions.EditAsset))) {
            return asset.editAsset(changes);
        }
        else
            throw new Exception("The user doesn't have permissions for this one");
    }

    //UC 6.2
    public boolean addUserAsTeamOwner(ManagementUser addingOwner, Team team, SignedUser newOwner) throws Exception {
        if(addingOwner instanceof Owner || (addingOwner instanceof TeamManager && ((TeamManager)addingOwner).hasPermission(TeamManagerPermissions.AddOwner))) {
            HashSet<Owner> teamOwners = team.getTeamOwners();
            if (teamOwners.stream().anyMatch(owner -> owner == newOwner))
                throw new Exception("The user is already defined as team owner");
            team.addTeamMember(addingOwner,newOwner);
            //TODO this user need to be changed to Owner
            addingOwner.addOwner(team, (Owner) newOwner);
            return true;
        }
        else
            throw new Exception("The user doesn't have permissions for this one");
    }

    //UC 6.3
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

    //UC 6.4
    public boolean addUserAsTeamManager(ManagementUser addingOwner, Team team, SignedUser newManager) throws Exception {
        if(addingOwner instanceof Owner || (addingOwner instanceof TeamManager && ((TeamManager)addingOwner).hasPermission(TeamManagerPermissions.AddManager))) {
            HashSet<Owner> teamOwners = team.getTeamOwners();
            HashSet<TeamManager> teamManagers = team.getTeamManagers();
            if (teamManagers.stream().anyMatch(teamManager -> teamManager == newManager) || teamOwners.stream().anyMatch(owner -> owner == newManager))
                throw new Exception("The user is already defined as team owner or manager");
            team.addTeamMember(addingOwner,newManager);
            //TODO this user need to be changed to Owner
            addingOwner.addTeamManager(team, (TeamManager) newManager);
            return true;
        }
        else
            throw new Exception("The user doesn't have permissions for this one");
    }

    //UC 6.5
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

    //UC 6.6.1
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

    //UC 6.6.2
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

    //UC 6.7
    public boolean addFinanceAction(ManagementUser managementUser, Team team, String kind, double amount, String description, long date,
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
