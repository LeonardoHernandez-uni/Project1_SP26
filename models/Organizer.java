package models;
public class Organizer extends User {

    public Organizer(String firstName, String lastName, String username, String password, int userID) {
        super(firstName, lastName, username, password, userID);
    }

    public void createEvent() {
        
    }

    @Override
    public String getUserType() {
        return "Organizer";
    }

    @Override
    public String toCSVString() {
        // Adds three empty commas so it aligns with the Customer columns in the CSV
        return super.toCSVString() + ",,,";
    }

}
