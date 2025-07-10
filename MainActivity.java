package com.sise.pelixy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.sise.pelixy.activity.CrearCuentaActivity;
import com.sise.pelixy.activity.MenuActivity;
import com.sise.pelixy.api.ApiClient;
import com.sise.pelixy.api.ApiService;
import com.sise.pelixy.models.response.LoginResponse;
import com.sise.pelixy.models.Usuario;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText etCorreoLogin, etPasswordLogin;
    private Button btnIngresar, btnCrearCuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Inicializa los campos
        etCorreoLogin = findViewById(R.id.etCorreoLogin);
        etPasswordLogin = findViewById(R.id.etPasswordLogin);
        btnIngresar = findViewById(R.id.btnlogin);
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta);

        // Listener del botón de ingreso
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo = etCorreoLogin.getText().toString().trim();
                String password = etPasswordLogin.getText().toString().trim();

                if (correo.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Por favor completa los campos.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Llamada Retrofit al backend
                ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
                Map<String, String> credentials = new HashMap<>();
                credentials.put("correo", correo);
                credentials.put("password", password);

                Call<LoginResponse> call = apiService.loginUsuario(credentials);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Usuario usuario = response.body().getUsuario();
                            String token = response.body().getToken();

                            Toast.makeText(MainActivity.this, "Bienvenido, " + usuario.getNombre(), Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                            intent.putExtra("usuario", usuario); // Usuario debe implementar Serializable
                            intent.putExtra("token", token);     // Puedes pasarlo también si lo vas a usar luego
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "Correo o contraseña incorrectos.", Toast.LENGTH_SHORT).show();
                            Log.e("LOGIN_RESPONSE", "Error: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("LOGIN_ERROR", "Excepción:", t);
                    }
                });
            }
        });

        // Listener del botón para crear cuenta
        btnCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CrearCuentaActivity.class);
                startActivity(intent);
            }
        });
    }
}