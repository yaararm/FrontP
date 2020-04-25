package unit;

import domain.Users.Complaint;
import domain.Users.Fan;
import domain.Users.SystemManager;
import junit.framework.TestCase;
import org.junit.Test;

import static domain.Enums.UserStatus.NotActive;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SystemManagerTest {
    SystemManager sm = new SystemManager("itai12@gmail.com","345678923","iati","dagan","itai12@gmail.com");
    @Test
    public void test_addComplaint(){
        Fan f = new Fan("shachar10@gmail.com", "12345654", "shachar", "rumney", "shachar12@gmail.com");
        Complaint c = new Complaint(f,"hola bebe!");


       boolean ans =  sm.addComplaint(c);
       assertTrue(ans);
    }

    @Test
    public void test_deleteUser()throws Exception{
        TestCase.assertTrue( sm.deleteUser());
        sm.getStatus().compareTo(NotActive);
    }

}
