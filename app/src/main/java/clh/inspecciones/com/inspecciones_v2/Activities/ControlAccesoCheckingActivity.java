package clh.inspecciones.com.inspecciones_v2.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import clh.inspecciones.com.inspecciones_v2.Fragments.ControlAccesoCheckingFragment;
import clh.inspecciones.com.inspecciones_v2.R;

public class ControlAccesoCheckingActivity extends AppCompatActivity implements ControlAccesoCheckingFragment.dataListener {

    /*
    Lanzada desde identificaciónVehiculoActivity
    Fragments: ControlAccesoCheckingFragment
    Layouts: activity_control_acceso_checking y fragment_control_acceso_checking.xml
     */

    private List<String> vehiculos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_acceso_checking);
        vehiculos = new ArrayList<String>();
        if(getIntent().getExtras()!= null){
            vehiculos.add(getIntent().getStringExtra("tractora").trim().toString());
            vehiculos.add("AA0001A");   //(getIntent().getStringExtra("cisterna"));
            vehiculos.add("1010000"); //getIntent().getStringExtra("conductor"));
          //  recibirDatos(vehiculos);
            datos_intent(vehiculos);
        }

        /*
        El siguiente paso es hacer el onitemclicklistener sobre cada elemento,
        conectarse a la bbdd online de control de acceso para sus datos,
        guardarlos en una base de datos realm y mostrarlos en pantalla.
         */
    }


    @Override
    public void itemPulsado(String vehiculo, int position) {
        Intent intent = new Intent();
        intent.putExtra("vehiculo", vehiculo);
        if(position == 0){
            intent.setClass(this,ControlAccesoResultadoTractoraActivity.class);
        }else if (position == 1){
            intent.setClass(this,ControlAccesoResultadoCisternaActivity.class);
        }else{
            intent.setClass(this,ControlAccesoResultadoConductorActivity.class);
        }

        startActivity(intent);
    }

    @Override
    public void datos_intent(List<String> datos) {
        ControlAccesoCheckingFragment controlAccesoCheckingFragment = (ControlAccesoCheckingFragment)getSupportFragmentManager().findFragmentById(R.id.ControlAccesoCheckingFragment);
        controlAccesoCheckingFragment.renderText(datos);
    }


}
