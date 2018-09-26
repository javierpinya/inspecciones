package clh.inspecciones.com.inspecciones_v2.Fragments;


import android.content.Context;
import android.content.Intent;
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

    public AltaNuevaListener callback;

    CheckBox cbrigido;
    CheckBox cbtractora;
    CheckBox cbcisterna;
    CheckBox cbconjunto;
    CheckBox cbligeros;
    CheckBox cbpesados;
    CheckBox cbjeta;
    Button btn_siguiente1;
    String t_vehiculo;
    String t_inspeccion;


    public AltaNuevaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            callback = (AltaNuevaListener)context;
        }catch (Exception e){
            throw new ClassCastException(context.toString() + " should implement AltaNuevaListener");
        }
    }

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
        btn_siguiente1 = v.findViewById(R.id.btn_siguiente1);

        cbrigido.setOnClickListener(this);
        cbtractora.setOnClickListener(this);
        cbcisterna.setOnClickListener(this);
        cbconjunto.setOnClickListener(this);
        cbligeros.setOnClickListener(this);
        cbpesados.setOnClickListener(this);
        cbjeta.setOnClickListener(this);
        btn_siguiente1.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cbrigido:
                t_vehiculo="R";
                cbtractora.setChecked(false);
                cbconjunto.setChecked(false);
                cbcisterna.setChecked(false);
                break;
            case R.id.cbtractora:
                cbconjunto.setChecked(false);
                cbcisterna.setChecked(false);
                cbrigido.setChecked(false);
                t_vehiculo="T";
                break;
            case R.id.cbcisterna:
                cbrigido.setChecked(false);
                cbconjunto.setChecked(false);
                cbtractora.setChecked(false);
                t_vehiculo="C";
                break;
            case R.id.cbconjunto:
                cbtractora.setChecked(false);
                cbcisterna.setChecked(false);
                cbrigido.setChecked(false);
                t_vehiculo="SLO";
                break;
            case R.id.cbligeros:
                t_inspeccion = "L";
                cbpesados.setChecked(false);
                cbjeta.setChecked(false);
                break;
            case R.id.cbpesados:
                t_inspeccion="P";
                cbligeros.setChecked(false);
                cbjeta.setChecked(false);
                break;
            case R.id.cbjeta:
                t_inspeccion="J";
                cbligeros.setChecked(false);
                cbpesados.setChecked(false);
                break;
            case R.id.btn_siguiente1:
                callback.altaNueva(t_vehiculo, t_inspeccion);
                break;
            default:
                break;
        }
    }

    public interface AltaNuevaListener{
        void altaNueva(String t_vehiculo, String t_inspeccion);
    }

}
