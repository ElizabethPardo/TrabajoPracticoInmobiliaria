package com.example.trabajopracticoinmobiliaria.ui.salir;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trabajopracticoinmobiliaria.R;

public class SalirFragment extends Fragment {

    private SalirViewModel mViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public SalirFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_salir, container, false);
        dialogSalir(root);
        return root;
    }

    public  void dialogSalir(View v)
    {
        new AlertDialog.Builder(getContext())
                .setTitle("Salir")
                .setMessage("Esta seguro que desea cerrar sesión?")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Navigation.findNavController(getActivity(),R.id.nav_host_fragment_content_main).navigate(R.id.nav_perfil);
                    }
                })
                .show();

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SalirViewModel.class);
        // TODO: Use the ViewModel
    }

}