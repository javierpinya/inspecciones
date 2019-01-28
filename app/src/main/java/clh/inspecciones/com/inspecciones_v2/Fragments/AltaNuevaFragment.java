package clh.inspecciones.com.inspecciones_v2.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import clh.inspecciones.com.inspecciones_v2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AltaNuevaFragment extends Fragment implements View.OnClickListener {

    // public AltaNuevaListener callback;

    CheckBox cbrigido;
    CheckBox cbtractora;
    CheckBox cbcisterna;
    CheckBox cbconjunto;
    CheckBox cbligeros;
    CheckBox cbpesados;
    CheckBox cbjeta;
   // Button btn_siguiente1;
    String tipoVehiculo;
    String tipoInspeccion;
    String tipoComponente;


    public AltaNuevaFragment() {
        // Required empty public constructor
    }
    /*

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            callback = (AltaNuevaListener)context;
        }catch (Exception e){
            throw new ClassCastException(context.toString() + " should implement AltaNuevaListener");
        }
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_alta_nueva, container, false);

        cbrigido = v.findViewById(R.id.cbrigido);
        cbtractora = v.findViewById(R.id.cbtractora);
        cbcisterna = v.findViewById(R.id.cbcisterna);
        cbconjunto = v.findViewById(R.id.cbconjunto);
        cbligeros = v.findViewById(R.id.cbligeros);
        cbpesados = v.findViewById(R.id.cbpesados);
        cbjeta = v.findViewById(R.id.cbjeta);
        //btn_siguiente1 = v.findViewById(R.id.btn_siguiente1);

        cbrigido.setOnClickListener(this);
        cbtractora.setOnClickListener(this);
        cbcisterna.setOnClickListener(this);
        cbconjunto.setOnClickListener(this);
        cbligeros.setOnClickListener(this);
        cbpesados.setOnClickListener(this);
        cbjeta.setOnClickListener(this);
        //btn_siguiente1.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cbrigido:
                tipoVehiculo ="0";
                tipoComponente ="R";
                cbtractora.setChecked(false);
                cbconjunto.setChecked(false);
                cbcisterna.setChecked(false);
                break;
            case R.id.cbtractora:
                tipoVehiculo ="0";
                tipoComponente ="T";
                cbconjunto.setChecked(false);
                cbcisterna.setChecked(false);
                cbrigido.setChecked(false);
                break;
            case R.id.cbcisterna:
                tipoVehiculo ="2";
                tipoComponente ="C";
                cbrigido.setChecked(false);
                cbconjunto.setChecked(false);
                cbtractora.setChecked(false);
                break;
            case R.id.cbconjunto:
                tipoVehiculo ="1";
                tipoComponente ="S"; //ojo, porque en este caso no se podría hacer una inspección de un tren de carretera
                cbtractora.setChecked(false);
                cbcisterna.setChecked(false);
                cbrigido.setChecked(false);
                break;
            case R.id.cbligeros:
                tipoInspeccion = "L";
                cbpesados.setChecked(false);
                cbjeta.setChecked(false);
                break;
            case R.id.cbpesados:
                tipoInspeccion ="P";
                cbligeros.setChecked(false);
                cbjeta.setChecked(false);
                break;
            case R.id.cbjeta:
                tipoInspeccion ="J";
                cbligeros.setChecked(false);
                cbpesados.setChecked(false);
                break;
            default:
                break;
        }
  //      callback.altaNueva(tipoVehiculo, tipoInspeccion, tipoComponente);
    }
/*
    public interface AltaNuevaListener{
        void altaNueva(String tipoVehiculo, String tipoInspeccion, String tipoComponente);
    }
*/
}
