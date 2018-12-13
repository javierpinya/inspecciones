package clh.inspecciones.com.inspecciones_v2.Activities;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import clh.inspecciones.com.inspecciones_v2.Clases.DetalleInspeccionBD;
import clh.inspecciones.com.inspecciones_v2.Fragments.CabeceraInspeccionFragment;
import clh.inspecciones.com.inspecciones_v2.R;

public class DetalleInspeccionActivity extends AppCompatActivity implements CabeceraInspeccionFragment.dataListener {

    private String tractora;
    private String cisterna;
    private String conductor;
    private String tipoComponente;
    private String tipoInspeccion;
    private DetalleInspeccionBD detalleInspeccionBD;
    private String inspeccion;
    private List<Integer> compartimentos;
    private List<String> tags;
    private List<Integer> capacidad;
    private Integer numCompartimentos;
    private SharedPreferences prefs;
    private int nuevaInspeccion;
    private String user;
    private String pass;
    private static Boolean guardadoOK;
    private static String matricula;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_inspeccion);
        if(getIntent().getExtras()!= null) {
            tipoComponente = getIntent().getStringExtra("tipoComponente").trim();
            tipoInspeccion = getIntent().getStringExtra("tipoInspeccion").trim();
            conductor = getIntent().getStringExtra("conductor").trim();

            prefs = getSharedPreferences("preferences", Context.MODE_PRIVATE);

            user = prefs.getString("user", "errorUser");
            pass = prefs.getString("pass", "errorPass");

            nuevaInspeccion = prefs.getInt("nuevaInspeccion", 0);
            switch (tipoComponente){
                case "S":
                    tractora = getIntent().getStringExtra("tractora").trim();
                    cisterna = getIntent().getStringExtra("cisterna").trim();
                    break;
                case "R":
                    tractora = getIntent().getStringExtra("tractora").trim();
                    cisterna="XXXXXX";
                    break;
                case "T":
                    tractora = getIntent().getStringExtra("tractora").trim();
                    cisterna="XXXXXX";
                case "C":
                    tractora = "XXXXXX";
                    cisterna = getIntent().getStringExtra("cisterna").trim();
                    break;
            }
            datosIntent(tractora,cisterna,conductor, tipoComponente, tipoInspeccion, user, pass);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_guardar_siguiente, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_guardar:
                CabeceraInspeccionFragment cabeceraInspeccionFragment = (CabeceraInspeccionFragment)getSupportFragmentManager().findFragmentById(R.id.CabeceraInspeccionFragment);
                cabeceraInspeccionFragment.prepararGuardado();
                return true;
            case R.id.menu_siguiente:
                if (this.guardadoOK){
                    Intent intent = new Intent();
                    intent.setClass(DetalleInspeccionActivity.this, CompartimentosActivity.class);
                    intent.putExtra("inspeccion", inspeccion);
                    intent.putExtra("matricula", matricula);
                    startActivity(intent);
                }else{
                    Toast.makeText(this, "Debe guardar primero la inspeccion", Toast.LENGTH_LONG).show();
                }

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void datosIntent(String tractora, String cisterna, String conductor, String t_rigido, String tipo_inspeccion, String user, String pass) {
        CabeceraInspeccionFragment cabeceraInspeccionFragment = (CabeceraInspeccionFragment)getSupportFragmentManager().findFragmentById(R.id.CabeceraInspeccionFragment);
        cabeceraInspeccionFragment.crearInspeccionBD(tractora,cisterna,conductor,t_rigido,tipo_inspeccion, user, pass);
    }


    @Override
    public void guardado(Boolean guardadoOK, String matricula) {
        this.guardadoOK = guardadoOK;
        this.matricula = matricula;
    }

    @Override
    public void obtenerInspeccion(String inspeccion, String Instalacion, String albaran, String transportista, String tabla_calibracion) {

    }

    @Override
    public void continuar(String inspeccion, String matricula) {

    }

}
