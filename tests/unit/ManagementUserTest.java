package unit;

import domain.Controllers.Utils;
import domain.Enums.FootballerPosition;
import domain.Enums.TeamState;
import domain.Impl.Team;
import domain.Users.Footballer;
import domain.Users.ManagementUser;
import domain.Users.Owner;
import domain.Users.TeamManager;
import org.junit.*;

import java.util.HashMap;

import static org.junit.Assert.*;

public class ManagementUserTest {
    ManagementUser mu = new Owner("avi@gmail.com", "123456", "avi", "levi","avi@gmail.com");
    Owner o = new Owner("moshe@gmail.com", "123456", "moshe", "cohen","moshe@gmail.com");
    TeamManager tm = new TeamManager("yossi@gmail.com", "123456", "yossi", "cohen","yossi@gmail.com");
    Team t = new Team("hapoel", TeamState.active,mu);

    @Test
    public void addOwnerTest(){
        try {
            assertTrue(mu.addOwner(t,o));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mu.addOwner(t,o);
            fail();
        } catch (Exception ignored) {
        }

    }

    @Test
    public void removeOwnerTest(){
        try {
            mu.addOwner(t,o);
        } catch (Exception ignored) {
        }

        try {
            assertTrue(mu.removeOwner(t,o));
        } catch (Exception ignored) {
        }

    }
    @Test
    public void addTeamManagerTest(){
        try {
            assertTrue(mu.addTeamManager(t,tm));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mu.addTeamManager(t,tm);
            fail();
        } catch (Exception ignored) {
        }

    }

    @Test
    public void removeTeamManagerTest(){
        try {
            mu.removeTeamManager(t,tm);
        } catch (Exception ignored) {
        }

        try {
            assertTrue(mu.removeTeamManager(t,tm));
        } catch (Exception ignored) {
        }

    }
}
