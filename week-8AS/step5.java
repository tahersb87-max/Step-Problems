import java.util.*;

interface PricingPlan {
    double calculatePrice(double originalPrice);

    String getName();
}

class DayScholarPlan implements PricingPlan {
    public double calculatePrice(double originalPrice) {
        return originalPrice;
    }

    public String getName() {
        return "Day Scholar";
    }
}

class HostellerPlan implements PricingPlan {
    public double calculatePrice(double originalPrice) {
        return originalPrice * 0.90;
    }

    public String getName() {
        return "Hosteller";
    }
}

class StaffPlan implements PricingPlan {
    public double calculatePrice(double originalPrice) {
        return originalPrice * 0.80;
    }

    public String getName() {
        return "Staff";
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

class Transaction {
    private double amount;

    public Transaction(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}

class Purchase {
    private SmartCard card;
    private FoodItem item;
    private double chargedAmount;
    private boolean refunded;

    public Purchase(SmartCard card, FoodItem item,
            double chargedAmount) {
        this.card = card;
        this.item = item;
        this.chargedAmount = chargedAmount;
        this.refunded = false;
    }

    public SmartCard getCard() {
        return card;
    }

    public FoodItem getItem() {
        return item;
    }

    public double getChargedAmount() {
        return chargedAmount;
    }

    public boolean isRefunded() {
        return refunded;
    }

    public void markRefunded() {
        refunded = true;
    }
}

class SmartCard {
    private String cardNumber;
    private PricingPlan plan;
    private List<Transaction> transactions;
    private boolean blocked;

    public SmartCard(String cardNumber, PricingPlan plan) {
        this.cardNumber = cardNumber;
        this.plan = plan;
        transactions = new ArrayList<>();
        blocked = false;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public double getBalance() {
        double balance = 0;

        for (Transaction transaction : transactions) {
            balance += transaction.getAmount();
        }

        return balance;
    }

    public void topUp(double amount) {
        if (blocked) {
            System.out.println("Top-up failed: Card is blocked.");
            return;
        }

        if (amount < 100) {
            System.out.println("Top-up failed: Minimum top-up is ₹100.00.");
            return;
        }

        if (getBalance() + amount > 5000) {
            System.out.println("Top-up failed: Maximum balance is ₹5000.00.");
            return;
        }

        transactions.add(new Transaction(amount));

        System.out.printf(
                "%s topped up with ₹%.2f. Balance: ₹%.2f.%n",
                cardNumber,
                amount,
                getBalance());
    }

    public Purchase purchase(FoodItem item) {
        if (blocked) {
            System.out.println("Purchase failed: Card is blocked.");
            return null;
        }

        double chargedAmount = plan.calculatePrice(item.getPrice());

        double balance = getBalance();

        if (chargedAmount > balance) {
            System.out.printf(
                    "Purchase failed: Insufficient balance "
                            + "(required ₹%.2f, available ₹%.2f).%n",
                    chargedAmount,
                    balance);

            return null;
        }

        transactions.add(
                new Transaction(-chargedAmount));

        System.out.printf(
                "%s purchased for ₹%.2f. Balance: ₹%.2f.%n",
                item.getName(),
                chargedAmount,
                getBalance());

        return new Purchase(
                this,
                item,
                chargedAmount);
    }

    public void refund(Purchase purchase) {
        if (purchase == null
                || purchase.getCard() != this) {
            System.out.println("Refund rejected: Invalid purchase.");
            return;
        }

        if (purchase.isRefunded()) {
            System.out.println(
                    "Refund rejected: "
                            + purchase.getItem().getName()
                            + " has already been refunded.");
            return;
        }

        double amount = purchase.getChargedAmount();

        transactions.add(new Transaction(amount));
        purchase.markRefunded();

        System.out.printf(
                "Refund of ₹%.2f for %s processed. Balance: ₹%.2f.%n",
                amount,
                purchase.getItem().getName(),
                getBalance());
    }

    public void block() {
        blocked = true;
    }

    public void unblock() {
        blocked = false;
    }

    public void miniStatement() {
        System.out.print(
                "Mini-statement for " + cardNumber + ": ");

        for (int i = 0; i < transactions.size(); i++) {
            double amount = transactions.get(i).getAmount();

            if (amount >= 0) {
                System.out.printf("+%.2f", amount);
            } else {
                System.out.printf("%.2f", amount);
            }

            if (i < transactions.size() - 1) {
                System.out.print(", ");
            }
        }

        System.out.printf(
                " = ₹%.2f.%n",
                getBalance());
    }
}

public class step5 {
    public static void main(String[] args) {
        SmartCard card = new SmartCard(
                "C-2045",
                new HostellerPlan());

        FoodItem vegThali = new FoodItem("Veg Thali", 120);

        FoodItem coldCoffee = new FoodItem("Cold Coffee", 60);

        card.topUp(500);

        Purchase vegPurchase = card.purchase(vegThali);

        Purchase coffeePurchase = card.purchase(coldCoffee);

        FoodItem expensiveItem = new FoodItem("Special Meal", 400);

        card.purchase(expensiveItem);

        card.refund(vegPurchase);

        card.refund(vegPurchase);

        card.miniStatement();
    }
}