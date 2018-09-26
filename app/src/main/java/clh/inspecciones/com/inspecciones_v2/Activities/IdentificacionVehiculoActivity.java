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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identificacion_vehiculo);
    }

    @Override
    public void buscarVehiculos(ArrayList<IdentificacionVehiculoClass> listaVehiculos) {
        IdentificacionVehiculoResultadoFragment identificacionVehiculoResultadoFragment = (IdentificacionVehiculoResultadoFragment) getSupportFragmentManager().findFragmentById(R.id.IdentificacionVehiculoResultadoFragment);
        identificacionVehiculoResultadoFragment.renderText(listaVehiculos);
    }

    @Override
    public void enviar(String tractora, String cisterna, String conductor) {
        Intent intent = new Intent(this, ControlAccesoCheckingActivity.class);
        intent.putExtra("cisterna", cisterna);
        intent.putExtra("tractora", tractora);
        intent.putExtra("conductor", conductor);
        startActivity(intent);

    }


}
