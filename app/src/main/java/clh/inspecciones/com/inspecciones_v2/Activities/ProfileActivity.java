package clh.inspecciones.com.inspecciones_v2.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

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

        prefs = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        user = prefs.getString("user", "errorUser");
        pass = prefs.getString("pass", "errorPass");
        rutaFoto = prefs.getString("rutaFoto", "errorRutaFoto");
        nombre = prefs.getString("nombre", "errorNombre");
        puesto = prefs.getString("puesto", "errorPuesto");
        correo = prefs.getString("correo", "errorCorreo");
        movil = prefs.getString("movil", "errorMovil");


        tvNombre.setText(nombre);
        tvPuesto.setText(puesto);
        tvCorreo.setText(correo);
        tvMovil.setText(movil);

        bm = BitmapFactory.decodeFile(rutaFoto);
        fotoPerfil.setImageBitmap(bm);

    }

}
