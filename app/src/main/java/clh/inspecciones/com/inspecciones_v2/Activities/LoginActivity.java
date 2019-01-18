package clh.inspecciones.com.inspecciones_v2.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import clh.inspecciones.com.inspecciones_v2.Fragments.LoginFragment;
import clh.inspecciones.com.inspecciones_v2.R;

public class LoginActivity extends AppCompatActivity implements LoginFragment.loginOk{

    /*
    Esta es la acitivity principal.
    Tiene asociada el fragment LoginFragment.
    Los layouts asociados son activity_login.xml y fragment_login.xml
    Además, se llama a VolleySingleton para iniciar sesion en el servidor web
     */

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        prefs = getSharedPreferences("preferences", Context.MODE_PRIVATE);

    }

    @Override
    public void loginOk(String usuario, String password, String rutaFoto, String nombre, String puesto, String correo, String movil) {
        saveOnPreferences(usuario, password, rutaFoto, nombre, puesto, correo, movil);
        Intent intent = new Intent(this, MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("usuario", usuario);
        intent.putExtra("password", password);
        startActivity(intent);
    }

    private void saveOnPreferences(String user, String password, String rutaFoto, String nombre, String puesto, String correo, String movil) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("user", user);
            editor.putString("pass", password);
            editor.putString("rutaFoto", rutaFoto);
        editor.putString("nombre", nombre);
        editor.putString("puesto", puesto);
        editor.putString("correo", correo);
        editor.putString("movil", movil);
            editor.commit();
            editor.apply();
    }
}
