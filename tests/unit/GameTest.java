package unit;

import domain.Controllers.TeamOwnerController;
import domain.Enums.TeamState;
import domain.Impl.Game;
import domain.Impl.Season;
import domain.Impl.Team;
import domain.Users.Fan;
import domain.Users.Owner;
import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class GameTest {

    private static Game game;
    private static Fan fan;

    @BeforeClass
    public static void befort_class(){
        Season season = new Season(2020,12042020);
        Owner owner1 = new Owner("ron","123","ron","niceman","ron@gmail.com");
        Owner owner2 = new Owner("dan","123","dan","glizman","dan@gmail.com");
        Team team1= new Team("beitar", TeamState.active,owner1);
        Team team2 = new Team("galil",TeamState.active,owner2);
        game = new Game(season,team1,team2);
        fan = new Fan("fan1","123","the","fan","thefan@gmail.com");
    }

    @Test
    public void test_attachObserver(){
        if(game.checkObserver(fan)){
            game.removeObserver(fan);
        }
        assertTrue(game.attachObserver(fan));
    }

    @Test
    public void test_removeObserver(){
        if(!game.checkObserver(fan)){
            game.attachObserver(fan);
        }
        assertTrue(game.removeObserver(fan));
    }


    //no implamantation
    @Test
    public void test_notifyAllObservers(){

    }

    //how to add referee???
    @Test
    public void test_removeReferee(){

    }


}
