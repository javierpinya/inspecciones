package clh.inspecciones.com.inspecciones_v2.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/*
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
*/
import clh.inspecciones.com.inspecciones_v2.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private String user;
    private String pass;
    private String rutaFoto;
    private String nombre;
    private String correo;
    private String movil;

    private TextView tvNombre;
    private TextView tvPuesto;
    private TextView tvCorreo;
    private TextView tvMovil;
    //private GraphView graph;

    private CircleImageView fotoPerfil;
    private Bitmap bm;
    private String puesto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvNombre = findViewById(R.id.profileActivityTvName);
        tvPuesto = findViewById(R.id.profileActivityTvPuesto);
        tvCorreo = findViewById(R.id.profileActivityTvCorreo);
        tvMovil = findViewById(R.id.profileActivityTvMovil);
        fotoPerfil = findViewById(R.id.profileActivityImage);
      //  graph = findViewById(R.id.graph);

        prefs = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        user = prefs.getString("user", "errorUser");
        pass = prefs.getString("pass", "errorPass");
        rutaFoto = prefs.getString("rutaFoto", "errorRutaFoto");
        nombre = prefs.getString("nombre", "errorNombre");
        puesto = prefs.getString("puesto", "errorPuesto");
        correo = prefs.getString("correo", "errorCorreo");
        movil = prefs.getString("movil", "errorMovil");
/*

actualizar version de compilación para que funcione
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });

        // styling
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });

        series.setSpacing(50);

        // draw values on top
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);
        //series.setValuesOnTopSize(50);

        graph.addSeries(series);
*/

        tvNombre.setText(nombre);
        tvPuesto.setText(puesto);
        tvCorreo.setText(correo);
        tvMovil.setText(movil);

        bm = BitmapFactory.decodeFile(rutaFoto);
        fotoPerfil.setImageBitmap(bm);

    }

}
