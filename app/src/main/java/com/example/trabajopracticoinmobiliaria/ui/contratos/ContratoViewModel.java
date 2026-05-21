package com.example.trabajopracticoinmobiliaria.ui.contratos;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.trabajopracticoinmobiliaria.modelo.Contrato;
import com.example.trabajopracticoinmobiliaria.modelo.Inmueble;
import com.example.trabajopracticoinmobiliaria.request.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratoViewModel extends AndroidViewModel {

    private MutableLiveData<List<Inmueble>> inmueblesMutable;
    private MutableLiveData<String> error = new MutableLiveData<>();;
    private Context context;

    public ContratoViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        inmueblesMutable = new MutableLiveData<>();
    }

    public LiveData<List<Inmueble>> getInmueblesMutable() {

        if(inmueblesMutable == null){
            inmueblesMutable = new MutableLiveData<>();
        }

        return inmueblesMutable;
    }

    public LiveData<String> getError() {

        if(error == null){
            error = new MutableLiveData<>();
        }

        return error;
    }

    public void cargarContratosVig(){

        String token = "Bearer " + ApiClient.leerToken(context);

        ApiClient.MyApiInterface servicio = ApiClient.getServicio();

        Call<List<Inmueble>> llamada = servicio.obtenerInmueblesAlquilados(token);

        llamada.enqueue(new Callback<List<Inmueble>>() {

            @Override
            public void onResponse(Call<List<Inmueble>> call,
                                   Response<List<Inmueble>> response) {

                if(response.isSuccessful() && response.body() != null){

                    inmueblesMutable.setValue(response.body());

                }else{

                    error.setValue("No se pudieron cargar los inmuebles");
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {

                error.setValue(t.getMessage());
            }
        });
    }
}