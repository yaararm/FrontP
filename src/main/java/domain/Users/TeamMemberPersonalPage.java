package domain.Users;

import domain.Controllers.SystemController;
import domain.Impl.Team;

public class TeamMemberPersonalPage extends PersonalPage {


    String birthday;
    String history;
    String hobbies;
    String type;
    String role;
    String team;

    public TeamMemberPersonalPage(TeamUser user) {
        super(user);

        this.pageName = user.getFirstName() + " " + user.getLastName();
        for (Team team1 : user.getTeams().keySet()) {
            team += team1.getTeamName() +"\n";
        }

        if (user instanceof Footballer){
            role = ((Footballer) user).getFootballerPosition().toString();
            type = "Footballer";
        }

        if (user instanceof Coach){
            type = "Coach";
            role = ((Coach) user).getCoachPosition().toString();
        }

        //Logger
        SystemController.logger.info("Creation | New Personal Page for Team Member have been created have been defined; Owner name: " + user.getFirstName() + " " + user.getLastName() +
                "; Personal Page ID: " + this.getPpID());

    }

    // =========== Getters and Setters =============
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getHistory() {
        return history;
    }

    public String getHobbies() {
        return hobbies;
    }

    public String getType() {
        return type;
    }

    public String getRole() {
        return role;
    }

    public String getTeam() {
        return team;
    }
}
