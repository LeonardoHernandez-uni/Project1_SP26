package ui;

import java.util.*;
import models.*;
import logic.UserManager;

public class MenuHandler {
    /** asks whether the main menu needs to be printed again. */
    static boolean printMainMenu = true;
    /** Accepts input from the user in the terminal */
    private static final Scanner input = new Scanner(System.in);

    /** Prints the main menu and listens to user input via the terminal. */
    public static void mainMenu() {
        /** ask whether the program has been terminated. */
        boolean isTerminated = false;

        while (!isTerminated) {
            if (printMainMenu) {
                System.out.println("[Welcome to TicketMiner]");
                System.out.println("Register ---> r");
                System.out.println("Login ------> l");
                System.out.println("Exit -------> EXIT");
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

    /** Prints register menu options, executes them, and listens to user input via the terminal. */
    public static void registerMenu() {
        System.out.println("[What would you like to register as?]");
        System.out.println("As a Customer --------> c");
        System.out.println("As an Organizer ------> o");
        System.out.println("Back -----------------> b");

        /** asks if we can stop looping the user interface listener and go back to the main menu */
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
                    Customer customer = new Customer(firstName, lastName, username, password, id, moneyAvailable, isMembership, new ArrayList<>());
                    UserManager.getUserList().add(customer);
                    
                    System.out.println("Registration successful!");
                    goBack = true;
                }
                case "o", "O" -> { }
                case "b", "B" -> goBack = true;
                default -> System.out.println("Input invalid");
            }
        }
        System.out.println("");
    }

    /** Prints the user login menu, prompts user for their login details, then verifies them using userList
     * @param userList List of users as retrieved by loadUsers() in UserManager class.
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
                    System.out.println("Available Funds: $" + customer.getMoneyAvailable());
                    System.out.println("Membership Status: " + (customer.getHasMembership() ? "Active" : "Inactive"));
                }
                found = true;
                break; 
            }
        }

        if (!found) {
            System.out.println("Error: Invalid username or password.");
        }
    }
}