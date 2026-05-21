package com.example.trabajopracticoinmobiliaria.ui.inmuebles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.trabajopracticoinmobiliaria.databinding.FragmentInmuebleDetalleBinding;
import com.example.trabajopracticoinmobiliaria.modelo.Inmueble;

public class InmuebleDetalleFragment extends Fragment {
    private InmuebleDetalleViewModel inmuebleDetalleViewModel;
    private FragmentInmuebleDetalleBinding binding;

    private Inmueble inmuebleActual;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentInmuebleDetalleBinding.inflate(inflater, container, false);
        inmuebleDetalleViewModel = new ViewModelProvider(this).get(InmuebleDetalleViewModel.class);

        View root = binding.getRoot();


        inmuebleDetalleViewModel.getInmuebleM().observe(getViewLifecycleOwner(), new Observer<Inmueble>() {
            @Override
            public void onChanged(Inmueble inmueble) {

                binding.tvCodigoInmueble.setText(inmueble.getId() + "");
                binding.tvAmbientesInmueble.setText(inmueble.getAmbientes() + "");
                binding.tvDireccionInmueble.setText(inmueble.getDireccion());
                binding.tvPrecioInmueble.setText(String.valueOf(inmueble.getValor()));
                binding.tvUsoInmueble.setText(inmueble.getUso());
                binding.tvTipo.setText(inmueble.getTipo());
                binding.cbEstadoInmueble.setChecked(inmueble.getDisponible());
                inmuebleActual = inmueble;

                String urlImagen = "https://capacitacion.alwaysdata.net/" +
                        inmueble.getImagen().replace("\\", "/");

                Glide.with(getContext())
                        .load(urlImagen)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(binding.ivFotoInmueble);
            }
        });

        inmuebleDetalleViewModel.getMensajeDis().observe(getViewLifecycleOwner(), texto -> {
            binding.cbEstadoInmueble.setText(texto);
        });

        Bundle bundle = getArguments();
        if(bundle != null){
            Inmueble inmueble = (Inmueble) bundle.getSerializable("inmueble");
            inmuebleDetalleViewModel.cargarInmueble(inmueble);
        }

        binding.cbEstadoInmueble.setOnClickListener(v -> {

            if(inmuebleActual != null){

                inmuebleDetalleViewModel.editarInmueble(binding.cbEstadoInmueble.isChecked());
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
