package clh.inspecciones.com.inspecciones_v2.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import clh.inspecciones.com.inspecciones_v2.Clases.DetalleInspeccionBD;
import clh.inspecciones.com.inspecciones_v2.R;
import clh.inspecciones.com.inspecciones_v2.SingleTones.VolleySingleton;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class CabeceraInspeccionFragment extends Fragment implements RealmChangeListener<RealmResults<DetalleInspeccionBD>>, View.OnClickListener {

    public dataListener callback;
    private Realm realm;
    private RealmResults<DetalleInspeccionBD> inspeccionBDs;
    private DetalleInspeccionBD inspeccionBD;
    private String inspeccion=null;
    private String returnInspeccion;
    private Button btn_siguiente;
    private Button btn_compartimentos;
    private EditText etIa;
    private EditText etAlbaran;
    private EditText etTrans;
    private EditText etTablaCal;
    private String ia="";
    private String albaran="";
    private String transportista="";
    private String tabla_calibracion="";
    private String matricula;
    private String tipoComponente;
    private String tipoInspeccion;
    private String matTractora;
    private String matCisterna;
    private String codConductor;
    private int nuevaInspeccion;
    private String user;
    private String pass;
    private String json_url = "http://pruebaalumnosandroid.esy.es/inspecciones/registrar_inspeccion.php";
    private String json_url1= "http://pruebaalumnosandroid.esy.es/inspecciones/consulta_num_inspeccion.php";
    private String respuestaNube;
    //private String contadorInspecciones;
    private int contadorInspecciones;



    ////DETALLE INSPECCION FRAGMENT
    private RealmResults<DetalleInspeccionBD> detalleInspeccionBDS;
    private RealmResults<DetalleInspeccionBD> detalleInspeccionToast;
    private String inspeccionIntent;
    private Button guardar;
    private Button continuar;
    private Boolean purgas;
    private DetalleInspeccionBD inspecciones;
    private String instalacion;
    private Boolean comprobacion = false;

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        try{
            callback =(dataListener)context;
        }catch(Exception e){
            throw new ClassCastException(context.toString() + " should implement dataListener");

        }
    }

    public CabeceraInspeccionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_cabecera_inspeccion, container, false);

       // mListView = (ListView)view.findViewById(R.id.lv_detalleinspecciones);

        btn_siguiente = (Button)view.findViewById(R.id.btn_siguiente2);
        btn_compartimentos= (Button)view.findViewById(R.id.btn_compartimentos);
        etIa = (EditText)view.findViewById(R.id.et_instalacion);
        etAlbaran = (EditText)view.findViewById(R.id.et_albaran);
        etTrans = (EditText)view.findViewById(R.id.et_transportistaresp);
        etTablaCal = (EditText)view.findViewById(R.id.et_empresatablacalibracion);

        btn_siguiente.setOnClickListener(this);
        btn_compartimentos.setOnClickListener(this);

        /////DETALLE INSPECCION FRAGMENT

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


        return view;
    }

    public void crearInspeccionBD(String tractora, String cisterna, String conductor, String tipoComponente, String tipoInspeccion, String user, String pass){
        //Toast.makeText(getActivity(), "inspeccion: " + inspeccion, Toast.LENGTH_SHORT).show();a;
        this.tipoComponente =tipoComponente.trim();
        this.tipoInspeccion=tipoInspeccion.trim();
        //inspeccion = user + nuevaInspeccion;
        this.user = user.trim();
        this.pass = pass.trim();
        matTractora= tractora.trim();
        matCisterna = cisterna.trim();
        this.codConductor = conductor.trim();
        buscarUltimaInspeccion(this.user, this.pass);
        renderText();
    }

    public void renderText(){


        //detalleInspeccionBDS.addChangeListener(this);

        bateriaDesconectada.setChecked(true);
        fichaSeguridad.setChecked(true);
        transponderTractora.setChecked(true);
        transponderCisterna.setChecked(true);
        frenoEstacionamiento.setChecked(true);
        apagallamas.setChecked(true);
        bajadaTagsPlanta.setChecked(true);
        adrCisterna.setChecked(true);
        adrConductor.setChecked(true);
        adrTractora.setChecked(true);
        mangueraGases.setChecked(true);
        tomaTierra.setChecked(true);
        movilDesconectado.setChecked(true);
        estanqueidadCajon.setChecked(true);
        estanqueidadCisterna.setChecked(true);
        estanqueidadEquiposTrasiego.setChecked(true);
        estanqueidadValvulasAPI.setChecked(true);
        estanqueidadValvulasFondo.setChecked(true);
        interruptorEmergencia.setChecked(true);
        itvCisterna.setChecked(true);
        itvTractora.setChecked(true);
        lecturaTagsIsleta.setChecked(true);
        tagsCorrectos.setChecked(true);
        permisoCirculacion.setChecked(true);
        posicionVehiculo.setChecked(true);
        purgaCompartimentos.setChecked(true);
        recogerAlbaran.setChecked(true);
        ropa.setChecked(true);
        superficieSupAntiDes.setChecked(true);
        tc2.setChecked(true);



    }

    public String obtenerCambios(){
        inspeccionBD = realm.where(DetalleInspeccionBD.class).equalTo("inspeccion", inspeccion).findFirst();
        //Toast.makeText(getActivity(), "inspeccion: " + inspeccionBD.get(0).getSuperficieSupAntideslizante(), Toast.LENGTH_SHORT).show();
        if (inspeccionBD.getInspeccion() != null) {
            returnInspeccion = inspeccionBD.getInspeccion();
        } else{
            returnInspeccion = "no encontrada";
        }
        return returnInspeccion;
    }

    @Override
    public void onChange(RealmResults<DetalleInspeccionBD> detalleInspeccionBD) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_compartimentos:
                if (comprobacion == false){
                    Toast.makeText(getActivity(), "Debe guardar la inspección primero", Toast.LENGTH_LONG).show();
                }else{
                    callback.continuar(inspeccion, matricula );
                }
                break;
            case R.id.btn_siguiente2:


                break;

                //DETALLE INSPECCION FRAGMENT
            case R.id.guardar_cambios:

                ia = etIa.getText().toString();
                albaran = etAlbaran.getText().toString();
                transportista = etTrans.getText().toString();
                tabla_calibracion = etTablaCal.getText().toString();

                if (ia.equals("") || albaran.equals("") || transportista.equals("")  || tabla_calibracion.equals("")){
                    Toast.makeText(getActivity(),  ia +  transportista + albaran + tabla_calibracion, Toast.LENGTH_SHORT).show();
                    break;
                } else{
                    //callback.obtenerInspeccion(inspeccion, ia, albaran, transportista, tabla_calibracion);
                    //callback.continuar(obtenerCambios(), matricula);
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
                    checklist.add(tagsCorrectos.isChecked());
                    checklist.add(permisoCirculacion.isChecked());
                    checklist.add(posicionVehiculo.isChecked());
                    checklist.add(purgaCompartimentos.isChecked());
                    checklist.add(recogerAlbaran.isChecked());
                    checklist.add(ropa.isChecked());
                    checklist.add(superficieSupAntiDes.isChecked());
                    checklist.add(tc2.isChecked());
                    guardar(checklist);
                    }
                    break;

                default:
                    break;


        }

    }

    public void guardar(List<Boolean> checklist){
        Toast.makeText(getActivity(), inspeccion, Toast.LENGTH_SHORT).show();
        realm.beginTransaction();
        DetalleInspeccionBD inspeccionBD = new DetalleInspeccionBD(inspeccion);
        switch (tipoComponente){
            case "S":
                inspeccionBD.setTractora(matTractora);
                inspeccionBD.setCisterna(matCisterna);
                this.matricula = matCisterna;
                break;
            case "T":
                inspeccionBD.setTractora(matTractora);
                inspeccionBD.setCisterna(matCisterna);
                this.matricula = matCisterna;
                break;
            case "R":
                inspeccionBD.setRigido(matTractora);
                this.matricula = matTractora;
                break;
            case "C":
                inspeccionBD.setTractora(matTractora);
                inspeccionBD.setCisterna(matCisterna);
                this.matricula = matCisterna;
                break;
        }
        inspeccionBD.setConductor(codConductor);
        inspeccionBD.setInstalacion(instalacion);
        inspeccionBD.setAlbaran(albaran);
        inspeccionBD.setTransportista(transportista);
        inspeccionBD.setTablaCalibracion(tabla_calibracion);
        inspeccionBD.setAccDesconectadorBaterias(checklist.get(0));
        inspeccionBD.setFichaSeguridad(checklist.get(1));
        inspeccionBD.setTransponderTractora(checklist.get(2));
        inspeccionBD.setTransponderCisterna(checklist.get(3));
        inspeccionBD.setAccFrenoEstacionamientoMarchaCorta(checklist.get(4));
        inspeccionBD.setApagallamas(checklist.get(5));
        inspeccionBD.setBajadaTagPlanta(checklist.get(6));
        inspeccionBD.setAdrCisterna(checklist.get(7));
        inspeccionBD.setAdrConductor(checklist.get(8));
        inspeccionBD.setAdrTractoraRigido(checklist.get(9));
        inspeccionBD.setConexionMangueraGases(checklist.get(10));
        inspeccionBD.setConexionTomaTierra(checklist.get(11));
        inspeccionBD.setDescTfnoMovil(checklist.get(12));
        inspeccionBD.setEstanqueidadCajon(checklist.get(13));
        inspeccionBD.setEstanqueidadCisterna(checklist.get(14));
        inspeccionBD.setEstanqueidadEquiposTrasiego(checklist.get(15));
        inspeccionBD.setEstanqueidadValvulasAPI(checklist.get(16));
        inspeccionBD.setEstanqueidadValvulasFondo(checklist.get(17));
        inspeccionBD.setInterrupEmergenciaYFuego(checklist.get(18));
        inspeccionBD.setItvCisterna(checklist.get(19));
        inspeccionBD.setItvTractoraRigido(checklist.get(20));
        inspeccionBD.setLecturaTagIsleta(checklist.get(21));
        inspeccionBD.setMontajeCorrectoTags(checklist.get(22));
        inspeccionBD.setPermisoConducir(checklist.get(23));
        inspeccionBD.setPosicionamientoAdecuadoEnIsleta(checklist.get(24));
        inspeccionBD.setPurgaCompartimentos(checklist.get(25));
        inspeccionBD.setRecogerAlbaran(checklist.get(26));
        inspeccionBD.setRopaSeguridad(checklist.get(27));
        inspeccionBD.setSuperficieSupAntideslizante(checklist.get(28));
        inspeccionBD.setTc2(checklist.get(29));
        realm.copyToRealmOrUpdate(inspeccionBD);
        realm.commitTransaction();
        comprobacion=true;
        registrarInspeccionNube(user, pass, inspeccion, matTractora);



    }

    private int buscarUltimaInspeccion(final String usuario, final String pass){

        StringRequest sr1 = new StringRequest(Request.Method.POST, json_url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    JSONArray json = jsonObject.optJSONArray("num_inspecciones");
                    contadorInspecciones = (json.optJSONObject(0).optInt("CONTADOR"));
                    inspeccion = usuario+String.valueOf(contadorInspecciones);
                    //Toast.makeText(getActivity(), String.valueOf(contadorInspecciones), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user", usuario);
                params.put("pass", pass);
                return params;
            }
        };
        VolleySingleton.getInstanciaVolley(getContext()).addToRequestqueue(sr1);

        return contadorInspecciones;
    }

    private void registrarInspeccionNube(final String user, final String pass, final String inspeccion, final String matTractora){
        /*
        DetalleInspeccionBD inspeccionBD;
        inspeccionBD = realm.where(DetalleInspeccionBD.class).equalTo("inspeccion", inspeccion).findFirst();

*/
        //Toast.makeText(getActivity(), user + "-" + pass + "-" + inspeccion + "-" + matTractora, Toast.LENGTH_SHORT).show();
        StringRequest  sr = new StringRequest(Request.Method.POST, json_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                respuestaNube = response;
                Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                respuestaNube = error.toString();
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user", user);
                params.put("pass", pass);
                params.put("inspeccion", inspeccion);
                params.put("matTractora", matTractora);
                return params;
            }
        };

        VolleySingleton.getInstanciaVolley(getContext()).addToRequestqueue(sr);

    }

    public interface dataListener{
        //void datosIntent(String tractora, String cisterna, String conductor, String t_rigido, String tipo_inspeccion);
        void obtenerInspeccion(String inspeccion, String Instalacion, String albaran, String transportista, String tabla_calibracion);
        void continuar(String inspeccion, String matricula);

    }

}
