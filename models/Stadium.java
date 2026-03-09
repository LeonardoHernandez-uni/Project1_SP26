package models;

public class Stadium extends Venue{
    public Stadium(int id, String name, String type, int capacity, double cost, int vipPercent, int goldPercent,
            int silverPercent, int bronzePercent, int generalAdmissionPercent, int reservedExtraPercent) {
                super(id, name, type, capacity, cost, vipPercent, goldPercent, silverPercent, bronzePercent, generalAdmissionPercent, reservedExtraPercent);
            }
}
