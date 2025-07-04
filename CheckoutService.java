import java.util.Map;

class CheckoutService {
    public static void processCheckout(Customer customer, Cart cart) {
        // Check if cart is empty
        if (cart.isEmpty()) {
            throw new IllegalStateException("Error: Cart is empty");
        }

        Map<Product, Integer> items = cart.getItems();

        // Check stock and expiration
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int requestedQuantity = entry.getValue();

            // Check if product is out of stock
            if (product.getQuantity() < requestedQuantity) {
                throw new IllegalStateException(
                        String.format("Error: %s is out of stock (requested: %d, available: %d)",
                                product.getName(),
                                requestedQuantity,
                                product.getQuantity()));
            }
            if (product instanceof PerishableProduct) {
                if (((PerishableProduct) product).isExpired()) {
                    throw new IllegalStateException(
                            String.format("Error: %s is expired", product.getName()));
                }
            }
        }
        int subtotal = calculateSubtotal(items);
        int shipping = (int) ShippingService.calculateShippingCost(items);
        int totalAmount = subtotal + shipping;

        // Check if customer has sufficient balance
        if (customer.getBalance() < totalAmount) {
            throw new IllegalStateException(
                    String.format("Error: Insufficient funds (required: %d, available: %.2f)",
                            totalAmount, customer.getBalance()));
        }

        // Deduct amount from customer's balance
        customer.deductBalance(totalAmount);
        updateProductStock(items);

        System.out.println("Transaction Successful!");
        System.out.println("----------------------");

        ShippingService.processShipment(items);
        printReceipt(items, subtotal, shipping, totalAmount, customer);
    }

    private static int calculateSubtotal(Map<Product, Integer> items) {
        return items.entrySet().stream()
                .mapToInt(entry -> (int) (entry.getKey().getPrice() * entry.getValue()))
                .sum();
    }

    private static void updateProductStock(Map<Product, Integer> items) {
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            product.setQuantity(product.getQuantity() - quantity);
        }
    }

    private static void printReceipt(Map<Product, Integer> items,
            int subtotal,
            int shipping,
            int amount,
            Customer customer) {
        System.out.println("** Checkout receipt **");
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            System.out.printf("%dx %s %d%n",
                    entry.getValue(),
                    entry.getKey().getName(),
                    (int) (entry.getKey().getPrice() * entry.getValue()));
        }
        System.out.println("----------------------");
        System.out.printf("Subtotal %d%n", subtotal);
        System.out.printf("Shipping %d%n", shipping);
        System.out.printf("Amount %d%n", amount);
        System.out.printf("Remaining Balance %.2f%n", customer.getBalance());
    }
}