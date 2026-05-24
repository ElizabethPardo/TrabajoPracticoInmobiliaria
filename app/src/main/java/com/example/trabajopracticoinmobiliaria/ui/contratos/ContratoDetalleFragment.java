package com.example.trabajopracticoinmobiliaria.ui.contratos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
import java.time.LocalDateTime;

public class ContratoDetalleFragment extends Fragment {

    private ContratoDetalleViewModel contratoDetalleViewModel;
    private FragmentContratoDetalleBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        contratoDetalleViewModel = new ViewModelProvider(this).get(ContratoDetalleViewModel.class);
        binding = FragmentContratoDetalleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TextView tvCodigoContrato = binding.tvCodigoContrato;
        final TextView tvInicioContrato = binding.tvInicioContrato;
        final TextView tvFinCOntrato = binding.tvFinContrato;
        final TextView tvMontoContrato = binding.tvMontoContrato;
        final TextView tvInquilinoContrato = binding.tvInquilinoContrato;
        final TextView tvInmuebleContrato = binding.tvInmuebleContrato;
        final Button btPagos = binding.btPagos;
        contratoDetalleViewModel.getContratoM().observe(getViewLifecycleOwner(), new Observer<Contrato>() {
            @Override
            public void onChanged(Contrato contrato) {

                tvCodigoContrato.setText(contrato.getId() + "");

                LocalDate fi = LocalDate.parse(contrato.getFechaInicio());
                //LocalDate fff = fi.toLocalDate();
                tvInicioContrato.setText(fi.toString());

                LocalDate fc = LocalDate.parse(contrato.getFechaFinalizacion());
                //LocalDate hhh = fc.toLocalDate();
                tvFinCOntrato.setText(fc.toString());

                tvMontoContrato.setText("$ " + contrato.getInmueble().getValor());
                tvInquilinoContrato.setText(contrato.getInquilino().getNombre()+" "+ contrato.getInquilino().getApellido());
                tvInmuebleContrato.setText(contrato.getInmueble().getDireccion());
                btPagos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("idContrato", contrato.getId());
                        Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main).navigate(R.id.pagoFragment,bundle);
                    }
                });
            }
        });
        //contratoDetalleViewModel.cargarCon(getArguments());
        int id = getArguments().getInt("idInmueble");
        contratoDetalleViewModel.buscarContrato(id);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
