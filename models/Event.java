package models;
import java.time.*;
public abstract class Event {
    private int eventID;
    private String name;
    private LocalDate dateTime;
    private String status;
    private Venue venue;

    public Event(int eventID, String name, LocalDate dateTime, String status, Venue venue) {
        this.eventID = eventID;
        this.name = name;
        this.dateTime = dateTime;
        this.status = status;
        this.venue = venue;
    }

    public int getEventID() {
    return eventID;
}

public void setEventID(int eventID) {
    this.eventID = eventID;
}

public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

public LocalDate getDateTime() {
    return dateTime;
}

public void setDateTime(LocalDate dateTime) {
    this.dateTime = dateTime;
}

public String getStatus() {
    return status;
}

public void setStatus(String status) {
    this.status = status;
}

public Venue getVenue() {
    return venue;
}

public void setVenue(Venue venue) {
    this.venue = venue;
}
}
