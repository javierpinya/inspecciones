package clh.inspecciones.com.inspecciones_v2.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private Switch swRemember;
    private String User;
    private String Pass;
    String json_url = "http://pruebaalumnosandroid.esy.es/inspecciones/login.php";
    String json_url1= "http://pruebaalumnosandroid.esy.es/inspecciones/consulta_num_inspeccion.php";
    private loginOk callback;
    private int nuevaInspeccion=0;
    private int contadorInspecciones;


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
        swRemember = view.findViewById(R.id.sw_preferences);
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

            nuevaInspeccion = buscarUltimaInspeccion(User) + 1;
            //Toast.makeText(getActivity(), String.valueOf(nuevaInspeccion), Toast.LENGTH_SHORT).show();

            callback.loginOk(User, Pass, swRemember.isChecked(), nuevaInspeccion);
        }
    },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                }
            }){
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<>();
            params.put("username", User);
            params.put("password", Pass);
            return params;
        }
    };

    private boolean login (String password){
        if(!isValidPassword(password)){
            return false;
        }else{
            return true;
        }
    }

    private int buscarUltimaInspeccion(final String usuario){

        StringRequest sr = new StringRequest(Request.Method.POST, json_url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    JSONArray json = jsonObject.optJSONArray("num_inspecciones");
                    contadorInspecciones = (json.optJSONObject(0).optInt("CONTADOR"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user", usuario);
                params.put("pass", Pass);
                return params;
            }
        };

        return contadorInspecciones;
    }

    private boolean isValidPassword(String password){
        return password.length()>=4;
    }

    public interface loginOk{
        void loginOk(String usuario, String password, boolean sw, int nuevaInspeccion);
    }
}
