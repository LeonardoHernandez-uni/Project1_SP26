package models;
public class OpenAir extends Venue {
    public OpenAir(int id, String name, String type, int capacity, int concertCapacity, double cost, double vipPercent, int goldPercent,
            int silverPercent, int bronzePercent, int generalAdmissionPercent, int reservedExtraPercent) {
                super(id, name, type, capacity, concertCapacity, cost, vipPercent, goldPercent, silverPercent, bronzePercent, generalAdmissionPercent, reservedExtraPercent);
            }
}
