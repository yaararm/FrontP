package domain.Controllers;

import domain.Enums.EventType;
import domain.Enums.RefereeRole;
import domain.Impl.Event;
import domain.Impl.EventLog;
import domain.Impl.Game;
import domain.Users.Referee;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class RefereeController {
    //Use Case 10.1
    public boolean updateDetails(Referee referee, HashMap<String, String> valuesToUpdate) throws Exception {
        for (Map.Entry<String, String> entry : valuesToUpdate.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key.toLowerCase()) {
                case "password":
                    if(value.length() >= 6) {
                        String hashPassword = Utils.sha256(value);
                        referee.setPassword(hashPassword);
                        break;
                    }
                    else{
                        throw new Exception("Password not long enough");
                    }
                case "email":
                    if(EmailValidator.getInstance().isValid(value)){
                        referee.setEmail(value);
                        break;
                    }
                    else{
                        throw new Exception("Not Valid Email");
                    }
                case "first name":
                    referee.setFirstName(value);
                    break;
                case "last name":
                    referee.setLastName(value);
                    break;
            }
        }
        return true;
    }

    //Use Case 10.2
    public HashMap<Game, RefereeRole> showRefereeAssignedGames(Referee referee) throws Exception {
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
    public boolean addEventToGame(Referee referee, Game game, EventType eventType, int eventMinute, String description){
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
            for (Game game : games.get(role)) {
                long gameStart = game.getGameDate();
                long gameEnd = game.getGameDate()+ TimeUnit.MINUTES.toMillis(300);
                if (currentTime >= gameStart && currentTime <= gameEnd) {
                    relevantGames.add(game);
                }
            }
        }
        return relevantGames;
    }

    //Use Case 10.4.1 B
    public HashSet<Event> getGamesEventsForEdit(Referee referee, Game game){
        HashSet<Event> events = game.getEventLog().getEvents();
        return events;
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


    //todo thonk how to send that
    //Use Case 10.4.2
    public EventLog createGameReport(Referee referee, Game game) {
        return game.getEventLog();
    }



}
