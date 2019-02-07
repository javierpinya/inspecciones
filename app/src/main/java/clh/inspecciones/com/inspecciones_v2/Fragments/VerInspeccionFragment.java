package clh.inspecciones.com.inspecciones_v2.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import clh.inspecciones.com.inspecciones_v2.Adapters.VerCompartimentosAdapter;
import clh.inspecciones.com.inspecciones_v2.Clases.BuscarInspeccionClass;
import clh.inspecciones.com.inspecciones_v2.Clases.CACompartimentosBD;
import clh.inspecciones.com.inspecciones_v2.Clases.DecodificaImagenClass;
import clh.inspecciones.com.inspecciones_v2.R;
import clh.inspecciones.com.inspecciones_v2.SingleTones.VolleySingleton;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class VerInspeccionFragment extends Fragment implements View.OnClickListener {

    private BuscarInspeccionClass buscarInspeccionClass;
    private RealmResults<CACompartimentosBD> compartimentosBD;
    private List<CACompartimentosBD> listaCompartimentos;
    Realm realm;
    private String inspeccion;
    private String user;
    private String pass;

    private SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

    private String fecha;
    private Button verFotos;

    //Views del layout
    private TextView tv_inspeccion;
    private TextView tv_inspeccion1;
    private TextView tv_tractora;
    private TextView tv_cisterna;
    private TextView tv_instalacion;
    private TextView tv_cod_cond;
    private TextView tv_transportistaresp;
    private TextView tv_albaran;
    private TextView tv_empresatablacalibracion;
    private CheckBox cb_permisoconducir;
    private CheckBox cb_adrcond;
    private CheckBox cb_tc2;
    private CheckBox cb_itvt;
    private CheckBox cb_adrt;
    private CheckBox cb_itvc;
    private CheckBox cb_adrc;
    private CheckBox cb_fichaseguridad;
    private TextView tv_tablacalibracion;
    private TextView tv_tablacalibracion1;
    private CheckBox cb_transpondert;
    private CheckBox cb_transponderc;
    private CheckBox cb_superficiesuperior;
    private CheckBox cb_posicionvehiculo;
    private CheckBox cb_frenoestacionamiento;
    private CheckBox cb_baterias;
    private CheckBox cb_apagallamas;
    private CheckBox cb_movil;
    private CheckBox cb_interruptores;
    private CheckBox cb_scully;
    private CheckBox cb_mangueragases;
    private CheckBox cb_estcajon;
    private CheckBox cb_estcisterna;
    private CheckBox cb_estequipostrasiego;
    private CheckBox cb_estvalvulasapi;
    private CheckBox cb_estvalvulasfondo;
    private CheckBox cb_purga;
    private CheckBox cb_bajadatags;
    private CheckBox cb_tags;
    private CheckBox cb_lecturatagsisleta;
    private CheckBox cb_recogealbaran;
    private CheckBox cb_ropa;
    private TextView tv_observaciones;
    private Button btn_verfotos;
    private String matTractora;
    private String matCisterna;
    private String matricula;
    private List<Integer> compartimentos;
    private VerCompartimentosAdapter adapter;
    private String numFotos;
    private String url = "http://pruebaalumnosandroid.esy.es/inspecciones/descargar_fotos_inspeccion_elegida.php";
    private String urlDescargarInspeccionElegida="http://pruebaalumnosandroid.esy.es/inspecciones/descargarFotosInspeccionElegida.php";

    private List<String> listaFotos = new ArrayList<>();
    private DecodificaImagenClass decodificaImagenClass = new DecodificaImagenClass();
    private int progreso;
    private String secuencial;
    private String foto;
    private int numFotosDescargadas=0;

    public VerInspeccionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ver_inspeccion, container, false);


        user = getArguments().getString("user", "sin_datos_user");
        pass = getArguments().getString("pass", "sin_datos_pass");
        inspeccion = getArguments().getString("inspeccion", "sin_datos_inspeccion");
        numFotos = getArguments().getString("numFotos", "0");
        verFotos = view.findViewById(R.id.btn_verFotos);
        verFotos.setText("VER " + numFotos + " FOTOS");

        verFotos.setOnClickListener(this);


        tv_inspeccion = view.findViewById(R.id.tv_inspeccion);
        tv_inspeccion1 = view.findViewById(R.id.tv_inspeccion1);
        tv_tractora = view.findViewById(R.id.tv_tractora);
        tv_cisterna = view.findViewById(R.id.tv_cisterna);
        tv_instalacion = view.findViewById(R.id.tv_instalacion);
        tv_cod_cond = view.findViewById(R.id.tv_codcond);
        tv_transportistaresp = view.findViewById(R.id.tv_transportistaresp);
        tv_albaran = view.findViewById(R.id.tv_albaran);
        tv_empresatablacalibracion = view.findViewById(R.id.tv_empresatablacalibracion);
        cb_permisoconducir = view.findViewById(R.id.cb_permisoconducir);
        cb_adrcond = view.findViewById(R.id.cb_adrcond);
        cb_tc2 = view.findViewById(R.id.cb_tc2);
        cb_itvt = view.findViewById(R.id.cb_itvt);
        cb_adrt = view.findViewById(R.id.cb_adrt);
        cb_itvc = view.findViewById(R.id.cb_itvc);
        cb_adrc = view.findViewById(R.id.cb_adrc);
        cb_fichaseguridad = view.findViewById(R.id.cb_fichaseguridad);
        tv_tablacalibracion = view.findViewById(R.id.tv_tablacalibracion);
        tv_tablacalibracion1 = view.findViewById(R.id.tv_tablacalibracion1);
        cb_transpondert = view.findViewById(R.id.cbtranspondert);
        cb_transponderc = view.findViewById(R.id.cb_transponderc);
        cb_superficiesuperior = view.findViewById(R.id.cb_superficiesuperior);
        cb_posicionvehiculo = view.findViewById(R.id.cb_posicionvehiculo);
        cb_frenoestacionamiento = view.findViewById(R.id.cb_frenoestacionamiento);
        cb_baterias = view.findViewById(R.id.cb_baterias);
        cb_apagallamas = view.findViewById(R.id.cb_apagallamas);
        cb_movil = view.findViewById(R.id.cb_movil);
        cb_interruptores = view.findViewById(R.id.cb_interruptores);
        cb_scully = view.findViewById(R.id.cb_scully);
        cb_mangueragases = view.findViewById(R.id.cb_manguera_gases);
        cb_estcajon = view.findViewById(R.id.cb_estanqueidadcajon);
        cb_estcisterna = view.findViewById(R.id.cb_estanqueidadcisterna);
        cb_estequipostrasiego = view.findViewById(R.id.cb_estanqueidadequipostrasiegos);
        cb_estvalvulasapi = view.findViewById(R.id.cb_estanqueidadvalvulasapi);
        cb_estvalvulasfondo = view.findViewById(R.id.cb_estanqueidadvalvulasfondo);
        cb_purga = view.findViewById(R.id.cb_purga);
        cb_bajadatags = view.findViewById(R.id.cb_bajadatags);
        cb_tags = view.findViewById(R.id.cb_tags);
        cb_lecturatagsisleta = view.findViewById(R.id.cb_lecturatagsisleta);
        cb_recogealbaran = view.findViewById(R.id.cb_recogealbaran);
        cb_ropa = view.findViewById(R.id.cb_ropa);
        tv_observaciones = view.findViewById(R.id.observaciones);
        btn_verfotos = view.findViewById(R.id.btn_verFotos);

        realm = Realm.getDefaultInstance();

        buscarInspeccionClass = realm.where(BuscarInspeccionClass.class).equalTo("inspeccion", inspeccion).findFirst();

        try {
            tv_inspeccion1.setText(buscarInspeccionClass.getInspeccion());
            tv_tractora.setText(buscarInspeccionClass.getTractora());
            tv_cisterna.setText(buscarInspeccionClass.getCisterna());
            matTractora=tv_tractora.getText().toString();
            matCisterna=tv_cisterna.getText().toString();
            matricula = matTractora + matCisterna;
            tv_instalacion.setText(buscarInspeccionClass.getInstalacion());
            tv_cod_cond.setText(buscarInspeccionClass.getConductor());
            tv_transportistaresp.setText(buscarInspeccionClass.getTransportista());
            tv_albaran.setText(buscarInspeccionClass.getAlbaran());
            tv_empresatablacalibracion.setText(buscarInspeccionClass.getEmpresaTablaCalibracion());
            cb_permisoconducir.setChecked(buscarInspeccionClass.getPermisoConducir());
            cb_adrcond.setChecked(buscarInspeccionClass.getAdrConductor());
            cb_tc2.setChecked(buscarInspeccionClass.getTc2());
            cb_itvt.setChecked(buscarInspeccionClass.getItvTractoraRigido());
            cb_adrt.setChecked(buscarInspeccionClass.getAdrTractoraRigido());
            cb_fichaseguridad.setChecked(buscarInspeccionClass.getFichaSeguridad());
            //tv_tablacalibracion1.setText(buscarInspeccionClass.getFechaTablaCalibracion().toString());
            cb_transpondert.setChecked(buscarInspeccionClass.getTransponderTractora());
            cb_transponderc.setChecked(buscarInspeccionClass.getTransponderCisterna());
            cb_superficiesuperior.setChecked(buscarInspeccionClass.getSuperficieSupAntideslizante());
            cb_posicionvehiculo.setChecked(buscarInspeccionClass.getPosicionamientoAdecuadoEnIsleta());
            cb_frenoestacionamiento.setChecked(buscarInspeccionClass.getPosicionamientoAdecuadoEnIsleta());
            cb_baterias.setChecked(buscarInspeccionClass.getAccDesconectadorBaterias());
            cb_apagallamas.setChecked(buscarInspeccionClass.getApagallamas());
            cb_movil.setChecked(buscarInspeccionClass.getDescTfnoMovil());
            cb_interruptores.setChecked(buscarInspeccionClass.getInterrupEmergenciaYFuego());
            cb_scully.setChecked(buscarInspeccionClass.getConexionTomaTierra());
            cb_mangueragases.setChecked(buscarInspeccionClass.getConexionMangueraGases());
            cb_estcajon.setChecked(buscarInspeccionClass.getEstanqueidadCajon());
            cb_estcisterna.setChecked(buscarInspeccionClass.getEstanqueidadCisterna());
            cb_estvalvulasfondo.setChecked(buscarInspeccionClass.getEstanqueidadValvulasFondo());
            cb_estvalvulasapi.setChecked(buscarInspeccionClass.getEstanqueidadValvulasAPI());
            cb_estequipostrasiego.setChecked(buscarInspeccionClass.getEstanqueidadEquiposTrasiego());
            cb_purga.setChecked(buscarInspeccionClass.getPurgaCompartimentos());
            cb_bajadatags.setChecked(buscarInspeccionClass.getBajadaTagPlanta());
            cb_tags.setChecked(buscarInspeccionClass.getMontajeCorrectoTags());
            cb_lecturatagsisleta.setChecked(buscarInspeccionClass.getLecturaTagIsleta());
            cb_recogealbaran.setChecked(buscarInspeccionClass.getRecogerAlbaran());
            cb_ropa.setChecked(buscarInspeccionClass.getRopaSeguridad());
        }catch (Exception e){
            e.printStackTrace();
        }
        //listaCompartimentos = realm.where(CACompartimentosBD.class).equalTo("matricula", matricula).findAll();

