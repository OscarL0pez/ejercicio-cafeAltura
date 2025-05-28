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

| Método | Endpoint | Descripción | Cuerpo de la Petición | Respuesta |
|--------|----------|-------------|----------------------|-----------|
| GET | `/api/cafes` | Obtener todos los cafés | - | Lista de cafés |
| GET | `/api/cafes/{id}` | Obtener un café por ID | - | Café |
| POST | `/api/cafes` | Crear un nuevo café | `{ "nombre": "string", "descripcion": "string", "precio": number, "origen": "string" }` | Café creado |
| PUT | `/api/cafes/{id}` | Actualizar un café completo | `{ "nombre": "string", "descripcion": "string", "precio": number, "origen": "string" }` | Café actualizado |
| PATCH | `/api/cafes/{id}` | Actualizar parcialmente un café | `{ "nombre": "string", "descripcion": "string", "precio": number, "origen": "string" }` | Café actualizado |
| DELETE | `/api/cafes/{id}` | Eliminar un café | - | Sin contenido |

#### Ejemplos de Peticiones

1. Crear un café:
```http
POST /api/cafes
Content-Type: application/json

{
    "nombre": "Café Colombiano",
    "descripcion": "Café suave y aromático de las montañas de Colombia",
    "precio": 9.99,
    "origen": "Colombia"
}
```

2. Actualizar parcialmente un café:
```http
PATCH /api/cafes/1
Content-Type: application/json

{
    "precio": 12.99,
    "descripcion": "Nueva descripción del café"
}
```

3. Actualizar un café completo:
```http
PUT /api/cafes/1
Content-Type: application/json

{
    "nombre": "Café Colombiano Premium",
    "descripcion": "Café de alta calidad de las montañas de Colombia",
    "precio": 12.99,
    "origen": "Colombia"
}
```

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

## Características

- Gestión de cafés, clientes y pedidos
- Paginación en todos los endpoints de listado
- Validación de datos
- Manejo de errores global
- Documentación de API con Swagger/OpenAPI
- Interfaz web para visualización de datos

## Endpoints

### Cafés
- `GET /api/cafes` - Listar todos los cafés (paginado)
  - Parámetros: `page` (número de página, 0-based), `size` (tamaño de página)
  - Ejemplo: `GET /api/cafes?page=0&size=10`
- `GET /api/cafes/{id}` - Obtener un café por ID
- `POST /api/cafes` - Crear un nuevo café
- `PUT /api/cafes/{id}` - Actualizar un café
- `DELETE /api/cafes/{id}` - Eliminar un café

### Clientes
- `GET /api/customers` - Listar todos los clientes (paginado)
  - Parámetros: `page` (número de página, 0-based), `size` (tamaño de página)
  - Ejemplo: `GET /api/customers?page=0&size=10`
- `GET /api/customers/{id}` - Obtener un cliente por ID
- `POST /api/customers` - Crear un nuevo cliente
- `PUT /api/customers/{id}` - Actualizar un cliente
- `DELETE /api/customers/{id}` - Eliminar un cliente

### Pedidos
- `GET /api/orders` - Listar todos los pedidos (paginado)
  - Parámetros: `page` (número de página, 0-based), `size` (tamaño de página)
  - Ejemplo: `GET /api/orders?page=0&size=10`
- `GET /api/orders/{id}` - Obtener un pedido por ID
- `GET /api/orders/customer/{customerId}` - Obtener pedidos por cliente
- `POST /api/orders/{customerId}` - Crear un nuevo pedido
- `DELETE /api/orders/{id}` - Eliminar un pedido

## Paginación

Todos los endpoints de listado (`GET /api/cafes`, `GET /api/customers`, `GET /api/orders`) soportan paginación. La respuesta incluye:

- `content`: Lista de elementos en la página actual
- `currentPage`: Número de página actual (0-based)
- `totalPages`: Número total de páginas
- `totalElements`: Número total de elementos
- `pageSize`: Tamaño de cada página
- `hasNext`: Indica si hay una página siguiente
- `hasPrevious`: Indica si hay una página anterior

Ejemplo de respuesta paginada:
```json
{
    "content": [...],
    "currentPage": 0,
    "totalPages": 5,
    "totalElements": 42,
    "pageSize": 10,
    "hasNext": true,
    "hasPrevious": false
}
```

## Interfaz Web

La aplicación incluye una interfaz web para visualizar los datos de forma paginada. Accede a través de:
- `http://localhost:8080` - Interfaz principal
- `http://localhost:8080/api/cafes` - API de cafés
- `http://localhost:8080/api/customers` - API de clientes
- `http://localhost:8080/api/orders` - API de pedidos

## Tecnologías

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- H2 Database (desarrollo)
- MySQL (producción)
- Maven
- Bootstrap 5 (interfaz web)

## Requisitos

- Java 17 o superior
- Maven 3.6 o superior
- MySQL 8.0 (para producción)

## Instalación

1. Clonar el repositorio
2. Configurar la base de datos en `application.properties`
3. Ejecutar `./mvnw spring-boot:run`
4. Acceder a `http://localhost:8080`

## Desarrollo

Para ejecutar en modo desarrollo:
```bash
./mvnw spring-boot:run
```

Para ejecutar los tests:
```bash
./mvnw test
```
