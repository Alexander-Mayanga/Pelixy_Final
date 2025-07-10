function inicializarSeries() {
    const modal = document.getElementById('modal-series');
    const btnAbrir = document.querySelector('.btn-abrir-modal');
    const btnCerrar = document.getElementById('btn-cerrar-modal');
    const form = document.getElementById('form-serie');
    const tbody = document.getElementById('body-series');
  
    if (!modal || !btnAbrir || !btnCerrar || !form || !tbody) return;
  
    function cargarSeries() {
      fetch('http://localhost:3000/api/v1/series')
        .then(res => res.json())
        .then(series => {
          tbody.innerHTML = '';
          series.forEach(s => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
              <td>${s.id}</td>
              <td>${s.titulo}</td>
              <td>${s.descripcion}</td>
              <td>${s.anio}</td>
              <td>${s.temporadas}</td>
              <td>${s.director}</td>
              <td>${s.clasificacion}</td>
              <td>${s.idioma}</td>
              <td>${s.url_portada ? `<img src="${s.url_portada}" width="40">` : 'Sin portada'}</td>
              <td>
                <button class="btn-editar" data-id="${s.id}">✏️</button>
                <button class="btn-eliminar" data-id="${s.id}">🗑️</button>
              </td>`;
            tbody.appendChild(tr);
          });
  
          agregarEventosBotones();
        });
    }
  
    cargarSeries();
  
    btnAbrir.addEventListener('click', () => modal.classList.remove('oculto'));
    btnCerrar.addEventListener('click', () => modal.classList.add('oculto'));
  
    form.addEventListener('submit', e => {
      e.preventDefault();
      const datos = Object.fromEntries(new FormData(form));
      form.addEventListener('submit', e => {
        e.preventDefault();
        const datos = Object.fromEntries(new FormData(form));
        console.log("Datos a enviar:", datos);  // <-- Esto ayuda a debuggear
        fetch('http://localhost:3000/api/v1/series', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(datos)
        })
      });
      
      fetch('http://localhost:3000/api/v1/series', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(datos)
      })
        .then(res => res.json())
        .then(() => {
          alert('Serie agregada');
          form.reset();
          modal.classList.add('oculto');
          cargarSeries();
        });
    });
  
    function agregarEventosBotones() {
      document.querySelectorAll('.btn-editar').forEach(btn => {
        btn.addEventListener('click', () => editarSerie(btn.dataset.id));
      });
  
      document.querySelectorAll('.btn-eliminar').forEach(btn => {
        btn.addEventListener('click', () => {
          if (confirm('¿Eliminar esta serie?')) {
            fetch(`http://localhost:3000/api/v1/series/${btn.dataset.id}`, {
              method: 'DELETE'
            })
              .then(res => res.json())
              .then(() => {
                alert('Serie eliminada');
                cargarSeries();
              });
          }
        });
      });
    }
  
    function editarSerie(id) {
      fetch(`http://localhost:3000/api/v1/series/${id}`)
        .then(res => res.json())
        .then(serie => {
          form.titulo.value = serie.titulo;
          form.descripcion.value = serie.descripcion;
          form.anio.value = serie.anio;
          form.temporadas.value = serie.temporadas;
          form.director.value = serie.director;
          form.clasificacion.value = serie.clasificacion;
          form.idioma.value = serie.idioma;
          form.url_portada.value = serie.url_portada;
  
          modal.classList.remove('oculto');
  
          form.onsubmit = function (e) {
            e.preventDefault();
            const datos = Object.fromEntries(new FormData(form));
            fetch(`http://localhost:3000/api/v1/series/${id}`, {
              method: 'PUT',
              headers: { 'Content-Type': 'application/json' },
              body: JSON.stringify(datos)
            })
              .then(res => res.json())
              .then(() => {
                alert('Serie actualizada');
                form.reset();
                modal.classList.add('oculto');
                form.onsubmit = null;
                cargarSeries();
              });
          };
        });
    }
  }
  