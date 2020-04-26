package integration;

import domain.Controllers.SystemController;
import domain.Controllers.SystemMangerController;
import domain.Controllers.TeamOwnerController;
import domain.Enums.TeamState;
import domain.Impl.Team;
import domain.Users.*;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SystemManager_System_Controller_Test {

    private static SystemMangerController systemMangerController;
    private static SystemController systemController;
    private static Owner owner;

    @BeforeClass
    public static void BeforeClass(){
        systemController = new SystemController();
        systemMangerController = new SystemMangerController();
        owner = new Owner("richel","123","rich","el","rich@gmail.com");
    }


    //signUpNewOwner
    @Test
    public void test_signUpNewOwner() throws Exception {
        Owner userOwner = systemMangerController.signUpNewOwner(owner,"ko","ki","koki@gmail.com");
        assertTrue(systemController.userNameUser.containsValue(userOwner));
    }

    @Test(expected = Exception.class)
    public void test_signUpNewOwner_notValidEmail() throws Exception {
        systemMangerController.signUpNewOwner(owner,"ko","ko","koko");
    }

    @Test(expected = Exception.class)
    public void test_signUpNewOwner_existUser() throws Exception {
        systemMangerController.signUpNewOwner(owner,"ro","cy","rocy@gmail.com");
        systemMangerController.signUpNewOwner(owner,"ro","cy","rocy@gmail.com");

    }

    //permanentlyCloseTeam
    @Test
    public void test_permanentlyCloseTeam() throws Exception {
        Team team = TeamOwnerController.addNewTeamToSystem(owner,"sahasTeam");
        assertTrue(systemController.systemTeams.contains(team));
        assertTrue(team.getState()!= TeamState.permanentlyClosed);
        systemMangerController.permanentlyCloseTeam(team);
        assertTrue(team.getState()== TeamState.permanentlyClosed);
        assertTrue(systemController.archivedTeams.contains(team));
    }

    @Test
    public void test_closeComplaint(){
        Fan fan = new Fan("ury","123","ury","ury","ury@gmail.com");
        Complaint complaint = new Complaint(fan,"nonoon");
        SystemManager systemManager = new SystemManager("riki","123","riki","blich","riki@gmail.com");
        systemMangerController.closeComplaint(systemManager,complaint);
    }


}
