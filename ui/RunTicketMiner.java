package ui;
import java.util.*;
import models.*;
import logic.UserManager;

import java.io.*;
/**
 * TicketMiner's user interface and loading/saving information mechanism.
 * 
 * @author Leonardo Andree Hernandez
 * @author Kevin Pinon
 */
public class RunTicketMiner {
    /** */
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
        RunTicketMiner rtm = new RunTicketMiner();
        rtm.loadData(); 
        printMainMenu = true;
        MenuHandler.mainMenu();
        input.close();
    }

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
                    MenuHandler.registerMenu();
                    printMainMenu = true;
                }
                case "l", "L" -> {
                    MenuHandler.loginMenu(UserManager.getUserList());
                    printMainMenu = true;
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

    public void loadData() {
        String customerFile = "Customer_List_PA1.csv";
        String line = "";
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(customerFile))) {
            // Skip the header line
            br.readLine(); 

            while ((line = br.readLine()) != null) {
                // Split line by comma
                String[] data = line.split(csvSplitBy);

                // Mapping CSV columns to variables based on file structure 
                int id = Integer.parseInt(data[0]);
                String firstName = data[1];
                String lastName = data[2];
                String username = data[3];
                String password = data[4];
                String userType = data[5];

                if (userType.equalsIgnoreCase("Customer")) {
                    double money = Double.parseDouble(data[6]);
                    boolean membership = Boolean.parseBoolean(data[7]);
                    // Initializing with empty list for purchased tickets as per current constructor [cite: 1]
                    ArrayList<Ticket> tickets = new ArrayList<>(); 
                    
                    Customer customer = new Customer(firstName, lastName, username, password, id, money, membership, tickets);
                    UserManager.getUserList().add(customer);
                } 
                // Add similar logic for Organizer or Admin if needed 
            }
        } catch (IOException e) {
            System.out.println("Error loading customer data: " + e.getMessage());
        }
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