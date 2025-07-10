package com.sise.pelixy.api;

import com.sise.pelixy.models.Comentario;
import com.sise.pelixy.models.request.ComentarioRequest;
import com.sise.pelixy.models.Favorito;
import com.sise.pelixy.models.estado.FavoritoEstado;
import com.sise.pelixy.models.request.FavoritoRequest;
import com.sise.pelixy.models.response.LoginResponse;
import com.sise.pelixy.models.Pelicula;
import com.sise.pelixy.models.response.TotalResponse;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.Part;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @Multipart
    @POST("/api/v1/usuarios/register")
    Call<Void> registrarUsuarioConAvatar(
            @Part("nombre") RequestBody nombre,
            @Part("apellido") RequestBody apellido,
            @Part("correo") RequestBody correo,
            @Part("password") RequestBody password,
            @Part("fecha_nacimiento") RequestBody fechaNacimiento,
            @Part("pais") RequestBody pais,
            @Part MultipartBody.Part avatar // ← Este es el archivo de imagen
    );

    @POST("/api/v1/usuarios/login")
    Call<LoginResponse> loginUsuario(@Body Map<String, String> credentials);

    @GET("/api/v1/peliculas")
    Call<List<Pelicula>> getPeliculas();

    @GET("/api/v1/peliculas/{id}")
    Call<Pelicula> getPeliculaPorId(@Path("id") int id);

    @POST("comentarios")
    Call<Void> crearComentario(@Body ComentarioRequest comentario);

    @GET("/api/v1/comentarios/{pelicula_id}")
    Call<List<Comentario>> getComentariosPorPelicula(@Path("pelicula_id") int peliculaId);

    // Obtener todos los favoritos de todos los usuarios (opcional)
    @GET("/api/v1/favoritos")
    Call<List<Favorito>> getTodosLosFavoritos();

    // Obtener los favoritos de un usuario específico
    @GET("/api/v1/favoritos/usuario/{usuarioId}")
    Call<List<Favorito>> getFavoritosPorUsuario(@Path("usuarioId") int usuarioId);

    // Verificar si una película está en favoritos
    @GET("/api/v1/favoritos/verificar/{usuarioId}/{peliculaId}")
    Call<FavoritoEstado> verificarFavorito(
            @Path("usuarioId") int usuarioId,
            @Path("peliculaId") int peliculaId
    );

    // Agregar un favorito
    @POST("/api/v1/favoritos")
    Call<Void> agregarFavorito(@Body FavoritoRequest favorito);

    @DELETE("/api/v1/favoritos/eliminar/{usuarioId}/{peliculaId}")
    Call<Void> eliminarFavoritoPorUsuarioYPelicula(
            @Path("usuarioId") int usuarioId,
            @Path("peliculaId") int peliculaId
    );

    // Eliminar un favorito por ID
    @DELETE("/api/v1/favoritos/{id}")
    Call<Void> eliminarFavorito(@Path("id") int idFavorito);

    // Obtener el total de favoritos
    @GET("/api/v1/favoritos/totales/favoritos")
    Call<TotalResponse> getTotalFavoritos();


}