package unit;

import BusinessLayer.Football.Team;
import BusinessLayer.Users.Footballer;
import BusinessLayer.Users.Owner;
import org.junit.Test;

import static BusinessLayer.Enum.FootballerPosition.Striker;
import static BusinessLayer.Enum.TeamState.active;
import static junit.framework.TestCase.assertTrue;

public class TeamUserTest {

    Footballer zlatan = new Footballer("zlatan@gmail.com","456773195", "zlatan","ibrahimovitz","zlatan@gmail.com",Striker);
    Owner tw = new Owner("delbusk12e@gmail.com", "192837465", "del", "buske", "delbuske12@gmail.com");
    Team rm = new Team("real_madrid1", active, tw);
    @Test
    public void test_AddOrRemove(){

     assertTrue(  zlatan.addTeam(rm,tw));
     assertTrue(zlatan.removeTeam(rm));


    }
}
