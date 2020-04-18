package domain.Users;

import domain.Controllers.PersonalPageSystem;
import domain.Controllers.SystemController;
import domain.Controllers.Utils;
import domain.Enums.FootballerPosition;
import domain.Impl.Team;
import domain.Interfaces.Asset;

import java.util.HashMap;
import java.util.Map;

public class Footballer extends TeamUser implements Asset {
    PersonalPage myPersonalPage;
    FootballerPosition footballerPosition;



    public Footballer(String username, String password, String firstName, String lastName, String email, FootballerPosition footballerPosition) {
        super(username, password, firstName, lastName, email);
        this.footballerPosition = footballerPosition;
        myPersonalPage = new TeamMemberPersonalPage(this);
        PersonalPageSystem.addToFootballerList(this, myPersonalPage);
    }

    public FootballerPosition getFootballerPosition() {
        return footballerPosition;
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
        string += footballerPosition.toString();
        return string;
    }

    public PersonalPage getMyPersonalPage() {
        return myPersonalPage;
    }
}
