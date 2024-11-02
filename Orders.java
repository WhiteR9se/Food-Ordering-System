import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Orders {
    private static List<Order> pastOrders = new ArrayList<>();
    private static int orderCounter = 1;

    public static class Order {
        private String orderId;
        private String customerId;
        private List<Item> items;
        private String status;

        public Order(String orderId, String customerId, List<Item> items, String status) {
            this.orderId = orderId;
            this.customerId = customerId;
            this.items = items;
            this.status = status;
        }

        public String getOrderId() {
            return orderId;
        }

        public String getCustomerId() {
            return customerId;
        }

        public List<Item> getItems() {
            return items;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
    // customer side order methods
    public static void addOrder(String customerId, List<Item> items) {
        String orderId = "O" + orderCounter++;
        pastOrders.add(new Order(orderId, customerId, items, "Processing"));
    }
    public static void viewOrders(String customerId) {
        System.out.println("Your orders:");
        for (Order order : pastOrders) {
            if (order.getCustomerId().equals(customerId)) {
                System.out.println("Order ID: " + order.getOrderId());
                System.out.println("Items:");
                for (Item item : order.getItems()) {
                    System.out.println(item.getName());
                }
                System.out.println("Status: " + order.getStatus());
                System.out.println();
            }
        }
    }
    public static void cancelOrder(String orderId) {
        for (Order order : pastOrders) {
            if (order.getOrderId().equals(orderId)) {
                if (order.getStatus().equals("Processing")) {
                    order.setStatus("Cancelled");
                    System.out.println("Order cancelled successfully.");
                    return;
                } else {
                    System.out.println("Cannot cancel order. Either preparing or out for delivery.");
                    return;
                }
            }
        }
        System.out.println("Order not found.");
    }

    public static List<Order> getPastOrders() {
        return pastOrders;
    }
    public static void reorder(Scanner scanner, String customerId) {
        System.out.println("Select an order to reorder:");
        for (Order order : pastOrders) {
            if (order.getCustomerId().equals(customerId) && order.getStatus().equals("Delivered")) {
                System.out.println("Order ID: " + order.getOrderId());
                System.out.println("Items:");
                for (Item item : order.getItems()) {
                    System.out.println(item.getName());
                }
                System.out.println("Status: " + order.getStatus());
                System.out.println();
            }
        }

        System.out.print("Enter order ID:\n>> ");
        String orderId = scanner.nextLine();

        for (Order order : pastOrders) {
            if (order.getOrderId().equals(orderId)) {
                List<Item> items = order.getItems();
                for (Item item : items) {
                    Item.addItemToCart(item, customerId);
                }
                System.out.println("Items added to cart.");
                return;
            }
        }
        System.out.println("Order not found.");
    }

    // admin side order methods
    public static void updateOrderStatus(String orderId, String status) {
        for (Order order : pastOrders) {
            if (order.getOrderId().equals(orderId)) {
                order.setStatus(status);
                System.out.println("Order status updated to: " + status);
                return;
            }
        }
        System.out.println("Order not found.");
    }
    public static void viewAllOrders() {
    System.out.println("All orders:");
    System.out.println("-----------------------------------");
    for (Order order : pastOrders) {
        System.out.println("Order ID: " + order.getOrderId());
        System.out.println("Customer ID: " + order.getCustomerId());
        System.out.println("Items:");
        for (Item item : order.getItems()) {
            System.out.println(item.getName());
        }
        System.out.println("Status: " + order.getStatus());
        System.out.println("-----------------------------------");
    }
}

    public static void updateOrderStatusMenu(Scanner scanner) {
        viewAllOrders();
        System.out.print("Enter order ID:\n>> ");
        String orderId = scanner.nextLine();

        System.out.println("Select new status:");
        System.out.println("1) Preparing");
        System.out.println("2) Out for delivery");
        System.out.println("3) Delivered");
        System.out.print(">> ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String status;
        switch (choice) {
            case 1:
                status = "Preparing";
                break;
            case 2:
                status = "Out for Delivery";
                break;
            case 3:
                status = "Delivered";
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        Orders.updateOrderStatus(orderId, status);
    }
}