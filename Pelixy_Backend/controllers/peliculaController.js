const db = require('../config/database');

// Listar todas las películas
exports.getPeliculas = async (req, res) => {
  try {
    const result = await db.query('SELECT * FROM peliculas ORDER BY id DESC');
    res.json(result.rows);
  } catch (error) {
    console.error('Error al listar películas:', error);
    res.status(500).json({ error: 'Error al listar películas' });
  }
};

// Obtener película por ID
exports.getPeliculaById = async (req, res) => {
  const { id } = req.params;
  try {
    const result = await db.query('SELECT * FROM peliculas WHERE id = $1', [id]);
    if (result.rows.length === 0) return res.status(404).json({ mensaje: 'Película no encontrada' });
    res.json(result.rows[0]);
  } catch (error) {
    res.status(500).json({ error: 'Error al obtener película' });
  }
};

// Crear nueva película
exports.createPelicula = async (req, res) => {
  const { titulo, descripcion, anio, duracion, director, clasificacion, idioma, url_portada, calificacion } = req.body;
  try {
    await db.query(
      `INSERT INTO peliculas (titulo, descripcion, anio, duracion, director, clasificacion, idioma, url_portada, calificacion)
       VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9)`,
      [titulo, descripcion, anio, duracion, director, clasificacion, idioma, url_portada, calificacion]
    );
    res.status(201).json({ mensaje: 'Película creada correctamente' });
  } catch (error) {
    console.error('Error al registrar película:', error);
    res.status(500).json({ error: 'Error al registrar película' });
  }
};

// Actualizar película
exports.updatePelicula = async (req, res) => {
  const { id } = req.params;
  const { titulo, descripcion, anio, duracion, director, clasificacion, idioma, url_portada, calificacion } = req.body;
  try {
    await db.query(
      `UPDATE peliculas 
       SET titulo=$1, descripcion=$2, anio=$3, duracion=$4, director=$5, clasificacion=$6, idioma=$7, url_portada=$8, calificacion=$9 
       WHERE id=$10`,
      [titulo, descripcion, anio, duracion, director, clasificacion, idioma, url_portada, calificacion, id]
    );
    res.json({ mensaje: 'Película actualizada correctamente' });
  } catch (error) {
    res.status(500).json({ error: 'Error al actualizar película' });
  }
};

// Eliminar película
exports.deletePelicula = async (req, res) => {
  const { id } = req.params;
  try {
    await db.query('DELETE FROM peliculas WHERE id = $1', [id]);
    res.json({ mensaje: 'Película eliminada correctamente' });
  } catch (error) {
    res.status(500).json({ error: 'Error al eliminar película' });
  }
};

// Obtener total de películas
exports.getTotalPeliculas = async (req, res) => {
  try {
    const result = await db.query('SELECT COUNT(*) AS total FROM peliculas');
    res.json({ total: parseInt(result.rows[0].total) });
  } catch (error) {
    console.error('Error al contar películas:', error);
    res.status(500).json({ error: 'Error al contar películas' });
  }
};