![BookVerse Logo](./assets/bookverse-banner.png)

<div align="center">

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.4-green?style=for-the-badge&logo=spring-boot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-13+-blue?style=for-the-badge&logo=postgresql)
![Maven](https://img.shields.io/badge/Maven-3.8+-red?style=for-the-badge&logo=apache-maven)

**Sistema de Gestión de Libros Interactivo**  
*Tu biblioteca digital personal con acceso a miles de libros*

[🚀 Características](#-características) • [🛠️ Tecnologías](#️-tecnologías) • [📦 Instalación](#-instalación) • [🎯 Uso](#-uso) • [🏗️ Arquitectura](#️-arquitectura)

</div>

---

## 🌟 Descripción

**BookVerse** es una aplicación de consola desarrollada con Spring Boot que te permite explorar, buscar y gestionar un catálogo personal de libros. La aplicación se conecta a la API de Gutenberg para obtener información de miles de libros y te permite almacenar tus favoritos en una base de datos PostgreSQL.

### ✨ ¿Qué hace especial a BookVerse?

- 🔍 **Búsqueda inteligente**: Encuentra libros por título con búsqueda en tiempo real
- 📊 **Gestión completa**: Almacena libros, autores y metadatos en base de datos
- 🌍 **Multiidioma**: Soporte para libros en diferentes idiomas
- 📈 **Estadísticas**: Visualiza número de descargas y popularidad
- 🎯 **Filtros avanzados**: Busca por autores, años y idiomas
- 💾 **Persistencia**: Tus datos se mantienen seguros en PostgreSQL

---

## 🚀 Características

### 📖 Gestión de Libros
- Búsqueda de libros por título en la API de Gutenberg
- Almacenamiento local de libros favoritos
- Visualización de todos los libros registrados
- Información detallada: título, autores, idiomas, descargas

### 👨‍💼 Gestión de Autores
- Registro automático de autores al guardar libros
- Búsqueda de autores por año de nacimiento/fallecimiento
- Visualización de autores vivos en años específicos
- Información biográfica completa

### 🌐 Filtros y Búsquedas
- Filtrado de libros por idioma
- Búsqueda de autores por período histórico
- Navegación intuitiva por consola
- Resultados organizados y legibles

---

## 🛠️ Tecnologías

| Categoría | Tecnología | Versión |
|-----------|------------|---------|
| **Backend** | Spring Boot | 3.5.4 |
| **Lenguaje** | Java | 17 |
| **Base de Datos** | PostgreSQL | 13+ |
| **ORM** | Hibernate/JPA | - |
| **Build Tool** | Maven | 3.8+ |
| **JSON Processing** | Jackson | 2.16.0 |
| **Utilities** | Lombok | - |

---

## 📦 Instalación

### Prerrequisitos

- Java 17 o superior
- Maven 3.8+
- PostgreSQL 13+
- Git

### Pasos de instalación

1. **Clona el repositorio**
   ```bash
   git clone https://github.com/tu-usuario/BookVerse.git
   cd BookVerse
   ```

2. **Configura la base de datos**
   ```bash
   # Crea una base de datos PostgreSQL
   createdb bookverse_db
   ```

3. **Configura las variables de entorno**
   ```bash
   # Crea un archivo .env o configura las variables de entorno
   export DB_HOST=localhost
   export DB_NAME=bookverse_db
   export DB_USERNAME=tu_usuario
   export DB_PASSWORD=tu_password
   ```

4. **Compila y ejecuta**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

---

## 🎯 Uso

### Menú Principal

Al ejecutar la aplicación, verás un menú interactivo con las siguientes opciones:

```
============================================================
                   BookVerse API
            Sistema de Gestión de Libros
============================================================

MENÚ PRINCIPAL:
  1) Buscar libro por su título
  2) Mostrar libros registrados
  3) Mostrar autores registrados
  4) Buscar autores por año
  5) Mostrar libros por idioma
  0) Salir
```

### Funcionalidades

#### 🔍 1. Buscar libro por título
- Ingresa el título del libro que deseas buscar
- La aplicación buscará en la API de Gutenberg
- Podrás confirmar si es el libro correcto
- Opción de guardar en tu biblioteca personal

#### 📚 2. Mostrar libros registrados
- Visualiza todos los libros guardados en tu base de datos
- Información completa: título, autores, idiomas, descargas

#### 👨‍💼 3. Mostrar autores registrados
- Lista todos los autores de tu biblioteca
- Información biográfica y obras

#### 📅 4. Buscar autores por año
- Encuentra autores vivos en años específicos
- Filtro histórico por períodos

#### 🌍 5. Mostrar libros por idioma
- Filtra tu biblioteca por idioma
- Soporte para múltiples idiomas

---

## 🏗️ Arquitectura

### Estructura del Proyecto

```
src/main/java/com/alura/bookverse/
├── 📁 dto/           # Data Transfer Objects
│   ├── AutorDTO.java
│   ├── DatosDTO.java
│   └── LibroDTO.java
├── 📁 model/         # Entidades JPA
│   ├── Autor.java
│   ├── Idioma.java
│   └── Libro.java
├── 📁 repository/    # Repositorios de datos
│   ├── AutorRepository.java
│   └── LibroRepository.java
├── 📁 service/       # Servicios de negocio
│   ├── ConsumoAPI.java
│   ├── ConvierteDatos.java
│   └── IConvierteDatos.java
├── 📁 principal/     # Lógica de presentación
│   └── Principal.java
└── BookVerseApplication.java
```

### Patrones de Diseño

- **Repository Pattern**: Para acceso a datos
- **DTO Pattern**: Para transferencia de datos
- **Service Layer**: Para lógica de negocio
- **Command Line Interface**: Para interacción con el usuario

---

## 🔧 Configuración

### Variables de Entorno

```properties
# Base de Datos
DB_HOST=localhost
DB_NAME=bookverse_db
DB_USERNAME=tu_usuario
DB_PASSWORD=tu_password

# API
GUTENBERG_API_URL=https://gutendex.com/books/?search=
```

### application.properties

```properties
spring.application.name=BookVerse
spring.datasource.url=jdbc:postgresql://${DB_HOST}/${DB_NAME}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
```

---


## 👨‍💻 Autor

**Miguel Andres Marin**

- 🐙 GitHub: [@Miguel-Andrez-MF](https://github.com/Miguel-Andrez-MF)
- 🔗 [LinkedIn](https://www.linkedin.com/in/miguel-andres-marin-fernandez-044574341)

---

<div align="center">

**⭐ Si te gusta este proyecto, ¡dale una estrella!**

*Made with ❤️ and ☕*

</div>
