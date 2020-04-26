package Acceptness;

import domain.BusinessLayer.Users.Coach;
import domain.BusinessLayer.Users.Footballer;
import domain.BusinessLayer.Users.Owner;
import domain.BusinessLayer.Users.TeamManager;
import domain.DB.SystemController;
import domain.ServiceLayer.Controllers.SystemMangerController;
import domain.ServiceLayer.Controllers.TeamOwnerController;
import domain.BusinessLayer.Enum.CoachPosition;
import domain.BusinessLayer.Enum.FieldType;
import domain.BusinessLayer.Enum.FootballerPosition;
import domain.BusinessLayer.Enum.TeamState;
import domain.BusinessLayer.Football.Field;
import domain.BusinessLayer.Football.Team;
import org.junit.*;

import java.util.HashMap;
import java.util.HashSet;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;


public class UC6 {

    private static Owner owner;
    private static TeamOwnerController teamOwnerController;
    private static Team newTeam;
    private static TeamManager teamManager;



    @BeforeClass
    public static void beforeClass() throws Exception {
        owner = new Owner("inbar123","123","inbar","tzur","inbartzur@gmail.com");
        teamOwnerController = new TeamOwnerController();
        newTeam = teamOwnerController.addNewTeamToSystem(owner,"Macabi");
        teamManager = new TeamManager("theMan","12345","the","man","theMan@gmail.com");
    }


    //UC 6.1
    @Test
    public void test_UC6_1_acceptnace() {
        boolean teamName = this.newTeam.getTeamName().equals("Macabi");
        boolean existInSystem = SystemController.systemTeams.stream().anyMatch(team -> team.getTeamName().equals("Macabi"));
        assertTrue(teamName && existInSystem);
    }

    @Test(expected = Exception.class)
    public void test_UC6_1_nonAcceptnace() throws Exception {
        Team extraTeam = teamOwnerController.addNewTeamToSystem(owner,"Macabi");
    }

    @Test(expected = Exception.class)
    public void test_UC6_1_nonAcceptnace_nonpramision() throws Exception {
        teamOwnerController.addNewTeamToSystem(teamManager,"gogo");
    }



    //UC 6.1.1
    @Test
    //team owner
    public void test_UC6_1_1_field_acceptnace() throws Exception {
        Field field = new Field(500,"tel-aviv","blomfild", FieldType.Tournament);
        assertTrue(teamOwnerController.addFieldToTeam(owner,newTeam,field));
        teamOwnerController.removeFieldFromTeam(owner,newTeam,field);
    }

    @Test
    public void test_UC6_1_1_teamMamber_acceptnace() throws Exception {
        Footballer footballer = new Footballer("at","1992","asaf","tzur","asaf@gmail.com",FootballerPosition.Center_Back);
        assertTrue(teamOwnerController.addMemberToTeam(owner,newTeam,footballer));
    }

    @Test(expected = Exception.class)
    public void test_UC6_1_1_field_nonAcceptnace() throws Exception {
        Field field = new Field(500,"tel-aviv","blomfild", FieldType.Tournament);
        assertTrue(teamOwnerController.addFieldToTeam(teamManager,this.newTeam,field));
    }



    //UC 6.1.2
    @Test
    public void test_UC6_1_2_field_acceptnace() throws Exception {
        Field field = new Field(700,"Ber Sheva","terner",FieldType.Tournament);
        teamOwnerController.addFieldToTeam(owner,newTeam,field);
        assertTrue(teamOwnerController.removeFieldFromTeam(owner,newTeam,field));
    }

    @Test
    public void test_UC6_1_2_teamMamber_acceptnace() throws Exception {
        Footballer footballer = new Footballer("sh","1990","sharon","tzur","sharon@gmail.com",FootballerPosition.Center_Back);
        teamOwnerController.addMemberToTeam(owner,newTeam,footballer);
        assertTrue(teamOwnerController.removeMemberFromTeam(owner,newTeam,footballer));
    }

    @Test(expected = Exception.class)
    public void test_UC6_1_2_nonAcceptnace() throws Exception {
        Footballer footballer = new Footballer("sh","1990","sharon","tzur","sharon@gmail.com",FootballerPosition.Center_Back);
        teamOwnerController.addMemberToTeam(owner,newTeam,footballer);
        teamOwnerController.removeMemberFromTeam(teamManager,newTeam,footballer);
    }

    @Test(expected = Exception.class)
    public void test_UC6_1_2_nonAcceptnace_noAssetToRemove() throws Exception {
        Footballer footballer = new Footballer("sh","1990","sharon","tzur","sharon@gmail.com",FootballerPosition.Center_Back);
        teamOwnerController.removeMemberFromTeam(owner,newTeam,footballer);
    }


