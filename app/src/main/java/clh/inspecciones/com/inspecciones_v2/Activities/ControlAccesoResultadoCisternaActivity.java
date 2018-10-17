package clh.inspecciones.com.inspecciones_v2.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import clh.inspecciones.com.inspecciones_v2.Clases.CACisternaBD;
import clh.inspecciones.com.inspecciones_v2.Clases.CATractoraBD;
import clh.inspecciones.com.inspecciones_v2.Fragments.ControlAccesoResultadoCisternaFragment;
import clh.inspecciones.com.inspecciones_v2.R;

public class ControlAccesoResultadoCisternaActivity extends AppCompatActivity implements ControlAccesoResultadoCisternaFragment.dataListener {

    private String cisterna;
    private String tipo_inspeccion;
    private String t_rigido;
    public List<CACisternaBD> cisternaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_acceso_resultado_cisterna);
        if(getIntent().getExtras()!= null){
            cisterna = getIntent().getStringExtra("vehiculo").trim();
            tipo_inspeccion = getIntent().getStringExtra("tipo_inspeccion").trim();
            t_rigido = getIntent().getStringExtra("t_rigido").trim();
            getCisternaIntent(cisterna);

        }
    }

    @Override
    public void getCisternaIntent(String cisterna) {
        ControlAccesoResultadoCisternaFragment controlAccesoResultadoCisternaFragment = (ControlAccesoResultadoCisternaFragment) getSupportFragmentManager().findFragmentById(R.id.ControlAccesoResultadoCisternaFragment);
        controlAccesoResultadoCisternaFragment.renderCisterna(cisterna);

    }
}
