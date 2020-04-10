package domain.Users;

import domain.Controllers.PersonalPageSystem;

public class Coach extends TeamUser {
    PersonalPage myPersonalPage;

    public Coach(String username, String password) {
        super(username, password);
        myPersonalPage= new PersonalPage(this);
        PersonalPageSystem.addToCoachList(this, myPersonalPage);
    }
}
