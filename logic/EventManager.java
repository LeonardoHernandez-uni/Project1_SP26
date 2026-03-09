package logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import models.Event;
import models.Concert;
import models.Special;
import models.Sport;

public class EventManager {
	private static ArrayList<Event> eventList = new ArrayList<>();

    public static ArrayList<Event> getEventList() {
        return eventList;
    }

	public static void loadData() {
        String customerFile = "Event_List_PA1.csv";
        String line;
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(customerFile))) {
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
	public void createEvent() {
		
	}

    public boolean cancelEvent() {
    	return false;
    }

    public void changeDate() {
    	
    }

    public void changeName() {
    	
    }
}
