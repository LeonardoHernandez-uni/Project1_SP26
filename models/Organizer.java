package models;
public class Organizer extends User {
    String organizationName;

    public Organizer(String firstName, String lastName, String username, String password, int userID, String organizationName) {
        super(firstName, lastName, username, password, userID);
        this.organizationName = organizationName;
    }

    public void createEvent() {
        
    }

}
