const db = require('../config/database');

// Obtener comentarios por película
exports.getComentariosByPelicula = async (req, res) => {
  const { pelicula_id } = req.params;
  try {
    const result = await db.query(
      `SELECT c.*, u.nombre 
       FROM comentarios c 
       JOIN usuarios u ON c.usuario_id = u.id 
       WHERE pelicula_id = $1 
       ORDER BY fecha_comentario DESC`,
      [pelicula_id]
    );
    res.json(result.rows);
  } catch (error) {
    res.status(500).json({ error: 'Error al obtener comentarios' });
  }
};

// Agregar comentario
exports.createComentario = async (req, res) => {
  const { usuario_id, pelicula_id, contenido } = req.body;
  try {
    await db.query(
      `INSERT INTO comentarios (usuario_id, pelicula_id, contenido)
       VALUES ($1, $2, $3)`,
      [usuario_id, pelicula_id, contenido]
    );
    res.status(201).json({ mensaje: 'Comentario agregado correctamente' });
  } catch (error) {
    res.status(500).json({ error: 'Error al agregar comentario' });
  }
};

// Editar comentario
exports.updateComentario = async (req, res) => {
  const { id } = req.params;
  const { contenido } = req.body;
  try {
    await db.query(
      `UPDATE comentarios SET contenido = $1 WHERE id = $2`,
      [contenido, id]
    );
    res.json({ mensaje: 'Comentario actualizado correctamente' });
  } catch (error) {
    res.status(500).json({ error: 'Error al actualizar comentario' });
  }
};

// Eliminar comentario
exports.deleteComentario = async (req, res) => {
  const { id } = req.params;
  try {
    await db.query(`DELETE FROM comentarios WHERE id = $1`, [id]);
    res.json({ mensaje: 'Comentario eliminado correctamente' });
  } catch (error) {
    res.status(500).json({ error: 'Error al eliminar comentario' });
  }
};

//Obtener total de Comentarios
exports.getTotalComentarios = async (req, res) => {
  try {
    const result = await db.query('SELECT COUNT(*) AS total FROM comentarios');
    res.json({ total: parseInt(result.rows[0].total) });
  } catch (error) {
    console.error('Error al contar comentarios:', error);
    res.status(500).json({ error: 'Error al contar comentarios' });
  }
};
