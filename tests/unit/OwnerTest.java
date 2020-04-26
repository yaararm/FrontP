package unit;

import BusinessLayer.Users.Owner;
import org.junit.*;
import static org.junit.Assert.*;

public class OwnerTest {
    Owner o = new Owner("moshe@gmail.com", "123456", "moshe", "cohen","moshe@gmail.com");

    @Test
    public void deleteUserTest() {
        try {
            assertTrue(o.deleteUser());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
