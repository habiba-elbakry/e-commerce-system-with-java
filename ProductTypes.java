import java.time.LocalDate;

class PerishableProduct extends Product implements Shippable {
    private LocalDate expiryDate;
    private double weight;

    public PerishableProduct(String name, double price, int quantity, LocalDate expiryDate, double weight) {
        super(name, price, quantity);
        this.expiryDate = expiryDate;
        this.weight = weight;
    }

    public boolean isExpired() {
        return LocalDate.now().isAfter(expiryDate);
    }

    @Override
    public double getWeight() {
        return weight;
    }
}

class NonPerishableProduct extends Product implements Shippable {
    private double weight;

    public NonPerishableProduct(String name, double price, int quantity, double weight) {
        super(name, price, quantity);
        this.weight = weight;
    }

    @Override
    public double getWeight() {
        return weight;
    }
}

class DigitalProduct extends Product {
    public DigitalProduct(String name, double price, int quantity) {
        super(name, price, quantity);
    }
}