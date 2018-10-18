package clh.inspecciones.com.inspecciones_v2.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;

import clh.inspecciones.com.inspecciones_v2.Clases.DetalleInspeccionBD;
import clh.inspecciones.com.inspecciones_v2.R;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleInspeccionFragment extends Fragment {

    public dataListener callback;

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
        return inflater.inflate(R.layout.fragment_detalle_inspeccion, container, false);
    }


    public interface dataListener{
    }

}
