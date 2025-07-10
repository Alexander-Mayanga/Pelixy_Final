package com.sise.pelixy.activity;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.core.view.GravityCompat;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sise.pelixy.R;
import com.sise.pelixy.api.ApiClient;
import com.sise.pelixy.api.ApiService;
import com.sise.pelixy.models.Pelicula;
import com.sise.pelixy.models.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuActivity extends BaseDrawerActivity {

    private Usuario usuarioActual;
    private LinearLayout contenedorPeliculas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityLayout(R.layout.activity_menu);

        this.usuarioActual = (Usuario) getIntent().getSerializableExtra("usuario");
        super.usuarioActual = this.usuarioActual;

        ImageView menuIcon = findViewById(R.id.menuIcon);
        contenedorPeliculas = findViewById(R.id.contenedorPeliculas);

        menuIcon.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        cargarPeliculasDesdeApi();
    }

    private void cargarPeliculasDesdeApi() {
        ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
        Call<List<Pelicula>> call = apiService.getPeliculas();

        call.enqueue(new Callback<List<Pelicula>>() {
            @Override
            public void onResponse(Call<List<Pelicula>> call, Response<List<Pelicula>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mostrarPeliculas(response.body());
                } else {
                    Toast.makeText(MenuActivity.this, "Error al cargar películas", Toast.LENGTH_SHORT).show();
                    Log.e("PELIS_API", "Código de respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Pelicula>> call, Throwable t) {
                Toast.makeText(MenuActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("PELIS_API", "Fallo de conexión: ", t);
            }
        });
    }

    private void mostrarPeliculas(List<Pelicula> lista) {
        LayoutInflater inflater = LayoutInflater.from(this);
        contenedorPeliculas.removeAllViews(); // limpiar por si acaso

        for (Pelicula p : lista) {
            View vistaItem = inflater.inflate(R.layout.item_pelicula_carrusel, contenedorPeliculas, false);

            ImageView img = vistaItem.findViewById(R.id.imgPelicula);
            TextView txt = vistaItem.findViewById(R.id.txtTitulo);

            txt.setText(p.getTitulo());

            if (p.tieneImagen()) {
                Glide.with(this)
                        .load(p.getUrlPortada())
                        .placeholder(R.drawable.placeholder)
                        .into(img);
            } else {
                img.setImageResource(R.drawable.placeholder);
            }

            vistaItem.setOnClickListener(v -> {
                Intent intent = new Intent(this, DetailsActivity.class);
                intent.putExtra("pelicula", p);
                intent.putExtra("usuario", usuarioActual);
                startActivity(intent);
            });

            contenedorPeliculas.addView(vistaItem);
        }
    }
}