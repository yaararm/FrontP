package Acceptness;

import domain.Controllers.*;
import domain.Enums.EventType;
import domain.Enums.RefereeRole;
import domain.Enums.RefereeTraining;
import domain.Enums.TeamState;
import domain.Impl.*;
import domain.Users.AssociationRepresentative;
import domain.Users.Owner;
import domain.Users.Referee;
import org.junit.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;


public class UC10 {
    //Class Fields
    static RefereeController rc;
    private static Referee r;
    private static Referee r2;
    private static Game g1;
    private static Game g2;
    private static Game g3;
    private static Game g4;


    @BeforeClass
    public static void setUp() throws Exception {
        rc = new RefereeController();
        AssociationRepresentativeController ac = new AssociationRepresentativeController();
        AssociationRepresentative assRep = new AssociationRepresentative("rep1", "1234", "avi", "cohen", "avi@football.com");


        //define  a league
        ac.defineNewLeague(assRep, "league", RefereeTraining.Begginer);
        League l = SystemController.leagueNameLeagues.get("league");

        //define season
        ac.addSeasonToLeague(assRep, l, 2001, 1234567890);
        Season s = l.getLeaguesSeasons().get(2001);

        //add referee to system
        ac.appointReferee(assRep, 524323454, "shofet", "aaa", "aaa@ref.com", RefereeTraining.Begginer);
        r = (Referee) SystemController.userNameUser.get("aaa@ref.com");

        ac.appointReferee(assRep, 555555555, "shofet2", "aaa2", "aaa2@ref.com", RefereeTraining.Begginer);
        r2 = (Referee) SystemController.userNameUser.get("aaa2@ref.com");
        //add referee to season
        ac.setRefereeToSeason(assRep, s, r);

        Owner o = new Owner("moshe@gmail.com", "123456", "moshe", "cohen", "moshe@gmail.com");

        //define 2 reams
        Team t1 = new Team("t1", TeamState.active, o);
        Team t2 = new Team("t2", TeamState.active, o);

        //add games to referee
        g1 = new Game(s, t1, t2);
        g1.setGameDate(System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(45));
        g2 = new Game(s, t2, t1);
        g2.setGameDate(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(600));
        g3 = new Game(s, t2, t1);
        g3.setGameDate(System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(500));
        g4 = new Game(s, t1, t2);
        g4.setGameDate(System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(45));

        g1.setMainReferee(r);
        r.addGame(RefereeRole.Main, g1);

        r.addGame(RefereeRole.Secondary, g2);

        r.addGame(RefereeRole.Main, g3);
        g3.setMainReferee(r);

        r.addGame(RefereeRole.Secondary, g4);
        g4.setMainReferee(r2);


    }

    //region Test Use Case 10.1
    @Test
    public void Test_updateDetails() {
        try {
            HashMap<String, String> newVal = new HashMap<>();
            newVal.put("Password", "123456");
            newVal.put("first name", "moshik");
            SignedInController.updateDetails(r, newVal);
            assertEquals(Utils.sha256("123456"), r.getPassword());
            assertEquals("moshik", r.getFirstName());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test(expected = Exception.class)
    public void Test_updateDetailsShortPassword() throws Exception {
        HashMap<String, String> newVal = new HashMap<>();
        newVal.put("Password", "1234");
        SignedInController.updateDetails(r, newVal);
    }
    //endregion

    //region Test Use Case 10.2
    @Test
    public void Test_showRefereeAssignedGames() throws Exception {
        HashMap<Game, RefereeRole> games = rc.showRefereeAssignedGames(r);
        assertEquals(1, games.size());
    }
    //endregion

    //region Test Use Case 10.3
    @Test
    public void Test_getCurrentGames() {
        HashSet<Game> games = rc.getCurrentGames(r);
        assertEquals(2, games.size());
    }

    @Test
    public void Test_addEventToCurrentGame() {
        HashSet<Game> games = rc.getCurrentGames(r);
        for (Game game : games) {
            rc.addEventToCurrentGame(r, game, EventType.Goal, 23, "aaa");
            boolean event = false;
            for (Event ev : game.getEventLog().getEvents()) {
                if (ev.getDescription().equals("aaa")) {
                    event = true;
                    break;
                }
            }
            assertTrue(event);
        }
    }
    //endregion

    //region Test Use Case 10.4.1
    @Test
    public void Test_getGamesForEdit(){
        HashSet<Game> games = rc.getGamesForEdit(r);
        assertEquals(1, games.size());
        assertTrue(games.contains(g1));
    }
    @Test
    public void Test_getGamesEventsForEdit()  {
        rc.addEventToCurrentGame(r, g1, EventType.Goal, 23, "aaa");
        rc.addEventToCurrentGame(r, g1, EventType.Offense, 85, "aaa");

        HashSet<Event> gameEvents = rc.getGamesEventsForEdit(r, g1);
        assertEquals(2, gameEvents.size());
        for (Event event : gameEvents) {
            assertEquals("aaa", event.getDescription());
        }
    }

    @Test
    public void Test_editGameEvent(){
        rc.addEventToCurrentGame(r, g2, EventType.Goal, 23, "aaa");
        HashSet<Event> gameEvents = rc.getGamesEventsForEdit(r, g2);
        assertEquals(1, gameEvents.size());

        HashMap<String, String> changes = new HashMap<>();
        changes.put("description", "bbb");
        changes.put("eventminute", "88");
        for (Event event : gameEvents) {
            assertEquals("aaa", event.getDescription());
            assertEquals(23, event.getEventMinute());

            rc.editGameEvent(r,event,changes );
            assertEquals("bbb", event.getDescription());
            assertEquals(88, event.getEventMinute());
        }
    }

    @Test
    public void Test_getGamesEventsForEditMoreThen5Hours()  {
        HashSet<Game> games = rc.getGamesForEdit(r);
        assertTrue(games.contains(g1));
        assertFalse(games.contains(g2));
        assertFalse(games.contains(g3));

    }

    @Test
    public void Test_getGamesForEditNotMainReferee(){
        HashSet<Game> games = rc.getGamesForEdit(r);
        assertEquals(1, games.size());
        assertFalse(games.contains(g4));
    }
    //endregion

    //region Test Use Case 10.4.2
    @Test
    public void Test_createGameReport() throws Exception {
        rc.addEventToCurrentGame(r, g3, EventType.Goal, 23, "aaa");
        rc.addEventToCurrentGame(r, g3, EventType.Offense, 24, "bbb");
        rc.addEventToCurrentGame(r, g3, EventType.Injury, 25, "ccc");

        EventLog gameEventsLog = rc.createGameReport(r,g3);
        assertEquals(3, gameEventsLog.getEvents().size());
    }

    @Test(expected = Exception.class)
    public void Test_createGameReportNotMainReferee() throws Exception {
        EventLog gameEventsLog = rc.createGameReport(r,g4);
    }
    //endregion

}
