# Sistema de Gestión de Empleados - JPA Hibernate

Un sistema de gestión de empleados basado en Java construido con JPA (Jakarta Persistence API) y el framework ORM Hibernate.

## Características

- **Gestión de Empleados**: Crear, leer, actualizar y eliminar registros de empleados
- **Gestión de Departamentos**: Administrar departamentos organizacionales
- **Gestión de Proyectos**: Manejar asignaciones y seguimiento de proyectos
- **Soporte de Base de Datos**: Compatible con bases de datos H2 (en memoria) y MySQL
- **JPA/Hibernate**: ORM moderno con Jakarta Persistence API

## Stack Tecnológico

- **Java**: 20
- **JPA**: Jakarta Persistence API 3.1.0
- **Hibernate**: 6.2.7.Final
- **Base de Datos**: H2 (en memoria) / MySQL 8.0.33
- **Herramienta de Construcción**: Maven

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/
│   │   └── org/example/
│   │       ├── entiity/          # Entidades JPA
│   │       │   ├── Empleado.java
│   │       │   ├── Departamento.java
│   │       │   └── Proyecto.java
│   │       ├── service/          # Servicios de Lógica de Negocio
│   │       │   ├── EmpleadoService.java
│   │       │   ├── DepartamentoService.java
│   │       │   └── ProyectoService.java
│   │       └── Main.java         # Punto de Entrada de la Aplicación
│   └── resources/
│       └── META-INF/
│           └── persistence.xml   # Configuración JPA
```

## Relaciones entre Entidades

- **Empleado**: Representa empleados con información personal y laboral
- **Departamento**: Departamentos organizacionales a los que pertenecen los empleados
- **Proyecto**: Proyectos a los que pueden ser asignados los empleados

## Comenzando

### Prerrequisitos

- Java 20 o superior
- Maven 3.6 o superior
- MySQL (opcional, la base de datos H2 en memoria se usa por defecto)

### Instalación

1. **Clonar el repositorio**
   ```bash
   git clone <url-del-repositorio>
   cd employee-jpa
   ```

2. **Compilar el proyecto**
   ```bash
   mvn clean compile
   ```

3. **Ejecutar la aplicación**
   ```bash
   mvn exec:java -Dexec.mainClass="org.example.Main"
   ```

### Configuración de Base de Datos

El proyecto soporta tanto bases de datos H2 (en memoria) como MySQL:

#### Base de Datos H2 (Por Defecto)
- Configurada automáticamente para desarrollo
- No requiere configuración adicional
- Los datos se pierden cuando la aplicación se detiene

#### Base de Datos MySQL
1. Crear una base de datos MySQL
2. Actualizar `persistence.xml` con las credenciales de tu base de datos
3. Asegurar que el servidor MySQL esté ejecutándose

## Ejemplos de Uso

La aplicación proporciona operaciones CRUD completas para:

- **Empleados**: Agregar, actualizar, eliminar y consultar información de empleados
- **Departamentos**: Gestionar estructura organizacional
- **Proyectos**: Manejar asignaciones y seguimiento de proyectos

## Configuración

### Configuración JPA (`persistence.xml`)
Ubicado en `src/main/resources/META-INF/persistence.xml`, este archivo contiene:
- Configuración de conexión a base de datos
- Configuración de Hibernate
- Configuración de escaneo de entidades

### Configuración Maven (`pom.xml`)
Contiene todas las dependencias del proyecto:
- Jakarta Persistence API
- Hibernate Core
- Base de Datos H2
- Conector MySQL

## Pruebas

Para ejecutar pruebas (si están disponibles):
```bash
mvn test
```

## Documentación de la API

La aplicación proporciona clases de servicio con las siguientes operaciones principales:

### EmpleadoService
- Crear, leer, actualizar, eliminar empleados
- Consultar empleados por diversos criterios
- Gestionar relaciones de empleados

### DepartamentoService
- Operaciones de gestión de departamentos
- Asignación de empleados a departamentos

### ProyectoService
- Operaciones de gestión de proyectos
- Asignación de empleados a proyectos

## Contribuir

1. Hacer fork del repositorio
2. Crear una rama de características (`git checkout -b feature/caracteristica-increible`)
3. Hacer commit de tus cambios (`git commit -m 'Agregar alguna característica increíble'`)
4. Hacer push a la rama (`git push origin feature/caracteristica-increible`)
5. Abrir un Pull Request

## Licencia

Este proyecto está licenciado bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

## Soporte

Si encuentras algún problema o tienes preguntas:
1. Revisar los problemas existentes
2. Crear un nuevo problema con información detallada
3. Contactar al equipo de desarrollo

## Historial de Versiones

- **v1.0-SNAPSHOT**: Lanzamiento inicial con operaciones CRUD básicas
  - Gestión de empleados
  - Gestión de departamentos
  - Gestión de proyectos
  - Integración JPA/Hibernate 