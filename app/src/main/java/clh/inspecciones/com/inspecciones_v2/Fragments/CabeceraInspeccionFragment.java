package clh.inspecciones.com.inspecciones_v2.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import clh.inspecciones.com.inspecciones_v2.Clases.DetalleInspeccionBD;
import clh.inspecciones.com.inspecciones_v2.R;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class CabeceraInspeccionFragment extends Fragment implements RealmChangeListener<RealmResults<DetalleInspeccionBD>> {

    public dataListener callback;
    private Realm realm;
    private RealmResults<DetalleInspeccionBD> inspeccionBD;
    private String inspeccion;

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

        inspeccion = "admin00001";
        realm = Realm.getDefaultInstance();
        if (realm.isEmpty()==false){
            realm.beginTransaction();
            realm.deleteAll();
            realm.commitTransaction();
        }
        return view;
    }

    public void crearInspeccionBD(String tractora, String cisterna, String conductor, String t_vehiculo, String tipo_inspeccion){
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
    }

    public void imprimirDatos(){
        inspeccionBD = realm.where(DetalleInspeccionBD.class).findAll();
        Toast.makeText(getActivity(), "inspeccion: " + inspeccionBD.get(0).getInspeccion(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onChange(RealmResults<DetalleInspeccionBD> detalleInspeccionBD) {

    }

    public interface dataListener{
        void datosIntent(String tractora, String cisterna, String conductor, String t_vehiculo, String tipo_inspeccion);

    }

}
