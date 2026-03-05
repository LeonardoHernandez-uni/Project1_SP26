public abstract class Venue {
    private String name;
    private int capacity;
    private double cost;
    private int venueID;
    private String address;

    public abstract boolean checkAvailability();

    public abstract double getCost();

    public abstract void changeCost();

    public abstract void changeCapacity();
}
