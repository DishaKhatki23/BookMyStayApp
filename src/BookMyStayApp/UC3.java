import java.util.HashMap;
import java.util.Map;

/**
 * UC3
 *
 * Demonstrates centralized room inventory management using HashMap.
 *
 * @author Dweep
 * @version 3.0
 */

// Inventory Class (Encapsulation of Logic)
class RoomInventory {

    private HashMap<String, Integer> inventory;

    // Constructor → Initialize inventory
    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 10);
        inventory.put("Double Room", 5);
        inventory.put("Suite Room", 2);
    }

    // Get availability of a specific room
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability
    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Display full inventory
    public void displayInventory() {
        System.out.println("\n--- Current Room Inventory ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// Main Class
public class UC3 {

    public static void main(String[] args) {

        System.out.println("=========================================");
        System.out.println("      BOOK MY STAY APP - VERSION 3.0     ");
        System.out.println("=========================================");

        // Initialize Inventory
        RoomInventory inventory = new RoomInventory();

        // Display Initial Inventory
        inventory.displayInventory();

        // Example: Update availability
        System.out.println("\nUpdating Single Room availability...");
        inventory.updateAvailability("Single Room", 8);

        // Display Updated Inventory
        inventory.displayInventory();

        // Example: Get availability
        System.out.println("\nAvailable Suite Rooms: " +
                inventory.getAvailability("Suite Room"));

        System.out.println("\n=========================================");
        System.out.println("      Inventory Loaded Successfully       ");
        System.out.println("=========================================");
    }
}