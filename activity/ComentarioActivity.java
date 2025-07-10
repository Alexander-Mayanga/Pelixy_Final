package com.sise.pelixy.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sise.pelixy.R;
import com.sise.pelixy.api.ApiClient;
import com.sise.pelixy.api.ApiService;
import com.sise.pelixy.models.request.ComentarioRequest;
import com.sise.pelixy.models.Pelicula;
import com.sise.pelixy.models.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComentarioActivity extends BaseDrawerActivity {

    private Pelicula pelicula;
    private Usuario usuarioActual;

    private TextView tvTituloPelicula;
    private ImageView imgPeliculaComentario;
    private EditText etComentario;
    private Button btnEnviar;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityLayout(R.layout.activity_comentario);

        // Inicializar Retrofit
        apiService = ApiClient.getRetrofit().create(ApiService.class);

        // Inicializar vistas
        tvTituloPelicula = findViewById(R.id.tvTituloPelicula);
        imgPeliculaComentario = findViewById(R.id.imgPeliculaComentario);
        etComentario = findViewById(R.id.etNuevoComentario);
        btnEnviar = findViewById(R.id.btnEnviarComentario);

        // Obtener datos recibidos
        pelicula = (Pelicula) getIntent().getSerializableExtra("pelicula");
        usuarioActual = (Usuario) getIntent().getSerializableExtra("usuario");

        if (pelicula != null) {
            tvTituloPelicula.setText(pelicula.getTitulo());

            if (pelicula.tieneImagen()) {
                Glide.with(this)
                        .load(pelicula.getUrlPortada())
                        .into(imgPeliculaComentario);
            } else {
                imgPeliculaComentario.setImageResource(R.drawable.usuario_default); // Imagen por defecto
            }
        }

        btnEnviar.setOnClickListener(v -> {
            String textoComentario = etComentario.getText().toString().trim();

            if (textoComentario.isEmpty()) {
                Toast.makeText(this, "Escribe algo antes de enviar", Toast.LENGTH_SHORT).show();
                return;
            }

            // Llamada a la API para enviar comentario
            enviarComentarioAPI(usuarioActual.getId(), pelicula.getId(), textoComentario);
        });
    }

    private void enviarComentarioAPI(int usuarioId, int peliculaId, String contenido) {
        ComentarioRequest comentario = new ComentarioRequest(usuarioId, peliculaId, contenido);
        Call<Void> call = apiService.crearComentario(comentario);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ComentarioActivity.this, "✅ Comentario enviado correctamente", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ComentarioActivity.this, "❌ Error al enviar comentario. Código: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ComentarioActivity.this, "⚠ Error de red: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}