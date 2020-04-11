package domain.Users;

import domain.Controllers.PersonalPageSystem;
import domain.Enums.CoachPosition;
import domain.Interfaces.Asset;

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
}
