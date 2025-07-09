
USE shoppingCartDB;

-- Insert data into the User table
INSERT INTO user (first_name, last_name, email, address, phone_number, username, password, is_active) VALUES
('John', 'Doe', 'john.doe@email.com', '123 Main St', '123-456-7890', 'josephr2316', '$2a$10$ZLUHFUm06kcn.qDH7vLa2upvEbfrMNGCfeQTZ/eQ0ai9iaVaE7bsO', true),
('Jane', 'Doe', 'jane.doe@email.com', '456 Oak St', '987-654-3210', 'janedoe', '$2a$10$ZLUHFUm06kcn.qDH7vLa2upvEbfrMNGCfeQTZ/eQ0ai9iaVaE7bsO', true),
('Alice', 'Smith', 'alice.smith@email.com', '789 Elm St', '555-123-4567', 'alicesmith', '$2a$10$ZLUHFUm06kcn.qDH7vLa2upvEbfrMNGCfeQTZ/eQ0ai9iaVaE7bsO', true),
('Bob', 'Johnson', 'bob.johnson@email.com', '987 Pine St', '111-222-3333', 'bobjohnson', '$2a$10$ZLUHFUm06kcn.qDH7vLa2upvEbfrMNGCfeQTZ/eQ0ai9iaVaE7bsO', true),
('Eva', 'Miller', 'eva.miller@email.com', '234 Maple St', '444-555-6666', 'evamiller', '$2a$10$ZLUHFUm06kcn.qDH7vLa2upvEbfrMNGCfeQTZ/eQ0ai9iaVaE7bsO', true),
('Alex', 'Johnson', 'alex.johnson@email.com', '876 Birch St', '333-444-5555', 'alexjohnson', '$2a$10$ZLUHFUm06kcn.qDH7vLa2upvEbfrMNGCfeQTZ/eQ0ai9iaVaE7bsO', true),
('Sara', 'White', 'sara.white@email.com', '543 Cedar St', '666-777-8888', 'sarawhite', '$2a$10$ZLUHFUm06kcn.qDH7vLa2upvEbfrMNGCfeQTZ/eQ0ai9iaVaE7bsO', true),
('Admin1', 'Admin', 'admin1@email.com', '123 Admin St', '777-888-9999', 'admin1', '$2a$10$ZLUHFUm06kcn.qDH7vLa2upvEbfrMNGCfeQTZ/eQ0ai9iaVaE7bsO', true),
('Admin2', 'Admin', 'admin2@email.com', '456 Admin St', '999-111-2222', 'admin2', '$2a$10$ZLUHFUm06kcn.qDH7vLa2upvEbfrMNGCfeQTZ/eQ0ai9iaVaE7bsO', true),
('Admin3', 'Admin', 'admin3@email.com', '789 Admin St', '111-222-3333', 'admin3', '$2a$10$ZLUHFUm06kcn.qDH7vLa2upvEbfrMNGCfeQTZ/eQ0ai9iaVaE7bsO', true),
('User1', 'User', 'user1@email.com', '123 User St', '555-666-7777', 'user1', '$2a$10$ZLUHFUm06kcn.qDH7vLa2upvEbfrMNGCfeQTZ/eQ0ai9iaVaE7bsO', true),
('User2', 'User', 'user2@email.com', '456 User St', '888-999-0000', 'user2', '$2a$10$ZLUHFUm06kcn.qDH7vLa2upvEbfrMNGCfeQTZ/eQ0ai9iaVaE7bsO', true),
('Ethan', 'Taylor', 'ethan.taylor@example.com', '222 Oak St, Townsville', '555-444-3333', 'ethan_taylor', '$2a$10$ZLUHFUm06kcn.qDH7vLa2upvEbfrMNGCfeQTZ/eQ0ai9iaVaE7bsO', true),
('Ava', 'Anderson', 'ava.anderson@example.com', '456 Pine St, Villagetown', '111-222-3333', 'ava_anderson', '$2a$10$ZLUHFUm06kcn.qDH7vLa2upvEbfrMNGCfeQTZ/eQ0ai9iaVaE7bsO', true),
('Liam', 'Moore', 'liam.moore@example.com', '789 Cedar St, Hamletville', '444-555-6666', 'liam_moore', '$2a$10$ZLUHFUm06kcn.qDH7vLa2upvEbfrMNGCfeQTZ/eQ0ai9iaVaE7bsO', true),
('Isabella', 'Smith', 'isabella.smith@example.com', '123 Elm St, Boroughburg', '777-888-9999', 'isabella_smith', '$2a$10$ZLUHFUm06kcn.qDH7vLa2upvEbfrMNGCfeQTZ/eQ0ai9iaVaE7bsO', true),
('James', 'Brown', 'james.brown@example.com', '555 Cedar St, Hamletville', '444-555-6666', 'james_brown', '$2a$10$ZLUHFUm06kcn.qDH7vLa2upvEbfrMNGCfeQTZ/eQ0ai9iaVaE7bsO', true),
('Ella', 'Miller', 'ella.miller@example.com', '789 Maple St, Citytown', '333-444-5555', 'ella_miller', '$2a$10$ZLUHFUm06kcn.qDH7vLa2upvEbfrMNGCfeQTZ/eQ0ai9iaVaE7bsO', true),
('Benjamin', 'Wilson', 'benjamin.wilson@example.com', '101 Pine St, Villagetown', '999-888-7777', 'benjamin_wilson', '$2a$10$ZLUHFUm06kcn.qDH7vLa2upvEbfrMNGCfeQTZ/eQ0ai9iaVaE7bsO', true),
('Emma', 'Moore', 'emma.moore@example.com', '222 Cedar St, Hamletville', '444-555-6666', 'emma_moore', '$2a$10$ZLUHFUm06kcn.qDH7vLa2upvEbfrMNGCfeQTZ/eQ0ai9iaVaE7bsO', true),
('William', 'Smith', 'william.smith@example.com', '123 Elm St, Boroughburg', '777-888-9999', 'william_smith', '$2a$10$ZLUHFUm06kcn.qDH7vLa2upvEbfrMNGCfeQTZ/eQ0ai9iaVaE7bsO', true),
('Sophia', 'Brown', 'sophia.brown@example.com', '555 Oak St, Townsville', '222-333-4444', 'sophia_brown', '$2a$10$ZLUHFUm06kcn.qDH7vLa2upvEbfrMNGCfeQTZ/eQ0ai9iaVaE7bsO', true),
('Olivia', 'Johnson', 'olivia.johnson@example.com', '789 Pine St, Villagetown', '111-222-3333', 'olivia_johnson', '$2a$10$ZLUHFUm06kcn.qDH7vLa2upvEbfrMNGCfeQTZ/eQ0ai9iaVaE7bsO', true),
('Liam', 'Brown', 'liam.brown@example.com', '222 Cedar St, Hamletville', '444-555-6666', 'liam_brown', '$2a$10$ZLUHFUm06kcn.qDH7vLa2upvEbfrMNGCfeQTZ/eQ0ai9iaVaE7bsO', true),
('Charlotte', 'Taylor', 'charlotte.taylor@example.com', '555 Elm St, Boroughburg', '777-888-9999', 'charlotte_taylor', '$2a$10$ZLUHFUm06kcn.qDH7vLa2upvEbfrMNGCfeQTZ/eQ0ai9iaVaE7bsO', true),
('Mason', 'Wilson', 'mason.wilson@example.com', '123 Maple St, Citytown', '333-444-5555', 'mason_wilson', '$2a$10$ZLUHFUm06kcn.qDH7vLa2upvEbfrMNGCfeQTZ/eQ0ai9iaVaE7bsO', true),
('Ella', 'Moore', 'ella.moore@example.com', '789 Oak St, Townsville', '999-888-7777', 'ella_moore', '$2a$10$ZLUHFUm06kcn.qDH7vLa2upvEbfrMNGCfeQTZ/eQ0ai9iaVaE7bsO', true),
('Benjamin', 'Smith', 'benjamin.smith@example.com', '101 Pine St, Villagetown', '111-222-3333', 'benjamin_smith', '$2a$10$ZLUHFUm06kcn.qDH7vLa2upvEbfrMNGCfeQTZ/eQ0ai9iaVaE7bsO', true),
('Emma', 'Taylor', 'emma.taylor@example.com', '555 Oak St, Townsville', '222-333-4444', 'emma_taylor', '$2a$10$ZLUHFUm06kcn.qDH7vLa2upvEbfrMNGCfeQTZ/eQ0ai9iaVaE7bsO', true),
('Lucas', 'Miller', 'lucas.miller@example.com', '789 Cedar St, Hamletville', '444-555-6666', 'lucas_miller', '$2a$10$ZLUHFUm06kcn.qDH7vLa2upvEbfrMNGCfeQTZ/eQ0ai9iaVaE7bsO', true),
('Grace', 'Martin', 'grace.martin@example.com', '345 Pine St, Villagetown', '555-777-8888', 'grace_martin', '$2a$10$ZLUHFUm06kcn.qDH7vLa2upvEbfrMNGCfeQTZ/eQ0ai9iaVaE7bsO', true),
('Henry', 'Turner', 'henry.turner@example.com', '678 Elm St, Citytown', '777-999-0000', 'henry_turner', '$2a$10$ZLUHFUm06kcn.qDH7vLa2upvEbfrMNGCfeQTZ/eQ0ai9iaVaE7bsO', true);

