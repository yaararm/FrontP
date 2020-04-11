package domain.Users;

import domain.Enums.TeamManagerPermissions;
import domain.Interfaces.Asset;

import java.util.EnumMap;

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
    public boolean deleteUser() {
        return false;
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

    public boolean hasPermission(TeamManagerPermissions permission){
        return permissions.get(permission);
    }
}
