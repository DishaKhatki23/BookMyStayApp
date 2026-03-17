import java.util.*;

/**
 * UC10
 *
 * Demonstrates booking cancellation and inventory rollback
 * using Stack (LIFO) for controlled state reversal.
 *
 * @author Dweep
 * @version 10.0
 */

// Reservation Class
class Reservation {
    private String reservationId;
    private String roomType;
    private String roomId;

    public Reservation(String reservationId, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }
}

// Inventory Service
class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    public void increaseAvailability(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("\n--- Current Inventory ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// Booking History (for validation)
class BookingHistory {

    private Map<String, Reservation> history = new HashMap<>();

    public void addReservation(Reservation r) {
        history.put(r.getReservationId(), r);
    }

    public Reservation getReservation(String id) {
        return history.get(id);
    }

    public void removeReservation(String id) {
        history.remove(id);
    }
}

// Cancellation Service
class CancellationService {

    private RoomInventory inventory;
    private BookingHistory history;

    // Stack for rollback tracking (LIFO)
    private Stack<String> rollbackStack = new Stack<>();

    public CancellationService(RoomInventory inventory, BookingHistory history) {
        this.inventory = inventory;
        this.history = history;
    }

    public void cancelBooking(String reservationId) {

        Reservation r = history.getReservation(reservationId);

        // Validation
        if (r == null) {
            System.out.println("Cancellation Failed: Reservation not found.");
            return;
        }

        // Push room ID to stack (rollback tracking)
        rollbackStack.push(r.getRoomId());

        // Restore inventory
        inventory.increaseAvailability(r.getRoomType());

        // Remove from history
        history.removeReservation(reservationId);

        // Confirmation
        System.out.println("\nCancellation Successful!");
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Released Room ID: " + r.getRoomId());
        System.out.println("-----------------------------");
    }

    public void showRollbackStack() {
        System.out.println("\n--- Rollback Stack (LIFO) ---");
        for (String id : rollbackStack) {
            System.out.println(id);
        }
    }
}

// Main Class
public class UC10 {

    public static void main(String[] args) {

        System.out.println("=========================================");
        System.out.println("      BOOK MY STAY APP - VERSION 10.0    ");
        System.out.println("=========================================");

        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();

        // Add confirmed bookings
        history.addReservation(new Reservation("R101", "Single Room", "SI-101"));
        history.addReservation(new Reservation("R102", "Double Room", "DO-202"));

        // Cancellation service
        CancellationService service = new CancellationService(inventory, history);

        // Cancel booking
        service.cancelBooking("R101");

        // Invalid cancellation
        service.cancelBooking("R999");

        // Show rollback stack
        service.showRollbackStack();

        // Show updated inventory
        inventory.displayInventory();

        System.out.println("\n=========================================");
        System.out.println("      Cancellation Process Completed      ");
        System.out.println("=========================================");
    }
}