package com.example.trabajopracticoinmobiliaria.request;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.trabajopracticoinmobiliaria.modelo.Inmueble;
import com.example.trabajopracticoinmobiliaria.modelo.Propietario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public class ApiClient {

    private static final String PATH="http://capacitacion.alwaysdata.net/";
    private static ApiClient api=null;
    public  static  MyApiInterface myApiInterface;
    public static MyApiInterface getServicio() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PATH)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        myApiInterface = retrofit.create(MyApiInterface.class);
        Log.d("salida", retrofit.baseUrl().toString());
        return myApiInterface;
    }

    public interface MyApiInterface
    {
        @FormUrlEncoded
        @POST("api/Propietarios/login")
        Call<String> loginForm(@Field("Usuario") String usuario, @Field("Clave") String clave);

        @GET("api/Propietarios")
        Call<Propietario> miPerfil(@Header("Authorization") String token);

        @GET("api/Inmuebles")
        Call<Inmueble> getInmueble(@Header("Authorization")String token);


    }

    public static void crearToken(Context context, String Token)
    {
        SharedPreferences sp = context.getSharedPreferences("token.dat", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("Token", Token);
        editor.commit();
    }
    public static String leerToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences("token.dat", Context.MODE_PRIVATE);
        return sp.getString("Token", null);
    }
}
