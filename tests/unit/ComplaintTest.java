package unit;

import domain.Enums.ComplaintStatus;
import domain.Users.*;
import org.junit.*;
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
        SystemManager sm = new SystemManager();
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
