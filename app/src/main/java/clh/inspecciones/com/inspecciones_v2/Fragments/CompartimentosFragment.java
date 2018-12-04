package clh.inspecciones.com.inspecciones_v2.Fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import clh.inspecciones.com.inspecciones_v2.Adapters.CompartimentosAdapter;
import clh.inspecciones.com.inspecciones_v2.Clases.CACompartimentosBD;
import clh.inspecciones.com.inspecciones_v2.Clases.CARigidoBD;
import clh.inspecciones.com.inspecciones_v2.Clases.DetalleInspeccionBD;
import clh.inspecciones.com.inspecciones_v2.R;
import clh.inspecciones.com.inspecciones_v2.SingleTones.VolleySingleton;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompartimentosFragment extends Fragment implements RealmChangeListener<RealmResults<CACompartimentosBD>> {

    private Realm realm;
    private RealmResults<CACompartimentosBD> caCompartimentosBD;
    private String matricula;
    private String inspeccion;
    private TextView cisterna;
    private dataListener callback;
    private CompartimentosAdapter adapter;
    private CACompartimentosBD compartimentosBD;
    private RealmList<CACompartimentosBD> compartimentosList;
    private String url = "http://pruebaalumnosandroid.esy.es/inspecciones/registrar_compartimentos.php";
    private String user;
    private String pass;

    //compartimentos
    private List<Integer> compartimentos;
    private List<Integer> capacidad;
    private List<String> tags;
    private List<Integer> cantidad;
    private List<Boolean> cumpleCantidad = new ArrayList<>();

    private RecyclerView mRecyclerView;
    // Puede ser declarado como 'RecyclerView.Adapter' o como nuetra clase adaptador 'MyAdapter'

    

    public CompartimentosFragment() {
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

        View view;
        view = inflater.inflate(R.layout.fragment_compartimentos, container, false);
        realm = Realm.getDefaultInstance();
        cisterna = (TextView)view.findViewById(R.id.tv_cisternamatricula);
        cisterna.setText(matricula);

        mRecyclerView = view.findViewById(R.id.rv_compartimentos);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setHasFixedSize(true);



        renderCompartimentos();
        // Inflate the layout for this fragment
        return view;
    }

    public void enviarMatricula(String matricula, String inspeccion){
        this.matricula = matricula.trim();
        this.inspeccion = inspeccion.trim();
    }


    public void renderCompartimentos(){


        caCompartimentosBD = realm.where(CACompartimentosBD.class).findAll();
        compartimentos = new ArrayList<>();
        capacidad = new ArrayList<>();
        tags = new ArrayList<>();
        cantidad = new ArrayList<>();

        if (caCompartimentosBD.size() > 0) {
            //Toast.makeText(getActivity(), "caCompartimentosBD.get(0).getCan_capacidad(): " + caCompartimentosBD.get(0).getCan_capacidad(), Toast.LENGTH_SHORT).show();

            for (int i = 0; i < caCompartimentosBD.size(); i++) {
                compartimentos.add (caCompartimentosBD.get(i).getCod_compartimento());
                capacidad.add(caCompartimentosBD.get(i).getCan_capacidad());
                tags.add(caCompartimentosBD.get(i).getCod_tag_cprt());
            }
        }else{
            Toast.makeText(getActivity(), "la query no da resultados...", Toast.LENGTH_SHORT).show();
        }

        adapter = new CompartimentosAdapter(caCompartimentosBD, R.layout.compartimentos_listview_item, new CompartimentosAdapter.OnItemClickListener(){

            @Override
            public void onItemClick(CACompartimentosBD compartimentosList, int position) {
                dialogoIntroducirCantidad("Introducir cantidad cargada comp " + compartimentosList.getCod_compartimento(), compartimentosList.getCan_capacidad(), position);
            }
        });

        mRecyclerView.setAdapter(adapter);
    }

    //** Dialogs **/

    private void dialogoIntroducirCantidad(String title, final int capacidad, final int position){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        if (title != null) builder.setTitle(title);

        View viewInflated = LayoutInflater.from(getActivity()).inflate(R.layout.dialogo_cantidad_cargada, null);
        builder.setView(viewInflated);

        final EditText input = (EditText) viewInflated.findViewById(R.id.et_cant_cargada);


        builder.setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int cantidad=1000000;
                Boolean existe=false;
                Boolean cumple=false;
                List<Integer> posiciones = new ArrayList<>();

                cantidad = Integer.valueOf(input.getText().toString().trim());
                if (cantidad<capacidad){
                    cumple=true;
                    Toast.makeText(getActivity(), "Cumple", Toast.LENGTH_SHORT).show();
                }else{
                    cumple=false;
                    Toast.makeText(getActivity(), "No Cumple. La cantidad cargada supera la capacidad registrada", Toast.LENGTH_LONG).show();
                }

                if (cantidad != 1000000) {
                    /*
                       Este for lo utilizamos para los casos en los que se quiera corregir una cantidad introduce anteriormente en un compartimento.
                       De esta forma, se borrará la cantidad introducida en la ocasión anterior y se
                    */
                    for (int j=0;j<posiciones.size();j++){
                        if (posiciones.get(j) == position){
                            existe=true;
                        }else{
                            posiciones.add(position);
                            existe=false;
                        }
                    }

                    if(existe){
                        cambiarCantidad(position,cantidad);
                        cumpleCantidad.set(position,cumple);
                    }else{
                        addCantidad(cantidad);
                        cumpleCantidad.add(cumple);
                        posiciones.add(position);
                    }
                }else {
                    Toast.makeText(getActivity(), "Introducir cantidad cargada", Toast.LENGTH_LONG).show();
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void addCantidad(int cantidad){
        this.cantidad.add(cantidad);
    }
    private void cambiarCantidad(int position, int cantidad){
        this.cantidad.set(position, cantidad);
    }


    public void guardar(String user, String pass){
        this.user = user;
        this.pass = pass;
        //Toast.makeText(getActivity(), "Can: " + this.cantidad.get(0) + " Can: " + this.cantidad.get(1) + " Can: " + this.cantidad.get(2) + " Can: " + this.cantidad.get(3) + " Can: " + this.cantidad.get(4) + " Can: " + this.cantidad.get(5), Toast.LENGTH_LONG).show();
        if (compartimentos.size() == cantidad.size()){
            for (int i=0;i<compartimentos.size();i++) {
                compartimentosBD = realm.where(CACompartimentosBD.class).equalTo("cod_compartimento", compartimentos.get(i).intValue()).findFirst(); //("cod_compartimento", compartimentos.get(i)).findFirst();
                realm.beginTransaction();
                compartimentosBD.setCan_cargada(cantidad.get(i));
                realm.copyToRealmOrUpdate(compartimentosBD);
                realm.commitTransaction();
                //registrar en BD Online
                guardarOnLine(user, pass,String.valueOf(compartimentos.get(i)), tags.get(i), String.valueOf(capacidad.get(i)), String.valueOf(cantidad.get(i)), String.valueOf(cumpleCantidad.get(i)), inspeccion);

            }
        }else{
            Toast.makeText(getActivity(), "Hay que registrar todos los compartimentos, aunque sea con 0", Toast.LENGTH_LONG).show();
        }
    }

    private void guardarOnLine(final String user, final String pass, final String compartimento, final String tag, final String capacidad, final String cantidad, final String cumple, final String inspeccion) {

        StringRequest sr = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getActivity(), response  + " - " + compartimento + " - " + tag + " - " + capacidad + " - " + cantidad + " - " + cumple +" - " +  inspeccion, Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
               // callback.continuar();  //mejor continuar para incluir observaciones, etc, no volver
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }){


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user", user);
                params.put("pass", pass);
                params.put("compartimento", compartimento);
                params.put("tag", tag);
                params.put("capacidad", capacidad);
                params.put("cantidad", cantidad);
                params.put("cumple", cumple);
                params.put("inspeccion", inspeccion);
                return params;
            }
        };

        VolleySingleton.getInstanciaVolley(getActivity()).addToRequestqueue(sr);
    }

    @Override
    public void onChange(RealmResults<CACompartimentosBD> caCompartimentosBDS) {

    }

    public interface dataListener{
        void volver();
        void continuar();
        //void enviarMatricula(String matricula, String inspeccion);
        void elegirCompartimento(int compartimento);
    }

}
