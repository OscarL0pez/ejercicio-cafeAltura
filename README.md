# Café de Altura

## Descripción general
Este proyecto es una API REST (backend) para una cafetería (Café de Altura) que permite gestionar cafés y pedidos, así como autenticar y autorizar a los usuarios (por ejemplo, con roles "USER" y "ADMIN"). Fue desarrollado como parte de mi formación como desarrollador backend, con el objetivo de practicar el uso de Spring Boot, Spring Security (con JWT), y la arquitectura en capas (por ejemplo, controladores, servicios, repositorios y DTOs). El proyecto resuelve el problema de automatizar la gestión de cafés y pedidos, ofreciendo endpoints seguros y validados para crear, actualizar, eliminar y listar cafés y pedidos.

## Tecnologías y herramientas utilizadas
### Backend
- **Java 17** (OpenJDK 17.0.2)
- **Spring Boot 3.2.3**
  - Spring Web
  - Spring Data JPA
  - Spring Security
  - Spring Validation
- **Spring Security 6.2.2** (con JWT para autenticación)
- **Spring Data JPA 3.2.3** (para persistencia)
- **H2 Database 2.2.224** (desarrollo) / **MySQL 8.0** (producción)
- **Maven 3.9.6** (gestión de dependencias)

### Frontend
- **HTML5** (estructura y contenido)
- **CSS3** (estilos y diseño responsive)
- **JavaScript** (interactividad y consumo de API)
- **Bootstrap 5.3** (framework CSS para diseño responsive)
- **Fetch API** (para peticiones HTTP al backend)
- **SweetAlert2** (para notificaciones y alertas)
- **Chart.js** (para gráficos y estadísticas)

### Herramientas de desarrollo
- **Lombok 1.18.30** (reducción de código boilerplate)
- **MapStruct 1.5.5** (mapeo entre objetos)
- **SpringDoc OpenAPI 2.3.0** (documentación de API)
- **JUnit 5.10.1** (pruebas unitarias)
- **Mockito 5.10.0** (mocking para pruebas)
- **JaCoCo 0.8.11** (cobertura de código)

### Patrones de diseño y arquitectura
- **Domain-Driven Design (DDD)**: Organización del código por dominios (coffee, order, user)
- **Arquitectura en capas**: Controller → Service → Repository
- **Patrones de diseño**:
  - Repository Pattern (acceso a datos)
  - Service Layer Pattern (lógica de negocio)
  - DTO Pattern (transferencia de datos)
  - Factory Pattern (creación de objetos)
  - Strategy Pattern (comportamientos intercambiables)
  - Observer Pattern (eventos del dominio)

