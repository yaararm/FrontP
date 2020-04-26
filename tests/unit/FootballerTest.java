package unit;

import domain.DB.SystemController;
import domain.CrossCutting.Utils;
import domain.BusinessLayer.Enum.FootballerPosition;
import domain.BusinessLayer.Football.Team;
import domain.BusinessLayer.Users.Footballer;
import org.junit.*;

import java.util.HashMap;

import static org.junit.Assert.*;

public class FootballerTest {
        Footballer footballer = new Footballer("avi@gmail.com", "123456", "avi", "levi", "avi@gmail.com", FootballerPosition.Goalkeeper);
    @Test
    public void editAsset(){
        HashMap<String,String> hm = new HashMap<>();
        hm.put("firstname","dani");
        hm.put("lastname","cohen");
        hm.put("email","dani@gmail.com");
        hm.put("password","654321");

        try {
            footballer.editAsset(hm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals("dani", footballer.getFirstName());
        assertEquals("cohen", footballer.getLastName());
        assertEquals(Utils.sha256("654321"), footballer.getPassword());
        assertEquals("dani@gmail.com", footballer.getEmail());

    }

    @Test
    public void deleteUserTest() {
        try {
            assertTrue(footballer.deleteUser());
            for (Team team : footballer.getTeams().keySet()) {
                assertFalse(team.getTeamCoaches().contains(footballer));
            }
            assertFalse(SystemController.userNameUser.containsKey(footballer.getUserName()));
            assertTrue(SystemController.archiveUsers.containsKey(footballer.getUserName()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
