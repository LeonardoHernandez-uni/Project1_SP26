public abstract class User {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private int userID;

    public boolean login(String username, String password) {
        return (username.equals(this.username) && password.equals(this.password));
    }

    public abstract void updateProfile();
}
