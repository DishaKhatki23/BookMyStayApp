import java.util.*;

/**
 * UC11
 *
 * Demonstrates concurrent booking using threads and synchronization
 * to ensure thread safety and prevent race conditions.
 *
 * @author Dweep
 * @version 11.0
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

// Shared Inventory (Thread-Safe)
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
    }

    // synchronized critical section
    public synchronized boolean allocateRoom(String roomType) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        }

        return false;
    }

    public void displayInventory() {
        System.out.println("\n--- Final Inventory ---");
        for (String type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type));
        }
    }
}

// Shared Queue
class BookingQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public synchronized void addRequest(Reservation r) {
        queue.add(r);
    }

    public synchronized Reservation getRequest() {
        return queue.poll();
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}

// Worker Thread (Booking Processor)
class BookingProcessor extends Thread {

    private BookingQueue queue;
    private RoomInventory inventory;

    public BookingProcessor(BookingQueue queue, RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {

        while (true) {

            Reservation r;

            // synchronized access to queue
            synchronized (queue) {
                if (queue.isEmpty()) break;
                r = queue.getRequest();
            }

            if (r != null) {

                boolean success = inventory.allocateRoom(r.getRoomType());

                if (success) {
                    System.out.println(Thread.currentThread().getName()
                            + " → Booking SUCCESS for " + r.getGuestName()
                            + " (" + r.getRoomType() + ")");
                } else {
                    System.out.println(Thread.currentThread().getName()
                            + " → Booking FAILED for " + r.getGuestName()
                            + " (" + r.getRoomType() + ")");
                }
            }
        }
    }
}

// Main Class
public class UC11 {

    public static void main(String[] args) {

        System.out.println("=========================================");
        System.out.println("      BOOK MY STAY APP - VERSION 11.0    ");
        System.out.println("=========================================");

        RoomInventory inventory = new RoomInventory();
        BookingQueue queue = new BookingQueue();

        // Add multiple requests (simulating users)
        queue.addRequest(new Reservation("Dweep", "Single Room"));
        queue.addRequest(new Reservation("Rahul", "Single Room"));
        queue.addRequest(new Reservation("Ananya", "Single Room"));
        queue.addRequest(new Reservation("Karan", "Double Room"));
        queue.addRequest(new Reservation("Neha", "Double Room"));

        // Create threads (concurrent processing)
        BookingProcessor t1 = new BookingProcessor(queue, inventory);
        BookingProcessor t2 = new BookingProcessor(queue, inventory);
        BookingProcessor t3 = new BookingProcessor(queue, inventory);

        t1.start();
        t2.start();
        t3.start();

        // Wait for threads to finish
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Final inventory
        inventory.displayInventory();

        System.out.println("\n=========================================");
        System.out.println("      Concurrent Booking Completed       ");
        System.out.println("=========================================");
    }
}