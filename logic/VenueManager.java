/**
 * Manages the list of venues
 * Handles loading venue data from CSV files and provides access to the venue list
 */
package logic;

import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import models.*;

public class VenueManager {

    /** The single, private static instance of the class */
    private static VenueManager instance;

    /** venueList stores all the venues in the system. */
    private ArrayList<Venue> venueList;

    /** * Private constructor to prevent instantiation from outside the class.
     * Initializes the venueList.
     */
    private VenueManager() {
        venueList = new ArrayList<>();
    }

    /**
     * Retrieves the single, shared instance of the VenueManager.
     * @return The Singleton instance of VenueManager.
     */
    public static VenueManager getInstance() {
        if (instance == null) {
            instance = new VenueManager();
        }
        return instance;
    }

    /** * Returns the list of venues.
     * @return The ArrayList containing all venues.
     */
    public ArrayList<Venue> getVenueList() {
        return venueList;
    }
    /**
     * Loads data from the venue file (in this case Venue_List_PA1.csv) and parses
     * its entries row by row into venueList as Venue objects, assigning parameters
     * to them column by column.
     * The method will use the 3rd column of the csv, the venueList column, to
     * determine whether the Venue will be an Arena, an Auditorium, OpenAir, or a
     * Stadium.
     */
    public void loadData() {
        String venueFile = "Venue_List_PA2.csv";
        String line;
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(venueFile))) {
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
                String name = data[colMap.get("name")];
                String type = data[colMap.get("type")];
                int capacity = Integer.parseInt(data[colMap.get("capacity")]);
                int concertCapacity = Integer.parseInt(data[colMap.get("concert capacity")]);
                double cost = Double.parseDouble(data[colMap.get("cost")]);
                double vipPercent = Double.parseDouble(data[colMap.get("vip percent")]);
                int goldPercent = Integer.parseInt(data[colMap.get("gold percent")]);
                int silverPercent = Integer.parseInt(data[colMap.get("silver percent")]);
                int bronzePercent = Integer.parseInt(data[colMap.get("bronze percent")]);
                int generalAdmissionPercent = Integer.parseInt(data[colMap.get("general admission percent")]);
                int reservedExtraPercent = Integer.parseInt(data[colMap.get("reserved extra percent")]);

                if (type.equalsIgnoreCase("Arena")) {
                    venueList.add(new Arena(id, name, type, capacity, concertCapacity, cost, vipPercent, goldPercent, silverPercent, bronzePercent, generalAdmissionPercent, reservedExtraPercent));
                } else if (type.equalsIgnoreCase("Auditorium")) {
                    venueList.add(new Auditorium(id, name, type, capacity, concertCapacity, cost, vipPercent, goldPercent, silverPercent, bronzePercent, generalAdmissionPercent, reservedExtraPercent));
                } else if (type.equalsIgnoreCase("Open Air") || type.equalsIgnoreCase("OpenAir")) {
                    venueList.add(new OpenAir(id, name, type, capacity, concertCapacity, cost, vipPercent, goldPercent, silverPercent, bronzePercent, generalAdmissionPercent, reservedExtraPercent));
                } else if (type.equalsIgnoreCase("Stadium")) {
                    venueList.add(new Stadium(id, name, type, capacity, concertCapacity, cost, vipPercent, goldPercent, silverPercent, bronzePercent, generalAdmissionPercent, reservedExtraPercent));
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading venue data: " + e.getMessage());
        }
    }

    public void saveData() {

        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy_MM_dd");

        try (FileWriter file = new FileWriter(LocalDate.now().format(pattern) + "_Venue_List_PA1.csv")) {
            file.write("ID,Name,Type,Capacity,Concert Capacity,Cost,VIP Percent,Gold Percent,Silver Percent,Bronze Percent,General Admission Percent,Reserved Extra Percent\n");

            //loook clean now
            for (Venue venue : venueList) {
                file.write(venue.toCSVString() + "\n");
            }

        } catch (IOException e) {
            System.err.println("Error saving venue data: " + e.getMessage());
        }
    }

    //refactored admin stuff
    public void displayAllVenues() {
        System.out.println("\n--- All Venues ---");
        System.out.printf("%-5s | %-25s | %-12s | %-8s | %-10s | %-15s | %-4s | %-5s | %-7s | %-7s | %-7s | %-7s%n",
            "ID", "Name", "Type", "Capacity", "ConcertCap", "Cost", "VIP%", "Gold%", "Silver%", "Bronze%", "GenAdm%", "ResExt%");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------");
        
        for (Venue v : venueList) {
            System.out.printf("%-5d | %-25s | %-12s | %-8d | %-10d | $%-12.2f | %-4.0f | %-5d | %-7d | %-7d | %-7d | %-7d%n",
                v.getId(), 
                v.getName(), 
                v.getType(), 
                v.getCapacity(), 
                v.getConcertCapacity(), 
                v.getCost(), 
                v.getVipPercent(), 
                v.getGoldPercent(), 
                v.getSilverPercent(), 
                v.getBronzePercent(), 
                v.getGeneralAdmissionPercent(), 
                v.getReservedExtraPercent());
        }
    }

    /**
     * Creates a new Venue and adds it to the system. The ID is automatically generated.
     * * @param type The type of venue ("Arena", "Auditorium", "OpenAir", or "Stadium").
     * @param name The name of the venue.
     * @param capacity The maximum capacity of the venue.
     * @param concertCapacity The specific capacity for concert events.
     * @param cost The base cost to rent or use the venue.
     * @param vipPercent The percentage of total capacity allocated to VIP seating.
     * @param goldPercent The percentage allocated to Gold seating.
     * @param silverPercent The percentage allocated to Silver seating.
     * @param bronzePercent The percentage allocated to Bronze seating.
     * @param generalAdmissionPercent The percentage allocated to General Admission.
     * @param reservedExtraPercent The percentage allocated to Reserved Extra seating.
     */
    public void addVenue(String type, String name, int capacity, int concertCapacity, double cost, double vipPercent, int goldPercent, int silverPercent, int bronzePercent, int generalAdmissionPercent, int reservedExtraPercent) {
        int newID = venueList.stream().mapToInt(Venue::getId).max().orElse(0) + 1;
        switch (type) {
            case "Arena" -> venueList.add(new Arena(newID, name, type, capacity, concertCapacity, cost, vipPercent, goldPercent, silverPercent, bronzePercent, generalAdmissionPercent, reservedExtraPercent));
            case "Auditorium" -> venueList.add(new Auditorium(newID, name, type, capacity, concertCapacity, cost, vipPercent, goldPercent, silverPercent, bronzePercent, generalAdmissionPercent, reservedExtraPercent));
            case "OpenAir" -> venueList.add(new OpenAir(newID, name, type, capacity, concertCapacity, cost, vipPercent, goldPercent, silverPercent, bronzePercent, generalAdmissionPercent, reservedExtraPercent));
            case "Stadium" -> venueList.add(new Stadium(newID, name, type, capacity, concertCapacity, cost, vipPercent, goldPercent, silverPercent, bronzePercent, generalAdmissionPercent, reservedExtraPercent));
            default -> System.out.println("Error: Invalid venue type.");
        }
    }

    /**
     * Searches for a Venue by matching a query against its ID, Name, or Type.
     * * @param query The string to search for.
     * @return The matching Venue object, or null if no match is found.
     */
    public Venue searchVenue(String query) {
        for (Venue v : venueList) {
            if (String.valueOf(v.getId()).equals(query) || v.getName().equalsIgnoreCase(query) || v.getType().equalsIgnoreCase(query)) {
                return v;
            }
        }
        return null;
    }

    /**
     * Updates the name of a specific Venue.
     * * @param v The Venue to update.
     * @param newName The new name for the venue.
     */
    public static void updateVenueName(Venue v, String newName) { v.setName(newName); }

    /**
     * Updates the base cost of a specific Venue.
     * * @param v The Venue to update.
     * @param newCost The new base cost.
     */
    public void updateVenueCost(Venue v, double newCost) { v.setCost(newCost); }

    /**
     * Updates the maximum capacity of a specific Venue.
     * * @param v The Venue to update.
     * @param newCapacity The new maximum capacity.
     */
    public void updateVenueCapacity(Venue v, int newCapacity) { v.setCapacity(newCapacity); }

    /**
     * Removes a specific Venue from the system.
     * * @param v The Venue to delete.
     */
    public void deleteVenue(Venue v) { venueList.remove(v); }
}
