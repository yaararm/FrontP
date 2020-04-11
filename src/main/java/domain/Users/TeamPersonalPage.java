package domain.Users;

import domain.Impl.Field;
import domain.Impl.Team;

public class TeamPersonalPage extends PersonalPage{
    String coachName;
    String teamFootballerMembers;
    String teamFields;
    String records;
    String Games;

    public TeamPersonalPage(SignedUser user, Team team) {
        super(user);
        this.pageName= team.getTeamName();

        for (Coach teamCoach : team.getTeamCoaches()) {
            coachName = teamCoach.getCoachPosition() + ": "+ teamCoach.getFirstName() +" " + teamCoach.getLastName()+"\n";
        }

        for (Footballer teamFootballer : team.getTeamFootballers()) {
            teamFootballerMembers = teamFootballer.getFootballerPosition() +": " + teamFootballer.getFirstName()  +" " + teamFootballer.getLastName()+"\n";
        }

        for (Field field : team.getFields()) {
            teamFootballerMembers = field. +": " + teamFootballer.getFirstName()  +" " + teamFootballer.getLastName()+"\n";


        }






    }


}
