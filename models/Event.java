package models;
import java.time.*;
public abstract class Event {
    private int eventID;
    private String name;
    private LocalDate dateTime;
    private String status;
    private Venue venue;

    public abstract void createEvent();

    public abstract boolean cancelEvent();

    public abstract void changeDate();

    public abstract void changeName();
}
