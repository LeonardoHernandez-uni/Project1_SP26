import java.util.*;
/**
 * TicketMiner's user interface and loading/saving information mechanism.
 * 
 * @author Leonardo Andree Hernandez
 * @author Kevin Pinon
 */
public class RunTicketMiner {
    /** */
    ArrayList<User> userList;
    /** The current user as determined by who the user registers or logs in as. */
    User currentUser;
    /** Accepts input from the user in the terminal */
    private static final Scanner input = new Scanner(System.in);

    /**
     * Initializes all class variables, starts listening to user input in terminal
     * using the input Scanner, and calls the mainMenu method that provides GUI for
     * the user.
     */
    public static void main(String[] args) {
        mainMenu();
        input.close();
    }

    /** Prints the main menu and listens to user input via the terminal. */
    public static void mainMenu() {
        /** ask whether the program has been terminated. */
        boolean isTerminated = false;
        /** asks whether the main menu needs to be printed again. */
        boolean printMainMenu = true;

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
                }
                case "exit", "Exit" -> System.out.println("Input invalid. Did you mean \"EXIT\"?");
                case "EXIT" -> {
                    isTerminated = true;
                }
                default -> {
                    System.out.println("Input invalid.");
                }
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
                    boolean isMembership;
                    switch (input.next()) {
                        case "y", "Y", "yes", "Yes", "YES" -> {
                            isMembership = true;
                        }
                        case "n", "N", "no", "No", "NO" -> {
                            isMembership = false;
                        }
                    }
                    // Insert constructor for a Customer object then add it to userList
                }
                case "o", "O" -> {

                }
                case "b", "B" -> goBack = true;
                default -> System.out.println("Input invalid");
            }
        }
        System.out.println("");
    }

    /*
     * public void loadData() {
     * }
     */
    /*
     * public void saveData() {
     * }
     */
    /*
     * public void logAction(String action) {
     * }
     */
}