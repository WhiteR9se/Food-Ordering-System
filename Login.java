import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Login {
    static HashMap<String, String> logininfo = new HashMap<String, String>();

    Login() {
        logininfo.put("admin", "pass");
        logininfo.put("customer@gmail.com", "customer");
    }

    public void printLoginInfo() {
        for (HashMap.Entry<String, String> entry : logininfo.entrySet()) {
            System.out.println("Email: " + entry.getKey() + ", Password: " + entry.getValue());
        }
    }

    protected HashMap getLoginInfo() { //getter method
        return logininfo;
    }

    public static void addToTxt(String email, String password) {
        try (FileWriter writer = new FileWriter("loginInfo.txt", true)) {
            writer.write(email + "," + password + "\n");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }
}