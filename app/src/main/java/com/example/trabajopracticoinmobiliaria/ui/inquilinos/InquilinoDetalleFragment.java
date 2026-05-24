package com.example.trabajopracticoinmobiliaria.ui.inquilinos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.trabajopracticoinmobiliaria.databinding.FragmentInquilinoDetalleBinding;
import com.example.trabajopracticoinmobiliaria.modelo.Contrato;


import org.jspecify.annotations.NonNull;

public class InquilinoDetalleFragment extends Fragment {
    private InquilinoDetalleViewModel inquilinoDetalleViewModel;
    private FragmentInquilinoDetalleBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inquilinoDetalleViewModel = new ViewModelProvider(this).get(InquilinoDetalleViewModel.class);
        binding = FragmentInquilinoDetalleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        inquilinoDetalleViewModel.getInquilinoM().observe(getViewLifecycleOwner(), new Observer<Contrato>() {
            @Override
            public void onChanged(Contrato inquilino) {
                binding.tvCodigoInquilino.setText(inquilino.getInquilino().getId() + "");
                binding.tvNombreInquilino.setText(inquilino.getInquilino().getNombre());
                binding.tvApellidoInquilino.setText(inquilino.getInquilino().getApellido());
                binding.tvDniInquilino.setText(inquilino.getInquilino().getDni()+"");
                binding.tvMailInquilino.setText(inquilino.getInquilino().getEmail());
                binding.tvTelInquilino.setText(inquilino.getInquilino().getTelefono());
            }
        });
        inquilinoDetalleViewModel.cargar(getArguments());
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
