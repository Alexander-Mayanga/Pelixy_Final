class Favorito {
  constructor(id, usuario_id, pelicula_id, fecha_guardado) {
    this.id = id;
    this.usuario_id = usuario_id;
    this.pelicula_id = pelicula_id;
    this.fecha_guardado = fecha_guardado;
  }
}

module.exports = Favorito;
