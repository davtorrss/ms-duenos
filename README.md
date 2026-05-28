# Sistema de Gestión Veterinaria: VetNova

Proyecto de microservicios desarrollado para la gestión de dueños y mascotas de una clínica veterinaria.

## 🚀 Arquitectura
El sistema se compone de dos microservicios independientes:
1. **ms-duenos**: Gestiona la información de los propietarios.
2. **ms-mascotas**: Gestiona el registro de mascotas y valida la existencia del dueño mediante comunicación inter-servicio.

## 🛠️ Requisitos previos
Para ejecutar este proyecto, necesitas tener instalado:
- **Java 17 o superior**.
- **Maven**.
- **MySQL Server** (o MySQL Workbench).
- **Postman** (para probar los endpoints).

## 🗄️ Configuración de Base de Datos
Antes de ejecutar los servicios, debes crear las bases de datos en tu servidor MySQL:

1. Crea la base de datos para dueños: `db_vetnova_duenos`.
2. Crea la base de datos para mascotas: `db_vetnova_mascotas`.
3. *(Opcional)* Ejecuta el script `database_setup.sql` incluido en este repositorio para cargar las tablas iniciales.

*Nota: Asegúrate de que las credenciales en `application.properties` (usuario: `root`, password: ``) coincidan con tu configuración local.*

## 🏃‍♂️ Cómo ejecutar el proyecto

### 1. Iniciar Microservicio Dueños
- Navega a la carpeta `ms-duenos`.
- Ejecuta el comando: `mvn spring-boot:run`
- El servicio estará disponible en: `http://localhost:8082`.

### 2. Iniciar Microservicio Mascotas
- Navega a la carpeta `ms-mascotas`.
- Ejecuta el comando: `mvn spring-boot:run`
- El servicio estará disponible en: `http://localhost:8081`.

## 📡 Endpoints Principales

### Dueños (`/api/duenos`)
* `GET /api/duenos`: Lista todos los dueños.
* `POST /api/duenos`: Crea un nuevo dueño.
* `PUT /api/duenos/{id}`: Actualiza datos de un dueño.
* `DELETE /api/duenos/{id}`: Elimina un dueño.

### Mascotas (`/api/mascotas`)
* `GET /api/mascotas`: Lista todas las mascotas.
* `POST /api/mascotas`: Crea una mascota (requiere un `duenoId` válido).
* `PUT /api/mascotas/{id}`: Actualiza una mascota.
* `DELETE /api/mascotas/{id}`: Elimina una mascota.

## 📝 Notas del desarrollador
- El sistema utiliza `WebClient` para la comunicación entre servicios.
- Las validaciones de datos (`@NotBlank`, `@NotNull`) están activas en ambos servicios para asegurar la integridad de los datos.