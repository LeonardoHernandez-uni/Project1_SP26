/**
 * 
 */
package models;

import java.time.LocalDate;
import java.time.LocalTime;

/** Our representation of a Sports event
 * 
 */
public class Sport extends Event {

	/**
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
	public Sport(int id, String eventType, String name, LocalDate date, LocalTime time, double vipPrice,
			double goldPrice, double silverPrice, double bronzePrice, double generalAdmissionPrice) {
		super(id, eventType, name, date, time, vipPrice, goldPrice, silverPrice, bronzePrice, generalAdmissionPrice);
	}

}
