package Acceptness;

import domain.ServiceLayer.Controllers.PersonalPageSystem;
import domain.BusinessLayer.Users.Coach;
import domain.BusinessLayer.SystemFeatures.TeamMemberPersonalPage;
import org.junit.Test;

import java.util.HashMap;

import static domain.BusinessLayer.Enum.CoachPosition.Main;
import static domain.BusinessLayer.Enum.CoachPosition.Strategy;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class UC5 {


    //================= updatePersonalPage 5.1 ===================
    PersonalPageSystem pps = new PersonalPageSystem();

    @Test
    public void test_UC5_1_Acceptance() throws Exception {
        Coach arsen = new Coach("arsen@gmail.com", "456123789", "arsen", "arsenal", "arsen@gmail.com", Strategy);
        HashMap<String, String> valuesToUpdate = new HashMap<>();
        valuesToUpdate.put("role", "Main");
        valuesToUpdate.put("history", "no history");
        valuesToUpdate.put("team", "arsenal");
        pps.updatePersonalPage(arsen.getMyPersonalPage(), valuesToUpdate);

        boolean ans = ((((TeamMemberPersonalPage) arsen.getMyPersonalPage()).getHistory().compareTo("no history") == 0) &&
                (((TeamMemberPersonalPage) arsen.getMyPersonalPage()).getTeam().compareTo("arsenal") == 0) &&
                (((TeamMemberPersonalPage) arsen.getMyPersonalPage()).getRole().compareTo("Main") == 0));

        assertTrue(ans);

    }

    @Test
    public void test_UC5_1_NotAcceptance() throws Exception {
        Coach arsen = new Coach("arsen@gmail.com", "456123789", "arsen", "arsenal", "arsen@gmail.com", Strategy);
        HashMap<String, String> valuesToUpdate = new HashMap<>();
        valuesToUpdate.put("teams", "london");
        pps.updatePersonalPage(arsen.getMyPersonalPage(), valuesToUpdate);

        boolean ans =  (((TeamMemberPersonalPage) arsen.getMyPersonalPage()).getTeam().compareTo("london") == 0);

        assertFalse(ans);


    }

    //=================== addContentToPersonalPage 5.2 ===============
    @Test
    public void test_UC5_2_Acceptance()  throws Exception {
        Coach ivitz = new Coach("ivitz@gmail.com", "789431256", "vladimir", "ivitz", "ivitz@gmail.com", Main);
        HashMap<String, String> valuesToUpdate = new HashMap<>();
        valuesToUpdate.put("history","i love tlv");
        valuesToUpdate.put("home","is serbia");
        pps.addContentToPersonalPage(ivitz.getMyPersonalPage(),valuesToUpdate);
        boolean ans = (((TeamMemberPersonalPage)ivitz.getMyPersonalPage()).getContent().compareTo("home: is serbia\n")==0);
        assertTrue(ans);
    }

    @Test
    public void test_UC5_2_NotAcceptance()  throws Exception {
        Coach kika = new Coach("kika@gmail.com", "789431256", "kika", "stein", "kika@gmail.com", Main);
        HashMap<String, String> valuesToUpdate = new HashMap<>();
        valuesToUpdate.put("history","i love barca");

        pps.addContentToPersonalPage(kika.getMyPersonalPage(),valuesToUpdate);
        boolean ans = (((TeamMemberPersonalPage)kika.getMyPersonalPage()).getContent().compareTo("history, i love barca")==0);
        assertFalse(ans);

    }


}
