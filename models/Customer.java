package models;
import java.util.*;
public class Customer extends User{
    private double moneyAvailable;
    private boolean hasMembership;
    private ArrayList<Ticket> purchasedTickets;
    
    /** Constructs a customer class by taking superclass User's parameters and instantiating them in a super class.
     * Also instantiates Customer unique attributes "moneyAvaliable", "hasMembership", and the ArrayList "ticketsPurchased"
     * @param firstName The user's first name.
     * @param lastName The user's last name.
     * @param username The user's username.
     * @param password The user's password.
     * @param userID The user's unique ID (encouraged to be no more than 4 digits seeing that typing long values out is tedious ;-;)
     * @param moneyAvailable The customer's funds as represented by a type double
     * @param hasMembership The customer's membership status.
     * @param purchasedTickets An ArrayList that stores all the Ticket() objects the user has purchased with (hopefully) their funds.
     */
    public Customer(String firstName, String lastName, String username, String password, int userID, double moneyAvailable, boolean hasMembership, ArrayList<Ticket> purchasedTickets) {
        super(firstName, lastName, username, password, userID);
        this.moneyAvailable = moneyAvailable;
        this.hasMembership = hasMembership;
        this.purchasedTickets = purchasedTickets;
    }

    /** This method buys a ticket and stores it to the customer's purchasedTickets attribute if the ticket isn't already sold and the customer has enough funds to cover the ticket.
     * When the customer buys the ticket, said ticket will be marked as sold meaning no other customer can buy that ticket again.
     * 
     * @param ticket The ticket the customer is trying to buy.
     */
    public void buyTicket(Ticket ticket) {
        if (moneyAvailable >= ticket.getPrice() && !ticket.checkIfSold()) {
            ticket.setIfSold(true);
            moneyAvailable-= ticket.getPrice();
            purchasedTickets.add(ticket);
        } else if ((moneyAvailable >= ticket.getPrice() == false)) {
            System.out.print("Error: Not enough funds.");
        } else {
            System.out.print("Error: The ticket trying to be bought has been sold (event has been sold out)");
        }
    }
    

    public void viewAvaliableEvents() {

    }

    @Override
    /** Return the string "Customer" for methods where instanceof isn't sufficient/necessary */
    public String getUserType() {
        return "Customer";
    }
    /** Returns the amount of funds a user has available in thier account as a double */
    public double getMoneyAvailable() { return moneyAvailable; }
    /** Returns whether a customer has a membership or not as either true or false. */
    public boolean getHasMembership() { return hasMembership; }
    /** Returns the amount of tickets a customer has purchased by getting the size of the purchased tickets ArrayList */
    public int getAmountOfTicketsPurchased() {return purchasedTickets.size();}
}
