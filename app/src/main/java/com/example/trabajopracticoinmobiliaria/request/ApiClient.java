package com.example.trabajopracticoinmobiliaria.request;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.trabajopracticoinmobiliaria.modelo.Inmueble;
import com.example.trabajopracticoinmobiliaria.modelo.Propietario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public class ApiClient {

    private static final String PATH="https://capacitacion.alwaysdata.net/";
    public  static  MyApiInterface myApiInterface;
    public static MyApiInterface getServicio() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PATH)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        myApiInterface = retrofit.create(MyApiInterface.class);

        return myApiInterface;
    }

    public interface MyApiInterface
    {
        @FormUrlEncoded
        @POST("api/Propietarios/login")
        Call<String> loginForm(@Field("Usuario") String Usuario, @Field("Clave") String Clave);

        @GET("api/Propietarios")
        Call<Propietario> miPerfil(@Header("Authorization") String token);

        @PUT("api/Propietarios/actualizar") Call<Propietario> actualizarPerfil(@Header("Authorization") String token, @Body Propietario propietario);

        @FormUrlEncoded
        @POST("api/Propietarios/email") Call<String> resetearPassword(@Field("email") String email);

        @FormUrlEncoded
        @PUT("api/Propietarios/changePassword")
        Call<Void> cambiarPassword(@Header("Authorization") String token, @Field("currentPassword") String claveActual, @Field("newPassword") String claveNueva);
        @GET("api/Inmuebles")
        Call<List<Inmueble>> obtenerInmuebles(@Header("Authorization") String token);
        @GET("api/Inmuebles/GetContratoVigente") Call<List<Inmueble>> obtenerInmueblesAlquilados(@Header("Authorization") String token);
        @PUT("api/Inmuebles/actualizar")
        Call<Inmueble> actualizarInmueble(@Header("Authorization") String token, @Body Inmueble inmueble);

        @Multipart
        @POST("api/Inmuebles/cargar")
        Call<Inmueble> cargarInmuebles(@Header("Authorization") String token, @Part MultipartBody.Part imagen, @Part("inmueble") RequestBody inmueble);
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
