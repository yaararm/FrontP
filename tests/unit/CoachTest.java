package unit;

import domain.Controllers.PersonalPageSystem;
import domain.Controllers.SystemController;
import domain.Controllers.Utils;
import domain.Enums.CoachPosition;
import domain.Users.AssociationRepresentative;
import domain.Users.Coach;
import org.junit.*;
import static org.junit.Assert.*;

import java.util.HashMap;


public class CoachTest {
    @Test
    public void editAsset(){
        Coach c = new Coach("avi@gmail.com", "123456", "avi", "levi", "avi@gmail.com", CoachPosition.Fitness);

        HashMap<String,String> hm = new HashMap<>();
        hm.put("firstname","dani");
        hm.put("lastname","cohen");
        hm.put("email","dani@gmail.com");
        hm.put("password","654321");

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
