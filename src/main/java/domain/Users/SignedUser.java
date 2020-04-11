package domain.Users;


import domain.Enums.UserStatus;

public abstract class SignedUser extends User{
    protected String userName;
    protected String password;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected UserStatus status;

    public SignedUser (String username, String password, String firstName, String lastName, String email) {
        this.userName=username;
        this.password=password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    //========== Getters and Setters ================

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public String getUserName() {
        return userName;
    }
    public UserStatus getStatus() {
        return status;
    }
    public String getPassword() {
        return password;
    }

    public void changeStatus(UserStatus status){
        this.status=status;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //========== Abstract Functions ================

    public abstract boolean deleteUser();
}
