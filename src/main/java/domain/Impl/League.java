package domain.Impl;

import domain.Enums.RefereeTraining;
import domain.Users.AssociationRepresentative;

import java.util.HashMap;

public class League {
    private static int idCounter = 0;
    private int leagueID;
    String leagueName;
    long openDate;
    AssociationRepresentative responsibleAssociationRepresentative;
    HashMap<Integer, Season> leaguesSeasons;

    RefereeTraining minRefereeTrainingRequired;

    public League(String leagueName, AssociationRepresentative associationRepresentative, RefereeTraining refereeTraining) {
        this.leagueName = leagueName;
        this.responsibleAssociationRepresentative = associationRepresentative;
        minRefereeTrainingRequired = refereeTraining;
        openDate = System.currentTimeMillis();
        leaguesSeasons = new HashMap<>();
        leagueID = idCounter++;
    }

    public boolean checkForSeason(Integer year) {
        return leaguesSeasons.containsKey(year);
    }

    public boolean addSeason(Integer year, Season season) {
        leaguesSeasons.put(year, season);
        return true;
    }

    public int getLeagueID() {
        return leagueID;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public RefereeTraining getMinRefereeTrainingRequired() {
        return minRefereeTrainingRequired;
    }
}
