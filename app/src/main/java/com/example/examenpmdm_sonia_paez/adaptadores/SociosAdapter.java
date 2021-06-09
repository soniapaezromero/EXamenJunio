package com.example.examenpmdm_sonia_paez.adaptadores;
/**
 * @author:Sonia Päez Creado
 * Adaptador del REcycler view q
 */
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.examenpmdm_sonia_paez.R;
import com.example.examenpmdm_sonia_paez.database.Socio;


import java.util.ArrayList;
import java.util.List;

public class SociosAdapter extends RecyclerView.Adapter<SociosAdapter.MyViewHolder> {
    private  Context context;
    public List<Socio> socioList;

    public SociosAdapter(){
        this.socioList = new ArrayList<>();
    }

    public SociosAdapter(Context context, List<Socio> listasocios) {
        this.context = context;
        this.socioList = listasocios;
    }

    public void setSocios(List<Socio> socios){
        socioList = socios;
        notifyDataSetChanged();
    }
    public Socio getSocioAt(int position) {
        return socioList.get(position);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, direccion, ciudad,fechaNAc;
        ImageView foto;



        public MyViewHolder(View itemview) {
            super(itemview);
            this.nombre = itemview.findViewById(R.id.nombreSocio);
            this.direccion =itemview.findViewById(R.id.direccionSocio);
            this.ciudad=itemview.findViewById(R.id.ciudadSocio);
            this.fechaNAc=itemview.findViewById(R.id.fechaSocio);
            this.foto= itemview.findViewById(R.id.imageView);




        }


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new MyViewHolder(view);

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(socioList !=null) {
            Socio socio = socioList.get(position);
            String uri= socio.getImagen().toString();
            holder.nombre.setText(socio.getNombre().toString());
            holder.direccion.setText(socio.getDireccion().toString());
            holder.ciudad.setText(socio.getCiudad().toString());
            holder.fechaNAc.setText(socio.getFecha().toString());
            Glide.with(holder.foto.getContext()).load(uri).placeholder(R.drawable.errorimagen).error(R.drawable.errorimagen).into(holder.foto);
        }else{
            holder.nombre.setText("No hay ninguna mascota");
        }

    }

    @Override
    public int getItemCount() {
        if(socioList != null) {
            return socioList.size();
        }else{
            return 0;
        }
    }


}