    //UC 6.1.3
    @Test
    public void test_UC6_1_3_acceptnace() throws Exception {
        Coach coach = new Coach("coachAmit","123","amit","dahan","coachAmit@gmail.com", CoachPosition.Fitness);
        teamOwnerController.addMemberToTeam(owner,newTeam,coach);
        HashMap info = new HashMap();
        info.put("email","amitTheCoach@gmail.com");
        teamOwnerController.editAssetOfTeam(owner,coach,info);
        assertTrue(coach.getEmail().equals("amitTheCoach@gmail.com"));
    }

    @Test(expected = Exception.class)
    public void test_UC6_1_3_nonAcceptnace() throws Exception {
        Coach coach = new Coach("coachAmit","123","amit","dahan","coachAmit@gmail.com", CoachPosition.Fitness);
        teamOwnerController.addMemberToTeam(owner,newTeam,coach);
        HashMap info = new HashMap();
        info.put("email","amitTheCoach@gmail.com");
        teamOwnerController.editAssetOfTeam(teamManager,coach,info);

    }

    @Test(expected = Exception.class)
    public void test_UC6_1_3_nonAcceptnace_noAsset() throws Exception {
        teamOwnerController.editAssetOfTeam(owner,null,new HashMap<>());

    }



    //UC 6.2
    @Test
    public void test_UC6_2_addTeamOwner_acceptnace() throws Exception {
        Footballer footballer = new Footballer("sh","1990","sharon","tzur","sharon@gmail.com",FootballerPosition.Center_Back);
        boolean added = teamOwnerController.addUserAsTeamOwner(owner,newTeam,footballer);
        HashSet<Owner> teamOwners = newTeam.getTeamOwners();
        boolean inList = false;
        for(Owner teamOwner : teamOwners){
            if(teamOwner.getUserName().equals(footballer.getUserName())){
                inList = true ;
            }
        }
        assertTrue(added&&inList);
    }

    @Test(expected = Exception.class)
    public void test_UC6_2_addTeamOwner_nonAcceptnace_noPremitions() throws Exception {
        Owner teamOwner = new Owner("tomer","123","tomer","godeli","tomgo@gmail.com");
        teamOwnerController.addUserAsTeamOwner(teamManager,newTeam,teamOwner);
    }

    @Test(expected = Exception.class)
    public void test_UC6_2_addTeamOwner_nonAcceptnace_alredyTeamOwner() throws Exception {
        Owner newOwner = new Owner("sapiralp","123","sapir","alp","alp@gmail.com");
        teamOwnerController.addUserAsTeamOwner(owner,newTeam,newOwner);
        teamOwnerController.addUserAsTeamOwner(owner,newTeam,newOwner);
    }



    //UC 6.3
    @Test
    public void test_UC6_3_acceptnce() throws Exception {
        TeamManager newOwner = new TeamManager("e","e","e","e","e@gmail.com");
        teamOwnerController.addUserAsTeamOwner(owner,newTeam,newOwner);
        HashSet<Owner> teamOwners = newTeam.getTeamOwners();
        Owner toRemove = null;
        for(Owner ownerInTeam : teamOwners){
            if(ownerInTeam.getEmail().equals("e@gmail.com")){
                toRemove = ownerInTeam;
            }
        }
        teamOwnerController.removeTeamOwner(owner,newTeam,toRemove);
        assertFalse(newTeam.getTeamOwners().contains(toRemove));
    }

    @Test(expected = Exception.class)
    public void test_UC6_3_nonAcceptnce_noPremitions() throws Exception {
        teamOwnerController.removeTeamOwner(teamManager,newTeam,null);
    }
    @Test(expected = Exception.class)
    public void test_UC6_3_nonAcceptnce_ownerNotExsist() throws Exception {
        Owner ownerToRemove = new Owner("a","a","a","a","a@gmail.com");
        teamOwnerController.removeTeamOwner(owner,newTeam,ownerToRemove);
    }


    //UC6.4
    @Test
    public void test_UC6_4_acceptnce() throws Exception {
        //TeamManager teamManagerToAdd = new TeamManager("itaiD","12345678","itai","dagan","itaiD@gmail.com");
        teamOwnerController.signUpNewTeamManager(owner,"itai","dagan","itaiD@gmail.com",newTeam);
        HashSet<TeamManager> teamManagers = newTeam.getTeamManagers();
        boolean existInTeam = false;
        for (TeamManager teamManager : teamManagers){
            if(teamManager.getEmail().equals("itaiD@gmail.com")){
                existInTeam = true;
            }
        }
        assertTrue(existInTeam);
    }

    @Test(expected = Exception.class)
    public void test_UC6_4_nonAccaptnce_noPramitions() throws Exception {
        teamOwnerController.signUpNewTeamManager(teamManager,"r","r","r@gamil.com",newTeam);
    }

