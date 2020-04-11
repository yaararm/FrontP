package domain.Users;

import domain.Controllers.Utils;
import domain.Enums.TeamManagerPermissions;
import domain.Interfaces.Asset;

import java.util.EnumMap;
import java.util.HashMap;
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
    public boolean deleteUser() {

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
