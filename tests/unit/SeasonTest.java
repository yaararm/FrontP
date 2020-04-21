package unit;

import domain.Enums.RefereeTraining;
import domain.Impl.Season;
import domain.Users.Referee;
import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class SeasonTest {

    private static Season season;
    private static Referee referee1;
    private static Referee referee2;

    @BeforeClass
    public static void before_class(){
        season = new Season(2020,01022020);
        referee1 = new Referee("yoyo","123y",3,"yo","yo","yoyo@gmail.com", RefereeTraining.Medium);
        referee2 = new Referee("toto","123t",4,"to","to","toto@gmail.com",RefereeTraining.Medium);
    }

    @Test
    public void test_addReferee(){
        assertTrue(season.addReferee(referee1.getRefereeTraining(),referee1));
    }

    @Test
    public void test_removeReferee(){
        season.addReferee(referee1.getRefereeTraining(),referee1);
        assertFalse(season.removeReferee(referee2.getRefereeTraining(),referee2));
    }
}
