import java.util.*;

public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        System.out.println("Concurrent Booking Simulation");
        System.out.println();

        SharedInventory inventory = new SharedInventory();
        RequestQueue queue = new RequestQueue();

        queue.addRequest(new BookingTask("Guest1", "Single"));
        queue.addRequest(new BookingTask("Guest2", "Single"));
        queue.addRequest(new BookingTask("Guest3", "Double"));
        queue.addRequest(new BookingTask("Guest4", "Suite"));

        WorkerThread t1 = new WorkerThread(queue, inventory);
        WorkerThread t2 = new WorkerThread(queue, inventory);

        t1.start();
        t2.start();
    }
}

class BookingTask {

    private String guest;
    private String roomType;

    public BookingTask(String guest, String roomType) {
        this.guest = guest;
        this.roomType = roomType;
    }

    public String getGuest() {
        return guest;
    }

    public String getRoomType() {
        return roomType;
    }
}

class RequestQueue {

    private Queue<BookingTask> queue = new LinkedList<>();

    public synchronized void addRequest(BookingTask task) {
        queue.add(task);
    }

    public synchronized BookingTask getRequest() {
        if (queue.isEmpty()) return null;
        return queue.poll();
    }
}

class SharedInventory {

    private Map<String, Integer> rooms = new HashMap<>();

    public SharedInventory() {
        rooms.put("Single", 2);
        rooms.put("Double", 2);
        rooms.put("Suite", 1);
    }

    public synchronized boolean allocateRoom(String type) {

        int count = rooms.getOrDefault(type, 0);

        if (count > 0) {
            rooms.put(type, count - 1);
            return true;
        }

        return false;
    }
}

class WorkerThread extends Thread {

    private RequestQueue queue;
    private SharedInventory inventory;

    public WorkerThread(RequestQueue queue, SharedInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    public void run() {

        while (true) {

            BookingTask task = queue.getRequest();

            if (task == null) break;

            boolean success = inventory.allocateRoom(task.getRoomType());

            if (success) {
                System.out.println(task.getGuest() + " booked " + task.getRoomType());
            } else {
                System.out.println(task.getGuest() + " booking failed for " + task.getRoomType());
            }
        }
    }
}