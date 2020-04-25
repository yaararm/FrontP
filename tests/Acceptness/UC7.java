package Acceptness;

import domain.Controllers.TeamOwnerController;
import domain.Enums.CoachPosition;
import domain.Enums.FieldType;
import domain.Enums.FootballerPosition;
import domain.Enums.TeamManagerPermissions;
import domain.Impl.Field;
import domain.Impl.Team;
import domain.Users.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.EnumMap;
import java.util.HashMap;

import static org.junit.Assert.*;

public class UC7 {
    private static ManagementUser owner;
    private static TeamManager manager;
    private static Team team;
    private static Field field;
    private static Coach coach;
    private static Footballer footballer;


    @BeforeClass
    public static void beforeClass() throws Exception {
        owner = new Owner("avi@gmail.com", "123456", "avi", "levi", "avi@gmail.com");
        team = TeamOwnerController.addNewTeamToSystem(owner,"team1111");
        manager = TeamOwnerController.signUpNewTeamManager(owner,"moshe","cohen","man@asd.com", team);
        field = new Field(100,"here","sdf", FieldType.Tournament);
        coach = new Coach("avi@gmail.com", "123456", "avi", "levi", "avi@gmail.com", CoachPosition.Fitness);
        footballer = new Footballer("ben@gmail.com", "123456", "ben", "levi", "ben@gmail.com", FootballerPosition.Goalkeeper);
    }

    public static void givePermission() throws Exception {
        EnumMap<TeamManagerPermissions, Boolean> permissions = new EnumMap<>(TeamManagerPermissions.class);
        for (TeamManagerPermissions value : TeamManagerPermissions.values()) {
            permissions.put(value, true);
        }
        TeamOwnerController.changePermissionsForTeamManager(owner,manager,permissions);
    }

    public static void removePermission() throws Exception {
        EnumMap<TeamManagerPermissions, Boolean> permissions = new EnumMap<>(TeamManagerPermissions.class);
        for (TeamManagerPermissions value : TeamManagerPermissions.values()) {
            permissions.put(value, false);
        }
        TeamOwnerController.changePermissionsForTeamManager(owner,manager,permissions);
    }

    //region Test Use Case 7.1
    @Test
    public void Test_OperationWithPermission() throws Exception {
        givePermission(); //give fuul permissions
        assertTrue(TeamOwnerController.addMemberToTeam(manager, team, coach));
        assertTrue(TeamOwnerController.removeMemberFromTeam(manager, team, coach));
        assertTrue(TeamOwnerController.addFieldToTeam(manager, team, field));
        assertTrue(TeamOwnerController.editAssetOfTeam(manager, field,new HashMap<>()));
        assertTrue(TeamOwnerController.removeFieldFromTeam(manager, team, field));
        assertTrue(TeamOwnerController.addUserAsTeamOwner (manager, team,footballer));
        assertTrue(TeamOwnerController.closeTeam(manager, team));
        assertTrue(TeamOwnerController.openTeam(manager, team));
        assertTrue(TeamOwnerController.addFinanceAction(manager, team,"aaa",123,"aaa",123456789,owner));
        removePermission();
    }

    @Test(expected = Exception.class)
    public void Test_OperationWithoutPermission() throws Exception {
        assertTrue(TeamOwnerController.addMemberToTeam(manager, team, coach));
        assertTrue(TeamOwnerController.removeMemberFromTeam(manager, team, coach));
        assertTrue(TeamOwnerController.addFieldToTeam(manager, team, field));
        assertTrue(TeamOwnerController.editAssetOfTeam(manager, field,new HashMap<>()));
        assertTrue(TeamOwnerController.removeFieldFromTeam(manager, team, field));
        assertTrue(TeamOwnerController.addUserAsTeamOwner (manager, team,footballer));
        assertTrue(TeamOwnerController.closeTeam(manager, team));
        assertTrue(TeamOwnerController.openTeam(manager, team));
        assertTrue(TeamOwnerController.addFinanceAction(manager, team,"aaa",123,"aaa",123456789,owner));

    }
    //endregion

}
