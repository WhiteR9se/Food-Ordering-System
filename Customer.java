import java.util.Scanner;

public class Customer extends User {
    public static void customerMenu(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("Customer Menu:");
            System.out.println("1) View All Items");
            System.out.println("2) View Item");
            System.out.println("3) Add to Cart");
            System.out.println("4) Remove from Cart");
            System.out.println("5) Checkout");
            System.out.println("6) Exit");
            System.out.print(">> ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        Item.viewItems();
                        break;
                    case 2:
                        Item.searchItem(scanner);
                        break;
                    case 3:
                        //Item.addToCart(scanner);
                        break;
                    case 4:
                        //Item.removeFromCart(scanner);
                        break;
                    case 5:
                        //Item.checkout();
                        break;
                    case 6:
                        exit = true;
                        System.out.println("Exiting Customer Menu.");
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
        System.out.println("Welcome Customer!!");
    }
}
