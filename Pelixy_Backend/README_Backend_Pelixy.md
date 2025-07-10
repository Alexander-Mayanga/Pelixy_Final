
📄 README — Pelixy Backend (Node.js + Express + PostgreSQL)
===========================================================

🎬 Pelixy es una plataforma de películas que permite a los usuarios explorar, comentar, calificar y marcar películas como favoritas. Este repositorio contiene el backend de la aplicación, construido con Node.js, Express y PostgreSQL.

📁 Estructura del Proyecto
--------------------------

```
Pelixy_Backend/
├── config/
│   └── database.js          # Configuración de conexión a PostgreSQL
├── controllers/
│   ├── usuarioController.js
│   ├── peliculaController.js
│   ├── comentarioController.js
│   ├── calificacionController.js
│   └── favoritoController.js ✅
├── models/
│   ├── Usuario.js
│   ├── Pelicula.js
│   ├── Comentario.js
│   ├── Calificacion.js
│   └── Favorito.js ✅
├── routes/
│   ├── usuarios.js
│   ├── peliculas.js
│   ├── comentarios.js
│   ├── calificaciones.js
│   └── favoritos.js ✅
├── database/
│   └── pelixy_db.sql        # Script de creación de base de datos
├── app.js                   # Punto de entrada principal
└── package.json
```

🔌 Conexión a Base de Datos
---------------------------

- Base de datos: PostgreSQL
- Nombre: `pelixy_db`
- Usuario: `postgres`
- Contraseña: `12345`
- Puerto: `5432`

🌐 Endpoints Disponibles
------------------------

Todas las rutas están prefijadas por:  
```
/api/v1/
```

📦 Usuarios
-----------
| Método | Endpoint                    | Descripción                |
|--------|-----------------------------|----------------------------|
| GET    | /usuarios                   | Obtener todos los usuarios |
| GET    | /usuarios/:id               | Obtener un usuario por ID  |
| POST   | /usuarios                   | Crear un nuevo usuario     |
| PUT    | /usuarios/:id               | Actualizar usuario         |
| DELETE | /usuarios/:id               | Eliminar usuario           |
| POST   | /usuarios/login             | Iniciar sesión             |

🎞️ Películas
------------
| Método | Endpoint                     | Descripción                  |
|--------|------------------------------|------------------------------|
| GET    | /peliculas                   | Listar todas las películas   |
| GET    | /peliculas/:id               | Detalle de película          |
| GET    | /peliculas/buscar/:titulo    | Buscar película por título   |
| POST   | /peliculas                   | Agregar nueva película       |
| PUT    | /peliculas/:id               | Editar película              |
| DELETE | /peliculas/:id               | Eliminar película            |

💬 Comentarios
--------------
| Método | Endpoint                      | Descripción                      |
|--------|-------------------------------|----------------------------------|
| GET    | /comentarios                  | Listar todos los comentarios     |
| GET    | /comentarios/pelicula/:id     | Comentarios de una película      |
| POST   | /comentarios                  | Agregar comentario               |
| PUT    | /comentarios/:id              | Editar comentario                |
| DELETE | /comentarios/:id              | Eliminar comentario              |

⭐ Favoritos
-----------
| Método | Endpoint                            | Descripción                         |
|--------|-------------------------------------|-------------------------------------|
| GET    | /favoritos                          | Listar todos los favoritos          |
| GET    | /favoritos/usuario/:usuario_id      | Ver favoritos por usuario           |
| GET    | /favoritos/verificar                | Verificar si una película es fav    |
| POST   | /favoritos                          | Agregar a favoritos                 |
| DELETE | /favoritos/:id                      | Eliminar favorito por ID            |

🔢 Ejemplos de uso en Postman
-----------------------------

📌 Verificar si una película está en favoritos:
```
GET http://localhost:3000/api/v1/favoritos/verificar?usuario_id=1&pelicula_id=2
```

📝 Agregar un favorito:
```
POST http://localhost:3000/api/v1/favoritos
Content-Type: application/json

{
  "usuario_id": 1,
  "pelicula_id": 2
}
```

🗑️ Eliminar un favorito:
```
DELETE http://localhost:3000/api/v1/favoritos/3
```

🔍 Listar favoritos por usuario:
```
GET http://localhost:3000/api/v1/favoritos/usuario/1
```

🔁 Listar todos los favoritos con info extra:
```
GET http://localhost:3000/api/v1/favoritos
```

🔐 Login de usuario (para simular sesión en Android):
```
POST http://localhost:3000/api/v1/usuarios/login
Content-Type: application/json

{
  "correo": "juan@gmail.com",
  "contrasena": "123456"
}
```

🚀 Iniciar el Servidor
----------------------

1. Instalar dependencias:
```bash
npm install
```

2. Iniciar servidor:
```bash
npm run dev
```

3. Ir a:
```
http://localhost:3000/api/v1/
```

🧠 Notas Finales
----------------

- Este backend ya está conectado correctamente con la app Android y el backoffice web.
- Se recomienda probar cada ruta con Postman antes de integrarla al frontend.
- ¡Listo para producción o seguir mejorando!
