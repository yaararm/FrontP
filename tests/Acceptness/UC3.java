package Acceptness;

import domain.Controllers.*;
import domain.Enums.TeamState;
import domain.Impl.Game;
import domain.Impl.Team;
import domain.Users.*;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static domain.Enums.FootballerPosition.Striker;
import static domain.Enums.TeamState.active;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class UC3 {


    // ====================logout 3.1 ================
    GuestController gc = new GuestController();
    SignedInController sc = new SignedInController();


    @Test
    public void test_UC3_1_Acceptance_LogOut() throws Exception {
        gc.singUp("ronaldo@gmail.com", "456789123", "ronaldo", "mashehu");
        sc.signIn("ronaldo@gmail.com", "456789123");
        SignedUser user = SystemController.checkCredentials("ronaldo@gmail.com", "456789123"); // return  null
        boolean ans = sc.logOut(user);
        assertTrue(ans);
    }

    @Test
    public void test_UC3_1_NotAcceptance_LogOut() throws Exception {

        SignedUser user = SystemController.checkCredentials("ronaldo@gmail.com", "456789123"); // return  null
        boolean ans = sc.logOut(user);
        assertTrue(ans);
    }


    FanController fc = new FanController();

    // ============== Follow 3.2 ============


    @Test
    public void test_UC3_2_Acceptance() throws Exception {
        Fan f = new Fan("shachar@gmail.com", "12345654", "shachar", "rumney", "shachar@gmail.com");
        Owner tw = new Owner("yaya@gmail.com", "123456789", "yaara", "rumney", "yaya@gmail.com");
        Team team = new Team("Arsenal", active, tw);
        Footballer tiri = TeamOwnerController.signUpNewFootballer(tw, "tiri", "henry", "tiri@gmail.com", Striker, team);

        boolean ans = fc.follow(f, tiri.getMyPersonalPage());
        assertTrue(ans);

    }

    @Test
    public void test_UC3_2_NotAcceptance_remove() throws Exception {
        Fan f = new Fan("shachar@gmail.com", "12345654", "shachar", "rumney", "shachar@gmail.com");
        Owner tw = new Owner("yaya@gmail.com", "123456789", "yaara", "rumney", "yaya@gmail.com");
        Team team = new Team("Arsenal", active, tw);
        Footballer tiri = TeamOwnerController.signUpNewFootballer(tw, "tiri", "henry", "tiri@gmail.com", Striker, team);
        fc.follow(f, tiri.getMyPersonalPage());
        boolean ans = fc.follow(f, tiri.getMyPersonalPage());
        assertFalse(ans);
    }


    // ============ Subscribe 3.3 ===========

    @Test
    public void test_UC3_3_Acceptance() {
        Fan f = new Fan("shachar@gmail.com", "12345654", "shachar", "rumney", "shachar@gmail.com");
        Game g = new Game();
        boolean ans = fc.subscribe(f, g);
        assertTrue(ans);
    }

    @Test
    public void test_UC3_3_NotAcceptance_remove() {
        Fan f1 = new Fan("sha@gmail.com", "12347834", "sha", "rumney", "sha@gmail.com");
        Game g1 = new Game();
        g1.attachObserver(f1);
        boolean ans = fc.subscribe(f1, g1);
        assertFalse(ans);
    }

    // ============ Complaint 3.4 ===========

    @Test
    public void test_UC3_4_Acceptance() {
        Fan f = new Fan("shachar@gmail.com", "12345654", "shachar", "rumney", "shachar@gmail.com");
        boolean ans = fc.createComplaint(f, "arsenal is the best!");
        assertTrue(ans);
    }

    @Test
    public void test_UC3_4_NotAcceptance() {
        Fan f = new Fan("shachar@gmail.com", "12345654", "shachar", "rumney", "shachar@gmail.com");
        boolean ans = fc.createComplaint(f, "");
        assertFalse(ans);
    }


    // ============ Search History 3.5 ==============

    @Test
    public void test_UC3_5_Acceptance() throws Exception { //Todo
        Fan f = new Fan("shachar@gmail.com", "12345654", "shachar", "rumney", "shachar@gmail.com");
        long start =  System.currentTimeMillis();
        SystemController.search("barcelona");
        Map<String, Long> mymap = fc.mySearchHistory(f, start,System.currentTimeMillis());
        boolean ns;
    }

    @Test
    public void test_UC3_5_NotAcceptance_WrongDates() throws Exception {
        try {
            Fan f = new Fan("shachar@gmail.com", "12345654", "shachar", "rumney", "shachar@gmail.com");
            Map<String, Long> mymap = fc.mySearchHistory(f, System.currentTimeMillis(), (long) System.currentTimeMillis() - 1000);
        } catch (Exception e) {
            String message = "Wrong Dates";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void test_UC3_5_NotAcceptance_NoSearch() {
        try {
            Fan f = new Fan("shachar@gmail.com", "12345654", "shachar", "rumney", "shachar@gmail.com");
            long start =  System.currentTimeMillis();
            Thread.sleep(5);
            long end =  System.currentTimeMillis();
            Map<String, Long> mymap = fc.mySearchHistory(f, start, end);
        } catch (Exception e) {
            String message = "No Search History";
            assertEquals(message, e.getMessage());
        }
    }

    // ========= Update 3.6 ==============
    @Test
    public void test_UC3_6_Acceptance() throws Exception{
        Fan f4 = new Fan("shachar.rum@gmail.com", "987456321", "Shachar", "Rumney", "shachar.rum@gmail.com");
        HashMap<String, String> valuesToUpdate = new HashMap<>();
        valuesToUpdate.put("firstname","asaf");
        valuesToUpdate.put("lastname","perri");
        valuesToUpdate.put("email","perri@gmail.com");
        valuesToUpdate.put("password","1234561234");
        fc.updateDetails(f4,valuesToUpdate );
        boolean ans = ( f4.getEmail().compareTo("perri@gmail.com")==0) && (f4.getFirstName().compareTo("asaf")==0)
                && (f4.getLastName().compareTo("perri")==0);   // && (f4.getPassword().compareTo("1234561234")==0); cant check password

        assertTrue(ans);

    }





}
