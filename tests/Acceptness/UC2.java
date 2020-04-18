package Acceptness;

import domain.Controllers.GuestController;
import domain.Controllers.SignedInController;
import org.junit.Before;
import org.junit.Test;


import static junit.framework.TestCase.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class UC2 {

//ToDo figure out whether every test should work on his own?
    //Todo also- not acceptance shouldfailed?

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
    public void test_UC2_3_signIn_NotAcceptance_noUser() { // return  null instead of exception
        try {
            sc.signIn("mevaes@bc.co.il", "1234565679");
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


}
