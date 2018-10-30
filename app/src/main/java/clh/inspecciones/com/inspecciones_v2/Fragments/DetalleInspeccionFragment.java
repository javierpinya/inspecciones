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
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;

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
    private Boolean purgas;

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
        if (realm.isEmpty()==false){
            realm.beginTransaction();
            realm.deleteAll();
            realm.commitTransaction();
        }


        return view;
    }

    public void renderText(String inspeccion){
       // guardar.setVisibility(View.VISIBLE);
        inspeccionIntent = inspeccion.trim();
        detalleInspeccionBDS = realm.where(DetalleInspeccionBD.class).findAll();
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
        purgaCompartimentos.setChecked(detalleInspeccionBDS.get(0).getPurgaCompartimentos());
        recogerAlbaran.setChecked(detalleInspeccionBDS.get(0).getRecogerAlbaran());
        ropa.setChecked(detalleInspeccionBDS.get(0).getRopaSeguridad());
        superficieSupAntiDes.setChecked(detalleInspeccionBDS.get(0).getSuperficieSupAntideslizante());
        tc2.setChecked(detalleInspeccionBDS.get(0).getTc2());

        //adapter = new ListView(getActivity(), ,R.layout.detalle_inspecciones_adapter);
        //mListView.setAdapter(adapter);


    }

    @Override
    public void onChange(RealmResults<DetalleInspeccionBD> detalleInspeccionBDS) {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        DetalleInspeccionBD inspeccion; // = new DetalleInspeccionBD();
        realm.beginTransaction();
        inspeccion = detalleInspeccionBDS.get(0);
        inspeccion.setPurgaCompartimentos(purgaCompartimentos.isChecked());
        realm.copyToRealmOrUpdate(inspeccion);
        realm.commitTransaction();

        detalleInspeccionToast = realm.where(DetalleInspeccionBD.class).findAll();
        detalleInspeccionToast.addChangeListener(this);
        purgas = detalleInspeccionToast.get(0).getPurgaCompartimentos();
        Toast.makeText(getActivity(), "purgas: " + purgas.toString(), Toast.LENGTH_SHORT).show();

    }


    public interface dataListener{
    }

}
