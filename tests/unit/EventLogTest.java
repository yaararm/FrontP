package unit;

import domain.Enums.EventType;
import domain.Enums.RefereeTraining;
import domain.Impl.Event;
import domain.Impl.EventLog;
import domain.Impl.Game;
import domain.Users.Referee;
import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class EventLogTest {

    private static Game game;
    private static EventLog eventLog;


    @BeforeClass
    public static void Before_class(){
        game = new Game();
        eventLog = new EventLog(game);

    }

    @Test
    public void test_addEvent(){
        Referee referee = new Referee("w","w",2,"w","w","w@gmail.com", RefereeTraining.Medium);
        Event event = new Event(EventType.Offense,33,"inbar did offense",referee);
        assertTrue(eventLog.addEvent(event));
    }

    @Test
    public void test_getEvents(){
        assertTrue(eventLog.getEvents()!=null);
    }
}
