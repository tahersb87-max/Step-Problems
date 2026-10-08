import java.util.*;

interface IPaymentMethod {
    boolean pay(double amount);

    String getName();
}

class CreditCardPayment implements IPaymentMethod {
    public boolean pay(double amount) {
        return true;
    }

    public String getName() {
        return "Credit Card";
    }
}

class DigitalWalletPayment implements IPaymentMethod {
    public boolean pay(double amount) {
        return false;
    }

    public String getName() {
        return "Digital Wallet";
    }
}

class CashOnDeliveryPayment implements IPaymentMethod {
    public boolean pay(double amount) {
        return true;
    }

    public String getName() {
        return "Cash on Delivery";
    }
}

class FoodItem {
    private String name;
    private double price;

    public FoodItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class Restaurant {
    private String name;
    private List<FoodItem> menu;

    public Restaurant(String name) {
        this.name = name;
        menu = new ArrayList<>();
    }

    public void addItem(FoodItem item) {
        menu.add(item);
    }

    public String getName() {
        return name;
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

    public void notifyCustomer(String message) {
        System.out.println("Notification: " + message);
    }
}

class LineItem {
    private FoodItem item;
    private int quantity;

    public LineItem(FoodItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public FoodItem getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotal() {
        return item.getPrice() * quantity;
    }
}

class Order {
    private int orderId;
    private Customer customer;
    private Restaurant restaurant;
    private List<LineItem> items;
    private String status;

    public Order(int orderId,
            Customer customer,
            Restaurant restaurant) {
        this.orderId = orderId;
        this.customer = customer;
        this.restaurant = restaurant;
        items = new ArrayList<>();
        status = "Created";

        System.out.println("Order created.");
    }

    public void addItem(FoodItem item, int quantity) {
        items.add(new LineItem(item, quantity));

        System.out.println(
                "Added " + item.getName()
                        + " (Qty " + quantity + ")");
    }

    public boolean place(IPaymentMethod paymentMethod) {
        if (items.isEmpty()) {
            System.out.println(
                    "Cannot place order: Order must contain at least one item.");
            return false;
        }

        status = "Pending Payment";

        System.out.println("Order placed successfully.");

        double total = calculateTotal();

        boolean success = paymentMethod.pay(total);

        if (success) {
            status = "Paid";

            System.out.println(
                    "Payment via "
                            + paymentMethod.getName()
                            + " successful.");

            System.out.println("Order status: Paid.");

            customer.notifyCustomer(
                    "Order #" + orderId
                            + " placed and paid.");
        } else {
            System.out.println(
                    "Payment via "
                            + paymentMethod.getName()
                            + " failed.");

            System.out.println(
                    "Order status: Pending Payment.");

            customer.notifyCustomer(
                    "Order #" + orderId
                            + " placed, awaiting payment.");
        }

        return success;
    }

    private double calculateTotal() {
        double total = 0;

        for (LineItem item : items) {
            total += item.getTotal();
        }

        return total;
    }
}

public class step5 {
    public static void main(String[] args) {
        Customer customer = new Customer("Asha");

        Restaurant restaurant = new Restaurant("Food Corner");

        FoodItem pizza = new FoodItem("Pizza", 200);

        FoodItem soda = new FoodItem("Soda", 50);

        FoodItem burger = new FoodItem("Burger", 150);

        Order order1 = new Order(
                123,
                customer,
                restaurant);

        order1.addItem(pizza, 2);
        order1.addItem(soda, 1);

        Order emptyOrder = new Order(
                122,
                customer,
                restaurant);

        emptyOrder.place(
                new CreditCardPayment());

        order1.place(
                new CreditCardPayment());

        Order order2 = new Order(
                124,
                customer,
                restaurant);

        order2.addItem(burger, 1);

        order2.place(
                new DigitalWalletPayment());
    }
}
