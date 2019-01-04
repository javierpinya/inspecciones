package clh.inspecciones.com.inspecciones_v2.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.Double;

import clh.inspecciones.com.inspecciones_v2.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalculadoraFragment extends Fragment implements View.OnClickListener{

    private TextView textView;
    private EditText editText;
    private Button button;
    private String texto="0";
    private String tipoVehiculo;

    private double dato =0.0;
    private Integer resultado=0;


    public CalculadoraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calculadora, container, false);
        editText = (EditText)view.findViewById(R.id.et_litrostotales);
        textView = (TextView)view.findViewById(R.id.tv_resultado96);
        button = (Button)view.findViewById(R.id.calcular);

        button.setOnClickListener(this);





        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.calcular:

                dato = Double.parseDouble(editText.getText().toString());
                dato = 0.96 * dato;
                texto = String.valueOf(dato);

                if(texto!="0"){

                    textView.setText(texto);
                }else{
                    Toast.makeText(getActivity(), "Introducir valor", Toast.LENGTH_SHORT).show();
                }
                break;
                default:
                    break;

        }

    }
}
