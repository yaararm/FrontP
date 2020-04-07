package domain;

public class Guest extends User {
    UserStatus currentStatus;

    @Override
    public void changeStatus(UserStatus status) {
        currentStatus=status;
    }
}

