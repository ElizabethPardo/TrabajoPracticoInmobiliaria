package com.example.trabajopracticoinmobiliaria.ui.contratos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trabajopracticoinmobiliaria.R;
import com.example.trabajopracticoinmobiliaria.databinding.FragmentContratoBinding;
import com.example.trabajopracticoinmobiliaria.modelo.Contrato;
import com.example.trabajopracticoinmobiliaria.modelo.Inmueble;

import java.util.List;

public class ContratoFragment  extends Fragment {
    private RecyclerView rvContrato;
    private ContratoAdapter contratoAdapter;
    private ContratoViewModel contratoViewModel;
    private FragmentContratoBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contratoViewModel = new ViewModelProvider(this).get(ContratoViewModel.class);
        binding = FragmentContratoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        rvContrato = binding.rvContratos;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
        rvContrato.setLayoutManager(gridLayoutManager);
        contratoViewModel.getInmueblesMutable().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                contratoAdapter = new ContratoAdapter(inmuebles, root.getContext(), getLayoutInflater());
                rvContrato.setAdapter(contratoAdapter);
            }
        });
        contratoViewModel.cargarContratosVig();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}