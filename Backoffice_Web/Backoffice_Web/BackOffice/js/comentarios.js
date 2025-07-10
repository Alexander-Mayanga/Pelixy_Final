function inicializarComentarios() {
  if (!document.getElementById('body-comentarios')) return;

  console.log("comentarios.js cargado ✅");
  const modal = document.getElementById('modal-comentarios'); // CORREGIDO
  const btnAbrir = document.querySelector('#comentarios .btn-abrir-modal'); // botón solo en sección comentarios
  const btnCerrar = document.getElementById('btn-cerrar-modal-comentarios'); // CORREGIDO
  const form = document.getElementById('form-comentario');
  const tbody = document.getElementById('body-comentarios');

  if (!modal || !btnAbrir || !btnCerrar || !form || !tbody) {
    console.warn('Faltan elementos necesarios');
    return;
  }

  function cargarComentarios() {
    fetch('http://localhost:3000/api/v1/comentarios')
      .then(res => res.json())
      .then(comentarios => {
        comentarios.sort((a, b) => a.id - b.id);
        tbody.innerHTML = '';
        comentarios.forEach(c => {
          const tr = document.createElement('tr');
          tr.innerHTML = `
            <td>${c.id}</td>
            <td>${c.usuario_nombre || 'Usuario ' + c.usuario_id}</td>
            <td>${c.pelicula_titulo || 'Película ' + c.pelicula_id}</td>
            <td>${c.contenido}</td>
            <td>${new Date(c.fecha_comentario).toLocaleString()}</td>
            <td>
              <button class="btn-editar" data-id="${c.id}">✏️</button>
              <button class="btn-eliminar" data-id="${c.id}">🗑️</button>
            </td>
          `;
          tbody.appendChild(tr);
        });
        agregarEventosBotones();
      });
  }

  cargarComentarios();

  btnAbrir.addEventListener('click', () => modal.classList.remove('oculto'));
  btnCerrar.addEventListener('click', () => modal.classList.add('oculto'));

  function agregarEventosBotones() {
    document.querySelectorAll('.btn-eliminar').forEach(btn => {
      btn.addEventListener('click', () => {
        const id = btn.dataset.id;
        if (confirm('¿Eliminar este comentario?')) {
          fetch(`http://localhost:3000/api/v1/comentarios/${id}`, {
            method: 'DELETE'
          })
            .then(res => res.json())
            .then(() => {
              alert('Comentario eliminado');
              cargarComentarios();
            });
        }
      });
    });

    document.querySelectorAll('.btn-editar').forEach(btn => {
      btn.addEventListener('click', () => {
        const id = btn.dataset.id;
        editarComentario(id);
      });
    });
  }

  function editarComentario(id) {
    fetch(`http://localhost:3000/api/v1/comentarios/${id}`)
      .then(res => res.json())
      .then(c => {
        document.querySelector('[name="usuario_id"]').value = c.usuario_id;
        document.querySelector('[name="pelicula_id"]').value = c.pelicula_id;
        document.querySelector('[name="contenido"]').value = c.contenido;
        modal.classList.remove('oculto');

        form.onsubmit = function (e) {
          e.preventDefault();
          const datos = Object.fromEntries(new FormData(form));
          fetch(`http://localhost:3000/api/v1/comentarios/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(datos)
          }).then(() => {
            alert('Comentario actualizado');
            modal.classList.add('oculto');
            cargarComentarios();
            form.onsubmit = null;
          });
        };
      });
  }

  form.addEventListener('submit', (e) => {
    e.preventDefault();
    const datos = Object.fromEntries(new FormData(form));
    fetch('http://localhost:3000/api/v1/comentarios', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(datos)
    })
      .then(res => res.json())
      .then(() => {
        alert('Comentario agregado');
        form.reset();
        modal.classList.add('oculto');
        cargarComentarios();
      });
  });
}

document.addEventListener("DOMContentLoaded", inicializarComentarios);
 // Asegúrate que esto esté al final
