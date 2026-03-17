import java.util.LinkedList;
import java.util.Queue;

/**
 * UC5
 *
 * Demonstrates booking request handling using Queue (FIFO).
 * Requests are stored in arrival order without modifying inventory.
 *
 * @author Dweep
 * @version 5.0
 */

// Reservation Class (Represents a booking request)
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

    public void display() {
        System.out.println("Guest: " + guestName + " | Room: " + roomType);
    }
}

// Booking Queue Class
class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Add request to queue
    public void addRequest(Reservation reservation) {
        queue.add(reservation);
        System.out.println("Request added for " + reservation.getGuestName());
    }

    // Display all requests (FIFO order)
    public void displayQueue() {
        System.out.println("\n--- Booking Request Queue (FIFO) ---");

        for (Reservation r : queue) {
            r.display();
        }
    }
}

// Main Class
public class UC5 {

    public static void main(String[] args) {

        System.out.println("=========================================");
        System.out.println("      BOOK MY STAY APP - VERSION 5.0     ");
        System.out.println("=========================================");

        // Initialize queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Add booking requests
        bookingQueue.addRequest(new Reservation("Dweep", "Single Room"));
        bookingQueue.addRequest(new Reservation("Rahul", "Double Room"));
        bookingQueue.addRequest(new Reservation("Ananya", "Suite Room"));

        // Display queue
        bookingQueue.displayQueue();

        System.out.println("\n=========================================");
        System.out.println("      Requests Queued Successfully        ");
        System.out.println("=========================================");
    }
}