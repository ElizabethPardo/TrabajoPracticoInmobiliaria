package com.example.trabajopracticoinmobiliaria.ui.contratos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.trabajopracticoinmobiliaria.R;
import com.example.trabajopracticoinmobiliaria.databinding.FragmentContratoDetalleBinding;
import com.example.trabajopracticoinmobiliaria.modelo.Contrato;

import org.jspecify.annotations.NonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ContratoDetalleFragment extends Fragment {

    private ContratoDetalleViewModel contratoDetalleViewModel;
    private FragmentContratoDetalleBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        contratoDetalleViewModel = new ViewModelProvider(this).get(ContratoDetalleViewModel.class);
        binding = FragmentContratoDetalleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        contratoDetalleViewModel.getContratoM().observe(getViewLifecycleOwner(), new Observer<Contrato>() {
            @Override
            public void onChanged(Contrato contrato) {

                binding.tvCodigoContrato.setText(contrato.getId() + "");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                LocalDate fi = LocalDate.parse(contrato.getFechaInicio());
                binding.tvInicioContrato.setText(fi.format(formatter));

                LocalDate fc = LocalDate.parse(contrato.getFechaFinalizacion());
                binding.tvFinContrato.setText(fc.format(formatter));

                binding.tvMontoContrato.setText("$ " + contrato.getInmueble().getValor());
                binding.tvInquilinoContrato.setText(contrato.getInquilino().getNombre()+" "+ contrato.getInquilino().getApellido());
                binding.tvInmuebleContrato.setText(contrato.getInmueble().getDireccion());
                binding.btPagos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("idContrato", contrato.getId());
                        Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main).navigate(R.id.pagoFragment,bundle);
                    }
                });
            }
        });

        Bundle args = getArguments();

        if(args != null){
            int id = args.getInt("idInmueble");
            contratoDetalleViewModel.buscarContrato(id);
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
