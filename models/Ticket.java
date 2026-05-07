package models;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Ticket {
    private int ticketID;
    private int eventID;
    private double price;
    private int seatNumber;
    private boolean isSold;
    private final double SALES_TAX = .0875;

    public Ticket(int ticketID, int eventID, double price, int seatNumber, boolean isSold) {
        this.ticketID = ticketID;
        this.eventID = eventID;
        this.price = price;
        this.seatNumber = seatNumber;
        this.isSold = isSold;
    }
    public int getTicketID() {
        return ticketID;
    }
    public int getEventID() {
        return eventID;
    }
    public double getPrice() {
        return price;
    }
    public int getSeatNumber() {
        return seatNumber;
    }
    public void setIfSold(boolean in) {
        isSold = in;
    }
    public boolean checkIfSold() {
        return isSold;
    }
}
