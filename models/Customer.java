package models;
import java.util.*;
public class Customer extends User{
    private double moneyAvailable;
    private boolean hasMembership;
    private ArrayList<Ticket> purchasedTickets;

    public Customer(String firstName, String lastName, String username, String password, int userID, double moneyAvailable, boolean hasMembership, ArrayList<Ticket> purchasedTickets) {
        super(firstName, lastName, username, password, userID);
        this.moneyAvailable = moneyAvailable;
        this.hasMembership = hasMembership;
        this.purchasedTickets = purchasedTickets;
    }

    public void buyTicket(Ticket ticket) {
        if (moneyAvailable >= ticket.getPrice() && !ticket.checkIfSold()) {
            ticket.setIfSold(true);
            moneyAvailable-= ticket.getPrice();
            purchasedTickets.add(ticket);
        } else {
            System.out.print("Error: Not enough funds.");
        }
    }
    
    public void viewAvaliableEvents() {

    }

    public double getMoneyAvailable() { return moneyAvailable; }
    public boolean getHasMembership() { return hasMembership; }
}