## Diagrama de arquitectura
```
┌─────────────────────────────────────────────────────────────┐
│                      Cliente (Frontend)                      │
└───────────────────────────┬─────────────────────────────────┘
                            │ HTTP/HTTPS
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                    API REST (Café de Altura)                 │
│                                                             │
│  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐     │
│  │  Controller │    │   Service   │    │ Repository  │     │
│  │   Layer     │───▶│   Layer     │───▶│   Layer     │     │
│  └─────────────┘    └─────────────┘    └─────────────┘     │
│         │                  │                  │             │
│         │                  │                  │             │
│         ▼                  ▼                  ▼             │
│  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐     │
│  │     DTO     │    │   Domain    │    │  Database   │     │
│  │   Layer     │    │   Model     │    │   Layer     │     │
│  └─────────────┘    └─────────────┘    └─────────────┘     │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

## Estructura del proyecto
El proyecto sigue una arquitectura en capas y está organizado por dominios. A continuación se muestra la estructura detallada:

```
src/main/java/com/cafeteria/cafedealtura/
│
├── config/                          # Configuración global
│   ├── SecurityConfig.java         # Configuración de seguridad
│   └── DatabaseInitializer.java    # Inicialización de datos
│
├── controller/                      # Controladores REST
│   ├── AuthController.java         # Autenticación y registro
│   ├── CoffeeController.java       # Gestión de cafés
│   ├── OrderController.java        # Gestión de pedidos
│   └── UserController.java         # Gestión de usuarios
│
├── domain/                         # Lógica de negocio por dominio
│   │
│   ├── coffee/                    # Dominio de cafés
│   │   ├── dto/
│   │   │   ├── request/          # DTOs de entrada
│   │   │   │   ├── CreateCoffeeRequestDTO.java
│   │   │   │   └── UpdateCoffeeRequestDTO.java
│   │   │   └── response/         # DTOs de salida
│   │   │       └── CoffeeResponseDTO.java
│   │   ├── model/
│   │   │   └── Coffee.java       # Entidad Café
│   │   ├── repository/
│   │   │   └── CoffeeRepository.java
│   │   └── service/
│   │       └── CoffeeService.java
│   │
│   ├── order/                     # Dominio de pedidos
│   │   ├── dto/
│   │   │   ├── request/          # DTOs de entrada
│   │   │   │   ├── CreateOrderRequestDTO.java
│   │   │   │   └── UpdateOrderRequestDTO.java
│   │   │   └── response/         # DTOs de salida
│   │   │       └── OrderResponseDTO.java
│   │   ├── model/
│   │   │   ├── Order.java        # Entidad Pedido
│   │   │   ├── OrderItem.java    # Entidad Item de Pedido
│   │   │   └── OrderStatus.java  # Enum de estados
│   │   ├── repository/
│   │   │   └── OrderRepository.java
│   │   └── service/
│   │       └── OrderService.java
│   │
│   └── user/                      # Dominio de usuarios
│       ├── dto/
│       │   ├── auth/             # DTOs de autenticación
│       │   │   ├── LoginRequestDTO.java
│       │   │   └── LoginResponseDTO.java
│       │   ├── profile/          # DTOs de perfil
│       │   │   └── UserProfileDTO.java
│       │   └── response/         # DTOs de respuesta
│       │       └── UserResponseDTO.java
│       ├── model/
│       │   ├── User.java         # Entidad Usuario
│       │   └── Role.java         # Entidad Rol
│       ├── repository/
│       │   └── UserRepository.java
│       └── service/
│           └── UserService.java
│
├── security/                      # Configuración de seguridad
│   ├── JwtAuthenticationFilter.java
│   └── JwtTokenProvider.java
│
└── common/                       # Componentes comunes
    ├── exception/               # Excepciones personalizadas
    │   ├── BadRequestException.java
    │   ├── ResourceNotFoundException.java
    │   └── UnauthorizedException.java
    └── util/                   # Utilidades comunes
        └── ValidationUtils.java

src/main/resources/
├── application.properties       # Configuración de la aplicación
└── data.sql                    # Datos iniciales (opcional)
```

Cada dominio (coffee, order, user) sigue el patrón de diseño Domain-Driven Design (DDD) y contiene:
- **DTOs**: Objetos de transferencia de datos para las peticiones y respuestas
- **Modelos**: Entidades que representan los objetos del dominio
- **Repositorios**: Interfaces para el acceso a datos
- **Servicios**: Implementación de la lógica de negocio

Los controladores exponen los endpoints REST y utilizan los servicios de cada dominio para procesar las peticiones. La seguridad está implementada usando Spring Security con JWT para la autenticación y autorización.

## Configuración del proyecto

### application.properties
```properties
# Configuración del servidor
server.port=8080
server.servlet.context-path=/api

# Configuración de la base de datos (H2 para desarrollo)
spring.datasource.url=jdbc:h2:mem:cafedealtura
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# Configuración de JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Configuración de JWT
jwt.secret=your-secret-key-here
jwt.expiration=86400000

# Configuración de logging
logging.level.org.springframework.security=DEBUG
logging.level.com.cafeteria.cafedealtura=DEBUG
```

### Configuración de MySQL (producción)
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/cafedealtura
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
```

## Guía de instalación y ejecución

### Requisitos previos
- **Java 17 (o superior)** instalado (por ejemplo, OpenJDK o Oracle JDK).
- **Maven** (por ejemplo, versión 3.6 o superior) instalado.
- **Base de datos** (por ejemplo, H2 en memoria o MySQL) configurada (por ejemplo, en "application.properties" o "application.yml").
- **IDE (opcional)** (por ejemplo, IntelliJ IDEA, Eclipse, VS Code) para facilitar la edición y depuración.

