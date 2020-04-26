package domain.BusinessLayer.Football;

import domain.BusinessLayer.Enum.RefereeTraining;
import domain.BusinessLayer.Users.AssociationRepresentative;

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

    public boolean isSeasonExist(Integer year) {
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

    public HashMap<Integer, Season> getLeaguesSeasons() {
        return leaguesSeasons;
    }

    @Override
    public String toString() {
        String string = leagueName+ " ";
        for (Integer integer : leaguesSeasons.keySet()) {
            string += integer+" ";
        }
        return string;
    }
}