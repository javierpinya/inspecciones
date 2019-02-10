package clh.inspecciones.com.inspecciones_v2.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import clh.inspecciones.com.inspecciones_v2.Adapters.BuscarInspeccionAdapter;
import clh.inspecciones.com.inspecciones_v2.Clases.BuscarInspeccionClass;
import clh.inspecciones.com.inspecciones_v2.Clases.CACompartimentosBD;
import clh.inspecciones.com.inspecciones_v2.R;
import clh.inspecciones.com.inspecciones_v2.SingleTones.VolleySingleton;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuscarInspeccionFragment extends Fragment {

    private  List<String> checklistNames = new ArrayList<>();
    private List<Boolean> checklistBoolean = new ArrayList<>();

    public dataListener callback;
    public RealmResults<BuscarInspeccionClass> rBuscarInspeccionClass;

    String url = "http://pruebaalumnosandroid.esy.es/inspecciones/buscar_inspecciones_max10.php";
    String url2 = "http://pruebaalumnosandroid.esy.es/inspecciones/buscar_inspeccion.php";
    public BuscarInspeccionClass buscarInspeccionClass;
    ArrayList<BuscarInspeccionClass> listaDatosInspeccion;
    public CACompartimentosBD caCompartimentosBD;
    ArrayList<CACompartimentosBD> listaCompartimentos;
    RecyclerView recyclerVehiculos;
    private Button buscar;
    private EditText tractora;
    private EditText cisterna;
    private String user;
    private String pass;
    private BuscarInspeccionAdapter adapter;
    Date date = new Date();
    private String mTractora = "";
    private String mCisterna = "";
    private SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    private String fecha = "";

    //Datos de la inspección
    private String inspeccion;
    private String fechaInicioInspeccion;

    private Realm realm;
    private String instalacion;
    private String albaran;
    private String conductor;
    private String transportista;
    private String conjunto;
    private String empresaTablaCalibracion;
    private String tipoComponente;
    private Boolean baterias=false;



    private String matriculas;
    private List<String> rutaFoto= new ArrayList<>();


    private BuscarInspeccionClass inspeccionBD;
    private Date fechaFinInspeccion;
    private String tag;
    private Integer capacidad=-1;
    private Integer cargada;
    private Boolean cumple;

    public BuscarInspeccionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            callback = (dataListener)context;
        }catch (Exception e){
            throw new ClassCastException(context.toString() + " should implement dataListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_buscar_inspeccion, container, false);

        checklistNames.add("BATERIAS");
        checklistNames.add("FICHASEGURIDAD");
        checklistNames.add("TRANSPONDERTRAC");
        checklistNames.add("TRANSPONDERCIST");
        checklistNames.add("FRENOESTACIONAMIENTO");
        checklistNames.add("APAGALLAMAS");
        checklistNames.add("BAJADATAGS");
        checklistNames.add("ADRCISTERNAS");
        checklistNames.add("ADRCONDUCTOR");
        checklistNames.add("ADRTRACTORA");
        checklistNames.add("MANGUERAGASES");
        checklistNames.add("TOMATIERRA");
        checklistNames.add("TFNOMOVIL");
        checklistNames.add("ESTCAJON");
        checklistNames.add("ESTCISTERNA");
        checklistNames.add("ESTEQUIPOS");
        checklistNames.add("ESTVALVULASAPI");
        checklistNames.add("ESTVALVULASFONDO");
        checklistNames.add("INTERRUPEMERGENCIA");
        checklistNames.add("ITVCISTERNA");
        checklistNames.add("ITVTRACTORA");
        checklistNames.add("LECTURATAGS");
        checklistNames.add("MONTAJETAGS");
        checklistNames.add("PERMISOCONDUCIR");
        checklistNames.add("POSICIONISLETA");
        checklistNames.add("PURGA");
        checklistNames.add("RECOGEALBARAN");
        checklistNames.add("ROPASEGURIDAD");
        checklistNames.add("SUPERFANTIDESLIZ");
        checklistNames.add("TC2");
        checklistNames.add("INSPECCIONADA");
        checklistNames.add("FAVORABLE");
        checklistNames.add("FECHA_FIN_INSPECCION");
        checklistNames.add("REVISADA");
        checklistNames.add("BLOQUEADA");
        checklistNames.add("OBSERVACIONES");
        /*
        checklistNames.add("C1_CODTAG");
        checklistNames.add("C1_CAPACIDAD");
        checklistNames.add("C1_CANTIDAD");
        checklistNames.add("C1_DIFERENCIA");
        checklistNames.add("C1_CUMPLE");
        checklistNames.add("C2_CODTAG");
        checklistNames.add("C2_CAPACIDAD");
        checklistNames.add("C2_CANTIDAD");
        checklistNames.add("C2_DIFERENCIA");
        checklistNames.add("C2_CUMPLE");
        checklistNames.add("C3_CODTAG");
        checklistNames.add("C3_CAPACIDAD");
        checklistNames.add("C3_CANTIDAD");
        checklistNames.add("C3_DIFERENCIA");
        checklistNames.add("C3_CUMPLE");
        checklistNames.add("C4_CODTAG");
        checklistNames.add("C4_CAPACIDAD");
        checklistNames.add("C4_CANTIDAD");
        checklistNames.add("C4_DIFERENCIA");
        checklistNames.add("C4_CUMPLE");
        checklistNames.add("C5_CODTAG");
        checklistNames.add("C5_CAPACIDAD");
        checklistNames.add("C5_CANTIDAD");
        checklistNames.add("C5_DIFERENCIA");
        checklistNames.add("C5_CUMPLE");
        checklistNames.add("C6_CODTAG");
        checklistNames.add("C6_CAPACIDAD");
        checklistNames.add("C6_CANTIDAD");
        checklistNames.add("C6_DIFERENCIA");
        checklistNames.add("C6_CUMPLE");
        checklistNames.add("C7_CODTAG");
        checklistNames.add("C7_CAPACIDAD");
        checklistNames.add("C7_CANTIDAD");
        checklistNames.add("C7_DIFERENCIA");
        checklistNames.add("C7_CUMPLE");
        checklistNames.add("C8_CODTAG");
        checklistNames.add("C8_CAPACIDAD");
        checklistNames.add("C8_CANTIDAD");
        checklistNames.add("C8_DIFERENCIA");
        checklistNames.add("C8_CUMPLE");
        */

        user = getArguments().getString("user", "no_user");
        pass = getArguments().getString("pass", "no_pass");

        buscar = view.findViewById(R.id.BuscarInspeccion);
        tractora = view.findViewById(R.id.etTractoraBuscarInspeccion);
        cisterna = view.findViewById(R.id.etCisternaBuscarInspeccion);

        tractora.setText("");
        cisterna.setText("");
        realm = Realm.getDefaultInstance();

        listaDatosInspeccion = new ArrayList<>();
        listaCompartimentos = new ArrayList<>();
        recyclerVehiculos = view.findViewById(R.id.rv_buscarInspecciones);
        recyclerVehiculos.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerVehiculos.setHasFixedSize(true);
        Toast.makeText(getActivity(), checklistNames.get(0), Toast.LENGTH_SHORT).show();

        buscarUltimasInspeccionesMax10(user, pass);

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tractora.getText().toString().equals("")) {
                    mTractora = "";
                } else {
                    mTractora = tractora.getText().toString();
                }
                if (cisterna.getText().toString().equals("")) {
                    mCisterna = "";
                } else {
                    mCisterna = cisterna.getText().toString();
                }

                tractora.clearFocus();
                cisterna.clearFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(tractora.getWindowToken(), 0);
                listaDatosInspeccion.clear();
                buscar(mTractora, mCisterna, user, pass, inspeccion);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void buscarUltimasInspeccionesMax10(final String user, final String pass) {

        StringRequest srUltimas = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                crearJson(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "error: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user", user);
                params.put("pass", pass);
                return params;
            }
        };
        VolleySingleton.getInstanciaVolley(getContext()).addToRequestqueue(srUltimas);

    }

    private void buscar(final String tractora, final String cisterna, final String user, final String pass, final String inspeccion) {

        StringRequest sr = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                crearJson(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "error: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user", user);
                params.put("pass", pass);
                params.put("rigido", tractora);
                params.put("cisterna", cisterna);
                return params;
            }
        };
        VolleySingleton.getInstanciaVolley(getContext()).addToRequestqueue(sr);
    }

    public void renderText(ArrayList<BuscarInspeccionClass> listaVehiculos) {

        adapter = new BuscarInspeccionAdapter(listaVehiculos, new BuscarInspeccionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BuscarInspeccionClass buscarInspeccionClass, int position) {

                String inspeccion = buscarInspeccionClass.getInspeccion();
                callback.verInspeccion(inspeccion);
            }
        });
        recyclerVehiculos.setAdapter(adapter);
    }



    public void crearJson(String response){
        try {

            //listaVehiculos.clear();
            //Convierto la respuesta, de tipo String, a un JSONObject.
            JSONObject jsonObject = new JSONObject(response);

            //Cramos un JSONArray del objeto JSON "vehiculo"
            JSONArray jsonVehiculo = jsonObject.optJSONArray("inspecciones");
            //Del objeto JSON "vehiculo" capturamos el primer grupo de valores 5145688

            Toast.makeText(getActivity(), "jsonLongitud: " + jsonVehiculo.length(), Toast.LENGTH_SHORT).show();
            for (int i = 0; i < jsonVehiculo.length(); i++) {

                JSONObject jsonObject1 = null;
                jsonObject1 = jsonVehiculo.getJSONObject(i);

                inspeccion = jsonObject1.optString("NUM_INSPECCION").trim();
                realm.beginTransaction();
                buscarInspeccionClass = new BuscarInspeccionClass(inspeccion);
                buscarInspeccionClass.setTractora(jsonObject1.optString("MATRICULA1"));
                buscarInspeccionClass.setCisterna(jsonObject1.optString("MATRICULA2"));
                buscarInspeccionClass.setInstalacion(jsonObject1.optString("INSTALACION"));
                fecha = jsonObject1.optString("FECHA_INICIO_INSPECCION");
                date = parseador.parse(fecha);
                buscarInspeccionClass.setFechaInicioInspeccion(date);
                buscarInspeccionClass.setAlbaran(jsonObject1.optString("ALBARAN"));
                buscarInspeccionClass.setConductor(jsonObject1.optString("CONDUCTOR"));
                buscarInspeccionClass.setTransportista(jsonObject1.optString("ID_TRANSPORTISTA"));
                buscarInspeccionClass.setConjunto(jsonObject1.optString("CONJUNTO"));
                buscarInspeccionClass.setEmpresaTablaCalibracion(jsonObject1.optString("TABLA_CALIBRACION"));
                buscarInspeccionClass.setTipoComponente(jsonObject1.optString("TIPO_COMPONENTE"));
                buscarInspeccionClass.setInspeccionada(Boolean.valueOf(jsonObject1.optString("INSPECCIONADA")));
                buscarInspeccionClass.setObservaciones(jsonObject1.optString("OBSERVACIONES"));

                buscarInspeccionClass.setAccDesconectadorBaterias(1 == jsonObject1.optInt("BATERIAS"));
                buscarInspeccionClass.setFichaSeguridad(1 == jsonObject1.optInt("FICHASEGURIDAD"));
                buscarInspeccionClass.setTransponderTractora(1 == jsonObject1.optInt("TRANSPONDERTRAC"));
                buscarInspeccionClass.setTransponderCisterna(1 == jsonObject1.optInt("TRANSPONDERCIST"));
                buscarInspeccionClass.setAccFrenoEstacionamientoMarchaCorta(1 == jsonObject1.optInt("FRENOESTACIONAMIENTO"));
                buscarInspeccionClass.setApagallamas(1 == jsonObject1.optInt("APAGALLAMAS"));
                buscarInspeccionClass.setBajadaTagPlanta(1 == jsonObject1.optInt("BAJADATAGS"));
                buscarInspeccionClass.setAdrCisterna(1 == jsonObject1.optInt("ADRCISTERNAS"));
                buscarInspeccionClass.setAdrConductor(1 == jsonObject1.optInt("ADRCONDUCTOR"));
                buscarInspeccionClass.setAdrTractoraRigido(1 == jsonObject1.optInt("ADRTRACTORA"));
                buscarInspeccionClass.setConexionMangueraGases(1 == jsonObject1.optInt("MANGUERAGASES"));
                buscarInspeccionClass.setConexionTomaTierra(1 == jsonObject1.optInt("TOMATIERRA"));
                buscarInspeccionClass.setDescTfnoMovil(1 == jsonObject1.optInt("TFNOMOVIL"));
                buscarInspeccionClass.setEstanqueidadCajon(1 == jsonObject1.optInt("ESTCAJON"));
                buscarInspeccionClass.setEstanqueidadCisterna(1 == jsonObject1.optInt("ESTCISTERNA"));
                buscarInspeccionClass.setEstanqueidadEquiposTrasiego(1 == jsonObject1.optInt(checklistNames.get(15)));
                buscarInspeccionClass.setEstanqueidadValvulasAPI(1 == jsonObject1.optInt(checklistNames.get(16)));
                buscarInspeccionClass.setEstanqueidadValvulasFondo(1 == jsonObject1.optInt(checklistNames.get(17)));
                buscarInspeccionClass.setInterrupEmergenciaYFuego(1 == jsonObject1.optInt(checklistNames.get(18)));
                buscarInspeccionClass.setItvCisterna(1 == jsonObject1.optInt(checklistNames.get(19)));
                buscarInspeccionClass.setItvTractoraRigido(1 == jsonObject1.optInt(checklistNames.get(20)));
                buscarInspeccionClass.setLecturaTagIsleta(1 == jsonObject1.optInt(checklistNames.get(21)));
                buscarInspeccionClass.setMontajeCorrectoTags(1 == jsonObject1.optInt(checklistNames.get(22)));
                buscarInspeccionClass.setPermisoConducir(1 == jsonObject1.optInt(checklistNames.get(23)));
                buscarInspeccionClass.setPosicionamientoAdecuadoEnIsleta(1 == jsonObject1.optInt(checklistNames.get(24)));
                buscarInspeccionClass.setPurgaCompartimentos(1 == jsonObject1.optInt(checklistNames.get(25)));
                buscarInspeccionClass.setRecogerAlbaran(1 == jsonObject1.optInt(checklistNames.get(26)));
                buscarInspeccionClass.setRopaSeguridad(1 == jsonObject1.optInt(checklistNames.get(27)));
                buscarInspeccionClass.setSuperficieSupAntideslizante(1 == jsonObject1.optInt(checklistNames.get(28)));
                buscarInspeccionClass.setTc2(1 == jsonObject1.optInt(checklistNames.get(29)));
                buscarInspeccionClass.setInspeccionada(1 == jsonObject1.optInt(checklistNames.get(30)));
                buscarInspeccionClass.setFavorable(1 == jsonObject1.optInt(checklistNames.get(31)));
                fecha = jsonObject1.optString("FECHA_FIN_INSPECCION");
                date = parseador.parse(fecha);
                buscarInspeccionClass.setFechaFinInspeccion(date);
                buscarInspeccionClass.setRevisada(1 == jsonObject1.optInt(checklistNames.get(33)));
                buscarInspeccionClass.setBloqueada(1 == jsonObject1.optInt(checklistNames.get(34)));
                realm.copyToRealmOrUpdate(buscarInspeccionClass);
                realm.commitTransaction();
                listaDatosInspeccion.add(buscarInspeccionClass);

                for (int j=1;j<9;j++){
                    jsonObject1 = jsonVehiculo.getJSONObject(j);
                    matriculas=jsonObject1.optString("MATRICULA1") + jsonObject1.optString("MATRICULA2");
                    try{
                        tag=jsonObject1.optString("C" + j + "_CODTAG");
                        capacidad=jsonObject1.optInt("C" + j + "_CAPACIDAD");
                        cargada=jsonObject1.optInt("C" + j+"_CANTIDAD");
                        cumple = (1 == jsonObject1.optInt("C" + j + "_CUMPLE"));
                    }catch(Exception e){
                        e.printStackTrace();
                    }

                    if(capacidad != -1) {
                        realm.beginTransaction();
                        caCompartimentosBD = new CACompartimentosBD(matriculas);
                        caCompartimentosBD.setInspeccion(inspeccion);
                        caCompartimentosBD.setCod_compartimento(j);
                        caCompartimentosBD.setCod_tag_cprt(tag);
                        caCompartimentosBD.setCan_capacidad(capacidad);
                        caCompartimentosBD.setCan_cargada(cargada);
                        caCompartimentosBD.setCumple(cumple);
                        realm.copyToRealmOrUpdate(caCompartimentosBD);
                        realm.commitTransaction();
                        listaCompartimentos.add(caCompartimentosBD);
                        capacidad = -1;
                    }
                }
            }

            renderText(listaDatosInspeccion);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException p){
            p.printStackTrace();
        }
    }

    public interface dataListener{
        void verInspeccion(String inspeccion);
    }

    /*

    public void guardarLocal(String inspeccion, List<String> checkListString, String instalacion, String transportista, String fechaInicioInspeccion, String fechaFinInspeccion, String conjunto, String tractora, String rigido, String cisterna, String fechaTablaCalibracion, String conductor, String albaran, String empresaTablaCalibracion, String observaciones ){
        realm.beginTransaction();
        DetalleInspeccionBD inspeccionBD = new DetalleInspeccionBD(inspeccion);
        List<Boolean> checklist;
        checklist = new ArrayList<>();
        for (int i=0;i<checkListString.size();i++){
            checklist.add(Boolean.getBoolean(checkListString.get(i)));
        }

        inspeccionBD.setInstalacion(instalacion);
        inspeccionBD.setTransportista(transportista);

        try {
            inspeccionBD.setFechaInicioInspeccion(parseador.parse(fechaInicioInspeccion));
            inspeccionBD.setFechaTablaCalCisterna(parseador.parse(fechaTablaCalibracion));
            inspeccionBD.setFechaFinInspeccion(parseador.parse(fechaFinInspeccion));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        inspeccionBD.setConjunto(conjunto);
        inspeccionBD.setTractora(tractora);
        inspeccionBD.setRigido(rigido);
        inspeccionBD.setCisterna(cisterna);
        inspeccionBD.setAlbaran(albaran);
        inspeccionBD.setFechaTablaCalibracion(empresaTablaCalibracion);
        inspeccionBD.setObservaciones(observaciones);
        inspeccionBD.setConductor(conductor);
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
    }

    */

}
