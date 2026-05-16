package com.example.trabajopracticoinmobiliaria.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.trabajopracticoinmobiliaria.modelo.Inmueble;

import com.example.trabajopracticoinmobiliaria.request.ApiClient;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleViewModel  extends AndroidViewModel {

    private MutableLiveData<String> error;
    private MutableLiveData<List<Inmueble>> inmueblesMutable;
    private Context context;


    public InmuebleViewModel(@NonNull Application application) {
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
    public LiveData<String> getError() {
        if(error == null)
        {
            error = new MutableLiveData<>();
        }
        return error;
    }

    public void cargarInmuebles(){

        String token = ApiClient.leerToken(context);
        ApiClient.MyApiInterface api = ApiClient.getServicio();
        Call<List<Inmueble>> call = api.obtenerInmuebles("Bearer " + token);
        call.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call,
                                   Response<List<Inmueble>> response) {

                if(response.isSuccessful() && response.body() != null){

                    inmueblesMutable.postValue(response.body());

                }else{

                    Toast.makeText(context,"Error al obtener inmuebles", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {

                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}