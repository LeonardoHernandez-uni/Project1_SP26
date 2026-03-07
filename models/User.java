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

    public int generateID() {
        int[] randomArray = new int[4];
        int assembledID = 0;
        for (int i = 0; i < 4; i++) {
            randomArray[i] = (int)(Math.random() * 10);
            assembledID = 10 * assembledID + randomArray[i];
        }
        return assembledID;
    }

    public boolean login(String username, String password) {
        return (username.equals(this.username) && password.equals(this.password));
    }

    public abstract void updateProfile();

    // Getters and Setters
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public int getUserID() { return this.userID; }
}
