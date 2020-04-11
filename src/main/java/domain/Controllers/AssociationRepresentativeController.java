package domain.Controllers;

import domain.Enums.RefereeTraining;
import domain.Impl.League;
import domain.Impl.Season;
import domain.SeasonPolicies.AssignPolicy;
import domain.SeasonPolicies.ScoreComputingPolicy;
import domain.Users.AssociationRepresentative;
import domain.Users.Referee;
import domain.Users.SignedUser;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.HashSet;
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

        //Logger
        SystemController.logger.info("Creation | New league have been defined; Association Representative ID: " + associationRepresentative.getAssociationRepresentativeID() +
                "; League ID: " + newLeague.getLeagueID() + "; League Name:" + newLeague.getLeagueName());

        return true;
    }

    //Use Case 9.2
    public boolean addSeasonToLeague(AssociationRepresentative associationRepresentative, League league, Integer year, long startDate) throws Exception {
    int length = String.valueOf(year).length();
    if(length!=4 || year<=2000 || year>=2022){
        throw new Exception("Not Valid Year");
    }
    if(!league.checkForSeason(year)){
        throw new Exception("There is season in this year for this league");
    }

    Season season = new Season(year, startDate);
    league.addSeason(year,season);

    //Logger
     SystemController.logger.info("Creation | New Season have been add to league; season ID: " + season.getSeasonID() +
                "; League ID: " + league.getLeagueID() + "; Association Representative ID:" + associationRepresentative.getAssociationRepresentativeID());

    return true;
    }

    //Use Case 9.3.1
    public boolean appointReferee (AssociationRepresentative associationRepresentative, int id, String fName, String lName, String email, RefereeTraining refereeTraining ) throws Exception {
        int length = String.valueOf(id).length();
        String userName = email;
        String password = String.valueOf(id);
        boolean valid = EmailValidator.getInstance().isValid(email);

        if(length != 9)
            throw new Exception("Not Valid ID");
        if(!valid)
            throw new Exception("Not Valid Email");

        if(findUserByID(id))
            throw new Exception("This ID Exist In The System");

        boolean send = ExternalServices.sendInviteToTheSystem(email, userName, password, associationRepresentative.getFirstName() + " " + associationRepresentative.getLastName());
        if(!send)
            throw new Exception("Have been problem with send the email");

        String hashPassword = Utils.sha256(password);
        Referee newReferee = new Referee(userName,hashPassword,id,fName,lName,email,refereeTraining);
        userNameUser.put(userName,newReferee);

        //Logger
        SystemController.logger.info("Creation | New Referee have been appoint; referee ID: " + newReferee.getRefereeID() +
                "; Association Representative ID:" + associationRepresentative.getAssociationRepresentativeID());

        return true;
    }

    //Use Case 9.3.2
    //TODO need to find replacer referee
    public boolean removeReferee (AssociationRepresentative associationRepresentative, Referee referee) throws Exception {
        SignedUser remove = userNameUser.remove(referee.getFirstName() + "_" + referee.getLastName());
        if(remove==null){
            throw new Exception("This Referee use name doesn't exist in the system");
        }
        referee.deleteUser();
        archiveUsers.put(referee.getUserName(),referee);
        //Logger
        SystemController.logger.info("Deletion | Referee have been remove from the system; Referee ID: " + referee.getRefereeID() +
                "; Association Representative ID:" + associationRepresentative.getAssociationRepresentativeID());
        return true;
    }

    //Use Case 9.4 A
    public Set<Referee> getAllRefereeThatCanBeForLeague(League league){
        int numTraining = league.getMinRefereeTrainingRequired().getNumVal();
        HashSet<Referee> refereeThatFitToTraining = findRefereeThatFitToTraining(numTraining);
        return refereeThatFitToTraining;
    }

    //Use Case 9.4 B
    public boolean setRefereeToLeague(AssociationRepresentative associationRepresentative, Season season, Referee... referees){
        for (Referee referee : referees) {
            season.addReferee(referee.getRefereeTraining(), referee);
            referee.addSeason(season);
            //Logger
            SystemController.logger.info("Linking | New Referee have been appoint to season; SeasonID: "+ season.getSeasonID()+"; Referee ID: " + referee.getRefereeID() +
                    "; Association Representative ID:" + associationRepresentative.getAssociationRepresentativeID());
        }
        return true;
    }

    //UseCase 9.5
    public boolean setScoreComputingPolicy(AssociationRepresentative associationRepresentative, Season season, ScoreComputingPolicy scoreComputingPolicy) throws Exception {
        long currentTime =System.currentTimeMillis();
        if(season.getStartDate()<=currentTime)
            throw new Exception("Season already started");

        season.setScorePolicy(scoreComputingPolicy);

        //Logger
        SystemController.logger.info("Creation | New Score Computing have been set to season; SeasonID: "+ season.getSeasonID()+"; Score Computing Name: " + scoreComputingPolicy.getName() +
                "; Association Representative ID:" + associationRepresentative.getAssociationRepresentativeID());

        return true;

    }

    //UseCase 9.6
    public boolean setAssignPolicy(AssociationRepresentative associationRepresentative, Season season, AssignPolicy assignPolicy) throws Exception {
        long currentTime =System.currentTimeMillis();
        if(season.getStartDate()<=currentTime)
            throw new Exception("Season already started");
        season.setAssignPolicy(assignPolicy);

        //Logger
        SystemController.logger.info("Creation | New Assign Policy have been set to season; SeasonID: "+ season.getSeasonID()+"; Assign Policy Name: " + assignPolicy.getName() +
                "; Association Representative ID:" + associationRepresentative.getAssociationRepresentativeID());
        return true;

    }
}