    @Test(expected = Exception.class)
    public void test_UC6_4_nonAccaptnce_emailNotValid() throws Exception {
        teamOwnerController.signUpNewTeamManager(owner,"inbar","tzur","inbar",newTeam);
    }

    @Test
    public void test_UC6_4_nonAccaptnce_UserExist() throws Exception {
        teamOwnerController.signUpNewTeamManager(owner,"inbar","tzur","inbartzur@gmail.com",newTeam);
    }



    //UC6.5
    @Test
    public void test_UC6_5_acceptnce() throws Exception {
        teamOwnerController.signUpNewTeamManager(owner,"r","r","r@gamil.com",newTeam);
        HashSet<TeamManager> teamManagers = newTeam.getTeamManagers();
        TeamManager teamManagerToRemove=null ;
        for(TeamManager teamManager : teamManagers){
            if(teamManager.getEmail().equals("r@gamil.com"));
            teamManagerToRemove = teamManager;
        }
        assertTrue(teamOwnerController.removeTeamManager(owner,newTeam,teamManagerToRemove));
    }

    @Test(expected = Exception.class)
    public void test_UC6_5_nonAcceptnce_notTeamManager() throws Exception {
        teamOwnerController.removeMemberFromTeam(owner,newTeam,new Footballer("w","w","w","w","w",FootballerPosition.Attacking_Midfielder));
    }

    @Test(expected = Exception.class)
    public void test_UC6_5_nonAcceptnce_noPremitions() throws Exception {
        teamOwnerController.signUpNewTeamManager(owner,"r","r","r@gamil.com",newTeam);
        HashSet<TeamManager> teamManagers = newTeam.getTeamManagers();
        TeamManager teamManagerToRemove=null ;
        for(TeamManager teamManager : teamManagers){
            if(teamManager.getEmail().equals("r@gamil.com"));
            teamManagerToRemove = teamManager;
        }
        teamOwnerController.removeMemberFromTeam(teamManager,newTeam,teamManagerToRemove);
    }


    //UC 6.6.1
    @Before
    public void before_UC6_6_1_accept() throws Exception {
        if(newTeam.getState()==TeamState.notActive){
            teamOwnerController.openTeam(owner,newTeam);
        }
    }

    @Test
    public void test_UC6_6_1_acceptnce() throws Exception {
        assertTrue(teamOwnerController.closeTeam(owner,newTeam));
    }

    @Test(expected = Exception.class)
    public void test_UC6_6_1_nonAcceptnce_noPremitions() throws Exception {
        teamOwnerController.closeTeam(teamManager,newTeam);
    }

    @Test(expected = Exception.class)
    public void test_UC6_6_1_nonAcceptnce_TeamClose() throws Exception {
        if(newTeam.getState()==TeamState.active){
            teamOwnerController.closeTeam(owner,newTeam);
        }
        teamOwnerController.closeTeam(owner,newTeam);
    }


    //UC 6.6.2
    @Test
    public void test_UC6_6_2_acceptnce() throws Exception {
        if(newTeam.getState()==TeamState.active){
            teamOwnerController.closeTeam(owner,newTeam);
        }
        assertTrue(teamOwnerController.openTeam(owner,newTeam));
    }

    @Test(expected = Exception.class)
    public void test_UC6_6_2_nonAcceptnce_AlredyActive() throws Exception {
        Owner newOwner =new Owner("alisa","123","alisa","fingold","ali@gmail.com");
        Team team = teamOwnerController.addNewTeamToSystem(newOwner,"qweenB");
        teamOwnerController.openTeam(newOwner,team);
    }

    @Test(expected = Exception.class)
    public void test_UC6_6_2_nonAccaptnace_close() throws Exception {
        Owner newOwner =new Owner("alisa","123","alisa","fingold","ali@gmail.com");
        Team team = teamOwnerController.addNewTeamToSystem(newOwner,"snoky");
        SystemMangerController.permanentlyCloseTeam(team);
        teamOwnerController.openTeam(newOwner,team);

    }

    @Test(expected = Exception.class)
    public void test_UC6_6_2_nonAcceptnce_noPremitions() throws Exception {
        teamOwnerController.closeTeam(owner,newTeam);
        teamOwnerController.openTeam(teamManager,newTeam);
    }


    //UC 6.7
    @Test
    public void test_UC6_7_acceptnce() throws Exception {
        boolean financeAction = teamOwnerController.addFinanceAction(owner,newTeam,"income",1000,"new towl",27052019,teamManager);
        assertTrue(financeAction);
    }

    @Test(expected = Exception.class)
    public void test_UC6_7_nonAcceptnce() throws Exception {
        teamOwnerController.addFinanceAction(teamManager,newTeam,"income",1000,"new towl",27052019,owner);
    }




}
