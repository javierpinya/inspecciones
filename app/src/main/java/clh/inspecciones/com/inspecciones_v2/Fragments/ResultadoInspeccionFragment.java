package clh.inspecciones.com.inspecciones_v2.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import clh.inspecciones.com.inspecciones_v2.Clases.CACompartimentosBD;
import clh.inspecciones.com.inspecciones_v2.Clases.DetalleInspeccionBD;
import clh.inspecciones.com.inspecciones_v2.R;
import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultadoInspeccionFragment extends Fragment {


    private Realm realm;

    private CheckBox cbInspeccionada;
    private CheckBox cbFavorable;
    private CheckBox cbDesfavorable;
    private EditText etFechaDesfavorable;
    private CheckBox cbBloqueo;
    private EditText etFechaBloqueo;
    private CheckBox cbRevisda;
    private EditText etFechaRevisada;
    private EditText etComentarios;
    private String user;
    private String pass;
    private String inspeccion;
    private DetalleInspeccionBD detalleInspeccionBD;
    private String fecha_desfavorable;
    private String comentarios;
    private Date fechaDesfavorableP;
    private String fecha_revisada;
    private String fecha_bloqueo;
    private Date fechaRevisadaP;
    private Date fechaBloqueoP;

    private SimpleDateFormat parseador = new SimpleDateFormat("dd-MM-yyyy");


    public ResultadoInspeccionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resultado_inspeccion, container, false);

        cbInspeccionada = (CheckBox)view.findViewById(R.id.cb_inspeccionada);
        cbFavorable = (CheckBox)view.findViewById(R.id.cb_favorable);
        cbDesfavorable = (CheckBox)view.findViewById(R.id.cb_desfavorable);
        etFechaDesfavorable = (EditText)view.findViewById(R.id.et_fechadesfavorable);
        cbBloqueo = (CheckBox)view.findViewById(R.id.cb_bloqueo);
        etFechaBloqueo = (EditText)view.findViewById(R.id.et_fechabloqueo);
        cbRevisda = (CheckBox)view.findViewById(R.id.cb_revisado);
        etFechaRevisada = (EditText) view.findViewById(R.id.et_fecha_revisado);
        etComentarios = (EditText)view.findViewById(R.id.comentarios);

        etFechaDesfavorable.setEnabled(cbDesfavorable.isChecked());
        etFechaBloqueo.setEnabled(cbBloqueo.isChecked());
        etFechaRevisada.setEnabled(cbRevisda.isChecked());

        //comentarios = etComentarios.getText();

        realm = Realm.getDefaultInstance();



        // Inflate the layout for this fragment
        return view;
    }

    public void renderResultadoInspeccion(String user, String pass, String inspeccion){
        this.user = user;
        this.pass = pass;
        this.inspeccion = inspeccion;

        cbInspeccionada.setChecked(true);
        cbFavorable.setChecked(true);
        cbRevisda.setChecked(false);
        cbBloqueo.setChecked(false);

    }

    public void guardar(String user, String pass, String inspeccion){
        this.user = user;
        this.pass = pass;
        this.inspeccion = inspeccion;
        this.fecha_desfavorable = etFechaDesfavorable.getText().toString();
        this.fecha_revisada = etFechaRevisada.getText().toString();
        this.fecha_bloqueo = etFechaBloqueo.getText().toString();

        try {
            this.fechaDesfavorableP = parseador.parse(this.fecha_desfavorable);
            this.fechaRevisadaP = parseador.parse(this.fecha_revisada);
            this.fechaBloqueoP = parseador.parse(this.fecha_bloqueo);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        //fecha_desfavorable
        //Toast.makeText(getActivity(), "Can: " + this.cantidad.get(0) + " Can: " + this.cantidad.get(1) + " Can: " + this.cantidad.get(2) + " Can: " + this.cantidad.get(3) + " Can: " + this.cantidad.get(4) + " Can: " + this.cantidad.get(5), Toast.LENGTH_LONG).show();
                detalleInspeccionBD = realm.where(DetalleInspeccionBD.class).equalTo("inspeccion", inspeccion).findFirst();
                //compartimentosBD = realm.where(CACompartimentosBD.class).equalTo("cod_compartimento", compartimentos.get(i).intValue()).findFirst(); //("cod_compartimento", compartimentos.get(i)).findFirst();
                realm.beginTransaction();
                detalleInspeccionBD.setInspeccionada(cbInspeccionada.isChecked());
                detalleInspeccionBD.setFavorable(cbFavorable.isChecked());
                detalleInspeccionBD.setDesfavorable(cbDesfavorable.isChecked());
                detalleInspeccionBD.setFechaDesfavorable(this.fechaDesfavorableP);
                detalleInspeccionBD.setRevisado(cbRevisda.isChecked());
                detalleInspeccionBD.setFechaRevisado(this.fechaRevisadaP);
                detalleInspeccionBD.setBloqueo(cbBloqueo.isChecked());
                detalleInspeccionBD.setFechaBloqueo(this.fechaBloqueoP);
                detalleInspeccionBD.setObservaciones(comentarios);
                realm.copyToRealmOrUpdate(detalleInspeccionBD);
                realm.commitTransaction();
                //registrar en BD Online
                //guardarOnLine(user, pass,String.valueOf(compartimentos.get(i)), tags.get(i), String.valueOf(capacidad.get(i)), String.valueOf(cantidad.get(i)), String.valueOf(cumpleCantidad.get(i)), inspeccion);

    }

    public interface dataListener{
    }

}
