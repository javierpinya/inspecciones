package clh.inspecciones.com.inspecciones_v2.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import clh.inspecciones.com.inspecciones_v2.Fragments.CompartimentosFragment;
import clh.inspecciones.com.inspecciones_v2.R;

public class CompartimentosActivity extends AppCompatActivity implements CompartimentosFragment.dataListener {

    private String matricula;
    private String inspeccion;
    private SharedPreferences prefs;
    private String user;
    private String pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compartimentos);
        prefs = getSharedPreferences("preferences", Context.MODE_PRIVATE);

        user = prefs.getString("user", "errorUser");
        pass = prefs.getString("pass", "errorPass");
        if(getIntent().getExtras()!= null) {
            matricula = getIntent().getStringExtra("matricula").trim();
            inspeccion = getIntent().getStringExtra("inspeccion").trim();
        }
        enviarMatricula(matricula, inspeccion);
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
                guardar(user, pass);
                return true;
            case R.id.menu_siguiente1:
                siguiente();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void guardar(String user, String pass){
        CompartimentosFragment compartimentosFragment = (CompartimentosFragment)getSupportFragmentManager().findFragmentById(R.id.CompartimentosFragment);
        compartimentosFragment.guardar(user,pass);
    }

    public void siguiente(){

    }

    public void enviarMatricula(String matricula, String inspeccion) {
        CompartimentosFragment compartimentosFragment = (CompartimentosFragment)getSupportFragmentManager().findFragmentById(R.id.CompartimentosFragment);
        compartimentosFragment.enviarMatricula(matricula, inspeccion);
    }

    @Override
    public void volver() {

    }

    @Override
    public void elegirCompartimento(int compartimento) {

    }
}
