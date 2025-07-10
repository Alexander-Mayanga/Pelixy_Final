package com.sise.pelixy.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.sise.pelixy.MainActivity;
import com.sise.pelixy.R;
import com.sise.pelixy.api.ApiClient;
import com.sise.pelixy.api.ApiService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearCuentaActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri avatarUri;
    private ImageView ivAvatarPreview;

    private EditText etNombre, etApellido, etCorreo, etPassword, etFechaNacimiento, etPais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crear_cuenta);

        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etCorreo = findViewById(R.id.etCorreo);
        etPassword = findViewById(R.id.etPassword);
        etFechaNacimiento = findViewById(R.id.etFechaNacimiento);
        etPais = findViewById(R.id.etPais);
        Button btnCrearCuenta = findViewById(R.id.btnCrearCuenta);
        Button btnSeleccionarAvatar = findViewById(R.id.btnSeleccionarAvatar);
        ivAvatarPreview = findViewById(R.id.ivAvatarPreview);

        btnSeleccionarAvatar.setOnClickListener(v -> abrirGaleria());

        btnCrearCuenta.setOnClickListener(v -> registrarUsuarioConImagen());
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            avatarUri = data.getData();
            ivAvatarPreview.setImageURI(avatarUri);
        }
    }

    private void registrarUsuarioConImagen() {
        String nombre = etNombre.getText().toString().trim();
        String apellido = etApellido.getText().toString().trim();
        String correo = etCorreo.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String fechaNacimiento = etFechaNacimiento.getText().toString().trim();
        String pais = etPais.getText().toString().trim();

        if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || password.isEmpty()
                || fechaNacimiento.isEmpty() || pais.isEmpty() || avatarUri == null) {
            Toast.makeText(this, "Por favor completa todos los campos e imagen", Toast.LENGTH_SHORT).show();
            return;
        }

        String path = guardarImagenInterna(avatarUri);
        if (path == null) {
            Toast.makeText(this, "Error al guardar imagen", Toast.LENGTH_SHORT).show();
            return;
        }

        File file = new File(path);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part avatarPart = MultipartBody.Part.createFormData("avatar", file.getName(), reqFile);

        // Crear cada campo como RequestBody
        RequestBody nombrePart = RequestBody.create(MediaType.parse("text/plain"), nombre);
        RequestBody apellidoPart = RequestBody.create(MediaType.parse("text/plain"), apellido);
        RequestBody correoPart = RequestBody.create(MediaType.parse("text/plain"), correo);
        RequestBody passwordPart = RequestBody.create(MediaType.parse("text/plain"), password);
        RequestBody fechaPart = RequestBody.create(MediaType.parse("text/plain"), fechaNacimiento);
        RequestBody paisPart = RequestBody.create(MediaType.parse("text/plain"), pais);

        // Llamada a Retrofit
        ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
        Call<Void> call = apiService.registrarUsuarioConAvatar(
                nombrePart, apellidoPart, correoPart, passwordPart, fechaPart, paisPart, avatarPart
        );

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CrearCuentaActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CrearCuentaActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(CrearCuentaActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(CrearCuentaActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("REGISTRO", "Fallo en la llamada", t);
            }
        });
    }

    private String guardarImagenInterna(Uri uriOriginal) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uriOriginal);
            if (inputStream == null) return null;

            File avatarFile = new File(getFilesDir(), "avatar_" + System.currentTimeMillis() + ".jpg");
            FileOutputStream outputStream = new FileOutputStream(avatarFile);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.close();
            inputStream.close();

            return avatarFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}