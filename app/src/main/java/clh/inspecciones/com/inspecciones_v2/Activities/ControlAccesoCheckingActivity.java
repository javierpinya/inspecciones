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
    Layouts: activity_control_acceso_checking y fragment_control_acceso_checking_listView_listView.xml
     */

    private List<String> vehiculos;
    private String t_rigido;
    private String tipo_inspeccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_acceso_checking);
        vehiculos = new ArrayList<String>();
        if(getIntent().getExtras()!= null){
            vehiculos.add(getIntent().getStringExtra("tractora").trim().toString());
            vehiculos.add(getIntent().getStringExtra("cisterna").trim().toString());   //(getIntent().getStringExtra("cisterna"));
            vehiculos.add(getIntent().getStringExtra("conductor").trim().toString()); //getIntent().getStringExtra("conductor"));
            t_rigido = getIntent().getStringExtra("t_rigido").trim();
            tipo_inspeccion = getIntent().getStringExtra("tipo_inspeccion").trim();
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
        intent.putExtra("t_rigido", t_rigido);
        intent.putExtra("tipo_inspeccion", tipo_inspeccion);
        if(position == 0){
            if (t_rigido.equals("T")){
                Toast.makeText(this,"TRACTORA: " + t_rigido, Toast.LENGTH_SHORT).show();
                intent.setClass(this,ControlAccesoResultadoTractoraActivity.class);
            }else{
                Toast.makeText(this,"RIGIDO: " + t_rigido, Toast.LENGTH_SHORT).show();
                intent.setClass(this,ControlAccesoResultadoRigidoActivity.class);
            }

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

    @Override
    public void inspecciones() {
        Intent intent = new Intent();
        intent.putExtra("tractora", vehiculos.get(0));
        intent.putExtra("cisterna", vehiculos.get(1));
        intent.putExtra("conductor", vehiculos.get(2));
        intent.putExtra("tipo_inspeccion", tipo_inspeccion);
        intent.putExtra("t_rigido", t_rigido);
        startActivity(intent);
    }


}
