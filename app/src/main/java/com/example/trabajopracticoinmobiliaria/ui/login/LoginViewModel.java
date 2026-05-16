package com.example.trabajopracticoinmobiliaria.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.trabajopracticoinmobiliaria.MainActivity;
import com.example.trabajopracticoinmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {

    MutableLiveData<Boolean> usuarioM;
    private MutableLiveData<String> mensaje = new MutableLiveData<>();
    private MutableLiveData<Boolean> resetMutable = new MutableLiveData<>();;
    private MutableLiveData<Boolean> estadoM;
    private MutableLiveData<String> error;
    private int activador = 0;
    private Context context;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        context= application.getApplicationContext();
        usuarioM= new MutableLiveData<>();
        error = new MutableLiveData<>();
        estadoM = new MutableLiveData<>();
    }
    public LiveData<String> getMensaje()
    {
        if(mensaje == null)
            mensaje= new MutableLiveData<>();

        return mensaje;
    }
    public LiveData<Boolean> getUsuarioM()
    {
        if(usuarioM == null)
            usuarioM= new MutableLiveData<>();

        return usuarioM;
    }

    public LiveData<String> getError() {
        if(error == null)
        {
            error = new MutableLiveData<>();
        }
        return error;
    }

    public MutableLiveData<Boolean> getResetMutable() {

        if(resetMutable == null)
        {
            resetMutable = new MutableLiveData<>();
        }
        return resetMutable;
    }
    public LiveData<Boolean> getEstadoM() {
        if(estadoM == null){
            estadoM = new MutableLiveData<>();
        }
        return estadoM;
    }

    public void recuperarDatos(String Usuario, String Clave)
    {

        if(Usuario.isEmpty() || Clave.isEmpty())
        {
            mensaje.setValue("Por favor, complete los campos");
        }
        else{
            ApiClient.MyApiInterface servicio= ApiClient.getServicio();
            Call<String> call= servicio.loginForm(Usuario,Clave);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.d("LOGIN", "CODIGO: " + response.code());
                    try {
                        Log.d("LOGIN_ERROR", response.errorBody().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (response.isSuccessful()) {
                        String token=response.body();
                        Log.d("LOGIN", "TOKEN OK");
                        ApiClient.crearToken(context,token);
                        usuarioM.setValue(true);


                    } else {
                        Log.d("Error",response.message());
                        mensaje.setValue("El usuario o contraseña son incorrectos");
                    }


                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d("Mensaje",t.getMessage());
                }
            });
        }


    }

    public void sensorG(float x){
        if(x > 1 || x < -1){
            activador++;
        }
        if(activador > 20){
            activador = 0;
            estadoM.setValue(true);
        }
    }

    public void resetClave(String email){

        ApiClient.MyApiInterface api = ApiClient.getServicio();

        Call<String> call = api.resetearPassword(email);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("RESPUESTA", "codigo: " + response.code());

                if(response.isSuccessful() && response.body() != null){
                    Toast.makeText(context,
                            response.body(),
                            Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(context,
                            "Error al enviar correo",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(context,
                        t.getMessage(),
                        Toast.LENGTH_LONG).show();

                Log.d("SALIDA", t.getMessage());
            }
        });
    }

}