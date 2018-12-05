package clh.inspecciones.com.inspecciones_v2.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import clh.inspecciones.com.inspecciones_v2.Clases.CACompartimentosBD;
import clh.inspecciones.com.inspecciones_v2.R;

public class CompartimentosAdapter extends RecyclerView.Adapter<CompartimentosAdapter.ViewHolder>{

    private List<CACompartimentosBD> compartimentosList;
    private int layout;
    private OnItemClickListener itemClickListener;
   // private Context context;


    public CompartimentosAdapter(List<CACompartimentosBD> compartimentosList, int layout, OnItemClickListener listener){

        this.compartimentosList = compartimentosList;
        this.layout = layout;
        this.itemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflamos el layout y se lo pasamos al constructor del ViewHolder, donde manejaremos
        // toda la lógica como extraer los datos, referencias...
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(layoutParams);
       // context = parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(compartimentosList.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return compartimentosList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_codCompartimento;
        TextView tv_codTag;
        TextView tv_capacidad;


        public ViewHolder(View itemView) {
            super(itemView);
            tv_codCompartimento = (TextView)itemView.findViewById(R.id.tv_numcomp);
            tv_codTag = (TextView)itemView.findViewById(R.id.tv_codtag);
            tv_capacidad= (TextView)itemView.findViewById(R.id.tv_capacidadtotalcomp1);
        }

        public void bind(final CACompartimentosBD compartimentos, final OnItemClickListener listener){
            this.tv_codCompartimento.setText("Compartimento: " + compartimentosList.get(getAdapterPosition()).getCod_compartimento());
            //this.tv_codCompartimento.setText("Compartimento " + compartimentosList.get(getAdapterPosition()).getCod_compartimento());
            this.tv_codTag.setText(compartimentosList.get(getAdapterPosition()).getCod_tag_cprt()); // (compartimentosList.get(getAdapterPosition()).getCod_tag_cprt());
            this.tv_capacidad.setText(String.valueOf(compartimentosList.get(getAdapterPosition()).getCan_capacidad()));

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    itemView.setBackgroundColor(Color.GREEN);
                    listener.onItemClick(compartimentos, getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(CACompartimentosBD compartimentosList, int position);
    }
}
