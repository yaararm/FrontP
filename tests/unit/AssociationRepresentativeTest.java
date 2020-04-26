package unit;

import BusinessLayer.Users.AssociationRepresentative;
import org.junit.*;
import static org.junit.Assert.*;

public class AssociationRepresentativeTest {

    @Test
    public void getAssociationRepresentativeIDTest() {
        AssociationRepresentative ar1 = new AssociationRepresentative("tmp1@gmail.com", "123456", "avi", "cohen", "tmp1@gmail.com");
        AssociationRepresentative ar2 = new AssociationRepresentative("tmp2@gmail.com", "123456", "avi", "cohen", "tmp2@gmail.com");
        assertEquals(0, ar1.getAssociationRepresentativeID());
        assertEquals(1, ar2.getAssociationRepresentativeID());

    }

    @Test
    public void deleteUserTest() {
        AssociationRepresentative ar1 = new AssociationRepresentative("tmp1@gmail.com", "123456", "avi", "cohen", "tmp1@gmail.com");
        assertTrue(ar1.deleteUser());
    }
}
