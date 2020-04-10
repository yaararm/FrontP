package domain;

public class AssociationRepresentative extends SignedUser {
    public AssociationRepresentative(String username, String password) {
        super(username, password);
    }

    @Override
    public boolean deleteUser() {
        return false;
    }
}
