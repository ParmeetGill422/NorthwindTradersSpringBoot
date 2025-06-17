package com.pluralsight.NorthwindTradersSpringBoot.dao;

import com.pluralsight.NorthwindTradersSpringBoot.model.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

public class SimpleProductDao implements ProductDao {

    private List<Product> products = new ArrayList<>();

    public SimpleProductDao() {
        products.add(new Product(1, "Laptop", "Electronics", 999.99));
        products.add(new Product(2, "Coffee Maker", "Kitchen", 79.99));
        products.add(new Product(3, "Desk Chair", "Furniture", 149.99));
    }

    @Override
    public void add(Product product) {
        products.add(product);
    }

    @Override
    public List<Product> getAll() {
        return products;
    }
}
