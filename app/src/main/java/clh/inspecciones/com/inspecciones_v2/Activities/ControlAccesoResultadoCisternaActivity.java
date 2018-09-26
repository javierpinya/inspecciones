package clh.inspecciones.com.inspecciones_v2.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import clh.inspecciones.com.inspecciones_v2.Fragments.ControlAccesoResultadoCisternaFragment;
import clh.inspecciones.com.inspecciones_v2.R;

public class ControlAccesoResultadoCisternaActivity extends AppCompatActivity implements ControlAccesoResultadoCisternaFragment.dataListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_acceso_resultado_cisterna);
    }

    @Override
    public void getCisternaIntent(String cisterna) {
        ControlAccesoResultadoCisternaFragment controlAccesoResultadoTractoraFragment = (ControlAccesoResultadoCisternaFragment) getSupportFragmentManager().findFragmentById(R.id.ControlAccesoResultadoCisternaFragment);
        controlAccesoResultadoTractoraFragment.renderCisterna(cisterna);

    }
}
