package clh.inspecciones.com.inspecciones_v2.Fragments;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
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
    private String json_url = "http://pruebaalumnosandroid.esy.es/inspecciones/login.php";

    private loginOk callback;
    private String foto;
    private String rutaFoto="";


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
                    VolleySingleton.getInstanciaVolley(getContext()).addToRequestqueue(sr);
                }else{
                    Toast.makeText(getActivity(), "Password no válido", Toast.LENGTH_LONG).show();
                }

        }


    }

    StringRequest sr = new StringRequest(Request.Method.POST, json_url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            try {
                //Convierto la respuesta, de tipo String, a un JSONObject.
                JSONObject jsonObject = new JSONObject(response);
                JSONArray json = jsonObject.optJSONArray("foto");

                foto = json.optString(0);
                rutaFoto = decodeImg(foto);

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(),"e.printStackTrace: " + e.toString(),Toast.LENGTH_LONG).show();
            }



            callback.loginOk(User, Pass, rutaFoto);

        }
    },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                }
            }){
        @Override
        protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<>();
            params.put("user", User);
            params.put("pass", Pass);
            return params;
        }
    };

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


    private boolean isValidPassword(String password){
        return password.length()>=4;
    }

    public interface loginOk{
        void loginOk(String usuario, String password, String rutaFoto);
    }
}
