package unit;

import domain.Controllers.TeamOwnerController;
import domain.Impl.Team;
import domain.Users.Footballer;
import domain.Users.Owner;
import domain.Users.TeamMemberPersonalPage;
import domain.Users.TeamPersonalPage;
import org.junit.Test;

import static domain.Enums.FootballerPosition.Center_Back;
import static domain.Enums.TeamState.active;
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
