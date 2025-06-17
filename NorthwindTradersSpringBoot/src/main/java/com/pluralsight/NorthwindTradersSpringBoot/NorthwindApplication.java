package com.pluralsight.NorthwindTradersSpringBoot;

import com.pluralsight.NorthwindTradersSpringBoot.dao.ProductDao;
import com.pluralsight.NorthwindTradersSpringBoot.dao.JdbcProductDao;
import com.pluralsight.NorthwindTradersSpringBoot.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class NorthwindApplication implements CommandLineRunner {

    @Autowired
    private ProductDao productDao;

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nProduct Management");
            System.out.println("1. List Products");
            System.out.println("2. Add Product");
            System.out.println("3. Update Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Search Product");
            System.out.println("0. Exit");
            System.out.print("Choose option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    List<Product> products = productDao.getAll();
                    products.forEach(System.out::println);
                    break;

                case "2":
                    System.out.print("Enter Product ID: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter Product Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Category: ");
                    String category = scanner.nextLine();
                    System.out.print("Enter Price: ");
                    double price = Double.parseDouble(scanner.nextLine());

                    productDao.add(new Product(id, name, category, price));
                    System.out.println("Product added successfully.");
                    break;

                case "3":
                    System.out.print("Enter Product ID to update: ");
                    int updateId = Integer.parseInt(scanner.nextLine());
                    Product existing = ((JdbcProductDao) productDao).findById(updateId);
                    if (existing == null) {
                        System.out.println("Product not found.");
                        break;
                    }

                    System.out.print("Enter New Name (" + existing.getName() + "): ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter New Category (" + existing.getCategory() + "): ");
                    String newCat = scanner.nextLine();
                    System.out.print("Enter New Price (" + existing.getPrice() + "): ");
                    double newPrice = Double.parseDouble(scanner.nextLine());

                    existing.setName(newName);
                    existing.setCategory(newCat);
                    existing.setPrice(newPrice);

                    ((JdbcProductDao) productDao).update(existing);
                    System.out.println("Product updated.");
                    break;

                case "4":
                    System.out.print("Enter Product ID to delete: ");
                    int deleteId = Integer.parseInt(scanner.nextLine());
                    ((JdbcProductDao) productDao).delete(deleteId);
                    System.out.println("Product deleted.");
                    break;

                case "5":
                    System.out.print("Enter Product ID to search: ");
                    int searchId = Integer.parseInt(scanner.nextLine());
                    Product found = ((JdbcProductDao) productDao).findById(searchId);
                    if (found != null)
                        System.out.println(found);
                    else
                        System.out.println("Product not found.");
                    break;

                case "0":
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
