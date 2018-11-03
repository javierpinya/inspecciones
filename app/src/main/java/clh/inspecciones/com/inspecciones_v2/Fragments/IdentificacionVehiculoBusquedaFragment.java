package clh.inspecciones.com.inspecciones_v2.Fragments;



import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import clh.inspecciones.com.inspecciones_v2.Clases.IdentificacionVehiculoClass;
import clh.inspecciones.com.inspecciones_v2.R;
import clh.inspecciones.com.inspecciones_v2.SingleTones.VolleySingleton;

/**
 * A simple {@link Fragment} subclass.
 */
public class IdentificacionVehiculoBusquedaFragment extends Fragment {

    private EditText tractora;
    private EditText cisterna;
    private EditText conductor;
    private Button buscar;
    private DataListener callback;
    private String tipo_vehiculo;
    private String tipo_inspeccion;
    private String t_rigido;
    String url = "http://pruebaalumnosandroid.esy.es/inspecciones/elegir_vehiculo.php";
    String username = "admin"; //cambiar en un futuro, que provenga del login
    String pass = "admin";

    public IdentificacionVehiculoClass identificacionVehiculoClass;

    ArrayList<IdentificacionVehiculoClass> listaVehiculos;

    public IdentificacionVehiculoBusquedaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            callback = (DataListener)context;
        }catch(Exception e){
            throw new ClassCastException(context.toString() + " should implement DataListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_identificacion_vehiculo_busqueda, container, false);


        listaVehiculos = new ArrayList<>();
        tractora = (EditText)view.findViewById(R.id.et_tractora);
        cisterna = (EditText)view.findViewById(R.id.et_cisterna);
        conductor = (EditText)view.findViewById(R.id.et_codcond);
        buscar = (Button)view.findViewById(R.id.Buscar);

        tractora.setHint("E3654FKD");

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaVehiculos.clear();
                buscar(tractora.getText().toString(), cisterna.getText().toString(),conductor.getText().toString());
            }
        });

        return view;
    }

    public void buscar(final String rigido, final String cisterna, final String conductor){

        RequestQueue requestQueue;

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


                    switch (tipo_vehiculo){
                        case "0": //tractora/rigido
                            for (int i=0;i<jsonVehiculo.length();i++)
                            {
                                identificacionVehiculoClass = new IdentificacionVehiculoClass();
                                JSONObject jsonObject1 = null;
                                jsonObject1=jsonVehiculo.getJSONObject(i);
                                identificacionVehiculoClass.setConductor("-"); //jsonObject1.optString("conductor"));
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
                                identificacionVehiculoClass.setConductor("-");
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
                                identificacionVehiculoClass.setConductor("-"); //jsonObject1.optString("conductor"));
                                identificacionVehiculoClass.setTractora("-");
                                identificacionVehiculoClass.setCisterna(jsonObject2.optString("COD_MATRICULA2"));//jsonObject1.optString("cisterna"));
                                listaVehiculos.add(identificacionVehiculoClass);
                            }

                            break;

                        default:
                            break;

                    }


                    callback.buscarVehiculos(listaVehiculos);



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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", pass);
                params.put("tipo_consulta", tipo_vehiculo);
                params.put("t_rigido", t_rigido);
                params.put("conductor", conductor);
                params.put("rigido", rigido);
                params.put("cisterna", cisterna);
                return params;
            }

        };
        VolleySingleton.getInstanciaVolley(getContext()).addToRequestqueue(sr);
    }

    public interface DataListener{
        void buscarVehiculos(ArrayList<IdentificacionVehiculoClass> listaVehiculos);
        void enviarDatos(String tipo_vehiculo,String tipo_inspeccion, String t_rigido);
    }

    public void recibir_intent(String tipo_vehiculo, String tipo_inspeccion, String t_rigido){
        this.tipo_inspeccion = tipo_inspeccion;
        this.tipo_vehiculo = tipo_vehiculo;
        this.t_rigido = t_rigido;
    }

}

