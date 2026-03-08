package models;
public abstract class User {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private int userID;

    public User(String firstName, String lastName, String username, String password, int userID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.userID = userID;
    }

    // Getters and Setters
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public int getUserID() { return userID; }
}
