package com.example.trabajopracticoinmobiliaria.ui.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.trabajopracticoinmobiliaria.MainActivity;
import com.example.trabajopracticoinmobiliaria.databinding.ActivityLoginBinding;

import java.util.List;


public class LoginActivity extends AppCompatActivity {

    private LoginViewModel vm;
    private ActivityLoginBinding b;
    private SensorManager sensorManager;
    private LeerSensor leerSensor;
    private List<Sensor> listaSensores;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        solicitarPerimisos();

        vm= ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()).create(LoginViewModel.class);

        leerSensor = new LeerSensor();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        listaSensores = sensorManager.getSensorList(Sensor.TYPE_GYROSCOPE);


        vm.getEstadoM().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:2664508236"));
                startActivity(i);
                Toast.makeText(LoginActivity.this, "Llamando Inmobiliaria", Toast.LENGTH_LONG).show();
            }
        });

        vm.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }
        });

        b.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = b.etUser.getText().toString().trim();
                String pass = b.etPass.getText().toString().trim();
                vm.recuperarDatos(email,pass);
            }
        });


         vm.getUsuarioM().observe(this, exito -> {
                if(exito != null && exito)
                {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("abrirDrawer", true);
                    startActivity(intent);
                    finish();

                }
            });

        b.tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = b.etUser.getText().toString().trim();

                if(email.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Ingrese un email", Toast.LENGTH_LONG).show();
                    return;
                }
                vm.resetClave(email);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(leerSensor, listaSensores.get(0), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(leerSensor);
    }

    private class LeerSensor implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            vm.sensorG(sensorEvent.values[0]);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    }

    private void solicitarPerimisos(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED
                        && checkSelfPermission(Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED) != PackageManager.PERMISSION_GRANTED
                        && checkSelfPermission(Manifest.permission.READ_MEDIA_VIDEO) != PackageManager.PERMISSION_GRANTED
                        && checkSelfPermission(Manifest.permission.READ_MEDIA_AUDIO) != PackageManager.PERMISSION_GRANTED
                        && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                        && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        )
        {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
        }
    }



}