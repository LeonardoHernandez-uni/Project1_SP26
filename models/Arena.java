package models;
public class Arena extends Venue {
    public Arena(int id, String name, String type, int capacity, double cost, int vipPercent, int goldPercent, int silverPercent, int bronzePercent, int generalAdmissionPercent, int reservedExtraPercent) {
                super(id, name, type, capacity, cost, vipPercent, goldPercent, silverPercent, bronzePercent, generalAdmissionPercent, reservedExtraPercent);
            }
}
