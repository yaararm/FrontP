package domain;

import domain.controllers.PersonalPageSystem;

public class Coach extends SignedUser {
    PersonalPage myPersonalPage;

    public Coach(String username, String password) {
        super(username, password);
        myPersonalPage= new PersonalPage(this);
        PersonalPageSystem.addToCoachList(this, myPersonalPage);
    }
}
