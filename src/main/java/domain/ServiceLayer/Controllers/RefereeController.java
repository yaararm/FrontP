package domain.ServiceLayer.Controllers;

import domain.BusinessLayer.Enum.EventType;
import domain.BusinessLayer.Enum.RefereeRole;
import domain.BusinessLayer.Football.Event;
import domain.BusinessLayer.SystemFeatures.EventLog;
import domain.BusinessLayer.Football.Game;
import domain.BusinessLayer.Users.Referee;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RefereeController {

    //Use Case 10.2
    public HashMap<Game, RefereeRole> showRefereeAssignedGames(Referee referee) {
        HashMap<RefereeRole, HashSet<Game>> games = referee.getGames();
        HashMap <Game, RefereeRole> relevantGames = new HashMap<>();
        long currentTime =System.currentTimeMillis();
        for (RefereeRole role: games.keySet()) {
            for (Game game : games.get(role)) {
                if (game.getGameDate()>=currentTime) {
                    relevantGames.put(game, role);
                }
            }
        }
        return relevantGames;
    }

    //Use Case 10.3 A
    public HashSet<Game> getCurrentGames(Referee referee){
        HashMap<RefereeRole, HashSet<Game>> games = referee.getGames();
        long currentTime =System.currentTimeMillis();
        HashSet <Game> relevantGames = new HashSet<>();
        for (RefereeRole role: games.keySet()) {
            for (Game game : games.get(role)) {
                long gameStart = game.getGameDate();
                long gameEnd = game.getGameDate()+ TimeUnit.MINUTES.toMillis(90);
                if (currentTime >= gameStart && currentTime <= gameEnd) {
                    relevantGames.add(game);
                }
            }
        }
        return relevantGames;
    }


    //Use Case 10.3 B
    public boolean addEventToCurrentGame(Referee referee, Game game, EventType eventType, int eventMinute, String description){
        Event event = new Event(eventType, eventMinute, description, referee);
        game.getEventLog().addEvent(event);
        return true;
    }


    //Use Case 10.4.1 A
    public HashSet<Game> getGamesForEdit(Referee referee){
        HashMap<RefereeRole, HashSet<Game>> games = referee.getGames();
        long currentTime =System.currentTimeMillis();
        HashSet <Game> relevantGames = new HashSet<>();
        for (RefereeRole role: games.keySet()) {
            if(role.equals(RefereeRole.Main)){
                for (Game game : games.get(role)) {
                    long gameStart = game.getGameDate()+ TimeUnit.MINUTES.toMillis(90);
                    long gameEnd = game.getGameDate()+ TimeUnit.MINUTES.toMillis(390);
                    if (currentTime >= gameStart && currentTime <= gameEnd) {
                        relevantGames.add(game);
                    }
                }
            }

        }
        return relevantGames;
    }

    //Use Case 10.4.1 B
    public HashSet<Event> getGamesEventsForEdit(Referee referee, Game game){
        return game.getEventLog().getEvents();
    }

    //Use Case 10.4.1 C
    public boolean editGameEvent(Referee referee, Event event, HashMap<String, String> valuesToUpdate) {
        for (Map.Entry<String, String> entry : valuesToUpdate.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key.toLowerCase()) {
                case "description":
                    event.editDescription(value);
                    break;
                case "eventminute":
                    event.editEventMinute(Integer.parseInt(value));
                    break;
            }
        }
        return true;
    }

    //Use Case 10.4.2
    public EventLog createGameReport(Referee referee, Game game) throws Exception {
        if(!(game.getMainReferee().equals(referee))){
            throw new Exception("Not Main Referee Of This Game");
        }
        return game.getEventLog();
    }


}
