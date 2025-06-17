package com.pluralsight.NorthwindTradersSpringBoot.dao;

import com.pluralsight.NorthwindTradersSpringBoot.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcProductDao implements ProductDao {

    private final DataSource dataSource;

    @Autowired
    public JdbcProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Product product) {
        String sql = "INSERT INTO products (product_id, product_name, category, unit_price) VALUES (?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, product.getProductId());
            stmt.setString(2, product.getName());
            stmt.setString(3, product.getCategory());
            stmt.setDouble(4, product.getPrice());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error inserting product: " + e.getMessage());
        }
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT product_id, product_name, category, unit_price FROM products";

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getString("category"),
                        rs.getDouble("unit_price")
                );
                products.add(product);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving products: " + e.getMessage());
        }

        return products;
    }

    public void update(Product product) {
        String sql = "UPDATE products SET product_name = ?, category = ?, unit_price = ? WHERE product_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setString(2, product.getCategory());
            stmt.setDouble(3, product.getPrice());
            stmt.setInt(4, product.getProductId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error updating product: " + e.getMessage());
        }
    }

    public void delete(int productId) {
        String sql = "DELETE FROM products WHERE product_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error deleting product: " + e.getMessage());
        }
    }

    public Product findById(int productId) {
        String sql = "SELECT product_id, product_name, category, unit_price FROM products WHERE product_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getString("category"),
                        rs.getDouble("unit_price")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error finding product: " + e.getMessage());
        }

        return null;
    }
}
