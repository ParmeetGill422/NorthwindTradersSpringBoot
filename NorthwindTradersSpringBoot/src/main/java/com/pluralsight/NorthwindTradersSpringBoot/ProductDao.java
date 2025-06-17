package com.pluralsight.NorthwindTradersSpringBoot;

import com.northwind.model.Product;
import java.util.List;

public interface ProductDao {
    void add(Product product);
    List<Product> getAll();
}