package com.sise.pelixy.activity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.flexbox.FlexboxLayout;
import com.sise.pelixy.R;
import com.sise.pelixy.api.ApiClient;
import com.sise.pelixy.api.ApiService;
import com.sise.pelixy.models.Comentario;
import com.sise.pelixy.models.estado.FavoritoEstado;
import com.sise.pelixy.models.request.FavoritoRequest;
import com.sise.pelixy.models.Pelicula;
import com.sise.pelixy.models.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends BaseDrawerActivity {

    ImageView imgPelicula;
    TextView txtTitulo, txtAnio, txtDuracion, txtDescripcion, txtIdioma, txtDirector;
    RatingBar ratingBar;
    FlexboxLayout flexboxGeneros;
    Button btnFavoritos;

    private Pelicula pelicula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityLayout(R.layout.activity_details);

        imgPelicula = findViewById(R.id.imgPelicula);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtAnio = findViewById(R.id.txtAnio);
        txtDuracion = findViewById(R.id.txtDuracion);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        txtIdioma = findViewById(R.id.txtIdioma);
        txtDirector = findViewById(R.id.txtDirector);
        ratingBar = findViewById(R.id.ratingBar);
        flexboxGeneros = findViewById(R.id.flexboxGeneros);
        btnFavoritos = findViewById(R.id.btnAgregarFavoritos);

        pelicula = (Pelicula) getIntent().getSerializableExtra("pelicula");
        this.usuarioActual = (Usuario) getIntent().getSerializableExtra("usuario");

        if (pelicula != null && (pelicula.getDescripcion() == null || pelicula.getDescripcion().isEmpty())) {
            cargarDetalleDesdeApi(pelicula.getId());
        } else if (pelicula != null) {
            mostrarInformacion(pelicula);
            mostrarComentarios(pelicula.getId());
            verificarYActualizarBotonFavoritos();
        }

        btnFavoritos.setOnClickListener(v -> manejarClickFavoritos());
    }

    private void manejarClickFavoritos() {
        ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);

        apiService.verificarFavorito(usuarioActual.getId(), pelicula.getId()).enqueue(new Callback<FavoritoEstado>() {
            @Override
            public void onResponse(Call<FavoritoEstado> call, Response<FavoritoEstado> response) {
                boolean yaEsFavorito = response.isSuccessful() && response.body() != null && response.body().isEstaEnFavoritos();

                if (yaEsFavorito) {
                    apiService.eliminarFavoritoPorUsuarioYPelicula(usuarioActual.getId(), pelicula.getId()).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(DetailsActivity.this, "Eliminado de Favoritos", Toast.LENGTH_SHORT).show();
                            btnFavoritos.setText("Agregar a Favoritos");
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(DetailsActivity.this, "Error de red", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    FavoritoRequest favoritoRequest = new FavoritoRequest(usuarioActual.getId(), pelicula.getId());
                    apiService.agregarFavorito(favoritoRequest).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(DetailsActivity.this, "Agregado a Favoritos", Toast.LENGTH_SHORT).show();
                                btnFavoritos.setText("Quitar de Favoritos");
                            } else {
                                Toast.makeText(DetailsActivity.this, "Ya está en favoritos", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(DetailsActivity.this, "Error de red", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<FavoritoEstado> call, Throwable t) {
                Toast.makeText(DetailsActivity.this, "Error al verificar favorito", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void verificarYActualizarBotonFavoritos() {
        ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);

        apiService.verificarFavorito(usuarioActual.getId(), pelicula.getId()).enqueue(new Callback<FavoritoEstado>() {
            @Override
            public void onResponse(Call<FavoritoEstado> call, Response<FavoritoEstado> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isEstaEnFavoritos()) {
                    btnFavoritos.setText("Quitar de Favoritos");
                } else {
                    btnFavoritos.setText("Agregar a Favoritos");
                }
            }

            @Override
            public void onFailure(Call<FavoritoEstado> call, Throwable t) {
                btnFavoritos.setText("Agregar a Favoritos");
            }
        });
    }

    private void mostrarInformacion(Pelicula peli) {
        txtTitulo.setText(peli.getTitulo());
        txtAnio.setText(String.valueOf(peli.getAnio()));
        txtDuracion.setText(peli.getDuracion());
        txtDescripcion.setText(peli.getDescripcion());
        txtIdioma.setText(peli.getIdioma());
        txtDirector.setText(peli.getDirector());
        ratingBar.setRating(peli.getCalificacion());

        if (peli.tieneImagen()) {
            Glide.with(this)
                    .load(peli.getUrlPortada())
                    .placeholder(R.drawable.placeholder)
                    .into(imgPelicula);
        } else {
            imgPelicula.setImageResource(R.drawable.placeholder);
        }

        mostrarGeneros(peli.getClasificacion());
    }

    private void mostrarGeneros(String textoClasificacion) {
        if (textoClasificacion == null || textoClasificacion.isEmpty()) return;

        String[] generos = textoClasificacion.split(",");

        for (String genero : generos) {
            Button btn = new Button(this);
            btn.setText(genero.trim());
            btn.setTextColor(Color.WHITE);
            btn.setTextSize(14);
            btn.setAllCaps(false);

            GradientDrawable shape = new GradientDrawable();
            shape.setColor(getColorPorGenero(genero.trim().toLowerCase()));
            shape.setCornerRadius(50);
            btn.setBackground(shape);

            FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 8, 8, 8);
            btn.setLayoutParams(params);

            flexboxGeneros.addView(btn);
        }
    }

    private int getColorPorGenero(String genero) {
        switch (genero) {
            case "terror": return Color.parseColor("#D32F2F");
            case "drama": return Color.parseColor("#7B1FA2");
            case "acción": return Color.parseColor("#F57C00");
            case "aventura": return Color.parseColor("#388E3C");
            case "comedia": return Color.parseColor("#FBC02D");
            case "fantasía": return Color.parseColor("#0288D1");
            case "thriller": return Color.parseColor("#455A64");
            case "romance": return Color.parseColor("#E91E63");
            case "ciencia ficción": return Color.parseColor("#0097A7");
            case "animación": return Color.parseColor("#8E24AA");
            default: return Color.GRAY;
        }
    }

    private void mostrarComentarios(int idPelicula) {
        ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
        Call<List<Comentario>> call = apiService.getComentariosPorPelicula(idPelicula);

        LinearLayout contenedor = findViewById(R.id.contenedorComentarios);
        contenedor.removeAllViews();

        call.enqueue(new Callback<List<Comentario>>() {
            @Override
            public void onResponse(Call<List<Comentario>> call, Response<List<Comentario>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LayoutInflater inflater = LayoutInflater.from(DetailsActivity.this);
                    for (Comentario c : response.body()) {
                        View vistaComentario = inflater.inflate(R.layout.item_comentario, contenedor, false);

                        TextView tvNombre = vistaComentario.findViewById(R.id.tvNombreUsuario);
                        TextView tvComentario = vistaComentario.findViewById(R.id.tvComentario);
                        TextView tvFecha = vistaComentario.findViewById(R.id.tvFechaComentario);
                        ImageView ivAvatar = vistaComentario.findViewById(R.id.ivAvatar);

                        tvNombre.setText(c.getNombreUsuario());
                        tvComentario.setText(c.getComentario());
                        tvFecha.setText(c.getFechaComentario());

                        if (c.getAvatarUri() != null && !c.getAvatarUri().isEmpty()) {
                            Glide.with(DetailsActivity.this)
                                    .load(c.getAvatarUri())
                                    .placeholder(R.drawable.usuario_default)
                                    .into(ivAvatar);
                        } else {
                            ivAvatar.setImageResource(R.drawable.usuario_default);
                        }

                        contenedor.addView(vistaComentario);
                    }
                } else {
                    Toast.makeText(DetailsActivity.this, "No se pudieron cargar los comentarios", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Comentario>> call, Throwable t) {
                Toast.makeText(DetailsActivity.this, "Error de red al cargar comentarios", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarDetalleDesdeApi(int peliculaId) {
        ApiService api = ApiClient.getRetrofit().create(ApiService.class);
        Call<Pelicula> call = api.getPeliculaPorId(peliculaId);

        call.enqueue(new Callback<Pelicula>() {
            @Override
            public void onResponse(Call<Pelicula> call, Response<Pelicula> response) {
                if (response.isSuccessful() && response.body() != null) {
                    pelicula = response.body();
                    mostrarInformacion(pelicula);
                    mostrarComentarios(pelicula.getId());
                    verificarYActualizarBotonFavoritos();
                } else {
                    Toast.makeText(DetailsActivity.this, "No se encontró la película", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pelicula> call, Throwable t) {
                Toast.makeText(DetailsActivity.this, "Error al cargar película", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
