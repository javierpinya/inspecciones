package clh.inspecciones.com.inspecciones_v2.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import clh.inspecciones.com.inspecciones_v2.Adapters.ControlAccesoResultadoTractoraAdapter;
import clh.inspecciones.com.inspecciones_v2.Clases.CARigidoBD;
import clh.inspecciones.com.inspecciones_v2.Clases.CATractoraBD;
import clh.inspecciones.com.inspecciones_v2.R;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class ControlAccesoResultadoRigidoFragment extends Fragment implements RealmChangeListener<RealmResults<CARigidoBD>>{

    private dataListener callback;
    private String json_url2 = "http://pruebaalumnosandroid.esy.es/inspecciones/consultas_inspecciones_app.php";
    private Realm realm;
    private RealmResults<CATractoraBD> tractoraBD;
    private ListView mListView;
    private ControlAccesoResultadoTractoraAdapter adapter;
    private String matriculaIntent;

    public ControlAccesoResultadoRigidoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = view = inflater.inflate(R.layout.fragment_control_acceso_resultado_rigido, container, false);

        return view;
    }

    @Override
    public void onChange(RealmResults<CARigidoBD> caRigidoBDS) {

    }

    public interface dataListener{
        void getRigidoIntent(String rigido);
    }
}
