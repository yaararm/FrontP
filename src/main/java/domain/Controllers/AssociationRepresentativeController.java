package domain.Controllers;

import domain.Enums.RefereeTraining;
import domain.Impl.League;
import domain.Impl.Season;
import domain.SeasonPolicies.AssignPolicy;
import domain.SeasonPolicies.ScoreComputingPolicy;
import domain.Users.AssociationRepresentative;
import domain.Users.Referee;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Set;
import java.util.stream.Collectors;

import static domain.Controllers.SystemController.*;

public class AssociationRepresentativeController {

    //Use Case 9.1
    public boolean defineNewLeague(AssociationRepresentative associationRepresentative, String leagueName, RefereeTraining refereeTraining) throws Exception {
        if (!leaguesNameValidation(leagueName))
            throw new Exception("This league name is already exist");
        League newLeague = new League(leagueName, associationRepresentative, refereeTraining);
        addNewLeague(leagueName, newLeague);
        return true;
    }

    //TODO
    //Use Case 9.2
    public boolean addSeasonToLeague(AssociationRepresentative associationRepresentative, League league, Integer year, long startDate) throws Exception {
    int length = String.valueOf(year).length();
    if(length!=4 || year<=2000 || year>=2022){
        throw new Exception("Not Valid Year");
    }
    if(!league.checkForSeason(year)){
        throw new Exception("There is season in this year for this league");
    }
    //TODO ADD TO THE USE CASE
    Season season = new Season(year, startDate);
    league.addSeason(year,season);
    return true;
    }

    //Use Case 9.3.1
    public boolean appointReferee (AssociationRepresentative associationRepresentative, Integer id, String fName, String lName, String email, RefereeTraining refereeTraining ) throws Exception {
        int length = String.valueOf(id).length();
        boolean valid = EmailValidator.getInstance().isValid(email);
        if(length != 9)
            throw new Exception("Not Valid ID");
        if(!valid)
            throw new Exception("Not Valid Email");
        if(IDReferees.containsKey(id))
            throw new Exception("This ID Exist In The System");

        String userName = String.valueOf(id);
        String password = String.valueOf(id);
        //TODO Send Email

        String hashPassword = Utils.sha256(password);
        Referee newReferee = new Referee(userName,hashPassword,id,fName,lName,email,refereeTraining);
        IDReferees.put(id,newReferee);
        return true;
    }


    //Use Case 9.3.2
    //TODO need to find replacer referee
    public boolean removeReferee (AssociationRepresentative associationRepresentative, Referee referee) throws Exception {
        referee.deleteUser();
        archiveUsers.add(referee);
        return true;
    }

    //Use Case 9.4 A
    public Set<Referee> getAllRefereeThatCanBeForLeague(League league){
        int numTraining = league.getMinRefereeTrainingRequired().getNumVal();
        Set<Referee> relevantReferees = IDReferees.values().stream().filter(referee -> referee.getRefereeTraining().getNumVal() <= numTraining).collect(Collectors.toSet());
        return relevantReferees;
    }

    //Use Case 9.4 B
    public boolean setRefereeToLeague(Season season, Referee... referees){
        for (Referee referee : referees) {
            season.addReferee(referee.getRefereeTraining(), referee);
            referee.addSeason(season);
        }
        return true;
    }

    //UseCase 9.5
    public boolean setScoreComputingPolicy(Season season, ScoreComputingPolicy scoreComputingPolicy) throws Exception {
        long currentTime =System.currentTimeMillis();
        if(season.getStartDate()<=currentTime)
            throw new Exception("Season already started");

        season.setScorePolicy(scoreComputingPolicy);
        return true;

    }

    //UseCase 9.6
    public boolean setAssignPolicy(Season season, AssignPolicy assignPolicy) throws Exception {
        long currentTime =System.currentTimeMillis();
        if(season.getStartDate()<=currentTime)
            throw new Exception("Season already started");
        season.setAssignPolicy(assignPolicy);
        return true;

    }
}