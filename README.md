# Café de Altura ☕

Aplicación REST desarrollada con **Spring Boot** para gestionar cafés, clientes y pedidos mediante operaciones CRUD, con almacenamiento en memoria y validaciones manuales.

---

## 🚀 Características principales
- CRUD completo de **Café**, **Cliente** y **Pedido**
- Almacenamiento en memoria (`Map<Long, ...>`) sin base de datos
- Validaciones manuales de datos y relaciones
- Respuestas HTTP adecuadas según la operación
- Estructura limpia con capas: `controller`, `service`, `model`, `repository`
- Ordenación de pedidos por fecha
- Ejemplo de uso con [Postman](https://www.postman.com/) o `curl`

---

## 📦 Modelos principales  aa

### Café
```json
{
  "id": 1,
  "nombre": "Café Colombia",
  "descripcion": "Suave y aromático",
  "precio": 9.5,
  "origen": "Colombia"
}
```

### Cliente
```json
{
  "id": 1,
  "nombre": "Juan Pérez"
}
```

### Pedido
```json
{
  "id": 1,
  "idCliente": 1,
  "nombreCliente": "Juan Pérez",
  "fecha": "2024-05-22T10:48:49.654",
  "items": [
    { "idCafe": 1, "nombre": "Café Colombia", "precio": 9.5, "cantidad": 2, "subtotal": 19.0 }
  ],
  "total": 19.0
}
```

---

## 🌐 Endpoints disponibles

### Café
- `GET /cafes` → Lista todos los cafés
- `POST /cafes` → Crea un nuevo café
- `PUT /cafes/{id}` → Reemplaza un café existente
- `PATCH /cafes/{id}` → Modifica parcialmente un café
- `DELETE /cafes/{id}` → Elimina un café

### Cliente
- `GET /customers` → Lista todos los clientes
- `POST /customers` → Crea un nuevo cliente
- `PUT /customers/{id}` → Actualiza un cliente
- `DELETE /customers/{id}` → Elimina un cliente

### Pedido
- `GET /orders` → Lista todos los pedidos (ordenados por fecha descendente)
- `POST /orders?customerId=1` → Crea un pedido para el cliente 1
- `GET /orders/{id}` → Obtiene un pedido por ID
- `GET /orders/customer/{customerId}` → Lista pedidos de un cliente

---

## 🛡️ Validaciones implementadas
- No se permite crear clientes sin nombre
- No se permite crear cafés sin nombre, descripción, origen o con precio <= 0
- No se permite crear pedidos vacíos
- No se permite agregar cafés a un pedido con cantidad <= 0
- Se valida la existencia de cliente y cafés al crear un pedido

---

## 🧪 Ejemplo de uso (con Postman o curl)

### Crear un café
```bash
curl -X POST http://localhost:8080/cafes -H "Content-Type: application/json" -d '{
  "nombre": "Café Guatemala",
  "descripcion": "Intenso y floral",
  "precio": 11.25,
  "origen": "Guatemala"
}'
```

### Crear un cliente
```bash
curl -X POST http://localhost:8080/customers -H "Content-Type: application/json" -d '{
  "nombre": "Juan Pérez"
}'
```

### Crear un pedido
```bash
curl -X POST "http://localhost:8080/orders?customerId=1" -H "Content-Type: application/json" -d '[
  { "idCafe": 1, "cantidad": 2 },
  { "idCafe": 2, "cantidad": 1 }
]'
```

---

## 🗃️ Estructura del proyecto
```
com.cafeteria.cafedealtura
├── controller
│   ├── CafeController.java
│   ├── CustomerController.java
│   └── OrderController.java
├── model
│   ├── Cafe.java
│   ├── Customer.java
│   ├── Order.java
│   └── OrderItem.java
├── repository
│   ├── CafeRepository.java
│   ├── CustomerRepository.java
│   └── OrderRepository.java
└── service
    ├── CafeService.java
    ├── CustomerService.java
    └── OrderService.java
```

---

## 🚀 Tecnologías usadas
- Java 17
- Spring Boot 3.x
- Maven
- Spring Web

---

## 📚 Autor
Este proyecto ha sido desarrollado por **Óscar** como parte de su formación práctica en programación Java y desarrollo web con Spring Boot.
