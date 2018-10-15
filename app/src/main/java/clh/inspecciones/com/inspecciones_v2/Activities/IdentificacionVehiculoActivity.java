package clh.inspecciones.com.inspecciones_v2.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import clh.inspecciones.com.inspecciones_v2.Clases.IdentificacionVehiculoClass;
import clh.inspecciones.com.inspecciones_v2.Fragments.IdentificacionVehiculoBusquedaFragment;
import clh.inspecciones.com.inspecciones_v2.Fragments.IdentificacionVehiculoResultadoFragment;
import clh.inspecciones.com.inspecciones_v2.R;

public class IdentificacionVehiculoActivity extends AppCompatActivity implements IdentificacionVehiculoBusquedaFragment.DataListener, IdentificacionVehiculoResultadoFragment.EnviarData {

    private String tipo_vehiculo;
    private String tipo_inspeccion;
    private String t_rigido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identificacion_vehiculo);
        if(getIntent().getExtras()!= null){
            tipo_vehiculo = getIntent().getStringExtra("tipo_vehiculo").trim().toString();
            tipo_inspeccion = getIntent().getStringExtra("tipo_inspeccion").trim().toString();
            t_rigido = getIntent().getStringExtra("t_rigido").trim();

            enviarDatos(tipo_vehiculo,tipo_inspeccion,t_rigido);
        }
    }

    @Override
    public void buscarVehiculos(ArrayList<IdentificacionVehiculoClass> listaVehiculos) {
        IdentificacionVehiculoResultadoFragment identificacionVehiculoResultadoFragment = (IdentificacionVehiculoResultadoFragment) getSupportFragmentManager().findFragmentById(R.id.IdentificacionVehiculoResultadoFragment);
        identificacionVehiculoResultadoFragment.renderText(listaVehiculos);
    }

    @Override
    public void enviarDatos(String tipo_vehiculo, String tipo_inspeccion, String t_rigido) {
        IdentificacionVehiculoBusquedaFragment identificacionVehiculoBusquedaFragment = (IdentificacionVehiculoBusquedaFragment) getSupportFragmentManager().findFragmentById(R.id.IdentificacionVehiculoBusquedaFragment);
        identificacionVehiculoBusquedaFragment.recibir_intent(tipo_vehiculo,tipo_inspeccion,t_rigido);
    }

    @Override
    public void enviar(String tractora, String cisterna, String conductor) {
        Intent intent = new Intent(this, ControlAccesoCheckingActivity.class);
        intent.putExtra("cisterna", cisterna);
        intent.putExtra("tractora", tractora);
        intent.putExtra("conductor", conductor);
        intent.putExtra("tipo_vehiculo", tipo_vehiculo);
        intent.putExtra("tipo_inspeccion", tipo_inspeccion);
        intent.putExtra("t_rigido", t_rigido);
        startActivity(intent);

    }


}
