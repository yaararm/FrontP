package domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class Team {
    private static int idCounter = 0;
    private String teamName;
    private int teamID;
    private TeamState state;
    private Members teamMembers;
    private HashSet<Asset> assets;
    private HashSet<FinanceActivity> financeActivities;

    public Team(String teamName, TeamState state) {
        this.teamName = teamName;
        this.teamID = idCounter++;
        this.state = state;
        this.teamMembers = new Members();
        this.assets = new HashSet<>();
        this.financeActivities = new HashSet<>();
    }

    public String getTeamName() {
        return teamName;
    }

    public int getTeamID() {
        return teamID;
    }

    public TeamState getState() {
        return state;
    }

    public boolean setStatus(TeamState newState) {
        this.state = newState;
        return true;
    }

    public boolean addTeamMember(SignedUser... signedUsers) throws Exception {
        List<SignedUser> addedUsers = new ArrayList<>();
        for (SignedUser signedUser : signedUsers) {
            if (signedUser instanceof TeamManager || signedUser instanceof Owner || signedUser instanceof Footballer || signedUser instanceof Coach) {
                teamMembers.addMember(signedUser);
                addedUsers.add(signedUser);
            } else {
                for (SignedUser addedUser : addedUsers) {
                    teamMembers.removeMember(addedUser);
                }
                throw new Exception("Team members can be only Footballer, Coach, Owner, TeamManager");
            }
        }
        return true;
    }

    public boolean removeTeamMember(SignedUser... signedUsers) throws Exception {
        List<SignedUser> removedUsers = new ArrayList<>();
        for (SignedUser signedUser : signedUsers) {
            if (signedUser instanceof TeamManager || signedUser instanceof Owner || signedUser instanceof Footballer || signedUser instanceof Coach) {
                teamMembers.removeMember(signedUser);
                removedUsers.add(signedUser);
            } else {
                for (SignedUser removed : removedUsers) {
                    teamMembers.addMember(removed);
                }
                throw new Exception("The following user isn't related to this team : " + signedUser);
            }
        }
        return true;
    }

    public boolean addAsset(Asset... assets) throws Exception {
        List<Asset> addedAssets = new ArrayList<>();
        for (Asset asset : assets) {
            if (this.assets.contains(asset)) {
                this.assets.removeAll(addedAssets);
                throw new Exception("The following asset is already related to the team" + asset);
            } else {
                addedAssets.add(asset);
                this.assets.add(asset);
            }
        }
        return true;
    }

    public boolean removeAsset(Asset... assets) throws Exception {
        List<Asset> removedAssets = new ArrayList<>();
        for (Asset asset : assets) {
            if (this.assets.contains(asset)) {
                removedAssets.add(asset);
                this.assets.remove(asset);
            } else {
                this.assets.addAll(removedAssets);
                throw new Exception("The following asset is not related to the team" + asset);
            }
        }
        return true;
    }

    public HashSet<Footballer> getTeamFootballers(){
        return this.teamMembers.getFootballers();
    }

    public HashSet<Coach> getTeamCoaches(){
        return this.teamMembers.getCoaches();
    }

    public HashSet<Owner> getTeamOwners(){
        return this.teamMembers.getOwners();
    }

    public HashSet<TeamManager> getTeamManagers(){
        return this.teamMembers.getTeamManagers();
    }

    public boolean addFinanceActivity(FinanceActivity financeActivity){
        this.financeActivities.add(financeActivity);
        return true;
    }

    public boolean removeFinanceActivity(FinanceActivity financeActivity){
        this.financeActivities.remove(financeActivity);
        return true;
    }
}