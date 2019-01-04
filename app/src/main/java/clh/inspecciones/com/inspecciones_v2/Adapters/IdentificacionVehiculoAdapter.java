package clh.inspecciones.com.inspecciones_v2.Adapters;

/**
 * Created by root on 7/09/18.
 */


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import clh.inspecciones.com.inspecciones_v2.Clases.IdentificacionVehiculoClass;
import clh.inspecciones.com.inspecciones_v2.R;

/**
 * Created by root on 24/08/18.
 */

public class IdentificacionVehiculoAdapter extends RecyclerView.Adapter<IdentificacionVehiculoAdapter.IdentificacionVehiculo_Holder> {

    //public IdentificacionVehiculoResultadoFragment.EnviarData callback;

    private List<IdentificacionVehiculoClass> listaVehiculos;
    private OnItemClickListener itemClickListener;


    public IdentificacionVehiculoAdapter(List<IdentificacionVehiculoClass> listaVehiculos, OnItemClickListener listener){
        this.listaVehiculos = listaVehiculos;
        this.itemClickListener = listener;
    }


    @NonNull
    @Override
    public IdentificacionVehiculo_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_identificacion_vehiculos_resultado,parent,false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new IdentificacionVehiculo_Holder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull IdentificacionVehiculo_Holder holder, int position) {

        holder.bind(listaVehiculos.get(position),itemClickListener);
    }

    @Override
    public int getItemCount() {
        return listaVehiculos.size();
    }



    public class IdentificacionVehiculo_Holder extends RecyclerView.ViewHolder{
        TextView tvConductor, tvTractora, tvCisterna;

        public IdentificacionVehiculo_Holder(View itemView) {
            super(itemView);

            tvConductor = (TextView)itemView.findViewById(R.id.tv_idvehiculoconductor);
            tvTractora = (TextView)itemView.findViewById(R.id.tv_idvehiculotractor);
            tvCisterna = (TextView)itemView.findViewById(R.id.tv_idvehiculocisterna);
        }

        public void bind(final IdentificacionVehiculoClass identificacionVehiculoClass, final OnItemClickListener listener) {

            this.tvTractora.setText(listaVehiculos.get(getAdapterPosition()).getTractora().toString());
            this.tvConductor.setText(listaVehiculos.get(getAdapterPosition()).getConductor().toString());
            this.tvCisterna.setText(listaVehiculos.get(getAdapterPosition()).getCisterna().toString());

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    listener.onItemClick(identificacionVehiculoClass,getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(IdentificacionVehiculoClass identificacionVehiculoClass, int position);

    }

}
