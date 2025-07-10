const db = require('../config/database');

// Listar calificaciones por película
exports.getCalificacionesByPelicula = async (req, res) => {
  const { pelicula_id } = req.params;
  try {
    const result = await db.query(
      `SELECT c.*, u.nombre 
       FROM calificaciones c 
       JOIN usuarios u ON c.usuario_id = u.id 
       WHERE pelicula_id = $1`,
      [pelicula_id]
    );
    res.json(result.rows);
  } catch (error) {
    res.status(500).json({ error: 'Error al obtener calificaciones' });
  }
};

// Registrar calificación
exports.createCalificacion = async (req, res) => {
  const { usuario_id, pelicula_id, puntuacion } = req.body;
  try {
    await db.query(
      `INSERT INTO calificaciones (usuario_id, pelicula_id, puntuacion)
       VALUES ($1, $2, $3)`,
      [usuario_id, pelicula_id, puntuacion]
    );
    res.status(201).json({ mensaje: 'Calificación registrada correctamente' });
  } catch (error) {
    res.status(500).json({ error: 'Error al registrar calificación' });
  }
};

// Ver promedio de calificaciones
exports.getPromedioByPelicula = async (req, res) => {
  const { pelicula_id } = req.params;
  try {
    const result = await db.query(
      `SELECT AVG(puntuacion)::numeric(10,2) AS promedio 
       FROM calificaciones 
       WHERE pelicula_id = $1`,
      [pelicula_id]
    );
    res.json(result.rows[0]);
  } catch (error) {
    res.status(500).json({ error: 'Error al obtener promedio' });
  }
};
