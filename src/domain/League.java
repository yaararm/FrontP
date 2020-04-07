package domain;

import java.util.Date;

public class League {
    String leagueName;
    Date openDate;
    AssociationRepresentative responsibleAssociationRepresentative;

    public League(String leagueName, AssociationRepresentative associationRepresentative) {
        this.leagueName=leagueName;
        this.responsibleAssociationRepresentative=associationRepresentative;
        openDate=new Date();
    }
}
