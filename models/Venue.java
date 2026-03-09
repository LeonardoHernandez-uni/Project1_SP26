package models;

/** Our abstract representation of a venue */
public abstract class Venue {
    private int id;
    private String name;
    private String type;
    private int capacity;
    private double cost;
    private int vipPercent;
    private int goldPercent;
    private int silverPercent;
    private int bronzePercent;
    private int generalAdmissionPercent;
    private int reservedExtraPercent;

    public Venue(int id, String name, String type, int capacity, double cost, int vipPercent, int goldPercent, int silverPercent, int bronzePercent, int generalAdmissionPercent, int reservedExtraPercent) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.cost = cost;
        this.vipPercent = vipPercent;
        this.goldPercent = goldPercent;
        this.silverPercent = silverPercent;
        this.bronzePercent = bronzePercent;
        this.generalAdmissionPercent = generalAdmissionPercent;
        this.reservedExtraPercent = reservedExtraPercent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getVipPercent() {
        return vipPercent;
    }

    public void setVipPercent(int vipPercent) {
        this.vipPercent = vipPercent;
    }

    public int getGoldPercent() {
        return goldPercent;
    }

    public void setGoldPercent(int goldPercent) {
        this.goldPercent = goldPercent;
    }

    public int getSilverPercent() {
        return silverPercent;
    }

    public void setSilverPercent(int silverPercent) {
        this.silverPercent = silverPercent;
    }

    public int getBronzePercent() {
        return bronzePercent;
    }

    public void setBronzePercent(int bronzePercent) {
        this.bronzePercent = bronzePercent;
    }

    public int getGeneralAdmissionPercent() {
        return generalAdmissionPercent;
    }

    public void setGeneralAdmissionPercent(int generalAdmissionPercent) {
        this.generalAdmissionPercent = generalAdmissionPercent;
    }

    public int getReservedExtraPercent() {
        return reservedExtraPercent;
    }

    public void setReservedExtraPercent(int reservedExtraPercent) {
        this.reservedExtraPercent = reservedExtraPercent;
    }
}
