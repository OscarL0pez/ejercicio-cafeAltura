-- Configuración de codificación
SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;
SET character_set_connection=utf8mb4;

-- Limpiar la tabla si existe
DELETE FROM order_items;
DELETE FROM orders;
DELETE FROM customers;
DELETE FROM cafes;

-- Resetear los autoincrementos
ALTER TABLE order_items AUTO_INCREMENT = 1;
ALTER TABLE orders AUTO_INCREMENT = 1;
ALTER TABLE customers AUTO_INCREMENT = 1;
ALTER TABLE cafes AUTO_INCREMENT = 1;

-- Insertar cafés de especialidad con IDs fijos
INSERT INTO cafes (id, nombre, descripcion, precio, origen) VALUES
    (1, 'Café Americano', 'Café negro suave con agua caliente', 2.50, 'Colombia'),
    (2, 'Café Latte', 'Espresso con leche cremosa y espuma', 3.50, 'Brasil'),
    (3, 'Cappuccino', 'Espresso con leche espumada y cacao', 3.00, 'Italia'),
    (4, 'Moka', 'Espresso con chocolate y leche', 3.75, 'Etiopía'),
    (5, 'Café de Altura', 'Café de especialidad de altura', 4.00, 'Perú'),
    (6, 'Flat White', 'Espresso con leche microespumada', 3.25, 'Australia'),
    (7, 'Cold Brew', 'Café infusionado en frío por 24 horas', 4.50, 'Costa Rica'),
    (8, 'Café Turco', 'Café molido finamente con especias', 3.50, 'Turquía'),
    (9, 'Café Irlandés', 'Café con whisky y crema batida', 5.00, 'Irlanda'),
    (10, 'Café Vienés', 'Café con crema batida y chocolate', 4.25, 'Austria');

-- Insertar clientes de ejemplo con IDs fijos
INSERT INTO customers (id, nombre) VALUES
    (1, 'Juan Pérez'),
    (2, 'María García'),
    (3, 'Carlos López'),
    (4, 'Ana Martínez'),
    (5, 'Roberto Sánchez'); 