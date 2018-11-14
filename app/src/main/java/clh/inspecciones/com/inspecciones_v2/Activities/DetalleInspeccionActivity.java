package clh.inspecciones.com.inspecciones_v2.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import clh.inspecciones.com.inspecciones_v2.Clases.DetalleInspeccionBD;
import clh.inspecciones.com.inspecciones_v2.Fragments.CabeceraInspeccionFragment;
import clh.inspecciones.com.inspecciones_v2.Fragments.DetalleInspeccionFragment;
import clh.inspecciones.com.inspecciones_v2.R;

public class DetalleInspeccionActivity extends AppCompatActivity implements CabeceraInspeccionFragment.dataListener {

    private String tractora;
    private String cisterna;
    private String conductor;
    private String tipoTractora;
    private String tipoInspeccion;
    private DetalleInspeccionBD detalleInspeccionBD;
    private String inspeccion;
    private List<Integer> compartimentos;
    private List<String> tags;
    private List<Integer> capacidad;
    private Integer numCompartimentos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_inspeccion);
        if(getIntent().getExtras()!= null) {
            tractora = getIntent().getStringExtra("tractora").trim();
            cisterna = getIntent().getStringExtra("cisterna").trim();
            conductor = getIntent().getStringExtra("conductor").trim();
            tipoTractora = getIntent().getStringExtra("tipoTractora").trim();
            tipoInspeccion = getIntent().getStringExtra("tipoInspeccion").trim();
            numCompartimentos = getIntent().getIntExtra("numCompartimentos", 0);
            for (int i=0; i<numCompartimentos; i++){
                compartimentos.add(getIntent().getIntExtra("compartimento" + i, 0));
                tags.add(getIntent().getStringExtra("tag" + i));
                capacidad.add(getIntent().getIntExtra("capacidad" + i, 0));
            }
            datosIntent(tractora,cisterna,conductor, tipoTractora, tipoInspeccion);
        }
    }

    @Override
    public void datosIntent(String tractora, String cisterna, String conductor, String t_rigido, String tipo_inspeccion) {
        CabeceraInspeccionFragment cabeceraInspeccionFragment = (CabeceraInspeccionFragment)getSupportFragmentManager().findFragmentById(R.id.CabeceraInspeccionFragment);
        cabeceraInspeccionFragment.crearInspeccionBD(tractora,cisterna,conductor,t_rigido,tipo_inspeccion);
    }

    @Override
    public void obtenerInspeccion(String inspeccion, String Instalacion, String albaran, String transportista, String tabla_calibracion) {

    }

    /*
    @Override
    public void obtenerInspeccion(String inspeccion, String Instalacion, String albaran, String transportista, String tabla_calibracion) {
        DetalleInspeccionFragment detalleInspeccionFragment = (DetalleInspeccionFragment)getSupportFragmentManager().findFragmentById(R.id.DetalleInspeccionFragment);
        detalleInspeccionFragment.renderText(inspeccion, Instalacion, albaran, transportista, tabla_calibracion);
    }*/

    @Override
    public void continuar(String inspeccion, String matricula) {
        Intent intent = new Intent();
        intent.setClass(DetalleInspeccionActivity.this, CompartimentosActivity.class);
        intent.putExtra("inspeccion", inspeccion);
        intent.putExtra("matricula", matricula);
        startActivity(intent);
    }

}
