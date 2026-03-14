package logic;

import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import models.*;

public class UserManager {
	
    /** userList stores all the users in the system as USer objects within an ArrayList. It is what we load/save data to, and its what we access to modify user entries. */
	private static ArrayList<User> userList = new ArrayList<>();

	/** Returns userList. Is used for accessing userList from other classes.*/
	public static ArrayList<User> getUserList() {
		return userList;
	}

	/** Loads data from the customer file (in this case Customer_List_PA1.csv) and parses its entries row by row into userList as User objects, assigning parameters to them column by column.
	 *  The method will use the 6th column of the csv, the userType column, to determine whether the User will be a Customer, an Organizer, or an Admin.
	 */
	public static void loadData() {
		String customerFile = "Customer_List_PA1.csv";
		String line;
		String csvSplitBy = ",";

		try (BufferedReader br = new BufferedReader(new FileReader(customerFile))) {
			br.readLine();
			while ((line = br.readLine()) != null) {
				String[] data = line.split(csvSplitBy);
				int id = Integer.parseInt(data[0]);
				String firstName = data[1];
				String lastName = data[2];
				String username = data[3];
				String password = data[4];
				String userType = data[5];

				if (userType.equalsIgnoreCase("Customer")) {
					double money = Double.parseDouble(data[6]);
					boolean membership = Boolean.parseBoolean(data[7]);
					userList.add(new Customer(firstName, lastName, username, password, id, money, membership,
							new ArrayList<>()));
				}
				if (userType.equalsIgnoreCase("Organizer")) {
					Organizer organizer = new Organizer(firstName, lastName, username, password, id);
					UserManager.getUserList().add(organizer);
				}
				if (userType.equalsIgnoreCase("Admin")) {
					Admin admin = new Admin(firstName, lastName, username, password, id);
					UserManager.getUserList().add(admin);
				}
			}
		} catch (IOException e) {
			System.err.println("Error loading user data: " + e.getMessage());
		}
	}

	public static void saveData() {
		DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy_MM_dd");
		try (FileWriter file = new FileWriter(LocalDate.now().format(pattern) + "_Customer_List_PA1.csv")) {
			// Following line writes very first row (which contains labels for the chart)
			file.write("ID,First Name,Last Name,Username,Password,User Type,Money Available,TicketMiner Membership,Concerts Purchased\n");
			for (User user: userList) {
				file.write(user.getUserID() + "," + user.getFirstName() + "," + user.getLastName() + "," + user.getUsername() + "," + user.getPassword() + "," + user.getUserType() + ",");
				if (user instanceof Customer customer) {
					file.write(String.valueOf(customer.getMoneyAvailable()) + "," + customer.getHasMembership() + "," + customer.getAmountOfTicketsPurchased());
				}
				file.write("\n");
			}
		} catch (IOException e) {
			System.err.println("Error saving user data: " + e.getMessage());
		}
	}

	/** generateID randomly generates a new 4 digit ID. After a new ID is generated, it is compared to all the IDs in userList using the isIDUnique() method. If the ID isn't unique, a new ID will be generated until
	 * the isIDUnique() method returns true.
	 * @return The randomly generated unique ID.
	*/
	public static int generateID() {
		int[] randomArray = new int[4];
		int assembledID = 0;
		do {
			for (int i = 0; i < 4; i++) {
				randomArray[i] = (int) (Math.random() * 10);
				assembledID = 10 * assembledID + randomArray[i];
			}
		} while (!isIDUnique(assembledID));
		return assembledID;
	}

	/** isIDUnique compares the given int to all userIDs in userList to check for uniqueness. If it returns true, the given int is unique. If it returns false, the given int is not unique.
	 * @param id The int we are checking against all userID ints in userList.
	 * @return Will either be true or false
	*/
	public static boolean isIDUnique(int id) {
		boolean isUnique = true;
		for (User user : userList) {
			if (user.getUserID() == id) {
				isUnique = false;
			}
		}
		return isUnique;
	}
}