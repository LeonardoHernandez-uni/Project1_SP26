package models;


import java.time.LocalDate;

public class Concert extends Event{
    public Concert(int eventID, String name, LocalDate dateTime, String status, Venue venue) {
        super(eventID, name, dateTime, status, venue);
    }
}
