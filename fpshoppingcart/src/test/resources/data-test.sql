 -- Test data for unit tests
-- This file contains minimal data needed for tests to pass

-- Insert test user
INSERT INTO users (first_name, last_name, email, address, phone_number, username, password, active, created_at, updated_at) VALUES
('Test', 'User', 'test@test.com', '123 Test St', '1234567890', 'testuser', '$2a$10$ZLUHFUm06kcn.qDH7vLa2upvEbfrMNGCfeQTZ/eQ0ai9iaVaE7bs0', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert test user roles
INSERT INTO user_roles (user_id, role) VALUES
(1, 'USER');

-- Insert test category
INSERT INTO categories (category_name, description, active, created_at, updated_at) VALUES
('Test Category', 'Test category description', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert test product
INSERT INTO products (product_name, description, price, stock, active, category_id, created_at, updated_at) VALUES
('Test Product', 'Test product description', 99.99, 10, true, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
