# PRUEBA TÉCNICA - LAURA CABRERA - DESARROLLADOR JUNIOR

# Descripcion
El proyecto desarrollado es un Sistema de Ventas full-stack que permite gestionar productos, vendedores y ventas mediante una aplicación web interactiva. Para su construcción, se utilizó Java con Spring Boot en el backend, trabajando con una base de datos SQL Server y siguiendo la arquitectura MVC para garantizar un código organizado y escalable. En el frontend, se empleó Vue 3 con TypeScript para crear una interfaz dinámica que interactúa con la API REST.

En este proyecto se implementó:

- Base de datos en SQL Server para almacenar productos, vendedores y ventas.

- Backend en Java con Spring Boot, siguiendo el patrón MVC.

- API REST para la gestión de operaciones CRUD.

- Frontend con Vue 3 y TypeScript para la interacción del usuario.

- Funcionalidades para listar, crear, actualizar y eliminar productos, vendedores y ventas.

- Integración completa entre frontend y backend para actualización en tiempo real

Incluye funciones para:
- Listar productos, vendedores y ventas.
- Crear,  editar y  eliminar registros.
- Relacionar ventas con productos y vendedores.
- Manejar fechas y cantidades.

---

##  Tecnologías utilizadas

### Frontend
- [Vue 3](https://vuejs.org/)
- [TypeScript](https://www.typescriptlang.org/)
- [Axios](https://axios-http.com/) para consumo de API
- [Vite](https://vitejs.dev/) como empaquetador
- Componentes reutilizables (`Modal.vue`, formularios, etc.)

### Backend
- [Java JDK 21](https://openjdk.org/projects/jdk/21/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [SQL Server](https://www.microsoft.com/sql-server/)
- API RESTful

---

##  Estructura del proyecto
```bash
sistemaVentas/
│
├── backend/ # API REST (Java Spring Boot)
│ ├── src/
│ │ ├── controllers/
│ │ ├── models/
│ │ ├── repositories/
│ │ └── services/
│ └── pom.xml
│
└── frontend/ # Código Vue 3 + CSS
├── src/
│ ├── components/ # Componentes reutilizables (Modal.vue, etc.)
│ ├── views/ # Vistas (ProductosView.vue, VendedoresView.vue, VentasView.vue)
│ ├── App.vue
│ └── main.ts
└── vite.config.ts
```

---

##  Instalación

1. **Clonar repositorio**
   ```bash
   git clone https://github.com/usuario/sistemaVentas.git
   cd sistemaVentas
   ```
2.Instalar dependencias del frontend
   ```bash
    cd frontend
    npm install
   ```
3.Configurar backend (Java JDK 21 + SQL Server)

- Asegurarse de tener instalado Java 21 y configurado en el PATH.

- Crear la base de datos en SQL Server:

  ```sql
  CREATE DATABASE sistema_ventas;
  ```

- Configurar el archivo application.properties o application.yml:
   ```bash
  spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=sistema_ventas
  spring.datasource.username=tu_usuario
  spring.datasource.password=tu_contraseña
  spring.jpa.hibernate.ddl-auto=update
   ```

4.Instalar dependencias del backend
   ```bash
    cd ../backend
    mvn install
   ```
---

# Ejecución
 - Backend
   ```bash
    cd backend
    mvn spring-boot:run
   ```
  - Frontend
   ```bash
    cd frontend
    npm run dev
   ```

## Accede a:

Frontend → http://localhost:5173

Backend → http://localhost:8080/api

---


## Endpoints principales (API REST)

Productos

- GET /api/productos → Lista todos los productos

- POST /api/productos → Crea un producto

- PUT /api/productos/:id → Actualiza un producto

- DELETE /api/productos/:id → Elimina un producto

Vendedores

- GET /api/vendedores

- POST /api/vendedores

- PUT /api/vendedores/:id

- DELETE /api/vendedores/:id

Ventas

- GET /api/ventas

- POST /api/ventas

- PUT /api/ventas/:id

- DELETE /api/ventas/:id

---

## Pruebas técnicas

Este proyecto cumple con:

- CRUD completo para tres entidades (productos, vendedores, ventas).

- Uso de modales para creación y edición.

- Integración Vue 3 + TypeScript.

- Mapeo y normalización de datos desde la API.

- Confirmaciones antes de eliminar registros.

- Backend en Java 21 + Spring Boot + SQL Server.

---

## Autor
Proyecto desarrollado por Laura Cabrera para la prueba técnica del puesto de Desarrollador Junior.
