package domain.Users;

import domain.Controllers.PersonalPageSystem;
import domain.Controllers.Utils;
import domain.Enums.CoachPosition;
import domain.Impl.Team;
import domain.Interfaces.Asset;

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
        return true;
    }
}
