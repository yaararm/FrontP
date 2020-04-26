package unit;

import BusinessLayer.Enum.FieldType;
import BusinessLayer.Enum.TeamState;
import BusinessLayer.Football.Field;
import BusinessLayer.Football.Game;
import BusinessLayer.Football.Season;
import BusinessLayer.Football.Team;
import BusinessLayer.Users.Owner;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;

import static junit.framework.TestCase.assertTrue;

public class FieldTest {

    private static Field field;
    private static Game game;

    @BeforeClass
    public static void before_class(){
        field = new Field(5000,"Tel-Aviv","bigband", FieldType.Tournament);
        Season season = new Season(2020,20201304);
        Owner owner = new Owner("saba","123","as","ba","saba@gmail.com");
        Team team = new Team("goodTeam", TeamState.active,owner);
        Team team1 = new Team("bedTeam",TeamState.active,owner);
        game = new Game(season,team,team1);
    }



    @Test
    public void test_editAsset() throws Exception {
        HashMap<String,String> fieldInfo = new HashMap<>();
        fieldInfo.put("seats","5500");
        fieldInfo.put("name","inbarsField");
        field.editAsset(fieldInfo);
        boolean afterEdit = field.getSeats()==5500 && field.getName().equals("inbarsField");
        assertTrue(afterEdit);
    }

    @Test
    public void test_addGame(){
        if(field.getGames().contains(game)){
            field.removeGame(game);
        }
        assertTrue(field.addGame(game));
    }

    @Test
    public void test_removeGame(){
        if(!field.getGames().contains(game)){
            field.addGame(game);
        }
        assertTrue(field.removeGame(game));
    }
}
