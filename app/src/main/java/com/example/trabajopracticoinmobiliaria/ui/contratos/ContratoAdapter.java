package com.example.trabajopracticoinmobiliaria.ui.contratos;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.trabajopracticoinmobiliaria.R;
import com.example.trabajopracticoinmobiliaria.modelo.Contrato;
import com.example.trabajopracticoinmobiliaria.modelo.Inmueble;
import com.example.trabajopracticoinmobiliaria.request.ApiClient;

import org.jspecify.annotations.NonNull;

import java.util.List;

public class ContratoAdapter extends RecyclerView.Adapter<ContratoAdapter.ViewHolder> {
    private List<Inmueble> lista;
    private Context context;
    private MutableLiveData<Contrato> contratoM;
    private LayoutInflater layoutInflater;
    private ContratoDetalleViewModel vm;

    public ContratoAdapter(List<Inmueble> lista, Context context, LayoutInflater layoutInflater) {
        this.lista = lista;
        this.context = context;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public ContratoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_contrato, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContratoAdapter.ViewHolder holder, int position) {
        Inmueble inmueble = lista.get(position);

        holder.tvDireccionC.setText(inmueble.getDireccion());
        String imagen = lista.get(position)
                .getImagen()
                .replace("\\", "/");
        Glide.with(context)
                .load("https://capacitacion.alwaysdata.net/" + imagen)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivInmuebleC);

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvDireccionC;
        private ImageView ivInmuebleC;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDireccionC = itemView.findViewById(R.id.tvDireccionContrato);
            ivInmuebleC = itemView.findViewById(R.id.ivInmuebleContrato);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("idInmueble", lista.get(getAdapterPosition()).getId());
                    Navigation.findNavController((Activity)context,R.id.nav_host_fragment_content_main).navigate(R.id.contratoDetalleFragment,bundle);
                }
            });
        }
    }
}
