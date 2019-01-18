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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import clh.inspecciones.com.inspecciones_v2.Adapters.BuscarInspeccionAdapter;
import clh.inspecciones.com.inspecciones_v2.Clases.BuscarInspeccionClass;
import clh.inspecciones.com.inspecciones_v2.R;
import clh.inspecciones.com.inspecciones_v2.SingleTones.VolleySingleton;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuscarInspeccionFragment extends Fragment {

    public BuscarInspeccionClass buscarInspeccionClass;
    String url = "http://pruebaalumnosandroid.esy.es/inspecciones/buscar_inspeccion.php";
    String url2 = "http://pruebaalumnosandroid.esy.es/inspecciones/buscar_inspeccion2.php";
    ArrayList<BuscarInspeccionClass> listaDatosInspeccion;
    RecyclerView recyclerVehiculos;
    private Button buscar;
    private EditText tractora;
    private EditText cisterna;
    private String user;
    private String pass;
    private BuscarInspeccionAdapter adapter;


    public BuscarInspeccionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_buscar_inspeccion, container, false);

        buscar = view.findViewById(R.id.BuscarInspeccion);
        tractora = view.findViewById(R.id.tvTractoraBuscarInspeccion);
        cisterna = view.findViewById(R.id.tvCisternaBuscarInspeccion);

        listaDatosInspeccion = new ArrayList<>();
        recyclerVehiculos = view.findViewById(R.id.rv_buscarInspecciones);
        recyclerVehiculos.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerVehiculos.setHasFixedSize(true);

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tractora.clearFocus();
                cisterna.clearFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(tractora.getWindowToken(), 0);
                listaDatosInspeccion.clear();
                buscar(tractora.getText().toString(), cisterna.getText().toString(), user, pass);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void buscar(final String tractora, final String cisterna, final String user, final String pass) {

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


                } catch (JSONException e) {
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
                params.put("rigido", tractora);
                params.put("cisterna", cisterna);
                return params;
            }

        };
        VolleySingleton.getInstanciaVolley(getContext()).addToRequestqueue(sr);
    }


}
