package clh.inspecciones.com.inspecciones_v2.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import clh.inspecciones.com.inspecciones_v2.Clases.CATractoraBD;
import clh.inspecciones.com.inspecciones_v2.Fragments.ControlAccesoResultadoTractoraFragment;
import clh.inspecciones.com.inspecciones_v2.R;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class ControlAccesoResultadoTractoraActivity extends AppCompatActivity implements ControlAccesoResultadoTractoraFragment.dataListener {

    private String tractora;
    private int tipo;
    public List<CATractoraBD> tractoraList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_acceso_resultado_tractora);
        if(getIntent().getExtras()!= null){
            tractora = getIntent().getStringExtra("vehiculo");
            getTractoraIntent(tractora);

        }

    }

    @Override
    public void getTractoraIntent(String tractora) {
        ControlAccesoResultadoTractoraFragment controlAccesoResultadoTractoraFragment = (ControlAccesoResultadoTractoraFragment) getSupportFragmentManager().findFragmentById(R.id.ControlAccesoResultadoTractoraFragment);
        controlAccesoResultadoTractoraFragment.renderTractora(tractora);
    }
}
