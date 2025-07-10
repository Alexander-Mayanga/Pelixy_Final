package com.sise.pelixy.activity;

import android.os.Bundle;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.Toast;

import com.sise.pelixy.adapter.FavoritosAdapter;
import com.sise.pelixy.manager.FavoritosManager;
import com.sise.pelixy.R;
import com.sise.pelixy.api.ApiClient;
import com.sise.pelixy.api.ApiService;
import com.sise.pelixy.models.Favorito;
import com.sise.pelixy.models.Pelicula;
import com.sise.pelixy.models.Usuario;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class FavoritosActivity extends BaseDrawerActivity {

    private RecyclerView recyclerFavoritos;
    private FavoritosAdapter favoritosAdapter;
    private List<Pelicula> listaFavoritos;

    private Usuario usuarioActual;

    @Override
    protected void onResume() {
        super.onResume();
        cargarFavoritosDesdeApi();

        listaFavoritos = obtenerFavoritosDesdePreferencias();
        favoritosAdapter.setListaFavoritos(listaFavoritos);
        favoritosAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityLayout(R.layout.activity_favoritos);
        usuarioActual = (Usuario) getIntent().getSerializableExtra("usuario");
        super.usuarioActual = usuarioActual;

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerFavoritos = findViewById(R.id.recyclerFavoritos);
        recyclerFavoritos.setLayoutManager(new GridLayoutManager(this, 2));

        listaFavoritos = obtenerFavoritosDesdePreferencias();

        Log.d("USUARIO", "usuarioActual = " + usuarioActual);
        if (usuarioActual == null) {
            Toast.makeText(this, "usuarioActual es NULL", Toast.LENGTH_LONG).show();
        }
        favoritosAdapter = new FavoritosAdapter(this, listaFavoritos, usuarioActual);
        recyclerFavoritos.setAdapter(favoritosAdapter);
    }

    private List<Pelicula> obtenerFavoritosDesdePreferencias() {
        FavoritosManager favoritosManager = new FavoritosManager(this);
        if (usuarioActual == null) return new ArrayList<>();
        return favoritosManager.obtenerFavoritos(usuarioActual.getId());
    }

    private void cargarFavoritosDesdeApi() {
        ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);

        apiService.getFavoritosPorUsuario(usuarioActual.getId()).enqueue(new retrofit2.Callback<List<Favorito>>() {
            @Override
            public void onResponse(Call<List<Favorito>> call, Response<List<Favorito>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Favorito> favoritosApi = response.body();
                    listaFavoritos = new ArrayList<>();

                    for (Favorito fav : favoritosApi) {
                        Pelicula peli = new Pelicula();
                        peli.setId(fav.getPelicula_id());
                        peli.setTitulo(fav.getTitulo_pelicula());
                        peli.setUrlPortada(fav.getUrl_portada());
                        listaFavoritos.add(peli);
                    }

                    favoritosAdapter.setListaFavoritos(listaFavoritos);
                    favoritosAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(FavoritosActivity.this, "No se pudieron cargar los favoritos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Favorito>> call, Throwable t) {
                Toast.makeText(FavoritosActivity.this, "Error de red al cargar favoritos", Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", "Error al cargar favoritos: " + t.getMessage());
            }
        });
    }
}