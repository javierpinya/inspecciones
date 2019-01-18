package clh.inspecciones.com.inspecciones_v2.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import clh.inspecciones.com.inspecciones_v2.Adapters.IdentificacionVehiculoAdapter;
import clh.inspecciones.com.inspecciones_v2.Clases.IdentificacionVehiculoClass;
import clh.inspecciones.com.inspecciones_v2.R;
import clh.inspecciones.com.inspecciones_v2.SingleTones.VolleySingleton;

/**
 * A simple {@link Fragment} subclass.
 */
public class IdentificacionVehiculoFragment extends Fragment {


    private SharedPreferences prefs;
    private EditText tractora;
    private EditText cisterna;
    private Button buscar;
    private DataListener callback;
    private String tipoVehiculo;
    private String tipoInspeccion;
    private String tipoComponente;
    String url = "http://pruebaalumnosandroid.esy.es/inspecciones/elegir_vehiculo.php";
    private String user;
    private String pass;
    public IdentificacionVehiculoClass identificacionVehiculoClass;
    ArrayList<IdentificacionVehiculoClass> listaVehiculos;

    RecyclerView recyclerVehiculos;
    private IdentificacionVehiculoAdapter adapter;

    public IdentificacionVehiculoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            callback = (IdentificacionVehiculoFragment.DataListener)context;
        }catch(Exception e){
            throw new ClassCastException(context.toString() + " should implement EnviarData");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_identificacion_vehiculo, container, false);

        //Bundle args = getArguments();
        tipoVehiculo = getArguments().getString("tipoVehiculo", "sin_datos");
        tipoInspeccion = getArguments().getString("tipoInspeccion", "sin_datos_inspeccion");
        tipoComponente = getArguments().getString("tipoComponente", "sin_datos_componente");
        user = getArguments().getString("user", "no_user");
        pass = getArguments().getString("pass", "no_pass");
        listaVehiculos = new ArrayList<>();
        tractora = view.findViewById(R.id.et_tractora);
        cisterna = view.findViewById(R.id.et_cisterna);
        buscar = view.findViewById(R.id.Buscar);
        recyclerVehiculos = view.findViewById(R.id.rv_identificacionvehiculo);
        recyclerVehiculos.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerVehiculos.setHasFixedSize(true);

        tractora.setHint("3660");

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tractora.clearFocus();
                cisterna.clearFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(tractora.getWindowToken(), 0);
                listaVehiculos.clear();
                buscar(tractora.getText().toString(), cisterna.getText().toString(), tipoVehiculo, tipoComponente, user, pass);
            }
        });



        return view;
    }

    public void buscar(final String rigido, final String cisterna, final String tipoVehiculo, final String tipoComponente, final String user, final String pass){

        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
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


                    switch (tipoVehiculo){
                        case "0": //tractora/rigido
                            for (int i=0;i<jsonVehiculo.length();i++)
                            {
                                identificacionVehiculoClass = new IdentificacionVehiculoClass();
                                JSONObject jsonObject1 = null;
                                jsonObject1=jsonVehiculo.getJSONObject(i);
                                identificacionVehiculoClass.setTractora(jsonObject1.optString("COD_MATRICULA1"));
                                identificacionVehiculoClass.setCisterna("-");//jsonObject1.optString("cisterna"));
                                listaVehiculos.add(identificacionVehiculoClass);
                            }
                            break;
                        case "1":   //conjunto
                            for (int i=0;i<jsonVehiculo.length();i++)
                            {
                                identificacionVehiculoClass = new IdentificacionVehiculoClass();
                                JSONObject jsonObject1;
                                jsonObject1=jsonVehiculo.getJSONObject(i);
                                identificacionVehiculoClass.setTractora(jsonObject1.optString("COD_MATRICULA1"));
                                identificacionVehiculoClass.setCisterna(jsonObject1.optString("COD_MATRICULA2"));
                                listaVehiculos.add(identificacionVehiculoClass);
                            }
                            break;
                        case "2":   //cisterna
                            for (int i=0;i<jsonCisterna.length();i++)
                            {
                                identificacionVehiculoClass = new IdentificacionVehiculoClass();
                                JSONObject jsonObject2 = null;
                                jsonObject2=jsonCisterna.getJSONObject(i);
                                identificacionVehiculoClass.setTractora("-");
                                identificacionVehiculoClass.setCisterna(jsonObject2.optString("COD_MATRICULA2"));//jsonObject1.optString("cisterna"));
                                listaVehiculos.add(identificacionVehiculoClass);
                            }

                            break;

                        default:
                            break;
                    }
                    renderText(listaVehiculos);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "error: " +error.toString(),Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("user", user);
                params.put("pass", pass);
                params.put("tipo_consulta", tipoVehiculo);
                params.put("tipoComponente", tipoComponente);
                params.put("rigido", rigido);
                params.put("cisterna", cisterna);
                return params;
            }

        };
        VolleySingleton.getInstanciaVolley(getContext()).addToRequestqueue(sr);
    }

    public void renderText(ArrayList<IdentificacionVehiculoClass> listaVehiculos){

        adapter = new IdentificacionVehiculoAdapter(listaVehiculos, new IdentificacionVehiculoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(IdentificacionVehiculoClass identificacionVehiculoClass, int position) {
                String tractora;
                String cisterna;
                String conductor;
                tractora = identificacionVehiculoClass.getTractora();
                cisterna = identificacionVehiculoClass.getCisterna();
                callback.enviarVehiculoIdentificado(tractora, cisterna);
            }
        });
        recyclerVehiculos.setAdapter(adapter);
    }


    public interface DataListener{
        void enviarVehiculoIdentificado(String tractora, String cisterna);
    }
}
