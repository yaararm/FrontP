package domain.Impl;

import domain.Enums.RefereeTraining;
import domain.SeasonPolicies.AssignPolicy;
import domain.SeasonPolicies.AssignPolicy1;
import domain.SeasonPolicies.ScoreComputingPolicy;
import domain.SeasonPolicies.ScoreComputingPolicy1;
import domain.Users.Referee;

import java.util.HashMap;
import java.util.HashSet;

public class Season {
    //Fields
    private static int idCounter = 0;
    private int seasonID;
    private int year;
    long startDate;
    //Connections
    private ScoreComputingPolicy scorePolicy;
    private AssignPolicy assignPolicy;
    HashMap<RefereeTraining, HashSet<Referee>> referees;

    public Season(Integer year, long startDate) {
        this.year=year;
        this.startDate = startDate;
        scorePolicy=new ScoreComputingPolicy1();
        assignPolicy= new AssignPolicy1();
        referees = new HashMap<>();
        seasonID = idCounter++;
    }

    public boolean addReferee(RefereeTraining refereeTraining, Referee referee){
        if(referees.containsKey(refereeTraining)){
            referees.get(refereeTraining).add(referee);
        }
        else {
            HashSet<Referee> thisReferees = new HashSet<>();
            thisReferees.add(referee);
            referees.put(refereeTraining, thisReferees);
        }
        return true;
    }

    public boolean removeReferee(RefereeTraining refereeTraining, Referee referee){
        HashSet<Referee> referees = this.referees.get(refereeTraining);
        if(referees.remove(referee))
            return true;
        return false;
    }

    @Override
    public String toString() {
        return ""+year;
    }

    // ========== Getters and Setters
    public ScoreComputingPolicy getScorePolicy() {
        return scorePolicy;
    }

    public AssignPolicy getAssignPolicy() {
        return assignPolicy;
    }

    public void setScorePolicy(ScoreComputingPolicy scorePolicy) {
        this.scorePolicy = scorePolicy;
    }

    public void setAssignPolicy(AssignPolicy assignPolicy) {
        this.assignPolicy = assignPolicy;
    }

    public long getStartDate() {
        return startDate;
    }

    public int getSeasonID() {
        return seasonID;
    }

    public HashMap<RefereeTraining, HashSet<Referee>> getReferees() {
        return referees;
    }
}