-- Insert data into the roles table
INSERT INTO user_roles (user_id, role) VALUES
(1, 'ADMIN'),
(2, 'BUYER'),
(3, 'BUYER'),
(4, 'ADMIN'),
(5, 'BUYER'),
(6, 'ADMIN'),
(7, 'BUYER'),
(8, 'ADMIN'),
(9, 'BUYER'),
(10, 'ADMIN'),
(11, 'BUYER'),
(12, 'ADMIN'),
(13, 'BUYER'),
(14, 'ADMIN'),
(15, 'BUYER'),
(16, 'ADMIN'),
(17, 'BUYER'),
(18, 'ADMIN'),
(19, 'BUYER'),
(20, 'ADMIN'),
(21, 'BUYER'),
(22, 'ADMIN'),
(23, 'BUYER'),
(24, 'ADMIN'),
(25, 'BUYER'),
(26, 'ADMIN'),
(27, 'BUYER'),
(28, 'ADMIN'),
(29, 'BUYER'),
(30, 'ADMIN'),
(31, 'BUYER'),
(32, 'ADMIN');


-- Insert data into the Category table
INSERT INTO category (category_name, is_active) VALUES
('Electronics', true),
('Clothing', true),
('Books', true),
('Home and Kitchen', true),
('Sports and Outdoors', true),
('Toys and Games', true),
('Beauty and Personal Care', true),
('Automotive', true),
('Health and Household', true),
('Tools and Home Improvement', true),
('Grocery and Gourmet Food', true),
('Baby', true),
('Pet Supplies', true),
('Office Products', true),
('Industrial and Scientific', true),
('Software', true),
('Musical Instruments', true),
('Movies and TV', true),
('Kindle Store', true),
('Arts, Crafts and Sewing', true),
('Electronics Accessories', true),
('Cell Phones and Accessories', true),
('Clothing, Shoes and Jewelry', true),
('Home Audio and Theater', true),
('Computers and Accessories', true),
('Camera and Photo', true),
('Video Games', true),
('Home Improvement', true),
('Toys and Games', true),
('Books', true),
('Automotive Parts and Accessories', true),
('Beauty', true),
('Sports and Outdoors', true),
('Baby Products', true),
('Health and Personal Care', true),
('Tools and Home Improvement', true),
('Office and School Supplies', true),
('Furniture', true),
('Appliances', true),
('Patio, Lawn and Garden', true),
('CDs and Vinyl', true),
('Pet Food and Supplies', true),
('Jewelry', true),
('Watches', true),
('Handmade', true),
('Grocery', true),
('Luggage and Travel Gear', true),
('Industrial', true),
('Home Services', true),
('Automotive', true);


