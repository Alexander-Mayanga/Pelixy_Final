const express = require('express');
const cors = require('cors');
const app = express();

// Importar rutas
const peliculaRoutes = require('./routes/peliculas');
const usuarioRoutes = require('./routes/usuarios');
const comentarioRoutes = require('./routes/comentarios');
const calificacionRoutes = require('./routes/calificaciones');
const favoritoRoutes = require('./routes/favoritos');


// Middleware
app.use(cors());
app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.use('/uploads', express.static('uploads'));

// Prefijos de rutas
app.use('/api/v1/peliculas', peliculaRoutes);
app.use('/api/v1/usuarios', usuarioRoutes);
app.use('/api/v1/comentarios', comentarioRoutes);
app.use('/api/v1/calificaciones', calificacionRoutes);
app.use('/api/v1/favoritos', favoritoRoutes);

// Iniciar servidor
const PORT = 3000;
app.listen(PORT, '0.0.0.0', () => {
  console.log(`✅ Servidor corriendo en http://0.0.0.0:${PORT}`);
});

