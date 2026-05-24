package com.example.trabajopracticoinmobiliaria.ui.inquilinos;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.trabajopracticoinmobiliaria.modelo.Inmueble;
import com.example.trabajopracticoinmobiliaria.request.ApiClient;

import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquilinoViewModel extends AndroidViewModel {

    private MutableLiveData<List<Inmueble>> lista;
    private MutableLiveData<List<Inmueble>> inmueblesMutable;
    private Context context;
    private MutableLiveData<String> error;

    public InquilinoViewModel(@NonNull Application application) {
        super(application);
        context=application.getApplicationContext();
    }

    public LiveData<List<Inmueble>> getInmuebles() {

        if(inmueblesMutable== null)
        {
            inmueblesMutable= new MutableLiveData<>();
        }
        return inmueblesMutable;
    }
    public LiveData<List<Inmueble>> getLista() {
        if(lista == null){
            lista = new MutableLiveData<>();
        }
        return lista;
    }

    public LiveData<String> getError() {
        if(error == null)
        {
            error = new MutableLiveData<>();
        }
        return error;
    }

    public void cargarInmueble(){

        String token= ApiClient.leerToken(context);
        ApiClient.MyApiInterface servicio= ApiClient.getServicio();
        Call<List<Inmueble>> inmuebles= servicio.obtenerInmueblesAlquilados(token);

        inmuebles.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if(response.isSuccessful()){
                    inmueblesMutable.postValue(response.body());
                }
                else {
                    error.setValue("No existen inmuebles");
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {

                Toast.makeText(context,"Ha ocurrido un error", Toast.LENGTH_LONG).show();
            }
        });

    }
}