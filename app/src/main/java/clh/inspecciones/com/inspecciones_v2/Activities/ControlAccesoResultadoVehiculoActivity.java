package clh.inspecciones.com.inspecciones_v2.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import clh.inspecciones.com.inspecciones_v2.Clases.CACisternaBD;
import clh.inspecciones.com.inspecciones_v2.Fragments.ControlAccesoResultadoCisternaFragment;
import clh.inspecciones.com.inspecciones_v2.Fragments.ControlAccesoResultadoVehiculoFragment;
import clh.inspecciones.com.inspecciones_v2.R;

public class ControlAccesoResultadoVehiculoActivity extends AppCompatActivity implements ControlAccesoResultadoVehiculoFragment.dataListener {

    private String matVehiculo;
    private String tipo_inspeccion;
    private String tipoVehiculo;
    public List<CACisternaBD> cisternaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_acceso_resultado_cisterna);
        if(getIntent().getExtras()!= null){
            matVehiculo = getIntent().getStringExtra("vehiculo").trim();
            tipo_inspeccion = getIntent().getStringExtra("tipo_inspeccion").trim();
            tipoVehiculo = getIntent().getStringExtra("tipoVehiculo").trim();
           // getVehiculoIntent(matVehiculo, tipoVehiculo);

        }
    }
/*
    @Override
    public void getVehiculoIntent(String matVehiculo, String tipoVehiculo) {
        ControlAccesoResultadoVehiculoFragment controlAccesoResultadoVehiculoFragment = (ControlAccesoResultadoVehiculoFragment) getSupportFragmentManager().findFragmentById(R.id.lv_controlaccesoresultadovehiculo);
        controlAccesoResultadoVehiculoFragment.renderVehiculo(matVehiculo, tipoVehiculo);

    }
*/
    @Override
    public void getVehiculoIntent(String rigido) {

    }
}
