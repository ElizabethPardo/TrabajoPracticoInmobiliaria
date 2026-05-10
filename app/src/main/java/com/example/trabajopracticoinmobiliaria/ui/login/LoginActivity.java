package com.example.trabajopracticoinmobiliaria.ui.login;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.trabajopracticoinmobiliaria.MainActivity;
import com.example.trabajopracticoinmobiliaria.databinding.ActivityLoginBinding;


public class LoginActivity extends AppCompatActivity {

    private LoginViewModel vm;
    private ActivityLoginBinding b;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        vm= ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()).create(LoginViewModel.class);

        b.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = b.etUser.getText().toString();
                String pass = b.etPass.getText().toString();
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

    }



}