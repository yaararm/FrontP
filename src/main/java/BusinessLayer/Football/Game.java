package BusinessLayer.Football;

import BusinessLayer.Enum.RefereeRole;
import BusinessLayer.SystemFeatures.EventLog;
import BusinessLayer.Users.Fan;
import BusinessLayer.Users.Referee;
import BusinessLayer.Enum.FieldType;

import java.util.ArrayList;
import java.util.List;


public class Game {

    private Season season;
    private Field field;
    private Team homeTeam;
    private Team awayTeam;
    private long gameDate;
    private EventLog eventLog;
    private Referee mainReferee;
    private Referee secondaryReferee1;
    private Referee secondaryReferee2;
    private int state;
    private List<Fan> fansObserver = new ArrayList();
    private int homeScore=0;
    private int awayScore=0;

//    //ToDO delete
//    public Game() {
//    }

    public Game(Season season, Team homeTeam, Team awayTeam) {
        this.season = season;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.eventLog = new EventLog(this);
        for (Field f :homeTeam.getFields() ) {
            if(f.getFieldType() == FieldType.Tournament){
                this.field=f;
                break;
                //ToDo what if team have no tournament fields?
            }
        }
    }

    public Season getSeason() {
        return season;
    }

    public Field getField() {
        return field;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public Referee getMainReferee() {
        return mainReferee;
    }

    public Referee getSecondaryReferee1() {
        return secondaryReferee1;
    }

    public Referee getSecondaryReferee2() {
        return secondaryReferee2;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        notifyAllObservers();
    }

    public void setField(Field field) {
        this.field = field;
    }

    public void setGameDate(long gameDate) {
        this.gameDate = gameDate;
    }

    public void setMainReferee(Referee mainReferee) {
        this.mainReferee = mainReferee;
    }

    public void setSecondaryReferee1(Referee secondaryReferee1) {
        this.secondaryReferee1 = secondaryReferee1;
    }

    public void setSecondaryReferee2(Referee secondaryReferee2) {
        this.secondaryReferee2 = secondaryReferee2;
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

    public List<Fan> getFansObserver() {
        return fansObserver;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }
}

