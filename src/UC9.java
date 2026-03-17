import java.util.*;

/**
 * UC9
 *
 * Demonstrates input validation and error handling
 * using custom exceptions to prevent invalid bookings.
 *
 * @author Dweep
 * @version 9.0
 */

// Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

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

// Inventory Class
class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0); // unavailable
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, -1);
    }

    public void reduceAvailability(String roomType) throws InvalidBookingException {

        int available = getAvailability(roomType);

        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for: " + roomType);
        }

        inventory.put(roomType, available - 1);
    }

    public void displayInventory() {
        System.out.println("\n--- Current Inventory ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// Validator Class
class BookingValidator {

    private static final List<String> validRoomTypes = Arrays.asList(
            "Single Room", "Double Room", "Suite Room"
    );

    public static void validate(Reservation reservation, RoomInventory inventory)
            throws InvalidBookingException {

        if (reservation == null) {
            throw new InvalidBookingException("Reservation cannot be null");
        }

        if (reservation.getGuestName() == null || reservation.getGuestName().isEmpty()) {
            throw new InvalidBookingException("Guest name is required");
        }

        if (!validRoomTypes.contains(reservation.getRoomType())) {
            throw new InvalidBookingException("Invalid room type: " + reservation.getRoomType());
        }

        if (inventory.getAvailability(reservation.getRoomType()) <= 0) {
            throw new InvalidBookingException("Room not available: " + reservation.getRoomType());
        }
    }
}

// Booking Service
class BookingService {

    private RoomInventory inventory;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void processBooking(Reservation reservation) {

        try {
            // Validate input
            BookingValidator.validate(reservation, inventory);

            // Reduce inventory
            inventory.reduceAvailability(reservation.getRoomType());

            // Confirm booking
            System.out.println("Booking Successful!");
            System.out.println("Guest: " + reservation.getGuestName());
            System.out.println("Room: " + reservation.getRoomType());
            System.out.println("-----------------------------");

        } catch (InvalidBookingException e) {
            // Graceful failure
            System.out.println("Booking Failed: " + e.getMessage());
        }
    }
}

// Main Class
public class UC9 {

    public static void main(String[] args) {

        System.out.println("=========================================");
        System.out.println("      BOOK MY STAY APP - VERSION 9.0     ");
        System.out.println("=========================================");

        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService(inventory);

        // Valid booking
        service.processBooking(new Reservation("Dweep", "Single Room"));

        // Invalid room type
        service.processBooking(new Reservation("Rahul", "Luxury Room"));

        // No availability
        service.processBooking(new Reservation("Ananya", "Suite Room"));

        // Empty name
        service.processBooking(new Reservation("", "Double Room"));

        // Show final inventory
        inventory.displayInventory();

        System.out.println("\n=========================================");
        System.out.println("      Validation Completed               ");
        System.out.println("=========================================");
    }
}