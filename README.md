# app_Medica

# Medical Data Management Platform

Esta es una plataforma de gestión de datos médicos desarrollada con **Spring Boot**, utilizando **PostgreSQL** como base de datos relacional y **MongoDB** para almacenar datos no estructurados. La plataforma permite gestionar información de pacientes, historiales médicos y datos no estructurados (imágenes, documentos, etc.).

---

## 📁 Estructura del Proyecto

El proyecto sigue el siguiente patrón de paquetes en Java:

```
App_Medica/
  pom.xml
  src/
  ├── main/
  │   ├── java/
  │   │   └── com/
  │   │       └── medicaldata/
  │   │           ├── config/                 # Configuración de bases de datos
  │   │           ├── model/                  # Modelos de datos
  │   │           │   ├── relational/          # Entidades relacionales (PostgreSQL)
  │   │           │   └── nosql/               # Documentos NoSQL (MongoDB)
  │   │           ├── repository/              # Repositorios de datos
  │   │           │   ├── relational/           # Repositorios JPA (PostgreSQL)
  │   │           │   └── nosql/                # Repositorios MongoDB
  │   │           ├── service/                 # Lógica de negocio
  │   │           ├── util/                    # Utilidades y helpers
  │   │           ├── web/                     # Controladores REST
  │   │           └── MedicalDataApplication.java # Clase principal
  │   └── resources/
  │       └── application.properties            # Configuración de la aplicación
  └──
```

---

## 🚀 Características

- **Gestión de Pacientes:** Registro, búsqueda y actualización de datos de pacientes.
- **Historiales Médicos:** Creación y consulta de historiales médicos asociados a pacientes.
- **Datos No Estructurados:** Almacenamiento de imágenes, documentos y otros datos médicos en MongoDB.
- **Consultas Avanzadas:** Búsquedas por términos, fechas y diagnóstico.

---

## ⚙️ Requisitos

- **Java 17** o superior
- **Spring Boot 3.x**
- **PostgreSQL 14** o superior
- **MongoDB 6.x** o superior
- **Maven** o **Gradle** para la gestión de dependencias

---

## 📦 Dependencias

Este proyecto utiliza las siguientes dependencias principales:

- **Spring Boot Starter Web** – Para crear APIs RESTful.
- **Spring Data JPA** – Para la integración con PostgreSQL.
- **Spring Data MongoDB** – Para la integración con MongoDB.
- **Spring Boot Starter Validation** – Para la validación de datos.
- **Lombok** – Para reducir el boilerplate de código (anotaciones como `@Getter`, `@Setter`).

---

## 🔧 Configuración

### 1. Configuración de Base de Datos

En el archivo `src/main/resources/application.properties`, configura las conexiones a PostgreSQL y MongoDB:

```properties
# PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/medicaldata
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# MongoDB
spring.data.mongodb.uri=mongodb://localhost:27017/medicaldata

# Configuración de servidor
server.port=8080

# Tamaño máximo de archivos
spring.servlet.multipart.max-file-size=20MB
spring.servlet.multipart.max-request-size=20MB
```

### 2. Configuración en `pom.xml`

Incluye las dependencias necesarias en tu archivo `pom.xml`:

```xml
<dependencies>
    <!-- Spring Boot Starter Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- Spring Data JPA para PostgreSQL -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
    </dependency>

    <!-- Spring Data MongoDB -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-mongodb</artifactId>
    </dependency>

    <!-- Lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>

    <!-- Validación -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
</dependencies>
```

---

## ▶️ Ejecución del Proyecto

1. **Clona el repositorio:**
   ```bash
   git clone https://github.com/tu_usuario/medical-data-management.git
   cd medical-data-management
   ```

2. **Configura las bases de datos:**
   - Crea una base de datos en **PostgreSQL**:
     ```sql
     CREATE DATABASE medicaldata;
     ```
   - Ejecuta **MongoDB** en el puerto predeterminado `27017`.

3. **Construye el proyecto con Maven:**
   ```bash
   mvn clean install
   ```

4. **Ejecuta la aplicación:**
   ```bash
   mvn spring-boot:run
   ```

5. **Accede a la API:**
   Abre tu navegador en `http://localhost:8080/api/v1`

---

## 📖 Documentación de la API

Puedes acceder a la documentación de la API usando **Swagger** o **Postman** para probar los endpoints disponibles. Algunos endpoints incluyen:

- **Pacientes:**
  - `POST /api/v1/patients` - Registrar un paciente
  - `GET /api/v1/patients/{id}` - Obtener paciente por ID
  - `GET /api/v1/patients/search` - Buscar pacientes por apellido o diagnóstico

- **Historiales Médicos:**
  - `POST /api/v1/medical-records` - Crear historial médico
  - `GET /api/v1/patients/{patientId}/medical-records` - Obtener historial de un paciente

- **Datos No Estructurados:**
  - `POST /api/v1/patients/{patientId}/unstructured-data` - Subir datos no estructurados
  - `GET /api/v1/unstructured-data/search` - Buscar datos por etiquetas

---

## 🧪 Pruebas

Para ejecutar las pruebas unitarias y de integración:

```bash
mvn test
```

---

## 📚 Tecnologías Utilizadas

- **Spring Boot** - Framework principal para la construcción de la aplicación.
- **PostgreSQL** - Base de datos relacional para datos estructurados.
- **MongoDB** - Base de datos NoSQL para datos no estructurados.
- **Spring Data JPA** - Integración con JPA para PostgreSQL.
- **Spring Data MongoDB** - Integración con MongoDB.
- **Lombok** - Para simplificar el código Java.
- **Maven** - Gestión de dependencias y construcción del proyecto.

---

## 📝 Notas y Mejoras Futuras

- Implementar **Swagger** para documentación automática de la API.
- Agregar **autenticación y autorización** con Spring Security.
- Mejorar el manejo de **excepciones globales**.
- Incluir **pruebas de integración** más completas.

---

## 👥 Contribuciones

¡Las contribuciones son bienvenidas! Si deseas colaborar:

1. Haz un fork del proyecto.
2. Crea una rama con una nueva característica o corrección de errores:
   ```bash
   git checkout -b feature/nueva-funcionalidad
   ```
3. Realiza tus cambios y haz un commit:
   ```bash
   git commit -m "Añadir nueva funcionalidad"
   ```
4. Envía un pull request para revisión.

---

## 📄 Licencia

Este proyecto está licenciado bajo la **MIT License**. Consulta el archivo `LICENSE` para más información.

---

## 🧑‍💻 Autor

Desarrollado por **Alithefool** 

---