### Instrucciones paso a paso
1. **Clonar el repositorio:**  
   (Por ejemplo, desde la terminal o desde el IDE) ejecuta:  
   git clone <url-del-repo>  
   (Reemplaza <url-del-repo> con la URL de tu repositorio.)

2. **Navegar al directorio del proyecto:**  
   (Por ejemplo, desde la terminal) ejecuta:  
   cd <ruta-del-proyecto>  
   (Reemplaza <ruta-del-proyecto> con la ruta donde se clonó el proyecto.)

3. **Compilar el proyecto:**  
   (Por ejemplo, desde la terminal) ejecuta:  
   mvn clean compile  
   (Esto compila el código y genera las clases en "target/classes".)

4. **Ejecutar la aplicación:**  
   (Por ejemplo, desde la terminal) ejecuta:  
   mvn spring-boot:run  
   (Esto inicia la aplicación en modo desarrollo, por defecto en "http://localhost:8080".)

5. **Probar la API:**  
   (Por ejemplo, usando Postman, cURL, o Swagger UI) puedes probar los endpoints (por ejemplo, "/api/coffees", "/api/orders", "/api/users/register", "/api/users/login", "/api/auth/**") para verificar que la API funciona correctamente.

### Solución de problemas comunes
1. **Error de conexión a la base de datos**
   - Verificar que la base de datos esté corriendo
   - Comprobar las credenciales en application.properties
   - Asegurar que el puerto no esté en uso

2. **Error de compilación**
   - Ejecutar `mvn clean install -U` para actualizar dependencias
   - Verificar la versión de Java (debe ser 17)
   - Comprobar que Maven esté correctamente instalado

3. **Error de autenticación**
   - Verificar que el token JWT sea válido
   - Comprobar que el usuario exista en la base de datos
   - Asegurar que las credenciales sean correctas

## Ejemplos de uso (endpoints)

### Autenticación
#### Registro de usuario
```http
POST /api/users/register
Content-Type: application/json

{
    "name": "Óscar",
    "email": "oscar@example.com",
    "password": "password123"
}
```
Respuesta (200 OK):
```json
{
    "id": 1,
    "name": "Óscar",
    "email": "oscar@example.com",
    "roles": ["ROLE_USER"]
}
```

#### Login
```http
POST /api/users/login
Content-Type: application/json

{
    "email": "oscar@example.com",
    "password": "password123"
}
```
Respuesta (200 OK):
```json
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "type": "Bearer",
    "user": {
        "id": 1,
        "name": "Óscar",
        "email": "oscar@example.com",
        "roles": ["ROLE_USER"]
    }
}
```

### Cafés
- **GET /api/coffees** (Listar todos los cafés, con paginación)  
  (Por ejemplo, "GET http://localhost:8080/api/coffees?page=0&size=10")
- **GET /api/coffees/{id}** (Obtener un café por su ID)  
  (Por ejemplo, "GET http://localhost:8080/api/coffees/1")
- **POST /api/coffees** (Crear un nuevo café, requiere rol "ADMIN")  
  (Por ejemplo, "POST http://localhost:8080/api/coffees" con un body JSON (por ejemplo, { "name": "Café de Altura", "description": "Café de origen único", "price": 2.5, "origin": "Colombia" }))
- **PUT /api/coffees/{id}** (Actualizar un café existente, requiere rol "ADMIN")  
  (Por ejemplo, "PUT http://localhost:8080/api/coffees/1" con un body JSON (por ejemplo, { "name": "Café de Altura (actualizado)", "description": "Café de origen único (actualizado)", "price": 3.0, "origin": "Colombia" }))
- **DELETE /api/coffees/{id}** (Eliminar un café, requiere rol "ADMIN")  
  (Por ejemplo, "DELETE http://localhost:8080/api/coffees/1")

### Pedidos
- **GET /api/orders** (Listar todos los pedidos, con paginación, requiere rol "ADMIN")  
  (Por ejemplo, "GET http://localhost:8080/api/orders?page=0&size=10")
