package Acceptness;

import domain.Controllers.*;
import domain.Enums.ComplaintStatus;
import domain.Enums.TeamState;
import domain.Impl.Team;
import domain.Users.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

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
        team = TeamOwnerController.addNewTeamToSystem(owner,"hapoelinbartzur");
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
        boolean removeFromSystem = systemMangerController.removeUserFromSystem(SystemController.userNameUser.get("adif@gmail.com"));
        assertTrue(inSystem&&removeFromSystem);
    }

    @Test(expected = Exception.class)
    public void test_UC8_2_nonAcceptnce() throws Exception {
        systemMangerController.removeUserFromSystem(owner);
    }


    //UC 8.3.1 getAllComplaints
    @Test
    public void test_UC8_3_acceptnce(){
        Complaint complaint =new Complaint(fan,"not good");
        ComplaintSystemController.addComplaint(complaint);
        assertFalse(systemMangerController.getAllComplaints().isEmpty());
    }

    //UC 8.3.2 addCommentToComplaint
    @Before
    public void before_UC8_3_2_acceptnce(){
        Fan fan = new Fan("in","87654321","in","bar","in@gmail.com");
        complaintInSystem =new Complaint(fan,"not good");
        ComplaintSystemController.addComplaint(complaintInSystem);
        systemManager = new SystemManager("elinort","123","elinor","tzur","eli@gmail.com");
    }

    @Test
    public void test_UC8_3_2_accptnce() throws Exception {
        assertTrue(systemMangerController.addCommentToComplaint(systemManager,complaintInSystem,"ok"));
    }
    @Before
    public void before_UC8_3_2_nonAcceptnce() throws Exception {
        Fan fan = new Fan("am","65456545","a","m","am@gmail.com");
        complaintInSystem = new Complaint(fan,"love this game");
        systemManager = new SystemManager("aviva","123","aviva","tova","aviva@gmail.com");
        systemMangerController.addCommentToComplaint(systemManager,complaintInSystem,"good");

    }

    @Test(expected = Exception.class)
    public void test_UC8_3_2_nonAcceptnce() throws Exception {
        complaintInSystem.setStatus(ComplaintStatus.Closed);
        systemMangerController.addCommentToComplaint(systemManager,complaintInSystem,"lala");
    }


    //UC8.4 getSystemEventsLog
    @Test
    public void test_UC8_4_acceptnace() throws Exception {
        Date from = new Date();
        Thread.sleep(1000*10);
        Date to =new Date();
        List<List<String>> log = systemMangerController.getSystemEventsLog(from.getTime(),to.getTime());
        List<List<String>> readLog = systemMangerController.readFromLog();
        assertNotNull(readLog);
    }

    @Test(expected = Exception.class)
    public void test_UC8_4_nonAcceptnace() throws Exception {
        Date to = new Date();
        Thread.sleep(1000*10);
        Date from =new Date();
        systemMangerController.getSystemEventsLog(from.getTime(),to.getTime());

    }









}
