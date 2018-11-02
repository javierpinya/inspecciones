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
    private RealmResults<DetalleInspeccionBD> inspeccionBDs;
    private DetalleInspeccionBD inspeccionBD;
    private String inspeccion;
    private String returnInspeccion;
    private Button btn_siguiente;
    private Button btn_incidencias;
    private EditText etIa;
    private EditText etAlbaran;
    private EditText etTrans;
    private EditText etTablaCal;
    private DetalleInspeccionAdapter adapter;
    private ListView mListView;
    private String ia;
    private String albaran;
    private String transportista;
    private String tabla_calibracion;

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

       // mListView = (ListView)view.findViewById(R.id.lv_detalleinspecciones);

        btn_siguiente = (Button)view.findViewById(R.id.btn_siguiente2);
        btn_incidencias= (Button)view.findViewById(R.id.btn_incidenciasInspeccion);
        etIa = (EditText)view.findViewById(R.id.et_instalacion);
        etAlbaran = (EditText)view.findViewById(R.id.et_albaran);
        etTrans = (EditText)view.findViewById(R.id.et_transportistaresp);
        etTablaCal = (EditText)view.findViewById(R.id.et_tablacalibracion);

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
            inspeccionBD.setCisterna(cisterna);
        }else{
            inspeccionBD.setRigido(tractora);
        }
        inspeccionBD.setConductor(conductor);
        realm.copyToRealmOrUpdate(inspeccionBD);
        realm.commitTransaction();
    }

    public String obtenerCambios(){
        inspeccionBD = realm.where(DetalleInspeccionBD.class).equalTo("inspeccion", inspeccion).findFirst();
        //Toast.makeText(getActivity(), "inspeccion: " + inspeccionBD.get(0).getSuperficieSupAntideslizante(), Toast.LENGTH_SHORT).show();
        if (inspeccionBD.getInspeccion() != null) {
            returnInspeccion = inspeccionBD.getInspeccion();
        } else{
            returnInspeccion = "no encontrada";
        }
        return returnInspeccion;
    }

    @Override
    public void onChange(RealmResults<DetalleInspeccionBD> detalleInspeccionBD) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_incidenciasInspeccion:

                ia = etIa.getText().toString();
                albaran = etAlbaran.getText().toString();
                transportista = etTrans.getText().toString();
                tabla_calibracion = etTablaCal.getText().toString();

                if (ia == null || albaran == null || transportista == null  || tabla_calibracion == null){
                    Toast.makeText(getActivity(), "Debe rellenar todos los datos antes de introducir las incidencias", Toast.LENGTH_SHORT).show();
                    break;
                } else{
                    callback.obtenerInspeccion(inspeccion, ia, albaran, transportista, tabla_calibracion);
                }



                break;
            case R.id.btn_siguiente2:

                callback.continuar(obtenerCambios());

                break;

                default:
                    break;


        }

    }

    public interface dataListener{
        void datosIntent(String tractora, String cisterna, String conductor, String t_rigido, String tipo_inspeccion);
        void obtenerInspeccion(String inspeccion, String Instalacion, String albaran, String transportista, String tabla_calibracion);
        void continuar(String inspeccion);

    }

}
