package com.example.trabajopracticoinmobiliaria.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.trabajopracticoinmobiliaria.modelo.Inmueble;
import com.example.trabajopracticoinmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleDetalleViewModel extends AndroidViewModel {

    private MutableLiveData<Inmueble> inmuebleM= new MutableLiveData<>();;
    private Context context;
    private Inmueble i;
    private MutableLiveData<String> error;

    public InmuebleDetalleViewModel(@NonNull Application application) {
        super(application);
        context=application.getApplicationContext();
    }

    public LiveData<Inmueble> getInmuebleM() {
        if(inmuebleM == null){
            inmuebleM = new MutableLiveData<>();
        }
        return inmuebleM;
    }

    public void setInmueble(Inmueble inmueble){
        inmuebleM.setValue(inmueble);
    }
    public LiveData<String> getError() {
        if(error == null)
        {
            error = new MutableLiveData<>();
        }
        return error;
    }

    public void editarInmueble(Inmueble inmu){

        String token= ApiClient.leerToken(context);
        ApiClient.MyApiInterface servicio= ApiClient.getServicio();

        Call<Inmueble> inmueble= servicio.actualizarInmueble("Bearer " + token,inmu);
        inmueble.enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {

                if(response.isSuccessful()){
                    inmuebleM.setValue(response.body());
                    Toast.makeText(context,"Se ha actualizado el estado del Inmueble!", Toast.LENGTH_LONG).show();
                }
                else {
                    error.setValue("No existen inmuebles");
                }
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable t) {

                Toast.makeText(context,"Ha ocurrido un error",Toast.LENGTH_LONG).show();
            }
        });
    }
}
