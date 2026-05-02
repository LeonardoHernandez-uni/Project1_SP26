package models;

public class Admin extends User {
    
    /**
     * Constructs a new Admin user.
     * * @param firstName The admin's first name.
     * @param lastName The admin's last name.
     * @param username The admin's username.
     * @param password The admin's password.
     * @param userID The admin's unique ID.
     */
    public Admin(String firstName, String lastName, String username, String password, int userID) {
        super(firstName, lastName, username, password, userID);
    }

    /**
     * Logs an action performed by this admin to the console.
     * * @param action A string describing the action performed.
     */
    public void logAction(String action) {
        System.out.println("User " + this.getUsername() + " " + action + ".");
    }

    /**
     * Retrieves the user type for this specific object.
     * * @return The string "Admin".
     */
    @Override
    public String getUserType() {
        return "Admin";
    }
}