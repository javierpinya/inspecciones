package clh.inspecciones.com.inspecciones_v2.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import clh.inspecciones.com.inspecciones_v2.Clases.DetalleInspeccionBD;
import clh.inspecciones.com.inspecciones_v2.Fragments.CabeceraInspeccionFragment;
import clh.inspecciones.com.inspecciones_v2.Fragments.DetalleInspeccionFragment;
import clh.inspecciones.com.inspecciones_v2.R;

public class DetalleInspeccionActivity extends AppCompatActivity implements CabeceraInspeccionFragment.dataListener, DetalleInspeccionFragment.dataListener {

    private String tractora;
    private String cisterna;
    private String conductor;
    private String t_rigido;
    private String tipo_inspeccion;
    private DetalleInspeccionBD detalleInspeccionBD;
    private String inspeccion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_inspeccion);
        if(getIntent().getExtras()!= null) {
            tractora = getIntent().getStringExtra("tractora").trim();
            cisterna = getIntent().getStringExtra("cisterna").trim();
            conductor = getIntent().getStringExtra("conductor").trim();
            t_rigido = getIntent().getStringExtra("t_rigido").trim();
            tipo_inspeccion = getIntent().getStringExtra("tipo_inspeccion").trim();
            datosIntent(tractora,cisterna,conductor,t_rigido,tipo_inspeccion);
        }
    }

    @Override
    public void datosIntent(String tractora, String cisterna, String conductor, String t_rigido, String tipo_inspeccion) {
        CabeceraInspeccionFragment cabeceraInspeccionFragment = (CabeceraInspeccionFragment)getSupportFragmentManager().findFragmentById(R.id.CabeceraInspeccionFragment);
        cabeceraInspeccionFragment.crearInspeccionBD(tractora,cisterna,conductor,t_rigido,tipo_inspeccion);
    }

    @Override
    public void obtenerInspeccion(String inspeccion, String Instalacion, String albaran, String transportista, String tabla_calibracion) {
        DetalleInspeccionFragment detalleInspeccionFragment = (DetalleInspeccionFragment)getSupportFragmentManager().findFragmentById(R.id.DetalleInspeccionFragment);
        detalleInspeccionFragment.renderText(inspeccion, Instalacion, albaran, transportista, tabla_calibracion);
    }

    @Override
    public void continuar(String inspeccion, String matricula) {
        Intent intent = new Intent();
        intent.setClass(DetalleInspeccionActivity.this, CompartimentosActivity.class);
        intent.putExtra("inspeccion", inspeccion);
        intent.putExtra("matricula", matricula);
        startActivity(intent);
    }

}
