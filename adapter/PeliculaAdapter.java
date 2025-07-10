package com.sise.pelixy.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sise.pelixy.R;
import com.sise.pelixy.activity.DetailsActivity;
import com.sise.pelixy.models.Pelicula;

import java.util.List;

public class PeliculaAdapter extends RecyclerView.Adapter<PeliculaAdapter.ViewHolder> {

    private Context context;
    private List<Pelicula> listaPeliculas;

    public PeliculaAdapter(Context context, List<Pelicula> listaPeliculas) {
        this.context = context;
        this.listaPeliculas = listaPeliculas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pelicula, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pelicula pelicula = listaPeliculas.get(position);

        holder.txtTitulo.setText(pelicula.getTitulo());
        holder.txtAnio.setText(String.valueOf(pelicula.getAnio()));
        holder.txtDuracion.setText(pelicula.getDuracion());
        holder.txtDescripcion.setText(pelicula.getDescripcion());
        holder.txtIdioma.setText(pelicula.getIdioma());
        holder.txtDirector.setText(pelicula.getDirector());
        holder.ratingBar.setRating(pelicula.getCalificacion());

        if (pelicula.tieneImagen()) {
            Glide.with(context)
                    .load(pelicula.getUrlPortada())
                    .placeholder(R.drawable.placeholder)
                    .into(holder.imgPelicula);
        } else {
            holder.imgPelicula.setImageResource(R.drawable.usuario_default);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("pelicula", pelicula);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listaPeliculas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPelicula;
        TextView txtTitulo, txtAnio, txtDuracion, txtDescripcion, txtIdioma, txtDirector;
        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPelicula = itemView.findViewById(R.id.imgPelicula);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            txtAnio = itemView.findViewById(R.id.txtAnio);
            txtDuracion = itemView.findViewById(R.id.txtDuracion);
            txtDescripcion = itemView.findViewById(R.id.txtDescripcion);
            txtIdioma = itemView.findViewById(R.id.txtIdioma);
            txtDirector = itemView.findViewById(R.id.txtDirector);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }
}
