package integration;

import domain.DB.SystemController;
import domain.ServiceLayer.Controllers.TeamOwnerController;
import domain.BusinessLayer.Enum.CoachPosition;
import domain.BusinessLayer.Enum.FootballerPosition;
import domain.BusinessLayer.Football.Team;
import domain.BusinessLayer.Users.Coach;
import domain.BusinessLayer.Users.Footballer;
import domain.BusinessLayer.Users.Owner;
import domain.BusinessLayer.Users.TeamManager;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TeamOwner_System_Controller_Test {

    private static Owner owner;
    private static TeamOwnerController teamOwnerController;
    private static SystemController systemController;
    private static Team team;
    private static TeamManager teamManager;

    @BeforeClass
    public static void beforeClass() throws Exception {
        owner = new Owner("fibi","123","fi","bi","fibi@gmail.com");
        teamOwnerController = new TeamOwnerController();
        systemController = new SystemController();
        team = teamOwnerController.addNewTeamToSystem(owner,"elinirsTeam");
        teamManager = teamOwnerController.signUpNewTeamManager(owner,"monika","geler","monic@gmail.com",team);
    }

    //addNewTeamToSystem
    @Test
    public void test_addNewTeamToSystem(){
        assertNotNull(team);
        assertTrue(systemController.systemTeams.contains(team));
    }

    //signUpNewFootballer
    @Test
    public void test_signUpNewFootballer() throws Exception {
        Footballer footballer = teamOwnerController.signUpNewFootballer(owner,"jojo","jo","jojo@gmail.com",FootballerPosition.Attacking_Midfielder,team);
        assertTrue(team.getTeamFootballers().contains(footballer));
        assertTrue(systemController.userNameUser.containsKey("jojo@gmail.com"));
    }

    @Test(expected = Exception.class)
    public void test_signUpNewFootballer_notValidEmail() throws Exception {
        teamOwnerController.signUpNewFootballer(owner,"jojo","jojo","jojo",FootballerPosition.Attacking_Midfielder,team);
    }

    @Test(expected = Exception.class)
    public void test_signUpNewFootballer_existUser() throws Exception {
        teamOwnerController.signUpNewFootballer(owner,"omer","adam","oa@gmail.com",FootballerPosition.Attacking_Midfielder,team);
        teamOwnerController.signUpNewFootballer(owner,"omer","adam","oa@gmail.com",FootballerPosition.Attacking_Midfielder,team);

    }

    //signUpNewCoach
    @Test
    public void test_signUpNewCoach() throws Exception {
        Coach coach = teamOwnerController.signUpNewCoach(owner,"ros","theman","ros@gmail.com", CoachPosition.Fitness,team);
        assertTrue(team.getTeamCoaches().contains(coach));
        assertTrue(systemController.userNameUser.containsKey("ros@gmail.com"));
    }

    @Test(expected = Exception.class)
    public void test_signUpNewCoach_notValidEmail() throws Exception {
        teamOwnerController.signUpNewCoach(owner,"rosy","theman","rosgmail.com", CoachPosition.Fitness,team);
    }

    @Test(expected = Exception.class)
    public void test_signUpNewCoach_exist() throws Exception {
        teamOwnerController.signUpNewCoach(owner,"mushe","peretz","mosh@gmail.com",CoachPosition.Fitness,team);
        teamOwnerController.signUpNewCoach(owner,"mushe","peretz","mosh@gmail.com",CoachPosition.Fitness,team);

    }

    //signUpNewTeamManager
    @Test
    public void test_signUpNewTeamManager(){
        assertTrue(team.getTeamManagers().contains(teamManager));
        assertTrue(systemController.userNameUser.containsKey("monic@gmail.com"));
    }

    //removeTeamManager
    @Test
    public void test_removeTeamManager() throws Exception {
        if(!team.getTeamManagers().contains(teamManager)){
            team.addTeamMember(teamManager);
        }
        teamOwnerController.removeTeamManager(owner,team,teamManager);
    }







}
