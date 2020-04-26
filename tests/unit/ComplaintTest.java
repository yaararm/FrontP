package unit;

import domain.BusinessLayer.Enum.ComplaintStatus;
import domain.BusinessLayer.Users.Fan;
import domain.BusinessLayer.Users.SystemManager;
import domain.BusinessLayer.SystemFeatures.Complaint;

import org.junit.*;
import domain.BusinessLayer.SystemFeatures.Complaint;
import static org.junit.Assert.*;



public class ComplaintTest {

    @Test
    public void createCommentTest() {
        Fan f = new Fan("fan1@gmail.com", "ffffff", "fan", "levi", "fan@gmail.com");
        Complaint comp = new Complaint(f, "hello there!");
        assertEquals(ComplaintStatus.New, comp.getStatus());
        assertEquals(f,comp.getFan());
    }

    @Test
    public void addCommentTest() {
        Fan f = new Fan("fan2@gmail.com", "ffffff", "fan", "levi", "fan@gmail.com");
        SystemManager sm = new SystemManager("sysMan@gmail.com", "123465", "sys", "mang", "sysMan@gmail.com");
        Complaint comp = new Complaint(f, "hello there!");
        assertEquals(ComplaintStatus.New, comp.getStatus());

        try {
            comp.addComment(sm,"hii");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(ComplaintStatus.Closed, comp.getStatus());
        assertEquals(f,comp.getFan());
    }

}
