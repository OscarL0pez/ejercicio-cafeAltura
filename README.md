# Café de Altura - API REST

API REST para una cafetería que permite gestionar clientes, cafés y pedidos.

## Tecnologías Utilizadas

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- MySQL 8
- Maven
- Lombok (opcional)

## Estructura del Proyecto

```
src/main/java/com/cafeteria/cafedealtura/
├── controller/         # Controladores REST
├── model/             # Entidades JPA
├── repository/        # Repositorios JPA
├── service/          # Lógica de negocio
└── exception/        # Manejo de excepciones
```

## Modelos de Datos

### Cafe
- ID (Long)
- nombre (String)
- descripcion (String)
- precio (double)
- origen (String)

### Customer
- ID (Long)
- nombre (String)

### Order
- ID (Long)
- customer (Customer)
- fecha (LocalDateTime)
- items (List<OrderItem>)
- total (double)

### OrderItem
- ID (Long)
- order (Order)
- cafe (Cafe)
- nombre (String)
- precio (double)
- cantidad (int)
- subtotal (double)

## Endpoints Disponibles

### Cafés (`/api/cafes`)
- `GET /` - Listar todos los cafés
- `GET /{id}` - Obtener café por ID
- `POST /` - Crear nuevo café
- `PUT /{id}` - Actualizar café
- `DELETE /{id}` - Eliminar café

### Clientes (`/api/customers`)
- `GET /` - Listar todos los clientes
- `GET /{id}` - Obtener cliente por ID
- `POST /` - Crear nuevo cliente
- `PUT /{id}` - Actualizar cliente
- `DELETE /{id}` - Eliminar cliente

### Órdenes (`/api/orders`)
- `GET /` - Listar todas las órdenes
- `GET /{id}` - Obtener orden por ID
- `GET /customer/{customerId}` - Obtener órdenes por cliente
- `POST /{customerId}` - Crear nueva orden
- `DELETE /{id}` - Eliminar orden

## Ejemplos de Uso

### Crear una Orden
```http
POST /api/orders/1
Content-Type: application/json

[
    {
        "cafe": {
            "id": 1
        },
        "nombre": "Café Americano",
        "precio": 2.50,
        "cantidad": 2
    }
]
```

### Crear un Cliente
```http
POST /api/customers
Content-Type: application/json

{
    "nombre": "Juan Pérez"
}
```

### Crear un Café
```http
POST /api/cafes
Content-Type: application/json

{
    "nombre": "Café Expresso",
    "descripcion": "Café concentrado servido en taza pequeña",
    "precio": 2.00,
    "origen": "Italia"
}
```

## Configuración de la Base de Datos

El proyecto utiliza MySQL como base de datos. La configuración se encuentra en `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/cafedealtura?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=Oscar90%
```

## Datos Iniciales

El proyecto incluye datos de ejemplo que se cargan automáticamente al iniciar:

### Cafés (IDs 1-10)
1. Café Americano (Colombia)
2. Café Latte (Brasil)
3. Cappuccino (Italia)
4. Moka (Etiopía)
5. Café de Altura (Perú)
6. Flat White (Australia)
7. Cold Brew (Costa Rica)
8. Café Turco (Turquía)
9. Café Irlandés (Irlanda)
10. Café Vienés (Austria)

### Clientes (IDs 1-5)
1. Juan Pérez
2. María García
3. Carlos López
4. Ana Martínez
5. Roberto Sánchez

## Características Implementadas

- ✅ Gestión completa de CRUD para cafés, clientes y órdenes
- ✅ Validación de datos con anotaciones JSR-380
- ✅ Manejo de excepciones personalizado
- ✅ IDs fijos para datos de ejemplo
- ✅ Cálculo automático de totales en órdenes
- ✅ Relaciones bidireccionales entre entidades
- ✅ Persistencia de datos entre reinicios
- ✅ Codificación UTF-8 para caracteres especiales

## Requisitos

- Java 17 o superior
- MySQL 8
- Maven

## Ejecución del Proyecto

1. Clonar el repositorio
2. Configurar la base de datos MySQL
3. Ejecutar:
   ```bash
   mvn spring-boot:run
   ```
4. La aplicación estará disponible en `http://localhost:8080`

## Pruebas

Puedes probar los endpoints usando Postman o cualquier cliente HTTP. Ejemplos de URLs:

- `http://localhost:8080/api/cafes`
- `http://localhost:8080/api/customers`
- `http://localhost:8080/api/orders`

## Notas Adicionales

- Los IDs de cafés y clientes son fijos (1-10 para cafés, 1-5 para clientes)
- Las órdenes se crean con IDs autoincrementales
- Los totales se calculan automáticamente
- La fecha de las órdenes se establece automáticamente al momento de la creación
