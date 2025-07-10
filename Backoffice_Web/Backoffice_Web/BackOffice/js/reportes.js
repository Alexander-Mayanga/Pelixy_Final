function actualizarTotales() {
  // Total de usuarios
  fetch('http://localhost:3000/api/v1/usuarios/totales/usuarios')
    .then(res => res.json())
    .then(data => {
      const spanUsuarios = document.getElementById('total-usuarios');
      if (spanUsuarios) {
        spanUsuarios.textContent = data.total;
      }
    })
    .catch(err => console.error('Error al obtener total de usuarios:', err));

  const spanPeliculas = document.getElementById('total-peliculas');
  if (spanPeliculas) {
    fetch('http://localhost:3000/api/v1/peliculas/totales/peliculas')
      .then(res => res.json())
      .then(data => {
        spanPeliculas.textContent = data.total;
      })
      .catch(err => console.error('Error al obtener total de películas:', err));
  }
}
function cargarTotalUsuarios() {
  fetch('http://localhost:3000/api/v1/usuarios')
    .then(res => res.json())
    .then(usuarios => {
      document.getElementById('total-usuarios').textContent = usuarios.length;
    })
    .catch(err => console.error('Error al cargar usuarios:', err));
}

function cargarTotalPeliculas() {
  fetch('http://localhost:3000/api/v1/peliculas')
    .then(res => res.json())
    .then(peliculas => {
      document.getElementById('total-peliculas').textContent = peliculas.length;
    })
    .catch(err => console.error('Error al cargar películas:', err));
}

function cargarTotalComentarios() {
  fetch('http://localhost:3000/api/v1/comentarios')
    .then(res => res.json())
    .then(comentarios => {
      document.querySelector('.content-comentarios span').textContent = comentarios.length;
    })
    .catch(err => console.error('Error al cargar comentarios:', err));
}

function cargarTotalSeries() {
  fetch('http://localhost:3000/api/v1/series')
    .then(res => res.json())
    .then(series => {
      document.getElementById('total-series').textContent = series.length;
    })
    .catch(err => console.error('Error al cargar series:', err));
}


document.addEventListener('DOMContentLoaded', () => {
  cargarTotalUsuarios();
  cargarTotalPeliculas();
  cargarTotalComentarios();
  cargarTotalSeries(); 
});

