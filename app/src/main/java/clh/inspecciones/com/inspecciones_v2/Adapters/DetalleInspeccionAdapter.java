package clh.inspecciones.com.inspecciones_v2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;


import java.util.List;

import clh.inspecciones.com.inspecciones_v2.Clases.DetalleInspeccionBD;
import clh.inspecciones.com.inspecciones_v2.R;

public class DetalleInspeccionAdapter extends BaseAdapter {

    private Context context;
    private List<DetalleInspeccionBD> inspeccionBDS;
    private int layout;


    public DetalleInspeccionAdapter(Context context, List<DetalleInspeccionBD> inspeccionBDS, int layout ){
        this.context = context;
        this.inspeccionBDS = inspeccionBDS;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return inspeccionBDS.size();
    }

    @Override
    public Object getItem(int position) {
        return inspeccionBDS.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder vh;
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layout, null);
            vh = new ViewHolder();
            vh.adrCisterna = (CheckBox) convertView.findViewById(R.id.cb_adrc);
            vh.adrConductor = (CheckBox) convertView.findViewById(R.id.cb_adrcond);
            vh.adrTractora = (CheckBox) convertView.findViewById(R.id.cb_adrt);
            vh.apagallamas = (CheckBox) convertView.findViewById(R.id.cb_apagallamas);
            vh.bateriaDesconectada = (CheckBox) convertView.findViewById(R.id.cb_baterias);
            vh.fichaSeguridad = (CheckBox) convertView.findViewById(R.id.cb_fichaseguridad);
            vh.frenoEstacionamiento = (CheckBox) convertView.findViewById(R.id.cb_frenoestacionamiento);
            vh.interruptorEmergencia = (CheckBox) convertView.findViewById(R.id.cb_interruptores);
            vh.itvCisterna = (CheckBox) convertView.findViewById(R.id.cb_itvc);
            vh.itvTractora = (CheckBox) convertView.findViewById(R.id.cb_itvt);
            vh.mangueraGases = (CheckBox) convertView.findViewById(R.id.cb_manguera_gases);
            vh.movilDesconectado = (CheckBox) convertView.findViewById(R.id.cb_movil);
            vh.permisoCirculacion = (CheckBox) convertView.findViewById(R.id.cb_permisoconducir);
            vh.posicionVehiculo = (CheckBox) convertView.findViewById(R.id.cb_posicionvehiculo);
            vh.purgaCompartimentos = (CheckBox) convertView.findViewById(R.id.cb_purga);
            vh.ropa = (CheckBox) convertView.findViewById(R.id.cb_ropa);
            vh.superficieSupAntiDes = (CheckBox) convertView.findViewById(R.id.cb_superficiesuperior);
            vh.tablaCalibracion = (CheckBox) convertView.findViewById(R.id.cb_tablacaltractora);
            vh.tagsCorrectos = (CheckBox) convertView.findViewById(R.id.cb_tags);
            vh.tc2 = (CheckBox) convertView.findViewById(R.id.cb_tc2);
            vh.tomaTierra = (CheckBox) convertView.findViewById(R.id.cb_scully);
            vh.transponderCisterna = (CheckBox) convertView.findViewById(R.id.cb_transponderc);
            vh.transponderTractora = (CheckBox) convertView.findViewById(R.id.cb_transpondert);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }

        DetalleInspeccionBD detalleInspeccionBD = inspeccionBDS.get(position);

        vh.adrCisterna.isChecked();
        vh.adrConductor.isChecked();
        vh.adrTractora.isChecked();
        vh.apagallamas.isChecked();
        vh.bateriaDesconectada.isChecked();
        vh.fichaSeguridad.isChecked();
        vh.frenoEstacionamiento.isChecked();
        vh.interruptorEmergencia.isChecked();
        vh.itvCisterna.isChecked();
        vh.itvTractora.isChecked();
        vh.mangueraGases.isChecked();
        vh.movilDesconectado.isChecked();
        vh.permisoCirculacion.isChecked();
        vh.posicionVehiculo.isChecked();
        vh.purgaCompartimentos.isChecked();
        vh.ropa.isChecked();
        vh.superficieSupAntiDes.isChecked();
        vh.tablaCalibracion.isChecked();
        vh.tagsCorrectos.isChecked();
        vh.tc2.isChecked();
        vh.tomaTierra.isChecked();
        vh.superficieSupAntiDes.isChecked();
        vh.transponderCisterna.isChecked();
        vh.transponderTractora.isChecked();

        return convertView;

        }

    public class ViewHolder{
        CheckBox permisoCirculacion;
        CheckBox adrConductor;
        CheckBox tc2;
        CheckBox itvTractora;
        CheckBox adrTractora;
        CheckBox itvCisterna;
        CheckBox adrCisterna;
        CheckBox fichaSeguridad;
        CheckBox tablaCalibracion;
        CheckBox transponderTractora;
        CheckBox transponderCisterna;
        CheckBox superficieSupAntiDes;
        CheckBox posicionVehiculo;
        CheckBox frenoEstacionamiento;
        CheckBox bateriaDesconectada;
        CheckBox apagallamas;
        CheckBox movilDesconectado;
        CheckBox interruptorEmergencia;
        CheckBox tomaTierra;
        CheckBox mangueraGases;
        CheckBox purgaCompartimentos;
        CheckBox tagsCorrectos;
        CheckBox ropa;



    }
}
