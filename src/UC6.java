import java.util.*;

/**
 * UC6
 *
 * Demonstrates booking confirmation and room allocation
 * with uniqueness and inventory consistency.
 *
 * @author Dweep
 * @version 6.0
 */

// Reservation Class
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Inventory Service
class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void reduceAvailability(String roomType) {
        if (getAvailability(roomType) > 0) {
            inventory.put(roomType, getAvailability(roomType) - 1);
        }
    }

    public void displayInventory() {
        System.out.println("\n--- Current Inventory ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// Booking Queue (FIFO)
class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.add(r);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// Booking Service (Allocation Logic)
class BookingService {

    private RoomInventory inventory;

    // Map: RoomType -> Set of Room IDs
    private HashMap<String, Set<String>> allocatedRooms = new HashMap<>();

    // Global set to ensure uniqueness
    private Set<String> allRoomIds = new HashSet<>();

    private Random random = new Random(); // efficient random usage

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void processBooking(Reservation reservation) {

        if (reservation == null) return;

        String roomType = reservation.getRoomType();

        // Check availability
        if (inventory.getAvailability(roomType) <= 0) {
            System.out.println("Booking failed for " + reservation.getGuestName()
                    + " (No rooms available)");
            return;
        }

        // Generate unique room ID
        String roomId;
        do {
            roomId = roomType.substring(0, 2).toUpperCase() + "-" + random.nextInt(1000);
        } while (allRoomIds.contains(roomId));

        // Store ID globally
        allRoomIds.add(roomId);

        // Map room type to allocated IDs
        allocatedRooms.putIfAbsent(roomType, new HashSet<>());
        allocatedRooms.get(roomType).add(roomId);

        // Reduce inventory
        inventory.reduceAvailability(roomType);

        // Confirm booking
        System.out.println("\nBooking Confirmed!");
        System.out.println("Guest: " + reservation.getGuestName());
        System.out.println("Room Type: " + roomType);
        System.out.println("Assigned Room ID: " + roomId);
        System.out.println("-----------------------------");
    }

    public void displayAllocations() {
        System.out.println("\n--- Allocated Rooms ---");
        for (String type : allocatedRooms.keySet()) {
            System.out.println(type + " -> " + allocatedRooms.get(type));
        }
    }
}

// Main Class
public class UC6 {

    public static void main(String[] args) {

        System.out.println("=========================================");
        System.out.println("      BOOK MY STAY APP - VERSION 6.0     ");
        System.out.println("=========================================");

        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue queue = new BookingRequestQueue();
        BookingService service = new BookingService(inventory);

        // Add booking requests (FIFO)
        queue.addRequest(new Reservation("Dweep", "Single Room"));
        queue.addRequest(new Reservation("Rahul", "Single Room"));
        queue.addRequest(new Reservation("Ananya", "Single Room")); // will fail
        queue.addRequest(new Reservation("Karan", "Suite Room"));

        // Process queue
        while (!queue.isEmpty()) {
            Reservation r = queue.getNextRequest();
            service.processBooking(r);
        }

        // Show final state
        inventory.displayInventory();
        service.displayAllocations();

        System.out.println("\n=========================================");
        System.out.println("      Booking Process Completed           ");
        System.out.println("=========================================");
    }
}