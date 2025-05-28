-- Configuración de codificación
SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;
SET character_set_connection=utf8mb4;

-- Limpiar la tabla si existe
DELETE FROM order_items;
DELETE FROM orders;
DELETE FROM customers;
DELETE FROM cafes;

-- Resetear los autoincrementos (usando TRUNCATE para asegurar el reinicio)
TRUNCATE TABLE order_items;
TRUNCATE TABLE orders;
TRUNCATE TABLE customers;
TRUNCATE TABLE cafes;

-- Recrear las tablas con la configuración correcta de AUTO_INCREMENT
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS cafes;

CREATE TABLE cafes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    precio DOUBLE NOT NULL,
    origen VARCHAR(255)
);

CREATE TABLE customers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL
);

CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    fecha DATETIME NOT NULL,
    total DOUBLE NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(id)
);

CREATE TABLE order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    cafe_id BIGINT NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    precio DOUBLE NOT NULL,
    cantidad INT NOT NULL,
    subtotal DOUBLE NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (cafe_id) REFERENCES cafes(id)
);

-- Insertar cafés de especialidad con IDs fijos
INSERT INTO cafes (nombre, descripcion, precio, origen) VALUES
    ('Café Americano', 'Café negro suave con agua caliente', 2.50, 'Colombia'),
    ('Café Latte', 'Espresso con leche cremosa y espuma', 3.50, 'Brasil'),
    ('Cappuccino', 'Espresso con leche espumada y cacao', 3.00, 'Italia'),
    ('Moka', 'Espresso con chocolate y leche', 3.75, 'Etiopía'),
    ('Café de Altura', 'Café de especialidad de altura', 4.00, 'Perú'),
    ('Flat White', 'Espresso con leche microespumada', 3.25, 'Australia'),
    ('Cold Brew', 'Café infusionado en frío por 24 horas', 4.50, 'Costa Rica'),
    ('Café Turco', 'Café molido finamente con especias', 3.50, 'Turquía'),
    ('Café Irlandés', 'Café con whisky y crema batida', 5.00, 'Irlanda'),
    ('Café Vienés', 'Café con crema batida y chocolate', 4.25, 'Austria');

-- Insertar clientes de ejemplo
INSERT INTO customers (nombre) VALUES
    ('Juan Pérez'),
    ('María García'),
    ('Carlos López'),
    ('Ana Martínez'),
    ('Roberto Sánchez');

-- Insertar pedidos de ejemplo
INSERT INTO orders (customer_id, fecha, total) VALUES
    (1, '2024-03-15 10:30:00', 11.25),
    (2, '2024-03-15 11:45:00', 8.50),
    (3, '2024-03-15 14:20:00', 15.75);

-- Insertar items de los pedidos
INSERT INTO order_items (order_id, cafe_id, nombre, precio, cantidad, subtotal) VALUES
    -- Items del pedido 1 (Juan Pérez)
    (1, 1, 'Café Americano', 2.50, 2, 5.00),
    (1, 3, 'Cappuccino', 3.00, 1, 3.00),
    (1, 5, 'Café de Altura', 4.00, 1, 4.00),
    
    -- Items del pedido 2 (María García)
    (2, 2, 'Café Latte', 3.50, 1, 3.50),
    (2, 4, 'Moka', 3.75, 1, 3.75),
    (2, 6, 'Flat White', 3.25, 1, 3.25),
    
    -- Items del pedido 3 (Carlos López)
    (3, 7, 'Cold Brew', 4.50, 2, 9.00),
    (3, 9, 'Café Irlandés', 5.00, 1, 5.00),
    (3, 10, 'Café Vienés', 4.25, 1, 4.25); 