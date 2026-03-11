package ui;

import java.util.*;
import logic.UserManager;
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

    /** Prints the main menu and listens to user input via the terminal. */
    public static void mainMenu() {
        /** ask whether the program has been terminated. */
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
                    loginMenu(UserManager.getUserList());
                    printMainMenu = true;
                }
                case "exit", "Exit" -> System.out.println("Input invalid. Did you mean \"EXIT\"?");
                case "EXIT" -> isTerminated = true;
                default -> System.out.println("Input invalid.");
            }
        }
    }

    /**
     * Prints register menu options, executes them, and listens to user input via
     * the terminal.
     */
    public static void registerMenu() {
        System.out.println("[What would you like to register as?]");
        System.out.println("c. As a Customer");
        System.out.println("o. As an Organizer");
        System.out.println("b. Back to Main Menu");

        /**
         * asks if we can stop looping the user interface listener and go back to the
         * main menu
         */
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
                    int id = UserManager.generateID();
                    Customer customer = new Customer(firstName, lastName, username, password, id, moneyAvailable,
                            isMembership, new ArrayList<>());
                    UserManager.getUserList().add(customer);

                    System.out.println("Registration successful!");
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
                    int id = UserManager.generateID();
                    Organizer organizer = new Organizer(firstName, lastName, username, password, id);
                    UserManager.getUserList().add(organizer);

                    System.out.println("Registration successful!");
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
     * verifies them using userList
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

                if (user instanceof Customer customer) {
                    currentUser = customer;
                    System.out.println("Available Funds: $" + customer.getMoneyAvailable());
                    System.out.println("Membership Status: " + (customer.getHasMembership() ? "Active" : "Inactive"));
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

    private static void manageUsers(Admin admin) {
        System.out.println("\n[Manage Users]\n1. Add User\n2. View/Search\n3. Update\n4. Delete\n5. Back");
        System.out.print(">> ");
        switch (input.next()) {
            case "1" -> {
                String userType = "Undecided";
                System.out.println("User Type:\n1. Customer\n2. Organizer\n3. Admin");
                do {
                switch(input.next()) {
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
                } }while(userType.equals("Undecided"));
                
                System.out.println("First Name: ");
                String firstName = input.next();
                System.out.println("Last Name: ");
                String lastName = input.next();
                System.out.println("Username: ");
                String userName = input.next();
                System.out.println("Password: ");
                String password = input.next();
                int userID = 0;
                System.out.println("Would you like to generate a unique ID?\t1. Yes\t2. No (You will be prompted to input a unique ID)");
                switch(input.next()) {
                    case "1" -> {
                        userID = UserManager.generateID();
                    }
                    case "2" -> {
                        System.out.println("Please input an ID: ");
                        do { 
                            userID = input.nextInt();
                        } while (UserManager.isIDUnique(userID) == false);
                    }
                    default -> {
                        System.out.println("Error: Invalid input");
                    }
                }
                double moneyAvailable = 0;
                boolean hasMembership = false;
                if (userType.equals("Customer")) {
                    System.out.println("Money Avaliable: ");
                    moneyAvailable = input.nextDouble();
                    System.out.println("Does this member have a membership?:\t1. Yes\t2. No");
                    switch (input.next()) {
                        case "1" -> {
                            hasMembership = true;
                        }
                        case "2" -> {
                            hasMembership = false;
                        }
                        default -> {
                            System.out.println("Error: Invalid input");
                        }
                    }
                }
                admin.addMember(userType, firstName, lastName, userName, password, userID, moneyAvailable, hasMembership, new ArrayList<Ticket>());
            }
            case "2" -> {
                System.out.println("\n[View/Search Users]\n1. Display All Users \n2. Search for a Member");
                switch (input.next()) {
                    case "1" -> {
                        admin.displayAllMembers();
                    }
                    case "2" -> {
                        System.out.print("Enter ID, Name, or Username to search: ");
                        User u = admin.searchMember(input.next());
                        if (u != null) {
                            System.out.println("User found! ID: " + u.getUserID() + "\tName: " + u.getFirstName() + " " + u.getLastName() + "\tUsername: "
                                    + u.getUsername() + "\tPassword: " + u.getPassword());
                            System.out.print("User Type: " + u.getUserType());
                            if (u instanceof Customer customer) {
                                System.out.println("\tMoneyAvaliable: " + customer.getMoneyAvailable() + "\tHas Membership: " + customer.getHasMembership() + "\tTickets Purchased: " + customer.getAmountOfTicketsPurchased());
                            }
                        } else {
                            System.out.println("User not found.");
                        }
                    }
                    default -> {
                        System.out.println("Error: Invalid input.");
                    }
                }

            }
            case "4" -> {
                System.out.print("Enter ID, Name, or Username to DELETE: ");
                User u = admin.searchMember(input.next());
                if (u != null) {
                    System.out.print("Are you sure you want to delete " + u.getFirstName() + "? (y/n): ");
                    if (input.next().equalsIgnoreCase("y"))
                        admin.deleteUser(u);
                }
            }
        }
    }

    private static void manageVenues(Admin admin) {
        System.out.println("\n[Manage Venues]\n1. Add Venue\n2. View/Search\n3. Update\n4. Delete\n5. Back");
        System.out.print(">> ");
        switch (input.next()) {
            case "2" -> {
                System.out.print("Enter Venue ID, Name, or Type to search: ");
                Venue v = admin.searchVenue(input.next());
                if (v != null)
                    System.out.println(v);
                else
                    System.out.println("Venue not found.");
            }
            case "4" -> {
                System.out.print("Enter Venue ID or Name to DELETE: ");
                Venue v = admin.searchVenue(input.next());
                if (v != null) {
                    System.out.print("Confirm deletion of " + v.getName() + "? (y/n): ");
                    if (input.next().equalsIgnoreCase("y"))
                        admin.deleteVenue(v);
                }
            }
        }
    }

    private static void manageEvents(Admin admin) {
        System.out.println("\n[Manage Events]\n1. Add Event\n2. View/Search\n3. Update\n4. Delete\n5. Back");
        System.out.print(">> ");
        switch (input.next()) {
            case "2" -> {
                System.out.print("Enter Event ID, Name, or Date (YYYY-MM-DD): ");
                Event e = admin.searchEvent(input.next());
                if (e != null)
                    System.out.println(e);
                else
                    System.out.println("Event not found.");
            }
            case "4" -> {
                System.out.print("Enter Event ID or Name to DELETE: ");
                Event e = admin.searchEvent(input.next());
                if (e != null) {
                    System.out.print("Confirm cancellation of " + e.getName() + "? (y/n): ");
                    if (input.next().equalsIgnoreCase("y"))
                        admin.deleteEvent(e);
                }
            }
        }
    }

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