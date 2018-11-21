package clh.inspecciones.com.inspecciones_v2.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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
    private String tipoComponente;    //R - rigido, T-tractora, C-cisterna
    private String tipoInspeccion;
    private String tipoVehiculo;
    private String tractora;
    private String cisterna;
    private String conductor;
    private List<Integer> compartimentos;
    private List<String> tags;
    private List<Integer> capacidad;
    private String respuesta;
    private SharedPreferences prefs;
    private String user;
    private String pass;
    private int nuevaInspeccion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_acceso_checking);
        vehiculos = new ArrayList<String>();
        compartimentos = new ArrayList<>();
        tags = new ArrayList<>();
        capacidad = new ArrayList<>();
        prefs = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        user = prefs.getString("user", "errorUser");
        pass = prefs.getString("pass", "errorPass");
        nuevaInspeccion = prefs.getInt("nuevaInspeccion", 0);


        if(getIntent().getExtras()!= null){
            tipoVehiculo = getIntent().getStringExtra("tipoVehiculo").trim();
            tipoComponente = getIntent().getStringExtra("tipoComponente").trim();
            tipoInspeccion = getIntent().getStringExtra("tipoInspeccion").trim();
            tractora=getIntent().getStringExtra("tractora").trim();
            cisterna=getIntent().getStringExtra("cisterna").trim();
            conductor=getIntent().getStringExtra("conductor").trim();
            vehiculos.add(tractora);
            if (tipoVehiculo.equals("1")) {
                vehiculos.add(cisterna);
            }
            //vehiculos.add(conductor);
            datos_intent(vehiculos, tipoVehiculo, tipoComponente, user, pass, nuevaInspeccion);
        }

        /*
        El siguiente paso es hacer el onitemclicklistener sobre cada elemento,
        conectarse a la bbdd online de control de acceso para sus datos,
        guardarlos en una base de datos realm y mostrarlos en pantalla.
         */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_siguiente, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_siguiente1:
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
            intent.putExtra("tipoComponente", tipoComponente);
            intent.putExtra("tipoInspeccion", tipoInspeccion);
            intent.setClass(this,DetalleInspeccionActivity.class);
            startActivity(intent);
    }

    @Override
    public void itemPulsado(String matVehiculo, String tipoComponente) {
        renderizar(tipoComponente);
    }



    public void datos_intent(List<String> datos, String tipoVehiculo, String tipoComponente, String user, String pass, int nuevaInspeccion) {
        ControlAccesoCheckingFragment controlAccesoCheckingFragment = (ControlAccesoCheckingFragment)getSupportFragmentManager().findFragmentById(R.id.ControlAccesoCheckingFragment);
        controlAccesoCheckingFragment.renderText(datos, tipoVehiculo, tipoComponente, user, pass, nuevaInspeccion);
    }

    @Override
    public void inspecciones() {
        Intent intent = new Intent();
        intent.putExtra("tractora", vehiculos.get(0));
        intent.putExtra("cisterna", vehiculos.get(1));
        intent.putExtra("conductor", vehiculos.get(2));
        intent.putExtra("tipoInspeccion", tipoInspeccion);
        intent.putExtra("tipoComponente", tipoComponente);
        intent.setClass(this,DetalleInspeccionActivity.class);
        startActivity(intent);
    }

    public void renderizar(String tipoComponente){
        ControlAccesoResultadoVehiculoFragment controlAccesoResultadoVehiculoFragment = (ControlAccesoResultadoVehiculoFragment)getSupportFragmentManager().findFragmentById(R.id.ControlAccesoResultadoVehiculoFragment);
        controlAccesoResultadoVehiculoFragment.renderVehiculo(tipoComponente);
    }


}