-- Insert data into the Product table
INSERT INTO product (product_name, description, price, stock, is_active, category_category_id) VALUES
('Smartphone X', 'High-end smartphone with latest features', 899.99, 50, true, 1),
('Laptop Pro', 'Thin and light laptop for productivity', 1299.99, 30, true, 1),
('Wireless Headphones', 'Over-ear headphones with noise cancellation', 149.99, 100, true, 1),
('Casual T-shirt', 'Comfortable cotton T-shirt for everyday wear', 19.99, 200, true, 2),
('Running Shoes Elite', 'Durable running shoes for fitness enthusiasts', 79.99, 50, true, 2),
('Bestseller Novel', 'Best-selling novel by a renowned author', 29.99, 20, true, 3),
('Premium Cookware Set', 'Premium stainless steel cookware set', 199.99, 10, true, 4),
('Outdoor Gas Grill', 'Gas-powered outdoor grill for barbecue parties', 299.99, 5, true, 4),
('Family Board Game', 'Family-friendly board game for entertainment', 39.99, 30, true, 5),
('Feature-Packed Smartwatch', 'Feature-packed smartwatch for fitness and notifications', 129.99, 15, true, 2),
('Stylish Leather Wallet', 'Stylish leather wallet for everyday use', 39.99, 25, true, 3),
('Durable Backpack', 'Durable backpack with multiple compartments', 49.99, 20, true, 4),
('Collapsible Grocery Basket', 'Collapsible grocery basket for convenient shopping', 14.99, 50, true, 5),
('Protective Safety Glasses', 'Protective safety glasses for various tasks', 9.99, 30, true, 6),
('Complete Home Cleaning Kit', 'Complete home cleaning kit for a tidy home', 29.99, 15, true, 7),
('Long-Lasting Car Air Freshener', 'Long-lasting car air freshener for a pleasant drive', 4.99, 100, true, 8),
('Comprehensive First Aid Kit', 'Comprehensive first aid kit for emergencies', 19.99, 10, true, 9),
('Gaming Console Mega', 'High-performance gaming console for ultimate gaming experience', 599.99, 10, true, 27),
('Portable Bluetooth Earbuds', 'Wireless earbuds with portable charging case', 79.99, 50, true, 1),
('Fitness Tracker Pro', 'Advanced fitness tracker for health monitoring', 49.99, 30, true, 11),
('Vintage Vinyl Record Player', 'Vintage-style record player for classic music lovers', 199.99, 15, true, 32),
('Smart Home Security System', 'Smart home security system with cameras and sensors', 349.99, 5, true, 28),
('Professional Chef s Knife', 'High-quality chef s knife for precision cutting', 89.99, 20, true, 4),
('Virtual Reality Headset', 'Immersive virtual reality headset for gaming and experiences', 299.99, 8, true, 1),
('Organic Baby Food Bundle', 'Organic baby food bundle for healthy infant nutrition', 24.99, 40, true, 32),
('Digital Drawing Tablet', 'Digital drawing tablet for artists and designers', 129.99, 10, true, 13),
('Solar-Powered Outdoor Lights', 'Solar-powered lights for outdoor illumination', 19.99, 30, true, 15),
('Professional Makeup Kit', 'Professional makeup kit for makeup enthusiasts', 69.99, 15, true, 7),
('High-Performance Mountain Bike', 'High-performance mountain bike for off-road adventures', 699.99, 5, true, 5),
('Digital Camera Kit', 'Complete digital camera kit for photography enthusiasts', 499.99, 15, true, 26),
('Smart Home Security System', 'Integrated smart home security system with cameras', 349.99, 8, true, 28),
('Wireless Charging Pad', 'Fast-charging wireless pad for compatible devices', 34.99, 40, true, 25);


