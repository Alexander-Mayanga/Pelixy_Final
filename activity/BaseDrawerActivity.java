package com.sise.pelixy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.widget.FrameLayout;

import com.sise.pelixy.R;
import com.sise.pelixy.models.Usuario;

import com.google.android.material.navigation.NavigationView;

public class BaseDrawerActivity extends AppCompatActivity {

    protected DrawerLayout drawerLayout;
    protected NavigationView navView;

    protected Usuario usuarioActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base_drawer);

        drawerLayout = findViewById(R.id.drawerLayout);
        navView = findViewById(R.id.navView);

        navView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_inicio) {
                Intent intent = new Intent(this, MenuActivity.class);
                intent.putExtra("usuario", usuarioActual);
                startActivity(intent);

            } else if (id == R.id.nav_favoritos) {
                Intent intent = new Intent(this, FavoritosActivity.class);
                intent.putExtra("usuario", usuarioActual);
                startActivity(intent);
            } else if (id == R.id.nav_miperfil) {
                Intent intent = new Intent(this, PerfilUsuarioActivity.class);
                String nombreCompleto = usuarioActual.getNombre() + " " + usuarioActual.getApellido();
                intent.putExtra("nombreCompleto", nombreCompleto);
                intent.putExtra("correo", usuarioActual.getCorreo());

                startActivity(intent);
            } else if (id == R.id.nav_salir) {
                finish();
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    protected void setActivityLayout(int layoutResID) {
        FrameLayout contentFrame = findViewById(R.id.content_frame);
        View view = LayoutInflater.from(this).inflate(layoutResID, contentFrame, false);
        contentFrame.addView(view);
    }
}