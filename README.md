# 🎬 Pelixy App - Aplicación Móvil Android

![movie-banner](https://github.com/user-attachments/assets/9a44695f-c528-426f-939d-1e5dee320881)

> Aplicación móvil nativa desarrollada para que los usuarios disfruten de un catálogo de películas, realicen comentarios, calificaciones y accedan a contenido detallado de forma rápida y amigable.

---

## 🟪 1. Introducción

![Intro GIF](https://github.com/user-attachments/assets/5da5ac25-0d62-4fbb-97bb-ecc1a0220179)

**Pelixy App** es la versión móvil de la plataforma Pelixy. Está diseñada para personas amantes del cine que desean descubrir, calificar y comentar películas.

Conectada directamente al backend oficial, esta app permite:
- Visualizar películas y buscar por título
- Ver detalles como sinopsis, género, calificaciones
- Comentar y puntuar películas
- Acceder con login seguro

> Se desarrolló con Android Studio, Java y arquitectura MVVM, integrando llamadas HTTP mediante Volley.

---

## 🟪 🧑‍🤝‍🧑 2. Integrantes del proyecto

![Coding Team GIF](https://github.com/user-attachments/assets/4af3fb1c-dc1f-417a-85ed-93cc3118fd3c)

🟦 César Alexander Mayanga Minaya  
🟩 Deysi Aracely Quintana Juarez  
🟨 Jaime Ccapacca Merino  
🟥 Jean Carlos Fasabi Orosco  
🟪 Osmar Mauricio Marca Peña

---

## 🟦 3. Objetivos del proyecto

🎯 Objetivo general:
> Desarrollar una aplicación móvil que permita a los usuarios interactuar con el contenido de la plataforma Pelixy desde cualquier lugar.

🎯 Objetivos específicos:
- Permitir autenticación de usuarios
- Visualizar y buscar películas
- Visualizar comentarios , calificaciones y favoritos
- Mostrar información detallada de cada película

---

## 🟩 4. Público objetivo

- 👥 Usuarios activos de la plataforma Pelixy
- 🧑‍💼 Administradores que deseen validar funcionalidades desde móvil
- 👨‍🏫 Docentes evaluadores del proyecto
- 🧪 Testers o QA que deseen probar el funcionamiento completo del sistema

---

## 🟧 5. Funcionalidades principales

✅ Inicio de sesión seguro con validación de credenciales  
✅ Listado dinámico de películas desde API  
✅ Búsqueda por nombre en tiempo real  
✅ Visualización de detalles:
- Imagen de portada  
- Sinopsis  
- Género  
- Calificación promedio  
- Comentarios registrados  

✅ Registro de comentarios y puntuaciones  
✅ Diseño responsivo y limpio  
✅ Separación lógica con arquitectura MVVM

---

## 🟫 6. Tecnologías utilizadas

📱 **Frontend móvil:**
- Java
- Android Studio
- Volley (para consumo REST)
- MVVM + RecyclerView

⚙️ **Backend conectado:**
- Node.js + Express
- PostgreSQL
- API RESTful con rutas `/api/v1/`

🧪 **Otros:**
- Postman (pruebas de endpoints)
- GitHub (repositorio del código)

---

## 🗂 7. Estructura del proyecto

com.sise.pelixy/
├── activity/
│ ├── BaseDrawerActivity.java
│ ├── ComentarioActivity.java
│ ├── CrearCuentaActivity.java
│ ├── DetailsActivity.java
│ ├── FavoritosActivity.java
│ ├── MenuActivity.java
│ └── PerfilUsuarioActivity.java
│
├── adapter/
│ ├── ComentarioAdapter.java
│ ├── FavoritosAdapter.java
│ └── PeliculaAdapter.java
│
├── api/
│ ├── ApiClient.java
│ └── ApiService.java
│
├── manager/
│ └── FavoritosManager.java
│
├── models/
│ ├── Usuario.java
│ ├── Pelicula.java
│ ├── Comentario.java
│ └── Calificacion.java
│
└── MainActivity.java

---

## 🧩 Archivos XML disponibles (`res/layout/`)

| XML                              | Descripción                                      |
|----------------------------------|--------------------------------------------------|
| `activity_base_drawer.xml`       | Actividad base con `NavigationView` y Drawer     |
| `activity_main.xml`              | Pantalla de inicio de sesión                     |
| `activity_crear_cuenta.xml`      | Registro de usuario con campos y avatar          |
| `activity_menu.xml`              | Menú principal que lista películas               |
| `activity_details.xml`           | Vista de detalles de una película                |
| `activity_favoritos.xml`         | Películas favoritas del usuario                  |
| `activity_perfil_usuario.xml`    | Edición del perfil                               |
| `activity_comentario.xml`        | Comentarios de una película                      |
| `dialog_cambiar_contrasena.xml`  | Diálogo modal para cambiar contraseña            |
| `item_pelicula.xml`              | Card individual de película (lista principal)    |
| `item_pelicula_carrusel.xml`     | Item para carrusel horizontal (si aplica)        |
| `item_favorito.xml`              | Vista de ítems favoritos                         |
| `item_comentario.xml`            | Vista de comentarios individuales                |

📂 También contiene:
- `menu/drawer_menu.xml`: ítems del menú lateral
- `mipmap/`: íconos y recursos gráficos

---

## 🛠️ Librerías Usadas

- 📦 **Retrofit2** – HTTP client para llamadas REST
- 📦 **Glide** – Carga eficiente de imágenes
- 📦 **FlexboxLayout** – Distribución flexible de géneros
- 📦 **Material Components** – UI moderna
- 📦 **ConstraintLayout** – Layout responsivo y fluido

---

## ⚙️ Conexión con Backend

🔗 Todos los endpoints se manejan desde `ApiService.java` usando Retrofit.  
✔️ Probado previamente con Postman y conectado al siguiente backend:



## ✅ Estado del Proyecto

🟢 Entregado y funcional  
🔧 Últimos ajustes visuales  
📦 Listo para presentar y documentar
