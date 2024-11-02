import java.util.Scanner;

public class Customer {
    private String email;
    private String customerId;
    private Cart cart;


    public Customer(String email, String customerId) {
        this.email = email;
        this.customerId = customerId;
        this.cart = new Cart();
    }

    public static Customer getCustomerById(String customerId) {
        for (Customer customer : Admin.customers) {
            if (customer.getCustomerId().equals(customerId)) {
                return customer;
            }
        }
        return null;
    }


    public String getEmail() {
        return email;
    }

    public String getCustomerId() {
        return customerId;
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
            System.out.println("4) View Orders");
            System.out.println("5) Cancel Order");
            System.out.println("6) Reorder");
            System.out.println("7) Search Item");
            System.out.println("8) Filter by Category");
            System.out.println("9) Sort Items by Price");
            System.out.println("10) Logout");
            System.out.print(">> ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        customer.getCart().addToCartMenu(scanner);
                        break;
                    case 2:
                        customer.getCart().viewCart();
                        break;
                    case 3:
                        customer.getCart().checkout(scanner, customer.getCustomerId());
                        break;
                    case 4:
                        Orders.viewOrders(customer.getCustomerId());
                        break;
                    case 5:
                        System.out.print("Enter order ID to cancel:\n>> ");
                        String orderId = scanner.nextLine();
                        Orders.cancelOrder(orderId);
                        break;
                    case 6:
                        Orders.reorder(scanner, customer.getCustomerId());
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
                        exit = true;
                        System.out.println("Logging out.");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } else {
                System.out.println("Invalid choice. Please try again.");
                scanner.next(); // Consume invalid input
            }
        }
    }
}