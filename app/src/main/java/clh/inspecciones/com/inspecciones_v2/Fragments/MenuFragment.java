package clh.inspecciones.com.inspecciones_v2.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import clh.inspecciones.com.inspecciones_v2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment implements View.OnClickListener{

    private Button mButtonNuevaInspeccion;
    private Button mButtonBuscarPrioritarias;
    private Button mButtonModificarInspeccion;
    private Button mButtonInterpolar;
    private Button mButtonSalir;
  //  private EleccionMenu callback;

    public MenuFragment() {
        // Required empty public constructor
    }
/*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            callback = (EleccionMenu)context;
        }catch (Exception e){
            throw new ClassCastException(context.toString() + " should implement EleccionMenu");
        }

    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_menu, container, false);

        mButtonNuevaInspeccion = v.findViewById(R.id.altanueva);
        mButtonBuscarPrioritarias = v.findViewById(R.id.buscarPrioritarias);
        mButtonModificarInspeccion = v.findViewById(R.id.modificarInspeccion);
        mButtonInterpolar = v.findViewById(R.id.interpolar);
        mButtonSalir = v.findViewById(R.id.salir);

        mButtonNuevaInspeccion.setOnClickListener(this);
        mButtonModificarInspeccion.setOnClickListener(this);
        mButtonBuscarPrioritarias.setOnClickListener(this);
        mButtonInterpolar.setOnClickListener(this);
        mButtonSalir.setOnClickListener(this);


        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.altanueva:
         //       callback.eleccionMenu(R.id.altanueva);
                break;
            case R.id.modificarInspeccion:
                break;
            case R.id.buscarPrioritarias:
                break;
            case R.id.interpolar:
                break;
            default:
                break;

        }

    }
/*
    public interface EleccionMenu{
        void eleccionMenu(int seleccion);
    }
*/
}
