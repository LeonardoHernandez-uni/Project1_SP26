/**
 * Manages the user interface and provides methods for displaying menus, handling user input, and navigating through different options. The class interacts with the UserManager to manage user accounts and with the Admin class to allow administrators to manage users, venues, and events
 */
package ui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import exceptions.InsufficientFundsException;
import logic.EventManager;
import logic.UserManager;
import logic.VenueManager;
import models.*;

public class MenuHandler {
    /** asks whether the main menu needs to be printed again. */
    static boolean printMainMenu = true;
    /** The current logged in user */
    private static User currentUser = null;
    /** Keeps track of whether the currentUser is an Admin or not. */
    private static boolean isCurrentUserAdmin = false;
    /** Accepts input from the user in the terminal */
    private static final Scanner input = new Scanner(System.in);

    /**
     * Prints the main menu and listens to user input via the terminal. From the
     * main menu, users can register, login, or exit. When the program exits, a call
     * is made to all the type managers to save
     * the contents of their respective ArrayList to a csv file.
     */
    public static void mainMenu() {
        boolean isTerminated = false;

        while (!isTerminated) {
            if (printMainMenu) {
                System.out.println("[Welcome to TicketMiner]");
                System.out.println("r. Register");
                System.out.println("l. Login");
                System.out.println("EXIT. Exit Program");
                System.out.print(">> ");
                printMainMenu = false;
            }
            switch (input.next()) {
                case "r", "R" -> {
                    registerMenu();
                    printMainMenu = true;
                }
                case "l", "L" -> {
                    // Pulling the list from UserManager
                    loginMenu(UserManager.getInstance().getUserList());
                    printMainMenu = true;
                }
                case "exit", "Exit" -> System.out.println("Input invalid. Did you mean \"EXIT\"?");
                case "EXIT" -> isTerminated = true;
                default -> System.out.println("Input invalid.");
            }
        }
    }

    /**
     * Prints register menu options and listens to user input via Scanner input in
     * the main method.
     * By inputting a series of options, the gui can be used to create a Customer or
     * an Organizer user object with
     * the given attributes, after which said user object will be stored to
     * userList.
     * 
     */
    public static void registerMenu() {
        System.out.println("[What would you like to register as?]");
        System.out.println("c. As a Customer");
        System.out.println("o. As an Organizer");
        System.out.println("b. Back to Main Menu");

        boolean goBack = false;
        while (!goBack) {
            System.out.print(">> ");
            switch (input.next()) {
                case "c", "C" -> {
                    System.out.println("What is your first name?");
                    String firstName = input.next();
                    System.out.println("What is your last name?");
                    String lastName = input.next();
                    System.out.println("What would you like your username to be?");
                    String username = input.next();
                    System.out.println("What would you like your password to be?");
                    String password = input.next();
                    System.out.println("How much money would you like to put into your account?");
                    double moneyAvailable = input.nextDouble();
                    System.out.println("Would you like to sign up for our TicketMiner membership? (y or n?)");
                    boolean isMembership = false;
                    String memberChoice = input.next();
                    if (memberChoice.equalsIgnoreCase("y") || memberChoice.equalsIgnoreCase("yes")) {
                        isMembership = true;
                    }

                    // Using UserManager to get the ID and add the user
                    int id = UserManager.getInstance().generateID();
                    Customer customer = new Customer(firstName, lastName, username, password, id, moneyAvailable,
                            isMembership, new ArrayList<>());
                    UserManager.getInstance().getUserList().add(customer);

                    System.out.println("Registration successful!");
                    RunTicketMiner.logAction(
                            firstName + lastName + "(ID: " + id + ") created a Customer account successfully");
                    goBack = true;
                }
                case "o", "O" -> {
                    System.out.println("What is your first name?");
                    String firstName = input.next();
                    System.out.println("What is your last name?");
                    String lastName = input.next();
                    System.out.println("What would you like your username to be?");
                    String username = input.next();
                    System.out.println("What would you like your password to be?");
                    String password = input.next();

                    // Using UserManager to get the ID and add the user
                    int id = UserManager.getInstance().generateID();
                    Organizer organizer = new Organizer(firstName, lastName, username, password, id);
                    UserManager.getInstance().getUserList().add(organizer);

                    System.out.println("Registration successful!");
                    RunTicketMiner.logAction(
                            firstName + lastName + "(ID: " + id + ") created an Organizer account successfully");
                    goBack = true;
                }
                case "b", "B" -> goBack = true;
                default -> System.out.println("Input invalid");
            }
        }
        System.out.println("");
    }

