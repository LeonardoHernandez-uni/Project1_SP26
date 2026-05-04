package models;
import java.time.*;
import Interface.Exportable;
public abstract class Event implements Exportable{
	private int id;
	private String eventType;
	private String name;
	private LocalDate date;
	private LocalTime time;
	private double vipPrice;
	private double goldPrice;
	private double silverPrice;
	private double bronzePrice;
	private double generalAdmissionPrice;
	
	@Override
    public String toCSVString() {
        return id + "," + eventType + "," + name + "," + date + "," + time + "," + 
               vipPrice + "," + goldPrice + "," + silverPrice + "," + 
               bronzePrice + "," + generalAdmissionPrice;
    }
	
	/** Our representation of an event object
	 * @param id
	 * @param eventType
	 * @param name
	 * @param date
	 * @param time
	 * @param vipPrice
	 * @param goldPrice
	 * @param silverPrice
	 * @param bronzePrice
	 * @param generalAdmissionPrice
	 */
	public Event(int id, String eventType, String name, LocalDate date, LocalTime time, double vipPrice,
			double goldPrice, double silverPrice, double bronzePrice, double generalAdmissionPrice) {
		super();
		this.id = id;
		this.eventType = eventType;
		this.name = name;
		this.date = date;
		this.time = time;
		this.vipPrice = vipPrice;
		this.goldPrice = goldPrice;
		this.silverPrice = silverPrice;
		this.bronzePrice = bronzePrice;
		this.generalAdmissionPrice = generalAdmissionPrice;
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
	 * @return the eventType
	 */
	public String getEventType() {
		return eventType;
	}
	/**
	 * @param eventType the eventType to set
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
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
	 * @return the date
	 */
	public LocalDate getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}
	/**
	 * @return the time
	 */
	public LocalTime getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(LocalTime time) {
		this.time = time;
	}
	/**
	 * @return the vipPrice
	 */
	public double getVipPrice() {
		return vipPrice;
	}
	/**
	 * @param vipPrice the vipPrice to set
	 */
	public void setVipPrice(double vipPrice) {
		this.vipPrice = vipPrice;
	}
	/**
	 * @return the goldPrice
	 */
	public double getGoldPrice() {
		return goldPrice;
	}
	/**
	 * @param goldPrice the goldPrice to set
	 */
	public void setGoldPrice(double goldPrice) {
		this.goldPrice = goldPrice;
	}
	/**
	 * @return the silverPrice
	 */
	public double getSilverPrice() {
		return silverPrice;
	}
	/**
	 * @param silverPrice the silverPrice to set
	 */
	public void setSilverPrice(double silverPrice) {
		this.silverPrice = silverPrice;
	}
	/**
	 * @return the bronzePrice
	 */
	public double getBronzePrice() {
		return bronzePrice;
	}
	/**
	 * @param bronzePrice the bronzePrice to set
	 */
	public void setBronzePrice(double bronzePrice) {
		this.bronzePrice = bronzePrice;
	}
	/**
	 * @return the generalAdmissionPrice
	 */
	public double getGeneralAdmissionPrice() {
		return generalAdmissionPrice;
	}
	/**
	 * @param generalAdmissionPrice the generalAdmissionPrice to set
	 */
	public void setGeneralAdmissionPrice(double generalAdmissionPrice) {
		this.generalAdmissionPrice = generalAdmissionPrice;
	}
}
