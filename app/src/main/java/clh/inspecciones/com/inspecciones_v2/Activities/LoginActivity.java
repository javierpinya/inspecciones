package clh.inspecciones.com.inspecciones_v2.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

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
    public void loginOk(String usuario, String password, boolean sw) {
        saveOnPreferences(usuario,password,sw);
        Intent intent = new Intent(this, MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("usuario", usuario);
        intent.putExtra("password", password);
        startActivity(intent);
    }

    private void saveOnPreferences(String user, String password, boolean sw){
       // if (sw){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("user", user);
            editor.putString("pass", password);
            //editor.putInt("nuevaInspeccion", nuevaInspeccion);
            editor.commit();//6957186 1 adit
            editor.apply();
        //}
    }
}
