import java.time.LocalDate;
import java.util.*;

interface PricingStrategy {
    double calculatePrice(int nights);
}

class StandardPricing implements PricingStrategy {
    public double calculatePrice(int nights) {
        return nights * 150;
    }
}

class DeluxePricing implements PricingStrategy {
    public double calculatePrice(int nights) {
        return nights * 200;
    }
}

class SuitePricing implements PricingStrategy {
    public double calculatePrice(int nights) {
        return nights * 300;
    }
}

class Customer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Room {
    private String roomNumber;
    private String category;
    private PricingStrategy pricingStrategy;

    public Room(String roomNumber, String category,
            PricingStrategy pricingStrategy) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.pricingStrategy = pricingStrategy;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getCategory() {
        return category;
    }

    public double calculatePrice(int nights) {
        return pricingStrategy.calculatePrice(nights);
    }
}

class Reservation {
    private Room room;
    private Customer customer;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;

    public Reservation(Room room, Customer customer,
            LocalDate startDate,
            LocalDate endDate) {
        this.room = room;
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
        active = true;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public boolean isActive() {
        return active;
    }

    public void cancel() {
        active = false;
    }

    public int getNights() {
        return (int) (endDate.toEpochDay()
                - startDate.toEpochDay());
    }
}

class BookingManager {
    private List<Reservation> reservations;

    public BookingManager() {
        reservations = new ArrayList<>();
    }

    public boolean isAvailable(Room room,
            LocalDate start,
            LocalDate end) {

        for (Reservation reservation : reservations) {
            if (!reservation.isActive()
                    || reservation.getRoom() != room) {
                continue;
            }

            boolean overlap = start.isBefore(reservation.getEndDate())
                    && end.isAfter(reservation.getStartDate());

            if (overlap) {
                return false;
            }
        }

        return true;
    }

    public Reservation book(Room room,
            Customer customer,
            LocalDate start,
            LocalDate end) {

        if (!isAvailable(room, start, end)) {
            System.out.println(
                    "Booking failed: " + room.getCategory()
                            + " Room " + room.getRoomNumber()
                            + " is not available for "
                            + start + " to " + end + ".");

            return null;
        }

        Reservation reservation = new Reservation(room, customer, start, end);

        reservations.add(reservation);

        double price = room.calculatePrice(reservation.getNights());

        System.out.println(
                room.getCategory() + " Room "
                        + room.getRoomNumber()
                        + " booked from " + start
                        + " to " + end + ".");

        System.out.printf("Total price: $%.2f%n", price);

        return reservation;
    }

    public void cancel(Reservation reservation,
            LocalDate cancellationDate) {

        if (reservation == null || !reservation.isActive()) {
            return;
        }

        if (!cancellationDate.isBefore(
                reservation.getStartDate())) {

            System.out.println(
                    "Cancellation failed: Cancellation deadline has passed.");

            return;
        }

        reservation.cancel();

        System.out.println(
                "Reservation for "
                        + reservation.getRoom().getCategory()
                        + " Room "
                        + reservation.getRoom().getRoomNumber()
                        + " cancelled successfully.");
    }
}

public class step3 {
    public static void main(String[] args) {
        Room deluxe = new Room(
                "101",
                "Deluxe",
                new DeluxePricing());

        Room standard = new Room(
                "205",
                "Standard",
                new StandardPricing());

        Customer customer = new Customer("John");

        BookingManager manager = new BookingManager();

        Reservation r1 = manager.book(
                deluxe,
                customer,
                LocalDate.of(2024, 12, 1),
                LocalDate.of(2024, 12, 5));

        Reservation r2 = manager.book(
                standard,
                customer,
                LocalDate.of(2024, 12, 3),
                LocalDate.of(2024, 12, 7));

        Reservation r3 = manager.book(
                deluxe,
                customer,
                LocalDate.of(2024, 12, 3),
                LocalDate.of(2024, 12, 7));

        manager.cancel(
                r1,
                LocalDate.of(2024, 11, 20));
    }
}