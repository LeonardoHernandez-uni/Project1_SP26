public abstract class User {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private int userID;

    public abstract boolean login();

    public abstract void updateProfile();
}
