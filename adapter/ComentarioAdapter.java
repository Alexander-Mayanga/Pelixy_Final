package com.sise.pelixy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sise.pelixy.R;
import com.sise.pelixy.models.Comentario;

import java.util.List;

public class ComentarioAdapter extends RecyclerView.Adapter<ComentarioAdapter.ViewHolder> {

    private List<Comentario> listaComentarios;
    private Context context;

    public ComentarioAdapter(List<Comentario> listaComentarios, Context context) {
        this.listaComentarios = listaComentarios;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar;
        TextView tvNombreUsuario, tvComentario, tvFecha;

        public ViewHolder(View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.ivAvatar);
            tvNombreUsuario = itemView.findViewById(R.id.tvNombreUsuario);
            tvComentario = itemView.findViewById(R.id.tvComentario);
            tvFecha = itemView.findViewById(R.id.tvFechaComentario);
        }
    }

    @Override
    public ComentarioAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_comentario, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ComentarioAdapter.ViewHolder holder, int position) {
        Comentario comentario = listaComentarios.get(position);

        holder.tvNombreUsuario.setText(comentario.getNombreUsuario());
        holder.tvComentario.setText(comentario.getComentario());
        holder.tvFecha.setText(comentario.getFechaComentario());

        String avatarUrl = comentario.getAvatarUri();
        if (avatarUrl != null && !avatarUrl.isEmpty()) {
            if (!avatarUrl.startsWith("http")) {
                avatarUrl = "http://192.168.1.10:3000/" + avatarUrl;
            }
            Glide.with(context)
                    .load(avatarUrl)
                    .placeholder(R.drawable.usuario_default)
                    .error(R.drawable.usuario_default)
                    .into(holder.ivAvatar);
        } else {
            holder.ivAvatar.setImageResource(R.drawable.usuario_default);
        }
    }

    @Override
    public int getItemCount() {
        return listaComentarios.size();
    }
}
