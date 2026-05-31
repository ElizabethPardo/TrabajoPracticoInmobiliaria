package com.example.trabajopracticoinmobiliaria.ui.contratos;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.trabajopracticoinmobiliaria.modelo.Contrato;
import com.example.trabajopracticoinmobiliaria.request.ApiClient;

import org.jspecify.annotations.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratoDetalleViewModel  extends AndroidViewModel {
    private MutableLiveData<Contrato> contratoM;
    private Context context;
    public ContratoDetalleViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Contrato> getContratoM() {
        if(contratoM == null){
            contratoM = new MutableLiveData<>();
        }
        return contratoM;
    }

    public void buscarContrato(int idInmueble){

        String token = ApiClient.leerToken(context);

        ApiClient.MyApiInterface servicio = ApiClient.getServicio();

        Call<Contrato> llamada = servicio.obtenerContratoPorInmueble(token, idInmueble);

        llamada.enqueue(new Callback<Contrato>() {

            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {

                if(response.isSuccessful() && response.body() != null){

                    contratoM.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {

            }
        });
    }

}
