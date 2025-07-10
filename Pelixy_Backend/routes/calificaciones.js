const express = require('express');
const router = express.Router();
const controller = require('../controllers/calificacionController');

router.get('/:pelicula_id', controller.getCalificacionesByPelicula);
router.get('/promedio/:pelicula_id', controller.getPromedioByPelicula);
router.post('/', controller.createCalificacion);

module.exports = router;
