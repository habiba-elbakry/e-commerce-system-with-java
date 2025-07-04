import java.util.HashMap;
import java.util.Map;

class Cart {
    private Map<Product, Integer> items = new HashMap<>();

    public void addProduct(Product product, int quantity) {
        // Validate quantity before adding to cart
        if (quantity <= 0) {
            throw new IllegalArgumentException("Error: Quantity must be greater than 0");
        }
        if (quantity > product.getQuantity()) {
            throw new IllegalArgumentException(
                    String.format("Error: Not enough stock for %s (requested: %d, available: %d)",
                            product.getName(),
                            quantity,
                            product.getQuantity()));
        }
        items.put(product, items.getOrDefault(product, 0) + quantity);
    }

    public Map<Product, Integer> getItems() {
        return items;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}