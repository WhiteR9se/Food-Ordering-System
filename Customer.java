import java.util.List;
import java.util.Scanner;

public class Customer {
    private  String email;
    private String customerId;
    private Cart cart;
    public static Customer[] customers;


    public Customer(String email, String customerId) {
        this.email = email;
        this.customerId = customerId;
        this.cart = new Cart();
    }
    public Customer(){}

    public static void initializeCustomers(List<Customer> existingCustomers) {
        customers = new Customer[existingCustomers.size()];
        for (int i = 0; i < existingCustomers.size(); i++) {
            customers[i] = existingCustomers.get(i);
        }
    }
    public static void printCustomers() {
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }
    public static Customer getCustomerById(String customerId) {
        for (Customer customer : customers) {
            if (Login.getCustomerId(customer.email).equals(customerId)) {
                return customer;
            }
        }
        return null;
    }
    @Override
    public String toString() {
        return
                "email='" + email + '\'' +
                ", customerId='" + customerId + '\'';
    }

    public String getEmail() {
        return email;
    }


    public Cart getCart() {
        return cart;
    }

    public static void customerMenu(Scanner scanner, Customer customer) {
    boolean exit = false;

    while (!exit) {
        System.out.println("Customer Menu:");
        System.out.println("1) Add to Cart");
        System.out.println("2) View Cart");
        System.out.println("3) Checkout");
        System.out.println("4) Order History");
        System.out.println("5) Cancel Order");
        System.out.println("6) Reorder");
        System.out.println("7) Search Item");
        System.out.println("8) Filter by Category");
        System.out.println("9) Sort Items by Price");
        System.out.println("10) Update Quantity");
        System.out.println("11) Logout");
        System.out.print(">> ");

        if (scanner.hasNextInt()) {
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    customer.getCart().addToCartMenu(scanner);
                    break;
                case 2:
                    customer.getCart().viewCart();
                    break;
                case 3:
                    customer.getCart().checkout(scanner, Login.getCustomerId(customer.email));
                    break;
                case 4:
                    Orders.viewOrders(Login.getCustomerId(customer.email));
                    break;
                case 5:
                    System.out.print("Enter order ID to cancel:\n>> ");
                    String orderId = scanner.nextLine();
                    Orders.cancelOrder(orderId);
                    break;
                case 6:
                    Orders.reorder(scanner, Login.getCustomerId(customer.email));
                    break;
                case 7:
                    Item.searchItem(scanner);
                    break;
                case 8:
                    Item.filterByCategory(scanner);
                    break;
                case 9:
                    Item.sortItemsByPrice(scanner);
                    break;
                case 10:
                    Item.updateQuantity(scanner);
                    break;
                case 11:
                    exit = true;
                    System.out.println("Logging out.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        else {
            System.out.println("Invalid choice. Please try again.");
            scanner.next(); // Consume invalid input
            }
        }
    }
}