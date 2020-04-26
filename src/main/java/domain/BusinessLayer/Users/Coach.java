package domain.BusinessLayer.Users;

import domain.ServiceLayer.Controllers.PersonalPageSystem;
import domain.DB.SystemController;
import domain.CrossCutting.Utils;
import domain.BusinessLayer.Enum.CoachPosition;
import domain.BusinessLayer.Football.Team;
import domain.BusinessLayer.SystemFeatures.PersonalPage;
import domain.BusinessLayer.SystemFeatures.TeamMemberPersonalPage;

import java.util.HashMap;
import java.util.Map;

public class Coach extends TeamUser implements Asset {
    private PersonalPage myPersonalPage;
    private CoachPosition coachPosition;

    public Coach(String username, String password, String firstName, String lastName, String email, CoachPosition coachPosition) {
        super(username, password, firstName, lastName, email);
        this.coachPosition = coachPosition;
        myPersonalPage = new TeamMemberPersonalPage(this);
        PersonalPageSystem.addToCoachList(this, myPersonalPage);
    }

    public CoachPosition getCoachPosition() {
        return coachPosition;
    }

    public PersonalPage getMyPersonalPage() {
        return myPersonalPage;
    }

    @Override
    public boolean editAsset(HashMap<String, String> changes) throws Exception {
        for (Map.Entry<String, String> entry : changes.entrySet()) {
            switch (entry.getKey().toLowerCase()) {
                case "email":
                    this.email = entry.getValue();
                    break;
                case "firstname":
                    this.firstName = entry.getValue();
                    break;
                case "lastname":
                    this.lastName = entry.getValue();
                    break;
                case "password":
                    this.password = Utils.sha256(entry.getValue());
                    break;
            }

        }
        return true;
    }

    @Override
    public boolean deleteUser() throws Exception {
        PersonalPageSystem.moveToArchive(myPersonalPage);
        for (Team team : this.teams.keySet()) {
            team.removeTeamMember(this);
        }
        SystemController.archiveUsers.put(this.getUserName(),this);
        SystemController.userNameUser.remove(this);
        return true;
    }

    @Override
    public String toString() {
        String string = super.toString();
        string += coachPosition.toString();
        return string;
    }
}
