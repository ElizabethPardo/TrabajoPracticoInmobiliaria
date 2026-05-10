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
                binding.etDireccion.setText(propietario.getDireccion());
                binding.etTelefono.setText(propietario.getTelefono());
            }
        });

        vm.cargarPerfil();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }




}