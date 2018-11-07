package clh.inspecciones.com.inspecciones_v2.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import clh.inspecciones.com.inspecciones_v2.Adapters.CompartimentosAdapter;
import clh.inspecciones.com.inspecciones_v2.Clases.CACompartimentosBD;
import clh.inspecciones.com.inspecciones_v2.Clases.CARigidoBD;
import clh.inspecciones.com.inspecciones_v2.R;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompartimentosFragment extends Fragment implements RealmChangeListener<RealmResults<CARigidoBD>> {

    private Realm realm;
    private CARigidoBD caRigidoBD;
    private RealmList<CACompartimentosBD> compartimentosBD;
    private RealmResults<CACompartimentosBD> caCompartimentosBD;
    private ListView mListView;

    //compartimentos
    private List<Integer> compartimentos;
    private List<Integer> capacidad;
    private List<String> tags;


    

    public CompartimentosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view;
        view = inflater.inflate(R.layout.fragment_compartimentos, container, false);

        mListView = (ListView)view.findViewById(R.id.lv_compartimentos);

        // Inflate the layout for this fragment
        return view;
    }

    public void renderCompartimentos(String matricula){
        caRigidoBD = realm.where(CARigidoBD.class).equalTo("matricula", matricula).findFirst();

        caCompartimentosBD = realm.where(CACompartimentosBD.class).findAll();

        compartimentosBD = caRigidoBD.getCompartimentos();
        for (int i=0; i<caCompartimentosBD.size(); i++){
            compartimentos.add(caCompartimentosBD.get(i).getCod_compartimento());
            capacidad.add(caCompartimentosBD.get(i).getCan_capacidad());
            tags.add(caCompartimentosBD.get(i).getCod_tag_cprt());
        }

        CompartimentosAdapter adapter = new CompartimentosAdapter(getActivity(), R.layout.compartimentos_listview_item, matricula, capacidad, compartimentos, tags);
        mListView.setAdapter(adapter);



    }

    @Override
    public void onChange(RealmResults<CARigidoBD> caRigidoBDS) {

    }

    public interface dataListener{
        void enviarMatricula(String matricula);
    }

}
