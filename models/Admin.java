package models;

public class Admin extends User{
    public Admin(String firstName, String lastName, String username, String password, int userID, String organizationName) {
        super(firstName, lastName, username, password, userID);
    }
}