-- Insert data into the Review table
INSERT INTO review (product_product_id, comment, like_dislike, user_user_id) VALUES
(1, 'Great product!', true, 1),
(2, 'The quality is amazing.', true, 2),
(3, 'Not satisfied with the purchase.', false, 3),
(4, 'Highly recommend this product.', true, 4),
(5, 'Average product, nothing special.', false, 5),
(6, 'Excellent service!', true, 6),
(7, 'Fast shipping and good packaging.', true, 7),
(8, 'Poor customer service experience.', false, 8),
(9, 'The product exceeded my expectations.', true, 9),
(10, 'Bad quality, won\'t buy again.', false, 10),
(11, 'Good value for money.', true, 11),
(12, 'Product arrived on time.', true, 12),
(13, 'Could be better.', false, 13),
(14, 'Satisfied with the purchase.', true, 14),
(15, 'Great customer support.', true, 15),
(16, 'Disappointed with the quality.', false, 16),
(17, 'Fast delivery and good condition.', true, 17),
(18, 'Not what I expected.', false, 18),
(19, 'Happy with the product.', true, 19),
(20, 'Product didn\'t meet expectations.', false, 20),
(21, 'Impressed with the service.', true, 21),
(22, 'Average experience.', false, 22),
(23, 'Would buy again.', true, 23),
(24, 'Needs improvement.', false, 24),
(25, 'Great value for the price.', true, 25),
(26, 'Product as described.', true, 26),
(27, 'Not worth the money.', false, 27),
(28, 'Excellent customer service.', true, 28),
(29, 'Quality could be better.', false, 29),
(30, 'Happy with the purchase.', true, 30),
(31, 'Average quality.', false, 31),
(32, 'Impressed with the product.', true, 32);


