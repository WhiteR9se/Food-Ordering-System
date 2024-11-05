import java.util.*;

public class Cart {
    private Map<Item, Integer> items;

    public Cart() {
        items = new HashMap<>();
    }
    public Map<Item, Integer> getItems() {
        return items;
    }
    public void addToCartMenu(Scanner scanner) {
        boolean continueAdding = true;

        while (continueAdding) {
            System.out.println("Available Items:");
            System.out.println("----------------");
            Item.viewItems();

            System.out.print("Enter the name of the item you want to add:\n>> ");
            String itemName = scanner.nextLine();
            Item item = findItemByName(itemName);

            if (item != null) {
                if (item.isAvailability()) {
                    System.out.print("Enter quantity:\n>> ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine();
                    items.put(item, items.getOrDefault(item, 0) + quantity);
                    System.out.println("Added " + quantity + " of " + item.getName() + " to the cart.");
                } else {
                    System.out.println("Item is not available.");
                }
            } else {
                System.out.println("Item not found.");
            }

            System.out.print("Would you like to continue adding to the cart? (y/n):\n>> ");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("n")) {
                continueAdding = false;
            }
        }
    }

    public void viewCart() {
        if (items.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("Items in your cart:");
            double total = 0.0;
            for (Map.Entry<Item, Integer> entry : items.entrySet()) {
                System.out.println(entry.getKey() + " - Quantity: " + entry.getValue());
                total += entry.getKey().getPrice() * entry.getValue();
            }
            System.out.println("Total money: " + total + "/-");
        }
    }

    public void checkout(Scanner scanner, String customerId) {
        if (items.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }
        System.out.print("Do you have any special requests? (y/n):\n>> ");
        String specialRequestResponse = scanner.nextLine();
        String specialRequest = "";

        if (specialRequestResponse.equalsIgnoreCase("y")) {
            System.out.print("Enter your special request:\n>> ");
            specialRequest = scanner.nextLine();
        }

        System.out.print("Enter payment details:\n>> ");
        String paymentDetails = scanner.nextLine();
        System.out.print("Enter delivery address:\n>> ");
        String deliveryAddress = scanner.nextLine();

        System.out.println("Processing payment and delivery...");

        Orders.addOrder(customerId, new HashMap<>(items), specialRequest);
        items.clear();
        System.out.println("Order placed successfully. Thank you for your purchase!");
    }

    private Item findItemByName(String name) {
        for (Item item : Item.getListOfItems()) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public void addToCart(Item item) {
        items.put(item, items.getOrDefault(item, 0) + 1);
    }

}