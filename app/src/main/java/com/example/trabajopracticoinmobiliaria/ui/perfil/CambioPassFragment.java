package com.example.trabajopracticoinmobiliaria.ui.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.trabajopracticoinmobiliaria.R;
import com.example.trabajopracticoinmobiliaria.databinding.FragmentCambioPassBinding;
import com.example.trabajopracticoinmobiliaria.databinding.FragmentPerfilBinding;
import com.example.trabajopracticoinmobiliaria.modelo.Propietario;
import com.google.android.material.textfield.TextInputEditText;

public class CambioPassFragment extends Fragment {
    private PerfilViewModel vm;
    private FragmentCambioPassBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCambioPassBinding.inflate(inflater, container, false);
        vm= new ViewModelProvider(this).get(PerfilViewModel.class);

        View root = binding.getRoot();

        vm.cargarPerfil();

        binding.btAceptarCambiarPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String claveActual = binding.etClaveActual.getText().toString().trim();
                String claveNueva = binding.etClaveNueva.getText().toString().trim();

                if(vm.validarPassword(claveActual, claveNueva)){
                    vm.cambiarPass(claveActual, claveNueva);
                }
            }
        });

        vm.getError().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getContext(),s, Toast.LENGTH_LONG).show();
            }
        });

        return root;
    }

}
