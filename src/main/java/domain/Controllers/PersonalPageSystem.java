package domain.Controllers;

import domain.Impl.Team;
import domain.Users.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PersonalPageSystem {
    static HashMap<Footballer, PersonalPage> footballersPersonalPage = new HashMap<>();
    static HashMap<Team, PersonalPage> teamsPersonalPage = new HashMap<>();
    static HashMap<Coach, PersonalPage> coachesPersonalPage = new HashMap<>();
    static HashMap<PersonalPage, String> archivePersonalPage = new HashMap<>();

    // ========== Add Personal Page to System ==========
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

    // ==========  Archive Personal Page ==========
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
        //Logger
        SystemController.logger.info("Delation | Personal Page have been move to archive; Personal Page ID: " + personalPage.getPpID() +
                "; Owner Name: " + nameOwner);
        return true;
    }

    // ========== Search in the System ==========
    public static HashSet<PersonalPage> searchInputFootballer(String[] searchArray) {
        HashSet<PersonalPage> returned = new HashSet<>();
        for (PersonalPage personalPage : footballersPersonalPage.values()) {
            for (String s : searchArray) {
                if(personalPage.toString().contains(s)){
                    returned.add(personalPage);
                    break;
                }
            }
        }
        return returned;
    }

    public static HashSet<PersonalPage> searchInputCoach(String[] searchArray) {
        HashSet<PersonalPage> returned = new HashSet<>();
        for (PersonalPage personalPage : coachesPersonalPage.values()) {
            for (String s : searchArray) {
                if(personalPage.toString().contains(s)){
                    returned.add(personalPage);
                    break;
                }
            }
        }
        return returned;
    }

    public static HashSet<PersonalPage> searchInputTeam(String[] searchArray) {
        HashSet<PersonalPage> returned = new HashSet<>();
        for (PersonalPage personalPage : teamsPersonalPage.values()) {
            for (String s : searchArray) {
                if(personalPage.toString().contains(s)){
                    returned.add(personalPage);
                    break;
                }
            }
        }
        return returned;
    }


    // ========== Update Personal Page ==========
    //Use Case 4.1 5.1
    public boolean updatePersonalPage(PersonalPage personalPage, HashMap<String,String> valuesToUpdate) {
        if (personalPage instanceof TeamMemberPersonalPage) {
            for (Map.Entry<String, String> entry : valuesToUpdate.entrySet()) {
                switch (entry.getKey()) {
                    case "birthday":
                        ((TeamMemberPersonalPage) personalPage).setBirthday(entry.getValue());
                        break;
                    case "history":
                        ((TeamMemberPersonalPage) personalPage).setHistory(entry.getValue());
                        break;
                    case "hobbies":
                        ((TeamMemberPersonalPage) personalPage).setHobbies(entry.getValue());
                        break;
                    case "role":
                        ((TeamMemberPersonalPage) personalPage).setRole(entry.getValue());
                        break;
                    case "team":
                        ((TeamMemberPersonalPage) personalPage).setTeam(entry.getValue());
                        break;
                }
            }
        } else {
            for (Map.Entry<String, String> entry : valuesToUpdate.entrySet()) {
            switch (entry.getKey()) {
                case "coachName":
                    ((TeamPersonalPage) personalPage).setCoachName(entry.getValue());
                    break;
                case "teamFootballerMembers":
                    ((TeamPersonalPage) personalPage).setTeamFootballerMembers(entry.getValue());
                    break;
                case "teamFields":
                    ((TeamPersonalPage) personalPage).setTeamFields(entry.getValue());
                    break;
                case "records":
                    ((TeamPersonalPage) personalPage).setRecords(entry.getValue());
                    break;
                case "Games":
                    ((TeamPersonalPage) personalPage).setGames(entry.getValue());
                    break;
            }
        }
    }
        return true;
    }

    //Use Case 4.2 5.2
    public boolean addContentToPersonalPage(PersonalPage personalPage, HashMap<String,String> valuesToUpdate){
        if(personalPage instanceof TeamMemberPersonalPage){
            for (Map.Entry<String, String> entry : valuesToUpdate.entrySet()) {
                ((TeamMemberPersonalPage) personalPage).setContent(entry.getKey()+": "+ entry.getValue()+"\n");
            }
            return true;
        }
        return false;
    }

}
