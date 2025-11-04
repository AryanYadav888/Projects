package com.ecommerce.service;

import com.ecommerce.entity.Product;
import com.ecommerce.payment.PaymentMethod;
import com.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final PaymentMethod creditCardPayment;
    private final PaymentMethod paypalPayment;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductRepository productRepository1,
                          @Qualifier("creditCardPayment") PaymentMethod creditCardPayment
            , @Qualifier("paypalPayment") PaymentMethod paypalPayment) {
        this.productRepository = productRepository1;
        this.creditCardPayment = creditCardPayment;
        this.paypalPayment = paypalPayment;
        System.out.println("Service: productService initialized with dependencies");
    }

    public Product createProduct(String name,String description,Double price,Integer stock){
        System.out.println("Service: Creating new Product - "+name);

        if(price < 0){
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if(stock < 0){
            throw  new IllegalArgumentException("Stock cannot be negative");
        }

        Product product = new Product(null , name,description,price,stock);
        return productRepository.save(product);
    }

    public Optional<Product> getProductById(Long id){
        System.out.println("Service: Fetching product with ID - "+id);
        return productRepository.findById(id);
    }

    public List<Product> getAllProduct(){
        System.out.println("Service: fetching all products");
        return productRepository.findAll();
    }

    public Product updateProduct(Long id ,Product updateProduct){
        System.out.println("Service: Updating product with id - "+id);

        Optional<Product> existingproduct = productRepository.findById(id);
        if(existingproduct.isPresent()){
            Product product = existingproduct.get();
            product.setName(updateProduct.getName());
            product.setDescription(updateProduct.getDescription());
            product.setPrice(updateProduct.getPrice());
            product.setStock(updateProduct.getStock());
            return productRepository.save(product);
        }
        throw new RuntimeException("Product not found with ID: "+id);
    }

    public void deleteProduct(Long id){
        System.out.println("Service: Deleteing product with ID - "+id);
        if(!productRepository.existsById(id)){
            throw new RuntimeException("Product not found with ID: "+id);
        }
        productRepository.deleteById(id);
    }

    public boolean purchaseProduct(Long productId,Integer quantity,String paymentMethod,String paymentDetails){
        System.out.println("\n=== Service: Processing Purchase ===");

        Optional<Product> productOpt = productRepository.findById(productId);
        if(!productOpt.isPresent()){
            System.out.println("Product not found!");
            return false;
        }

        Product product = productOpt.get();

        if(product.getStock() < quantity){
            System.out.println("Insufficient stock! Availabe: "+product.getStock()
            + ", Requested: "+quantity);
            return false;
        }

        Double totalAmount = product.getPrice()*quantity;

        PaymentMethod selectPayment;
        if("CREDIT_CARD".equalsIgnoreCase(paymentMethod)){
            selectPayment = creditCardPayment;
        } else if("PAYPAL".equalsIgnoreCase(paymentMethod)){
            selectPayment = paypalPayment;
        } else {
            System.out.println("Invalid payment method: "+paymentMethod);
            return false;
        }

        boolean paymentSuccess = selectPayment.processPayment(totalAmount,paymentDetails);

        if(paymentSuccess){
            product.setStock(product.getStock() - quantity);
            productRepository.save(product);
            System.out.println("Purchase completed! Remaining Stock: "+product.getStock());
            return true;
        }
        return false;
    }

    public long getProductCount(){
        return productRepository.count();
    }
}