-- Insertar datos en la tabla ShoppingCart
INSERT INTO shopping_cart (user_user_id, total_price) VALUES
(1, 100.00),
(2, 75.50),
(3, 50.25),
(4, 120.75),
(5, 90.00),
(6, 30.50),
(7, 200.00),
(8, 45.75),
(9, 80.25),
(10, 150.50),
(11, 110.00),
(12, 95.25),
(13, 180.50),
(14, 60.75),
(15, 75.00),
(16, 120.25),
(17, 50.50),
(18, 160.75),
(19, 70.00),
(20, 130.25),
(21, 85.50),
(22, 40.25),
(23, 95.75),
(24, 110.50),
(25, 70.25),
(26, 120.00),
(27, 150.75),
(28, 55.50),
(29, 80.25),
(30, 125.75),
(31, 100.25),
(32, 140.50);


-- Insertar datos en la tabla cart_product
INSERT INTO cart_product (cart_id, product_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 4),
(2, 5),
(2, 6),
(3, 7),
(3, 8),
(3, 9),
(4, 10),
(4, 11),
(4, 12),
(5, 13),
(5, 14),
(5, 15),
(6, 16),
(6, 17),
(6, 18),
(7, 19),
(7, 20),
(7, 21),
(8, 22),
(8, 23),
(8, 24),
(9, 25),
(9, 26),
(9, 27),
(10, 28),
(10, 29),
(10, 30),
(11, 31),
(12, 32);



-- Insertar datos en la tabla orders
INSERT INTO orders (order_date, order_status, user_user_id) VALUES
('2023-11-01 10:30:00', 'DELIVERED', 1),
('2023-11-02 11:45:00', 'PROCESSING', 2),
('2023-11-03 09:15:00', 'PENDING', 3),
('2023-11-04 13:20:00', 'DELIVERED', 4),
('2023-11-05 14:00:00', 'CANCELLED', 5),
('2023-11-06 15:30:00', 'DELIVERED', 6),
('2023-11-07 08:45:00', 'PROCESSING', 7),
('2023-11-08 12:10:00', 'DELIVERED', 8),
('2023-11-09 16:45:00', 'PENDING', 9),
('2023-11-10 09:00:00', 'CANCELLED', 10),
('2023-11-11 11:20:00', 'DELIVERED', 11),
('2023-11-12 14:55:00', 'PROCESSING', 12),
('2023-11-13 10:05:00', 'DELIVERED', 13),
('2023-11-14 12:30:00', 'DELIVERED', 14),
('2023-11-15 15:15:00', 'PENDING', 15),
('2023-11-16 08:20:00', 'PROCESSING', 16),
('2023-11-17 11:40:00', 'DELIVERED', 17),
('2023-11-18 13:50:00', 'DELIVERED', 18),
('2023-11-19 14:25:00', 'CANCELLED', 19),
('2023-11-20 16:00:00', 'DELIVERED', 20),
('2023-11-21 09:10:00', 'PROCESSING', 21),
('2023-11-22 12:50:00', 'DELIVERED', 22),
('2023-11-23 14:15:00', 'DELIVERED', 23),
('2023-11-24 15:40:00', 'PENDING', 24),
('2023-11-25 08:55:00', 'PROCESSING', 25),
('2023-11-26 11:05:00', 'DELIVERED', 26),
('2023-11-27 13:30:00', 'DELIVERED', 27),
('2023-11-28 15:50:00', 'CANCELLED', 28),
('2023-11-29 16:25:00', 'DELIVERED', 29),
('2023-11-30 09:35:00', 'DELIVERED', 30),
('2023-12-01 12:15:00', 'PENDING', 31),
('2023-12-02 14:45:00', 'PROCESSING', 32);

