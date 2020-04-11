package domain.Users;

public class AssociationRepresentative extends SignedUser {
    private static int idCounter = 0;
    private int associationRepresentativeID;

    public AssociationRepresentative(String username, String password, String fName, String lName) {
        super(username, password, fName, lName);
        associationRepresentativeID = idCounter++;
    }

    @Override
    public boolean deleteUser() {
        return true;
    }

    public int getAssociationRepresentativeID() {
        return associationRepresentativeID;
    }
}
