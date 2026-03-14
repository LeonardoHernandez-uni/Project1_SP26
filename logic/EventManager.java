/**
 * Manages the list of events
 * Handles loading event data from CSV files and provides access to the event list
 */
package logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import models.Concert;
import models.Event;
import models.Special;
import models.Sport;

public class EventManager {
    /** eventList stores all the events in the system as Event objects within an ArrayList. It is what we load/save data to, and its what we access to modify event entries. */
	private static ArrayList<Event> eventList = new ArrayList<>();

    /** Returns eventList. Is used for accessing eventList from other classes.*/
    public static ArrayList<Event> getEventList() {
        return eventList;
    }

    
	/** Loads data from the event file (in this case Event_List_PA1.csv) and parses its entries row by row into eventList as event objects, assigning parameters to them column by column.
	 *  The method will use the 2nd column of the csv, the type column, to determine whether the event is a Concert, some Special Event, or a Sporting Event.
	 */
	public static void loadData() {
        String eventFile = "Event_List_PA1.csv";
        String line;
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(eventFile))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                int id = Integer.parseInt(data[0]);
                String type = data[1];
                String name = data[2];
                LocalDate date = LocalDate.parse(data[3], DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                LocalTime time = LocalTime.parse(data[4], DateTimeFormatter.ofPattern("h:mm a"));
                double vipPrice = Double.parseDouble(data[5]);
                double goldPrice = Double.parseDouble(data[6]);
                double silverPrice = Double.parseDouble(data[7]);
                double bronzePrice = Double.parseDouble(data[8]);
                double generalAdmissionPrice = Double.parseDouble(data[9]);
                
                if (type.equalsIgnoreCase("Concert")) {
                    Concert concert = new Concert(id, type, name, date, time, vipPrice, goldPrice, silverPrice, bronzePrice, generalAdmissionPrice);
                    eventList.add(concert);  
                }
                if (type.equalsIgnoreCase("Special")) {
                	Special special = new Special(id, type, name, date, time, vipPrice, goldPrice, silverPrice, bronzePrice, generalAdmissionPrice);
                    eventList.add(special); 
                }
                if (type.equalsIgnoreCase("Sport")) {
                    Sport sport = new Sport(id, type, name, date, time, vipPrice, goldPrice, silverPrice, bronzePrice, generalAdmissionPrice);
                    eventList.add(sport); 
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading venue data: " + e.getMessage());
        }
    }
}
