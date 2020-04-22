package unit;

import domain.Controllers.Utils;
import domain.Enums.TeamManagerPermissions;
import domain.Users.TeamManager;
import org.junit.Test;

import java.util.EnumMap;
import java.util.HashMap;

import static domain.Enums.TeamManagerPermissions.*;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class TeamManagerTest {
    TeamManager tm = new TeamManager( "balagan@gmail.com", "ffff87ff", "fan", "levi", "balagan@gmail.com");

    @Test
    public void deleteUserTest() {
        try {
            assertTrue(tm.deleteUser());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testPermissions(){
        tm.addPermissions(AddOwner,CloseTeam,ReportFinance,AddAsset);
        assertTrue(tm.hasPermission(CloseTeam));
        tm.removePermissions(ReportFinance);
        assertFalse(tm.hasPermission(ReportFinance));

        EnumMap<TeamManagerPermissions, Boolean> permissions = new EnumMap<> (TeamManagerPermissions.class);
        permissions.put(EditPermissions,true);
        permissions.put(EditAsset,true);
        tm.changePermissions(permissions);
        assertTrue(tm.hasPermission(EditAsset));

    }

    @Test
    public void testEditAsset(){
        HashMap<String,String> hm = new HashMap<>();
        hm.put("firstname","daniela");
        hm.put("lastname","cohen");
        hm.put("email","dani22@gmail.com");
        hm.put("password","654321");

        try {
            tm.editAsset(hm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals("daniela", tm.getFirstName());
        assertEquals("cohen", tm.getLastName());
        assertEquals(Utils.sha256("654321"), tm.getPassword());
        assertEquals("dani22@gmail.com", tm.getEmail());

    }

}
