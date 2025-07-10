package com.sise.pelixy.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;

import com.sise.pelixy.R;

public class PerfilUsuarioActivity extends BaseDrawerActivity {

    TextView textNombreUsuario, textCorreo;
    ImageView imagePerfil;
    Button btnCambiarFoto, btnCambiarContrasena, btnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setActivityLayout(R.layout.activity_perfil_usuario);

        // Vinculamos vistas
        textNombreUsuario = findViewById(R.id.textNombreUsuario);
        textCorreo = findViewById(R.id.textCorreo);
        imagePerfil = findViewById(R.id.imagePerfil);
        btnCambiarFoto = findViewById(R.id.btnCambiarFoto);
        btnCambiarContrasena = findViewById(R.id.btnCambiarContrasena);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        // Obtener datos del intent
        Intent intent = getIntent();
        String nombreCompleto = intent.getStringExtra("nombreCompleto");
        String correo = intent.getStringExtra("correo");

        if (nombreCompleto != null) textNombreUsuario.setText(nombreCompleto);
        if (correo != null) textCorreo.setText(correo);

        btnCambiarFoto.setOnClickListener(v -> {
            Toast.makeText(this, "Función aún no disponible", Toast.LENGTH_SHORT).show();
        });

        btnCambiarContrasena.setOnClickListener(v -> {
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_cambiar_contrasena, null);
            EditText editActual = dialogView.findViewById(R.id.editContraseñaActual);
            EditText editNueva = dialogView.findViewById(R.id.editNuevaContraseña);
            Button btnGuardar = dialogView.findViewById(R.id.btnGuardar);

            AlertDialog dialog = new AlertDialog.Builder(PerfilUsuarioActivity.this)
                    .setView(dialogView)
                    .setCancelable(true)
                    .create();

            btnGuardar.setOnClickListener(view -> {
                String actual = editActual.getText().toString();
                String nueva = editNueva.getText().toString();

                if (actual.isEmpty() || nueva.isEmpty()) {
                    Toast.makeText(this, "Completa ambos campos", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Contraseña cambiada (simulado)", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });

            dialog.show();
        });

        btnCerrarSesion.setOnClickListener(v -> {
            // Agregar logica para cerrar sesion
        });
    }
}
