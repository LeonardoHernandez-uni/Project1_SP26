package models;
/**
 *  Our abstract representation of a User 
 */
public abstract class User {
    /** Our user's first name */
    private String firstName;
    /** Our user's last name */
    private String lastName;
    /** Our user's username */
    private String username;
    /** Our user's password */
    private String password;
    /* Our user's unique ID (limited to 4 digits but can be less) */
    private int userID;

    /** Constructor for the User object
     * @param firstName Sets our user's first name
     * @param lastName Sets our user's last name
     * @param username Sets our user's username
     * @param password Sets our user's password
     * @param userID Sets our user's ID
     */
    public User(String firstName, String lastName, String username, String password, int userID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.userID = userID;
    }

    /** Getters */
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public int getUserID() { return userID; }
}
