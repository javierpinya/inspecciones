package clh.inspecciones.com.inspecciones_v2.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import clh.inspecciones.com.inspecciones_v2.Fragments.AltaNuevaFragment;
import clh.inspecciones.com.inspecciones_v2.R;

public class AltaNuevaActivity extends AppCompatActivity implements AltaNuevaFragment.AltaNuevaListener{

    /*
    Viene precedida de MenuActivity
    En esta activity elegimos el tipo de vehículo a inspeccionar
    AltaNuevaFragment.java es el fragment asociado
    activity_alta_nueva.xml y fragment_alta_nueva.xml
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_nueva);
    }

    @Override
    public void altaNueva(String t_vehiculo, String t_inspeccion, String t_rigido) {
        Intent intent = new Intent(this, IdentificacionVehiculoActivity.class);
        intent.putExtra("tipo_vehiculo", t_vehiculo);
        intent.putExtra("tipo_inspeccion", t_inspeccion);
        intent.putExtra("t_rigido", t_rigido);
        startActivity(intent);
    }

}
