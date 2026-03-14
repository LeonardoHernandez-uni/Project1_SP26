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
    /**
     * venueList stores all the venue in the system as Venue objects within an
     * ArrayList. It is what we load/save data to, and its what we access to modify
     * venue entries.
     */
    private static ArrayList<Venue> venueList = new ArrayList<>();

    /** Returns venueList. Is used for accessing venueList from other classes. */
    public static ArrayList<Venue> getVenueList() {
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
    public static void loadData() {
        String venueFile = "Venue_List_PA1.csv";
        String line;
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(venueFile))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                int id = Integer.parseInt(data[0]);
                String name = data[1];
                String type = data[2];

                int capacity = Integer.parseInt(data[3]);
                int concertCapacity = Integer.parseInt(data[4]);
                double cost = Double.parseDouble(data[5]);

                double vipPercent = Double.parseDouble(data[6]);
                int goldPercent = Integer.parseInt(data[7]);
                int silverPercent = Integer.parseInt(data[8]);
                int bronzePercent = Integer.parseInt(data[9]);
                int generalAdmissionPercent = Integer.parseInt(data[10]);
                int reservedExtraPercent = Integer.parseInt(data[11]);

                if (type.equalsIgnoreCase("Arena")) {
                    venueList.add(new Arena(id, name, type, capacity,concertCapacity, cost, vipPercent,
                            goldPercent, silverPercent, bronzePercent,
                            generalAdmissionPercent, reservedExtraPercent));
                } else if (type.equalsIgnoreCase("Auditorium")) {
                    venueList.add(new Auditorium(id, name, type, capacity,  concertCapacity, cost, vipPercent,
                            goldPercent, silverPercent, bronzePercent,
                            generalAdmissionPercent, reservedExtraPercent));
                } else if (type.equalsIgnoreCase("Open Air")) {
                    venueList.add(new OpenAir(id, name, type, capacity, concertCapacity, cost, vipPercent,
                            goldPercent, silverPercent, bronzePercent,
                            generalAdmissionPercent, reservedExtraPercent));
                } else if (type.equalsIgnoreCase("Stadium")) {
                    venueList.add(new Stadium(id, name, type, capacity, concertCapacity, cost, vipPercent,
                            goldPercent, silverPercent, bronzePercent,
                            generalAdmissionPercent, reservedExtraPercent));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading venue data: " + e.getMessage());
        }
    }

    public static void saveData() {

        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy_MM_dd");

        try (FileWriter file = new FileWriter(LocalDate.now().format(pattern) + "_Venue_List_PA1.csv")) {

            file.write(
                    "ID,Name,Type,Capacity,Concert Capacity,Cost,VIP Percent,Gold Percent,Silver Percent,Bronze Percent,General Admission Percent,Reserved Extra Percent\n");

            for (Venue venue : venueList) {
                //Word wrapping is weird lowk
                file.write(
                        venue.getId() + "," +
                                venue.getName() + "," +
                                venue.getType() + "," +
                                venue.getCapacity() + "," +
                                venue.getConcertCapacity() + "," + 
                                venue.getCost() + "," +
                                venue.getVipPercent() + "," +
                                venue.getGoldPercent() + "," +
                                venue.getSilverPercent() + "," +
                                venue.getBronzePercent() + "," +
                                venue.getGeneralAdmissionPercent() + "," +
                                venue.getReservedExtraPercent() + "\n");
            }

        } catch (IOException e) {
            System.err.println("Error saving venue data: " + e.getMessage());
        }
    }
}
