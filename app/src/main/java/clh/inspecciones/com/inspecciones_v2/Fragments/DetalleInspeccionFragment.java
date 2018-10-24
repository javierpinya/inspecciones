package clh.inspecciones.com.inspecciones_v2.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.text.SimpleDateFormat;

import clh.inspecciones.com.inspecciones_v2.Adapters.DetalleInspeccionAdapter;
import clh.inspecciones.com.inspecciones_v2.Clases.DetalleInspeccionBD;
import clh.inspecciones.com.inspecciones_v2.R;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleInspeccionFragment extends Fragment implements RealmChangeListener<RealmResults<DetalleInspeccionBD>>{

    public dataListener callback;
    private ListView mListView;
    private Realm realm;
    private RealmResults<DetalleInspeccionBD> detalleInspeccionBDS;
    private String inspeccionIntent;
    private DetalleInspeccionAdapter adapter;

    private SimpleDateFormat parseador = new SimpleDateFormat("dd-MM-yyyy");




    public DetalleInspeccionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            callback =(dataListener)context;
        }catch(Exception e){
            throw new ClassCastException(context.toString() + " should implement dataListener");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalle_inspeccion, container, false);
        mListView = view.findViewById(R.id.lv_detalleinspecciones);

        realm = Realm.getDefaultInstance();
        if (realm.isEmpty()==false){
            realm.beginTransaction();
            realm.deleteAll();
            realm.commitTransaction();
        }

        return view;
    }

    public void renderText(String inspeccion){
        inspeccionIntent = inspeccion.trim();
        detalleInspeccionBDS = realm.where(DetalleInspeccionBD.class).findAll();
        detalleInspeccionBDS.addChangeListener(this);
        adapter = new DetalleInspeccionAdapter(getActivity(), detalleInspeccionBDS,R.layout.detalle_inspecciones_adapter);
        mListView.setAdapter(adapter);

    }

    @Override
    public void onChange(RealmResults<DetalleInspeccionBD> detalleInspeccionBDS) {
        adapter.notifyDataSetChanged();
    }


    public interface dataListener{
    }

}
