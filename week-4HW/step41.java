class FoodOrder {

    private String studentName;
    private String dishName;
    private boolean delivered;

    public FoodOrder(String studentName, String dishName) {

        if (studentName == null || studentName.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid student name");
        }

        if (dishName == null || dishName.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid dish name");
        }

        this.studentName = studentName.trim();
        this.dishName = dishName.trim();
        this.delivered = false;
    }

    public void markDelivered() {

        if (delivered) {
            System.out.println("Order already delivered for " + studentName);
        } else {
            delivered = true;
            System.out.println("Order delivered for " + studentName);
        }
    }

    public static void processBatch(String[][] rawOrders) {

        int valid = 0;
        int rejected = 0;

        for (String[] order : rawOrders) {

            try {

                if (order == null || order.length < 2) {
                    throw new IllegalArgumentException("Invalid order");
                }

                FoodOrder foodOrder = new FoodOrder(order[0], order[1]);

                valid++;

            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        System.out.println("Valid: " + valid + " | Rejected: " + rejected);
    }
}

public class step41 {

    public static void main(String[] args) {

        String[][] rawOrders = {
                { "Ravi", "Paneer Butter Masala" },
                { "", "Chole Bhature" },
                { "Meera", " " },
                { "Divya", "Veg Biryani" }
        };

        FoodOrder.processBatch(rawOrders);

        FoodOrder order = new FoodOrder("Ravi", "Paneer Butter Masala");

        order.markDelivered();
        order.markDelivered();
    }
}