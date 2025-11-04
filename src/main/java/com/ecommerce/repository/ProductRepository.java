package com.ecommerce.repository;

import com.ecommerce.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ProductRepository {
    private Map<Long, Product> database = new HashMap<>();
    private Long currentId = 1L;

    public ProductRepository(){
        save(new Product(null,"Laptop","High-preformance Laptop",999.99,10));
        save(new Product(null, "Smartphone", "Latest Android phone", 599.99, 25));
        save(new Product(null, "Headphones", "Noise-cancelling headphones", 199.99, 50));
        save(new Product(null, "Keyboard", "Mechanical gaming keyboard", 129.99, 30));
        save(new Product(null, "Mouse", "Wireless optical mouse", 49.99, 100));
    }

    public Product save(Product product) {
        if(product.getId() == null){
            product.setId(currentId++);
        }
        database.put(product.getId(),product);
        System.out.println("Repository: Saved product - "+product);
        return product;
    }

    public Optional<Product> findById(Long id){
        System.out.println("Repository: Finding product by id - "+id);
        return Optional.ofNullable(database.get(id));
    }

    public List<Product> findAll(){
        System.out.println("Repository: Retriving all Products");
        return new ArrayList<>(database.values());
    }

    public void deleteById(Long id){
        System.out.println("Repository: Deleting Product with id - "+id);
        database.remove(id);
    }
    public boolean existsById(Long id){
        return database.containsKey(id);
    }
    public long count(){
        return database.size();
    }
}
