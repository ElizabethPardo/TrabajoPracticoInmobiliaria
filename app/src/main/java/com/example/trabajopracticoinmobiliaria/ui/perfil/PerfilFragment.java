package com.example.trabajopracticoinmobiliaria.ui.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.trabajopracticoinmobiliaria.R;
import com.example.trabajopracticoinmobiliaria.databinding.FragmentPerfilBinding;
import com.example.trabajopracticoinmobiliaria.modelo.Propietario;


public class PerfilFragment extends Fragment {

        private PerfilViewModel vm;
        private FragmentPerfilBinding binding;
        private Propietario propietarioActual = null;

        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container,
                                 Bundle savedInstanceState) {

            binding = FragmentPerfilBinding.inflate(inflater, container, false);

            vm = new ViewModelProvider(this).get(PerfilViewModel.class);

            View root = binding.getRoot();

            habilitarEdicion(false);

            vm.getPropietarioM().observe(getViewLifecycleOwner(), propietario -> {

                binding.etDni.setText(propietario.getDni());

                binding.etNombre.setText(propietario.getNombre());

                binding.etApellido.setText(propietario.getApellido());

                binding.etEmail.setText(propietario.getEmail());

                binding.etTelefono.setText(propietario.getTelefono());

                propietarioActual = propietario;
            });

            vm.getErrorNombre().observe(getViewLifecycleOwner(), error -> {

                binding.etNombre.setError(error);
            });

            vm.getErrorApellido().observe(getViewLifecycleOwner(), error -> {

                binding.etApellido.setError(error);
            });

            vm.getErrorDni().observe(getViewLifecycleOwner(), error -> {

                binding.etDni.setError(error);
            });

            vm.getErrorTelefono().observe(getViewLifecycleOwner(), error -> {

                binding.etTelefono.setError(error);
            });

            vm.getError().observe(getViewLifecycleOwner(), s -> {

                Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
            });

            vm.cargarPerfil();

            binding.btEditar.setOnClickListener(v -> {

                habilitarEdicion(true);
            });

            binding.btAceptar.setOnClickListener(v -> {

                String nombre = binding.etNombre.getText().toString().trim();

                String apellido = binding.etApellido.getText().toString().trim();

                String telefono = binding.etTelefono.getText().toString().trim();

                String dni = binding.etDni.getText().toString().trim();

                boolean valido = vm.validarCampos(nombre, apellido, dni, telefono);

                if(!valido){
                    return;
                }

                Propietario prop = new Propietario(
                        propietarioActual.getId(),
                        nombre,
                        apellido,
                        dni,
                        telefono,
                        propietarioActual.getEmail(),
                        null
                );

                vm.editarPerfil(prop);

                habilitarEdicion(false);
            });

            binding.btCambiarPass.setOnClickListener(v -> {

                Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main).navigate(R.id.cambioPassFragment);
            });

            return root;
        }

        private void habilitarEdicion(boolean estado){

            binding.etNombre.setEnabled(estado);

            binding.etApellido.setEnabled(estado);

            binding.etDni.setEnabled(estado);

            binding.etTelefono.setEnabled(estado);

            binding.btEditar.setVisibility(estado ? View.GONE : View.VISIBLE);

            binding.btAceptar.setVisibility(estado ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onDestroyView() {

            super.onDestroyView();

            binding = null;
        }




}