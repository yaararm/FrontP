package Acceptness;

import domain.Controllers.AssociationRepresentativeController;
import domain.Controllers.GuestController;
import domain.Controllers.SignedInController;
import domain.Controllers.SystemController;
import domain.Enums.FieldType;
import domain.Enums.FootballerPosition;
import domain.Enums.RefereeTraining;
import domain.Enums.TeamState;
import domain.Impl.Field;
import domain.Impl.League;
import domain.Impl.Season;
import domain.Impl.Team;
import domain.Users.*;
import org.apache.commons.lang3.SystemUtils;
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static domain.Enums.CoachPosition.Main;
import static junit.framework.TestCase.*;
import static org.junit.Assert.assertEquals;


public class UC2 {

    GuestController gc = new GuestController();

    //===================================== signUp 2.2==================================//
    @Test
    public void test_UC2_2_signUp_acceptance() throws Exception {
        boolean ans = false;

        ans = gc.singUp("messi@bc.co.il", "123456789", "leo", "messi");

        assertTrue(ans);
    }

    @Test
    public void test_UC2_2_signUp_NotAcceptance_userName() {

        try {
            gc.singUp("messi2@bc.co.il", "1234565679", "leonid", "messi");
            gc.singUp("messi2@bc.co.il", "1234569", "leonid", "messi");
        } catch (Exception e) {
            String message = "Not unique user name";
            assertEquals(message, e.getMessage());
        }

    }

    @Test
    public void test_UC2_2_signUp_NotAcceptance_shortPassword() {

        try {
            gc.singUp("mess@bc.co.il", "1234", "leonid", "messi");

        } catch (Exception e) {
            String message = "Not long enough";
            assertEquals(message, e.getMessage());
        }

    }

    //===================================== signIn 2.3==================================//

    SignedInController sc = new SignedInController();


    @Test
    public void test_UC2_3_signIn_Acceptance() throws Exception {
        gc.singUp("messi90@bc.co.il", "1234565679", "leonid", "messi");
        boolean ans = false;
        ans = sc.signIn("messi90@bc.co.il", "1234565679");
        assertTrue(ans);
    }

    @Test
    public void test_UC2_3_signIn_NotAcceptance_noUser() throws Exception { // return  null instead of exception
        try {
            sc.signIn("nouserSuch@bc.co.il", "1234565679");
        } catch (Exception e) {
            String message = "Wrong credentials";
            assertEquals(message, e.getMessage());
        }

    }

    @Test
    public void test_UC2_3_signIn_NotAcceptance_nullInput() {
        try {
            sc.signIn("messi256@bc.co.il", "123");
        } catch (Exception e) {
            String message = "Couldn't be that credentials";
            assertEquals(message, e.getMessage());
        }

    }

    //===================================== search 2.5 ==================================//

    @Test
    public void test_UC2_5_Acceptance() throws Exception {
       Owner owner = new Owner("mey@gmail.com","123","im","owner","mey@gmail.com");
        Team team = new Team("macabi_TLV", TeamState.active,owner);
        Team team2 = new Team("hapoel_TLV", TeamState.active,owner);
        Footballer footballer = new Footballer("yossi@ben.com","19921995","yossi","ben","yossi@ben.com", FootballerPosition.Center_Back);
        Coach kika = new Coach("kika45@gmail.com", "789431256", "kiktrha", "stgerein", "kika45@gmail.com", Main);
        team.addTeamMember(owner,kika);
        team.addTeamMember(owner, footballer);
        Guest gs  = new Guest();

        HashMap<String, HashSet<Object>> result=  SystemController.search(gs, "macabi_TLV" );
        List<Object> list = new ArrayList<Object>(result.get("Footballer"));
        List<Object> list2 = new ArrayList<Object>(result.get("Team"));
        boolean ans = list.get(0).toString().contains("yossi");
        boolean ans2 = list2.get(0).toString(). contains("macabi_TLV");
        assertTrue(ans);
        assertTrue(ans2);

    }

    @Test
    public void test_UC2_5_NotAcceptance() {
        Guest gs1  = new Guest();

        HashMap<String, HashSet<Object>> result=  SystemController.search(gs1, "gibrish" );
        List<Object> list = new ArrayList<Object>(result.get("Footballer"));
        List<Object> list2 = new ArrayList<Object>(result.get("Team"));
        boolean ans = list.isEmpty();
        boolean ans2 = list2.isEmpty();
        assertTrue(ans);
        assertTrue(ans2);

    }


}
