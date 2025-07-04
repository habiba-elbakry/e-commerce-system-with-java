import java.time.LocalDate;

public class Main {
    public static void runTest(String testName, Runnable test) {
        System.out.println(testName);
        try {
            test.run();
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Transaction Failed!");
            System.out.println("----------------------");
            System.out.println(e.getMessage());
        }
        System.out.println();
    }

    public static void main(String[] args) {
        PerishableProduct cheese = new PerishableProduct("Cheese", 200, 3,
                LocalDate.now().plusDays(7), 0.5);
        PerishableProduct biscuits = new PerishableProduct("Biscuits", 50, 10,
                LocalDate.now().plusDays(30), 0.25);
        PerishableProduct expiredMilk = new PerishableProduct("Milk", 150, 5,
                LocalDate.now().minusDays(1), 1);
        NonPerishableProduct rice = new NonPerishableProduct("Rice", 100, 20, 1.0);
        DigitalProduct ebook = new DigitalProduct("E-book", 300, 100);
        DigitalProduct gameCode = new DigitalProduct("Game Code", 250, 50);

        // Create customers
        Customer Customer = new Customer("habiba", 2000.0);

        // Test 1:
        runTest("Test 1:", () -> {
            Cart Cart = new Cart();
            CheckoutService.processCheckout(Customer, Cart);
        });

        // Test 2:
        runTest("Test 2:", () -> {
            Cart Cart = new Cart();
            Cart.addProduct(cheese, 2);
            Cart.addProduct(biscuits, 3);
            Cart.addProduct(rice, 4);
            Cart.addProduct(ebook, 1);
            Cart.addProduct(gameCode, 1);
            CheckoutService.processCheckout(Customer, Cart);
        });

        // Test 3:
        runTest("Test 3:", () -> {
            Cart cart = new Cart();
            cart.addProduct(expiredMilk, 1);
            CheckoutService.processCheckout(Customer, cart);
        });

        // Test 4:
        runTest("Test 4: ", () -> {
            Cart cart = new Cart();
            cart.addProduct(cheese, 10);
            CheckoutService.processCheckout(Customer, cart);
        });
    }
}