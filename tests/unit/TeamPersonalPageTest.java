package unit;

import domain.BusinessLayer.Football.Team;
import domain.BusinessLayer.Users.Fan;
import domain.BusinessLayer.Users.Owner;
import domain.BusinessLayer.SystemFeatures.TeamPersonalPage;
import org.junit.Test;

import static domain.BusinessLayer.Enum.TeamState.active;
import static junit.framework.TestCase.*;

public class TeamPersonalPageTest {
    Owner tw = new Owner("delbusk12e@gmail.com", "192837465", "del", "buske", "delbuske12@gmail.com");
    Team rm = new Team("real_madrid1", active, tw);
    TeamPersonalPage tpt =  (TeamPersonalPage) rm.getTeamPersonalPage();

    @Test
    public void addOrRemoveFan(){
        Fan f = new Fan("rotem@gmail.com", "12345654", "rotem", "rumney", "rotem@gmail.com");
       assertTrue(tpt.addFan(f));
        assertTrue(tpt.removeFans(f));
    }

    @Test
    public void upFdatedetails(){
        tpt.setCoachName("samuel");
        tpt.setTeamFields("wembly");
        tpt.setTeamFootballerMembers("henri");
        tpt.setGames("champion");
        assertEquals("samuel", tpt.getCoachName());
        assertEquals("wembly", tpt.getTeamFields());
        assertEquals("henri", tpt.getTeamFootballerMembers());
        assertEquals("champion", tpt.getGames());
    }





}
