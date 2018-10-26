package clh.inspecciones.com.inspecciones_v2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;


import java.nio.file.StandardWatchEventKinds;
import java.util.List;

import clh.inspecciones.com.inspecciones_v2.Clases.DetalleInspeccionBD;
import clh.inspecciones.com.inspecciones_v2.R;

public class DetalleInspeccionAdapter extends BaseAdapter implements View.OnClickListener{

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
            vh.bateriaDesconectada = (CheckBox) convertView.findViewById(R.id.cb_baterias);
            vh.fichaSeguridad = (CheckBox) convertView.findViewById(R.id.cb_fichaseguridad);
            vh.transponderTractora = (CheckBox) convertView.findViewById(R.id.cb_transpondert);
            vh.transponderCisterna = (CheckBox) convertView.findViewById(R.id.cb_transponderc);
            vh.frenoEstacionamiento = (CheckBox) convertView.findViewById(R.id.cb_frenoestacionamiento);
            vh.apagallamas = (CheckBox) convertView.findViewById(R.id.cb_apagallamas);
            vh.bajadaTagsPlanta = (CheckBox)convertView.findViewById(R.id.cb_bajadatags);
            vh.adrCisterna = (CheckBox) convertView.findViewById(R.id.cb_adrc);
            vh.adrConductor = (CheckBox) convertView.findViewById(R.id.cb_adrcond);
            vh.adrTractora = (CheckBox) convertView.findViewById(R.id.cb_adrt);
            vh.mangueraGases = (CheckBox) convertView.findViewById(R.id.cb_manguera_gases);
            vh.tomaTierra = (CheckBox)convertView.findViewById(R.id.cb_scully);
            vh.movilDesconectado = (CheckBox) convertView.findViewById(R.id.cb_movil);
            vh.estanqueidadCajon = (CheckBox)convertView.findViewById(R.id.cb_estanqueidadcajon);
            vh.estanqueidadCisterna = (CheckBox)convertView.findViewById(R.id.cb_estanqueidadcisterna);
            vh.estanqueidadEquiposTrasiego = (CheckBox)convertView.findViewById(R.id.cb_estanqueidadequipostrasiegos);
            vh.estanqueidadValvulasAPI = (CheckBox)convertView.findViewById(R.id.cb_estanqueidadvalvulasapi);
            vh.estanqueidadValvulasFondo = (CheckBox)convertView.findViewById(R.id.cb_estanqueidadvalvulasfondo);
            vh.interruptorEmergencia = (CheckBox) convertView.findViewById(R.id.cb_interruptores);
            vh.itvCisterna = (CheckBox) convertView.findViewById(R.id.cb_itvc);
            vh.itvTractora = (CheckBox) convertView.findViewById(R.id.cb_itvt);
            vh.lecturaTagsIsleta = (CheckBox)convertView.findViewById(R.id.cb_lecturatagsisleta);
            vh.tagsCorrectos = (CheckBox)convertView.findViewById(R.id.cb_tags);
            vh.permisoCirculacion = (CheckBox) convertView.findViewById(R.id.cb_permisoconducir);
            vh.posicionVehiculo = (CheckBox) convertView.findViewById(R.id.cb_posicionvehiculo);
            vh.purgaCompartimentos = (CheckBox) convertView.findViewById(R.id.cb_purga);
            vh.recogerAlbaran = (CheckBox)convertView.findViewById(R.id.cb_recogealbaran);
            vh.ropa = (CheckBox) convertView.findViewById(R.id.cb_ropa);
            vh.superficieSupAntiDes = (CheckBox) convertView.findViewById(R.id.cb_superficiesuperior);
            vh.tc2 = (CheckBox) convertView.findViewById(R.id.cb_tc2);
          //  vh.guardar = (Button) convertView.findViewById(R.id.guardar_cambios);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }

        DetalleInspeccionBD detalleInspeccionBD = inspeccionBDS.get(position);

       // vh.guardar.setOnClickListener(this);

