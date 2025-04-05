package user;

public class User {

    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public int authenticate(String password) {
        if(this.password.equals(password))
        return 1;
        else
        return 0;
    }
}