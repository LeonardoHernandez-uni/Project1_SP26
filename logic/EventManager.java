/**
 * Manages the list of events
 * Handles loading event data from CSV files and provides access to the event list
 */
package logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import models.Concert;
import models.Event;
import models.Special;
import models.Sport;

public class EventManager {
    /**
     * eventList stores all the events in the system as Event objects within an
     * ArrayList. It is what we load/save data to, and its what we access to modify
     * event entries.
     */
    private static ArrayList<Event> eventList = new ArrayList<>();

    /** Returns eventList. Is used for accessing eventList from other classes. */
    public static ArrayList<Event> getEventList() {
        return eventList;
    }

    /**
     * Loads data from the event file (in this case Event_List_PA1.csv) and parses
     * its entries row by row into eventList as event objects, assigning parameters
     * to them column by column.
     * The method will use the 2nd column of the csv, the type column, to determine
     * whether the event is a Concert, some Special Event, or a Sporting Event.
     */
    public static void loadData() {
        String eventFile = "Event_List_PA1.csv";
        String line;
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(eventFile))) {
            String headerLine = br.readLine();
            if (headerLine == null) return;

            String[] headers = headerLine.split(csvSplitBy);
            Map<String, Integer> colMap = new HashMap<>();
            for (int i = 0; i < headers.length; i++) {
                colMap.put(headers[i].trim().toLowerCase(), i);
            }

            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy, -1);
                
                int id = Integer.parseInt(data[colMap.get("id")]);
                String type = data[colMap.get("type")];
                String name = data[colMap.get("name")];
                
                // Using M/d/yyyy and h:mm a handles both single and double digit months/days/hours
                LocalDate date = LocalDate.parse(data[colMap.get("date")], DateTimeFormatter.ofPattern("M/d/yyyy"));
                LocalTime time = LocalTime.parse(data[colMap.get("time")], DateTimeFormatter.ofPattern("h:mm a"));
                
                double vipPrice = Double.parseDouble(data[colMap.get("vip price")]);
                double goldPrice = Double.parseDouble(data[colMap.get("gold price")]);
                double silverPrice = Double.parseDouble(data[colMap.get("silver price")]);
                double bronzePrice = Double.parseDouble(data[colMap.get("bronze price")]);
                double generalAdmissionPrice = Double.parseDouble(data[colMap.get("general admission price")]);

                if (type.equalsIgnoreCase("Concert")) {
                    eventList.add(new Concert(id, type, name, date, time, vipPrice, goldPrice, silverPrice, bronzePrice, generalAdmissionPrice));
                } else if (type.equalsIgnoreCase("Special")) {
                    eventList.add(new Special(id, type, name, date, time, vipPrice, goldPrice, silverPrice, bronzePrice, generalAdmissionPrice));
                } else if (type.equalsIgnoreCase("Sport")) {
                    eventList.add(new Sport(id, type, name, date, time, vipPrice, goldPrice, silverPrice, bronzePrice, generalAdmissionPrice));
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading event data: " + e.getMessage());
        }
    }

    public static void saveData() {

        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy_MM_dd");

        try (FileWriter file = new FileWriter(LocalDate.now().format(pattern) + "_Event_List_PA1.csv")) {

            file.write(
                    "ID,Type,Name,Date,Time,VIP Price,Gold Price,Silver Price,Bronze Price,General Admission Price\n");

            for (Event event : eventList) {
                file.write(
                    event.getId() + "," +
                    event.getEventType() + "," + 
                    event.getName() + "," +
                    event.getDate() + "," +
                    event.getTime() + "," + 
                    event.getVipPrice() + "," + 
                    event.getGoldPrice() + "," + 
                    event.getSilverPrice() + "," +
                    event.getBronzePrice() + "," + 
                    event.getGeneralAdmissionPrice() + "\n"
            );
            }

        } catch (IOException e) {
            System.err.println("Error saving event data: " + e.getMessage());
        }
    }

    //refactored admin stuff
    public static void displayAllEvents() {
        System.out.println("\n--- All Events ---");
        System.out.printf("%-5s | %-30s | %-10s | %-12s | %-10s%n", "ID", "Name", "Type", "Date", "Time");
        System.out.println("-----------------------------------------------------------------------------");
        
        for (Event e : eventList) { 
            System.out.printf("%-5d | %-30s | %-10s | %-12s | %-10s%n", 
                e.getId(), e.getName(), e.getEventType(), e.getDate().toString(), e.getTime().toString());
        }
    }

    /**
     * Creates a new Event and adds it to the system. The ID is automatically generated.
     * * @param type The type of event ("Concert", "Special", or "Sport").
     * @param name The name of the event.
     * @param date The scheduled date of the event.
     * @param time The scheduled start time of the event.
     * @param vipPrice The price for a VIP ticket.
     * @param goldPrice The price for a Gold ticket.
     * @param silverPrice The price for a Silver ticket.
     * @param bronzePrice The price for a Bronze ticket.
     * @param generalAdmissionPrice The price for a General Admission ticket.
     */
    public static void addEvent(String type, String name, LocalDate date, LocalTime time, double vipPrice, double goldPrice, double silverPrice, double bronzePrice, double generalAdmissionPrice) {
        int newID = eventList.stream().mapToInt(Event::getId).max().orElse(0) + 1;
        switch (type) {
            case "Concert" -> eventList.add(new Concert(newID, type, name, date, time, vipPrice, goldPrice, silverPrice, bronzePrice, generalAdmissionPrice));
            case "Special" -> eventList.add(new Special(newID, type, name, date, time, vipPrice, goldPrice, silverPrice, bronzePrice, generalAdmissionPrice));
            case "Sport" -> eventList.add(new Sport(newID, type, name, date, time, vipPrice, goldPrice, silverPrice, bronzePrice, generalAdmissionPrice));
            default -> System.out.println("Error: Invalid event type.");
        }
    }

    /**
     * Searches for an Event by matching a query against its ID, Name, or Date (YYYY-MM-DD).
     * * @param query The string to search for.
     * @return The matching Event object, or null if no match is found.
     */
    public static Event searchEvent(String query) {
        for (Event e : eventList) { 
            if (String.valueOf(e.getId()).equals(query) || e.getName().equalsIgnoreCase(query) || e.getDate().toString().equals(query)) { 
                return e;
            }
        }
        return null;
    }

    /**
     * Updates the name of a specific Event.
     * * @param e The Event to update.
     * @param newName The new name for the event.
     */
    public static void updateEventName(Event e, String newName) { e.setName(newName); }
    
    /**
     * Updates the date and time of a specific Event.
     * * @param e The Event to update.
     * @param newDate The new date for the event.
     * @param newTime The new time for the event.
     */
    public static void updateEventDateTime(Event e, LocalDate newDate, LocalTime newTime) { e.setDate(newDate); e.setTime(newTime); }

    /**
     * Removes a specific Event from the system.
     * * @param e The Event to delete.
     */
    public static void deleteEvent(Event e) { eventList.remove(e); }
}
