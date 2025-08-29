
-- Insert data into the users table
INSERT INTO users (first_name, last_name, email, address, phone_number, username, password, active, created_at, updated_at) VALUES
('John', 'Doe', 'john.doe@email.com', '123 Main St', '123-456-7890', 'josephr2316', '$2a$10$ZLUHFUm06kcn.qDH7vLa2upvEbfrMNGCfeQTZ/eQ0ai9iaVaE7bsO', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Jane', 'Doe', 'jane.doe@email.com', '456 Oak St', '987-654-3210', 'janedoe', '$2a$10$ZLUHFUm06kcn.qDH7vLa2upvEbfrMNGCfeQTZ/eQ0ai9iaVaE7bsO', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Alice', 'Smith', 'alice.smith@email.com', '789 Elm St', '555-123-4567', 'alicesmith', '$2a$10$ZLUHFUm06kcn.qDH7vLa2upvEbfrMNGCfeQTZ/eQ0ai9iaVaE7bsO', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Bob', 'Johnson', 'bob.johnson@email.com', '987 Pine St', '111-222-3333', 'bobjohnson', '$2a$10$ZLUHFUm06kcn.qDH7vLa2upvEbfrMNGCfeQTZ/eQ0ai9iaVaE7bsO', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Eva', 'Miller', 'eva.miller@email.com', '234 Maple St', '444-555-6666', 'evamiller', '$2a$10$ZLUHFUm06kcn.qDH7vLa2upvEbfrMNGCfeQTZ/eQ0ai9iaVaE7bsO', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Admin1', 'Admin', 'admin1@email.com', '123 Admin St', '777-888-9999', 'admin1', '$2a$10$ZLUHFUm06kcn.qDH7vLa2upvEbfrMNGCfeQTZ/eQ0ai9iaVaE7bsO', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('User1', 'User', 'user1@email.com', '123 User St', '555-666-7777', 'user1', '$2a$10$ZLUHFUm06kcn.qDH7vLa2upvEbfrMNGCfeQTZ/eQ0ai9iaVaE7bsO', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert data into the user_roles table
INSERT INTO user_roles (user_id, role) VALUES
(1, 'ADMIN'),
(2, 'USER'),
(3, 'USER'),
(4, 'ADMIN'),
(5, 'USER'),
(6, 'ADMIN'),
(7, 'USER');

-- Insert data into the categories table
INSERT INTO categories (category_name, description, active, created_at, updated_at) VALUES
('Electronics', 'Electronic devices and accessories', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Clothing', 'Fashion and apparel', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Books', 'Books and literature', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Home and Kitchen', 'Home appliances and kitchen items', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Sports and Outdoors', 'Sports equipment and outdoor gear', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert data into the products table
INSERT INTO products (product_name, description, price, stock, active, category_id, created_at, updated_at) VALUES
('Smartphone X', 'High-end smartphone with latest features', 899.99, 50, true, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Laptop Pro', 'Thin and light laptop for productivity', 1299.99, 30, true, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Wireless Headphones', 'Over-ear headphones with noise cancellation', 149.99, 100, true, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Casual T-shirt', 'Comfortable cotton T-shirt for everyday wear', 19.99, 200, true, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Running Shoes Elite', 'Durable running shoes for fitness enthusiasts', 79.99, 50, true, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Bestseller Novel', 'Best-selling novel by a renowned author', 29.99, 20, true, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Premium Cookware Set', 'Premium stainless steel cookware set', 199.99, 10, true, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Outdoor Gas Grill', 'Gas-powered outdoor grill for barbecue parties', 299.99, 5, true, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Family Board Game', 'Family-friendly board game for entertainment', 39.99, 30, true, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Feature-Packed Smartwatch', 'Feature-packed smartwatch for fitness and notifications', 129.99, 15, true, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert data into the carts table
INSERT INTO carts (user_id, total_price, created_at, updated_at) VALUES
(1, 100.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 75.50, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 50.25, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 120.75, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 90.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert data into the cart_items table
INSERT INTO cart_items (cart_id, product_id, quantity, created_at, updated_at) VALUES
(1, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 3, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 4, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 5, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert data into the orders table
INSERT INTO orders (user_id, status, total_amount, shipping_address, order_number, created_at, updated_at) VALUES
(1, 'DELIVERED', 1049.98, '123 Main St, City, State 12345', 'ORD-1704067200000', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'PROCESSING', 149.99, '456 Oak St, City, State 12345', 'ORD-1704153600000', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'PENDING', 19.99, '789 Elm St, City, State 12345', 'ORD-1704240000000', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'DELIVERED', 199.99, '987 Pine St, City, State 12345', 'ORD-1704326400000', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'CANCELLED', 299.99, '234 Maple St, City, State 12345', 'ORD-1704412800000', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert data into the order_items table
INSERT INTO order_items (order_id, product_id, quantity, unit_price, product_name_snapshot, created_at, updated_at) VALUES
(1, 1, 1, 899.99, 'Smartphone X', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 2, 1, 149.99, 'Wireless Headphones', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 3, 1, 149.99, 'Wireless Headphones', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 4, 1, 19.99, 'Casual T-shirt', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 7, 1, 199.99, 'Premium Cookware Set', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert data into the invoices table
INSERT INTO invoices (order_id, total_amount, invoice_number, payment_status, tax_amount, discount_amount, created_at, updated_at) VALUES
(1, 1049.98, 'INV-1704067200000', 'PAID', 52.50, 0.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 149.99, 'INV-1704153600000', 'PENDING', 7.50, 0.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 19.99, 'INV-1704240000000', 'PENDING', 1.00, 0.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 199.99, 'INV-1704326400000', 'PAID', 10.00, 0.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert data into the reviews table
INSERT INTO reviews (product_id, user_id, comment, rating, like_dislike, created_at, updated_at) VALUES
(1, 1, 'Great product! Excellent quality and fast delivery.', 5, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 'The laptop is perfect for my work needs.', 5, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 3, 'Good headphones but could be better.', 4, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 4, 'Comfortable t-shirt, exactly as described.', 5, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 5, 'Great running shoes, very durable.', 4, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(1, 2, 'Amazing smartphone with great features.', 5, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 3, 'Good laptop but expensive.', 4, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 4, 'Not satisfied with the sound quality.', 2, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);