package unit;

import domain.Enums.EventType;
import domain.Enums.RefereeTraining;
import domain.Enums.TeamState;
import domain.Impl.*;
import domain.Users.Owner;
import domain.Users.Referee;
import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class EventLogTest {

    private static Game game;
    private static EventLog eventLog;


    @BeforeClass
    public static void Before_class(){
        Season season = new Season(2020,12042020);
        Owner owner1 = new Owner("ron","123","ron","niceman","ronen@gmail.com");
        Owner owner2 = new Owner("dan","123","dan","glizman","dannon@gmail.com");
        Team team1= new Team("beitarA", TeamState.active,owner1);
        Team team2 = new Team("galilH",TeamState.active,owner2);
        game = new Game(season,team1,team2);
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
