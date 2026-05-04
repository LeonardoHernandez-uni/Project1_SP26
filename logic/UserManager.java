package logic;

import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import models.*;

public class UserManager {

    /** The single, private static instance of the class */
    private static UserManager instance;
	
    /** userList stores all the users in the system. */
    private ArrayList<User> userList;

    /** Private constructor to ensure only one instance of the class is created. */
    private UserManager() {
        userList = new ArrayList<>();
    }

    /** Returns the single, static instance of the UserManager class. */
    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

	/** Returns userList. Is used for accessing userList from other classes.*/
	public ArrayList<User> getUserList() {
		return userList;
	}

	/** Loads data from the customer file (in this case Customer_List_PA1.csv) and parses its entries row by row into userList as User objects, assigning parameters to them column by column.
	 *  The method will use the 6th column of the csv, the userType column, to determine whether the User will be a Customer, an Organizer, or an Admin.
	 */
	public void loadData() {
		String customerFile = "Customer_List_PA2.csv";
        String line;
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(customerFile))) {
            String headerLine = br.readLine();
            if (headerLine == null) return;
            
            // Map column names to their index
            String[] headers = headerLine.split(csvSplitBy);
            Map<String, Integer> colMap = new HashMap<>();
            for (int i = 0; i < headers.length; i++) {
                colMap.put(headers[i].trim().toLowerCase(), i);
            }

            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy, -1); 
                
                int id = Integer.parseInt(data[colMap.get("id")]);
                String firstName = data[colMap.get("first name")];
                String lastName = data[colMap.get("last name")];
                String username = data[colMap.get("username")];
                String password = data[colMap.get("password")];
                String userType = data[colMap.get("user type")];

                if (userType.equalsIgnoreCase("Customer")) {
                    double money = Double.parseDouble(data[colMap.get("money available")]);
                    boolean membership = Boolean.parseBoolean(data[colMap.get("ticketminer membership")]);
                    userList.add(new Customer(firstName, lastName, username, password, id, money, membership, new ArrayList<>()));
                } else if (userType.equalsIgnoreCase("Organizer")) {
                    userList.add(new Organizer(firstName, lastName, username, password, id));
                } else if (userType.equalsIgnoreCase("Admin")) {
                    userList.add(new Admin(firstName, lastName, username, password, id));
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading user data: " + e.getMessage());
        }
	}

	public void saveData() {
		DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy_MM_dd");
        
        try (FileWriter file = new FileWriter(LocalDate.now().format(pattern) + "_Customer_List_PA2.csv")) {
            file.write("ID,First Name,Last Name,Username,Password,User Type,Money Available,TicketMiner Membership,Tickets Purchased\n");
            for (User user : userList) {
                file.write(user.toCSVString() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error saving user data: " + e.getMessage());
        }
	}

	/** generateID randomly generates a new 4 digit ID. After a new ID is generated, it is compared to all the IDs in userList using the isIDUnique() method. If the ID isn't unique, a new ID will be generated until
	 * the isIDUnique() method returns true.
	 * @return The randomly generated unique ID.
	*/
	public int generateID() {
		int[] randomArray = new int[4];
        int assembledID = 0;
        do {
            assembledID = 0;
            for (int i = 0; i < 4; i++) {
                randomArray[i] = (int) (Math.random() * 10);
                assembledID = 10 * assembledID + randomArray[i];
            }
        } while (!isIDUnique(assembledID));
        return assembledID;
	}

	/** isIDUnique compares the given int to all userIDs in userList to check for uniqueness. If it returns true, the given int is unique. If it returns false, the given int is not unique.
	 * @param id The int we are checking against all userID ints in userList.
	 * @return Will either be true or false
	*/
	public boolean isIDUnique(int id) {
		for (User user : userList) {
            if (user.getUserID() == id) {
                return false;
            }
        }
        return true;
	}


	//refactored admin stuff//
	/**
     * Creates a new User (Admin, Organizer, or Customer) and adds it to the system.
     * * @param userType The intended User type ("Admin", "Organizer", or "Customer").
     * @param firstName First name of the new user.
     * @param lastName Last name of the new user.
     * @param username Username of the new user.
     * @param password Password of the new user.
     * @param userID Unique ID of the new user.
     * @param moneyAvailable Starting funds (only applies if userType is "Customer").
     * @param hasMembership Membership status (only applies if userType is "Customer").
     * @param purchasedTickets Initial list of purchased tickets (only applies if userType is "Customer").
     */
	public void addMember(String userType, String firstName, String lastName, String username, String password, int userID, double moneyAvailable, boolean hasMembership, ArrayList<Ticket> purchasedTickets) {
        switch (userType) {
            case "Admin" -> userList.add(new Admin(firstName, lastName, username, password, userID));
            case "Organizer" -> userList.add(new Organizer(firstName, lastName, username, password, userID));
            case "Customer" -> userList.add(new Customer(firstName, lastName, username, password, userID, moneyAvailable, hasMembership, purchasedTickets));
            default -> System.out.println("Error: Invalid userType string.");
        }
    }

	/**
     * Displays all users currently in the system using a formatted, aligned table.
     */
    public void displayAllMembers() {
        System.out.println("\n--- All Members ---");
        System.out.printf("%-5s | %-25s | %-15s | %-15s%n", "ID", "Name", "Username", "Role");
        System.out.println("--------------------------------------------------------------------");

        for (User u : userList) {
            String fullName = u.getFirstName() + " " + u.getLastName();
            System.out.printf("%-5d | %-25s | %-15s | %-15s%n", 
                u.getUserID(), fullName, u.getUsername(), u.getClass().getSimpleName());
        }
    }

	/**
     * Searches for a User by matching a query against IDs, names, and usernames.
     * * @param query The string to search for.
     * @return The matching User object, or null if no match is found.
     */
    public User searchMember(String query) {
        for (User u : userList) {
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

	/**
     * Updates the first and last name of a specific User.
     * * @param u The User to update.
     * @param newFirstName The new first name.
     * @param newLastName The new last name.
     */
    public void updateUserName(User u, String newFirstName, String newLastName) {
        u.setFirstName(newFirstName);
        u.setLastName(newLastName);
    }

	/**
     * Attempts to update a User's username, ensuring the new username is unique.
     * * @param u The User to update.
     * @param newUsername The requested new username.
     * @return True if the username was unique and successfully updated; false otherwise.
     */
    public boolean updateUsername(User u, String newUsername) {
        for (User existingUser : userList) {
            if (existingUser.getUsername().equalsIgnoreCase(newUsername)) {
                return false; 
            }
        }
        u.setUsername(newUsername);
        return true;
    }
    
	/**
     * Updates the password for a specific User.
     * * @param u The User to update.
     * @param newPassword The new password.
     */
    public void updateUserPassword(User u, String newPassword) {
        u.setPassword(newPassword);
    }

	/**
     * Removes a specific User from the system.
     * * @param u The User to delete.
     */
    public void deleteUser(User u) {
        userList.remove(u);
    }
}