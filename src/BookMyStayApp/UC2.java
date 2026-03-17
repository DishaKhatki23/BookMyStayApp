/**
 * UC2
 *
 * Demonstrates object modeling using abstraction, inheritance,
 * and static availability for different room types.
 *
 * @author Dweep
 * @version 2.0
 */

// Abstract Class
abstract class Room {

    protected String roomType;
    protected int beds;
    protected double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Price per night: ₹" + price);
    }
}

// Single Room
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 1500);
    }
}

// Double Room
class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 2500);
    }
}

// Suite Room
class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 5000);
    }
}

// Main Class
public class UC2 {

    public static void main(String[] args) {

        System.out.println("=========================================");
        System.out.println("      BOOK MY STAY APP - VERSION 2.0     ");
        System.out.println("=========================================");

        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        int singleAvailable = 10;
        int doubleAvailable = 5;
        int suiteAvailable = 2;

        System.out.println("\n--- Single Room ---");
        single.displayDetails();
        System.out.println("Available: " + singleAvailable);

        System.out.println("\n--- Double Room ---");
        doubleRoom.displayDetails();
        System.out.println("Available: " + doubleAvailable);

        System.out.println("\n--- Suite Room ---");
        suite.displayDetails();
        System.out.println("Available: " + suiteAvailable);

        System.out.println("\n=========================================");
        System.out.println("      System Loaded Successfully         ");
        System.out.println("=========================================");
    }
}