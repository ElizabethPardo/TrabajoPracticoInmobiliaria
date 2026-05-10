package com.example.trabajopracticoinmobiliaria.ui.perfil;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.trabajopracticoinmobiliaria.modelo.Propietario;
import com.example.trabajopracticoinmobiliaria.request.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {

    private MutableLiveData<Propietario> propietarioM;
    private MutableLiveData<String> error;
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


    }
}