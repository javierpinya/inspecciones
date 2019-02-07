package clh.inspecciones.com.inspecciones_v2.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import clh.inspecciones.com.inspecciones_v2.Clases.CACompartimentosBD;
import clh.inspecciones.com.inspecciones_v2.R;

public class VerCompartimentosAdapter extends RecyclerView.Adapter<VerCompartimentosAdapter.VerCompartimentos_Holder> {

    private int layout;
    private List<CACompartimentosBD> compartimentosBDList;

    public VerCompartimentosAdapter(int layout, List<CACompartimentosBD> compartimentosList) {
        this.layout = layout;
        this.compartimentosBDList = compartimentosList;

    }


    @Override
    public VerCompartimentos_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.compartimentos_listview_item, parent, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new VerCompartimentos_Holder(vista);
    }

    @Override
    public void onBindViewHolder(VerCompartimentos_Holder holder, int position) {
        holder.bind(compartimentosBDList.get(position));
    }

    @Override
    public int getItemCount() {
        return compartimentosBDList.size();
    }

    public class VerCompartimentos_Holder extends RecyclerView.ViewHolder {

        TextView tv_codCompartimento;
        TextView tv_codTag;
        TextView tv_capacidad;
        TextView tv_cantidad;

        public VerCompartimentos_Holder(View itemView) {
            super(itemView);
            tv_codCompartimento = (TextView) itemView.findViewById(R.id.tv_numcomp);
            tv_codTag = (TextView) itemView.findViewById(R.id.tv_codtag);
            tv_capacidad = (TextView) itemView.findViewById(R.id.tv_capacidadtotalcomp1);
            tv_cantidad = (TextView) itemView.findViewById(R.id.tv_cant_cargada);
        }

        public void bind(final CACompartimentosBD compartimentosBD) {
            this.tv_codCompartimento.setText(compartimentosBD.getCod_compartimento());
            this.tv_codTag.setText(compartimentosBD.getCod_tag_cprt());
            this.tv_capacidad.setText(compartimentosBD.getCan_capacidad());
            this.tv_cantidad.setText(compartimentosBD.getCan_cargada());
        }

    }
}
