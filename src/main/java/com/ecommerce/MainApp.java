package com.ecommerce;


import com.ecommerce.controller.ProductController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.ecommerce")
public class MainApp {
    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("  E-COMMERCE APPLICATION - LAYERED ARCHITECTURE DEMO");
        System.out.println("=".repeat(60));

        ApplicationContext context = new AnnotationConfigApplicationContext(MainApp.class);

        ProductController controller = context.getBean(ProductController.class);

        System.out.println("\n" + "=".repeat(60));
        System.out.println("  DEPENDENCY INJECTION COMPLETE");
        System.out.println("=".repeat(60));

        // Test all operations
        testApplicationFlow(controller);
    }

    private static void testApplicationFlow(ProductController controller) {
        // 1. Display all products
        controller.displayAllProducts();

        // 2. View specific product
        controller.displayProductById(1L);

        // 3. Create new product
        controller.createNewProduct(
                "Tablet",
                "10-inch Android tablet",
                299.99,
                15
        );

        // 4. Display updated list
        controller.displayAllProducts();

        // 5. Purchase with Credit Card
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  TEST: Credit Card Purchase");
        System.out.println("=".repeat(60));
        controller.purchaseProduct(
                1L,                          // Laptop
                2,                            // Quantity
                "CREDIT_CARD",                // Payment method
                "4532 1234 5678 9010"        // Card number
        );

        // 6. Purchase with PayPal
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  TEST: PayPal Purchase");
        System.out.println("=".repeat(60));
        controller.purchaseProduct(
                2L,                          // Smartphone
                1,                            // Quantity
                "PAYPAL",                     // Payment method
                "user@example.com"           // PayPal email
        );

        // 7. Test invalid payment method (Ambiguity handling)
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  TEST: Invalid Payment Method");
        System.out.println("=".repeat(60));
        controller.purchaseProduct(
                3L,
                1,
                "BITCOIN",                    // Invalid method
                "bc1qxy2kgdygjrsqtzq2n0yrf2493p83kkfjhx0wlh"
        );

        // 8. Update product
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  TEST: Update Product");
        System.out.println("=".repeat(60));
        var product = new com.ecommerce.entity.Product(15L,"Gaming Laptop",
                "High-end gaming laptop with RTX 4090",
                1499.99,5);
        controller.updateExistingProduct(1L, product);

        // 9. Final product list
        controller.displayAllProducts();

        // 10. Product count
        controller.displayProductCount();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("  APPLICATION DEMONSTRATION COMPLETE");
        System.out.println("=".repeat(60));
    }
}
