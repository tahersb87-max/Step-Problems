import java.util.HashSet;

class BusTicket {

    private String passengerName;
    private String destination;
    private boolean checkedIn;

    public BusTicket(String passengerName, String destination) {

        if (passengerName == null ||
                passengerName.trim().isEmpty() ||
                !passengerName.trim().matches("[A-Za-z ]+")) {

            throw new IllegalArgumentException("Invalid passenger name");
        }

        if (destination == null ||
                destination.trim().isEmpty() ||
                !destination.trim().matches("[A-Za-z ]+")) {

            throw new IllegalArgumentException("Invalid destination");
        }

        this.passengerName = passengerName.trim();
        this.destination = destination.trim();
        this.checkedIn = false;
    }

    public void markCheckedIn() {

        if (checkedIn) {
            System.out.println(
                    passengerName + " is already checked in.");
        } else {
            checkedIn = true;
            System.out.println(
                    passengerName + " checked in successfully.");
        }
    }

    public static void processBatch(String[][] rawBookings) {

        int valid = 0;
        int rejected = 0;
        int duplicates = 0;

        HashSet<String> acceptedBookings = new HashSet<>();

        for (String[] booking : rawBookings) {

            try {

                if (booking == null || booking.length < 2) {
                    throw new IllegalArgumentException();
                }

                BusTicket ticket = new BusTicket(booking[0], booking[1]);

                String bookingKey = ticket.passengerName.toLowerCase()
                        + "|"
                        + ticket.destination.toLowerCase();

                if (acceptedBookings.contains(bookingKey)) {
                    duplicates++;
                } else {
                    acceptedBookings.add(bookingKey);
                    valid++;
                }

            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        System.out.println(
                "Valid: " + valid +
                        " | Rejected: " + rejected +
                        " | Duplicates skipped: " + duplicates);
    }
}

public class step41 {

    public static void main(String[] args) {

        String[][] rawBookings = {
                { "Divya", "Chennai" },
                { "", "Bangalore" },
                { "Ravi123", "Pune" },
                { "Divya", "Chennai" },
                { " ", " " }
        };

        BusTicket.processBatch(rawBookings);

        BusTicket ticket = new BusTicket("Divya", "Chennai");

        ticket.markCheckedIn();
        ticket.markCheckedIn();
    }
}
