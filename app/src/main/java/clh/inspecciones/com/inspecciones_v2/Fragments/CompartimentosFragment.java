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

import java.util.ArrayList;
import java.util.List;

import clh.inspecciones.com.inspecciones_v2.Adapters.CompartimentosAdapter;
import clh.inspecciones.com.inspecciones_v2.Clases.CACompartimentosBD;
import clh.inspecciones.com.inspecciones_v2.Clases.CARigidoBD;
import clh.inspecciones.com.inspecciones_v2.Clases.DetalleInspeccionBD;
import clh.inspecciones.com.inspecciones_v2.R;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompartimentosFragment extends Fragment implements RealmChangeListener<RealmResults<CACompartimentosBD>>, View.OnClickListener {

    private Realm realm;
    private RealmResults<CACompartimentosBD> caCompartimentosBD;
    private String matricula;
    private String inspeccion;
    private TextView cisterna;
    private dataListener callback;
    private CompartimentosAdapter adapter;
    private CACompartimentosBD compartimentosBD;
    private RealmList<CACompartimentosBD> compartimentosList;
    private Button guardar;

    //compartimentos
    private List<String> compartimentos;
    private List<Integer> capacidad;
    private List<String> tags;
    private List<Integer> cantidad;

    private RecyclerView mRecyclerView;
    // Puede ser declarado como 'RecyclerView.Adapter' o como nuetra clase adaptador 'MyAdapter'
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    

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
        guardar = (Button)view.findViewById(R.id.btn_guardar);
        cisterna.setText(matricula);

        guardar.setOnClickListener(this);
        mRecyclerView = view.findViewById(R.id.rv_compartimentos);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setHasFixedSize(true);



        renderCompartimentos();
        // Inflate the layout for this fragment
        return view;
    }

    public void enviarMatricula(String matricula, String inspeccion){
        this.matricula = matricula;
        this.inspeccion = inspeccion;
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
                compartimentos.add ("Compartimento " + caCompartimentosBD.get(i).getCod_compartimento());
                capacidad.add(caCompartimentosBD.get(i).getCan_capacidad());
                tags.add(caCompartimentosBD.get(i).getCod_tag_cprt());
            }
        }else{
            Toast.makeText(getActivity(), "la query no da resultados...", Toast.LENGTH_SHORT).show();
        }

        adapter = new CompartimentosAdapter(caCompartimentosBD, R.layout.compartimentos_listview_item, new CompartimentosAdapter.OnItemClickListener(){

            @Override
            public void onItemClick(CACompartimentosBD compartimentosList, int position) {
                dialogoIntroducirCantidad("Introducir cantidad cargada comp " + compartimentosList.getCod_compartimento());
            }
        });

        mRecyclerView.setAdapter(adapter);




        /*
        compartimentosBD = caRigidoBD.getCompartimentos();
        for (int i=0; i<caCompartimentosBD.size(); i++){
            compartimentos.add(caCompartimentosBD.get(i).getCod_compartimento());
            capacidad.add(caCompartimentosBD.get(i).getCan_capacidad());
            tags.add(caCompartimentosBD.get(i).getCod_tag_cprt());
        }


*/
    }

    //** Dialogs **/

    private void dialogoIntroducirCantidad(String title){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if (title != null) builder.setTitle(title);

        View viewInflated = LayoutInflater.from(getActivity()).inflate(R.layout.dialogo_cantidad_cargada, null);
        builder.setView(viewInflated);

        final EditText input = (EditText) viewInflated.findViewById(R.id.et_cant_cargada);


        builder.setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int cantidad=1000000;
                cantidad = Integer.valueOf(input.getText().toString().trim());
                if (cantidad != 1000000)
                    addCantidad(cantidad);
                else
                    Toast.makeText(getActivity(), "Introducir cantidad cargada", Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void addCantidad(int cantidad){
        this.cantidad.add(cantidad);
    }


/*
    @Override
    public void onChange(CARigidoBD caRigidoBDS) {
        adapter.notifyDataSetChanged();
    }
*/
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_guardar:
                if (compartimentos.size() == cantidad.size()){
                    for (int i=0;i<compartimentos.size();i++) {
                        compartimentosBD = realm.where(CACompartimentosBD.class).equalTo("cod_compartimento", compartimentos.get(i)).findFirst();
                        realm.beginTransaction();
                        compartimentosBD.setCan_cargada(cantidad.get(i));
                        realm.copyToRealmOrUpdate(compartimentosBD);
                        realm.commitTransaction();
                    }
                        //registrar en BD Online

                        callback.volver();  //mejor continuar para incluir observaciones, etc, no volver
                }else{
                    Toast.makeText(getActivity(), "Hay que registrar todos los compartimentos, aunque sea con 0", Toast.LENGTH_LONG).show();
                }
                break;
        }

    }


    @Override
    public void onChange(RealmResults<CACompartimentosBD> caCompartimentosBDS) {

    }

    public interface dataListener{
        void volver();
        //void enviarMatricula(String matricula, String inspeccion);
        void elegirCompartimento(int compartimento);
    }

}
