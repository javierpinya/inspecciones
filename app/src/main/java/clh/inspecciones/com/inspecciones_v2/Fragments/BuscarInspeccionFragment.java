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
import clh.inspecciones.com.inspecciones_v2.Clases.DetalleInspeccionBD;
import clh.inspecciones.com.inspecciones_v2.R;
import clh.inspecciones.com.inspecciones_v2.SingleTones.VolleySingleton;
import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuscarInspeccionFragment extends Fragment {

    public BuscarInspeccionClass buscarInspeccionClass;
    String url = "http://pruebaalumnosandroid.esy.es/inspecciones/buscar_inspecciones_max10.php";
    String url2 = "http://pruebaalumnosandroid.esy.es/inspecciones/buscar_inspeccion.php";
    ArrayList<BuscarInspeccionClass> listaDatosInspeccion;
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

    private Realm realm;

    public BuscarInspeccionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_buscar_inspeccion, container, false);

        user = getArguments().getString("user", "no_user");
        pass = getArguments().getString("pass", "no_pass");

        buscar = view.findViewById(R.id.BuscarInspeccion);
        tractora = view.findViewById(R.id.etTractoraBuscarInspeccion);
        cisterna = view.findViewById(R.id.etCisternaBuscarInspeccion);

        tractora.setText("");
        cisterna.setText("");
        realm = Realm.getDefaultInstance();

        listaDatosInspeccion = new ArrayList<>();
        recyclerVehiculos = view.findViewById(R.id.rv_buscarInspecciones);
        recyclerVehiculos.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerVehiculos.setHasFixedSize(true);

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
                buscar(mTractora, mCisterna, user, pass);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void buscarUltimasInspeccionesMax10(final String user, final String pass) {

        StringRequest srUltimas = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    //listaVehiculos.clear();
                    //Convierto la respuesta, de tipo String, a un JSONObject.
                    JSONObject jsonObject = new JSONObject(response);

                    //Cramos un JSONArray del objeto JSON "vehiculo"
                    JSONArray jsonVehiculo = jsonObject.optJSONArray("inspecciones");
                    //Del objeto JSON "vehiculo" capturamos el primer grupo de valores

                    for (int i = 0; i < jsonVehiculo.length(); i++) {
                        buscarInspeccionClass = new BuscarInspeccionClass();
                        JSONObject jsonObject1 = null;
                        jsonObject1 = jsonVehiculo.getJSONObject(i);
                        buscarInspeccionClass.setTractora(jsonObject1.optString("MATRICULA1"));
                        buscarInspeccionClass.setCisterna(jsonObject1.optString("MATRICULA2"));
                        buscarInspeccionClass.setInstalacion(jsonObject1.optString("INSTALACION"));

                        fecha = jsonObject1.optString("FECHA");
                        date = parseador.parse(fecha);
                        buscarInspeccionClass.setFecha(df.format(date));

                        listaDatosInspeccion.add(buscarInspeccionClass);
                    }

                    renderText(listaDatosInspeccion);

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }


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

    private void buscar(final String tractora, final String cisterna, final String user, final String pass) {

        StringRequest sr = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    //listaVehiculos.clear();
                    //Convierto la respuesta, de tipo String, a un JSONObject.
                    JSONObject jsonObject = new JSONObject(response);

                    //Cramos un JSONArray del objeto JSON "vehiculo"
                    JSONArray jsonVehiculo = jsonObject.optJSONArray("vehiculo");
                    JSONArray jsonCisterna = jsonObject.optJSONArray("cisterna");
                    //Del objeto JSON "vehiculo" capturamos el primer grupo de valores


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //guardarLocal(inspeccion, checklist);
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
                String tractora;
                String cisterna;
                String fecha;
                String instalacion;

                tractora = buscarInspeccionClass.getTractora();
                cisterna = buscarInspeccionClass.getCisterna();
                fecha = buscarInspeccionClass.getFecha();
                instalacion = buscarInspeccionClass.getInstalacion();

                //callback.
            }
        });
        recyclerVehiculos.setAdapter(adapter);
    }

    public void guardarLocal(String inspeccion, List<String> checkListString, String instalacion, String transportista, String fechaInspeccion, String conjunto, String tractora, String rigido, String cisterna, String fechaTablaCalibracion, String conductor, String albaran, String empresaTablaCalibracion, String observaciones ){
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
            inspeccionBD.setFechaInspeccion(parseador.parse(fechaInspeccion));
            inspeccionBD.setFechaTablaCalCisterna(parseador.parse(fechaTablaCalibracion));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        inspeccionBD.setConjunto(conjunto);
        inspeccionBD.setTractora(tractora);
        inspeccionBD.setRigido(rigido);
        inspeccionBD.setCisterna(cisterna);
        inspeccionBD.setAlbaran(albaran);
        inspeccionBD.setTablaCalibracion(empresaTablaCalibracion);
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

}
