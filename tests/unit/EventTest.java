package unit;

import domain.BusinessLayer.Enum.EventType;
import domain.BusinessLayer.Enum.RefereeTraining;
import domain.BusinessLayer.Football.Event;
import domain.BusinessLayer.Users.Referee;
import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class EventTest {

    private static Event event;

    @BeforeClass
    public static void before_class(){
        Referee referee = new Referee("refe","123",1,"r","f","rf@gmail.com", RefereeTraining.Expert);
        event = new Event(EventType.Goal,47,"was gol",referee);

    }
    //test function - editEventMinute
    @Test
    public void test_editEventMinute(){
        boolean beforeChainge = event.getEventMinute()==47;
        event.editEventMinute(53);
        assertTrue(event.getEventMinute()==53 && beforeChainge);
    }

    @Test
    public void test_editDescription(){
        boolean before = event.getDescription().equals("was gol");
        event.editDescription("the gol was not valid");
        assertTrue(event.getDescription().equals("the gol was not valid")&&before);
    }
}
