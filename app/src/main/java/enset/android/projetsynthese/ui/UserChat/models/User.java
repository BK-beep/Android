package enset.android.projetsynthese.ui.UserChat.models;

public class User {
    String userId;
    String userName;
    String userEmail;
    String userPassword;

    public String getUserId() {
        return userId;
    }

    public User() {
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public User(String userId, String userEmail, String userPassword,String userName) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }
}
