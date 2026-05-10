package com.example.trabajopracticoinmobiliaria.ui.inmuebles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.trabajopracticoinmobiliaria.databinding.FragmentInmuebleBinding;


public class InmuebleFragment extends Fragment {

    private FragmentInmuebleBinding binding;
    private InmuebleViewModel vm;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

                vm= new ViewModelProvider(this).get(InmuebleViewModel.class);

        binding = FragmentInmuebleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}