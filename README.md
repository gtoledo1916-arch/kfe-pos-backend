# KFE-POS Backend

Backend API REST para un sistema Point of Sale (Punto de Venta) desarrollado con Spring Boot, JWT y MySQL.

---

## Tecnologías

| Tecnología | Versión | Uso |
|------------|---------|-----|
| **Java** | 21 | Lenguaje principal |
| **Spring Boot** | 3.3.2 | Framework base |
| **Spring Security** | - | Autenticación y autorización |
| **Spring Data JPA** | - | Persistencia y ORM |
| **MySQL** | 8.x | Base de datos |
| **JWT (jjwt)** | 0.11.5 | Tokens de autenticación |
| **Maven** | 3.6+ | Gestión de dependencias |

### Dependencias principales

- `spring-boot-starter-web` - API REST
- `spring-boot-starter-data-jpa` - JPA/Hibernate
- `spring-boot-starter-security` - Seguridad
- `spring-boot-starter-validation` - Validación de datos
- `mysql-connector-j` - Driver MySQL
- `jjwt-api`, `jjwt-impl`, `jjwt-jackson` - JWT

---

## Requisitos previos

1. **Java 21** (JDK 21)
2. **Maven 3.6+** (o Maven Wrapper incluido)
3. **MySQL 8.x** con el servicio en ejecución

---

## Instalación

### 1. Clonar el repositorio

```bash
git clone <url-del-repositorio>
cd demo
```

### 2. Crear la base de datos

En MySQL, ejecuta:

```sql
CREATE DATABASE kfe_pos CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 3. Configurar la conexión

Edita `src/main/resources/application.properties` y ajusta las credenciales:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/kfe_pos
spring.datasource.username=root
spring.datasource.password=TU_PASSWORD
```

### 4. Instalar dependencias

```bash
mvn clean install
```

### 5. Ejecutar la aplicación

```bash
mvn spring-boot:run
```

O con Maven Wrapper:

```bash
# Windows
mvnw.cmd spring-boot:run

# Linux / macOS
./mvnw spring-boot:run
```

La API quedará disponible en **http://localhost:8081**

---

## Tablas de la base de datos

Hibernate crea las tablas automáticamente (`ddl-auto=update`). Resumen del modelo:

### users

| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | BIGINT (PK) | ID autoincremental |
| username | VARCHAR | Nombre de usuario |
| apellido_paterno | VARCHAR | Apellido paterno |
| apellido_materno | VARCHAR | Apellido materno |
| email | VARCHAR (unique) | Correo electrónico |
| password | VARCHAR | Contraseña (encriptada) |
| role | ENUM | ADMIN, CAJERO, GERENTE |

### category

| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | BIGINT (PK) | ID autoincremental |
| name | VARCHAR (unique) | Nombre de la categoría |

### products

| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | BIGINT (PK) | ID autoincremental |
| name | VARCHAR | Nombre del producto |
| price | DECIMAL | Precio base |
| description | TEXT | Descripción |
| image_url | VARCHAR | URL de la imagen |
| stock | INT | Stock disponible |
| category_id | BIGINT (FK) | Categoría del producto |

### product_variants

| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | BIGINT (PK) | ID autoincremental |
| product_id | BIGINT (FK) | Producto asociado |
| size | VARCHAR | Tamaño (ej: Grande, Mediano) |
| price | DECIMAL | Precio de la variante |
| stock | INT | Stock de la variante |

### sales

| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | BIGINT (PK) | ID autoincremental |
| cajero_id | BIGINT (FK) | Usuario cajero |
| status | ENUM | COMPLETADA, CANCELADA |
| cancelado_por_id | BIGINT (FK, nullable) | Usuario que canceló |
| fecha_cancelacion | DATETIME | Fecha de cancelación |
| created_at | DATETIME | Fecha de creación |
| subtotal | DECIMAL | Subtotal de la venta |
| iva | DECIMAL | Impuesto IVA |
| total | DECIMAL | Total de la venta |

### sale_details

| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | BIGINT (PK) | ID autoincremental |
| sale_id | BIGINT (FK) | Venta asociada |
| product_id | BIGINT (FK) | Producto vendido |
| quantity | INT | Cantidad |
| unit_price | DECIMAL | Precio unitario |
| subtotal | DECIMAL | Subtotal línea |
| iva | DECIMAL | IVA línea |
| total | DECIMAL | Total línea |

### Relaciones

- **Product** → **Category** (ManyToOne)
- **ProductVariant** → **Product** (ManyToOne)
- **Sale** → **User** (cajero) (ManyToOne)
- **Sale** → **User** (cancelado_por) (ManyToOne, nullable)
- **Sale** → **SaleDetail** (OneToMany)
- **SaleDetail** → **Sale**, **Product** (ManyToOne)

---

## API REST

### Autenticación

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/auth/login` | Inicio de sesión (JWT) |
| POST | `/api/auth/register` | Registrar usuario (ADMIN) |

### Endpoints principales

- **Usuarios**: `/api/users`
- **Productos**: `/api/products`
- **Variantes**: `/api/products/{id}/variants`
- **Ventas**: `/api/sales`

Se usa autenticación JWT con la cabecera: `Authorization: Bearer <token>`

---

## Estructura del proyecto

```
demo/
├── pom.xml
├── mvnw, mvnw.cmd
├── src/main/java/org/kfe/api/demo/
│   ├── PosApplication.java
│   ├── config/
│   ├── controller/
│   ├── dto/
│   ├── entity/
│   ├── exception/
│   ├── repository/
│   ├── security/
│   └── service/
└── src/main/resources/
    └── application.properties
```

---

## Licencia

Proyecto interno PROSUR.
