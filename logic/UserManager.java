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
        String line = "";
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
                    userList.add(new Customer(firstName, lastName, username, password, id, money, membership, new ArrayList<>()));
                } 
            }
        } catch (IOException e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
    }

    public static int generateNextID() {
        int maxID = 0;
        for (User u : userList) {
            if (u.getUserID() > maxID) {
                maxID = u.getUserID();
            }
        }
        return maxID + 1;
    }

    public static User validateLogin(String username, String password) {
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}