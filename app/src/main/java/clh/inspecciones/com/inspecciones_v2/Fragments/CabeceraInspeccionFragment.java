package clh.inspecciones.com.inspecciones_v2.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import clh.inspecciones.com.inspecciones_v2.Adapters.DetalleInspeccionAdapter;
import clh.inspecciones.com.inspecciones_v2.Clases.DetalleInspeccionBD;
import clh.inspecciones.com.inspecciones_v2.R;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class CabeceraInspeccionFragment extends Fragment implements RealmChangeListener<RealmResults<DetalleInspeccionBD>>, View.OnClickListener {

    public dataListener callback;
    private Realm realm;
    private RealmResults<DetalleInspeccionBD> inspeccionBD;
    private String inspeccion;
    private Button btn_siguiente;
    private Button btn_incidencias;
    private EditText etIa;
    private EditText etAlbaran;
    private EditText etTrans;
    private DetalleInspeccionAdapter adapter;
    private ListView mListView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        try{
            callback =(dataListener)context;
        }catch(Exception e){
            throw new ClassCastException(context.toString() + " should implement dataListener");

        }
    }

    public CabeceraInspeccionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_cabecera_inspeccion, container, false);

        mListView = (ListView)view.findViewById(R.id.lv_detalleinspecciones);

        btn_siguiente = (Button)view.findViewById(R.id.btn_siguiente2);
        btn_incidencias= (Button)view.findViewById(R.id.btn_incidenciasInspeccion);
        etIa = (EditText)view.findViewById(R.id.et_instalacion);
        etAlbaran = (EditText)view.findViewById(R.id.et_albaran);
        etTrans = (EditText)view.findViewById(R.id.et_transportistaresp);

        btn_siguiente.setOnClickListener(this);
        btn_incidencias.setOnClickListener(this);

        inspeccion = "admin00001";
        realm = Realm.getDefaultInstance();
        if (realm.isEmpty()==false){
            realm.beginTransaction();
            realm.deleteAll();
            realm.commitTransaction();
        }
        return view;
    }

    public void crearInspeccionBD(String tractora, String cisterna, String conductor, String t_rigido, String tipo_inspeccion){
        //Toast.makeText(getActivity(), "inspeccion: " + inspeccion, Toast.LENGTH_SHORT).show();
        realm.beginTransaction();
        DetalleInspeccionBD inspeccionBD = new DetalleInspeccionBD(inspeccion);
        if (t_rigido.equals("T")){
            inspeccionBD.setTractora(tractora);
        }else{
            inspeccionBD.setRigido(tractora);
        }
        //inspeccionBD.setInstalacion(etIa.getText().toString());
        //inspeccionBD.setTransportista(etTrans.getText().toString());
        //inspeccionBD.setAlbaran(etAlbaran.getText().toString());
        inspeccionBD.setCisterna(cisterna);
        //inspeccionBD.setConductor(conductor);
        realm.copyToRealmOrUpdate(inspeccionBD);
        realm.commitTransaction();
    }

    public void imprimirDatos(){
        inspeccionBD = realm.where(DetalleInspeccionBD.class).findAll();
        Toast.makeText(getActivity(), "inspeccion: " + inspeccionBD.get(0).getTractora(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onChange(RealmResults<DetalleInspeccionBD> detalleInspeccionBD) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_incidenciasInspeccion:

                inspeccionBD = realm.where(DetalleInspeccionBD.class).findAll();
                inspeccionBD.addChangeListener(this);

                adapter = new DetalleInspeccionAdapter(getActivity(), inspeccionBD, R.layout.detalle_inspecciones_adapter);
                mListView.setAdapter(adapter);

                break;
            case R.id.btn_siguiente2:
                /*
                realm.beginTransaction();
                DetalleInspeccionBD inspeccionBD = new DetalleInspeccionBD();
                inspeccionBD.setInspeccion(inspeccion);
                if (t_vehiculo.equals("T")){
                    inspeccionBD.setTractora(tractora);
                }else{
                    inspeccionBD.setRigido(tractora);
                }
                inspeccionBD.setCisterna(cisterna);
                inspeccionBD.setConductor(conductor);
                realm.copyToRealmOrUpdate(inspeccionBD);
                realm.commitTransaction();
*/
                break;

                default:
                    break;


        }

    }

    public interface dataListener{
        void datosIntent(String tractora, String cisterna, String conductor, String t_rigido, String tipo_inspeccion);

    }

}
