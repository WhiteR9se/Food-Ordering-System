import java.util.Scanner;

public class Admin extends User {
    public static void adminMenu(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("Admin Menu:");
            System.out.println("1) Add New Item");
            System.out.println("2) View All Items");
            System.out.println("3) Update Existing Item");
            System.out.println("4) Remove Item");
            System.out.println("5) Exit");
            System.out.print(">> ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        Item.addItem(scanner);
                        break;
                    case 2:
                        Item.viewItems();
                        break;
                    case 3:
                        Item.updateItem(scanner);
                        break;
                    case 4:
                        Item.removeItem(scanner);
                        break;
                    case 5:
                        exit = true;
                        System.out.println("Exiting Admin Menu.");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
            else {
                System.out.println("Invalid choice. Please try again.");
                scanner.next();
            }
        }
    }

    public  void hello(){
        System.out.println("Welcome Admin!!");
    }
}