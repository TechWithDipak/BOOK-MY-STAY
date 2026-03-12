import java.util.*;

public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        System.out.println("Error Handling and Validation");
        System.out.println();

        BookingProcessor processor = new BookingProcessor();

        try {
            GuestInput input1 = new GuestInput("Abhi", "Single", 2);
            processor.process(input1);
        } catch (InvalidBookingException e) {
            System.out.println("Error: " + e.getMessage());
        }

        try {
            GuestInput input2 = new GuestInput("Subha", "Deluxe", -1);
            processor.process(input2);
        } catch (InvalidBookingException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

class GuestInput {

    private String guestName;
    private String roomType;
    private int nights;

    public GuestInput(String guestName, String roomType, int nights) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.nights = nights;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getNights() {
        return nights;
    }
}

class BookingProcessor {

    private InputChecker checker = new InputChecker();

    public void process(GuestInput input) throws InvalidBookingException {

        checker.validate(input);

        System.out.println("Booking Successful");
        System.out.println("Guest: " + input.getGuestName());
        System.out.println("Room Type: " + input.getRoomType());
        System.out.println("Nights: " + input.getNights());
        System.out.println();
    }
}

class InputChecker {

    private List<String> validRooms = Arrays.asList("Single", "Double", "Suite");

    public void validate(GuestInput input) throws InvalidBookingException {

        if (!validRooms.contains(input.getRoomType())) {
            throw new InvalidBookingException("Invalid room type selected");
        }

        if (input.getNights() <= 0) {
            throw new InvalidBookingException("Number of nights must be greater than zero");
        }
    }
}

class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
    }
}