package com.sise.pelixy.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sise.pelixy.R;
import com.sise.pelixy.activity.ComentarioActivity;
import com.sise.pelixy.activity.DetailsActivity;
import com.sise.pelixy.api.ApiClient;
import com.sise.pelixy.api.ApiService;
import com.sise.pelixy.models.Pelicula;
import com.sise.pelixy.models.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritosAdapter extends RecyclerView.Adapter<FavoritosAdapter.FavoritoViewHolder> {

    private Context context;
    private List<Pelicula> listaFavoritos;
    private Usuario usuario;

    public FavoritosAdapter(Context context, List<Pelicula> listaFavoritos, Usuario usuario) {
        this.context = context;
        this.listaFavoritos = listaFavoritos;
        this.usuario = usuario;
    }

    public void setListaFavoritos(List<Pelicula> nuevosFavoritos) {
        this.listaFavoritos = nuevosFavoritos;
    }

    @NonNull
    @Override
    public FavoritoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(context).inflate(R.layout.item_favorito, parent, false);
        return new FavoritoViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritoViewHolder holder, int position) {
        Pelicula peli = listaFavoritos.get(position);

        holder.tvTitulo.setText(peli.getTitulo());

        Glide.with(context)
                .load(peli.getUrlPortada())
                .placeholder(R.drawable.placeholder)
                .into(holder.imgFavorito);

        holder.btnVer.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("pelicula", peli);
            intent.putExtra("usuario", usuario);
            context.startActivity(intent);
        });

        holder.btnCalificar.setOnClickListener(v -> {
            Intent intent = new Intent(context, ComentarioActivity.class);
            intent.putExtra("pelicula", peli);
            intent.putExtra("usuario", usuario);
            context.startActivity(intent);
        });

        holder.btnEliminar.setOnClickListener(v -> {
            ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);

            Call<Void> call = apiService.eliminarFavoritoPorUsuarioYPelicula(
                    usuario.getId(),
                    peli.getId()
            );

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "Favorito eliminado", Toast.LENGTH_SHORT).show();
                        int index = holder.getAdapterPosition();
                        listaFavoritos.remove(index);
                        notifyItemRemoved(index);
                        notifyItemRangeChanged(index, listaFavoritos.size());
                    } else {
                        Toast.makeText(context, "Error al eliminar favorito", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                    Toast.makeText(context, "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return listaFavoritos.size();
    }

    public class FavoritoViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo;
        ImageView imgFavorito;
        Button btnVer, btnEliminar, btnCalificar;

        public FavoritoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.txtTituloFavorito);
            imgFavorito = itemView.findViewById(R.id.imgFavorito);
            btnVer = itemView.findViewById(R.id.btnVer);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
            btnCalificar = itemView.findViewById(R.id.btnCalificar);
        }
    }
}