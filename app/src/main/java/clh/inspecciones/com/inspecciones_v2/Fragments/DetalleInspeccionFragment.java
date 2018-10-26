package clh.inspecciones.com.inspecciones_v2.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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
public class DetalleInspeccionFragment extends Fragment implements RealmChangeListener<RealmResults<DetalleInspeccionBD>>, View.OnClickListener{

    public dataListener callback;
    private ListView mListView;
    private Realm realm;
    private RealmResults<DetalleInspeccionBD> detalleInspeccionBDS;
    private RealmResults<DetalleInspeccionBD> detalleInspeccionToast;
    private String inspeccionIntent;
    private DetalleInspeccionAdapter adapter;
    private Button guardar;
    private Boolean purgas;

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
        guardar = (Button)view.findViewById(R.id.guardar_cambios);
        guardar.setVisibility(View.VISIBLE);


        guardar.setOnClickListener(this);

        realm = Realm.getDefaultInstance();
        if (realm.isEmpty()==false){
            realm.beginTransaction();
            realm.deleteAll();
            realm.commitTransaction();
        }


        return view;
    }

    public void renderText(String inspeccion){
       // guardar.setVisibility(View.VISIBLE);
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

    @Override
    public void onClick(View v) {
        DetalleInspeccionBD inspeccion;
        realm.beginTransaction();
        inspeccion = detalleInspeccionBDS.get(0);
        inspeccion.setPurgaCompartimentos(inspeccion.getPurgaCompartimentos());
        realm.copyToRealmOrUpdate(inspeccion);
        realm.commitTransaction();

        detalleInspeccionToast = realm.where(DetalleInspeccionBD.class).findAll();
        detalleInspeccionToast.addChangeListener(this);
        purgas = detalleInspeccionToast.get(0).getPurgaCompartimentos();
        Toast.makeText(getActivity(), "purgas: " + purgas.toString(), Toast.LENGTH_SHORT).show();

    }


    public interface dataListener{
    }

}
