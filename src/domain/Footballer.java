package domain;

import domain.controllers.PersonalPageSystem;

public class Footballer extends SignedUser {
    PersonalPage myPersonalPage;

    public Footballer(String username, String password) {
        super(username, password);
        myPersonalPage = new PersonalPage(this);
        PersonalPageSystem.addToFootballerList(this, myPersonalPage);
    }
}
