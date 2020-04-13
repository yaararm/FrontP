package Acceptness;

import domain.Controllers.*;
import domain.Enums.TeamState;
import domain.Impl.Game;
import domain.Impl.Team;
import domain.Users.*;
import org.junit.Before;
import org.junit.Test;

import static domain.Enums.FootballerPosition.Striker;
import static domain.Enums.TeamState.active;
import static junit.framework.TestCase.assertTrue;
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
    public void test_UC3_4_Acceptance(){
        Fan f = new Fan("shachar@gmail.com", "12345654", "shachar", "rumney", "shachar@gmail.com");
        boolean ans = fc.createComplaint(f,"arsenal is the best!");
        assertTrue(ans);
    }
    @Test
    public void test_UC3_4_NotAcceptance(){
        Fan f = new Fan("shachar@gmail.com", "12345654", "shachar", "rumney", "shachar@gmail.com");
        boolean ans = fc.createComplaint(f,"");
        assertFalse(ans);
    }



}
