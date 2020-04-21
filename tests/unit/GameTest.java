package unit;

import domain.Impl.Game;
import domain.Users.Fan;
import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class GameTest {

    private static Game game;
    private static Fan fan;

    @BeforeClass
    public static void befort_class(){
        game = new Game();
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