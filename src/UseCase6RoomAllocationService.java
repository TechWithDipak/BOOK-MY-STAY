import java.util.*;

/**
 * Represents a guest's booking request for Use Case 6.
 */
class BookingRequestUC6 {
    private String guestName;
    private String roomType;

    public BookingRequestUC6(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    @Override
    public String toString() {
        return "BookingRequestUC6{Guest='" + guestName + "', RoomType='" + roomType + "'}";
    }
}

/**
 * Manages room inventory for Use Case 6.
 */
class InventoryManagerUC6 {
    private Map<String, Integer> availabilityMap;

    public InventoryManagerUC6() {
        availabilityMap = new HashMap<>();
        availabilityMap.put("Single Room", 5);
        availabilityMap.put("Double Room", 3);
        availabilityMap.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return availabilityMap.getOrDefault(roomType, 0);
    }

    public boolean decrementAvailability(String roomType) {
        int available = getAvailability(roomType);
        if (available > 0) {
            availabilityMap.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (var entry : availabilityMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " available");
        }
    }
}

/**
 * Queue for booking requests (Use Case 6).
 */
class BookingQueueUC6 {
    private Queue<BookingRequestUC6> requestQueue = new LinkedList<>();

    public void addRequest(BookingRequestUC6 request) {
        requestQueue.offer(request);
        System.out.println("Added booking request: " + request);
    }

    public BookingRequestUC6 getNextRequest() {
        return requestQueue.poll();
    }

    public boolean hasRequests() {
        return !requestQueue.isEmpty();
    }
}

/**
 * Allocates rooms and confirms reservations (Use Case 6).
 */
class RoomAllocatorUC6 {

    private InventoryManagerUC6 inventory;
    private BookingQueueUC6 bookingQueue;
    private Map<String, Set<String>> allocatedRooms = new HashMap<>();

    public RoomAllocatorUC6(InventoryManagerUC6 inventory, BookingQueueUC6 bookingQueue) {
        this.inventory = inventory;
        this.bookingQueue = bookingQueue;
    }

    public void processBookings() {
        System.out.println("\nProcessing booking requests...\n");

        while (bookingQueue.hasRequests()) {
            BookingRequestUC6 request = bookingQueue.getNextRequest();
            String roomType = request.getRoomType();

            int available = inventory.getAvailability(roomType);
            if (available > 0) {
                String roomId = generateUniqueRoomId(roomType);
                boolean decremented = inventory.decrementAvailability(roomType);
                if (decremented) {
                    allocatedRooms.computeIfAbsent(roomType, k -> new HashSet<>()).add(roomId);
                    System.out.println("Reservation Confirmed: " + request.getGuestName() +
                            " assigned " + roomType + " with Room ID: " + roomId);
                }
            } else {
                System.out.println("No availability for " + roomType + ". Reservation for " + request.getGuestName() + " declined.");
            }
        }
    }

    private String generateUniqueRoomId(String roomType) {
        String prefix;
        switch (roomType) {
            case "Single Room": prefix = "S"; break;
            case "Double Room": prefix = "D"; break;
            case "Suite Room": prefix = "SU"; break;
            default: prefix = "R"; break;
        }

        Set<String> allocatedIds = allocatedRooms.getOrDefault(roomType, Collections.emptySet());
        int nextNumber = 1;
        while (allocatedIds.contains(prefix + "-" + nextNumber)) {
            nextNumber++;
        }
        return prefix + "-" + nextNumber;
    }

    public void displayAllocations() {
        System.out.println("\nCurrent Allocations:");
        for (var entry : allocatedRooms.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}

/**
 * MAIN CLASS - Use Case 6
 */
public class UseCase6RoomAllocationService {

    public static void main(String[] args) {
        System.out.println("======================================");
        System.out.println("      Welcome to Book My Stay App     ");
        System.out.println("      Hotel Booking System v6.0       ");
        System.out.println("======================================\n");

        InventoryManagerUC6 inventory = new InventoryManagerUC6();
        BookingQueueUC6 bookingQueue = new BookingQueueUC6();

        bookingQueue.addRequest(new BookingRequestUC6("Alice", "Single Room"));
        bookingQueue.addRequest(new BookingRequestUC6("Bob", "Double Room"));
        bookingQueue.addRequest(new BookingRequestUC6("Charlie", "Suite Room"));
        bookingQueue.addRequest(new BookingRequestUC6("Diana", "Single Room"));
        bookingQueue.addRequest(new BookingRequestUC6("Eve", "Suite Room"));
        bookingQueue.addRequest(new BookingRequestUC6("Frank", "Suite Room")); // Declined

        inventory.displayInventory();

        RoomAllocatorUC6 allocator = new RoomAllocatorUC6(inventory, bookingQueue);
        allocator.processBookings();

        inventory.displayInventory();
        allocator.displayAllocations();
    }
}