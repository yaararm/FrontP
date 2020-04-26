package unit;

import BusinessLayer.Enum.FootballerPosition;
import BusinessLayer.Football.Members;
import BusinessLayer.Users.Fan;
import BusinessLayer.Users.Footballer;
import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class MembersTest {

    private static Members members;
    private static Footballer footballer;
    private static Fan fan;

    @BeforeClass
    public static void before_class(){
        members = new Members();
        footballer = new Footballer("foot","123","fo","ot","foot@gmail.com", FootballerPosition.Attacking_Midfielder);
        fan = new Fan("q","123","q","q","q@gmail.com");


    }

    @Test
    public void test_addMember_acceptnace() throws Exception {
        if(members.getFootballers().contains(footballer)){
            members.removeMember(footballer);
        }
        members.addMember(footballer);
        assertTrue(!members.getFootballers().isEmpty() && members.getCoaches().isEmpty() && members.getOwners().isEmpty()
                    && members.getTeamManagers().isEmpty());
    }

    @Test(expected = Exception.class)
    public void test_addMember_nonAcceptnace() throws Exception {
        members.addMember(fan);
    }

    @Test
    public void test_removeMember_acceptnace() throws Exception {
        if(!members.getFootballers().contains(footballer)){
            members.addMember(footballer);
        }
        assertTrue(members.removeMember(footballer));
    }

    @Test(expected = Exception.class)
    public void test_removeMember_nonAcceptnace() throws Exception {
        members.removeMember(fan);
    }

}
