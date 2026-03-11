package logic;

import java.io.*;
import java.util.*;
import models.*;

public class UserManager {
	private static ArrayList<User> userList = new ArrayList<>();

	public static ArrayList<User> getUserList() {
		return userList;
	}

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