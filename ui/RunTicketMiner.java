package ui;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import logic.*;
import models.*;

/**
 * TicketMiner's user interface and loading/saving information mechanism.
 * 
 * @author Leonardo Andree Hernandez
 * @author Kevin Pinon
 */
public class RunTicketMiner {
    /**
     * Loads an array list of users from the "Customer_List_PA1.csv" file in the
     * root folder.
     */
    static ArrayList<User> userList = new ArrayList<>();
    /**
     * Loads an array list of users from the "Venue_List_PA1.csv" file in the root
     * folder.
     */
    static ArrayList<Venue> venueList = new ArrayList<>();
    /** The current user as determined by who the user registers or logs in as. */
    static User currentUser;
    /** Keeps an array list log of all actions as strings. */
    static ArrayList<String> actionLog = new ArrayList<>();
    /** Accepts input from the user in the terminal */
    private static final Scanner input = new Scanner(System.in);

    /**
     * Initializes all class variables, starts listening to user input in terminal
     * using the input Scanner, and calls the mainMenu method that provides GUI for
     * the user.
     */
    public static void main(String[] args) {
        UserManager.loadData();
        VenueManager.loadData();
        EventManager.loadData();
        MenuHandler.mainMenu();
        UserManager.saveData();
        VenueManager.saveData();
        EventManager.saveData();
        printActionLog();
        input.close();
    }

    /** Adds a String representing an action to the actionLog. */
    public static void logAction(String action) {
        actionLog.add(action);
    }

    /** Writes the actionLog to a text file labeled with the current date. Very nice, very evil.*/
    public static void printActionLog() {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy_MM_dd");
        try (FileWriter file = new FileWriter(LocalDate.now().format(pattern) + "_Log.txt")) {
            for (String action : actionLog) {
                file.write(action + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing action log: " + e.getMessage());
        }
    }
}