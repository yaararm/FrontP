package domain.Users;

import domain.Impl.Team;

public class TeamPersonalPage extends PersonalPage{
    String coachName;
    String teamFootballerMembers;
    String records;
    String Games;

    public TeamPersonalPage(SignedUser user, Team team) {
        super(user);
        this.pageName= team.getTeamName();

        for (Coach teamCoach : team.getTeamCoaches()) {
            coachName = teamCoach.getCoachPosition() + ": "+ teamCoach.getFirstName() +" " + teamCoach.getLastName();
        }

        for (Footballer teamFootballer : team.getTeamFootballers()) {
            teamFootballer.get

        }






    }


}
