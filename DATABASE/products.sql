-- Make sure we're using the correct DB
CREATE DATABASE IF NOT EXISTS northwind;
USE northwind;

-- Forcefully drop the foreign key table first
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS inventory_transactions;
DROP TABLE IF EXISTS products;

SET FOREIGN_KEY_CHECKS = 1;

-- Now recreate the products table
CREATE TABLE products (
    product_id INT PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    category VARCHAR(255),
    unit_price DECIMAL(10,2)
);

-- Optional: recreate inventory_transactions if needed
CREATE TABLE inventory_transactions (
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT,
    quantity INT,
    transaction_date DATETIME,
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- Insert sample products
INSERT INTO products (product_id, product_name, category, unit_price) VALUES
(1, 'Laptop', 'Electronics', 999.99),
(2, 'Coffee Maker', 'Kitchen', 79.99),
(3, 'Desk Chair', 'Furniture', 149.99);
