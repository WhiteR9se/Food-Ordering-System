import java.util.Scanner;

public class User {
    public void userMenu(Scanner scanner) {
        Login login = new Login();
        boolean exit = false;

        while (!exit) {
            login.printLoginInfo();
            System.out.println("  /--------------------------------------------/");
            System.out.println(" / Khaane ki vyavastha me aapka swaagat hai!! /");
            System.out.println("/____________________________________________/");
            hello();
            System.out.println("Choose an option:");
            System.out.println("1) Admin");
            System.out.println("2) Customer");
            System.out.println("3) Exit");
            System.out.print(">> ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        handleAdmin(scanner, login);
                        break;
                    case 2:
                        handleCustomer(scanner, login);
                        break;
                    case 3:
                        exit = true;
                        System.out.println("Exiting the program.");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } else {
                System.out.println("Invalid choice. Please try again.");
                scanner.next();
            }
        }
        scanner.close();
    }

    public static void hello() {
        System.out.println("Welcome User!!");
    }

    private void handleAdmin(Scanner scanner, Login login) {
        System.out.print("Enter email:\n>> ");
        String email = scanner.nextLine();
        System.out.print("Enter password:\n>> ");
        String password = scanner.nextLine();
        if (login.getLoginInfo().containsKey(email) && login.getLoginInfo().get(email).equals(password)) {
            System.out.println("Admin login successful.");
            Admin.adminMenu(scanner);
        } else {
            System.out.println("Invalid email or password.");
        }
    }

    private void handleCustomer(Scanner scanner, Login login) {
        boolean validChoice = false;
        while (!validChoice) {
            System.out.println("1) Login");
            System.out.println("2) Register");
            System.out.println("3) Back to Main Menu");
            System.out.print(">> ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        validChoice = true;
                        System.out.print("Enter email:\n>> ");
                        String email = scanner.nextLine();
                        System.out.print("Enter password:\n>> ");
                        String password = scanner.nextLine();
                        if (login.getLoginInfo().containsKey(email) && login.getLoginInfo().get(email).equals(password)) {
                            System.out.println("Customer login successful.");
                            Customer.customerMenu(scanner, new Customer(email, password));
                        } else {
                            System.out.println("Invalid email or password.");
                        }
                        break;
                    case 2:
                        validChoice = true;
                        System.out.print("Enter email:\n>> ");
                        email = scanner.nextLine();
                        if (login.getLoginInfo().containsKey(email)) {
                            System.out.println("Email already registered. Please login.");
                            break;
                        }
                        System.out.print("Enter password:\n>> ");
                        password = scanner.nextLine();
                        String customerId = "C" + login.getNextCustomerId();
                        login.addLoginInfo(email, password, customerId);
                        System.out.println("Customer registered successfully.");
                        Customer.customerMenu(scanner, new Customer(email, customerId));
                        break;
                    case 3:
                        validChoice = true;
                        System.out.println("Returning to main menu.");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } else {
                System.out.println("Invalid choice. Please try again.");
                scanner.next();
            }
        }
    }
}