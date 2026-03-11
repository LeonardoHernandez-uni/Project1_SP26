package models;

import java.time.LocalDate;
import java.time.LocalTime;
import logic.EventManager;
import logic.UserManager;
import logic.VenueManager;

public class Admin extends User {

    public Admin(String firstName, String lastName, String username, String password, int userID) {
        super(firstName, lastName, username, password, userID);
    }

    public void displayAllMembers() {
        System.out.println("\n--- All Members ---");
        for (User u : UserManager.getUserList()) {
            System.out.println("ID: " + u.getUserID() + " | Name: " + u.getFirstName() + " " + u.getLastName() + " | Username: " + u.getUsername() + " | Role: " + u.getClass().getSimpleName());
        }

    }

    public User searchMember(String query) {
        for (User u : UserManager.getUserList()) {
            // Checks ID, first name, last name, full name, or username [cite: 79]
            if (String.valueOf(u.getUserID()).equals(query) || 
                u.getFirstName().equalsIgnoreCase(query) || 
                u.getLastName().equalsIgnoreCase(query) ||
                (u.getFirstName() + " " + u.getLastName()).equalsIgnoreCase(query) ||
                u.getUsername().equalsIgnoreCase(query)) {
                
                return u;
            }
        }
        return null;
    }

    public void updateUserName(User u, String newFirstName, String newLastName) {
        u.setFirstName(newFirstName);
        u.setLastName(newLastName);
    }

    public boolean updateUsername(User u, String newUsername) {
        for (User existingUser : UserManager.getUserList()) {
            if (existingUser.getUsername().equalsIgnoreCase(newUsername)) {
                return false; // Registration fails, not unique [cite: 95]
            }
        }
        u.setUsername(newUsername);
        return true;
    }

    public void updateUserPassword(User u, String newPassword) {
        u.setPassword(newPassword);
    }

    public void deleteUser(User u) {
        UserManager.getUserList().remove(u);
    }

    public void displayAllVenues() {
        System.out.println("\n--- All Venues ---");
        for (Venue v : VenueManager.getVenueList()) {
            System.out.println("ID: " + v.getId() + " | Name: " + v.getName() + " | Type: " + v.getType());
        }
    }

    public Venue searchVenue(String query) {
        for (Venue v : VenueManager.getVenueList()) {
            // Checks ID, Name, or Type [cite: 113]
            if (String.valueOf(v.getId()).equals(query) || 
                v.getName().equalsIgnoreCase(query) || 
                v.getType().equalsIgnoreCase(query)) {
                
                return v;
            }
        }
        return null;
    }

    public void updateVenueName(Venue v, String newName) {
        v.setName(newName);
    }

    public void updateVenueCost(Venue v, double newCost) {
        v.setCost(newCost);
    }

    public void updateVenueCapacity(Venue v, int newCapacity) {
        v.setCapacity(newCapacity); 
    }

    public void deleteVenue(Venue v) {
        VenueManager.getVenueList().remove(v);
    }

    public void displayAllEvents() {
        System.out.println("\n--- All Events ---");
        for (Event e : EventManager.getEventList()) { 
            System.out.println("ID: " + e.getId() + " | Name: " + e.getName() + " | Type: " + e.getEventType() + " | Date: " + e.getDate() + " | Time: " + e.getTime());
        }
    }

    public Event searchEvent(String query) {
        for (Event e : EventManager.getEventList()) { 
            // Checks ID, Name, or Date (format YYYY-MM-DD) [cite: 145]
            if (String.valueOf(e.getId()).equals(query) || 
                e.getName().equalsIgnoreCase(query) || 
                e.getDate().toString().equals(query)) { 
                
                return e;
            }
        }
        return null;
    }

    public void updateEventName(Event e, String newName) {
        e.setName(newName);
    }

    public void updateEventDateTime(Event e, LocalDate newDate, LocalTime newTime) {
        e.setDate(newDate);
        e.setTime(newTime);
    }

    public void deleteEvent(Event e) {
        EventManager.getEventList().remove(e);
    }

    public void logAction(String action) {
        System.out.println("User " + this.getUsername() + " " + action + ".");
    }

    @Override
    public String getUserType() {
        return "Admin";
    }
}