package unit;

import domain.Enums.CoachPosition;
import domain.Enums.ComplaintStatus;
import domain.Impl.Game;
import domain.Users.*;
import org.junit.*;
import static org.junit.Assert.*;

public class FanTest {
    Fan f = new Fan("fan1@gmail.com", "ffffff", "fan", "levi", "fan@gmail.com");
    Coach c = new Coach("avi@gmail.com", "123456", "avi", "levi", "avi@gmail.com", CoachPosition.Fitness);

    //========== Follow Test ================
    @Test
    public void addToFollowedTest() {
        assertTrue(f.addToFollowed(new TeamMemberPersonalPage(c)));
    }

    @Test
    public void checkIfFollowedTest() {
        TeamMemberPersonalPage pp = new TeamMemberPersonalPage(c);
        assertTrue(f.addToFollowed(pp));
        assertTrue(f.checkIfFollowed(pp));
    }

    @Test
    public void removeFollowedTest() {
        TeamMemberPersonalPage pp = new TeamMemberPersonalPage(c);
        assertTrue(f.addToFollowed(pp));
        assertTrue(f.checkIfFollowed(pp));
        assertTrue(f.removeFollowed(pp));
        assertFalse(f.checkIfFollowed(pp));
    }


    //========== Get Notify Test ================
    @Test
    public void updateTest() {
        //ToDo
        assertTrue(true);
    }

    @Test
    public void addToObservedGamesTest() {
        Game g = new Game();
        assertTrue(f.addToObservedGames(g));
    }

    @Test
    public void addToMyComplaintsTest() {
        Complaint comp = new Complaint(f, "hello there!");
        assertTrue(f.addToMyComplaints(comp));
    }

    //========== Delete Test ================
    @Test
    public void deleteUserTest() {
        try {
            assertTrue(f.deleteUser());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
