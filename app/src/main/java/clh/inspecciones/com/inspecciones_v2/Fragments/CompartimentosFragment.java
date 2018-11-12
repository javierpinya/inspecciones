package clh.inspecciones.com.inspecciones_v2.Fragments;


import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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
    private CARigidoBD caRigidoBD;
    private RealmResults<CACompartimentosBD> pepe;
    private RealmList<CACompartimentosBD> compartimentosBD;
    //private RealmResults<CACompartimentosBD> caCompartimentosBD;
    private ListView mListView;
    private List<String> names;
    private String matricula;
    private TextView cisterna;
    private dataListener callback;
    private ArrayAdapter<String> adapter;
    private RealmResults<DetalleInspeccionBD> detalleInspeccionBDS;

    //compartimentos
    private List<Integer> compartimentos;
    private List<Integer> capacidad;
    private List<String> tags;


    

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

        mListView = (ListView)view.findViewById(R.id.lv_compartimentos);

        realm = Realm.getDefaultInstance();
        pepe = realm.where(CACompartimentosBD.class).findAll();
        if(pepe.size()<1){
            Toast.makeText(getActivity(), "sin datos en CARigido", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getActivity(), "CARigido tiene datos", Toast.LENGTH_SHORT).show();
        }
        if(realm.isEmpty()==true){
            Toast.makeText(getActivity(), "está vacia", Toast.LENGTH_SHORT).show();
        }
        //cisterna = (TextView)view.findViewById(R.id.tv_cisternamatricula);
        //cisterna.setText(matricula);
/*
        renderCompartimentos(matricula);
*/
        // Inflate the layout for this fragment
        return view;
    }

    public void enviarMatricula(String matricula){
        this.matricula = matricula;
    }

    public void renderCompartimentos(String matricula){

        caRigidoBD = realm.where(CARigidoBD.class).equalTo("matricula", matricula).findFirst();
        Toast.makeText(getActivity(), "matricula: " +  matricula, Toast.LENGTH_SHORT).show();
        //caRigidoBD.addChangeListener(this);
        compartimentosBD = caRigidoBD.getCompartimentos();
        //caCompartimentosBD = realm.where(CACompartimentosBD.class).findAll();
        names = new ArrayList<>();
        names.add("hola");
    //        Toast.makeText(getActivity(), "caRigidoBD size: " +caRigidoBD.getMatricula().trim(), Toast.LENGTH_SHORT).show();


        if (compartimentosBD.size() > 0) {
            Toast.makeText(getActivity(), "caCompartimentosBD.get(0).getCan_capacidad(): " + compartimentosBD.get(0).getCan_capacidad(), Toast.LENGTH_SHORT).show();

            for (int i = 0; i < compartimentosBD.size(); i++) {
                names.add("Compartimento " + i + 1);
            }
        }else{
            Toast.makeText(getActivity(), "la query no da resultados...", Toast.LENGTH_SHORT).show();
        }

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, names);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "Compartimento: " + names.get(position).toString(), Toast.LENGTH_SHORT).show();

            }
        });

        /*
        compartimentosBD = caRigidoBD.getCompartimentos();
        for (int i=0; i<caCompartimentosBD.size(); i++){
            compartimentos.add(caCompartimentosBD.get(i).getCod_compartimento());
            capacidad.add(caCompartimentosBD.get(i).getCan_capacidad());
            tags.add(caCompartimentosBD.get(i).getCod_tag_cprt());
        }
        */

    }
/*
    @Override
    public void onChange(CARigidoBD caRigidoBDS) {
        adapter.notifyDataSetChanged();
    }
*/
    @Override
    public void onClick(View v) {

    }


    @Override
    public void onChange(RealmResults<CACompartimentosBD> caCompartimentosBDS) {

    }

    public interface dataListener{
        void enviarMatricula(String matricula);
        void elegirCompartimento(int compartimento);
    }

}
