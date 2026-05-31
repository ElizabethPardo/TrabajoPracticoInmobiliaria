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
    private MutableLiveData<String> errorNombre;
    private MutableLiveData<String> errorApellido;
    private MutableLiveData<String> errorDni;
    private MutableLiveData<String> errorTelefono;
    private Context context;

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        propietarioM= new MutableLiveData<>();
        errorNombre = new MutableLiveData<>();
        errorApellido = new MutableLiveData<>();
        errorDni = new MutableLiveData<>();
        errorTelefono = new MutableLiveData<>();
    }


    public LiveData<Propietario> getPropietarioM() {

        return propietarioM;
    }
    public LiveData<String> getError() {
        if(error == null)
        {
            error = new MutableLiveData<>();
        }
        return error;
    }
    public LiveData<String> getErrorNombre() {
        return errorNombre;
    }

    public LiveData<String> getErrorApellido() {
        return errorApellido;
    }

    public LiveData<String> getErrorDni() {
        return errorDni;
    }

    public LiveData<String> getErrorTelefono() {
        return errorTelefono;
    }


    public void cargarPerfil()
    {
        String token= ApiClient.leerToken(context);
        ApiClient.MyApiInterface servicio= ApiClient.getServicio();
        Call<Propietario> propietario = servicio.miPerfil(token);
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
        Call<Propietario> propietarios= servicio.actualizarPerfil(token,prop);

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
        Call <Void> call=  servicio.cambiarPassword(token,claveActual,claveNueva);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){

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

    public boolean validarCampos(String nombre, String apellido, String dni, String telefono){

        errorNombre.setValue(null);
        errorApellido.setValue(null);
        errorDni.setValue(null);
        errorTelefono.setValue(null);

        boolean valido = true;

        if(nombre.isEmpty()){

            errorNombre.setValue("Ingrese un nombre");
            valido = false;
        }

        if(apellido.isEmpty()){

            errorApellido.setValue("Ingrese un apellido");
            valido = false;
        }

        if(dni.isEmpty()){

            errorDni.setValue("Ingrese un DNI");
            valido = false;
        }

        if(telefono.isEmpty()){

            errorTelefono.setValue("Ingrese un teléfono");
            valido = false;
        }

        return valido;
    }

    public boolean validarPassword(String claveActual, String claveNueva) {

        if(claveActual.isEmpty()){
            error.setValue("Ingrese la contraseña actual");
            return false;
        }

        if(claveNueva.isEmpty()){
            error.setValue("Ingrese la nueva contraseña");
            return false;
        }

        if(claveNueva.length() < 6){
            error.setValue("La contraseña debe tener al menos 6 caracteres");
            return false;
        }

        if(claveActual.equals(claveNueva)){
            error.setValue("La nueva contraseña no puede ser igual a la actual");
            return false;
        }

        return true;
    }
}
