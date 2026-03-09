package models;

/** Our abstract representation of a Venue */
public abstract class Venue {
    private int id;
    private String name;
    private String type;
    private int capacity;
    private double cost;
    private double vipPercent;
    private int goldPercent;
    private int silverPercent;
    private int bronzePercent;
    private int generalAdmissionPercent;
    private int reservedExtraPercent;
    
    
    public Venue(int id, String name, String type, int capacity, double cost, double vipPercent, int goldPercent, int silverPercent, int bronzePercent, int generalAdmissionPercent, int reservedExtraPercent) {
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


	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}


	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}


	/**
	 * @return the capacity
	 */
	public int getCapacity() {
		return capacity;
	}


	/**
	 * @param capacity the capacity to set
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}


	/**
	 * @return the cost
	 */
	public double getCost() {
		return cost;
	}


	/**
	 * @param cost the cost to set
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}


	/**
	 * @return the vipPercent
	 */
	public double getVipPercent() {
		return vipPercent;
	}


	/**
	 * @param vipPercent the vipPercent to set
	 */
	public void setVipPercent(int vipPercent) {
		this.vipPercent = vipPercent;
	}


	/**
	 * @return the goldPercent
	 */
	public int getGoldPercent() {
		return goldPercent;
	}


	/**
	 * @param goldPercent the goldPercent to set
	 */
	public void setGoldPercent(int goldPercent) {
		this.goldPercent = goldPercent;
	}


	/**
	 * @return the silverPercent
	 */
	public int getSilverPercent() {
		return silverPercent;
	}


	/**
	 * @param silverPercent the silverPercent to set
	 */
	public void setSilverPercent(int silverPercent) {
		this.silverPercent = silverPercent;
	}


	/**
	 * @return the bronzePercent
	 */
	public int getBronzePercent() {
		return bronzePercent;
	}


	/**
	 * @param bronzePercent the bronzePercent to set
	 */
	public void setBronzePercent(int bronzePercent) {
		this.bronzePercent = bronzePercent;
	}


	/**
	 * @return the generalAdmissionPercent
	 */
	public int getGeneralAdmissionPercent() {
		return generalAdmissionPercent;
	}


	/**
	 * @param generalAdmissionPercent the generalAdmissionPercent to set
	 */
	public void setGeneralAdmissionPercent(int generalAdmissionPercent) {
		this.generalAdmissionPercent = generalAdmissionPercent;
	}


	/**
	 * @return the reservedExtraPercent
	 */
	public int getReservedExtraPercent() {
		return reservedExtraPercent;
	}


	/**
	 * @param reservedExtraPercent the reservedExtraPercent to set
	 */
	public void setReservedExtraPercent(int reservedExtraPercent) {
		this.reservedExtraPercent = reservedExtraPercent;
	}
	

    
}
