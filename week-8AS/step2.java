import java.util.*;

interface ShippingType {
    double calculateCharge(double weight);

    String getName();
}

class StandardShipping implements ShippingType {
    public double calculateCharge(double weight) {
        return 40 + 10 * weight;
    }

    public String getName() {
        return "Standard";
    }
}

class ExpressShipping implements ShippingType {
    public double calculateCharge(double weight) {
        return 80 + 15 * weight;
    }

    public String getName() {
        return "Express";
    }
}

class FragileShipping implements ShippingType {
    private ShippingType standard = new StandardShipping();

    public double calculateCharge(double weight) {
        return standard.calculateCharge(weight) + 50;
    }

    public String getName() {
        return "Fragile";
    }
}

interface NotificationChannel {
    void notify(String parcelId, String status);
}

class SmsChannel implements NotificationChannel {
    public void notify(String parcelId, String status) {
        System.out.println("[SMS] " + parcelId + " is now " + status + ".");
    }
}

class EmailChannel implements NotificationChannel {
    public void notify(String parcelId, String status) {
        System.out.println("[Email] " + parcelId + " is now " + status + ".");
    }
}

class Customer {
    private String name;
    private List<NotificationChannel> channels;

    public Customer(String name) {
        this.name = name;
        channels = new ArrayList<>();
    }

    public void subscribe(NotificationChannel channel) {
        channels.add(channel);
    }

    public List<NotificationChannel> getChannels() {
        return channels;
    }

    public String getName() {
        return name;
    }
}

enum ParcelStatus {
    BOOKED,
    PICKED_UP,
    IN_TRANSIT,
    OUT_FOR_DELIVERY,
    DELIVERED
}

class Parcel {
    private String parcelId;
    private double weight;
    private ShippingType shippingType;
    private Customer customer;
    private ParcelStatus status;
    private List<NotificationChannel> channels;

    public Parcel(String parcelId, double weight,
            ShippingType shippingType, Customer customer) {
        this.parcelId = parcelId;
        this.weight = weight;
        this.shippingType = shippingType;
        this.customer = customer;
        this.status = ParcelStatus.BOOKED;
        this.channels = new ArrayList<>(customer.getChannels());
    }

    public String getParcelId() {
        return parcelId;
    }

    public double getCharge() {
        return shippingType.calculateCharge(weight);
    }

    public ParcelStatus getStatus() {
        return status;
    }

    public boolean changeStatus(ParcelStatus newStatus) {
        if (!isValidTransition(newStatus)) {
            System.out.println("Invalid transition: " + status
                    + " → " + newStatus + " is not allowed.");
            return false;
        }

        status = newStatus;
        notifyChannels();
        return true;
    }

    private boolean isValidTransition(ParcelStatus newStatus) {
        if (status == ParcelStatus.BOOKED
                && newStatus == ParcelStatus.PICKED_UP)
            return true;

        if (status == ParcelStatus.PICKED_UP
                && newStatus == ParcelStatus.IN_TRANSIT)
            return true;

        if (status == ParcelStatus.IN_TRANSIT
                && newStatus == ParcelStatus.OUT_FOR_DELIVERY)
            return true;

        if (status == ParcelStatus.OUT_FOR_DELIVERY
                && newStatus == ParcelStatus.DELIVERED)
            return true;

        return false;
    }

    public boolean cancel() {
        if (status != ParcelStatus.BOOKED) {
            System.out.println("Cancellation failed: " + parcelId
                    + " can be cancelled only while BOOKED.");
            return false;
        }

        return true;
    }

    private void notifyChannels() {
        for (NotificationChannel channel : channels) {
            channel.notify(parcelId, status.toString());
        }
    }

    public void notifyBooked() {
        notifyChannels();
    }
}

class ParcelService {
    public Parcel bookParcel(String parcelId, double weight,
            ShippingType shippingType,
            Customer customer) {
        Parcel parcel = new Parcel(
                parcelId,
                weight,
                shippingType,
                customer);

        System.out.println("Parcel " + parcelId + " booked ("
                + shippingType.getName() + ", "
                + weight + " kg).");

        System.out.printf("Charge: ₹%.2f%n", parcel.getCharge());

        parcel.notifyBooked();

        return parcel;
    }

    public void updateStatus(Parcel parcel, ParcelStatus status) {
        parcel.changeStatus(status);
    }

    public void cancel(Parcel parcel) {
        parcel.cancel();
    }
}

public class step2 {
    public static void main(String[] args) {
        Customer customer = new Customer("Customer");

        customer.subscribe(new SmsChannel());
        customer.subscribe(new EmailChannel());

        ParcelService service = new ParcelService();

        Parcel parcel = service.bookParcel(
                "P101",
                2,
                new ExpressShipping(),
                customer);

        service.updateStatus(parcel, ParcelStatus.PICKED_UP);

        service.cancel(parcel);

        service.updateStatus(parcel, ParcelStatus.IN_TRANSIT);

        service.updateStatus(parcel, ParcelStatus.DELIVERED);
    }
}