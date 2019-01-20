package clh.inspecciones.com.inspecciones_v2.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import clh.inspecciones.com.inspecciones_v2.Clases.BuscarInspeccionClass;
import clh.inspecciones.com.inspecciones_v2.R;

public class BuscarInspeccionAdapter extends RecyclerView.Adapter<BuscarInspeccionAdapter.BuscarInspeccion_Holder> {


    private List<BuscarInspeccionClass> listaDatosInspeccion;
    private OnItemClickListener itemClickListener;


    public BuscarInspeccionAdapter(List<BuscarInspeccionClass> listaDatosInspeccion, OnItemClickListener listener) {
        this.listaDatosInspeccion = listaDatosInspeccion;
        this.itemClickListener = listener;
    }


    @Override
    public BuscarInspeccion_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_buscar_inspeccion, parent, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new BuscarInspeccion_Holder(vista);
    }

    @Override
    public void onBindViewHolder(BuscarInspeccion_Holder holder, int position) {
        holder.bind(listaDatosInspeccion.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return listaDatosInspeccion.size();
    }



    public class BuscarInspeccion_Holder extends RecyclerView.ViewHolder {
        TextView tvTractora, tvCisterna, tvInstalacion, tvFecha;

        public BuscarInspeccion_Holder(View itemView) {
            super(itemView);

            tvTractora = itemView.findViewById(R.id.rv_tractoraBI);
            tvCisterna = itemView.findViewById(R.id.rv_cisternaBI);
            tvInstalacion = itemView.findViewById(R.id.rv_instalacionBI);
            tvFecha = itemView.findViewById(R.id.rv_fechaBI);

        }

        public void bind(final BuscarInspeccionClass buscarInspeccionClass, final OnItemClickListener listener) {

            this.tvTractora.setText(listaDatosInspeccion.get(getAdapterPosition()).getTractora());
            this.tvCisterna.setText(listaDatosInspeccion.get(getAdapterPosition()).getCisterna());
            this.tvInstalacion.setText(listaDatosInspeccion.get(getAdapterPosition()).getInstalacion());
            this.tvFecha.setText(listaDatosInspeccion.get(getAdapterPosition()).getFecha());

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    listener.onItemClick(buscarInspeccionClass, getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(BuscarInspeccionClass buscarInspeccionClass, int position);

    }

}
