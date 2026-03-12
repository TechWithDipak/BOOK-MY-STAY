import java.io.*;
import java.util.*;

public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        System.out.println("Data Persistence and System Recovery");
        System.out.println();

        SystemState state = new SystemState();

        state.addBooking(new BookingData("C101", "Abhi", "Single"));
        state.addBooking(new BookingData("C102", "Subha", "Double"));

        state.updateInventory("Single", 4);
        state.updateInventory("Double", 3);

        PersistenceHandler handler = new PersistenceHandler();

        handler.saveState(state);

        System.out.println("State saved to file");
        System.out.println();

        SystemState recovered = handler.loadState();

        if (recovered != null) {

            System.out.println("Recovered Bookings:");
            for (BookingData b : recovered.getBookings()) {
                System.out.println(b.getId() + " " + b.getGuest() + " " + b.getRoom());
            }

            System.out.println();
            System.out.println("Recovered Inventory:");
            for (String type : recovered.getInventory().keySet()) {
                System.out.println(type + " : " + recovered.getInventory().get(type));
            }
        }
    }
}

class BookingData implements Serializable {

    private String id;
    private String guest;
    private String room;

    public BookingData(String id, String guest, String room) {
        this.id = id;
        this.guest = guest;
        this.room = room;
    }

    public String getId() {
        return id;
    }

    public String getGuest() {
        return guest;
    }

    public String getRoom() {
        return room;
    }
}

class SystemState implements Serializable {

    private List<BookingData> bookings = new ArrayList<>();
    private Map<String, Integer> inventory = new HashMap<>();

    public void addBooking(BookingData b) {
        bookings.add(b);
    }

    public List<BookingData> getBookings() {
        return bookings;
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public void updateInventory(String type, int count) {
        inventory.put(type, count);
    }
}

class PersistenceHandler {

    private final String FILE_NAME = "systemdata.dat";

    public void saveState(SystemState state) {

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
            out.writeObject(state);
            out.close();
        } catch (Exception e) {
            System.out.println("Error saving data");
        }
    }

    public SystemState loadState() {

        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME));
            SystemState state = (SystemState) in.readObject();
            in.close();
            return state;
        } catch (Exception e) {
            System.out.println("No previous data found or file corrupted");
            return null;
        }
    }
}