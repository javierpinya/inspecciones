package clh.inspecciones.com.inspecciones_v2.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import clh.inspecciones.com.inspecciones_v2.Adapters.ControlAccesoResultadoCisternaAdapter;
import clh.inspecciones.com.inspecciones_v2.Adapters.ControlAccesoResultadoRigidoAdapter;
import clh.inspecciones.com.inspecciones_v2.Adapters.ControlAccesoResultadoTractoraAdapter;
import clh.inspecciones.com.inspecciones_v2.Clases.CACisternaBD;
import clh.inspecciones.com.inspecciones_v2.Clases.CACompartimentosBD;
import clh.inspecciones.com.inspecciones_v2.Clases.CARigidoBD;
import clh.inspecciones.com.inspecciones_v2.Clases.CATractoraBD;
import clh.inspecciones.com.inspecciones_v2.R;
import clh.inspecciones.com.inspecciones_v2.SingleTones.VolleySingleton;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;

public class ControlAccesoResultadoVehiculoFragment extends Fragment implements RealmChangeListener<RealmResults<CATractoraBD>>{

    public dataListener callback;
    private Realm realm;
    private RealmResults<CARigidoBD> rigidoBD;
    private RealmResults<CACisternaBD> cisternaBD;
    private RealmResults<CATractoraBD> tractoraBD;
    private ListView mListView;

    //Adaptadores
    private ControlAccesoResultadoRigidoAdapter adapterRigido;
    private ControlAccesoResultadoTractoraAdapter adapterTractora;
    private ControlAccesoResultadoCisternaAdapter adapterCisterna;

    public ControlAccesoResultadoVehiculoFragment() {
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = view = inflater.inflate(R.layout.fragment_control_acceso_resultado_vehiculo, container, false);

        mListView = (ListView)view.findViewById(R.id.lv_controlaccesoresultadovehiculo);


        realm = Realm.getDefaultInstance();
        return view;
    }

    public void renderVehiculo(String tipoComponente){
        Toast.makeText(getActivity(), tipoComponente.trim(), Toast.LENGTH_SHORT).show();
        switch (tipoComponente.trim()){
            case "R":
                rigidoBD = realm.where(CARigidoBD.class).findAll();
                adapterRigido = new ControlAccesoResultadoRigidoAdapter(getActivity(), rigidoBD, R.layout.ca_rigido_checking_listview_item);
                mListView.setAdapter(adapterRigido);
                break;
            case "T":
                tractoraBD = realm.where(CATractoraBD.class).findAll();
                adapterTractora = new ControlAccesoResultadoTractoraAdapter(getActivity(), tractoraBD, R.layout.ca_tractora_checking_listview_item);
                mListView.setAdapter(adapterTractora);
                break;
            case "C":
                cisternaBD = realm.where(CACisternaBD.class).findAll();
                adapterCisterna = new ControlAccesoResultadoCisternaAdapter(getActivity(), cisternaBD, R.layout.ca_cisterna_checking_listview_item);
                mListView.setAdapter(adapterCisterna);
                break;
        }

    }
/*
    @Override
    public void onChange(RealmResults<CARigidoBD> caRigidoBDS) {

    }
*/
    @Override
    public void onChange(RealmResults<CATractoraBD> caTractoraBDS) {

    }


    public interface dataListener{
    }
}
