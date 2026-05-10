package com.example.trabajopracticoinmobiliaria.ui.inquilinos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.trabajopracticoinmobiliaria.databinding.FragmentInquilinoBinding;


public class InquilinoFragment extends Fragment {

    private FragmentInquilinoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        InquilinoViewModel inquilinoViewModel =
                new ViewModelProvider(this).get(InquilinoViewModel.class);

        binding = FragmentInquilinoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSettings;
        inquilinoViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}