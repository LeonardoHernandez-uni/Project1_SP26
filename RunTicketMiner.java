import java.util.*;

/**
 * TicketMiner's user interface and loading/saving information mechanism.
 * 
 * @author Leonardo Andree Hernandez
 * @author Kevin Pinon
 */
public class RunTicketMiner {
    User currentUser;

    public static void main(String[] args) {
        // Leo: Leaving this empty until other classes are created (the errors are
        // driving me insane)
        Scanner input = new Scanner(System.in);

        System.out.println("[Welcome to TicketMiner]");
        System.out.println("Register ---> r");
        System.out.println("Login ------> l");
        System.out.println("Exit -------> EXIT");
        

        boolean isTerminated = false;
        while (!isTerminated) {
            System.out.print(">> ");
            switch (input.next()) {
                case "r", "R" -> {
                }
                case "l", "L" -> {
                }
                case "exit" -> System.out.println("Input invalid. Did you mean \"EXIT\"?");
                case "EXIT" -> {
                    isTerminated = true;
                    input.close();
                }
                default -> System.out.println("Input invalid.");
            }
        }
    }

    /*
    public void loadData() {
    }
    */
   /*
    public void saveData() {
    }
    */
   /*
    public void logAction(String action) {
    }
    */
}