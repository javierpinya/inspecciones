package clh.inspecciones.com.inspecciones_v2.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import clh.inspecciones.com.inspecciones_v2.R;

public class ResultadoInspeccionActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private int nuevaInspeccion;
    private String user;
    private String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_inspeccion);

        prefs = getSharedPreferences("preferences", Context.MODE_PRIVATE);

        user = prefs.getString("user", "errorUser");
        pass = prefs.getString("pass", "errorPass");
        nuevaInspeccion = prefs.getInt("nuevaInspeccion", 0);
    }
}
