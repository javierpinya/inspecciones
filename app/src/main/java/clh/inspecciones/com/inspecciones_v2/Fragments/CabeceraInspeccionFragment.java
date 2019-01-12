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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
public class CabeceraInspeccionFragment extends Fragment implements RealmChangeListener<RealmResults<DetalleInspeccionBD>>, View.OnClickListener{

    public dataListener callback;
    private Button button;
    private Realm realm;
    private RealmResults<DetalleInspeccionBD> inspeccionBDs;
    private DetalleInspeccionBD inspeccionBD;
    private String inspeccion=null;
    private String returnInspeccion;
    private Button btn_siguiente;
    private Button btn_compartimentos;
    private EditText etIa;
    private EditText etConductor;
    private EditText etAlbaran;
    private EditText etTrans;
    private EditText etTablaCal;
    private String ia;
    private String cond;
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
    private int contadorInspecciones;



    ////DETALLE INSPECCION FRAGMENT
    private RealmResults<DetalleInspeccionBD> detalleInspeccionBDS;
    private RealmResults<DetalleInspeccionBD> detalleInspeccionToast;
    private String inspeccionIntent;
    private Button continuar;
    private Boolean purgas;
    private DetalleInspeccionBD inspecciones;
    private Boolean comprobacion = false;

    private List<Boolean> checklist;

    private TextView tractora;
    private TextView cisterna;
    private TextView conductor;
    private TextView tvInspeccion;
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
    private SimpleDateFormat parseador2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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

        etIa = (EditText)view.findViewById(R.id.et_instalacion);
        etConductor = (EditText)view.findViewById(R.id.et_codcond);
        etAlbaran = (EditText)view.findViewById(R.id.et_albaran);
        etTrans = (EditText)view.findViewById(R.id.et_transportistaresp);
        etTablaCal = (EditText)view.findViewById(R.id.et_empresatablacalibracion);
        button = (Button)view.findViewById(R.id.btn_guardar);
        tvInspeccion = (TextView)view.findViewById(R.id.tv_inspeccion1);


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

        realm = Realm.getDefaultInstance();

        tipoInspeccion = getArguments().getString("tipoInspeccion", "sin_datos_inspeccion");
        tipoComponente = getArguments().getString("tipoComponente", "sin_datos_componente");
        user = getArguments().getString("user", "no_user");
        pass = getArguments().getString("pass", "no_pass");
        matTractora = getArguments().getString("tractora", "sin_tractora");
        matCisterna = getArguments().getString("cisterna", "sin_cisterna");

        button.setOnClickListener(this);

        buscarUltimaInspeccion(user, pass);
        renderText();

        return view;
    }

    public void renderText(){

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

    public void prepararGuardado(){
        ia = etIa.getText().toString();
        cond = etConductor.getText().toString();
        albaran = etAlbaran.getText().toString();
        transportista = etTrans.getText().toString();
        tabla_calibracion = etTablaCal.getText().toString();

        if (ia.equals("") || albaran.equals("") || transportista.equals("")  || tabla_calibracion.equals("") || cond.equals("")){
            Toast.makeText(getActivity(),  "Hay que rellenar todos los campos para poder guardar", Toast.LENGTH_SHORT).show();
        } else{
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
    }

    public void guardar(List<Boolean> checklist){
        List<String> checkListString;
        Date today = Calendar.getInstance().getTime();
        String fechaInspeccion;
        fechaInspeccion = parseador2.format(today);
        checkListString = new ArrayList<>();
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
        inspeccionBD.setConductor(cond);
        inspeccionBD.setInstalacion(ia);
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
        for (int i=0;i<checklist.size();i++){
            checkListString.add(checklist.get(i).toString());
        }

        registrarInspeccionNube(user, pass, fechaInspeccion, inspeccion, ia, matTractora, matCisterna, cond, tipoComponente, transportista, albaran, tabla_calibracion, checkListString);
        callback.guardado(comprobacion, matricula, inspeccion);

    }

    private void buscarUltimaInspeccion(final String usuario, final String pass){

        StringRequest sr1 = new StringRequest(Request.Method.POST, json_url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    JSONArray json = jsonObject.optJSONArray("num_inspecciones");
                    contadorInspecciones = (json.optJSONObject(0).optInt("CONTADOR"));
                    inspeccion = usuario+String.valueOf(contadorInspecciones);
                    tvInspeccion.setText(inspeccion);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "ObtenerInspeccion: " + error.toString(), Toast.LENGTH_SHORT).show();

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
    }

    private void registrarInspeccionNube(final String user, final String pass, final String fechaInspeccion, final String inspeccion, final String instalacion, final String matTractora, final String matCisterna,
                                         final String conductor, final String tipoComponente, final String transportista, final String albaran, final String tabla_calibracion,  final List<String> checklist){

        StringRequest  sr = new StringRequest(Request.Method.POST, json_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                respuestaNube = response;
                Toast.makeText(getActivity(), response.trim(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                respuestaNube = error.toString();
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user", user);
                params.put("pass", pass);
                params.put("inspeccion", inspeccion);
                params.put("instalacion", instalacion);
                params.put("matTractora", matTractora);
                params.put("matCisterna", matCisterna);
                params.put("conductor", conductor);
                params.put("tipoComponente", tipoComponente);
                params.put("fecha",fechaInspeccion);
                params.put("transportista", transportista);
                params.put("tabla_calibracion", tabla_calibracion);
                params.put("albaran", albaran);
                params.put("bateriaDesconectada", checklist.get(0));
                params.put("fichaSeguridad", checklist.get(1));
                params.put("transponderTractora", checklist.get(2));
                params.put("transponderCisterna", checklist.get(3));
                params.put("frenoEstacionamiento", checklist.get(4));
                params.put("apagallamas", checklist.get(5));
                params.put("bajadaTagPlanta", checklist.get(6));
                params.put("adrCisterna", checklist.get(7));
                params.put("adrConductor", checklist.get(8));
                params.put("adrTractora", checklist.get(9));
                params.put("mangueraGases", checklist.get(10));
                params.put("tomaTierra", checklist.get(11));
                params.put("tfnoMovil", checklist.get(12));
                params.put("estCajon", checklist.get(13));
                params.put("estCisterna", checklist.get(14));
                params.put("estEquiposTrasiegos", checklist.get(15));
                params.put("estValvulasApi", checklist.get(16));
                params.put("estValvulasFondo", checklist.get(17));
                params.put("interruptorEmergencia", checklist.get(18));
                params.put("itvCisterna", checklist.get(19));
                params.put("itvTractora", checklist.get(20));
                params.put("lecturaTagsIsleta", checklist.get(21));
                params.put("montajeTagsCorrecto", checklist.get(22));
                params.put("permisoConducir", checklist.get(23));
                params.put("posicionIsleta", checklist.get(24));
                params.put("purga", checklist.get(25));
                params.put("recogerAlbaran", checklist.get(26));
                params.put("ropaSeguridad", checklist.get(27));
                params.put("superficieAntideslizante", checklist.get(28));
                params.put("tc2", checklist.get(29));
                return params;
            }
        };

        VolleySingleton.getInstanciaVolley(getContext()).addToRequestqueue(sr);
    }

    @Override
    public void onClick(View v) {
        prepararGuardado();

    }

    public interface dataListener{
        void guardado(Boolean guardadoOK, String matricula, String inspeccion);
    }

}
