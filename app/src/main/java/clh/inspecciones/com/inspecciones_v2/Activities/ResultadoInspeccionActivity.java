package clh.inspecciones.com.inspecciones_v2.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import clh.inspecciones.com.inspecciones_v2.Fragments.ResultadoInspeccionFragment;
import clh.inspecciones.com.inspecciones_v2.R;

public class ResultadoInspeccionActivity extends AppCompatActivity implements ResultadoInspeccionFragment.dataListener{

    private SharedPreferences prefs;
    private String inspeccion;
    private String user;
    private String pass;
    private String matricula;
    private Boolean finalizada=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_inspeccion);

        prefs = getSharedPreferences("preferences", Context.MODE_PRIVATE);

        user = prefs.getString("user", "errorUser");
        pass = prefs.getString("pass", "errorPass");
        if(getIntent().getExtras()!= null) {
            matricula = getIntent().getStringExtra("matricula").trim();
            inspeccion = getIntent().getStringExtra("inspeccion").trim();
        }

        resultadoInspeccion(user, pass, inspeccion);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_siguiente, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_guardar:
                guardar(user, pass, inspeccion);
                return true;
            case R.id.menu_siguiente:
                Toast.makeText(this, "Inspección finalizada", Toast.LENGTH_SHORT).show();
                siguiente(finalizada);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void guardar(String user, String pass, String inspeccion) {
        ResultadoInspeccionFragment resultadoInspeccionFragment = (ResultadoInspeccionFragment)getSupportFragmentManager().findFragmentById(R.id.resultadoinspeccionfragment);
        resultadoInspeccionFragment.guardar(user,pass,inspeccion);
    }

    private void siguiente(Boolean finalizada){
        if(finalizada) {
            Intent intent = new Intent();
            intent.setClass(ResultadoInspeccionActivity.this, MenuActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(this, "La inspección aún no se ha guardado", Toast.LENGTH_LONG).show();
        }
    }

    public void resultadoInspeccion(String user, String pass, String inspeccion){
        ResultadoInspeccionFragment resultadoInspeccionFragment = (ResultadoInspeccionFragment)getSupportFragmentManager().findFragmentById(R.id.resultadoinspeccionfragment);
        resultadoInspeccionFragment.renderResultadoInspeccion(user, pass, inspeccion);
    }

    @Override
    public void guardada() {
        finalizada = true;

    }
}
