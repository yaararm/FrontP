package domain.Users;

import domain.Controllers.PersonalPageSystem;

public class Footballer extends TeamUser {
    PersonalPage myPersonalPage;

    public Footballer(String username, String password) {
        super(username, password);
        myPersonalPage = new PersonalPage(this);
        PersonalPageSystem.addToFootballerList(this, myPersonalPage);
    }
}
