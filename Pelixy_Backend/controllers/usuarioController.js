const db = require('../config/database');

// Registrar usuario
exports.registerUsuario = async (req, res) => {
  try {
    const { nombre, apellido, correo, password, fecha_nacimiento, pais } = req.body;

    // Si se sube imagen, usar esa URL, si no, usar imagen por defecto
    const avatar_url = req.file
      ? `http://10.0.2.2:3000/uploads/${req.file.filename}`
      : 'https://via.placeholder.com/150';

    // Validar si el correo ya existe
    const existe = await db.query('SELECT id FROM usuarios WHERE correo = $1', [correo]);
    if (existe.rows.length > 0) {
      return res.status(400).json({ mensaje: 'El correo ya está registrado' });
    }

    // Insertar usuario nuevo
    await db.query(
      `INSERT INTO usuarios (nombre, apellido, correo, password, avatar_url, fecha_nacimiento, pais)
       VALUES ($1, $2, $3, $4, $5, $6, $7)`,
      [nombre, apellido, correo, password, avatar_url, fecha_nacimiento, pais]
    );

    res.status(201).json({ mensaje: 'Usuario registrado con imagen correctamente' });
  } catch (error) {
    console.error('❌ Error al registrar usuario con imagen:', error);
    res.status(500).json({ error: 'Error al registrar usuario con imagen' });
  }
};

// Login usuario
const jwt = require('jsonwebtoken');

exports.loginUsuario = async (req, res) => {
  const { correo, password } = req.body;
  try {
    const result = await db.query('SELECT * FROM usuarios WHERE correo = $1 AND password = $2', [correo, password]);

    if (result.rows.length === 0) {
      return res.status(401).json({ mensaje: 'Credenciales inválidas' });
    }

    const usuario = result.rows[0];
    const token = jwt.sign({ id: usuario.id }, 'secreto_jwt', { expiresIn: '1h' });

    delete usuario.password;
    res.json({ usuario, token }); // ← devolvemos token y datos del usuario
  } catch (error) {
    console.error('Error al iniciar sesión:', error);
    res.status(500).json({ error: 'Error al iniciar sesión' });
  }
};

// Listar todos los usuarios
exports.getUsuarios = async (req, res) => {
  try {
    const result = await db.query('SELECT * FROM usuarios');
    res.json(result.rows);
  } catch (error) {
    res.status(500).json({ error: 'Error al obtener usuarios' });
  }
};

// Obtener usuario por ID
exports.getUsuarioById = async (req, res) => {
  const { id } = req.params;
  try {
    const result = await db.query('SELECT * FROM usuarios WHERE id = $1', [id]);
    if (result.rows.length === 0) return res.status(404).json({ mensaje: 'Usuario no encontrado' });
    res.json(result.rows[0]);
  } catch (error) {
    res.status(500).json({ error: 'Error al obtener usuario' });
  }
};

// Eliminar usuario por ID
exports.deleteUsuario = async (req, res) => {
  const { id } = req.params;
  try {
    await db.query('DELETE FROM usuarios WHERE id = $1', [id]);
    res.json({ mensaje: 'Usuario eliminado correctamente' });
  } catch (error) {
    res.status(500).json({ error: 'Error al eliminar usuario' });
  }
};


// Editar usuario
exports.updateUsuario = async (req, res) => {
  const { id } = req.params;
  const { nombre, apellido, avatar_url, fecha_nacimiento, pais } = req.body;
  try {
    await db.query(
      `UPDATE usuarios 
       SET nombre = $1, apellido = $2, avatar_url = $3, fecha_nacimiento = $4, pais = $5 
       WHERE id = $6`,
      [nombre, apellido, avatar_url, fecha_nacimiento, pais, id]
    );
    res.json({ mensaje: 'Usuario actualizado correctamente' });
  } catch (error) {
    res.status(500).json({ error: 'Error al actualizar usuario' });
  }
};

//Obtener total de usuarios
  exports.getTotalUsuarios = async (req, res) => {
    try {
      const result = await db.query('SELECT COUNT(*) AS total FROM usuarios');
      res.json({ total: parseInt(result.rows[0].total) });
    } catch (error) {
      console.error('Error al contar usuarios:', error);
      res.status(500).json({ error: 'Error al contar usuarios' });
    }
  };

  //Subir imagen 
  exports.subirAvatar = async (req, res) => {
    try {
      if (!req.file) {
        return res.status(400).json({ mensaje: 'No se proporcionó ningún archivo' });
      }

      const rutaPublica = `http://10.0.2.2:3000/uploads/${req.file.filename}`; // <- URL accesible desde Android

      res.status(200).json({
        mensaje: 'Imagen subida correctamente',
        url: rutaPublica
      });
    } catch (error) {
      console.error('Error al subir imagen:', error);
      res.status(500).json({ error: 'Error interno al subir imagen' });
    }
  };
