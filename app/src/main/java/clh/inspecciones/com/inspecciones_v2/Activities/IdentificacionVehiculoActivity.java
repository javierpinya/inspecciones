package clh.inspecciones.com.inspecciones_v2.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import clh.inspecciones.com.inspecciones_v2.Clases.CACisternaBD;
import clh.inspecciones.com.inspecciones_v2.Clases.CACompartimentosBD;
import clh.inspecciones.com.inspecciones_v2.Clases.CARigidoBD;
import clh.inspecciones.com.inspecciones_v2.Clases.CATractoraBD;
import clh.inspecciones.com.inspecciones_v2.Clases.IdentificacionVehiculoClass;
import clh.inspecciones.com.inspecciones_v2.Fragments.IdentificacionVehiculoBusquedaFragment;
import clh.inspecciones.com.inspecciones_v2.Fragments.IdentificacionVehiculoResultadoFragment;
import clh.inspecciones.com.inspecciones_v2.R;
import io.realm.Realm;

public class IdentificacionVehiculoActivity extends AppCompatActivity implements IdentificacionVehiculoBusquedaFragment.DataListener, IdentificacionVehiculoResultadoFragment.EnviarData {

    private String tipoVehiculo;
    private String tipoInspeccion;
    private String tipoComponente;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identificacion_vehiculo);
        if(getIntent().getExtras()!= null){
            tipoVehiculo = getIntent().getStringExtra("tipoVehiculo").trim().toString();
            tipoInspeccion = getIntent().getStringExtra("tipoInspeccion").trim().toString();
            tipoComponente = getIntent().getStringExtra("tipoComponente").trim();



            enviarDatos(tipoVehiculo, tipoInspeccion, tipoComponente);
        }
    }

    @Override
    public void buscarVehiculos(ArrayList<IdentificacionVehiculoClass> listaVehiculos, String tipoVehiculo) {
        IdentificacionVehiculoResultadoFragment identificacionVehiculoResultadoFragment = (IdentificacionVehiculoResultadoFragment) getSupportFragmentManager().findFragmentById(R.id.IdentificacionVehiculoResultadoFragment);
        identificacionVehiculoResultadoFragment.renderText(listaVehiculos);
    }

    @Override
    public void enviarDatos(String tipoVehiculo, String tipoInspeccion, String tipoComponente) {
        IdentificacionVehiculoBusquedaFragment identificacionVehiculoBusquedaFragment = (IdentificacionVehiculoBusquedaFragment) getSupportFragmentManager().findFragmentById(R.id.IdentificacionVehiculoBusquedaFragment);
        identificacionVehiculoBusquedaFragment.recibir_intent(tipoVehiculo,tipoInspeccion, tipoComponente);
    }

    @Override
    public void enviar(String tractora, String cisterna, String conductor) {
        realm = Realm.getDefaultInstance();
        if(realm.isEmpty() == false){
            realm.beginTransaction();
            realm.delete(CARigidoBD.class);
            realm.delete(CATractoraBD.class);
            realm.delete(CACisternaBD.class);
            realm.delete(CACompartimentosBD.class);
            realm.commitTransaction();
        }
        Intent intent = new Intent(this, ControlAccesoCheckingActivity.class);
        intent.putExtra("cisterna", cisterna);
        intent.putExtra("tractora", tractora);
        intent.putExtra("conductor", conductor);
        intent.putExtra("tipoVehiculo", tipoVehiculo);
        intent.putExtra("tipoInspeccion", tipoInspeccion);
        intent.putExtra("tipoComponente", tipoComponente);
        startActivity(intent);
    }


}
