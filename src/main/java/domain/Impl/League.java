package domain.Impl;

import domain.Enums.RefereeTraining;
import domain.Users.AssociationRepresentative;

import java.util.HashMap;

public class League {
    private static int idCounter = 0;
    private int leagueID;
    private String leagueName;
    long openDate;
    private AssociationRepresentative responsibleAssociationRepresentative;
    private HashMap<Integer, Season> leaguesSeasons;

    RefereeTraining minRefereeTrainingRequired;

    // ================ Constructor =======================
    public League(String leagueName, AssociationRepresentative associationRepresentative, RefereeTraining refereeTraining) {
        this.leagueName = leagueName;
        this.responsibleAssociationRepresentative = associationRepresentative;
        minRefereeTrainingRequired = refereeTraining;
        openDate = System.currentTimeMillis();
        leaguesSeasons = new HashMap<>();
        leagueID = idCounter++;
    }


    // ================= Season ===========================
    public boolean addSeason(Integer year, Season season) {
        leaguesSeasons.put(year, season);
        return true;
    }

    public boolean isSeasonExist(Integer year) {
        return leaguesSeasons.containsKey(year);
    }

    // ============ Getters and Setter ==============

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

    public long getOpenDate() {
        return openDate;
    }

    public AssociationRepresentative getResponsibleAssociationRepresentative() {
        return responsibleAssociationRepresentative;
    }

    // ========== to String ================
    @Override
    public String toString() {
        String string = leagueName+ " ";
        for (Integer integer : leaguesSeasons.keySet()) {
            string += integer+" ";
        }
        return string;
    }
}
