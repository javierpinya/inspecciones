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
import clh.inspecciones.com.inspecciones_v2.Clases.CACisternaBD;
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
    private String tipoVehiculo;



    //Variables donde recibir los datos de internet y pasarlos después a la BBDD
    private String cisterna;
    private String tipo_componente;
    private int chip;
    private String adr;
    private String itv;
    private int ejes;
    private int tara;
    private int mma;
    private String fec_calibracion;
    private String ind_carga_pesados;
    private String fec_baja;
    private String cod_nacion;
    private String solo_gasoleo;
    //private boolean contador;
    private boolean bloqueado;
    private String fecha_bloqueo;
    private String motivo_bloqueo;
    // private List<CACompartimentosBD> compartimentos;


    private SimpleDateFormat parseador = new SimpleDateFormat("dd-MM-yyyy");


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

        View view = inflater.inflate(R.layout.fragment_control_acceso_resultado_vehiculo, container, false);
        mListView = (ListView)view.findViewById(R.id.lv_controlaccesoresultadovehiculo);
        realm = Realm.getDefaultInstance();
        if (realm.isEmpty()==false){
            realm.beginTransaction();
            realm.deleteAll();
            realm.commitTransaction();
        }
        return view;
    }

    private void llamadavolley() {

        final String usuario="admin";
        final String pass="admin";

        StringRequest sr = new StringRequest(Request.Method.POST, json_url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    //Convierto la respuesta, de tipo String, a un JSONObject.
                    JSONObject jsonObject = new JSONObject(response);
                    //Cramos un JSONArray del objeto JSON "vehiculo"
                    JSONArray json = jsonObject.optJSONArray("vehiculo");
                    Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();

                    for (int i=0; i<json.length(); i++){

                        cisterna=(json.optJSONObject(i).optString("cod_matricula_real"));
                        tipo_componente = (json.optJSONObject(i).optString("id_tipo_componente"));
                        ejes = (json.optJSONObject(i).optInt("num_ejes"));
                        itv=(json.optJSONObject(i).optString("fec_cadu_itv"));
                        adr=(json.optJSONObject(i).optString("fec_cadu_adr"));
                        fec_calibracion = (json.optJSONObject(i).optString("fec_cadu_calibracion"));
                        ind_carga_pesados = (json.optJSONObject(i).optString("ind_carga_pesados"));
                        cod_nacion = (json.optJSONObject(i).optString("cod_nacion"));
                        tara=(json.optJSONObject(i).optInt("can_tara"));
                        mma=(json.optJSONObject(i).optInt("can_peso_maximo"));
                        chip=(json.optJSONObject(i).optInt("num_chip"));
                        fec_baja=(json.optJSONObject(i).optString("fec_baja"));
                        //contador=(json.optJSONObject(i).opt)
                        solo_gasoleo=(json.optJSONObject(i).optString("ind_solo_gasoleo"));
                        bloqueado=(json.optJSONObject(i).optBoolean("ind_bloqueo"));
                    }

                    Date adr_p = parseador.parse(adr);
                    Date itv_p = parseador.parse(itv);
                    Date fec_calibracion_p = parseador.parse(fec_calibracion);
                    Date fec_baja_p;
                    if (fec_baja!=null){
                        fec_baja_p = parseador.parse(fec_baja);
                    } else {
                        fec_baja_p=null;
                    }


                    createNewMatricula(cisterna,tipo_componente, ejes, itv_p,adr_p,fec_calibracion_p,ind_carga_pesados,cod_nacion, tara,mma,chip,fec_baja_p,solo_gasoleo,bloqueado);

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
                params.put("username", "admin");
                params.put("password", "admin");
                params.put("tipo_consulta", tipoVehiculo);  //0=rigido, 1=tractora, 2, cisterna
                params.put("matVehiculo", matriculaIntent);
                return params;
            }
        };

        VolleySingleton.getInstanciaVolley(getActivity()).addToRequestqueue(sr);
    }


    //** CRUD Actions **/
    private void createNewMatricula(String matricula, String tipo_componente, int ejes, Date itv, Date adr, Date fec_cadu_calibracion,String ind_carga_pesados,String cod_nacion, int tara, int peso_maximo, int chip, Date fec_baja, String solo_gasoleos, boolean bloqueado){
        realm.beginTransaction();
        CACisternaBD cisterna = new CACisternaBD(matricula);
        cisterna.setTipo_componente(tipo_componente);
        cisterna.setEjes(ejes);
        cisterna.setItv(itv);
        cisterna.setAdr(adr);
        cisterna.setFec_calibracion(fec_cadu_calibracion);
        cisterna.setInd_carga_pesados(ind_carga_pesados);
        cisterna.setCod_nacion(cod_nacion);
        cisterna.setTara(tara);
        cisterna.setMma(peso_maximo);
        cisterna.setChip(chip);
        cisterna.setFec_baja(fec_baja);
        cisterna.setSoloGasoelos(solo_gasoleos);
        cisterna.setBloqueado(bloqueado);
        realm.copyToRealmOrUpdate(cisterna);
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

    public void renderVehiculo(String matVehiculo, String tipoVehiculo){
        matriculaIntent = matVehiculo.trim();
        tipoVehiculo = tipoVehiculo.trim();
        llamadavolley();
    }

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