- **GET /api/orders/{id}** (Obtener un pedido por su ID)  
  (Por ejemplo, "GET http://localhost:8080/api/orders/1")
- **POST /api/orders** (Crear un nuevo pedido, requiere autenticación)  
  (Por ejemplo, "POST http://localhost:8080/api/orders" con un body JSON (por ejemplo, { "userId": 1, "items": [ { "coffeeId": 1, "quantity": 2 } ] }))
- **PUT /api/orders/{id}** (Actualizar un pedido existente)  
  (Por ejemplo, "PUT http://localhost:8080/api/orders/1" con un body JSON (por ejemplo, { "userId": 1, "items": [ { "coffeeId": 1, "quantity": 3 } ], "status": "CONFIRMED" }))
- **DELETE /api/orders/{id}** (Eliminar un pedido)  
  (Por ejemplo, "DELETE http://localhost:8080/api/orders/1")

### Flujos típicos
1. **Registro y primer pedido**
   ```mermaid
   sequenceDiagram
       Client->>+API: POST /register
       API->>+DB: Guardar usuario
       DB-->>-API: Usuario creado
       API-->>-Client: Token JWT
       Client->>+API: POST /orders (con token)
       API->>+DB: Crear pedido
       DB-->>-API: Pedido creado
       API-->>-Client: Pedido confirmado
   ```

2. **Actualización de pedido**
   ```mermaid
   sequenceDiagram
       Client->>+API: PUT /orders/{id}
       API->>+DB: Verificar pedido
       DB-->>-API: Pedido encontrado
       API->>+DB: Actualizar pedido
       DB-->>-API: Pedido actualizado
       API-->>-Client: Pedido actualizado
   ```

## Seguridad

### Autenticación JWT
1. **Registro**: El usuario se registra con email y contraseña
2. **Login**: El servidor valida las credenciales y genera un token JWT
3. **Autorización**: El token se incluye en el header `Authorization: Bearer <token>`

### Roles y permisos
- **ROLE_USER**: 
  - Ver cafés
  - Crear pedidos
  - Ver sus propios pedidos
- **ROLE_ADMIN**:
  - Todas las operaciones de USER
  - Gestionar cafés (CRUD)
  - Ver todos los pedidos
  - Gestionar usuarios

### Buenas prácticas implementadas
- Contraseñas encriptadas con BCrypt
- Tokens JWT con expiración
- Validación de datos de entrada
- Protección contra CSRF
- Headers de seguridad (HSTS, XSS, etc.)

## Pruebas

### Ejecutar pruebas
```bash
# Ejecutar todas las pruebas
mvn test

# Ejecutar pruebas con cobertura
mvn verify

# Ejecutar pruebas específicas
mvn test -Dtest=CoffeeServiceTest
```

### Tipos de pruebas
1. **Pruebas unitarias**
   - Servicios
   - Controladores
   - Utilidades
2. **Pruebas de integración**
   - Repositorios
   - Endpoints REST
3. **Pruebas de seguridad**
   - Autenticación
   - Autorización

### Cobertura de código
- Objetivo: >80% de cobertura
- Reporte generado en: `target/site/jacoco/index.html`

## Despliegue

### Requisitos de producción
- Java 17 o superior
- MySQL 8.0
- Maven 3.9.x
- Servidor web (opcional: Nginx, Apache)

### Pasos para despliegue
1. **Preparación**
   ```bash
   # Compilar para producción
   mvn clean package -Pprod
   ```

2. **Configuración**
   - Crear base de datos MySQL
   - Configurar application-prod.properties
   - Establecer variables de entorno

3. **Ejecución**
   ```bash
   # Ejecutar en producción
   java -jar target/cafedealtura-1.0.0.jar --spring.profiles.active=prod
   ```

### Monitoreo
- Endpoints de salud: `/api/health`
- Métricas: `/api/metrics`
- Logs: Configurados en `logback-spring.xml`

## Contribución

### Guía de contribución
1. Fork el repositorio
2. Crear una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir un Pull Request

