import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Item {
    private String name;
    private double price;
    private String category;
    private boolean availability;
    private double rating;

    public Item(String name, double price, String category, boolean availability, double rating) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.availability = availability;
        this.rating = rating;
    }

    public static void addItemToCart(Item item, String customerId) {
        Customer customer = Customer.getCustomerById(customerId);
        if (customer != null) {
            customer.getCart().addToCart(item);
            System.out.println("Item added to cart.");
        } else {
            System.out.println("Customer not found.");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "name = " + name + "\n" +
                "price = " + price + "\n" +
                "category = " + category + "\n" +
                "availability = " + availability + "\n" +
                "rating = " + rating + "\n" +
                "___________________";
    }

    private static final List<Item> ListOfItems = new ArrayList<>(List.of(
            new Item("Paalak Paneer", 10.99, "Veg", true, 4.5),
            new Item("Aalo", 5.49, "Veg", false, 3.8),
            new Item("Fried Rice", 20.00, "Veg", true, 4.9),
            new Item("Butter Chicken", 15.75, "NonVeg", true, 4.2),
            new Item("Chicken Drumsticks", 7.30, "NonVeg", false, 3.5)
    ));
    public static final List<Item> getListOfItems() {
        return ListOfItems;
    }
    public static void viewItems() {
        for (Item item : ListOfItems) {
            System.out.println(item);
        }
    }

    public static void addItem(Scanner scanner) {
        System.out.print("Enter item name:\n>> ");
        String name = scanner.nextLine();
        System.out.print("Enter item price:\n>> ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter item category:\n>> ");
        String category = scanner.nextLine();
        System.out.print("Is the item available (true/false):\n>> ");
        boolean availability = scanner.nextBoolean();
        System.out.print("Enter item rating:\n>> ");
        double rating = scanner.nextDouble();
        scanner.nextLine();

        Item newItem = new Item(name, price, category, availability, rating);
        ListOfItems.add(newItem);
        System.out.println("Item added successfully.");
    }
    public static void updateQuantity(Scanner scanner) {
    Customer customer = new Customer();
    customer.getCart().viewCart();
    System.out.print("Enter the name of the item you want to update the quantity for:\n>> ");
    String name = scanner.nextLine();
    Item item = null;

    for (Item i : ListOfItems) {
        if (i.getName().equalsIgnoreCase(name)) {
            item = i;
            break;
        }
    }

    if (item == null) {
        System.out.println("Item not found.");
        return;
    }

    System.out.print("Enter the new quantity:\n>> ");
    int newQuantity = scanner.nextInt();
    scanner.nextLine(); // Consume newline

    if (newQuantity < 0) {
        System.out.println("Quantity cannot be negative.");
        return;
    }

    Customer customerId = Customer.getCustomerById("customerId"); // Replace with actual customer ID
    if (customerId != null) {
        Cart cart = customerId.getCart();
        if (cart.getItems().containsKey(item)) {
            if (newQuantity == 0) {
                cart.getItems().remove(item);
                System.out.println("Item removed from the cart.");
            } else {
                cart.getItems().put(item, newQuantity);
                System.out.println("Quantity updated to " + newQuantity + " for item " + item.getName() + ".");
            }
        } else {
            System.out.println("Item not found in the cart.");
        }
    } else {
        System.out.println("Customer not found.");
    }
}
    public static void removeItem(Scanner scanner) {
        System.out.print("Enter the name of the item you want to remove:\n>> ");
        String name = scanner.nextLine();

        boolean removed = false;
        for (Item item : ListOfItems) {
            if (item.getName().equals(name)) {
                ListOfItems.remove(item);
                removed = true;
                break;
            }
        }

        if (removed) {
            System.out.println("Item removed successfully.");
        } else {
            System.out.println("Item not found.");
        }
    }

    public static void searchItem(Scanner scanner) {
        System.out.print("Enter the name of the item you want to search for:\n>> ");
        String name = scanner.nextLine();

        boolean found = false;
        for (Item item : ListOfItems) {
            if (item.getName().toLowerCase().contains(name.toLowerCase())) {
                System.out.println(item);
                System.out.println("------------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("Item not found.");
        }
    }

    public static void filterByCategory(Scanner scanner) {
        System.out.print("Enter the category you want to filter by:\n>> ");
        String category = scanner.nextLine();

        boolean found = false;
        for (Item item : ListOfItems) {
            if (item.getCategory().toLowerCase().equals(category.toLowerCase())) {
                System.out.println(item);
                System.out.println("------------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No items found in this category.");
        }
    }

    public static void sortItemsByPrice(Scanner scanner) {
        System.out.println("Choose sorting order:");
        System.out.println("1) Ascending");
        System.out.println("2) Descending");
        System.out.print(">> ");

        if (scanner.hasNextInt()) {
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    ListOfItems.sort(Comparator.comparingDouble(Item::getPrice));
                    System.out.println("Sorting in Ascending Order:");
                    break;
                case 2:
                    ListOfItems.sort(Comparator.comparingDouble(Item::getPrice).reversed());
                    System.out.println("Sorting in Descending Order:");
                    break;
                default:
                    System.out.println("Invalid choice. Sorting in ascending order by default.");
                    ListOfItems.sort(Comparator.comparingDouble(Item::getPrice));
            }

            for (Item item : ListOfItems) {
                System.out.println(item);
            }
        } else {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
        }
    }
    //admin side
    public static void updateItem(Scanner scanner) {
        System.out.print("Enter the name of the item you want to update:\n>> ");
        String name = scanner.nextLine();

        boolean found = false;
        for (Item item : ListOfItems) {
            if (item.getName().equals(name)) {
                System.out.println("Enter new item name:");
                String newName = scanner.nextLine();
                System.out.println("Enter new item price:");
                double newPrice = scanner.nextDouble();
                scanner.nextLine();
                System.out.println("Enter new item category:");
                String newCategory = scanner.nextLine();
                System.out.println("Is the item available (true/false):");
                boolean newAvailability = scanner.nextBoolean();
                System.out.println("Enter new item rating:");
                double newRating = scanner.nextDouble();
                scanner.nextLine();

                item.setName(newName);
                item.setPrice(newPrice);
                item.setCategory(newCategory);
                item.setAvailability(newAvailability);
                item.setRating(newRating);

                System.out.println("Item updated successfully.");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Item not found.");
        }
    }

}

