const pool = require('../config/database');

// LISTAR TODOS LOS FAVORITOS (con info de usuario y película)
const obtenerFavoritos = async (req, res) => {
  try {
    const query = `
        SELECT f.id, f.usuario_id, u.nombre AS nombre_usuario, 
              f.pelicula_id, p.titulo AS titulo_pelicula, 
              p.url_portada, f.fecha_guardado
        FROM favoritos f
        JOIN usuarios u ON f.usuario_id = u.id
        JOIN peliculas p ON f.pelicula_id = p.id
        ORDER BY f.fecha_guardado DESC
    `;
    const result = await pool.query(query);
    res.json(result.rows);
  } catch (error) {
    console.error('❌ Error al obtener favoritos:', error.message);
    res.status(500).json({ mensaje: 'Error al obtener los favoritos' });
  }
};

// LISTAR FAVORITOS DE UN USUARIO
const obtenerFavoritosPorUsuario = async (req, res) => {
  try {
    const { usuarioId } = req.params;
    const query = `
      SELECT f.id, f.usuario_id, u.nombre AS nombre_usuario, 
       f.pelicula_id, p.titulo AS titulo_pelicula, 
       p.url_portada, f.fecha_guardado
      FROM favoritos f
      JOIN usuarios u ON f.usuario_id = u.id
      JOIN peliculas p ON f.pelicula_id = p.id
      WHERE f.usuario_id = $1
      ORDER BY f.fecha_guardado DESC
    `;
    const result = await pool.query(query, [usuarioId]);
    res.json(result.rows);
  } catch (error) {
    console.error('❌ Error al obtener favoritos por usuario:', error.message);
    res.status(500).json({ mensaje: 'Error al obtener los favoritos del usuario' });
  }
};

// AGREGAR FAVORITO
const agregarFavorito = async (req, res) => {
  const { usuario_id, pelicula_id } = req.body;

  try {
    const yaExiste = await pool.query(`
      SELECT * FROM favoritos WHERE usuario_id = $1 AND pelicula_id = $2
    `, [usuario_id, pelicula_id]);

    if (yaExiste.rows.length > 0) {
      return res.status(409).json({ mensaje: 'La película ya está en favoritos' });
    }

    const query = `
      INSERT INTO favoritos (usuario_id, pelicula_id) 
      VALUES ($1, $2) RETURNING *
    `;
    const result = await pool.query(query, [usuario_id, pelicula_id]);
    res.status(201).json(result.rows[0]);

  } catch (error) {
    console.error('❌ Error al agregar favorito:', error.message);
    res.status(500).json({ mensaje: 'Error al agregar favorito' });
  }
};

// ELIMINAR FAVORITO POR ID
const eliminarFavorito = async (req, res) => {
  try {
    const { id } = req.params;
    await pool.query('DELETE FROM favoritos WHERE id = $1', [id]);
    res.json({ mensaje: '✅ Favorito eliminado' });
  } catch (error) {
    console.error('❌ Error al eliminar favorito:', error.message);
    res.status(500).json({ mensaje: 'Error al eliminar favorito' });
  }
};

// VERIFICAR SI UNA PELÍCULA ESTÁ EN FAVORITOS
const verificarFavorito = async (req, res) => {
  try {
    const { usuarioId, peliculaId } = req.params;
    const query = `
      SELECT * FROM favoritos 
      WHERE usuario_id = $1 AND pelicula_id = $2
    `;
    const result = await pool.query(query, [usuarioId, peliculaId]);
    const estaEnFavoritos = result.rows.length > 0;
    res.json({ estaEnFavoritos });
  } catch (error) {
    console.error('❌ Error al verificar favorito:', error.message);
    res.status(500).json({ mensaje: 'Error al verificar favorito' });
  }
};

//Obtener total de favoritos
const getTotalFavoritos = async (req, res) => {
  try {
    const result = await db.query('SELECT COUNT(*) AS total FROM favoritos');
    res.json({ total: parseInt(result.rows[0].total) });
  } catch (error) {
    console.error('Error al contar favoritos:', error);
    res.status(500).json({ error: 'Error al contar favoritos' });
  }
};

// Eliminar favorito por usuario_id y pelicula_id
const eliminarFavoritoPorUsuarioYPelicula = async (req, res) => {
  try {
    const { usuarioId, peliculaId } = req.params;
    await pool.query(
      'DELETE FROM favoritos WHERE usuario_id = $1 AND pelicula_id = $2',
      [usuarioId, peliculaId]
    );
    res.json({ mensaje: '✅ Favorito eliminado correctamente' });
  } catch (error) {
    console.error('❌ Error al eliminar favorito:', error.message);
    res.status(500).json({ mensaje: 'Error al eliminar favorito' });
  }
};


module.exports = {
  obtenerFavoritos,
  obtenerFavoritosPorUsuario,
  agregarFavorito,
  eliminarFavorito,
  verificarFavorito,
  getTotalFavoritos,
  eliminarFavoritoPorUsuarioYPelicula,
};
