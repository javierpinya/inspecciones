package clh.inspecciones.com.inspecciones_v2.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import clh.inspecciones.com.inspecciones_v2.Adapters.ControlAccesoResultadoCisternaAdapter;
import clh.inspecciones.com.inspecciones_v2.Adapters.ControlAccesoResultadoTractoraAdapter;
import clh.inspecciones.com.inspecciones_v2.Clases.CACisternaBD;
import clh.inspecciones.com.inspecciones_v2.Clases.CATractoraBD;
import clh.inspecciones.com.inspecciones_v2.R;
import clh.inspecciones.com.inspecciones_v2.SingleTones.VolleySingleton;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class ControlAccesoResultadoCisternaFragment extends Fragment implements RealmChangeListener<RealmResults<CACisternaBD>> {

    public dataListener callback;
    private String json_url2 = "http://pruebaalumnosandroid.esy.es/inspecciones/consultas_inspecciones_app.php";
    private Realm realm;
    private RealmResults<CACisternaBD> cisternaBD;
    private ListView mListView;
    private ControlAccesoResultadoCisternaAdapter adapter;
    private String matriculaIntent;



    //Variables donde recibir los datos de internet y pasarlos después a la BBDD
    private String cisterna;
    private int chip;
    private String adr;
    private String itv;
    private int tara;
    private int mma;
    private String fec_baja;
    private String solo_gasoleo;
    private boolean bloqueado;

    private SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");


    public ControlAccesoResultadoCisternaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            callback = (dataListener)context;
        }catch (Exception e){
            throw new ClassCastException(context.toString() + " should implement dataListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        llamadavolley();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_control_acceso_resultado_cisterna, container, false);
    }

    private void llamadavolley() {

        final String usuario="";
        final String pass="";

        StringRequest sr = new StringRequest(Request.Method.POST, json_url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    //Convierto la respuesta, de tipo String, a un JSONObject.
                    JSONObject jsonObject = new JSONObject(response);
                    //Cramos un JSONArray del objeto JSON "vehiculo"
                    JSONArray json = jsonObject.optJSONArray("vehiculo");

                    for (int i=0; i<json.length(); i++){

                        cisterna=(json.optJSONObject(i).optString("cod_matricula_real"));
                        itv=(json.optJSONObject(i).optString("fec_cadu_itv"));
                        adr=(json.optJSONObject(i).optString("fec_cadu_adr"));
                        tara=(json.optJSONObject(i).optInt("can_tara"));
                        mma=(json.optJSONObject(i).optInt("can_peso_maximo"));
                        chip=(json.optJSONObject(i).optInt("num_chip"));
                        fec_baja=(json.optJSONObject(i).optString("fec_baja"));
                        solo_gasoleo=(json.optJSONObject(i).optString("ind_solo_gasoleo"));
                        bloqueado=(json.optJSONObject(i).optBoolean("ind_bloqueo"));
                    }

                    Date adr_p = parseador.parse(adr);
                    Date itv_p = parseador.parse(itv);
                    Date fec_baja_p;
                    if (fec_baja!=null){
                        fec_baja_p = parseador.parse(fec_baja);
                    } else {
                        fec_baja_p=null;
                    }


                    createNewMatricula(cisterna,itv_p,adr_p,tara,mma,chip,fec_baja_p,solo_gasoleo,bloqueado);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(),"e.printStackTrace: " + e.toString(),Toast.LENGTH_LONG).show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getActivity(),"MatriculaIntent - " + matriculaIntent + " Error conexion: " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("username", usuario);
                params.put("password", pass);
                params.put("tipo_consulta", "2");
                // params.put("tipo_inspeccion", tipo_inspeccion);
                params.put("conductor", "1020000");
                params.put("tractora", matriculaIntent);
                params.put("cisterna", "01242U");

                return params;
            }
        };

        VolleySingleton.getInstanciaVolley(getActivity()).addToRequestqueue(sr);
    }


    //** CRUD Actions **/
    private void createNewMatricula(String matricula, Date itv, Date adr, int tara, int peso_maximo, int chip, Date fec_baja, String solo_gasoleos, boolean bloqueado){
        realm.beginTransaction();
        CACisternaBD rigido = new CACisternaBD(matricula);
        rigido.setItv(itv);
        rigido.setAdr(adr);
        rigido.setTara(tara);
        rigido.setMma(peso_maximo);
        rigido.setChip(chip);
        rigido.setFec_baja(fec_baja);
        rigido.setSoloGasoelos(solo_gasoleos);
        rigido.setBloqueado(bloqueado);
        realm.copyToRealmOrUpdate(rigido);
        realm.commitTransaction();

    }
/*
    private Date stringToDate(String cadena) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd'T'"); //HH:mm:ss'Z'");
        Date date = null;
        try {
            date = format.parse(cadena);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
*/

    public void renderCisterna(String cisterna){
        matriculaIntent = cisterna.trim();
        llamadavolley();
        cisternaBD = realm.where(CACisternaBD.class).findAll();
        cisternaBD.addChangeListener(this);

        adapter = new ControlAccesoResultadoCisternaAdapter(getActivity(), cisternaBD,R.layout.ca_cisterna_checking_listview_item);
        mListView.setAdapter(adapter);

    }

    @Override
    public void onChange(RealmResults<CACisternaBD> caCisternaBDS) {
        adapter.notifyDataSetChanged();

    }

    public interface dataListener {
        void getCisternaIntent(String cisterna);
    }

}
