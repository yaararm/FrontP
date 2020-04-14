package Acceptness;

import domain.Controllers.PersonalPageSystem;
import domain.Controllers.TeamOwnerController;
import domain.Impl.Team;
import domain.Users.*;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static domain.Enums.FootballerPosition.*;
import static domain.Enums.TeamState.active;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class UC4 {

    //================= updatePersonalPage 4.1 ===================
    PersonalPageSystem pps = new PersonalPageSystem();

    @Test
    public void test_UC4_1_Acceptance()  throws Exception {
        Owner tw = new Owner("yalla@gmail.com", "192837465", "yaara", "rumney", "yalla@gmail.com");
        Team manU = new Team("Manchester_United", active, tw);
        Footballer david = TeamOwnerController.signUpNewFootballer(tw, "david", "beckham", "david@gmail.com", Striker, manU);
        HashMap<String, String> valuesToUpdate = new HashMap<>();
        valuesToUpdate.put("role", "Attacking_Midfielder");
        valuesToUpdate.put("birthday", "22/7/1993");
        valuesToUpdate.put("hobbies", "pokemon");
        pps.updatePersonalPage(david.getMyPersonalPage(), valuesToUpdate);

    boolean ans = ( (((TeamMemberPersonalPage)david.getMyPersonalPage()).getBirthday().compareTo("22/7/1993")==0) &&
            (((TeamMemberPersonalPage)david.getMyPersonalPage()).getHobbies().compareTo("pokemon")==0) &&
            (((TeamMemberPersonalPage)david.getMyPersonalPage()).getRole().compareTo("Attacking_Midfielder")==0) );

   assertTrue(ans);

    }

    @Test
    public void test_UC4_1_NotAcceptance()  throws Exception {
        Owner tw = new Owner("yalla@gmail.com", "192837465", "yaara", "rumney", "yalla@gmail.com");
        Team manU = new Team("Manchester_United", active, tw);
        Footballer david = TeamOwnerController.signUpNewFootballer(tw, "david", "beckham", "david@gmail.com", Striker, manU);
        HashMap<String, String> valuesToUpdate = new HashMap<>();
        valuesToUpdate.put("roles", "Attacking_Midfielder");

        pps.updatePersonalPage(david.getMyPersonalPage(), valuesToUpdate);

        boolean ans = (((TeamMemberPersonalPage)david.getMyPersonalPage()).getRole().compareTo("Attacking_Midfielder")==0);

        assertFalse(ans);

    }

    //=================== addContentToPersonalPage 4.2 ===============
    @Test
    public void test_UC4_2_Acceptance()  throws Exception {
        Owner tw = new Owner("delbuske@gmail.com", "192837465", "del", "buske", "delbuske@gmail.com");
        Team rm = new Team("real_madrid", active, tw);
        Footballer ramos = TeamOwnerController.signUpNewFootballer(tw, "sergio", "ramos", "sergio@gmail.com", Center_Back, rm);
        HashMap<String,String> valuesToUpdate = new HashMap<>();
        valuesToUpdate.put("history","i love spain");
        valuesToUpdate.put("home","is whenever im with you");
        pps.addContentToPersonalPage(ramos.getMyPersonalPage(),valuesToUpdate);
        boolean ans = (((TeamMemberPersonalPage)ramos.getMyPersonalPage()).getContent().compareTo("history: i love spain")==0);
        assertTrue(ans);
    }

    @Test
    public void test_UC4_2_NotAcceptance()  throws Exception {
        Owner tw = new Owner("delbuske@gmail.com", "192837465", "del", "buske", "delbuske@gmail.com");
        Team rm = new Team("real_madrid", active, tw);
        Footballer ramos = TeamOwnerController.signUpNewFootballer(tw, "sergio", "ramos", "sergio@gmail.com", Center_Back, rm);
        HashMap<String,String> valuesToUpdate = new HashMap<>();
        valuesToUpdate.put("history","i love spain");
        pps.addContentToPersonalPage(ramos.getMyPersonalPage(),valuesToUpdate);
        boolean ans = (((TeamMemberPersonalPage)ramos.getMyPersonalPage()).getContent().compareTo("history,i love spain")==0);
        assertTrue(ans);
    }

}
