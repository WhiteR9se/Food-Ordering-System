import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Orders {
    private static List<Order> pastOrders = new ArrayList<>();
    static int orderCounter = 1;

    static {
        initializeOrderCounter();
    }

    public static class Order {
        private String orderId;
        private String customerId;
        private static Map<Item, Integer> items;
        private String status;
        private String specialRequest;

        public Order(String orderId, String customerId, Map<Item, Integer> items, String status, String specialRequest) {
            this.orderId = orderId;
            this.customerId = customerId;
            this.items = items;
            this.status = status;
            this.specialRequest = specialRequest;
        }

        public String getOrderId() {
            return orderId;
        }

        public String getCustomerId() {
            return customerId;
        }

        public static Map<Item, Integer> getItems() {
            return items;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getSpecialRequest() {
            return specialRequest;
        }

        @Override
        public String toString() {
            return "Order ID: " + orderId + "\n" +
                    "Customer ID: " + customerId + "\n" +
                    "Items: " + items + "\n" +
                    "Status: " + status + "\n" +
                    "Special Request: " + specialRequest + "\n";
        }
    }

    public static void initializeOrderCounter() {
        try (Scanner fileScanner = new Scanner(new File("Orders.txt"))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.startsWith("Order ID: ")) {
                    String orderId = line.split(": ")[1];
                    int orderNumber = Integer.parseInt(orderId.substring(1));
                    if (orderNumber >= orderCounter) {
                        orderCounter = orderNumber + 1;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Orders file not found.");
            e.printStackTrace();
        }
    }

    public static void addOrder(String customerId, Map<Item, Integer> items, String specialRequest) {
        String orderId = "O" + orderCounter++;
        pastOrders.add(new Order(orderId, customerId, items, "Processing", specialRequest));
    }

    public static void viewOrders(String customerId) {
        System.out.println("Your orders:");
        try (Scanner fileScanner = new Scanner(new File("Orders.txt"))) {
            boolean orderFound = false;
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.contains("Customer ID: " + customerId)) {
                    orderFound = true;
                    if (line.contains("Order ID: ")) {
                        String orderId = line.split(": ")[1];
                        System.out.println("Order ID: " + orderId);
                    }

                    while (fileScanner.hasNextLine()) {
                        line = fileScanner.nextLine();
                        if (line.startsWith("Items:")) {
                            while (fileScanner.hasNextLine()) {
                                line = fileScanner.nextLine();
                                if (line.startsWith("Status:")) {
                                    break;
                                }
                                String[] itemParts = line.split(" - Quantity: ");
                                if (itemParts.length == 2) {
                                    String itemName = itemParts[0];
                                    int quantity = Integer.parseInt(itemParts[1]);
                                    System.out.println("Item: " + itemName + ", Quantity: " + quantity);
                                }
                            }
                        }
                        if (line.startsWith("-----------------------------------")) {
                            break;
                        }
                    }
                    System.out.println("-----------------");
                }
            }
            if (!orderFound) {
                System.out.println("No orders found for customer ID: " + customerId);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Orders file not found.");
            e.printStackTrace();
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

    public static void reorder(Scanner scanner, String customerId) {
        System.out.println("Select an order to reorder:");
        boolean hasDeliveredOrders = false;
        for (Order order : pastOrders) {
            if (order.getCustomerId().equals(customerId) && order.getStatus().equals("Delivered")) {
                System.out.println("Order ID: " + order.getOrderId());
                System.out.println("Items:");
                for (Map.Entry<Item, Integer> entry : order.getItems().entrySet()) {
                    System.out.println(entry.getKey().getName() + " - Quantity: " + entry.getValue());
                }
                System.out.println("Status: " + order.getStatus());
                System.out.println();
                hasDeliveredOrders = true;
            }
        }

        if (!hasDeliveredOrders) {
            System.out.println("No past orders.");
            return;
        }

        System.out.print("Enter order ID:\n>> ");
        String orderId = scanner.nextLine();

        for (Order order : pastOrders) {
            if (order.getOrderId().equals(orderId)) {
                Map<Item, Integer> items = order.getItems();
                for (Map.Entry<Item, Integer> entry : items.entrySet()) {
                    for (int i = 0; i < entry.getValue(); i++) {
                        Item.addItemToCart(entry.getKey(), customerId);
                    }
                }
                System.out.println("Items added to cart.");
                return;
            }
        }
        System.out.println("Order not found.");
    }

    public static void saveOrderToFile(Order order) {
        try (FileWriter writer = new FileWriter("Orders.txt", true)) {
            writer.write("Order ID: " + order.getOrderId() + "\n");
            writer.write("Customer ID: " + order.getCustomerId() + "\n");
            writer.write("Items: \n");
            for (Map.Entry<Item, Integer> entry : order.getItems().entrySet()) {
                writer.write(entry.getKey().getName() + " - Quantity: " + entry.getValue() + "\n");
            }
            writer.write("Status: " + order.getStatus() + "\n");
            writer.write("Special Request: " + order.getSpecialRequest() + "\n");
            writer.write("-----------------------------------\n");
        } catch (IOException e) {
            System.out.println("An error occurred while writing the order to the file.");
            e.printStackTrace();
        }
    }

    public static void viewAllOrders() {
        System.out.println("All orders:");
        System.out.println("-----------------------------------");
        for (Order order : pastOrders) {
            System.out.println("Order ID: " + order.getOrderId());
            System.out.println("Customer ID: " + order.getCustomerId());
            System.out.print("Items:\n");
            for (Map.Entry<Item, Integer> entry : order.getItems().entrySet()) {
                System.out.println(entry.getKey().getName() + " (Quantity: " + entry.getValue() + ")");
            }
            System.out.println("Status: " + order.getStatus());
            System.out.println("Special Request: " + (order.getSpecialRequest().isEmpty() ? "" : order.getSpecialRequest()));
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
}