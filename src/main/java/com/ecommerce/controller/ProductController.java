package com.ecommerce.controller;

import com.ecommerce.entity.Product;
import com.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@RequestMapping("/products")
@Controller
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
        System.out.println("Controller: ProductController initialized");
    }

    public String displayAllProducts() {
        System.out.println("\n>>> Controller: Display All Products Request");
        List<Product> products = productService.getAllProduct();

        System.out.println("\n--- Product Catalog ---");
        products.forEach(p -> System.out.println(p));
        System.out.println("Total Products: " + products.size());

        return "products/list"; // View name
    }

    public String displayProductById(Long id) {
        System.out.println("\n>>> Controller: Display Product Detail Request (ID: " + id + ")");
        Optional<Product> product = productService.getProductById(id);

        if (product.isPresent()) {
            System.out.println("Product Found: " + product.get());
            return "products/detail"; // View name
        } else {
            System.out.println("Product not found!");
            return "errors/404"; // Error view
        }
    }

    public String createNewProduct(String name, String description, Double price, Integer stock) {
        System.out.println("\n>>> Controller: Create Product Request");

        try {
            Product created = productService.createProduct(name, description, price, stock);
            System.out.println("Product Created Successfully: " + created);
            return "redirect:/products"; // Redirect after creation
        } catch (IllegalArgumentException e) {
            System.out.println("Validation Error: " + e.getMessage());
            return "products/create"; // Return to form with error
        }
    }

    public String updateExistingProduct(Long id, Product product) {
        System.out.println("\n>>> Controller: Update Product Request (ID: " + id + ")");

        try {
            Product updated = productService.updateProduct(id, product);
            System.out.println("Product Updated Successfully: " + updated);
            return "redirect:/products/" + id;
        } catch (RuntimeException e) {
            System.out.println("Update Error: " + e.getMessage());
            return "errors/404";
        }
    }

    public String deleteProduct(Long id) {
        System.out.println("\n>>> Controller: Delete Product Request (ID: " + id + ")");

        try {
            productService.deleteProduct(id);
            System.out.println("Product Deleted Successfully");
            return "redirect:/products";
        } catch (RuntimeException e) {
            System.out.println("Delete Error: " + e.getMessage());
            return "errors/404";
        }
    }

    // Purchase endpoint demonstrating payment strategy selection
    public String purchaseProduct(Long productId, Integer quantity, String paymentMethod, String paymentDetails) {
        System.out.println("\n>>> Controller: Purchase Product Request");
        System.out.println("Product ID: " + productId);
        System.out.println("Quantity: " + quantity);
        System.out.println("Payment Method: " + paymentMethod);

        boolean success = productService.purchaseProduct(productId, quantity, paymentMethod, paymentDetails);

        if (success) {
            System.out.println("✓ Purchase successful!");
            return "purchase/success";
        } else {
            System.out.println("✗ Purchase failed!");
            return "purchase/failure";
        }
    }

    public String displayProductCount() {
        System.out.println("\n>>> Controller: Product Count Request");
        long count = productService.getProductCount();
        System.out.println("Total Products in System: " + count);
        return "products/stats";
    }
}
