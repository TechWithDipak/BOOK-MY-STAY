import java.util.HashMap;

class HotelStock {

    private HashMap<String, Integer> stockData;

    public HotelStock() {
        stockData = new HashMap<>();
        stockData.put("Single Room", 5);
        stockData.put("Double Room", 3);
        stockData.put("Suite Room", 2);
    }

    public int checkAvailability(String roomType) {
        return stockData.getOrDefault(roomType, 0);
    }

    public void updateStock(String roomType, int count) {
        stockData.put(roomType, count);
    }

    public void showStock() {
        System.out.println("\n--- Current Room Inventory ---");
        for (String roomType : stockData.keySet()) {
            System.out.println(roomType + " : " + stockData.get(roomType) + " available");
        }
    }
}

public class UseCase3InventorySetup {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("      Welcome to Book My Stay App     ");
        System.out.println("      Hotel Booking System v3.1       ");
        System.out.println("======================================");

        HotelStock hotelStock = new HotelStock();

        hotelStock.showStock();

        System.out.println("\nChecking availability for Double Room:");
        System.out.println("Available: " + hotelStock.checkAvailability("Double Room"));

        System.out.println("\nUpdating availability for Double Room...");
        hotelStock.updateStock("Double Room", 4);

        hotelStock.showStock();
    }
}