# Proyecto Semestral - API VetNova

## Descripción del Proyecto
Este repositorio contiene el backend del sistema de gestión para la clínica veterinaria VetNova, desarrollado para la asignatura de Desarrollo FullStack 1. El proyecto está construido bajo una arquitectura de microservicios usando Spring Boot y Java 21.

El desarrollo sigue estrictamente el patrón de diseño CSR (Controller-Service-Repository) e implementa Spring Data JPA con MySQL para la persistencia de datos.

## Equipo de Trabajo
* David Torrealba
* Adriano Contretas
* Diego Torres 

## Listado de Microservicios
El proyecto se compone de los siguientes microservicios:
1. ms-Auth (puerto 8081): seguridad y autenticación
2. ms-duenos (Puerto 8082): Registro y validación de datos de clientes/dueños.
3. ms-mascotas (Puerto 8083): Registro de mascotas

## API Gateway y Enrutamiento
Las peticiones externas son administradas y redirigidas a través de un API Gateway centralizado.
* Puerto local del Gateway: 8080
* Ruta base para Dueños: http://localhost:8080/api/duenos 

## Documentación Técnica (Swagger)
La documentación de los endpoints, códigos de respuesta y modelos de datos (DTOs) está implementada con OpenAPI 3.0. Una vez levantados los servicios, se puede acceder a las interfaces mediante:
* Swagger ms-duenos: http://localhost:8082/swagger-ui.html


## Instrucciones de Ejecución
Para desplegar el proyecto en un entorno local, seguir estos pasos:
1. Clonar el repositorio.
2. Asegurar que el motor de base de datos MySQL esté corriendo en el puerto 3306 (mediante XAMPP, Docker, etc.).
3. Crear las bases de datos vacías correspondientes  (db_vetnova_duenos). Las tablas se generarán automáticamente gracias a la configuración de Hibernate.
4. Compilar las dependencias del proyecto utilizando Maven.
5. Ejecutar la clase principal de cada microservicio de manera independiente, y finalmente levantar el servicio de API Gateway.

## Pruebas Unitarias y Cobertura
El proyecto incluye un set de pruebas unitarias desarrolladas con JUnit 5 y Mockito.
* Las pruebas están diseñadas aislando la capa de base de datos mediante mocks.
* Se sigue el patrón de diseño AAA (Arrange-Act-Assert) / Given-When-Then.
* Los tests cubren la lógica de negocio y el manejo de excepciones, asegurando una cobertura superior al 80%. Para ejecutar la batería de pruebas, utilizar el comando: mvn test