    /**
     * Prints the user login menu, prompts user for their login details, then
     * verifies them by checking if they exist in userList. If they do not, access
     * is denied to the user
     * and the program will go back to the main menu.
     * 
     * @param userList List of users as retrieved by loadUsers() in UserManager
     *                 class.
     */
    public static void loginMenu(ArrayList<User> userList) {
        if (userList.isEmpty()) {
            System.out.println("System Error: No users loaded.");
            return;
        }
        System.out.println("[Login]");
        System.out.print("Enter username: ");
        String usernameInput = input.next();
        System.out.print("Enter password: ");
        String passwordInput = input.next();

        boolean found = false;
        for (User user : userList) {
            if (user.getUsername().equals(usernameInput) && user.getPassword().equals(passwordInput)) {
                System.out.println("\nLogin Successful! Welcome, " + user.getFirstName() + " " + user.getLastName());
                RunTicketMiner.logAction(user.getUserType() + " " + user.getFirstName() + " " + user.getLastName()
                        + " successfully logged into their account.");

                if (user instanceof Customer customer) {
                    currentUser = customer;
                    System.out.println("Available Funds: $" + customer.getMoneyAvailable());
                    System.out.println("Membership Status: " + (customer.getHasMembership() ? "Active" : "Inactive"));
                    
                    customerMenu(customer);
                }
                if (user instanceof Organizer organizer) {
                    currentUser = organizer;
                    System.out.println("Organizer");
                }
                if (user instanceof Admin admin) {
                    currentUser = admin;
                    isCurrentUserAdmin = true;
                    adminMenu(admin);
                }
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Error: Invalid username or password.");
        }
    }

    /**
     * Prints a gui and listens to user input to manage users by performing methods
     * located within the Admin User object
     * class. The method takes a User Admin called admin in order to access these
     * methods and edit various
     * entries in the userList ArrayList.
     * 
     * @param admin The Admin User object logged in and performing edits to.
     */
    private static void manageUsers(Admin admin) {
        System.out.println("\n[Manage Users]\n1. Add User\n2. View/Search\n3. Update\n4. Delete\n5. Back");
        System.out.print(">> ");
        switch (input.next()) {
            case "1" -> {
                String userType = "Undecided";
                System.out.println("User Type:\n1. Customer\n2. Organizer\n3. Admin");
                do {
                    switch (input.next()) {
                        case "1" -> {
                            userType = "Customer";
                        }
                        case "2" -> {
                            userType = "Organizer";
                        }
                        case "3" -> {
                            userType = "Admin";
                        }
                        default -> {
                            System.out.println("Error: Invalid input");
                        }
                    }
                } while (userType.equals("Undecided"));

                System.out.println("First Name: ");
                String firstName = input.next();
                System.out.println("Last Name: ");
                String lastName = input.next();
                System.out.println("Username: ");
                String userName = input.next();
                System.out.println("Password: ");
                String password = input.next();
                int userID = 0;
                System.out.println(
                        "Would you like us to generate a unique ID?\t1. Yes\t2. No (You will be prompted to input a unique ID)");
                switch (input.next()) {
                    case "1" -> {
                        userID = UserManager.getInstance().generateID();
                    }
                    case "2" -> {
                        System.out.println("Please input a unique ID:");
                        do {
                            try {
                                userID = Integer.parseInt(input.next());
                                if (UserManager.getInstance().isIDUnique(userID) == false) {
                                    System.out.println("The ID you entered isn't unique, please try again.");
                                }
                            } catch (java.lang.NumberFormatException e) {
                                System.out.println(
                                        "Error: Invalid Input (Please enter a whole number, preferrably one with no more than four digits)");
                                userID = 0;
                            }
                        } while (UserManager.getInstance().isIDUnique(userID) == false);
                    }
                    default -> {
                        System.out.println("Error: Invalid input");
                    }
                }
                double moneyAvailable = 0;
                boolean hasMembership = false;
                boolean moveOn = false; // Keeps track of whether we can move on from a menu (get out of a repeating
                                        // menu's loop)
                if (userType.equals("Customer")) {
                    System.out.println("Money Avaliable: ");
                    moneyAvailable = Double.parseDouble(input.next());
                    System.out.println("Does this member have a membership?:\t1. Yes\t2. No");
                    do {

                        switch (input.next()) {
                            case "1" -> {
                                hasMembership = true;
                                moveOn = true;
                            }
                            case "2" -> {
                                hasMembership = false;
                                moveOn = true;
                            }
                            default -> {
                                System.out.println("Error: Invalid input");
                            }
                        }
                    } while (!moveOn);

                }
                UserManager.getInstance().addMember(userType, firstName, lastName, userName, password, userID, moneyAvailable,
                        hasMembership, new ArrayList<>());
                System.out.println(userType + " " + firstName + " " + lastName + " has been created successfully!");
                RunTicketMiner.logAction(currentUser.getUserType() + " " + currentUser.getUserID() + " created a new "
                        + userType + "(ID: " + userID + ").");
            }
            case "2" -> {
                System.out.println("\n[View/Search Users]\n1. Display All Users \n2. Search for a Member");
                switch (input.next()) {
                    case "1" -> {
                        UserManager.getInstance().displayAllMembers();
                        RunTicketMiner.logAction(currentUser.getUserType() + " " + currentUser.getUserID()
                                + " printed all members to the console");
                    }
                    case "2" -> {
                        System.out.print("Enter ID, Name, or Username to search: ");
                        User u = UserManager.getInstance().searchMember(input.next());
                        if (u != null) {
                            System.out.println("User found! ID: " + u.getUserID() + "\tName: " + u.getFirstName() + " "
                                    + u.getLastName() + "\tUsername: "
                                    + u.getUsername() + "\tPassword: " + u.getPassword());
                            System.out.print("User Type: " + u.getUserType());
                            if (u instanceof Customer customer) {
                                System.out.println("\tMoneyAvaliable: " + customer.getMoneyAvailable()
                                        + "\tHas Membership: " + customer.getHasMembership() + "\tTickets Purchased: "
                                        + customer.getAmountOfTicketsPurchased());
                            }
                            RunTicketMiner.logAction(currentUser.getUserType() + " " + currentUser.getUserID()
                                    + " searched and found information for user " + u.getUserID());
                        } else {
                            System.out.println("User not found.");
                            RunTicketMiner.logAction(currentUser.getUserType() + currentUser.getUserID()
                                    + " attempted to search for a user but didn't find them");
                        }
                    }
                    default -> {
                        System.out.println("Error: Invalid input.");
                    }
                }

            }
            case "3" -> {
                System.out.print("Enter ID, Name, or Username to update: ");
                User u = UserManager.getInstance().searchMember(input.next());
                if (u != null) {
                    System.out.println(
                            "What would you like to update?\n1. Change Name\n2. Change Username\n3. Change Password");
                    switch (input.next()) {
                        case "1" -> {
                            System.out.print("New First Name: ");
                            String newFirstName = input.next();
                            System.out.print("New Last Name: ");
                            String newLastName = input.next();
                            UserManager.getInstance().updateUserName(u, newFirstName, newLastName);
                            System.out.println("Name updated successfully!");
                            RunTicketMiner.logAction(currentUser.getUserType() + currentUser.getUserID() + " updated "
                                    + u.getUserID() + "'s first and last name");
                        }
                        case "2" -> {
                            boolean usernameUpdated = false;
                            while (!usernameUpdated) {
                                System.out.print("New Username: ");
                                String newUsername = input.next();
                                if (UserManager.getInstance().updateUsername(u, newUsername)) {
                                    System.out.println("Username updated successfully!");
                                    RunTicketMiner.logAction(currentUser.getUserType() + " " + currentUser.getUserID()
                                            + " updated " + u.getUserID() + "'s username");
                                    usernameUpdated = true;
                                } else {
                                    System.out.println("Error: Username already taken, please try a different one.");
                                    RunTicketMiner.logAction(currentUser.getUserType() + " " + currentUser.getUserID()
                                            + " attempted to update " + u.getUserID()
                                            + "'s username but was unsuccessful");
                                }
                            }
                        }
                        case "3" -> {
                            System.out.print("New Password: ");
                            UserManager.getInstance().updateUserPassword(u, input.next());
                            System.out.println("Password updated successfully!");
                            RunTicketMiner.logAction(currentUser.getUserType() + " " + currentUser.getUserID()
                                    + " updated " + u.getUserID() + "'s password");
                        }
                        default -> System.out.println("Error: Invalid input.");
                    }
                } else {
                    System.out.println("User not found.");
                }
            }
            case "4" -> {
                System.out.print("Enter ID, Name, or Username to DELETE: ");
                User u = UserManager.getInstance().searchMember(input.next());
                if (u != null) {
                    System.out.print("Are you sure you want to delete " + u.getFirstName() + "? (y/n): ");
                    if (input.next().equalsIgnoreCase("y")) {
                        RunTicketMiner.logAction(currentUser.getUserType() + " " + currentUser.getUserID()
                                + " deleted user " + u.getUserID());
                        UserManager.getInstance().deleteUser(u);
                    } else {
                        System.out.println("User not found.");
                    }
                }
            }
        }
    }

    /**
     * Prints a gui and listens to user input to manage venues by performing methods
     * located within the Admin User object
     * class. The method takes a User Admin called admin in order to access these
     * methods and edit various
     * entries in the venueList ArrayList.
     * 
     * @param admin The Admin User object logged in and performing edits to the
     *              venueList.
     */
    private static void manageVenues(Admin admin) {
        System.out.println("\n[Manage Venues]\n1. Add Venue\n2. View/Search\n3. Update\n4. Delete\n5. Back");
        System.out.print(">> ");
        switch (input.next()) {
            // manageVenues case "1"
            case "1" -> {
                System.out.println("Venue Type:\n1. Arena\n2. Auditorium\n3. OpenAir\n4. Stadium");
                String venueType = "Undecided";
                do {
                    System.out.print(">> ");
                    switch (input.next()) {
                        case "1" -> venueType = "Arena";
                        case "2" -> venueType = "Auditorium";
                        case "3" -> venueType = "OpenAir";
                        case "4" -> venueType = "Stadium";
                        default -> System.out.println("Error: Invalid input.");
                    }
                } while (venueType.equals("Undecided"));

                System.out.print("Name: ");
                input.nextLine();
                String name = input.nextLine().trim();
                System.out.print("Capacity: ");
                int capacity = Integer.parseInt(input.next());
                System.out.print("Concert Capacity: ");
                int concertCapacity = Integer.parseInt(input.next());
                System.out.print("Cost: ");
                double cost = Double.parseDouble(input.next());
                System.out.print("VIP %: ");
                double vipPercent = Double.parseDouble(input.next());
                System.out.print("Gold %: ");
                int goldPercent = Integer.parseInt(input.next());
                System.out.print("Silver %: ");
                int silverPercent = Integer.parseInt(input.next());
                System.out.print("Bronze %: ");
                int bronzePercent = Integer.parseInt(input.next());
                System.out.print("General Admission %: ");
                int generalAdmissionPercent = Integer.parseInt(input.next());
                System.out.print("Reserved Extra %: ");
                int reservedExtraPercent = Integer.parseInt(input.next());

                VenueManager.getInstance().addVenue(venueType, name, capacity, concertCapacity, cost, vipPercent, goldPercent, silverPercent,
                        bronzePercent, generalAdmissionPercent, reservedExtraPercent);
                System.out.println(venueType + " \"" + name + "\" added successfully!");
                RunTicketMiner.logAction(currentUser.getUserType() + " " + currentUser.getUserID()
                        + " created a new venue (Name: " + name + ")");
            }
            case "2" -> {
                System.out.println("\n[View/Search Venues]\n1. Display All Venues\n2. Search for a Venue");
                switch (input.next()) {
                    case "1" -> {
                        VenueManager.getInstance().displayAllVenues();
                        RunTicketMiner.logAction(currentUser.getUserType() + " " + currentUser.getUserID()
                                + " printed all venues to console");
                    }
                    case "2" -> {
                        System.out.print("Enter Venue ID, Name, or Type to search: ");
                        Venue v = VenueManager.getInstance().searchVenue(input.next());
                        if (v != null) {
                            System.out.println(v);
                            RunTicketMiner.logAction(currentUser.getUserType() + " " + currentUser.getUserID()
                                    + " searched and got " + v.getType() + " " + v.getId());
                        } else {
                            System.out.println("Venue not found.");
                        }
                    }
                    default -> System.out.println("Error: Invalid input.");
                }
            }

            case "3" -> {
                System.out.print("Enter Venue ID, Name, or Type to update: ");
                Venue v = VenueManager.getInstance().searchVenue(input.next());
                if (v != null) {
                    System.out.println("What would you like to update?\n1. Name\n2. Cost\n3. Capacity");
                    switch (input.next()) {
                        case "1" -> {
                            System.out.print("New Name: ");
                            String newName = input.nextLine().trim();
                            if (newName.isEmpty())
                                newName = input.nextLine().trim();
                            VenueManager.updateVenueName(v, newName);
                            System.out.println("Venue name updated successfully!");
                            RunTicketMiner.logAction(currentUser.getUserType() + " " + currentUser.getUserID() + " updated the name of venue " + v.getId());
                        }
                        case "2" -> {
                            System.out.print("New Cost: ");
                            VenueManager.getInstance().updateVenueCost(v, Double.parseDouble(input.next()));
                            System.out.println("Venue cost updated successfully!");
                            RunTicketMiner.logAction(currentUser.getUserType() + " " + currentUser.getUserID() + " updated the cost of venue " + v.getId());
                        }
                        case "3" -> {
                            System.out.print("New Capacity: ");
                            VenueManager.getInstance().updateVenueCapacity(v, Integer.parseInt(input.next()));
                            System.out.println("Venue capacity updated successfully!");
                            RunTicketMiner.logAction(currentUser.getUserType() + " " + currentUser.getUserID() + " updated the capacity of venue " + v.getId());
                        }
                        default -> System.out.println("Error: Invalid input.");
                    }
                } else {
                    System.out.println("Venue not found.");
                }
            }
            case "4" -> {
                System.out.print("Enter Venue ID or Name to DELETE: ");
                Venue v = VenueManager.getInstance().searchVenue(input.next());
                if (v != null) {
                    System.out.print("Confirm deletion of " + v.getName() + "? (y/n): ");
                    if (input.next().equalsIgnoreCase("y")) {
                        RunTicketMiner.logAction(currentUser.getUserType() + " " + currentUser.getUserID() + " deleted venue " + v.getId());
                        VenueManager.getInstance().deleteVenue(v);
                    }
                } else {
                    System.out.println("Venue not found.");
                }
            }
            case "5" -> {
                /* Back */ }
            default -> System.out.println("Error: Invalid input.");
        }
    }

    /**
     * Prints a gui and listens to user input to manage events by performing methods
     * located within the Admin User object
     * class. The method takes a User Admin called admin in order to access these
     * methods and edit various
     * entries in the eventList ArrayList.
     * 
     * @param admin The Admin User object logged in and performing edits to the
     *              eventList.
     */
    private static void manageEvents(Admin admin) {
        System.out.println("\n[Manage Events]\n1. Add Event\n2. View/Search\n3. Update\n4. Delete\n5. Back");
        System.out.print(">> ");
        switch (input.next()) {
            // manageEvents case "1"
            case "1" -> {
                System.out.println("Event Type:\n1. Concert\n2. Special\n3. Sport");
                String eventType = "Undecided";
                do {
                    System.out.print(">> ");
                    switch (input.next()) {
                        case "1" -> eventType = "Concert";
                        case "2" -> eventType = "Special";
                        case "3" -> eventType = "Sport";
                        default -> System.out.println("Error: Invalid input.");
                    }
                } while (eventType.equals("Undecided"));

                System.out.print("Name: ");
                input.nextLine();
                String name = input.nextLine().trim();
                System.out.print("Date (YYYY-MM-DD): ");
                LocalDate date = LocalDate.parse(input.next());
                System.out.print("Time (e.g. 7:30 PM): ");
                input.nextLine();
                LocalTime time = LocalTime.parse(input.nextLine().trim().toUpperCase(),
                        DateTimeFormatter.ofPattern("h:mm a"));
                System.out.print("VIP Price: ");
                double vipPrice = Double.parseDouble(input.next());
                System.out.print("Gold Price: ");
                double goldPrice = Double.parseDouble(input.next());
                System.out.print("Silver Price: ");
                double silverPrice = Double.parseDouble(input.next());
                System.out.print("Bronze Price: ");
                double bronzePrice = Double.parseDouble(input.next());
                System.out.print("General Admission Price: ");
                double generalAdmissionPrice = Double.parseDouble(input.next());

                EventManager.getInstance().addEvent(eventType, name, date, time, vipPrice, goldPrice, silverPrice, bronzePrice,
                        generalAdmissionPrice);
                System.out.println(eventType + " \"" + name + "\" added successfully!");
                RunTicketMiner.logAction(currentUser.getUserType() + " " + currentUser.getUserID() + " created a new venue (Name: " + name);
            }
            case "2" -> {
                System.out.println("\n[View/Search Events]\n1. Display All Events\n2. Search for an Event");
                switch (input.next()) {
                    case "1" -> {
                        EventManager.getInstance().displayAllEvents();
                        RunTicketMiner.logAction(currentUser.getUserType() + " " + currentUser.getUserID() + " printed all events to console.");
                    }
                    case "2" -> {
                        System.out.print("Enter Event ID, Name, or Date (YYYY-MM-DD): ");
                        Event e = EventManager.getInstance().searchEvent(input.next());
                        if (e != null) {
                            System.out.println(e);
                            RunTicketMiner.logAction(currentUser.getUserType() + " " + currentUser.getUserID() + " searched and found event " + e.getId());
                        }
                        else
                            System.out.println("Event not found.");
                    }
                    default -> System.out.println("Error: Invalid input.");
                }
            }
            case "3" -> {
                System.out.print("Enter Event ID, Name, or Date to update: ");
                Event e = EventManager.getInstance().searchEvent(input.next());
                if (e != null) {
                    System.out.println("What would you like to update?\n1. Name\n2. Date & Time");
                    switch (input.next()) {
                        case "1" -> {
                            System.out.print("New Name: ");
                            String newName = input.nextLine().trim();
                            if (newName.isEmpty())
                                newName = input.nextLine().trim();
                            EventManager.getInstance().updateEventName(e, newName);
                            System.out.println("Event name updated successfully!");
                            RunTicketMiner.logAction(currentUser.getUserType() + " " + currentUser.getUserID() + " updated the name of event " + e.getId());
                        }
                        case "2" -> {
                            System.out.print("New Date (YYYY-MM-DD): ");
                            LocalDate newDate = LocalDate.parse(input.next());
                            System.out.print("New Time (e.g. 7:30 PM): ");
                            input.nextLine();
                            LocalTime newTime = LocalTime.parse(input.nextLine().trim().toUpperCase(),
                                    DateTimeFormatter.ofPattern("h:mm a"));
                            EventManager.getInstance().updateEventDateTime(e, newDate, newTime);
                            System.out.println("Event date and time updated successfully!");
                            RunTicketMiner.logAction(currentUser.getUserType() + " " + currentUser.getUserID() + " updated the date and time of event " + e.getId());
                        }
                        default -> System.out.println("Error: Invalid input.");
                    }
                } else {
                    System.out.println("Event not found.");
                }
            }
            case "4" -> {
                System.out.print("Enter Event ID or Name to DELETE: ");
                Event e = EventManager.getInstance().searchEvent(input.next());
                if (e != null) {
                    System.out.print("Confirm cancellation of " + e.getName() + "? (y/n): ");
                    if (input.next().equalsIgnoreCase("y")) {
                        RunTicketMiner.logAction(currentUser.getUserType() + " " + currentUser.getUserID() + " deleted event " + e.getId());
                        EventManager.getInstance().deleteEvent(e);
                    }
                } else {
                    System.out.println("Event not found.");
                }
            }
            case "5" -> {
                /* Back */ }
            default -> System.out.println("Error: Invalid input.");
        }
    }

    /**
     * Prints a gui providing Customer User objects the choice to purchase tickets
     * or print their order summaries. Handles the InsufficientFundsException.
     * * @param customer The Customer User object logged in.
     */
    public static void customerMenu(Customer customer) {
        boolean logout = false;
        while (!logout) {
            System.out.println("\n[Customer Menu]\n1. Purchase Tickets\n2. Print Order Summary\n3. Logout");
            System.out.print(">> ");
            switch (input.next()) {
                case "1" -> {
                    System.out.println("\n--- Available Events ---");
                    for (Event e : EventManager.getInstance().getEventList()) {
                        System.out.println("ID: " + e.getId() + " | Name: " + e.getName() + " | Date: " + e.getDate());
                    }
                    System.out.print("Enter the ID of the event you want to attend: ");
                    Event selectedEvent = EventManager.getInstance().searchEvent(input.next());
                    
                    if (selectedEvent == null) {
                        System.out.println("Event not found.");
                        continue;
                    }

                    System.out.println("\nTicket Prices for " + selectedEvent.getName() + ":");
                    System.out.println("1. VIP: $" + selectedEvent.getVipPrice());
                    System.out.println("2. Gold: $" + selectedEvent.getGoldPrice());
                    System.out.println("3. Silver: $" + selectedEvent.getSilverPrice());
                    System.out.println("4. Bronze: $" + selectedEvent.getBronzePrice());
                    System.out.println("5. General Admission: $" + selectedEvent.getGeneralAdmissionPrice());
                    System.out.print("Select a ticket type (1-5): ");
                    
                    double ticketPrice = 0.0;
                    switch(input.next()) {
                        case "1" -> ticketPrice = selectedEvent.getVipPrice();
                        case "2" -> ticketPrice = selectedEvent.getGoldPrice();
                        case "3" -> ticketPrice = selectedEvent.getSilverPrice();
                        case "4" -> ticketPrice = selectedEvent.getBronzePrice();
                        case "5" -> ticketPrice = selectedEvent.getGeneralAdmissionPrice();
                        default -> {
                            System.out.println("Invalid ticket type.");
                            continue;
                        }
                    }
                    
                    // Create a standard ticket to process the transaction
                    int generatedTicketID = (int)(Math.random() * 100000);
                    Ticket newTicket = new Ticket(generatedTicketID, selectedEvent.getId(), ticketPrice, 0, false);
                    
                    // --- THIS IS WHERE OUR CUSTOM EXCEPTION IS CAUGHT ---
                    try {
                        customer.buyTicket(newTicket);
                        System.out.println("\nPurchase successful! Ticket ID: " + generatedTicketID);
                        System.out.println("Remaining Balance: $" + customer.getMoneyAvailable());
                        RunTicketMiner.logAction("Customer " + customer.getUserID() + " purchased a ticket for event " + selectedEvent.getId());
                    } catch (InsufficientFundsException e) {
                        System.out.println("\n" + e.getMessage()); // Prints our custom error message
                        RunTicketMiner.logAction("Customer " + customer.getUserID() + " failed to purchase ticket due to insufficient funds.");
                    }
                }
                case "2" -> {
                    System.out.println("\nOrder summary text file generation coming soon...");
                }
                case "3" -> logout = true;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    /**
     * Prints a gui providing Admin User objects the choice to modify userList,
     * venueList, or eventList entries.
     * Each choice sends the admin to a menu of options for adding, viewing,
     * updating, and deleting entries in each
     * respective ArrayList.
     * 
     * @param admin The Admin User object logged in and performing edits to the
     *              selected object type.
     */
    public static void adminMenu(Admin admin) {
        boolean logout = false;
        while (!logout) {
            System.out.println("\n[Admin Menu]\n1. Manage Users\n2. Manage Venues\n3. Manage Events\n4. Logout");
            System.out.print(">> ");
            switch (input.next()) {
                case "1" -> manageUsers(admin);
                case "2" -> manageVenues(admin);
                case "3" -> manageEvents(admin);
                case "4" -> logout = true;
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}