package domain.Users;

public class TeamMemberPersonalPage extends PersonalPage {
    String birthday;
    String history;
    String hobbies;

    public TeamMemberPersonalPage(SignedUser user) {
        super(user);
        if(user instanceof Footballer || user instanceof  Coach)
        this.pageName= user.getFirstName() +" "+user.getLastName();
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }
}
