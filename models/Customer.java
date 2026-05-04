package models;
import java.util.*;

import exceptions.InsufficientFundsException;
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

    /** * This method buys a ticket and stores it to the customer's purchasedTickets attribute.
     * Throws a custom exception if the user does not have enough money.
     * * @param ticket The ticket the customer is trying to buy.
     * @throws InsufficientFundsException if the customer's funds are lower than the ticket price.
     */
    public void buyTicket(Ticket ticket) throws InsufficientFundsException {
        if (ticket.checkIfSold()) {
            System.out.println("Error: The ticket trying to be bought has been sold (event has been sold out)");
            return; // Stop the method
        }
        
        if (moneyAvailable < ticket.getPrice()) {
            // Throwing our custom exception instead of just printing a message!
            throw new InsufficientFundsException("Transaction failed: " + this.getUsername() + " does not have enough funds for this " + ticket.getPrice() + " ticket.");
        }
        
        // If we make it here, the ticket is available and they have the money
        ticket.setIfSold(true);
        moneyAvailable -= ticket.getPrice();
        purchasedTickets.add(ticket);
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

    @Override
    public String toCSVString() {
        // Appends the specific customer data to the base user data
        return super.toCSVString() + "," + moneyAvailable + "," + hasMembership + "," + getAmountOfTicketsPurchased();
    }
}
