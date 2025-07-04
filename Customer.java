class Customer {
    private String name;
    private double balance;

    public Customer(String name, double initialBalance) {
        this.name = name;
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deductBalance(double amount) {
        if (amount > balance) {
            throw new IllegalStateException("Insufficient funds");
        }
        this.balance -= amount;
    }

    public String getName() {
        return name;
    }
}