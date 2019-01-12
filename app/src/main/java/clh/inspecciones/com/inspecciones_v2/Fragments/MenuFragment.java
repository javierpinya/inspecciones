package clh.inspecciones.com.inspecciones_v2.Fragments;


import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import clh.inspecciones.com.inspecciones_v2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment implements View.OnClickListener{

    private ImageButton nuevaInspeccion;
    private ImageButton buscarInspeccion;
    private ImageButton calculadora;
    private ImageButton salir;
    private EleccionMenu callback;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            callback = (EleccionMenu)context;
        }catch (Exception e){
            throw new ClassCastException(context.toString() + " should implement EleccionMenu");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_menu, container, false);

        nuevaInspeccion = (ImageButton)v.findViewById(R.id.altanueva);
        buscarInspeccion = (ImageButton)v.findViewById(R.id.buscarinspeccion);
        calculadora = (ImageButton)v.findViewById(R.id.calculadora);
        salir = (ImageButton)v.findViewById(R.id.salir);

        nuevaInspeccion.setOnClickListener(this);
        buscarInspeccion.setOnClickListener(this);
        calculadora.setOnClickListener(this);
        salir.setOnClickListener(this);


        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.altanueva:
                callback.eleccionMenu("altaNueva");
                break;
            case R.id.buscarinspeccion:
                callback.eleccionMenu("buscarInspeccion");
                break;
            case R.id.calculadora:
                callback.eleccionMenu("calculadora");
                break;
            case R.id.salir:
                callback.eleccionMenu("salir");
                break;
            default:
                break;

        }

    }

    public interface EleccionMenu{
        void eleccionMenu(String seleccion);
    }

}
