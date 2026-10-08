import java.util.*;

abstract class Vehicle {
    private String name;
    private boolean available;

    public Vehicle(String name) {
        this.name = name;
        available = true;
    }

    public String getName() {
        return name;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public abstract double calculateCharge(int days);
}

class StandardCar extends Vehicle {
    public StandardCar(String name) {
        super(name);
    }

    public double calculateCharge(int days) {
        return days * 50;
    }
}

class LuxuryCar extends Vehicle {
    public LuxuryCar(String name) {
        super(name);
    }

    public double calculateCharge(int days) {
        return days * 100;
    }
}

class SUV extends Vehicle {
    public SUV(String name) {
        super(name);
    }

    public double calculateCharge(int days) {
        return days * 80;
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

class Rental {
    private Customer customer;
    private Vehicle vehicle;
    private int days;
    private double totalCharge;
    private boolean active;

    public Rental(Customer customer, Vehicle vehicle, int days) {
        this.customer = customer;
        this.vehicle = vehicle;
        this.days = days;
        totalCharge = vehicle.calculateCharge(days);
        active = true;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public double getTotalCharge() {
        return totalCharge;
    }

    public boolean isActive() {
        return active;
    }

    public void close() {
        active = false;
        vehicle.setAvailable(true);
    }
}

class RentalService {
    private List<Rental> rentals;

    public RentalService() {
        rentals = new ArrayList<>();
    }

    public Rental rent(Customer customer, Vehicle vehicle, int days) {
        if (!vehicle.isAvailable()) {
            System.out.println("Rental failed: Vehicle is not available.");
            return null;
        }

        Rental rental = new Rental(customer, vehicle, days);

        vehicle.setAvailable(false);
        rentals.add(rental);

        System.out.println(vehicle.getName()
                + " rented for " + days + " days.");

        System.out.printf("Total charge: $%.2f%n",
                rental.getTotalCharge());

        return rental;
    }

    public void returnVehicle(Rental rental) {
        if (rental == null || !rental.isActive()) {
            return;
        }

        Vehicle vehicle = rental.getVehicle();

        rental.close();

        System.out.println(vehicle.getName()
                + " returned. Now available.");
    }
}

public class step2 {
    public static void main(String[] args) {
        Customer customer = new Customer("John");

        Vehicle luxury = new LuxuryCar("Luxury Car A");
        Vehicle standard = new StandardCar("Standard Car B");

        RentalService service = new RentalService();

        Rental rental1 = service.rent(customer, luxury, 3);

        Rental rental2 = service.rent(customer, standard, 5);

        service.returnVehicle(rental1);
    }
}