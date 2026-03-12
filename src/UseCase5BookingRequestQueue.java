import java.util.LinkedList;
import java.util.Queue;

/**
 * Represents a guest's booking request.
 */
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

    @Override
    public String toString() {
        return "Reservation{" +
                "Guest='" + guestName + '\'' +
                ", RoomType='" + roomType + '\'' +
                '}';
    }
}

/**
 * BookingRequestQueue manages incoming booking requests in FIFO order.
 */
class BookingRequestQueue {
    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    /**
     * Adds a new booking request to the queue.
     * @param reservation the booking request to add
     */
    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
        System.out.println("Request added: " + reservation);
    }

    /**
     * Returns the next booking request to process without removing it.
     */
    public Reservation peekNextRequest() {
        return requestQueue.peek();
    }

    /**
     * Processes and removes the next booking request in the queue.
     */
    public Reservation processNextRequest() {
        Reservation next = requestQueue.poll();
        if (next != null) {
            System.out.println("Processing request: " + next);
        }
        return next;
    }

    /**
     * Returns true if there are any pending requests.
     */
    public boolean hasPendingRequests() {
        return !requestQueue.isEmpty();
    }
}

/**
 * MAIN CLASS - UseCase5BookingRequestQueue
 *
 * Demonstrates booking requests queued fairly in FIFO order.
 */
public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("      Welcome to Book My Stay App     ");
        System.out.println("      Hotel Booking System v5.0       ");
        System.out.println("======================================\n");

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Simulating incoming booking requests (guest name, room type)
        bookingQueue.addRequest(new Reservation("Alice", "Single Room"));
        bookingQueue.addRequest(new Reservation("Bob", "Double Room"));
        bookingQueue.addRequest(new Reservation("Charlie", "Suite Room"));
        bookingQueue.addRequest(new Reservation("Diana", "Single Room"));

        System.out.println("\nProcessing booking requests in order:");

        while (bookingQueue.hasPendingRequests()) {
            bookingQueue.processNextRequest();
            // Note: No inventory mutation happens here; allocation is separate.
        }
    }
}