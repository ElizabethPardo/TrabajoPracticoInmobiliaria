package com.example.trabajopracticoinmobiliaria.ui.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.trabajopracticoinmobiliaria.databinding.FragmentPerfilBinding;
import com.example.trabajopracticoinmobiliaria.modelo.Propietario;


/**
 * Fragment that demonstrates a responsive layout pattern where the format of the content
 * transforms depending on the size of the screen. Specifically this Fragment shows items in
 * the [RecyclerView] using LinearLayoutManager in a small screen
 * and shows items using GridLayoutManager in a large screen.
 */
public class PerfilFragment extends Fragment {

    private PerfilViewModel vm;
    private FragmentPerfilBinding binding;

    private Propietario propietarioActual=null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        vm= new ViewModelProvider(this).get(PerfilViewModel.class);

        View root = binding.getRoot();

        vm.getPropietarioM().observe(getViewLifecycleOwner(), new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                binding.etDni.setText(propietario.getDni());
                binding.etNombre.setText(propietario.getNombre());
                binding.etApellido.setText(propietario.getApellido());
                binding.etEmail.setText(propietario.getEmail());
                binding.etTelefono.setText(propietario.getTelefono());

                propietarioActual=propietario;
            }
        });

        vm.cargarPerfil();


        binding.btEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.etNombre.setEnabled(true);
                binding.etApellido.setEnabled(true);
                binding.etDni.setEnabled(true);
                binding.etEmail.setEnabled(true);
                binding.etTelefono.setEnabled(true);
                binding.btEditar.setVisibility(View.GONE);
                binding.btAceptar.setVisibility(View.VISIBLE);

            }
        });

        binding.btAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Propietario prop= new Propietario(propietarioActual.getId(),binding.etNombre.getText().toString(),binding.etApellido.getText().toString(),binding.etDni.getText().toString(),binding.etTelefono.getText().toString(),propietarioActual.getEmail(), propietarioActual.getClave());
                vm.editarPerfil(prop);
                binding.etNombre.setEnabled(false);
                binding.etApellido.setEnabled(false);
                binding.etDni.setEnabled(false);
                binding.etEmail.setEnabled(false);
                binding.etTelefono.setEnabled(false);
                binding.btEditar.setVisibility(View.VISIBLE);
                binding.btAceptar.setVisibility(View.INVISIBLE);
            }
        });
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }




}