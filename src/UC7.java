import java.util.*;

/**
 * UC7
 *
 * Demonstrates add-on service selection for reservations
 * without modifying booking or inventory logic.
 *
 * @author Dweep
 * @version 7.0
 */

// Reservation Class
class Reservation {
    private String reservationId;
    private String guestName;

    public Reservation(String reservationId, String guestName) {
        this.reservationId = reservationId;
        this.guestName = guestName;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }
}

// Add-On Service Class
class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    public void display() {
        System.out.println(serviceName + " (₹" + cost + ")");
    }
}

// Add-On Service Manager
class AddOnServiceManager {

    // Map: ReservationID -> List of Services
    private HashMap<String, List<AddOnService>> serviceMap = new HashMap<>();

    // Add service to reservation
    public void addService(String reservationId, AddOnService service) {

        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);

        System.out.println("Added service: " + service.getServiceName()
                + " to Reservation ID: " + reservationId);
    }

    // Display services for a reservation
    public void displayServices(String reservationId) {

        System.out.println("\n--- Services for Reservation: " + reservationId + " ---");

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        for (AddOnService s : services) {
            s.display();
        }
    }

    // Calculate total cost
    public double calculateTotalCost(String reservationId) {

        List<AddOnService> services = serviceMap.get(reservationId);
        double total = 0;

        if (services != null) {
            for (AddOnService s : services) {
                total += s.getCost();
            }
        }

        return total;
    }
}

// Main Class
public class UC7 {

    public static void main(String[] args) {

        System.out.println("=========================================");
        System.out.println("      BOOK MY STAY APP - VERSION 7.0     ");
        System.out.println("=========================================");

        // Create reservation
        Reservation reservation = new Reservation("RES101", "Dweep");

        // Create service manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Add services
        manager.addService(reservation.getReservationId(),
                new AddOnService("Breakfast", 500));

        manager.addService(reservation.getReservationId(),
                new AddOnService("Airport Pickup", 1200));

        manager.addService(reservation.getReservationId(),
                new AddOnService("Spa Access", 2000));

        // Display services
        manager.displayServices(reservation.getReservationId());

        // Show total cost
        double total = manager.calculateTotalCost(reservation.getReservationId());

        System.out.println("\nTotal Add-On Cost: ₹" + total);

        System.out.println("\n=========================================");
        System.out.println("      Add-On Services Processed           ");
        System.out.println("=========================================");
    }
}