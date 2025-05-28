-- Crear rol ADMIN si no existe
INSERT INTO roles (name) SELECT 'ADMIN' WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ADMIN');

-- Crear usuario admin (password: admin123, BCrypt hash)
INSERT INTO users (name, email, password) VALUES ('Administrador', 'admin@cafe.com', '$2a$10$7EqJtq98hPqEX7fNZaFWoOa5rE6i6rL2F6Qe5p8l6y1ZC1Q1Yy1yG');

-- Asignar rol ADMIN al usuario admin
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r WHERE u.email = 'admin@cafe.com' AND r.name = 'ADMIN'; 