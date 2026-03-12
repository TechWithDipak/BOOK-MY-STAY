import java.util.*;

public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        System.out.println("Add-On Service Selection");
        System.out.println();

        StayReservation res = new StayReservation("R101", "Abhi");

        ExtraService s1 = new ExtraService("Breakfast", 500);
        ExtraService s2 = new ExtraService("Airport Pickup", 800);
        ExtraService s3 = new ExtraService("Spa Access", 1200);

        ServiceManager manager = new ServiceManager();

        manager.addService(res.getReservationId(), s1);
        manager.addService(res.getReservationId(), s2);
        manager.addService(res.getReservationId(), s3);

        System.out.println("Reservation ID: " + res.getReservationId());
        System.out.println("Guest Name: " + res.getGuestName());
        System.out.println();
        System.out.println("Selected Services:");

        for (ExtraService s : manager.getServices(res.getReservationId())) {
            System.out.println(s.getServiceName() + " - " + s.getServiceCost());
        }

        System.out.println();
        System.out.println("Total Add-On Cost: " + manager.calculateTotal(res.getReservationId()));
    }
}

class StayReservation {

    private String reservationId;
    private String guestName;

    public StayReservation(String reservationId, String guestName) {
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

class ExtraService {

    private String serviceName;
    private double serviceCost;

    public ExtraService(String serviceName, double serviceCost) {
        this.serviceName = serviceName;
        this.serviceCost = serviceCost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getServiceCost() {
        return serviceCost;
    }
}

class ServiceManager {

    private Map<String, List<ExtraService>> serviceMap = new HashMap<>();

    public void addService(String reservationId, ExtraService service) {

        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);
    }

    public List<ExtraService> getServices(String reservationId) {
        return serviceMap.getOrDefault(reservationId, new ArrayList<>());
    }

    public double calculateTotal(String reservationId) {

        double total = 0;

        for (ExtraService s : getServices(reservationId)) {
            total += s.getServiceCost();
        }

        return total;
    }
}