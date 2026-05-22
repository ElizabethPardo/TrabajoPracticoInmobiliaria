package com.example.trabajopracticoinmobiliaria.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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

    private MutableLiveData<Inmueble> inmuebleM = new MutableLiveData<>();
    ;
    private Context context;
    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<String> mensajeDis;

    public InmuebleDetalleViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Inmueble> getInmuebleM() {
        if (inmuebleM == null) {
            inmuebleM = new MutableLiveData<>();
        }
        return inmuebleM;
    }

    public void setInmueble(Inmueble inmueble) {
        inmuebleM.setValue(inmueble);
    }

    public LiveData<String> getError() {
        if (error == null) {
            error = new MutableLiveData<>();
        }
        return error;
    }

    public LiveData<String> getMensajeDis() {
        if (mensajeDis == null) {
            mensajeDis = new MutableLiveData<>();
        }
        return mensajeDis;
    }

    private void actualizarEstado(boolean estado) {
        if (estado) {
            mensajeDis.postValue("Disponible");
        } else {
            mensajeDis.postValue("No disponible");
        }
    }

    public void cargarInmueble(Inmueble inmueble) {
        this.inmuebleM.setValue(inmueble);
        actualizarEstado(inmueble.getDisponible());
    }

    public void editarInmueble(Boolean disponible) {

        Inmueble inmu = inmuebleM.getValue();
        if (inmu != null) {
            inmu.setDisponible(disponible);

            String token = ApiClient.leerToken(context);
            ApiClient.MyApiInterface servicio = ApiClient.getServicio();

            Call<Inmueble> inmueble = servicio.actualizarInmueble(token, inmu);

            inmueble.enqueue(new Callback<Inmueble>() {
                @Override
                public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {

                    if (response.isSuccessful() && response.body() != null) {
                        Inmueble inmuebleRepo = response.body();
                        inmuebleM.setValue(response.body());
                        actualizarEstado(inmuebleRepo.getDisponible());
                        Toast.makeText(context, "Estado del inmueble actualizado correctamente", Toast.LENGTH_SHORT).show();

                    } else {

                        try {
                            error.setValue(response.errorBody().string());
                            Toast.makeText(context, "Error al actualizar", Toast.LENGTH_LONG).show();

                        } catch (Exception e) {

                            error.setValue(e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<Inmueble> call, Throwable t) {

                    Toast.makeText(context, "Ha ocurrido un error", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}