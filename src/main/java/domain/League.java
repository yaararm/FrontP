package domain;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class League {
    String leagueName;
    Date openDate;
    AssociationRepresentative responsibleAssociationRepresentative;
    HashMap<Integer, Season> leaguesSeasons;



    RefereeTraining minRefereeTrainingRequired;

    public League(String leagueName, AssociationRepresentative associationRepresentative, RefereeTraining refereeTraining) {
        this.leagueName=leagueName;
        this.responsibleAssociationRepresentative=associationRepresentative;
        minRefereeTrainingRequired =refereeTraining;
        openDate=new Date();
        leaguesSeasons = new HashMap<>();
    }

    public boolean checkForSeason(Integer year) {
        return leaguesSeasons.containsKey(year);
    }

    public boolean addSeason(Integer year, Season season) {
        leaguesSeasons.put(year,season);
        return true;
    }



    public RefereeTraining getMinRefereeTrainingRequired() {
        return minRefereeTrainingRequired;
    }
}
