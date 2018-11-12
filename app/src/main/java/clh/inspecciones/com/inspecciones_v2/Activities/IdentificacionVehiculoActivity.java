package clh.inspecciones.com.inspecciones_v2.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import clh.inspecciones.com.inspecciones_v2.Clases.IdentificacionVehiculoClass;
import clh.inspecciones.com.inspecciones_v2.Fragments.IdentificacionVehiculoBusquedaFragment;
import clh.inspecciones.com.inspecciones_v2.Fragments.IdentificacionVehiculoResultadoFragment;
import clh.inspecciones.com.inspecciones_v2.R;

public class IdentificacionVehiculoActivity extends AppCompatActivity implements IdentificacionVehiculoBusquedaFragment.DataListener, IdentificacionVehiculoResultadoFragment.EnviarData {

    private String tipoVehiculo;
    private String tipoInspeccion;
    private String tipoTractora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identificacion_vehiculo);
        if(getIntent().getExtras()!= null){
            tipoVehiculo = getIntent().getStringExtra("tipoVehiculo").trim().toString();
            tipoInspeccion = getIntent().getStringExtra("tipoInspeccion").trim().toString();
            tipoTractora = getIntent().getStringExtra("tipoTractora").trim();

            enviarDatos(tipoVehiculo, tipoInspeccion, tipoTractora);
        }
    }

    @Override
    public void buscarVehiculos(ArrayList<IdentificacionVehiculoClass> listaVehiculos) {
        IdentificacionVehiculoResultadoFragment identificacionVehiculoResultadoFragment = (IdentificacionVehiculoResultadoFragment) getSupportFragmentManager().findFragmentById(R.id.IdentificacionVehiculoResultadoFragment);
        identificacionVehiculoResultadoFragment.renderText(listaVehiculos);
    }

    @Override
    public void enviarDatos(String tipoVehiculo, String tipoInspeccion, String tipoTractora) {
        IdentificacionVehiculoBusquedaFragment identificacionVehiculoBusquedaFragment = (IdentificacionVehiculoBusquedaFragment) getSupportFragmentManager().findFragmentById(R.id.IdentificacionVehiculoBusquedaFragment);
        identificacionVehiculoBusquedaFragment.recibir_intent(tipoVehiculo,tipoInspeccion,tipoTractora);
    }

    @Override
    public void enviar(String tractora, String cisterna, String conductor) {
        Intent intent = new Intent(this, ControlAccesoCheckingActivity.class);
        intent.putExtra("cisterna", cisterna);
        intent.putExtra("tractora", tractora);
        intent.putExtra("conductor", conductor);
        intent.putExtra("tipoVehiculo", tipoVehiculo);
        intent.putExtra("tipoInspeccion", tipoInspeccion);
        intent.putExtra("tipoTractora", tipoTractora);
        startActivity(intent);
    }


}
