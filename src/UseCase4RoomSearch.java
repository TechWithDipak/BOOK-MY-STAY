import java.util.HashMap;

/**
 * Abstract Room class representing common room properties.
 */
abstract class RoomBase {
    protected String name;
    protected int beds;
    protected int size; // in sqft
    protected double price;

    public RoomBase(String name, int beds, int size, double price) {
        this.name = name;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void displayDetails(int availability) {
        System.out.println(name + ":");
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sqft");
        System.out.println("Price per night: " + price);
        System.out.println("Available: " + availability);
        System.out.println();
    }
}

/**
 * Concrete Room types renamed to avoid duplication.
 */
class Room1 extends RoomBase {
    public Room1() {
        super("Single Room", 1, 250, 1500.0);
    }
}

class Room2 extends RoomBase {
    public Room2() {
        super("Double Room", 2, 400, 2500.0);
    }
}

class Room3 extends RoomBase {
    public Room3() {
        super("Suite Room", 3, 750, 5000.0);
    }
}

/**
 * RoomInventory manages availability counts for rooms.
 */
class RoomInventory {
    private HashMap<String, Integer> availabilityMap;

    public RoomInventory() {
        availabilityMap = new HashMap<>();
        availabilityMap.put("Single Room", 5);
        availabilityMap.put("Double Room", 3);
        availabilityMap.put("Suite Room", 2);
    }

    public int getAvailability(String roomName) {
        return availabilityMap.getOrDefault(roomName, 0);
    }
}

/**
 * RoomSearchService handles read-only search/display of available rooms.
 */
class RoomSearchService {

    /**
     * Displays rooms with availability > 0.
     */
    public void searchAvailableRooms(RoomInventory inventory, RoomBase room1, RoomBase room2, RoomBase room3) {
        System.out.println("Room Search\n");

        if (inventory.getAvailability(room1.getName()) > 0) {
            room1.displayDetails(inventory.getAvailability(room1.getName()));
        }
        if (inventory.getAvailability(room2.getName()) > 0) {
            room2.displayDetails(inventory.getAvailability(room2.getName()));
        }
        if (inventory.getAvailability(room3.getName()) > 0) {
            room3.displayDetails(inventory.getAvailability(room3.getName()));
        }
    }
}

/**
 * MAIN CLASS - UseCase4RoomSearch
 *
 * Demonstrates room search and availability check.
 */
public class UseCase4RoomSearch {

    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();

        RoomBase room1 = new Room1();
        RoomBase room2 = new Room2();
        RoomBase room3 = new Room3();

        RoomSearchService searchService = new RoomSearchService();
        searchService.searchAvailableRooms(inventory, room1, room2, room3);
    }
}