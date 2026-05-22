package com.example.trabajopracticoinmobiliaria.ui.inmuebles;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.trabajopracticoinmobiliaria.modelo.Inmueble;
import com.example.trabajopracticoinmobiliaria.request.ApiClient;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarInmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<String> errorDireccion;
    private MutableLiveData<String> errorAmbientes;
    private MutableLiveData<String> errorSuperficie;
    private MutableLiveData<String> errorPrecio;
    private MutableLiveData<String> errorImagen;
    private Context context;

    private MutableLiveData<Uri> uriMutable;
    public AgregarInmuebleViewModel(@NonNull Application application) {
        super(application);
        context=application.getApplicationContext();
        errorDireccion= new MutableLiveData<>();
        errorAmbientes= new MutableLiveData<>();
        errorSuperficie= new MutableLiveData<>();
        errorPrecio= new MutableLiveData<>();
        errorImagen= new MutableLiveData<>();
        uriMutable = new MutableLiveData<>();
    }

    public void recibirFoto(Uri uri) {
        if (uri!=null) {
            uriMutable.setValue(uri);
        }
    }
    public LiveData<Uri> getUriMutable() {
        if(uriMutable==null){
            uriMutable = new MutableLiveData<>();
        }
        return uriMutable;
    }
    public LiveData<String> getErrorDireccion(){
        return errorDireccion;
    }

    public LiveData<String> getErrorAmbientes(){
        return errorAmbientes;
    }

    public LiveData<String> getErrorPrecio(){
        return errorPrecio;
    }

    public LiveData<String> getErrorImagen(){
        return errorImagen;
    }

    public LiveData<String> getErrorSuperficie(){
        return errorSuperficie;
    }


    public void nuevoInmueble(String direccion, String uso, String tipo, String ambientes, String superficie, String valor)
    {
        try {
            if(direccion.isEmpty() || uso.isEmpty() || tipo.isEmpty() || ambientes.isEmpty()
            || superficie.isEmpty() || valor.isEmpty())
            {
                Toast.makeText(context, "Ha ocurrido un error", Toast.LENGTH_LONG).show();
            }
            else{
                    Inmueble i= new Inmueble();
                    i.setDireccion(direccion);
                    i.setUso(uso);
                    i.setTipo(tipo);
                    i.setAmbientes(Integer.parseInt(ambientes));
                    i.setSuperficie(Integer.parseInt(superficie));
                    i.setValor(Double.parseDouble(valor));

                    byte[] imagen = transformarImagen();
                    if (imagen.length == 0) {
                        Toast.makeText(getApplication(), "Debe ingresar imagen", Toast.LENGTH_LONG).show();
                        return;
                    }

                    String inmuebleJson = new Gson().toJson(i);
                    RequestBody inmuebleBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), inmuebleJson);
                    RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imagen);
                    MultipartBody.Part imagenPart = MultipartBody.Part.createFormData("imagen", "imagen.jpg", requestFile);

                    String token = ApiClient.leerToken(context);
                    ApiClient.MyApiInterface servicio = ApiClient.getServicio();

                    Call<Inmueble> inmu = servicio.cargarInmuebles(token,imagenPart, inmuebleBody);
                    inmu.enqueue(new Callback<Inmueble>() {
                    @Override
                    public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {

                        if(response.isSuccessful()){

                            Toast.makeText(context, "Inmueble creado correctamente!", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(context, "Ha ocurrido un error", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Inmueble> call, Throwable t) {

                        Toast.makeText(context, "Ha ocurrido una falla", Toast.LENGTH_LONG).show();
                    }
                });

            }
        }catch (NumberFormatException e)
        {

        }
    }

    private byte[] transformarImagen() {
        try {
            Uri uri = uriMutable.getValue();
            InputStream inputStream = getApplication().getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (FileNotFoundException ex) {
            Toast.makeText(getApplication(), "Debe ingresar una foto", Toast.LENGTH_LONG).show();
            return new byte[]{};
        }

    }


    public boolean validarCampos(String direccion, String ambientesTexto, String superficie,String precioTexto, Uri uriFoto)
    {
        errorDireccion.setValue(null);
        errorAmbientes.setValue(null);
        errorPrecio.setValue(null);
        errorImagen.setValue(null);

        boolean valido = true;

        if(direccion.isEmpty()){
            errorDireccion.setValue("Ingrese una dirección");
            valido = false;
        }

        if(ambientesTexto.isEmpty()){
            errorAmbientes.setValue("Ingrese cantidad de ambientes");
            valido = false;
        }
        if(superficie.isEmpty()){
            errorSuperficie.setValue("Ingrese superficie");
            valido = false;
        }
        if(precioTexto.isEmpty()){
            errorPrecio.setValue("Ingrese un precio");
            valido = false;
        }

        if(uriFoto == null){
            errorImagen.setValue("Debe seleccionar una imagen");
            valido = false;
        }

        if(!valido){
            return false;
        }

        try {

            int ambientes = Integer.parseInt(ambientesTexto);

            if(ambientes <= 0){

                errorAmbientes.setValue("Debe ser mayor a 0");
                return false;
            }

        }catch (NumberFormatException e){

            errorAmbientes.setValue("Número inválido");
            return false;
        }

        try {

            int sup = Integer.parseInt(superficie);

            if(sup <= 0){

                errorSuperficie.setValue("Debe ser mayor a 0");
                return false;
            }

        }catch (NumberFormatException e){

            errorSuperficie.setValue("Número inválido");
            return false;
        }

        try {

            double precio = Double.parseDouble(precioTexto);

            if(precio <= 0){

                errorPrecio.setValue("Debe ser mayor a 0");
                return false;
            }

        }catch (NumberFormatException e){

            errorPrecio.setValue("Precio inválido");
            return false;
        }

        return true;
    }


}
