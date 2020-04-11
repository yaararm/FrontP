package domain.Users;

import domain.Impl.Team;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Owner extends ManagementUser {
    private SignedUser additionalRole;

    public Owner(String username, String password, String firstName, String lastName, String email) {
        super(username, password, firstName, lastName, email);
    }

    @Override
    public boolean deleteUser() throws Exception {
        for (Team team : this.teams.keySet()) {
            team.removeTeamMember(this);
        }
        for (Map.Entry<Team, HashSet<Owner>> teamHashSetEntry : assignedOwners.entrySet()) {
            for (Owner owner : teamHashSetEntry.getValue()) {
                removeTeamOwner(ownerToRemove, teamHashSetEntry.getKey(), owner);
            }
        }
        for (Map.Entry<Team, HashSet<TeamManager>> teamHashSetEntry : assignedTeamManagers.entrySet()) {
            for (TeamManager teamManager : teamHashSetEntry.getValue()) {
                removeTeamManager(ownerToRemove, teamHashSetEntry.getKey(), teamManager);
            }
        }
    }

    public Owner(SignedUser signedUser) throws Exception {
        //TODO check constructors
        this(signedUser.getUserName(),signedUser.getPassword(),signedUser.getFirstName(),signedUser.getLastName(),signedUser.getEmail());
        if (!(additionalRole instanceof Footballer || additionalRole instanceof Coach || additionalRole instanceof TeamManager))
            throw new Exception("Only the following combinations are allowed: teamOwner and (Footballer or Coach or TeamManager)");
        this.additionalRole = signedUser;
    }

    public SignedUser getAdditionalRole() {
        return additionalRole;
    }

    public String additionalRole(){
        return this.additionalRole.getClass().getSimpleName();
    }

    public void setAdditionalRole(SignedUser additionalRole) throws Exception {
        if (!(additionalRole instanceof Footballer || additionalRole instanceof Coach || additionalRole instanceof TeamManager))
            throw new Exception("Only the following combinations are allowed: teamOwner and (Footballer or Coach or TeamManager)");
        this.additionalRole = additionalRole;
    }
}
