package unit;

import DB.SystemController;
import BusinessLayer.Enum.TeamState;
import BusinessLayer.Football.Team;
import BusinessLayer.Users.ManagementUser;
import BusinessLayer.Users.Owner;
import BusinessLayer.Users.TeamManager;
import org.junit.*;

import java.util.HashSet;
import java.util.Map;

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


    @Test
    public void deleteUserTest() {
        try {
            assertTrue(mu.deleteUser());
            for (Team team : mu.getTeams().keySet()) {
                assertFalse(team.getTeamCoaches().contains(mu));
            }
            for (Map.Entry<Team, HashSet<Owner>> teamHashSetEntry : mu.getAssignedOwners().entrySet()) {
                for (Owner owner : teamHashSetEntry.getValue()) {
                    assertFalse(teamHashSetEntry.getKey().getTeamOwners().contains(owner));

                }
            }
            for (Map.Entry<Team, HashSet<TeamManager>> teamHashSetEntry : mu.getAssignedTeamManagers().entrySet()) {
                for (TeamManager teamManager : teamHashSetEntry.getValue()) {
                    assertFalse(teamHashSetEntry.getKey().getTeamManagers().contains(teamManager));
                }
            }
            assertFalse(SystemController.userNameUser.containsKey(mu.getUserName()));
            assertTrue(SystemController.archiveUsers.containsKey(mu.getUserName()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
