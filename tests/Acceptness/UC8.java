package Acceptness;

import domain.Controllers.*;
import domain.Enums.ComplaintStatus;
import domain.Enums.TeamState;
import domain.Impl.Team;
import domain.Users.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class UC8 {


    private static Team team;
    private static SystemMangerController systemMangerController;
    private static Owner owner;
    private static Fan fan;

    private  SystemManager systemManager;
    private  Complaint complaintInSystem;





    @BeforeClass
    public static void before_all() throws Exception {
        systemMangerController = new SystemMangerController();
        owner = new Owner("amitd","123","amit","dahan","amitosh@gmail.com");
        team = TeamOwnerController.addNewTeamToSystem(owner,"hapoel");
        fan = new Fan("in","87654321","in","bar","inbars@gmail.com");

    }


  //  UC 8.1 permanentlyCloseTeam

    @Test
    public void test_UC8_1_acceptnce() throws Exception {
        assertTrue(systemMangerController.permanentlyCloseTeam(team));
    }



    @Test(expected = Exception.class)
    public void test_UC8_1_nonAcceptnce() throws Exception {
        Team team = TeamOwnerController.addNewTeamToSystem(owner,"ABC");
        systemMangerController.permanentlyCloseTeam(team);
        systemMangerController.permanentlyCloseTeam(team);
    }



    //UC 8.2 removeUserFromSystem


    @Test
    public void test_UC8_2_acceptnce() throws Exception {
        GuestController guestController = new GuestController();
        guestController.singUp("adif@gmail.com","12345678","adi","flint");
        boolean inSystem = SystemController.userNameUser.containsKey("adif@gmail.com");
        boolean removeFromSystem = systemMangerController.removeUserFromSystem(SystemController.userNameUser.get("adi@gmail.com"));
        assertTrue(inSystem&&removeFromSystem);
    }

    //******missing implamentation*****
    @Test//(expected = Exception.class)
    public void test_UC8_2_nonAcceptnce(){

    }


    //UC 8.3 getAllComplaints

    @Test
    public void test_UC8_3_acceptnce(){

        Complaint complaint =new Complaint(fan,"not good");
        ComplaintSystemController.addComplaint(complaint);
        assertFalse(systemMangerController.getAllComplaints().isEmpty());
    }

    //UC 8.4 addCommentToComplaint

    @Before
    public void before_UC8_4_acceptnce(){
        Fan fan = new Fan("in","87654321","in","bar","in@gmail.com");
        complaintInSystem =new Complaint(fan,"not good");
        ComplaintSystemController.addComplaint(complaintInSystem);
        systemManager = new SystemManager();
    }

    @Test
    public void test_UC8_4_accptnce() throws Exception {
        assertTrue(systemMangerController.addCommentToComplaint(systemManager,complaintInSystem,"ok"));
    }

    @Before
    public void before_UC8_4_nonAcceptnce() throws Exception {
        Fan fan = new Fan("am","65456545","a","m","am@gmail.com");
        complaintInSystem = new Complaint(fan,"love this game");
        systemManager = new SystemManager();
        systemMangerController.addCommentToComplaint(systemManager,complaintInSystem,"good");

    }

    @Test(expected = Exception.class)
    public void test_UC8_4_nonAcceptnce() throws Exception {
        complaintInSystem.setStatus(ComplaintStatus.Closed);
        systemMangerController.addCommentToComplaint(systemManager,complaintInSystem,"lala");
    }

    //UC8.5 getSystemEventsLog



}
