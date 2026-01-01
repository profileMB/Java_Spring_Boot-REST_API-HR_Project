
-- Demo data for H2 in-memory database (BCrypt hashed passwords)

DROP TABLE IF EXISTS employees;
 
CREATE TABLE employees (
  id BIGINT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(100) NOT NULL,
  mail VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(60) NOT NULL
);
 
INSERT INTO employees (first_name, last_name, mail, password) VALUES
  ('Jean', 'DUPONT', 'jeandupont@mail.com', '$2a$10$/IIqugvENfCFjDa4opuAre8/SqTpCcvpSp7.nV/Z8Mr8SepG70DPm'),
  ('Marie', 'BERNARD', 'mariebernard@mail.com', '$2a$10$lmq5yes0tb1x9Wz1rXqGCe8503eM5oECcFqG2M.ysQs4bkI7ZZesi'),
  ('Pierre', 'MARTIN', 'pierremartin@mail.com', '$2a$10$rrpoxrwjpRgDCjsp0w40WehVfx2n4L6.xB9QJw6o4HUjWkWGav5Ou');