package domain.Users;

import domain.Controllers.SystemController;
import domain.Controllers.TeamOwnerController;
import domain.Controllers.Utils;
import domain.Enums.TeamManagerPermissions;
import domain.Impl.Team;
import domain.Interfaces.Asset;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class TeamManager extends ManagementUser implements Asset {
    private EnumMap<TeamManagerPermissions, Boolean> permissions;

    public TeamManager(String username, String hashPassword, String firstName, String lastName, String email) {
        super(username, hashPassword, firstName, lastName, email);
        this.permissions = new EnumMap<TeamManagerPermissions, Boolean>(TeamManagerPermissions.class);
        for (TeamManagerPermissions value : TeamManagerPermissions.values()) {
            permissions.put(value, false);
        }
    }

    @Override
    public boolean deleteUser() throws Exception {
        for (Team team : this.teams.keySet()) {
            team.removeTeamMember(this);
        }
        for (Map.Entry<Team, HashSet<Owner>> teamHashSetEntry : assignedOwners.entrySet()) {
            for (Owner owner : teamHashSetEntry.getValue()) {
                TeamOwnerController.removeTeamOwner(this, teamHashSetEntry.getKey(), owner);
            }
        }
        for (Map.Entry<Team, HashSet<TeamManager>> teamHashSetEntry : assignedTeamManagers.entrySet()) {
            for (TeamManager teamManager : teamHashSetEntry.getValue()) {
                TeamOwnerController.removeTeamManager(this, teamHashSetEntry.getKey(), teamManager);
            }
        }
        SystemController.archiveUsers.put(this.getUserName(),this);
        SystemController.userNameUser.remove(this);
        return true;
    }

    public boolean addPermissions(TeamManagerPermissions ... teamManagerPermissions){
        for (TeamManagerPermissions teamManagerPermission : teamManagerPermissions) {
            permissions.put(teamManagerPermission, true);
            System.currentTimeMillis();
        }
        return true;
    }

    public boolean removePermissions(TeamManagerPermissions ... teamManagerPermissions){
        for (TeamManagerPermissions teamManagerPermission : teamManagerPermissions) {
            permissions.put(teamManagerPermission, false);
        }
        return true;
    }

    public boolean changePermissions(EnumMap<TeamManagerPermissions, Boolean> permissions){
        for (Map.Entry<TeamManagerPermissions, Boolean> teamManagerPermissionsBooleanEntry : permissions.entrySet()) {
            this.permissions.put(teamManagerPermissionsBooleanEntry.getKey(),teamManagerPermissionsBooleanEntry.getValue());
        }
        return true;
    }

    public boolean hasPermission(TeamManagerPermissions permission){
        return permissions.get(permission);
    }

    @Override
    public boolean editAsset(HashMap<String, String> changes) throws Exception {
        for (Map.Entry<String, String> entry : changes.entrySet()) {
            switch (entry.getKey().toLowerCase()) {
                case "email":
                    this.email = entry.getValue();
                    break;
                case "firstname":
                    this.firstName = entry.getValue();
                    break;
                case "lastname":
                    this.lastName = entry.getValue();
                    break;
                case "password":
                    this.password = Utils.sha256(entry.getValue());
                    break;
            }

        }
        return true;
    }
}
