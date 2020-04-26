package unit;

import DB.SystemController;
import CrossCutting.Utils;
import BusinessLayer.Enum.CoachPosition;
import BusinessLayer.Football.Team;
import BusinessLayer.Users.Coach;
import org.junit.*;

import static org.junit.Assert.*;

import java.util.HashMap;


public class CoachTest {
    @Test
    public void editAsset() {
        Coach c = new Coach("avi@gmail.com", "123456", "avi", "levi", "avi@gmail.com", CoachPosition.Fitness);

        HashMap<String, String> hm = new HashMap<>();
        hm.put("firstname", "dani");
        hm.put("lastname", "cohen");
        hm.put("email", "dani@gmail.com");
        hm.put("password", "654321");

        try {
            c.editAsset(hm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals("dani", c.getFirstName());
        assertEquals("cohen", c.getLastName());
        assertEquals(Utils.sha256("654321"), c.getPassword());
        assertEquals("dani@gmail.com", c.getEmail());

    }

    @Test
    public void deleteUserTest() {
        Coach c = new Coach("avi@gmail.com", "123456", "avi", "levi", "avi@gmail.com", CoachPosition.Fitness);
        try {
            assertTrue(c.deleteUser());
            for (Team team : c.getTeams().keySet()) {
                assertFalse(team.getTeamCoaches().contains(c));
            }
            assertFalse(SystemController.userNameUser.containsKey(c.getUserName()));
            assertTrue(SystemController.archiveUsers.containsKey(c.getUserName()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
