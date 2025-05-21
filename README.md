# Café de Altura ☕

Aplicación REST desarrollada con **Spring Boot** para gestionar cafés mediante operaciones CRUD (Create, Read, Update, Delete).

Este proyecto forma parte de un ejercicio de prácticas con el objetivo de construir una API REST básica, sin base de datos y con validaciones manuales.

---

## 📌 Características principales

- CRUD completo del recurso `Café`
- Almacenamiento en memoria (`Map<Long, Cafe>`)
- Validaciones **sin usar librerías externas**
- Respuestas HTTP adecuadas según la operación
- Estructura limpia con capas: `controller`, `service`, `model`

---

## 📦 Modelo `Café`

```json
{
  "id": 1,
  "nombre": "Café Colombia",
  "descripcion": "Suave y aromático",
  "precio": 9.5,
  "origen": "Colombia"
}
```

---

## 🌐 Endpoints disponibles

### ✔️ `GET /cafes`
> Devuelve la lista de todos los cafés.

📥 Sin parámetros

📤 Respuesta: `200 OK` con lista de cafés

---

### ➕ `POST /cafes`
> Crea un nuevo café.

📥 Body JSON:
```json
{
  "nombre": "Café Colombia",
  "descripcion": "Suave y aromático",
  "precio": 9.5,
  "origen": "Colombia"
}
```

📤 Respuesta:
- `201 Created` si es válido
- `400 Bad Request` si faltan campos o precio es inválido

---

### 🔁 `PUT /cafes/{id}`
> Reemplaza completamente un café existente.

📥 Body JSON (con todos los campos obligatorios)

📤 Respuesta:
- `200 OK` si se actualiza correctamente
- `400 Bad Request` si hay campos inválidos
- `404 Not Found` si no existe el café

---

### ✏️ `PATCH /cafes/{id}`
> Modifica parcialmente un café (uno o varios campos).

📥 Body JSON:
```json
{
  "precio": 8.75
}
```

📤 Respuesta:
- `200 OK` si se actualiza
- `404 Not Found` si el café no existe

---

### ❌ `DELETE /cafes/{id}`
> Elimina un café por su ID.

📤 Respuesta:
- `204 No Content` si se elimina
- `404 Not Found` si no existe

---

## 🛡️ Validaciones manuales

- Campos obligatorios: `nombre`, `descripcion`, `precio > 0`, `origen`
- No se permite `null`, vacío o precio negativo
- Implementadas directamente en el controlador, sin anotaciones externas

---

## 🧪 Cómo probar la API

Puedes usar [Postman](https://www.postman.com/) o `curl` para hacer pruebas.

### Ejemplo POST válido:
```json
POST http://localhost:8080/cafes
Body:
{
  "nombre": "Café Guatemala",
  "descripcion": "Intenso y con notas florales",
  "precio": 11.25,
  "origen": "Guatemala"
}
```

---

## 🚀 Tecnologías usadas

- Java 17
- Spring Boot 3.x
- Maven
- Spring Web

---

## 🗃️ Estructura del proyecto

```
com.cafeteria.cafedealtura
├── controller
│   └── CafeController.java
├── model
│   └── Cafe.java
└── service
    └── CafeService.java
```

---

## 📚 Autor

Este proyecto ha sido desarrollado por **Óscar** como parte de su formación práctica en programación Java y desarrollo web con Spring Boot.
