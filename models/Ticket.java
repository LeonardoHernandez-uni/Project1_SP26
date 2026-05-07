package models;

public class Ticket {
    private int ticketID;
    private String ticketType;
    private int eventID;
    private double price;
    private int seatNumber;
    private boolean isSold;
    private final double SALES_TAX = .0875;
    private Event event;

    public Ticket(int ticketID, int eventID, double price, int seatNumber, boolean isSold) {
        this.ticketID = ticketID;
        this.eventID = eventID;
        this.price = price;
        this.seatNumber = seatNumber;
        this.isSold = isSold;
    }
    public Ticket(int ticketID, String ticketType, int eventID, double price, int seatNumber, boolean isSold, Event event) {
        this.ticketID = ticketID;
        this.ticketType = ticketType;
        this.eventID = eventID;
        this.price = price;
        this.seatNumber = seatNumber;
        this.isSold = isSold;
        this.event = event;
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
    public Event getEvent() {
        return event;
    }
    public String getTicketType() {
        return ticketType;
    }
}
