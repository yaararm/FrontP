package domain.Users;

import domain.Controllers.SystemController;
import domain.Controllers.TeamOwnerController;
import domain.Impl.Team;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Owner extends ManagementUser {
    //TODO change to TEAM USER

    public Owner(String username, String password, String firstName, String lastName, String email) {
        super(username, password, firstName, lastName, email);
    }

    public Owner(SignedUser signedUser) throws Exception {
        //TODO check constructors
        this(signedUser.getUserName(),signedUser.getPassword(),signedUser.getFirstName(),signedUser.getLastName(),signedUser.getEmail());
        if (!(signedUser instanceof Footballer || signedUser instanceof Coach || signedUser instanceof TeamManager))
            throw new Exception("Only the following combinations are allowed: teamOwner and (Footballer or Coach or TeamManager)");
        this.additionalRole = signedUser;
    }
}
