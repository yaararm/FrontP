package integration;

import domain.Controllers.PersonalPageSystem;
import domain.Enums.TeamState;
import domain.Impl.Team;
import domain.Users.Owner;
import domain.Users.TeamPersonalPage;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PersonalPageSystemTest {

    PersonalPageSystem pps = new PersonalPageSystem();
    Owner owner = new Owner("irin","123","im","owner","irin@gmail.com");
    Team team = new Team("ayax",TeamState.active, owner);
    Team team_extre = new Team("porto",TeamState.active,owner);
    @Test
   public void teste_editDetails(){
        HashMap<String, String> details = new HashMap<>();
        details.put("teamFootballerMembers", "imale");
        details.put("teamFields", "test2");
        details.put("records", "test3");
        details.put("Games", "test4");
        details.put("coachName", "test4");

        assertTrue(pps.updatePersonalPage(team.getTeamPersonalPage(),details));
        assertTrue(team.getTeamPersonalPage().toString().contains("test2"));

    }
    @Test
    public void teste_UpdateDetails(){
        HashMap<String, String> details = new HashMap<>();
        details.put("teamFootballerMembers", "imale");
        details.put("teamFields", "test2");
        details.put("records", "test3");
        details.put("Games", "test4");

        assertFalse(pps.addContentToPersonalPage(team_extre.getTeamPersonalPage(),details));


    }
    @Test
    public void removeTeam(){
        assertTrue(pps.moveToArchive(team.getTeamPersonalPage()));

    }

}