        vh.bateriaDesconectada.setChecked(detalleInspeccionBD.getAccDesconectadorBaterias());
        vh.fichaSeguridad.setChecked(detalleInspeccionBD.getFichaSeguridad());
        vh.transponderTractora.setChecked(detalleInspeccionBD.getTransponderTractora());
        vh.transponderCisterna.setChecked(detalleInspeccionBD.getTransponderCisterna());
        vh.frenoEstacionamiento.setChecked(detalleInspeccionBD.getAccFrenoEstacionamientoMarchaCorta());
        vh.apagallamas.setChecked(detalleInspeccionBD.getApagallamas());
        vh.bajadaTagsPlanta.setChecked(detalleInspeccionBD.getBajadaTagPlanta());
        vh.adrCisterna.setChecked(detalleInspeccionBD.getAdrCisterna());
        vh.adrConductor.setChecked(detalleInspeccionBD.getAdrConductor());
        vh.adrTractora.setChecked(detalleInspeccionBD.getAdrTractoraRigido());
        vh.mangueraGases.setChecked(detalleInspeccionBD.getConexionMangueraGases());
        vh.tomaTierra.setChecked(detalleInspeccionBD.getConexionTomaTierra());
        vh.movilDesconectado.setChecked(detalleInspeccionBD.getDescTfnoMovil());
        vh.estanqueidadCajon.setChecked(detalleInspeccionBD.getEstanqueidadCajon());
        vh.estanqueidadCisterna.setChecked(detalleInspeccionBD.getEstanqueidadCisterna());
        vh.estanqueidadEquiposTrasiego.setChecked(detalleInspeccionBD.getEstanqueidadEquiposTrasiego());
        vh.estanqueidadValvulasAPI.setChecked(detalleInspeccionBD.getEstanqueidadValvulasAPI());
        vh.estanqueidadValvulasFondo.setChecked(detalleInspeccionBD.getEstanqueidadValvulasFondo());
        vh.interruptorEmergencia.setChecked(detalleInspeccionBD.getInterrupEmergenciaYFuego());
        vh.itvCisterna.setChecked(detalleInspeccionBD.getItvCisterna());
        vh.itvTractora.setChecked(detalleInspeccionBD.getItvTractoraRigido());
        vh.lecturaTagsIsleta.setChecked(detalleInspeccionBD.getLecturaTagIsleta());
        vh.tagsCorrectos.setChecked(detalleInspeccionBD.getMontajeCorrectoTags());
        vh.permisoCirculacion.setChecked(detalleInspeccionBD.getPermisoConducir());
        vh.posicionVehiculo.setChecked(detalleInspeccionBD.getPosicionamientoAdecuadoEnIsleta());
        vh.purgaCompartimentos.setChecked(detalleInspeccionBD.getPurgaCompartimentos());
        vh.recogerAlbaran.setChecked(detalleInspeccionBD.getRecogerAlbaran());
        vh.ropa.setChecked(detalleInspeccionBD.getRopaSeguridad());
        vh.superficieSupAntiDes.setChecked(detalleInspeccionBD.getSuperficieSupAntideslizante());
        vh.tc2.setChecked(detalleInspeccionBD.getTc2());

        return convertView;

        }

    @Override
    public void onClick(View v) {

    }

    public class ViewHolder{

        CheckBox bateriaDesconectada;
        CheckBox fichaSeguridad;
        CheckBox transponderTractora;
        CheckBox transponderCisterna;
        CheckBox frenoEstacionamiento;
        CheckBox apagallamas;
        CheckBox bajadaTagsPlanta;
        CheckBox adrCisterna;
        CheckBox adrConductor;
        CheckBox adrTractora;
        //CheckBox bloqueo;
        CheckBox mangueraGases;
        CheckBox tomaTierra;
        CheckBox movilDesconectado;
        CheckBox estanqueidadCajon;
        CheckBox estanqueidadCisterna;
        CheckBox estanqueidadEquiposTrasiego;
        CheckBox estanqueidadValvulasAPI;
        CheckBox estanqueidadValvulasFondo;
        CheckBox interruptorEmergencia;
        CheckBox itvCisterna;
        CheckBox itvTractora;
        CheckBox lecturaTagsIsleta;
        CheckBox tagsCorrectos;
        CheckBox permisoCirculacion;
        CheckBox posicionVehiculo;
        CheckBox purgaCompartimentos;
        CheckBox recogerAlbaran;
        CheckBox ropa;
        CheckBox superficieSupAntiDes;
        CheckBox tc2;
       // Button guardar;

    }
}
