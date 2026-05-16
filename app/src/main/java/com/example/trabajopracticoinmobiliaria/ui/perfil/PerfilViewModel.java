package com.example.trabajopracticoinmobiliaria.ui.perfil;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.trabajopracticoinmobiliaria.modelo.Propietario;
import com.example.trabajopracticoinmobiliaria.request.ApiClient;
import com.example.trabajopracticoinmobiliaria.ui.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {

    private MutableLiveData<Propietario> propietarioM;
    private MutableLiveData<String> error = new MutableLiveData<>();
    private Context context;

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }


    public LiveData<Propietario> getPropietarioM() {

        if(propietarioM == null)
            propietarioM= new MutableLiveData<>();

        return propietarioM;
    }
    public LiveData<String> getError() {
        if(error == null)
        {
            error = new MutableLiveData<>();
        }
        return error;
    }


    public void cargarPerfil()
    {
        String token= ApiClient.leerToken(context);
        ApiClient.MyApiInterface servicio= ApiClient.getServicio();
        Call<Propietario> propietario =
                servicio.miPerfil("Bearer " + token);
        propietario.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if(response.isSuccessful()){
                    propietarioM.postValue(response.body());
                }
                else{
                    error.setValue("Perfil no encontrado");
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {


                Toast.makeText(context,"Ha ocurrido un error"+ t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });


    }

    public void editarPerfil(Propietario prop)
    {
        String token= ApiClient.leerToken(context);
        ApiClient.MyApiInterface servicio= ApiClient.getServicio();
        Call<Propietario> propietarios= servicio.actualizarPerfil("Bearer " +token,prop);

        propietarios.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {

                if(response.isSuccessful()){
                    if(response.body() != null) {
                        propietarioM.setValue(response.body());
                        Toast.makeText(context,"Datos actualizados!",Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(context,"No hay datos para actualizar",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    error.setValue("No se pudo modificar el perfil");
                }

            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {

                Toast.makeText(context,"Ha ocurrido un error"+ t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    public void cambiarPass(String claveActual,String claveNueva)
    {
        String token= ApiClient.leerToken(context);
        ApiClient.MyApiInterface servicio= ApiClient.getServicio();
        Call <Void> call=  servicio.cambiarPassword("Bearer " + token,claveActual,claveNueva);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                   // propietarioM.postValue(response.body());
                    Toast.makeText(context,"Contraseña actualizada!",Toast.LENGTH_LONG).show();
                    ApiClient.crearToken(context, "");


                    Intent intent = new Intent(context, LoginActivity.class);

                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);
                }
                else{
                    error.setValue("No se puede cambiar contraseña");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {


                Toast.makeText(context,"Ha ocurrido un error"+ t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
