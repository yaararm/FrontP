package integration;

import domain.Controllers.*;
import domain.Enums.EventType;
import domain.Enums.RefereeRole;
import domain.Enums.RefereeTraining;
import domain.Impl.*;
import domain.Users.AssociationRepresentative;
import domain.Users.Owner;
import domain.Users.Referee;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class RefereeControllerTest {
    //Class Fields
    static RefereeController rc;
    static SystemMangerController smc;
    private static Referee r;
    private static Referee r2;
    private static Game gameNow;
    private static Game gameTomorrow;
    private static Game gameFinished;


    @BeforeClass
    public static void setUp() throws Exception {
        rc = new RefereeController();
        smc = new SystemMangerController();
        AssociationRepresentativeController ac = new AssociationRepresentativeController();
        AssociationRepresentative assRep = new AssociationRepresentative("rep1", "1234", "avi", "cohen", "avi12@football.com");

        //define  a league
        ac.defineNewLeague(assRep, "league12", RefereeTraining.Begginer);
        League l = SystemController.leagueNameLeagues.get("league12");

        //define season
        ac.addSeasonToLeague(assRep, l, 2001, System.currentTimeMillis() * 3);
        Season s = l.getLeaguesSeasons().get(2001);

        //add referee to system
        ac.appointReferee(assRep, 524323412, "shofet", "aaa", "aaa12@ref.com", RefereeTraining.Begginer);
        r = (Referee) SystemController.userNameUser.get("aaa12@ref.com");
        ac.appointReferee(assRep, 555555512, "shofet2", "aaa2", "aaa212@ref.com", RefereeTraining.Begginer);
        r2 = (Referee) SystemController.userNameUser.get("aaa212@ref.com");
        //add referee to season
        ac.setRefereeToSeason(assRep, s, r);
        ac.setRefereeToSeason(assRep, s, r2);

        Owner o = smc.signUpNewOwner(null, "moshe", "cohen", "moshe12@gmail.com");

        //define 2 reams
        Team t1 = TeamOwnerController.addNewTeamToSystem(o, "t112");
        Team t2 = TeamOwnerController.addNewTeamToSystem(o, "t212");
        Team t3 = TeamOwnerController.addNewTeamToSystem(o, "t312");

        //add teams to season
        HashSet<Team> teams = new HashSet<>();
        teams.add(t1);
        teams.add(t2);
        teams.add(t3);
        ac.setSeasonsTeams(assRep, s, teams);

        //assign games
        ac.assignGames(assRep, s);


        //add games to referee
        HashSet<Game> g1 = s.getGames().get(1);
        HashSet<Game> g2 = s.getGames().get(2);
        HashSet<Game> g3 = s.getGames().get(3);
        for (Game game : g1) {
            gameNow = game;
        }
        for (Game game : g2) {
            gameTomorrow = game;
        }
        for (Game game : g3) {
            gameFinished = game;
        }

        for (Game game : g1) {
            game.setGameDate(System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(45));
            game.setMainReferee(r);
            r.addGame(RefereeRole.Main, game);
            game.setSecondaryReferee1(r2);
            r.addGame(RefereeRole.Secondary, game);


        }
        for (Game game : g2) {
            game.setGameDate(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(600));
            game.setMainReferee(r);
            r.addGame(RefereeRole.Main, game);
            game.setSecondaryReferee1(r2);
            r.addGame(RefereeRole.Secondary, game);
        }
        for (Game game : g3) {
            game.setGameDate(System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(200));
            game.setMainReferee(r);
            r.addGame(RefereeRole.Main, game);
            game.setSecondaryReferee1(r2);
            r.addGame(RefereeRole.Secondary, game);
        }
    }

    @Test
    public void Test_updateDetails() {
        try {
            HashMap<String, String> newVal = new HashMap<>();
            newVal.put("Password", "123456");
            newVal.put("first name", "moshik");
            newVal.put("last name", "afia");
            SignedInController.updateDetails(r, newVal);
            assertEquals(Utils.sha256("123456"), SystemController.userNameUser.get(r.getEmail()).getPassword());
            assertEquals("moshik", SystemController.userNameUser.get(r.getEmail()).getFirstName());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


    @Test
    public void Test_getCurrentGames() {
        HashSet<Game> games = rc.getCurrentGames(r);
        assertEquals(1, games.size());
    }

    @Test
    public void Test_addEventToCurrentGame() {
        HashSet<Game> games = rc.getCurrentGames(r);
        for (Game game : games) {
            rc.addEventToCurrentGame(r, game, EventType.Goal, 23, "now");
            boolean event = false;
            for (Event ev : game.getEventLog().getEvents()) {
                if (ev.getDescription().equals("now")) {
                    event = true;
                    break;
                }
            }
            assertTrue(event);
        }
    }

    @Test
    public void Test_getGamesForEdit() {
        HashSet<Game> games = rc.getGamesForEdit(r);
        assertEquals(1, games.size());
    }

    @Test
    public void Test_getGamesEventsForEdit() {
        HashSet<Game> games = rc.getGamesForEdit(r);
        assertEquals(1,games.size());
        for (Game game : games) {
            HashSet<Event> gameEvents = rc.getGamesEventsForEdit(r, game);
            assertEquals(2, gameEvents.size());
            for (Event event : gameEvents) {
                assertEquals("bbb", event.getDescription());
            }
        }


    }

    @Test
    public void Test_editGameEvent() {
        HashSet<Game> games = rc.getGamesForEdit(r);
        for (Game game : games) {
            HashSet<Event> gameEvents = rc.getGamesEventsForEdit(r, game);
            rc.addEventToCurrentGame(r, game, EventType.Goal, 23, "aaa");
            rc.addEventToCurrentGame(r, game, EventType.Offense, 23, "aaa");
            HashMap<String, String> changes = new HashMap<>();
            changes.put("description", "bbb");
            changes.put("eventminute", "88");
            for (Event event : gameEvents) {
                assertEquals("aaa", event.getDescription());
                assertEquals(23, event.getEventMinute());

                rc.editGameEvent(r, event, changes);
                assertEquals("bbb", event.getDescription());
                assertEquals(88, event.getEventMinute());
            }
        }

    }

    @Test
    public void Test_createGameReport() throws Exception {
        HashSet<Game> games = rc.getGamesForEdit(r);
        for (Game game : games) {
            EventLog gameEventsLog = rc.createGameReport(r, game);
            assertEquals(2, gameEventsLog.getEvents().size());

        }
    }


}

