-- ===============================
-- User Service - DEV Seed Data
-- ===============================

-- Passwords below are BCrypt hash of "123456"

DO $$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM users) THEN

    INSERT INTO users (first_name, last_name, email, password, role) VALUES
      ('James',  'James',  'james@test.com',  '$2b$10$NBDbjw75y9SnG1xnKxfnZeZ1GS9eaLylxHW8WBGe1Y3Ku0nKbzZx6', 'STUDENT'),
      ('Penny',  'Penny',  'penny@test.com',  '$2b$10$NBDbjw75y9SnG1xnKxfnZeZ1GS9eaLylxHW8WBGe1Y3Ku0nKbzZx6', 'STUDENT'),
      ('Nez',    'Nez',    'nez@test.com',    '$2b$10$NBDbjw75y9SnG1xnKxfnZeZ1GS9eaLylxHW8WBGe1Y3Ku0nKbzZx6', 'STUDENT'),
      ('Laily',  'Laily',  'laily@test.com',  '$2b$10$NBDbjw75y9SnG1xnKxfnZeZ1GS9eaLylxHW8WBGe1Y3Ku0nKbzZx6', 'INSTRUCTOR');

  END IF;
END $$;
