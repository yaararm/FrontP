package domain.Impl;

import domain.Enums.EventType;
import domain.Users.Referee;

public class Event {
    private EventType eventType;
    private int eventMinute;
    private String description;
    private Referee referee;

    public Event(EventType eventType, int eventMinute, String description, Referee referee) {
        this.description=description;
        this.eventMinute=eventMinute;
        this.eventType=eventType;
        this.referee=referee;
    }

    public void editEventMinute(int eventMinute) {
        this.eventMinute = eventMinute;
    }

    public void editDescription(String description) {
        this.description = description;
    }

    public int getEventMinute() {
        return eventMinute;
    }

    public String getDescription() {
        return description;
    }
}
