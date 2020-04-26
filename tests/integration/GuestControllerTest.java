package integration;

import ServiceLayer.Controllers.GuestController;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GuestControllerTest {

GuestController gc = new GuestController();

    @Test
    public void test_signUp_email() {

        try {
            gc.singUp("messbcil", "1234565679", "sfdf", "fs");
        } catch (Exception e) {
            String message = "Not valid email";
            assertEquals(message, e.getMessage());
        }

    }
}
