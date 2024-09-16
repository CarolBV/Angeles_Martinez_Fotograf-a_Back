# Ángeles Martinez Fotografía - Backend

## Descripción General 🖥️
Este es el backend de una aplicación para una fotógrafa profesional, diseñado para gestionar la autenticación de la administradora y el almacenamiento de imágenes de la galería. La API permite la creación, edición, eliminación y visualización de las entradas de la galería, y está implementada con Java Spring Boot, conectada a una base de datos PostgreSQL y Cloudinary para el manejo de imágenes en la nube.

Características Principales 🚀

Gestión de Administradora:

- Autenticación mediante JWT (JSON Web Tokens).
- Login seguro de la administradora con credenciales validadas en la base de datos.
- Restricciones de acceso a las rutas protegidas solo para la administradora autenticada.
  
Gestión de Galería:

- Subida de imágenes a Cloudinary y almacenamiento de la URL en la base de datos.
- Edición y actualización de imágenes y categorías.
- Eliminación de imágenes, tanto en Cloudinary como en la base de datos.
- Visualización de imágenes agrupadas por categorías.
  
Otras Funcionalidades:

- Obtención de imágenes aleatorias para mostrar en secciones destacadas.
- Listado de todas las categorías disponibles en la galería.
  
Tecnologías Utilizadas 🛠️

- Java: Lenguaje principal del backend.
- Spring Boot: Framework para la construcción de aplicaciones Java.
- Spring Security: Gestión de la autenticación y autorización con JWT.
- PostgreSQL: Base de datos relacional para almacenar información de las imágenes y de la administradora.
- Cloudinary: Servicio de almacenamiento en la nube para gestionar las imágenes.
- Maven: Herramienta de gestión de dependencias y construcción del proyecto.
  
API Endpoints 📡

Autenticación:

- POST /admin/login: Inicia sesión y devuelve un token JWT.
- POST /admin/create: Crea una nueva cuenta de administradora.
  
Galería:

- GET /gallery/categories: Obtiene una lista de todas las categorías con sus respectivas imágenes.
- GET /gallery/category/{category}: Obtiene todas las imágenes de una categoría específica.
- GET /gallery/image/{id}: Obtiene una imagen por su ID.
- GET /gallery/random/images: Obtiene un conjunto de imágenes aleatorias.
- POST /gallery/image: Sube una nueva imagen (autenticación requerida).
- PUT /gallery/image/{id}: Edita una imagen existente (autenticación requerida).
- DELETE /gallery/id/{id}: Elimina una imagen (autenticación requerida).
  
## Configuración del Proyecto 🔧

Clonar este repositorio:

```
git clone https://github.com/tuUsuario/Angeles_Martinez_Fotografia_Back.git
```

Navegar al directorio del proyecto:

```
cd Angeles_Martinez_Fotografia_Back
```
Configurar la base de datos PostgreSQL:

- Crear una base de datos y actualizar los detalles de conexión en el archivo application.properties.
  
Configurar las credenciales de Cloudinary:

- Añadir las claves de Cloudinary en el archivo .env o en application.properties.
  
Construir y ejecutar la aplicación:

```
mvn clean install
mvn spring-boot:run
```

## Variables de Entorno ⚙️

Debes configurar las siguientes variables de entorno en tu archivo .env o application.properties:

- JWT_SECRET_KEY: Clave secreta para la generación de tokens JWT.
- CLOUDINARY_API_KEY: API Key de tu cuenta de Cloudinary.
- CLOUDINARY_API_SECRET: API Secret de Cloudinary.
- CLOUDINARY_CLOUD_NAME: Nombre del Cloudinary.
  
## Contribución 👥

Si deseas contribuir a este proyecto, sigue los siguientes pasos:

1. Haz un fork del repositorio.
  
2. Crea una nueva rama:

```
git checkout -b feature/nueva-funcionalidad
```

3. Realiza tus cambios y haz commit:

```
git commit -m 'Añadir nueva funcionalidad'
```

4. Sube los cambios a tu rama:
```
git push origin feature/nueva-funcionalidad
```

5. Abre un Pull Request.

Autora ✒️
- [**Carolina**](https://github.com/CarolBV)
