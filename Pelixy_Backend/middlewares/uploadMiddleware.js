const multer = require('multer');
const path = require('path');

// Configuración del almacenamiento
const storage = multer.diskStorage({
  destination: function (req, file, cb) {
    cb(null, 'uploads/');   
  },
  filename: function (req, file, cb) {
    const ext = path.extname(file.originalname);
    const nombre = Date.now() + '-' + Math.round(Math.random() * 1E9) + ext;
    cb(null, nombre);
  }
});

const upload = multer({ storage });

module.exports = upload;