### Convenciones de código
- Seguir las convenciones de Java
- Documentar con Javadoc
- Escribir pruebas unitarias
- Mantener la cobertura de código

### Proceso de revisión
1. Revisión de código
2. Pruebas automáticas
3. Verificación de cobertura
4. Aprobación del mantenedor

## Autor
Desarrollado por Óscar, como parte de mi formación como desarrollador backend.

## Licencia
Este proyecto se distribuye bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## Aviso de uso educativo
Este proyecto se ha desarrollado con fines educativos. Se recomienda:
- Revisar la configuración de seguridad antes de usar en producción
- Personalizar las credenciales y secretos
- Implementar monitoreo y logging apropiados
- Realizar pruebas de carga y seguridad

## Estructura del proyecto
```
cafedealtura/
├── src/                           # Backend (Spring Boot)
│   └── main/
│       ├── java/
│       │   └── com/cafeteria/cafedealtura/
│       │       ├── config/        # Configuración global
│       │       ├── controller/    # Controladores REST
│       │       ├── domain/        # Lógica de negocio
│       │       ├── security/      # Configuración de seguridad
│       │       └── common/        # Componentes comunes
│       │
│       └── resources/
│           ├── static/           # Archivos estáticos del frontend
│           │   ├── css/         # Estilos CSS
│           │   │   ├── styles.css
│           │   │   └── bootstrap.min.css
│           │   ├── js/          # Scripts JavaScript
│           │   │   ├── auth.js      # Lógica de autenticación
│           │   │   ├── coffee.js    # Gestión de cafés
│           │   │   ├── order.js     # Gestión de pedidos
│           │   │   └── utils.js     # Utilidades comunes
│           │   └── img/         # Imágenes
│           │
│           └── templates/       # Plantillas HTML
│               ├── index.html           # Página principal
│               ├── login.html           # Página de login
│               ├── register.html        # Página de registro
│               ├── coffee/
│               │   ├── list.html        # Lista de cafés
│               │   └── detail.html      # Detalle de café
│               ├── order/
│               │   ├── list.html        # Lista de pedidos
│               │   └── create.html      # Crear pedido
│               └── user/
│                   └── profile.html     # Perfil de usuario
│
└── pom.xml                        # Configuración de Maven
```

### Integración Frontend-Backend
El frontend se integra con el backend de la siguiente manera:

1. **Autenticación**:
   - El frontend maneja el login/registro mediante formularios HTML
   - Las credenciales se envían al backend mediante Fetch API
   - El token JWT recibido se almacena en localStorage
   - Todas las peticiones subsecuentes incluyen el token en el header

2. **Gestión de Cafés**:
   - Listado de cafés con paginación
   - Detalles de cada café con imágenes
   - Formularios para crear/editar cafés (solo admin)
   - Filtros y búsqueda de cafés

3. **Gestión de Pedidos**:
   - Carrito de compras en el frontend
   - Creación de pedidos con validación
   - Seguimiento del estado de pedidos
   - Historial de pedidos del usuario

4. **Características del Frontend**:
   - Diseño responsive con Bootstrap
   - Validación de formularios en cliente
   - Manejo de errores y notificaciones
   - Gráficos para estadísticas (admin)
   - Interfaz adaptativa según rol de usuario

### Ejemplo de integración (JavaScript)
```javascript
// Ejemplo de autenticación
async function login(email, password) {
    try {
        const response = await fetch('/api/users/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ email, password })
        });
        
        if (!response.ok) {
            throw new Error('Error en la autenticación');
        }
        
        const data = await response.json();
        localStorage.setItem('token', data.token);
        window.location.href = '/coffee/list.html';
    } catch (error) {
        Swal.fire('Error', error.message, 'error');
    }
}

// Ejemplo de petición autenticada
async function getCoffees(page = 0) {
    try {
        const token = localStorage.getItem('token');
        const response = await fetch(`/api/coffees?page=${page}`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        
        if (!response.ok) {
            throw new Error('Error al obtener cafés');
        }
        
        const data = await response.json();
        displayCoffees(data.content);
    } catch (error) {
        Swal.fire('Error', error.message, 'error');
    }
}
```
