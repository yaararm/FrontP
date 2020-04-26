package BusinessLayer.SystemFeatures;

import BusinessLayer.Football.Event;
import BusinessLayer.Football.Game;

import java.util.HashSet;

public class EventLog {
    private Game game;
    private HashSet<Event> events;

    public EventLog(Game game) {
        this.game = game;
        events = new HashSet<>();
    }
     public boolean addEvent(Event event){
         events.add(event);
         return true;
     }

    public HashSet<Event> getEvents() {
        return events;
    }
}
