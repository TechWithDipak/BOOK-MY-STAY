import java.util.*;

public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        System.out.println("Booking Cancellation and Inventory Rollback");
        System.out.println();

        BookingStore store = new BookingStore();

        BookingEntry b1 = new BookingEntry("B101", "Abhi", "Single");
        BookingEntry b2 = new BookingEntry("B102", "Subha", "Double");

        store.addBooking(b1);
        store.addBooking(b2);

        CancellationHandler handler = new CancellationHandler();

        handler.cancelBooking("B101", store);
        handler.cancelBooking("B200", store);
    }
}

class BookingEntry {

    private String bookingId;
    private String guestName;
    private String roomType;
    private boolean active;

    public BookingEntry(String bookingId, String guestName, String roomType) {
        this.bookingId = bookingId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.active = true;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getRoomType() {
        return roomType;
    }

    public boolean isActive() {
        return active;
    }

    public void cancel() {
        active = false;
    }
}

class BookingStore {

    private Map<String, BookingEntry> bookings = new HashMap<>();
    private Map<String, Integer> roomInventory = new HashMap<>();

    public BookingStore() {
        roomInventory.put("Single", 5);
        roomInventory.put("Double", 5);
        roomInventory.put("Suite", 5);
    }

    public void addBooking(BookingEntry entry) {
        bookings.put(entry.getBookingId(), entry);
    }

    public BookingEntry getBooking(String id) {
        return bookings.get(id);
    }

    public void increaseInventory(String roomType) {
        roomInventory.put(roomType, roomInventory.get(roomType) + 1);
    }
}

class CancellationHandler {

    private Stack<String> rollbackStack = new Stack<>();

    public void cancelBooking(String bookingId, BookingStore store) {

        BookingEntry entry = store.getBooking(bookingId);

        if (entry == null) {
            System.out.println("Cancellation Failed: Booking not found for ID " + bookingId);
            return;
        }

        if (!entry.isActive()) {
            System.out.println("Cancellation Failed: Booking already cancelled");
            return;
        }

        rollbackStack.push(bookingId);

        store.increaseInventory(entry.getRoomType());

        entry.cancel();

        System.out.println("Booking Cancelled Successfully for ID: " + bookingId);
    }
}