-- Insertar datos en la tabla orders_product
INSERT INTO orders_product (orders_id, product_id) VALUES
-- Order 1
(1, 1), (1, 2), (1, 3),
-- Order 2
(2, 4), (2, 5),
-- Order 3
(3, 6), (3, 7), (3, 8),
-- Order 4
(4, 9), (4, 10),
-- Order 5
(5, 11), (5, 12),
-- Order 6
(6, 13), (6, 14), (6, 15),
-- Order 7
(7, 16), (7, 17),
-- Order 8
(8, 18), (8, 19), (8, 20),
-- Order 9
(9, 21), (9, 22),
-- Order 10
(10, 23), (10, 24),
-- Order 11
(11, 25), (11, 26), (11, 27),
-- Order 12
(12, 28), (12, 29),
-- Order 13
(13, 30), (13, 31), (13, 32),
-- Order 14
(14, 1), (14, 2),
-- Order 15
(15, 3), (15, 4), (15, 5),
-- Order 16
(16, 6), (16, 7),
-- Order 17
(17, 8), (17, 9), (17, 10),
-- Order 18
(18, 11), (18, 12),
-- Order 19
(19, 13), (19, 14), (19, 15),
-- Order 20
(20, 16), (20, 17),
-- Order 21
(21, 18), (21, 19), (21, 20),
-- Order 22
(22, 21), (22, 22),
-- Order 23
(23, 23), (23, 24),
-- Order 24
(24, 25), (24, 26), (24, 27),
-- Order 25
(25, 28), (25, 29),
-- Order 26
(26, 30), (26, 31), (26, 32),
-- Order 27
(27, 1), (27, 2),
-- Order 28
(28, 3), (28, 4), (28, 5),
-- Order 29
(29, 6), (29, 7),
-- Order 30
(30, 8), (30, 9), (30, 10),
-- Order 31
(31, 11), (31, 12),
-- Order 32
(32, 13), (32, 14), (32, 15);

-- Insertar datos en la tabla invoice
INSERT INTO invoice (total_amount, issue_date, order_order_id) VALUES
(99.99, '2023-01-01', 1),
(149.99, '2023-01-02', 2),
(199.99, '2023-01-03', 3),
(79.99, '2023-01-04', 4),
(129.99, '2023-01-05', 5),
(49.99, '2023-01-06', 6),
(179.99, '2023-01-07', 7),
(39.99, '2023-01-08', 8),
(69.99, '2023-01-09', 9),
(119.99, '2023-01-10', 10),
(89.99, '2023-01-11', 11),
(159.99, '2023-01-12', 12),
(199.99, '2023-01-13', 13),
(109.99, '2023-01-14', 14),
(79.99, '2023-01-15', 15),
(129.99, '2023-01-16', 16),
(149.99, '2023-01-17', 17),
(69.99, '2023-01-18', 18),
(119.99, '2023-01-19', 19),
(199.99, '2023-01-20', 20),
(89.99, '2023-01-21', 21),
(59.99, '2023-01-22', 22),
(129.99, '2023-01-23', 23),
(179.99, '2023-01-24', 24),
(99.99, '2023-01-25', 25),
(149.99, '2023-01-26', 26),
(79.99, '2023-01-27', 27),
(119.99, '2023-01-28', 28),
(69.99, '2023-01-29', 29),
(199.99, '2023-01-30', 30),
(89.99, '2023-01-31', 31),
(109.99, '2023-02-01', 32);