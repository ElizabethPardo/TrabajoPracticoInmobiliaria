package com.example.trabajopracticoinmobiliaria.ui.inquilinos;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.trabajopracticoinmobiliaria.modelo.Contrato;
import com.example.trabajopracticoinmobiliaria.modelo.Inmueble;
import com.example.trabajopracticoinmobiliaria.modelo.Inquilino;
import com.example.trabajopracticoinmobiliaria.request.ApiClient;

import org.jspecify.annotations.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquilinoDetalleViewModel extends AndroidViewModel {

    private MutableLiveData<Contrato> inquilinoM;
    private Context context;
    private MutableLiveData<String> error;

    public InquilinoDetalleViewModel(@NonNull Application application) {
        super(application);
        context=application.getApplicationContext();
    }


    public LiveData<String> getError() {
        if(error == null)
        {
            error = new MutableLiveData<>();
        }
        return error;
    }
    public LiveData<Contrato> getInquilinoM() {
        if(inquilinoM == null){
            inquilinoM = new MutableLiveData<>();
        }
        return inquilinoM;
    }
    public void cargar(Bundle b){

        String token= ApiClient.leerToken(context);
        ApiClient.MyApiInterface servicio= ApiClient.getServicio();
        Inmueble inmu = (Inmueble) b.getSerializable("inmueble");

        Call<Contrato> inquilino=servicio.obtenerContratoPorInmueble(token, inmu.getId());

        inquilino.enqueue(new Callback<Contrato>() {
            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {

                if(response.isSuccessful()){
                    inquilinoM.setValue(response.body());
                }
                else {
                    error.setValue("No existe Inquilino");
                }
            }

            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {

                Toast.makeText(context,"Ha ocurrido un error",Toast.LENGTH_LONG).show();
            }
        });

    }


}
