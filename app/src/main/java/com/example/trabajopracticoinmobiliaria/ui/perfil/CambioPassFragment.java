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
    private Propietario propietarioActual;
    private FragmentCambioPassBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCambioPassBinding.inflate(inflater, container, false);
        vm= new ViewModelProvider(this).get(PerfilViewModel.class);

        View root = binding.getRoot();
        vm.getPropietarioM().observe(getViewLifecycleOwner(), new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {

                propietarioActual=propietario;
            }
        });

        vm.cargarPerfil();

        binding.btAceptarCambiarPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String claveActual = binding.etClaveActual.getText().toString().trim();
                String claveNueva = binding.etClaveNueva.getText().toString().trim();

                if(claveActual.isEmpty()){
                    binding.etClaveActual.setError("Ingrese la contraseña actual");
                    return;
                }

                if(claveNueva.isEmpty()){
                    binding.etClaveNueva.setError("Ingrese la nueva contraseña");
                    return;
                }

                if(claveNueva.length() < 6){
                    binding.etClaveNueva.setError("La contraseña debe tener al menos 6 caracteres");
                    return;
                }

                if(claveActual.equals(claveNueva)){
                    binding.etClaveNueva.setError("La nueva contraseña no puede ser igual a la actual");
                    return;
                }

                vm.cambiarPass(claveActual, claveNueva);
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
