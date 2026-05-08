SELECT 1;
-- TO RUN default scrips for another tables, use the specific .sql files
-- insert into constant(name) values ('true'), ('false');

DELETE FROM person;
DELETE FROM unit;

-- Clean up and insert admin user with password '123' (bcrypt hash)
DELETE FROM users WHERE username = 'admin';
INSERT INTO users(username, password) VALUES ('admin', '$2a$10$GIkct28TsCYqOgF4h/KefOVtViBQhhSFdCiyKAmxGYz2tX.JYVEfa');
--TODO add refrences on for entities {{package}} : com.omsentities.history