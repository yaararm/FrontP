package unit;

import domain.BusinessLayer.Enum.FieldType;
import domain.BusinessLayer.Enum.TeamState;
import domain.BusinessLayer.Football.Field;
import domain.BusinessLayer.Football.Game;
import domain.BusinessLayer.Football.Season;
import domain.BusinessLayer.Football.Team;
import domain.BusinessLayer.Users.Owner;
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
