package clh.inspecciones.com.inspecciones_v2.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import clh.inspecciones.com.inspecciones_v2.Fragments.CompartimentosFragment;
import clh.inspecciones.com.inspecciones_v2.R;

public class CompartimentosActivity extends AppCompatActivity implements CompartimentosFragment.dataListener {

    private String matricula;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compartimentos);
        if(getIntent().getExtras()!= null) {
            matricula = getIntent().getStringExtra("matricula").trim();
        }
    }


    @Override
    public void enviarMatricula(String matricula) {
        CompartimentosFragment compartimentosFragment = (CompartimentosFragment)getSupportFragmentManager().findFragmentById(R.id.CompartimentosFragment);
        compartimentosFragment.renderCompartimentos(matricula);
    }
}
