import java.util.Map;

class ShippingService {
    private static final double SHIPPING_RATE = 10.0;

    public static double calculateShippingCost(Map<Product, Integer> items) {
        double totalWeight = items.entrySet().stream()
                .filter(entry -> entry.getKey() instanceof Shippable)
                .mapToDouble(entry -> ((Shippable) entry.getKey()).getWeight() * entry.getValue())
                .sum();
        return totalWeight * SHIPPING_RATE;
    }

    public static void processShipment(Map<Product, Integer> items) {
        System.out.println("** Shipment notice **");
        double totalWeightKg = 0.0;

        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            if (entry.getKey() instanceof Shippable) {
                Shippable item = (Shippable) entry.getKey();
                int quantity = entry.getValue();
                double itemWeightGrams = item.getWeight() * 1000; // Convert to grams
                totalWeightKg += (itemWeightGrams * quantity) / 1000.0;

                System.out.printf("%dx %s %dg%n",
                        quantity,
                        item.getName(),
                        (int) itemWeightGrams);
            }
        }
        System.out.printf("Total package weight %.1fkg%n", totalWeightKg);
    }
}