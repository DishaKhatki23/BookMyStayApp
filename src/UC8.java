import java.util.*;

/**
 * UC8
 *
 * Demonstrates booking history tracking and reporting
 * using List for ordered storage of reservations.
 *
 * @author Dweep
 * @version 8.0
 */

// Reservation Class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room: " + roomType);
    }
}

// Booking History Class (Storage)
class BookingHistory {

    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    // Add confirmed booking
    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    // Get all bookings
    public List<Reservation> getAllReservations() {
        return history;
    }
}

// Report Service (Read-only)
class BookingReportService {

    private BookingHistory history;

    public BookingReportService(BookingHistory history) {
        this.history = history;
    }

    // Display full booking history
    public void displayAllBookings() {
        System.out.println("\n--- Booking History ---");

        List<Reservation> list = history.getAllReservations();

        if (list.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation r : list) {
            r.display();
        }
    }

    // Generate summary report
    public void generateSummary() {

        System.out.println("\n--- Booking Summary Report ---");

        List<Reservation> list = history.getAllReservations();

        int total = list.size();

        Map<String, Integer> countByRoom = new HashMap<>();

        for (Reservation r : list) {
            countByRoom.put(r.getRoomType(),
                    countByRoom.getOrDefault(r.getRoomType(), 0) + 1);
        }

        System.out.println("Total Bookings: " + total);

        for (String type : countByRoom.keySet()) {
            System.out.println(type + " : " + countByRoom.get(type));
        }
    }
}

// Main Class
public class UC8 {

    public static void main(String[] args) {

        System.out.println("=========================================");
        System.out.println("      BOOK MY STAY APP - VERSION 8.0     ");
        System.out.println("=========================================");

        // Initialize history
        BookingHistory history = new BookingHistory();

        // Add confirmed bookings
        history.addReservation(new Reservation("R101", "Dweep", "Single Room"));
        history.addReservation(new Reservation("R102", "Rahul", "Double Room"));
        history.addReservation(new Reservation("R103", "Ananya", "Single Room"));
        history.addReservation(new Reservation("R104", "Karan", "Suite Room"));

        // Report service
        BookingReportService report = new BookingReportService(history);

        // Display history
        report.displayAllBookings();

        // Generate summary
        report.generateSummary();

        System.out.println("\n=========================================");
        System.out.println("      Reporting Completed Successfully    ");
        System.out.println("=========================================");
    }
}