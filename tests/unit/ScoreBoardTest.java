package unit;

import domain.Enums.FieldType;
import domain.Enums.TeamState;
import domain.Impl.*;
import domain.Interfaces.ScoreComputingPolicy;
import domain.SeasonPolicies.ScoreComputingPolicy1;
import domain.Users.Owner;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class ScoreBoardTest {

    private static Season season;
    private static ScoreBoard scoreBoard;
    private static Game game;

    @BeforeClass
    public static void beforeClass() throws Exception {
        String myDate = "2020/10/29 18:10:45";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = sdf.parse(myDate);
        long millis = date.getTime();
        season = new Season(2021,millis);
        Owner owner = new Owner("u","123u","u","t","u@gmail.com");
        Team team1 = new Team("amitChamp", TeamState.active,owner);
        Team team2 = new Team("amitloz",TeamState.active,owner);
        HashSet<Team> seasonTeam = new HashSet<>();
        seasonTeam.add(team1);
        seasonTeam.add(team2);
        season.setSeasonTeams(seasonTeam);
        game = new Game(season,team1,team2);

        String myDateGame = "2020/11/05 18:10:45";
        SimpleDateFormat sdfGame = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date dateGame = sdf.parse(myDate);
        long millisGame = date.getTime();
        game.setGameDate(millisGame);
        Field field = new Field(2000,"nir-banim","inbartzurfiled", FieldType.Youth);
        game.setField(field);
        game.setHomeScore(3);
        game.setAwayScore(2);
        ScoreComputingPolicy scoreComputingPolicy = new ScoreComputingPolicy1();
        season.setScorePolicy(scoreComputingPolicy);
        scoreBoard = new ScoreBoard(season);
    }

    @Test
    public void test_updateScoreBoard(){
        scoreBoard.updateScoreBoard(game);
        List<ScoreBoard.TeamScores> list = scoreBoard.getBoard();
        assertTrue(list.get(0).getTotalPoints()==0 && list.get(1).getTotalPoints()==3);
    }


}
