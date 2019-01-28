package clh.inspecciones.com.inspecciones_v2.Fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import clh.inspecciones.com.inspecciones_v2.Clases.Login;
import clh.inspecciones.com.inspecciones_v2.R;
import clh.inspecciones.com.inspecciones_v2.SingleTones.VolleySingleton;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;
import static android.support.v4.content.PermissionChecker.checkSelfPermission;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener{

    private Login Login;
    private EditText Usuario;
    private EditText Password;
    private Button ButtonLogin;
    //private Switch swRemember;
    private String User;
    private String Pass;
    private String nombre = "";
    private String json_url = "http://pruebaalumnosandroid.esy.es/inspecciones/login.php";

    private loginOk callback;
    private String foto;
    private String rutaFoto="";
    private String urlPerfil = "http://pruebaalumnosandroid.esy.es/inspecciones/recibir_foto_usuario.php";
    private String correo = "";
    private String movil = "";
    private String puesto = "";
    private boolean validaPermisos;


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (loginOk) context;
        }catch (Exception e){
            throw new ClassCastException(context.toString() + " should implement loginOk");
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        // Inflate the layout for this fragment



        Usuario = view.findViewById(R.id.et_usuario);
        Password = view.findViewById(R.id.et_password);
        //swRemember = view.findViewById(R.id.sw_preferences);
        ButtonLogin = view.findViewById(R.id.btn_login);
        ButtonLogin.setEnabled(true);
        Usuario.setText("");
        Password.setText("");

        if(validaPermisos()){
            validaPermisos=true;
        }else{
            validaPermisos=false;
        }

        ButtonLogin.setOnClickListener(this);




        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                User = Usuario.getText().toString();
                Pass = Password.getText().toString();

                if(login(Pass)){
                    if (validaPermisos) {
                        VolleySingleton.getInstanciaVolley(getContext()).addToRequestqueue(sr);
                    }
                }else{
                    Toast.makeText(getActivity(), "Password no válido", Toast.LENGTH_LONG).show();
                }
        }


    }

    StringRequest sr = new StringRequest(Request.Method.POST, json_url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            obtenerPerfil(User, Pass, urlPerfil);
        }
    },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                }
            }) {
        @Override
        protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<>();
            params.put("user", User);
            params.put("pass", Pass);
            return params;
        }
    };

    private void obtenerPerfil(final String user, final String pass, final String urlPerfil) {
        StringRequest srPerfil = new StringRequest(Request.Method.POST, urlPerfil, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    //Convierto la respuesta, de tipo String, a un JSONObject.
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray json = jsonObject.optJSONArray("foto");
                    JSONArray json2 = jsonObject.optJSONArray("nombre");
                    JSONArray json5 = jsonObject.optJSONArray("puesto");
                    JSONArray json3 = jsonObject.optJSONArray("correo");
                    JSONArray json4 = jsonObject.optJSONArray("movil");


                    foto = json.optString(0);
                    rutaFoto = decodeImg(foto);
                    nombre = json2.optJSONObject(0).optString("nombre");
                    correo = json3.optJSONObject(0).optString("correo");
                    movil = json4.optJSONObject(0).optString("movil");
                    puesto = json5.optJSONObject(0).optString("puesto");


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "e.printStackTrace: " + e.toString(), Toast.LENGTH_LONG).show();
                }

                callback.loginOk(User, Pass, rutaFoto, nombre, puesto, correo, movil);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user", user);
                params.put("pass", pass);
                return params;
            }
        };

        VolleySingleton.getInstanciaVolley(getContext()).addToRequestqueue(srPerfil);
    }

    private boolean login (String password){
        return isValidPassword(password);
    }


    private String decodeImg(String foto){
        String fotoConRuta;
        byte[] decodedString = Base64.decode(foto, Base64.DEFAULT);
        // Bitmap Image
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        String filename = "fotoPerfil_" + User + ".png";
        File file= Environment.getExternalStorageDirectory();
        File dest = new File(file, filename);

        try {
            FileOutputStream out = new FileOutputStream(dest);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        fotoConRuta = file + "/" + filename;
        return fotoConRuta;
    }

    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(getActivity());
        dialogo.setTitle("Permisos desactivados");
        dialogo.setMessage("Debe aceptar los permisos para continuar");
        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, 100);
            }
        });
        dialogo.show();
    }

    private boolean validaPermisos() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if ((checkSelfPermission(getActivity(), CAMERA) == PackageManager.PERMISSION_GRANTED && (checkSelfPermission(getActivity(), WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED))) {
            return true;
        }
        if ((shouldShowRequestPermissionRationale(CAMERA)) || (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))) {
            cargarDialogoRecomendacion();
        } else {
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, 100);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length == 2 && grantResults[0] == PERMISSION_GRANTED && grantResults[1] == PERMISSION_GRANTED) {

            } else {
                Toast.makeText(getActivity(), "Es necesario aceptar los permisos para continuar", Toast.LENGTH_LONG).show();
                solicitarPermisosManual();
            }
        }
    }

    private void solicitarPermisosManual() {
        final CharSequence[] opciones = {"si", "no"};
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(getActivity());
        alertOpciones.setTitle("¿Desea configurar los permisos de forma manual?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (opciones[which].equals("si")) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "Los permisos no fueron aceptados", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            }
        });
    }


    private boolean isValidPassword(String password){
        return password.length()>=4;
    }

    public interface loginOk{
        void loginOk(String usuario, String password, String rutaFoto, String nombre, String puesto, String correo, String movil);
    }
}
