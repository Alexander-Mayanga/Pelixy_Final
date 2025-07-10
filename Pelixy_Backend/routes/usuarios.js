const express = require('express');
const router = express.Router();
const controller = require('../controllers/usuarioController');
const upload = require('../middlewares/uploadMiddleware');

router.post('/register', upload.single('avatar'), controller.registerUsuario);
router.post('/login', controller.loginUsuario);
router.get('/totales/usuarios', controller.getTotalUsuarios);
router.get('/', controller.getUsuarios);
router.get('/:id', controller.getUsuarioById);
router.put('/:id', controller.updateUsuario);
router.delete('/:id', controller.deleteUsuario);
router.post('/subir-avatar', upload.single('avatar'), controller.subirAvatar);

module.exports = router;

