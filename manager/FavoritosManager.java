package com.sise.pelixy.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.sise.pelixy.models.Pelicula;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FavoritosManager {

    private static final String PREFS_NAME = "favoritos";
    private final SharedPreferences sharedPreferences;
    private final Gson gson = new Gson();

    public FavoritosManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void agregarFavoritoSimple(int userId, int peliculaId, String titulo, String urlPortada) {
        String clave = userId + "_" + peliculaId;

        Pelicula peli = new Pelicula();
        peli.setId(peliculaId);
        peli.setTitulo(titulo);
        peli.setUrlPortada(urlPortada);

        String json = gson.toJson(peli);
        sharedPreferences.edit().putString(clave, json).apply();
    }

    public void quitarFavorito(int userId, int peliculaId) {
        String clave = userId + "_" + peliculaId;
        sharedPreferences.edit().remove(clave).apply();
    }

    public boolean esFavorito(int userId, int peliculaId) {
        String clave = userId + "_" + peliculaId;
        return sharedPreferences.contains(clave);
    }

    public List<Pelicula> obtenerFavoritos(int userId) {
        List<Pelicula> lista = new ArrayList<>();
        Map<String, ?> todos = sharedPreferences.getAll();

        for (Map.Entry<String, ?> entry : todos.entrySet()) {
            String clave = entry.getKey();
            if (clave.startsWith(userId + "_")) {
                String json = (String) entry.getValue();
                Pelicula peli = gson.fromJson(json, Pelicula.class);
                lista.add(peli);
            }
        }

        return lista;
    }
}