/*
        if (listaCompartimentos.size() > 0) {
            //Toast.makeText(getActivity(), "caCompartimentosBD.get(0).getCan_capacidad(): " + caCompartimentosBD.get(0).getCan_capacidad(), Toast.LENGTH_SHORT).show();
            adapter = new VerCompartimentosAdapter(R.layout.compartimentos_listview_item, listaCompartimentos);
            mRecyclerView.setAdapter(adapter);
        }else{
            Toast.makeText(getActivity(), "la query no da resultados...", Toast.LENGTH_SHORT).show();
        }
        */
        //descargarFotosInspeccionElegida(user, pass, inspeccion);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View v) {

        //descargarFotos(user, pass, inspeccion);
        //Abrir fragment con las fotos (gridview)

    }

    public void descargarFotosInspeccionElegida(final String user, final String pass, final String inspeccion){
        StringRequest sr = new StringRequest(Request.Method.POST, urlDescargarInspeccionElegida, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    //listaVehiculos.clear();
                    //Convierto la respuesta, de tipo String, a un JSONObject.
                    JSONObject jsonObject = new JSONObject(response);

                    //Cramos un JSONArray del objeto JSON "vehiculo"
                    JSONArray jsonVehiculo = jsonObject.optJSONArray("fotos_inspeccion");
                    //Del objeto JSON "vehiculo" capturamos el primer grupo de valores

                    numFotosDescargadas=jsonVehiculo.length();

                    if(numFotosDescargadas>0) {

                        for (int i = 0; i < jsonVehiculo.length(); i++) {
                            // = new DecodificaImagenClass().execute(foto, inspeccion, secuencial);
                            JSONObject jsonObject1 = null;
                            jsonObject1 = jsonVehiculo.getJSONObject(i);
                            foto = (jsonObject1.optString("foto"));
                            secuencial = String.valueOf(i + 1);
                            decodificaImagenClass.execute(foto, inspeccion, secuencial);
                            //rutaFoto.add(decodificaImagenClass.getRuta());
                        }
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user", user);
                params.put("pass", pass);
                params.put("num_inspeccion", inspeccion);
                return params;
            }

        };
        VolleySingleton.getInstanciaVolley(getContext()).addToRequestqueue(sr);
    }

    /*
    private void descargarFotos(final String user, final String pass, final String inspeccion) {
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    //Convierto la respuesta, de tipo String, a un JSONObject.
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray json = jsonObject.optJSONArray("fotos_inspeccion");

                    for(int i=0;i<json.length();i++){
                        listaFotos.add(json.optJSONObject(i).optString("foto"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "e.printStackTrace: " + e.toString(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user", user);
                params.put("pass", pass);
                params.put("num_inspeccion", inspeccion);
                return params;
            }
        };
        VolleySingleton.getInstanciaVolley(getContext()).addToRequestqueue(sr);
    }
    */
}
