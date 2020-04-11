package domain.Controllers;

import domain.Impl.Team;
import domain.Users.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PersonalPageSystem {
    static HashMap<Footballer, PersonalPage> footballersPersonalPage = new HashMap<>();
    static HashMap<Team, PersonalPage> teamsPersonalPage = new HashMap<>();
    static HashMap<Coach, PersonalPage> coachesPersonalPage = new HashMap<>();
    static HashMap<PersonalPage, String> archivePersonalPage = new HashMap<>();

    public static boolean addToFootballerList(Footballer footballer, PersonalPage myPersonalPage) {
        footballersPersonalPage.put(footballer,myPersonalPage);
        return true;
    }

    public static boolean addToCoachList(Coach coach, PersonalPage myPersonalPage) {
        coachesPersonalPage.put(coach,myPersonalPage);
        return true;
    }

    public static boolean addToTeamList(Team team, PersonalPage myPersonalPage) {
        teamsPersonalPage.put(team,myPersonalPage);
        return true;
    }

    public static boolean moveToArchive(PersonalPage personalPage) {
        SignedUser pageOwner = personalPage.getPageOwner();
        String nameOwner = pageOwner.getFirstName()+" "+pageOwner.getLastName();
        if(personalPage instanceof TeamPersonalPage){
            List<Map.Entry<Team, PersonalPage>> collect = teamsPersonalPage.entrySet().stream().filter(teamPersonalPageEntry -> teamPersonalPageEntry.getValue().getPpID() == personalPage.getPpID()).collect(Collectors.toList());
            Team key = collect.get(0).getKey();
            teamsPersonalPage.remove(key);
        }
        else {
            if(((TeamMemberPersonalPage)personalPage).getType().equals("Footballer")){
                List<Map.Entry<Footballer, PersonalPage>> collect = footballersPersonalPage.entrySet().stream().filter(teamPersonalPageEntry -> teamPersonalPageEntry.getValue().getPpID() == personalPage.getPpID()).collect(Collectors.toList());
                Footballer key = collect.get(0).getKey();
                teamsPersonalPage.remove(key);
            }
            else{
                List<Map.Entry<Coach, PersonalPage>> collect = coachesPersonalPage.entrySet().stream().filter(teamPersonalPageEntry -> teamPersonalPageEntry.getValue().getPpID() == personalPage.getPpID()).collect(Collectors.toList());
                Coach key = collect.get(0).getKey();
                teamsPersonalPage.remove(key);
            }

        }
        archivePersonalPage.put(personalPage, nameOwner);
        SystemController.logger.info("Delation | Peronal Page have been move to archive; Personal Page ID: " + personalPage.getPpID() +
                "; Owner Name: " + nameOwner);
        return true;
    }

    //Use Case 4.1 5.1
    public boolean updatePersonalPage(SignedUser signedUser, HashMap<String,String> valuesToUpdate){
        if(signedUser instanceof Footballer || signedUser instanceof Coach){
            for (Map.Entry<String, String> entry : valuesToUpdate.entrySet()) {
                switch (entry.getKey()) {
                    case "birthday":
                        break;
                    case "history":
                        break;

                }
            }

        }
        return true;
    }

    //TODO complete
    //Use Case 4.2 5.2
    public boolean addContentToPersonalPage(SignedUser signedUser, HashMap<String,String> valuesToUpdate){
        if(signedUser instanceof Footballer || signedUser instanceof Coach){
            for (Map.Entry<String, String> entry : valuesToUpdate.entrySet()) {
                switch (entry.getKey().toLowerCase()) {
                    case "birthday":
                        break;
                    case "history":
                        break;

                }
            }

        }
        return true;
    }

}
