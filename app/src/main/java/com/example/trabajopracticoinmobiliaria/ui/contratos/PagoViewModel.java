package com.example.trabajopracticoinmobiliaria.ui.contratos;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.trabajopracticoinmobiliaria.modelo.Contrato;
import com.example.trabajopracticoinmobiliaria.modelo.Pago;
import com.example.trabajopracticoinmobiliaria.request.ApiClient;

import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagoViewModel extends AndroidViewModel {
    private MutableLiveData<List<Pago>> pagosM;

    private Context context;
    private MutableLiveData<String> error;
    public PagoViewModel(@NonNull Application application) {
        super(application);
        context=application.getApplicationContext();
    }

    public LiveData<List<Pago>> getPagosM() {
        if(pagosM == null){
            pagosM = new MutableLiveData<>();
        }
        return pagosM;
    }

    public LiveData<String> getError() {
        if(error == null)
        {
            error = new MutableLiveData<>();
        }
        return error;
    }
    public void cargarPagos(Bundle bundle){

        int idContrato = bundle.getInt("idContrato");

        String token = ApiClient.leerToken(context);
        ApiClient.MyApiInterface servicio = ApiClient.getServicio();
        Call<List<Pago>> pagos= servicio.obtenerPagosPorContrato(token,idContrato);

        pagos.enqueue(new Callback<List<Pago>>() {
            @Override
            public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                if (response.isSuccessful())
                {
                    if(response.body().size() != 0) {
                        pagosM.postValue(response.body());
                    }
                    else{
                        error.postValue("No tiene pagos");
                    }
                }
                else{
                    error.postValue("No hubo respuesta");
                }
            }

            @Override
            public void onFailure(Call<List<Pago>> call, Throwable t) {

                error.setValue("No existen Pagos");

            }
        });

    }
}
