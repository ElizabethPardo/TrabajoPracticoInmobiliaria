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
    MutableLiveData<String> mensaje;
    private Context context;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        context= application.getApplicationContext();

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

    public void recuperarDatos(String usuario, String clave)
    {

        if(usuario.isEmpty() || clave.isEmpty())
        {
            mensaje.setValue("Por favor, complete los campos");
        }
        else{
            ApiClient.MyApiInterface servicio= ApiClient.getServicio();
            Call<String> call= servicio.loginForm(usuario,clave);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.d("LOGIN", "CODIGO: " + response.code());
                    if (response.isSuccessful()) {
                        String token=response.body();
                        Log.d("LOGIN", "TOKEN OK");
                        ApiClient.crearToken(context,token);
//                        Intent intent= new Intent(context, MainActivity.class);
//                        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
//                        context.startActivity(intent);
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

}