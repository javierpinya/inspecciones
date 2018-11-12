package clh.inspecciones.com.inspecciones_v2.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import clh.inspecciones.com.inspecciones_v2.Adapters.DetalleInspeccionAdapter;
import clh.inspecciones.com.inspecciones_v2.Clases.DetalleInspeccionBD;
import clh.inspecciones.com.inspecciones_v2.R;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleInspeccionFragment extends Fragment implements RealmChangeListener<RealmResults<DetalleInspeccionBD>>, View.OnClickListener{

    public dataListener callback;
    private ListView mListView;
    private Realm realm;
    private RealmResults<DetalleInspeccionBD> detalleInspeccionBDS;
    private RealmResults<DetalleInspeccionBD> detalleInspeccionToast;
    private String inspeccionIntent;
    private DetalleInspeccionAdapter adapter;
    private Button guardar;
    private Button continuar;
    private Boolean purgas;
    private DetalleInspeccionBD inspecciones;
    private String instalacion;
    private String albaran;
    private String transportista;
    private String tabla_calibracion;

    private List<Boolean> checklist;

    private TextView tractora;
    private TextView cisterna;
    private TextView conductor;
    private CheckBox bateriaDesconectada;
    private CheckBox fichaSeguridad;
    private CheckBox transponderTractora;
    private CheckBox transponderCisterna;
    private CheckBox frenoEstacionamiento;
    private CheckBox apagallamas;
    private CheckBox bajadaTagsPlanta;
    private CheckBox adrCisterna;
    private CheckBox adrConductor;
    private CheckBox adrTractora;
    //CheckBox bloqueo;
    private CheckBox mangueraGases;
    private CheckBox tomaTierra;
    private CheckBox movilDesconectado;
    private CheckBox estanqueidadCajon;
    private CheckBox estanqueidadCisterna;
    private CheckBox estanqueidadEquiposTrasiego;
    private CheckBox estanqueidadValvulasAPI;
    private CheckBox estanqueidadValvulasFondo;
    private CheckBox interruptorEmergencia;
    private CheckBox itvCisterna;
    private CheckBox itvTractora;
    private CheckBox lecturaTagsIsleta;
    private CheckBox tagsCorrectos;
    private CheckBox permisoCirculacion;
    private CheckBox posicionVehiculo;
    private CheckBox purgaCompartimentos;
    private CheckBox recogerAlbaran;
    private CheckBox ropa;
    private CheckBox superficieSupAntiDes;
    private CheckBox tc2;

    private Boolean chequeo;

    private SimpleDateFormat parseador = new SimpleDateFormat("dd-MM-yyyy");




    public DetalleInspeccionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            callback =(dataListener)context;
        }catch(Exception e){
            throw new ClassCastException(context.toString() + " should implement dataListener");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalle_inspeccion, container, false);
        //View view = inflater.inflate(R.layout.detalle_inspecciones_adapter, container, false);

        checklist = new ArrayList<>();

        tractora = (TextView)view.findViewById(R.id.tv_tractoramatricula);
        cisterna = (TextView)view.findViewById(R.id.tv_cisternamatricula);
        conductor = (TextView)view.findViewById(R.id.tv_codcond);
        bateriaDesconectada = (CheckBox) view.findViewById(R.id.cb_baterias);
        fichaSeguridad = (CheckBox) view.findViewById(R.id.cb_fichaseguridad);
        transponderTractora = (CheckBox) view.findViewById(R.id.cb_transpondert);
        transponderCisterna = (CheckBox) view.findViewById(R.id.cb_transponderc);
        frenoEstacionamiento = (CheckBox) view.findViewById(R.id.cb_frenoestacionamiento);
        apagallamas = (CheckBox) view.findViewById(R.id.cb_apagallamas);
        bajadaTagsPlanta = (CheckBox)view.findViewById(R.id.cb_bajadatags);
        adrCisterna = (CheckBox) view.findViewById(R.id.cb_adrc);
        adrConductor = (CheckBox) view.findViewById(R.id.cb_adrcond);
        adrTractora = (CheckBox) view.findViewById(R.id.cb_adrt);
        mangueraGases = (CheckBox) view.findViewById(R.id.cb_manguera_gases);
        tomaTierra = (CheckBox)view.findViewById(R.id.cb_scully);
        movilDesconectado = (CheckBox) view.findViewById(R.id.cb_movil);
        estanqueidadCajon = (CheckBox)view.findViewById(R.id.cb_estanqueidadcajon);
        estanqueidadCisterna = (CheckBox)view.findViewById(R.id.cb_estanqueidadcisterna);
        estanqueidadEquiposTrasiego = (CheckBox)view.findViewById(R.id.cb_estanqueidadequipostrasiegos);
        estanqueidadValvulasAPI = (CheckBox)view.findViewById(R.id.cb_estanqueidadvalvulasapi);
        estanqueidadValvulasFondo = (CheckBox)view.findViewById(R.id.cb_estanqueidadvalvulasfondo);
        interruptorEmergencia = (CheckBox) view.findViewById(R.id.cb_interruptores);
        itvCisterna = (CheckBox) view.findViewById(R.id.cb_itvc);
        itvTractora = (CheckBox) view.findViewById(R.id.cb_itvt);
        lecturaTagsIsleta = (CheckBox)view.findViewById(R.id.cb_lecturatagsisleta);
        tagsCorrectos = (CheckBox)view.findViewById(R.id.cb_tags);
        permisoCirculacion = (CheckBox) view.findViewById(R.id.cb_permisoconducir);
        posicionVehiculo = (CheckBox) view.findViewById(R.id.cb_posicionvehiculo);
        purgaCompartimentos = (CheckBox) view.findViewById(R.id.cb_purga);
        recogerAlbaran = (CheckBox)view.findViewById(R.id.cb_recogealbaran);
        ropa = (CheckBox) view.findViewById(R.id.cb_ropa);
        superficieSupAntiDes = (CheckBox) view.findViewById(R.id.cb_superficiesuperior);
        tc2 = (CheckBox) view.findViewById(R.id.cb_tc2);

        //mListView = view.findViewById(R.id.lv_detalleinspecciones);
        guardar = (Button)view.findViewById(R.id.guardar_cambios);


        guardar.setOnClickListener(this);

        realm = Realm.getDefaultInstance();
        /*
        if (realm.isEmpty()==false){
            realm.beginTransaction();
            realm.deleteAll();
            realm.commitTransaction();
        }*/


        return view;
    }

    public void renderText(String inspeccion, String Instalacion, String albaran, String transportista, String tabla_calibracion){
        inspeccionIntent = inspeccion.trim();
        detalleInspeccionBDS=realm.where(DetalleInspeccionBD.class).findAll();
        inspecciones = realm.where(DetalleInspeccionBD.class).equalTo("inspeccion", inspeccionIntent).findFirst();

        this.instalacion = Instalacion;
        this.albaran = albaran;
        this.transportista = transportista;
        this.tabla_calibracion = tabla_calibracion;

        detalleInspeccionBDS.addChangeListener(this);

        bateriaDesconectada.setChecked(detalleInspeccionBDS.get(0).getAccDesconectadorBaterias());
        fichaSeguridad.setChecked(detalleInspeccionBDS.get(0).getFichaSeguridad());
        transponderTractora.setChecked(detalleInspeccionBDS.get(0).getTransponderTractora());
        transponderCisterna.setChecked(detalleInspeccionBDS.get(0).getTransponderCisterna());
        frenoEstacionamiento.setChecked(detalleInspeccionBDS.get(0).getAccFrenoEstacionamientoMarchaCorta());
        apagallamas.setChecked(detalleInspeccionBDS.get(0).getApagallamas());
        bajadaTagsPlanta.setChecked(detalleInspeccionBDS.get(0).getBajadaTagPlanta());
        adrCisterna.setChecked(detalleInspeccionBDS.get(0).getAdrCisterna());
        adrConductor.setChecked(detalleInspeccionBDS.get(0).getAdrConductor());
        adrTractora.setChecked(detalleInspeccionBDS.get(0).getAdrTractoraRigido());
        mangueraGases.setChecked(detalleInspeccionBDS.get(0).getConexionMangueraGases());
        tomaTierra.setChecked(detalleInspeccionBDS.get(0).getConexionTomaTierra());
        movilDesconectado.setChecked(detalleInspeccionBDS.get(0).getDescTfnoMovil());
        estanqueidadCajon.setChecked(detalleInspeccionBDS.get(0).getEstanqueidadCajon());
        estanqueidadCisterna.setChecked(detalleInspeccionBDS.get(0).getEstanqueidadCisterna());
        estanqueidadEquiposTrasiego.setChecked(detalleInspeccionBDS.get(0).getEstanqueidadEquiposTrasiego());
        estanqueidadValvulasAPI.setChecked(detalleInspeccionBDS.get(0).getEstanqueidadValvulasAPI());
        estanqueidadValvulasFondo.setChecked(detalleInspeccionBDS.get(0).getEstanqueidadValvulasFondo());
        interruptorEmergencia.setChecked(detalleInspeccionBDS.get(0).getInterrupEmergenciaYFuego());
        itvCisterna.setChecked(detalleInspeccionBDS.get(0).getItvCisterna());
        itvTractora.setChecked(detalleInspeccionBDS.get(0).getItvTractoraRigido());
        lecturaTagsIsleta.setChecked(detalleInspeccionBDS.get(0).getLecturaTagIsleta());
        tagsCorrectos.setChecked(detalleInspeccionBDS.get(0).getMontajeCorrectoTags());
        permisoCirculacion.setChecked(detalleInspeccionBDS.get(0).getPermisoConducir());
        posicionVehiculo.setChecked(detalleInspeccionBDS.get(0).getPosicionamientoAdecuadoEnIsleta());
        purgaCompartimentos.setChecked(inspecciones.getPurgaCompartimentos());
        recogerAlbaran.setChecked(detalleInspeccionBDS.get(0).getRecogerAlbaran());
        ropa.setChecked(detalleInspeccionBDS.get(0).getRopaSeguridad());
        superficieSupAntiDes.setChecked(detalleInspeccionBDS.get(0).getSuperficieSupAntideslizante());
        tc2.setChecked(detalleInspeccionBDS.get(0).getTc2());



    }

    @Override
    public void onChange(RealmResults<DetalleInspeccionBD> detalleInspeccionBDS) {
        //adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.guardar_cambios:
                checklist.add(bateriaDesconectada.isChecked());
                checklist.add(fichaSeguridad.isChecked());
                checklist.add(transponderTractora.isChecked());
                checklist.add(transponderCisterna.isChecked());
                checklist.add(frenoEstacionamiento.isChecked());
                checklist.add(apagallamas.isChecked());
                checklist.add(bajadaTagsPlanta.isChecked());
                checklist.add(adrCisterna.isChecked());
                checklist.add(adrConductor.isChecked());
                checklist.add(adrTractora.isChecked());
                checklist.add(mangueraGases.isChecked());
                checklist.add(tomaTierra.isChecked());
                checklist.add(movilDesconectado.isChecked());
                checklist.add(estanqueidadCajon.isChecked());
                checklist.add(estanqueidadCisterna.isChecked());
                checklist.add(estanqueidadEquiposTrasiego.isChecked());
                checklist.add(estanqueidadValvulasAPI.isChecked());
                checklist.add(estanqueidadValvulasFondo.isChecked());
                checklist.add(interruptorEmergencia.isChecked());
                checklist.add(itvCisterna.isChecked());
                checklist.add(itvTractora.isChecked());
                checklist.add(lecturaTagsIsleta.isChecked());
                checklist.add(permisoCirculacion.isChecked());
                checklist.add(posicionVehiculo.isChecked());
                checklist.add(purgaCompartimentos.isChecked());
                checklist.add(recogerAlbaran.isChecked());
                checklist.add(ropa.isChecked());
                checklist.add(superficieSupAntiDes.isChecked());
                checklist.add(tc2.isChecked());
                guardar(inspecciones, checklist);
                break;
                default:
                    break;
        }



    }

    public void guardar(DetalleInspeccionBD detalleInspeccionBD, List<Boolean> checklist){
        realm.beginTransaction();
        detalleInspeccionBD.setInstalacion(instalacion);
        detalleInspeccionBD.setAlbaran(albaran);
        detalleInspeccionBD.setTransportista(transportista);
        detalleInspeccionBD.setTablaCalibracion(tabla_calibracion);
        detalleInspeccionBD.setAccDesconectadorBaterias(checklist.get(0));
        detalleInspeccionBD.setFichaSeguridad(checklist.get(1));
        detalleInspeccionBD.setTransponderTractora(checklist.get(2));
        detalleInspeccionBD.setTransponderCisterna(checklist.get(3));
        detalleInspeccionBD.setAccFrenoEstacionamientoMarchaCorta(checklist.get(4));
        detalleInspeccionBD.setApagallamas(checklist.get(5));
        detalleInspeccionBD.setBajadaTagPlanta(checklist.get(6));
        detalleInspeccionBD.setAdrCisterna(checklist.get(7));
        detalleInspeccionBD.setAdrConductor(checklist.get(8));
        detalleInspeccionBD.setAdrTractoraRigido(checklist.get(9));
        detalleInspeccionBD.setConexionMangueraGases(checklist.get(10));
        detalleInspeccionBD.setConexionTomaTierra(checklist.get(11));
        detalleInspeccionBD.setDescTfnoMovil(checklist.get(12));
        detalleInspeccionBD.setEstanqueidadCajon(checklist.get(13));
        detalleInspeccionBD.setEstanqueidadCisterna(checklist.get(14));
        detalleInspeccionBD.setEstanqueidadEquiposTrasiego(checklist.get(15));
        detalleInspeccionBD.setEstanqueidadValvulasAPI(checklist.get(16));
        detalleInspeccionBD.setEstanqueidadValvulasFondo(checklist.get(17));
        detalleInspeccionBD.setInterrupEmergenciaYFuego(checklist.get(18));
        detalleInspeccionBD.setItvCisterna(checklist.get(19));
        detalleInspeccionBD.setItvTractoraRigido(checklist.get(20));
        detalleInspeccionBD.setLecturaTagIsleta(checklist.get(21));
        detalleInspeccionBD.setMontajeCorrectoTags(checklist.get(22));
        detalleInspeccionBD.setPermisoConducir(checklist.get(23));
        detalleInspeccionBD.setPosicionamientoAdecuadoEnIsleta(checklist.get(24));
        detalleInspeccionBD.setRecogerAlbaran(checklist.get(25));
        detalleInspeccionBD.setRopaSeguridad(checklist.get(26));
        detalleInspeccionBD.setSuperficieSupAntideslizante(checklist.get(27));
        detalleInspeccionBD.setTc2(checklist.get(28));
        realm.copyToRealmOrUpdate(detalleInspeccionBD);
        realm.commitTransaction();

        Toast.makeText(getActivity(), "Cambios Guardados", Toast.LENGTH_SHORT).show();
        //Toast.makeText(getActivity(), "size: " + detalleInspeccionBDS.size(), Toast.LENGTH_SHORT).show();

    }


    public interface dataListener{
    }

}
