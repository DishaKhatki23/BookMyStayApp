import java.io.*;
import java.util.*;

/**
 * UC12
 *
 * Demonstrates data persistence and recovery using serialization.
 * Saves and restores booking history and inventory from file.
 *
 * @author Dweep
 * @version 12.0
 */

// Reservation Class (Serializable)
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public void display() {
        System.out.println(reservationId + " | " + guestName + " | " + roomType);
    }
}

// Inventory Class (Serializable)
class RoomInventory implements Serializable {
    private static final long serialVersionUID = 1L;

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    public void displayInventory() {
        System.out.println("\n--- Inventory ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// Data Container (Serializable)
class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    List<Reservation> bookings;
    RoomInventory inventory;

    public SystemState(List<Reservation> bookings, RoomInventory inventory) {
        this.bookings = bookings;
        this.inventory = inventory;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "system_state.ser";

    // Save state
    public static void save(SystemState state) {

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("\nState saved successfully!");

        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    // Load state
    public static SystemState load() {

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            SystemState state = (SystemState) ois.readObject();
            System.out.println("\nState loaded successfully!");
            return state;

        } catch (FileNotFoundException e) {
            System.out.println("\nNo previous data found. Starting fresh.");
        } catch (Exception e) {
            System.out.println("\nError loading data. Starting safe state.");
        }

        return null;
    }
}

// Main Class
public class UC12 {

    public static void main(String[] args) {

        System.out.println("=========================================");
        System.out.println("      BOOK MY STAY APP - VERSION 12.0    ");
        System.out.println("=========================================");

        // Try loading previous state
        SystemState state = PersistenceService.load();

        List<Reservation> bookings;
        RoomInventory inventory;

        if (state == null) {
            // Fresh start
            bookings = new ArrayList<>();
            inventory = new RoomInventory();

            // Add sample data
            bookings.add(new Reservation("R101", "Dweep", "Single Room"));
            bookings.add(new Reservation("R102", "Rahul", "Double Room"));

        } else {
            bookings = state.bookings;
            inventory = state.inventory;
        }

        // Display data
        System.out.println("\n--- Booking History ---");
        for (Reservation r : bookings) {
            r.display();
        }

        inventory.displayInventory();

        // Save state before exit
        PersistenceService.save(new SystemState(bookings, inventory));

        System.out.println("\n=========================================");
        System.out.println("      System Recovery Completed           ");
        System.out.println("=========================================");
    }