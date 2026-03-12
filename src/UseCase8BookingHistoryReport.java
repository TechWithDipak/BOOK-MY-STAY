import java.util.*;

public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        System.out.println("Booking History and Reporting");
        System.out.println();

        HistoryTracker tracker = new HistoryTracker();

        StayRecord r1 = new StayRecord("A101", "Abhi", "Single");
        StayRecord r2 = new StayRecord("A102", "Subha", "Double");
        StayRecord r3 = new StayRecord("A103", "Vanmathi", "Suite");

        tracker.addRecord(r1);
        tracker.addRecord(r2);
        tracker.addRecord(r3);

        ReportGenerator generator = new ReportGenerator();
        generator.showReport(tracker);
    }
}

class StayRecord {

    private String id;
    private String guest;
    private String room;

    public StayRecord(String id, String guest, String room) {
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

class HistoryTracker {

    private List<StayRecord> records;

    public HistoryTracker() {
        records = new ArrayList<>();
    }

    public void addRecord(StayRecord r) {
        records.add(r);
    }

    public List<StayRecord> getRecords() {
        return records;
    }
}

class ReportGenerator {

    public void showReport(HistoryTracker tracker) {

        System.out.println("Booking History Report");

        for (StayRecord r : tracker.getRecords()) {
            System.out.println("ID: " + r.getId() +
                    ", Guest: " + r.getGuest() +
                    ", Room Type: " + r.getRoom());
        }
    }
}