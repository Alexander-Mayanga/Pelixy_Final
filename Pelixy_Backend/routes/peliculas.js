const express = require('express');
const router = express.Router();
const controller = require('../controllers/peliculaController');
const upload = require('../middlewares/uploadMiddleware'); 

router.get('/totales/peliculas', controller.getTotalPeliculas);
router.get('/', controller.getPeliculas);
router.get('/:id', controller.getPeliculaById);
router.post('/', upload.single('portada'), controller.createPelicula);
router.put('/:id', controller.updatePelicula);
router.delete('/:id', controller.deletePelicula);

module.exports = router;
