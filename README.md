# ☕️ CafedeAltura

¡Bienvenido a CafedeAltura!  
Sistema de gestión de cafetería con autenticación JWT, CRUD de productos, pedidos y usuarios.

---

## 📋 Tabla de Contenidos

- [Instalación](#-instalación)
- [Demo](#-demo)
- [Uso](#-uso)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Tecnologías](#-tecnologías)
- [Contribución](#-contribución)
- [Contacto](#-contacto)

---

## 🛠️ Instalación

1. **Clona el repositorio:**
   ```bash
   git clone <URL-DEL-REPO>
   cd cafedealtura
   ```

2. **Compila el proyecto:**
   ```bash
   mvn clean install
   ```

3. **Ejecuta la aplicación:**
   ```bash
   mvn spring-boot:run
   ```

4. **Accede a la app:**
   - Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
   - H2 Console: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

---




## 🧪 Uso

- **Login de ejemplo:**
  - Email: `admin@cafe.com`
  - Contraseña: `Admin123` (recuerda la mayúscula)

- **Probar la API:**
  1. Ve a Swagger UI.
  2. Usa `/api/auth/login` para obtener tu token JWT.
  3. Haz clic en "Authorize" e ingresa:  
     ```
     Bearer <tu_token>
     ```
  4. Prueba los endpoints protegidos.

---

## 📁 Estructura del Proyecto

```
cafedealtura/
│
├── src/
│   ├── main/
│   │   ├── java/com/cafeteria/cafedealtura/
│   │   │   ├── domain/      # Lógica de negocio (usuarios, pedidos, café)
│   │   │   ├── security/    # Seguridad y JWT
│   │   │   ├── controller/  # Controladores REST
│   │   │   └── ...          
│   │   └── resources/
│   │       ├── application.properties
│   │       └── data.sql
│   └── test/
│
├── pom.xml
└── README.md
```

---

## 🚀 Demo

![Pantalla de login](docs/12345.png)
_Pantalla de login de la aplicación web_

![Swagger UI](docs/12323.png)
_Interfaz de Swagger UI para probar la API REST_

![Consola H2](docs/1234567.png)
_Visualización de la base de datos en la consola H2_

---

## 🧰 Tecnologías

- Java 17
- Spring Boot 3
- Spring Security (JWT)
- H2 Database
- Maven
- Swagger/OpenAPI

---

## 🤝 Contribución

¿Quieres mejorar el proyecto?  
¡Haz un fork, crea una rama y envía tu PR!

---

## 📬 Contacto

- Autor: [Oscar Lopez](https://github.com/OscarL0pez)
- Profesor: [Angel David Macho]

---
