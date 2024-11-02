import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Login {
    static HashMap<String, String> logininfo = new HashMap<>();
    static HashMap<String, String> customerIds = new HashMap<>();
    private static int customerIdCounter = 1;
    private static boolean isCustomerIdsCleared = false;

    Login() {
        logininfo.put("admin", "pass");
        logininfo.put("customer@gmail.com", "customer");
        customerIds.put("customer@gmail.com", "C" + customerIdCounter++);
    }

    static {
        loadLoginInfo();
    }

    public void printLoginInfo() {
        for (Map.Entry<String, String> entry : logininfo.entrySet()) {
            System.out.println("Email: " + entry.getKey() + ", Password: " + entry.getValue());
        }
    }

    public Map<String, String> getLoginInfo() {
        return logininfo;
    }

    public String getCustomerId(String email) {
        return customerIds.get(email);
    }

    public int getNextCustomerId() {
        return customerIdCounter++;
    }

    public void addLoginInfo(String email, String password, String customerId) {
        logininfo.put(email, password);
        customerIds.put(email, customerId);
        addToTxt(email, password, customerId);
        saveCustomerIdsToFile();
    }

    public static Map<String, String> getCustomerIds() {
        return customerIds;
    }

    private static void loadLoginInfo() {
        try (Scanner scanner = new Scanner(new File("loginInfo.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    logininfo.put(parts[0], parts[1]);
                    customerIds.put(parts[0], parts[2]);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }
    }

    public static void addToTxt(String email, String password, String customerId) {
        try (FileWriter writer = new FileWriter("loginInfo.txt", true)) {
            writer.write(email + "," + password + "," + customerId + "\n");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    public static void saveCustomerIdsToFile() {
        try (FileWriter writer = new FileWriter("customerId.txt")) {
            for (Map.Entry<String, String> entry : customerIds.entrySet()) {
                writer.write("Customer email: " + entry.getKey() + ", Customer ID: " + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    public static void clearCustomerIdsOnce() {
        if (!isCustomerIdsCleared) {
            customerIds.clear();
            isCustomerIdsCleared = true;
        }
    }
}