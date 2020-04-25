package unit;

import domain.Controllers.SystemMangerController;
import domain.Controllers.TeamOwnerController;
import domain.Enums.FieldType;
import domain.Enums.FootballerPosition;
import domain.Enums.TeamState;
import domain.Impl.Field;
import domain.Impl.FinanceActivity;
import domain.Impl.Team;
import domain.Users.Fan;
import domain.Users.Footballer;
import domain.Users.Owner;
import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class TeamTest {

    private static Team team;
    private static Team team_extre;
    private static Owner owner;
    private static Field field1;
    private static Field field2;
    private static Footballer footballer;
    private static Fan fan;


    @BeforeClass
    public static void before_class() throws Exception {
        owner = new Owner("me","123","im","owner","im@gmail.com");
        team = new Team("macani", TeamState.active,owner);
        team_extre = new Team("hapoel",TeamState.active,owner);
        field1 = new Field(500,"tel-aviv","blomfild", FieldType.Tournament);
        footballer = new Footballer("at","1992","asaf","tzur","asaf@gmail.com", FootballerPosition.Center_Back);
        field2 = new Field(700,"Ber Sheva","terner",FieldType.Tournament);
        fan = new Fan("q","123","q","q","q@gmail.com");
        team_extre.addField(field2);

    }

    @Test
    public void test_addTeamMember() throws Exception {
        if(team.getTeamFootballers().contains(footballer)){
            team.removeTeamMember(footballer);
        }
        assertTrue(team.addTeamMember(owner,footballer)) ;
    }

    @Test(expected = Exception.class)
    public void test_addTeamMember_nonAcceptnce() throws Exception {
        team.addTeamMember(owner,fan);
    }

    @Test
    public void test_removeTeamMember() throws Exception {
        if(!team.getTeamFootballers().contains(footballer)){
            team.addTeamMember(owner,footballer);
        }
        assertTrue(team.removeTeamMember(footballer));
    }


    @Test
    public void test_addField() throws Exception {
        if(team.getFields().contains(field1)){
            team.removeField(field1);
        }
        assertTrue(team.addField(field1));
    }


    @Test(expected = Exception.class)
    public void test_addField_nonAcceptnce() throws Exception {
        team_extre.addField(field2);
    }


    @Test
    public void test_removeField() throws Exception {
        if(!team.getFields().contains(field1)){
            team.addField(field1);
        }
        assertTrue(team.removeField(field1));
    }

    @Test(expected = Exception.class)
    public void test_removeField_nonAcceptnce() throws Exception {
        team.removeField(field2);
    }

    @Test
    public void test_removeFinanceActivity() throws Exception {
        FinanceActivity financeActivity=new FinanceActivity("income",5000,"win",20200102,owner);
        assertTrue(team.removeFinanceActivity(financeActivity)&&team.addFinanceActivity(financeActivity));
    }
}
