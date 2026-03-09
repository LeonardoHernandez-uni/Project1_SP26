package models;

import logic.UserManager;
import logic.VenueManager;
import logic.EventManager;
import java.time.LocalDate;
import java.time.LocalTime;

public class Admin extends User {

    public Admin(String firstName, String lastName, String username, String password, int userID) {
        super(firstName, lastName, username, password, userID);
    }

    // ==========================================
    // MANAGE USERS (Requirement 5.c.i)
    // ==========================================

    public void displayAllMembers() {
        System.out.println("\n--- All Members ---");
        for (User u : UserManager.getUserList()) {
            System.out.println("ID: " + u.getUserID() + " | Name: " + u.getFirstName() + " " + u.getLastName() + " | Username: " + u.getUsername() + " | Role: " + u.getClass().getSimpleName());
        }
        //logAction("printed information about all members to the console"); [cite: 78, 169]
    }

    public User searchMember(String query) {
        for (User u : UserManager.getUserList()) {
            // Checks ID, first name, last name, full name, or username [cite: 79]
            if (String.valueOf(u.getUserID()).equals(query) || 
                u.getFirstName().equalsIgnoreCase(query) || 
                u.getLastName().equalsIgnoreCase(query) ||
                (u.getFirstName() + " " + u.getLastName()).equalsIgnoreCase(query) ||
                u.getUsername().equalsIgnoreCase(query)) {
                
               // logAction("searched for User ID/Name/Username " + query); [cite: 166]
                return u;
            }
        }
        //logAction("searched for User ID/Name/Username " + query + " but no member was found"); [cite: 88, 166]
        return null;
    }

    public void updateUserName(User u, String newFirstName, String newLastName) {
        u.setFirstName(newFirstName);
        u.setLastName(newLastName);
        //logAction("updated Name to " + newFirstName + " " + newLastName + " for User ID " + u.getUserID()); [cite: 93, 166]
    }

    public boolean updateUsername(User u, String newUsername) {
        // Requirement 5.c.i.3.a.ii: Username must be unique [cite: 94]
        for (User existingUser : UserManager.getUserList()) {
            if (existingUser.getUsername().equalsIgnoreCase(newUsername)) {
                return false; // Registration fails, not unique [cite: 95]
            }
        }
        u.setUsername(newUsername);
        //logAction("updated Username to " + newUsername + " for User ID " + u.getUserID()); [cite: 94, 166]
        return true;
    }

    public void updateUserPassword(User u, String newPassword) {
        u.setPassword(newPassword);
        //logAction("updated Password for User ID " + u.getUserID()); [cite: 96, 166]
    }

    public void deleteUser(User u) {
        UserManager.getUserList().remove(u);
        //logAction("deleted User ID " + u.getUserID()); [cite: 101, 166]
    }

    // ==========================================
    // MANAGE VENUES (Requirement 5.c.ii)
    // ==========================================

    public void displayAllVenues() {
        System.out.println("\n--- All Venues ---");
        for (Venue v : VenueManager.getVenueList()) {
            System.out.println("ID: " + v.getId() + " | Name: " + v.getName() + " | Type: " + v.getType());
        }
        //logAction("printed information about all venues to the console"); [cite: 112, 169]
    }

    public Venue searchVenue(String query) {
        for (Venue v : VenueManager.getVenueList()) {
            // Checks ID, Name, or Type [cite: 113]
            if (String.valueOf(v.getId()).equals(query) || 
                v.getName().equalsIgnoreCase(query) || 
                v.getType().equalsIgnoreCase(query)) {
                
                //logAction("searched for Venue ID/Name/Type " + query); [cite: 166]
                return v;
            }
        }
        //logAction("searched for Venue ID/Name/Type " + query + " but no venue was found"); [cite: 117, 166]
        return null;
    }

    public void updateVenueName(Venue v, String newName) {
        v.setName(newName);
        //logAction("updated Venue Name to " + newName + " for Venue ID " + v.getId()); [cite: 125, 166]
    }

    public void updateVenueCost(Venue v, double newCost) {
        v.setCost(newCost);
        //logAction("updated Venue Cost to " + newCost + " for Venue ID " + v.getId()); [cite: 126, 166]
    }

    public void updateVenueCapacity(Venue v, int newCapacity) {
        v.setCapacity(newCapacity); 
        //logAction("updated Venue Capacity to " + newCapacity + " for Venue ID " + v.getId()); [cite: 127, 166]
    }

    public void deleteVenue(Venue v) {
        VenueManager.getVenueList().remove(v);
        //logAction("deleted Venue ID " + v.getId()); [cite: 132, 166]
    }

    // ==========================================
    // MANAGE EVENTS (Requirement 5.c.iii)
    // ==========================================

    public void displayAllEvents() {
        System.out.println("\n--- All Events ---");
        for (Event e : EventManager.getEventList()) { 
            System.out.println("ID: " + e.getId() + " | Name: " + e.getName() + " | Type: " + e.getEventType() + " | Date: " + e.getDate() + " | Time: " + e.getTime());
        }
        //logAction("printed information about all events to the console"); [cite: 144, 169]
    }

    public Event searchEvent(String query) {
        for (Event e : EventManager.getEventList()) { 
            // Checks ID, Name, or Date (format YYYY-MM-DD) [cite: 145]
            if (String.valueOf(e.getId()).equals(query) || 
                e.getName().equalsIgnoreCase(query) || 
                e.getDate().toString().equals(query)) { 
                
                //logAction("searched for Event ID/Name/Date " + query); [cite: 166]
                return e;
            }
        }
        //logAction("searched for Event ID/Name/Date " + query + " but no event was found"); [cite: 149, 166]
        return null;
    }

    public void updateEventName(Event e, String newName) {
        e.setName(newName);
        //logAction("updated Event Name to " + newName + " for Event ID " + e.getId()); [cite: 153, 166]
    }

    public void updateEventDateTime(Event e, LocalDate newDate, LocalTime newTime) {
        e.setDate(newDate);
        e.setTime(newTime);
        //logAction("updated Event Date and Time for Event ID " + e.getId()); [cite: 154, 166]
    }

    public void deleteEvent(Event e) {
        EventManager.getEventList().remove(e);
        //logAction("deleted Event ID " + e.getId()); [cite: 162, 166]
    }

    // ==========================================
    // LOGGING (Requirement 6)
    // ==========================================

    public void logAction(String action) {
        // Satisfies instruction 6.a [cite: 166, 168]
        System.out.println("User " + this.getUsername() + " " + action + ".");
    }
}