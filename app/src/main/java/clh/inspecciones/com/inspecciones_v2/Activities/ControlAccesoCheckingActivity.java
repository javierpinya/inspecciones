package clh.inspecciones.com.inspecciones_v2.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import clh.inspecciones.com.inspecciones_v2.Fragments.ControlAccesoCheckingFragment;
import clh.inspecciones.com.inspecciones_v2.Fragments.ControlAccesoResultadoVehiculoFragment;
import clh.inspecciones.com.inspecciones_v2.R;


public class ControlAccesoCheckingActivity extends AppCompatActivity implements ControlAccesoCheckingFragment.dataListener, ControlAccesoResultadoVehiculoFragment.dataListener {

    /*
    Lanzada desde identificaciónVehiculoActivity
    Fragments: ControlAccesoCheckingFragment
    Layouts: activity_control_acceso_checking y fragment_control_acceso_checking_listView_listView.xml
     */

    private List<String> vehiculos;
    private String tipoTractora;
    private String tipoInspeccion;
    private String tipoVehiculo;
    private String tractora;
    private String cisterna;
    private String conductor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_acceso_checking);
        vehiculos = new ArrayList<String>();
        if(getIntent().getExtras()!= null){
            tractora=getIntent().getStringExtra("tractora").trim();
            cisterna=getIntent().getStringExtra("cisterna").trim();
            conductor=getIntent().getStringExtra("conductor").trim();
            vehiculos.add(tractora);
            vehiculos.add(cisterna);
            vehiculos.add(conductor);
            tipoVehiculo = getIntent().getStringExtra("tipoVehiculo").trim();
            tipoTractora = getIntent().getStringExtra("tipoTractora").trim();
            tipoInspeccion = getIntent().getStringExtra("tipoInspeccion").trim();
            datos_intent(vehiculos);
        }

        /*
        El siguiente paso es hacer el onitemclicklistener sobre cada elemento,
        conectarse a la bbdd online de control de acceso para sus datos,
        guardarlos en una base de datos realm y mostrarlos en pantalla.
         */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_siguiente:
                siguiente();
                return true;
                /*
            case R.id.menu_logout:
                logOut();
                return true;
            case R.id.menu_forget_logout:
                removeSharedPreferences();
                logOut();
                return true;
                */
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void siguiente() {
        Intent intent = new Intent();
        intent.putExtra("tractora", tractora);
        intent.putExtra("cisterna", cisterna);
        intent.putExtra("conductor", conductor);
        intent.putExtra("tipoTractora", tipoTractora);
        intent.putExtra("tipoInspeccion", tipoInspeccion);
        intent.setClass(this,DetalleInspeccionActivity.class);
        startActivity(intent);
    }

    @Override
    public void itemPulsado(String matVehiculo, int position) {


        /*
        Intent intent = new Intent();
        intent.putExtra("matVehiculo", matVehiculo);
        intent.putExtra("tipoTractora", tipoTractora);
        intent.putExtra("tipoInspeccion", tipoInspeccion);
        if(position == 0){
            if (tipoTractora.equals("T")){
                intent.setClass(this,ControlAccesoResultadoTractoraActivity.class);
            }else{
                intent.setClass(this,ControlAccesoResultadoRigidoActivity.class);
            }

        }else if (position == 1){
            intent.setClass(this,ControlAccesoResultadoVehiculoActivity.class);
        }else{
            intent.setClass(this,ControlAccesoResultadoConductorActivity.class);
        }

        startActivity(intent);
        */
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
        intent.putExtra("tipoInspeccion", tipoInspeccion);
        intent.putExtra("tipoTractora", tipoTractora);
        intent.setClass(this,DetalleInspeccionActivity.class);
        startActivity(intent);
    }

    public void renderizar(String matricula, String tipoVehiculo){
       // ControlAccesoResultadoVehiculoFragment controlAccesoResultadoVehiculoFragment = (ControlAccesoResultadoVehiculoFragment)getSupportFragmentManager().findFragmentById(R.id.Con)
    }


    @Override
    public void getVehiculoIntent(String rigido) {

    }
}
