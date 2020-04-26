package BusinessLayer.Football;

import BusinessLayer.Enum.RefereeTraining;
import BusinessLayer.Users.Referee;
import BusinessLayer.SeasonPolicies.*;

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
    private HashMap<RefereeTraining, HashSet<Referee>> referees;
    private League league;
    private HashSet<Team> seasonsTeams;
    private HashMap<Integer,HashSet<Game>> RoundGames;
    private ScoreBoard scoreBoard;


    public Season(Integer year, long startDate) {
        this.year=year;
        this.startDate = startDate;
        scorePolicy=new ScoreComputingPolicy1();
        assignPolicy= new AssignPolicy2();
        referees = new HashMap<>();
        seasonID = idCounter++;
        RoundGames = new HashMap<>();
        seasonsTeams = new HashSet<>();
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
        return referees.remove(referee);
    }

    public void setSeasonTeams(HashSet<Team> seasonsTeams) throws Exception {
        if(System.currentTimeMillis() > this.startDate){
            throw new Exception("cant change season teams after season start date");
        }
        this.seasonsTeams = seasonsTeams;

        //init new scoreBoard for those teams
        scoreBoard = new ScoreBoard(this);

    }

    @Override
    public String toString() {
        return ""+year;
    }

    // ========== Getters and Setters ============


    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public HashSet<Team> getSeasonsTeams() {
        return seasonsTeams;
    }

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

    public HashMap<Integer,HashSet<Game>> getGames() {
        return RoundGames;
    }

    public void setSeasonGames(HashMap<Integer, HashSet<Game>> roundGames) {
        RoundGames = roundGames;
    }

    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }
}
