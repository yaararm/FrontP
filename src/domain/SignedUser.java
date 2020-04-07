package domain;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class SignedUser extends User{
    String userName;
    String password;
    UserStatus status;

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




}
