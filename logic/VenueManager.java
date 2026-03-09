package logic;

import java.io.*;
import java.util.*;
import models.*;

public class VenueManager {
    private static ArrayList<Venue> venueList = new ArrayList<>();

    public static ArrayList<Venue> getUserList() {
        return venueList;
    }

    public static void loadData() {
        String customerFile = "Venue_List_PA1.csv";
        String line = "";
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(customerFile))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                int id = Integer.parseInt(data[0]);
                String name = data[1];
                String type = data[2];
                int capacity = Integer.parseInt(data[3]);
                int concertCapacity = Integer.parseInt(data[4]);
                double cost = Double.parseDouble(data[5]);
                int vipPercent = Integer.parseInt(data[6]);
                int goldPercent= Integer.parseInt(data[7]);
                int silverPercent = Integer.parseInt(data[8]);
                int bronzePercent = Integer.parseInt(data[9]);
                int generalAdmissionPercent = Integer.parseInt(data[10]);
                int reservedExtraPercent = Integer.parseInt(data[11]);

                if (type.equalsIgnoreCase("Arena")) {
                    Arena arena = new Arena(id, name, type, concertCapacity, cost, vipPercent, goldPercent, silverPercent, bronzePercent, generalAdmissionPercent, reservedExtraPercent);
                    venueList.add(arena);  
                }
                if (type.equalsIgnoreCase("Auditorium")) {
                    Auditorium auditorium = new Auditorium(id, name, type, concertCapacity, cost, vipPercent, goldPercent, silverPercent, bronzePercent, generalAdmissionPercent, reservedExtraPercent);
                    venueList.add(auditorium); 
                }
                if (type.equalsIgnoreCase("OpenAir")) {
                    OpenAir openAir = new OpenAir(id, name, type, concertCapacity, cost, vipPercent, goldPercent, silverPercent, bronzePercent, generalAdmissionPercent, reservedExtraPercent);
                    venueList.add(openAir); 
                }
                if (type.equalsIgnoreCase("Stadium")) {
                    Stadium stadium = new Stadium(id, name, type, concertCapacity, cost, vipPercent, goldPercent, silverPercent, bronzePercent, generalAdmissionPercent, reservedExtraPercent);
                    venueList.add(stadium); 
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading venue data: " + e.getMessage());
        }
    }
}
