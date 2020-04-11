package domain.Users;

import domain.Controllers.PersonalPageSystem;

public class Coach extends TeamUser {
    PersonalPage myPersonalPage;

    public Coach(String username, String password, String firstName, String lastName) {
        super(username, password,firstName, lastName);
        myPersonalPage = new PersonalPage(this);
        PersonalPageSystem.addToCoachList(this, myPersonalPage);
    }

    @Override
    public boolean deleteUser() {
        return false;
    }
}
