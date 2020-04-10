package domain.Users;

import domain.Enums.TeamManagerPermissions;

import java.util.EnumMap;

public class TeamManager extends ManagementUser {
    private EnumMap<TeamManagerPermissions, Boolean> permissions;

    public TeamManager(String username, String password) {
        super(username, password);
        this.permissions = new EnumMap<TeamManagerPermissions, Boolean>(TeamManagerPermissions.class);
        for (TeamManagerPermissions value : TeamManagerPermissions.values()) {
            permissions.put(value, false);
        }
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
