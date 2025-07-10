// routes/favoritos.js
const express = require('express');
const router = express.Router();

const {
  obtenerFavoritos,
  obtenerFavoritosPorUsuario,
  agregarFavorito,
  eliminarFavorito,
  verificarFavorito,
  getTotalFavoritos,
  eliminarFavoritoPorUsuarioYPelicula
} = require('../controllers/favoritoController');

router.get('/totales/favoritos', getTotalFavoritos);
router.get('/', obtenerFavoritos);
router.get('/usuario/:usuarioId', obtenerFavoritosPorUsuario);
router.post('/', agregarFavorito);
router.delete('/eliminar/:usuarioId/:peliculaId', eliminarFavoritoPorUsuarioYPelicula);
router.delete('/:id', eliminarFavorito);
router.get('/verificar/:usuarioId/:peliculaId', verificarFavorito);

module.exports = router;
