package models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import logic.EventManager;
import logic.UserManager;
import logic.VenueManager;

public class Admin extends User {
    /** Constructs an admin class by taking superclass User's parameters and instantiating them in a super class
     * @param firstName The user's first name.
     * @param lastName The user's last name.
     * @param username The user's username.
     * @param password The user's password.
     * @param userID The user's unique ID (encouraged to be no more than 4 digits seeing that typing long values out is tedious ;-;)
     */
    public Admin(String firstName, String lastName, String username, String password, int userID) {
        super(firstName, lastName, username, password, userID);
    }

    /** Creates a new User of either an Admin, Organizer, or Customer object type and assigns its parameters with the given parameters.
     * 
     * @param userType The intended User type. Can be Admin, Organizer, or Customer.
     * @param firstName First name to be given to the new User.
     * @param lastName Last name to be given to the new User.
     * @param username Username to be given to the new User.
     * @param password Password to be given to the new User.
     * @param userID ID to be given to the new User.
     * @param moneyAvailable Money avaliable in account to be given to a new User should their type be Customer.
     * @param hasMembership Membership status to be given to a new User should their type be Customer.
     * @param purchasedTickets An ArrayList of Tickets to be given to the new user should their type be Customer.
     */
    public void addMember(String userType, String firstName, String lastName, String username, String password, int userID, double moneyAvailable, boolean hasMembership, ArrayList<Ticket> purchasedTickets) {
        switch (userType) {
            case "Admin" -> {
                UserManager.getUserList().add(new Admin(firstName, lastName, username, password, userID));
            }
            case "Organizer" -> {
                UserManager.getUserList().add(new Organizer(firstName, lastName, username, password, userID));
            }
            case "Customer" -> {
                UserManager.getUserList().add(new Customer(firstName, lastName, username, password, userID, moneyAvailable, hasMembership, purchasedTickets));
            }
            default -> {
                System.out.println("Error: addMember() method recieved an invalid userType string and isn't happy :(");
            }
        }
    }

    /** Displays all member data from userList by printing every User in a row of attributes where every column is an attribute.*/
    public void displayAllMembers() {
        System.out.println("\n--- All Members ---");
        for (User u : UserManager.getUserList()) {
            System.out.println("ID: " + u.getUserID() + " | Name: " + u.getFirstName() + " " + u.getLastName() + " | Username: " + u.getUsername() + " | Role: " + u.getClass().getSimpleName());
        }

    }

    /** Given a string, searchMember() will check whether this String belongs to a User in userList. If it does (and there are no duplicates (To-do: fix that)), the method will return that User. If there are no Users 
     * with attributes that match the query, the method will return null
     * @return The user who's attributes matched the query String. Will return null if no User is found to match the query.
     */
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

    /** Given a User u and a full name, the method will update the user's first and last name attributes.
     * 
     * @param u The User who's name we wish to update.
     * @param newFirstName The new first name we are assigning to our User.
     * @param newLastName The new last name we are assigning to our User.
    */
    public void updateUserName(User u, String newFirstName, String newLastName) {
        u.setFirstName(newFirstName);
        u.setLastName(newLastName);
    }

    /** Given a User u and a new username, we update the User's username. The method also checks if the username is unique and returns the result. If said result is false, the username isn't updated).
     * 
     * @param u The User who's username we wish to update.
     * @param newUsername The new username we are assigning to our User.
     * @return The result of trying to update username. If it is successful, it returns true. If it was not, it returns false.
    */
    public boolean updateUsername(User u, String newUsername) {
        for (User existingUser : UserManager.getUserList()) {
            if (existingUser.getUsername().equalsIgnoreCase(newUsername)) {
                return false; // Registration fails since the username isn't unique
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