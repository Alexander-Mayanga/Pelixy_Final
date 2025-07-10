package com.sise.pelixy.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    // URL base de tu backend (asegúrate de que incluya /api/ si lo tienes en tu backend Express)
    private static final String BASE_URL = "http://10.0.2.2:3000/api/v1/";

    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)  // DEBE terminar en / para Retrofit
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}