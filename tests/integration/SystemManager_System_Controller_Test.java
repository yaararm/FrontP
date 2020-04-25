package integration;

import domain.Controllers.SystemController;
import domain.Controllers.SystemMangerController;
import domain.Controllers.TeamOwnerController;
import domain.Enums.TeamState;
import domain.Impl.Team;
import domain.Users.Owner;
import domain.Users.User;
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

    //permanentlyCloseTeam
    @Test
    public void test_permanentlyCloseTeam() throws Exception {
        Team team = TeamOwnerController.addNewTeamToSystem(owner,"sahasTeam");
        assertTrue(systemController.systemTeams.contains(team));
        assertTrue(team.getState()!= TeamState.permanentlyClosed);
        systemMangerController.permanentlyCloseTeam(team);
        assertTrue(team.getState()== TeamState.permanentlyClosed);
    }


}
