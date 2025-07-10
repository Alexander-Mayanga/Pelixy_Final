const express = require('express');
const router = express.Router();
const controller = require('../controllers/comentarioController');

// Comentarios
router.get('/totales/comentarios', controller.getTotalComentarios);
router.post('/', controller.createComentario);
router.get('/:pelicula_id', controller.getComentariosByPelicula);
router.put('/:id', controller.updateComentario);    
router.delete('/:id', controller.deleteComentario);  

module.exports = router;
