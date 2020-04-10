package domain.Users;


import domain.Enums.UserStatus;

public abstract class SignedUser extends User{
    private String userName;

    private String password;
    private UserStatus status;

    public SignedUser (String username, String password) {
        this.userName=username;
        this.password=password;
    }

    public String getUserName() {
        return userName;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void changeStatus(UserStatus status){
        this.status=status;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public abstract boolean deleteUser() throws Exception;
}
