package ui;
import java.util.*;
import logic.UserManager;
import models.*;
/**
 * TicketMiner's user interface and loading/saving information mechanism.
 * 
 * @author Leonardo Andree Hernandez
 * @author Kevin Pinon
 */
public class RunTicketMiner {
    /** Loads an array list of users from the "Customer_List_PA1.csv" file in the root folder.*/
    static ArrayList<User> userList = new ArrayList<>();
    /** The current user as determined by who the user registers or logs in as. */
    static User currentUser;
    /** asks whether the main menu needs to be printed again. */
    static boolean printMainMenu = true;
    /** Accepts input from the user in the terminal */
    private static final Scanner input = new Scanner(System.in);

    /**
     * Initializes all class variables, starts listening to user input in terminal
     * using the input Scanner, and calls the mainMenu method that provides GUI for
     * the user.
     */
    public static void main(String[] args) {
        UserManager.loadData(); 
        printMainMenu = true;
        MenuHandler.mainMenu();
        input.close();
    }

    /*
     * public void saveData() {
     * }
     */
    /*
     * public void logAction(String action) {
     * }
     */
}