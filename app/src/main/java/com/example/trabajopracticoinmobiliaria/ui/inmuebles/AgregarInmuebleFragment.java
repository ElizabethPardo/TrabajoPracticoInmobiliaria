package com.example.trabajopracticoinmobiliaria.ui.inmuebles;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.trabajopracticoinmobiliaria.databinding.FragmentAgregarInmuebleBinding;
import com.example.trabajopracticoinmobiliaria.modelo.Inmueble;

public class AgregarInmuebleFragment extends Fragment {
    private AgregarInmuebleViewModel vm;
    private FragmentAgregarInmuebleBinding binding;
    private ActivityResultLauncher<PickVisualMediaRequest> arl;
    private Uri uriFoto;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new ViewModelProvider(this).get(AgregarInmuebleViewModel.class);
        arl= registerForActivityResult(new ActivityResultContracts.PickVisualMedia(),uri->{
            vm.recibirFoto(uri);
        });
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = FragmentAgregarInmuebleBinding.inflate(inflater, container,false);
        View root = binding.getRoot();

        vm.getErrorDireccion().observe(getViewLifecycleOwner(), error -> {
            binding.etDireInmueble.setError(error);
        });

        vm.getErrorAmbientes().observe(getViewLifecycleOwner(), error -> {
            binding.etAmbiestesInmueble.setError(error);
        });

        vm.getErrorSuperficie().observe(getViewLifecycleOwner(), error -> {
            binding.etSuperficieInmueble.setError(error);
        });

        vm.getErrorPrecio().observe(getViewLifecycleOwner(), error -> {
            binding.etPrecioInmueble.setError(error);
        });

        vm.getErrorImagen().observe(getViewLifecycleOwner(), error -> {
            if(error != null){
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });

        vm.getUriMutable().observe(getViewLifecycleOwner(), new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                binding.ivInmuebleNuevo.setVisibility(View.VISIBLE);
                binding.ivInmuebleNuevo.setImageURI(uri);
                uriFoto=uri;
            }
        });
        binding.btCargarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arl.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
            }
        });

        binding.btAgregar.setOnClickListener(v -> {

            String direccion = binding.etDireInmueble.getText().toString().trim();
            String ambientes = binding.etAmbiestesInmueble.getText().toString().trim();
            String superficie= binding.etSuperficieInmueble.getText().toString().trim();
            String precio = binding.etPrecioInmueble.getText().toString().trim();
            String uso=binding.spUso.getSelectedItem().toString();
            String tipo= binding.spTipo.getSelectedItem().toString();

            boolean valido = vm.validarCampos(direccion, ambientes,superficie, precio, uriFoto);

           if(valido){
                vm.nuevoInmueble(direccion,uso,tipo,ambientes,superficie,precio);
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