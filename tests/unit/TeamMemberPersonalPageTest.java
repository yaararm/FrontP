package unit;

import ServiceLayer.Controllers.TeamOwnerController;
import BusinessLayer.Football.Team;
import BusinessLayer.Users.Footballer;
import BusinessLayer.Users.Owner;
import BusinessLayer.SystemFeatures.TeamMemberPersonalPage;
import org.junit.Test;

import static BusinessLayer.Enum.FootballerPosition.Center_Back;
import static BusinessLayer.Enum.TeamState.active;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeamMemberPersonalPageTest {

    Owner tw = new Owner("delb@gmail.com", "192837465", "del", "buske", "delbe@gmail.com");
    Team rm = new Team("real_madrid", active, tw);

    @Test
    public void editDetails() throws Exception {
        Footballer casias = TeamOwnerController.signUpNewFootballer(tw, "casias", "casias", "sergio@gmail.com", Center_Back, rm);
        TeamMemberPersonalPage tpt =  (TeamMemberPersonalPage) casias.getMyPersonalPage();
        assertEquals(casias,tpt.getPageOwner());

        tpt.setContent("i love espania!");
        tpt.setRole("goal keeper");
        assertEquals("i love espania!", tpt.getContent());
        assertEquals("goal keeper", tpt.getRole());

    }


}
