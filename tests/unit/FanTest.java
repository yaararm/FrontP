package unit;
import BusinessLayer.SystemFeatures.Complaint;
import BusinessLayer.SystemFeatures.PersonalPage;
import BusinessLayer.SystemFeatures.TeamMemberPersonalPage;
import BusinessLayer.Users.Coach;
import BusinessLayer.Users.Fan;
import BusinessLayer.Users.Owner;
import DB.SystemController;
import BusinessLayer.Enum.CoachPosition;
import BusinessLayer.Enum.TeamState;
import BusinessLayer.Football.Game;
import BusinessLayer.Football.Season;
import BusinessLayer.Football.Team;
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
        Owner o = new Owner("moshe@gmail.com", "123456", "moshe", "cohen", "moshe@gmail.com");

        Game g = new Game(new Season(2001,123456),new Team("t1", TeamState.active,o),new Team("t2",TeamState.active,o));
        assertTrue(f.addToObservedGames(g));
        assertTrue(g.attachObserver(f));

        assertTrue(g.getFansObserver().contains(f));
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
            for (PersonalPage followedPersonalPage : f.getFollowedPersonalPages()) {
                assertFalse(followedPersonalPage.getFans().contains(f));
            }
            for (Game g : f.getObservedGames()) {
                assertFalse(g.getFansObserver().contains(f));
            }
            assertFalse(SystemController.userNameUser.containsKey(f.getUserName()));
            assertTrue(SystemController.archiveUsers.containsKey(f.getUserName()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
