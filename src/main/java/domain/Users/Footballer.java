package domain.Users;

import domain.Controllers.PersonalPageSystem;
import domain.Enums.FootballerPosition;
import domain.Interfaces.Asset;

public class Footballer extends TeamUser implements Asset {
    PersonalPage myPersonalPage;
    FootballerPosition footballerPosition;

    public Footballer(String username, String password, String firstName, String lastName, String email, FootballerPosition footballerPosition) {
        super(username, password, firstName, lastName, email);
        this.footballerPosition = footballerPosition;
        myPersonalPage = new PersonalPage(this);
        PersonalPageSystem.addToFootballerList(this, myPersonalPage);
    }
}
