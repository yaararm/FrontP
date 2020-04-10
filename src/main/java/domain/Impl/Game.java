package domain.Impl;

import domain.Enums.RefereeRole;
import domain.Users.Fan;
import domain.Users.Referee;

import java.util.ArrayList;

import java.util.List;


public class Game {

    private List<Fan> fansObserver = new ArrayList();
    private int state;
    private Referee mainReferee;
    private Referee secondaryReferee1;
    private Referee secondaryReferee2;
    private long gameDate;
    private EventLog eventLog;


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        notifyAllObservers();
    }

    public boolean attachObserver(Fan fan) {
        if (fansObserver.add(fan))
            return true;
        return false;
    }

    public boolean removeObserver(Fan fan) {
        if (fansObserver.remove(fan))
            return true;
        return false;
    }

    public boolean checkObserver(Fan fan) {
        if (fansObserver.contains(fan))
            return true;
        return false;
    }

    public void notifyAllObservers() {
        for (Fan fan : fansObserver) {
            fan.update();
        }
    }

    public boolean removeReferee(Referee referee, RefereeRole refereeRole) {
        switch (refereeRole) {
            case Main:
                if (mainReferee.getId() == referee.getId()) {
                    mainReferee = null;
                    return true;
                }
            case Secondary:
                if (secondaryReferee1.getId() == referee.getId()) {
                    secondaryReferee1 = null;
                    return true;
                } else if (secondaryReferee2.getId() == referee.getId()) {
                    secondaryReferee2 = null;
                    return true;
                }

        }
        return false;
    }

    public long getGameDate() {
        return gameDate;
    }

    public EventLog getEventLog() {
        return eventLog;
    }
}
