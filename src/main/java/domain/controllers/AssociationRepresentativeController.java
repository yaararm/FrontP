package domain.controllers;

import domain.*;

import static domain.controllers.SystemController.*;

public class AssociationRepresentativeController {

    //Use Case 9.1
    public boolean defineNewLeague(AssociationRepresentative associationRepresentative, String leagueName) throws Exception {
        if (!leaguesNameValidation(leagueName))
            throw new Exception("This league name is already exist");

        League newLeague = new League(leagueName, associationRepresentative);
        addNewLeague(leagueName, newLeague);
        return true;

    }

    //TODO
    //Use Case 9.2
}