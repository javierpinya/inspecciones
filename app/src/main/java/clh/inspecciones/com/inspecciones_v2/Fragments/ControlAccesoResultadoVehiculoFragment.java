package clh.inspecciones.com.inspecciones_v2.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import clh.inspecciones.com.inspecciones_v2.Adapters.ControlAccesoResultadoCisternaAdapter;
import clh.inspecciones.com.inspecciones_v2.Adapters.ControlAccesoResultadoRigidoAdapter;
import clh.inspecciones.com.inspecciones_v2.Adapters.ControlAccesoResultadoTractoraAdapter;
import clh.inspecciones.com.inspecciones_v2.Clases.CACisternaBD;
import clh.inspecciones.com.inspecciones_v2.Clases.CACompartimentosBD;
import clh.inspecciones.com.inspecciones_v2.Clases.CARigidoBD;
import clh.inspecciones.com.inspecciones_v2.Clases.CATractoraBD;
import clh.inspecciones.com.inspecciones_v2.R;
import clh.inspecciones.com.inspecciones_v2.SingleTones.VolleySingleton;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;

public class ControlAccesoResultadoVehiculoFragment extends Fragment{

    public dataListener callback;
    private String json_url2 = "http://pruebaalumnosandroid.esy.es/inspecciones/consultas_inspecciones_app.php";
    private Realm realm;
    private RealmResults<CARigidoBD> rigidoBD;
    private RealmResults<CACisternaBD> cisternaBD;
    private RealmResults<CATractoraBD> tractoraBD;
    private RealmList<CACompartimentosBD> compartimentosBD;
    private CARigidoBD caRigidoBD;
    private CACisternaBD caCisternaBD;
    private CATractoraBD caTractoraBD;
    private RealmResults<CACompartimentosBD> caCompartimentosBD;
    private ListView mListView;

    //Adaptadores
    private ControlAccesoResultadoRigidoAdapter adapterRigido;
    private ControlAccesoResultadoTractoraAdapter adapterTractora;
    private ControlAccesoResultadoCisternaAdapter adapterCisterna;

    private String matriculaIntent;
    private String tipoVehiculo;



    //Variables donde recibir los datos de internet y pasarlos después a la BBDD
    private String matVehiculo;
    private String tipo_componente;
    private int chip;
    private String adr;
    private String itv;
    private int tara;
    private int mma;
    private int num_ejes;
    private String fec_baja;
    private String solo_gasoleo;
    private String carga_pesados;
    private boolean bloqueado;
    private String fec_cadu_calibracion;
    private String tResp;
    private String cod_nacion;

    //compartimentos
    private List<Integer> compartimentos;
    private List<Integer> capacidad;
    private List<String> tags;


    private SimpleDateFormat parseador = new SimpleDateFormat("dd-MM-yyyy");

    public ControlAccesoResultadoVehiculoFragment() {
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = view = inflater.inflate(R.layout.fragment_control_acceso_resultado_vehiculo, container, false);

        mListView = (ListView)view.findViewById(R.id.lv_controlaccesoresultadovehiculo);

        compartimentos = new ArrayList<>();
        capacidad = new ArrayList<>();
        tags = new ArrayList<>();

        realm = Realm.getDefaultInstance();

        return view;
    }

    private void llamadavolley() {

        StringRequest sr = new StringRequest(Request.Method.POST, json_url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                switch (tipoVehiculo){
                    case "0":

                        try {
                            //Convierto la respuesta, de tipo String, a un JSONObject.
                            JSONObject jsonObject = new JSONObject(response);
                            //Cramos un JSONArray del objeto JSON "vehiculo"
                            JSONArray json = jsonObject.optJSONArray("vehiculo");
                            JSONArray json1  = jsonObject.optJSONArray("compartimentos");

                            for (int i=0; i<json.length(); i++){

                                matVehiculo=(json.optJSONObject(i).optString("cod_matricula_real"));
                                tipo_componente=(json.optJSONObject(i).optString("id_tipo_componente"));
                                itv=(json.optJSONObject(i).optString("fec_cadu_itv"));
                                adr=(json.optJSONObject(i).optString("fec_cadu_adr"));
                                tara=(json.optJSONObject(i).optInt("can_tara"));
                                mma=(json.optJSONObject(i).optInt("can_peso_maximo"));
                                chip=(json.optJSONObject(i).optInt("num_chip"));
                                num_ejes=(json.optJSONObject(i).optInt("num_ejes"));
                                fec_baja=(json.optJSONObject(i).optString("fec_baja"));
                                fec_cadu_calibracion = (json.optJSONObject(i).optString("fec_cadu_calibracion"));
                                carga_pesados = (json.optJSONObject(i).optString("ind_carga_pesados"));
                                solo_gasoleo=(json.optJSONObject(i).optString("ind_solo_gasoleo"));
                                bloqueado=(json.optJSONObject(i).optBoolean("ind_bloqueo"));
                                tResp = (json.optJSONObject(i).optString("COD_TRANSPORTISTA_RESP"));
                            }

                            for(int i=0; i<json1.length(); i++){
                                compartimentos.add(json1.optJSONObject(i).optInt("cod_compartimento"));
                                tags.add(json1.optJSONObject(i).optString("cod_tag_cprt"));
                                capacidad.add(json1.optJSONObject(i).optInt("can_capacidad"));
                            }

                            Date adr_p = parseador.parse(adr);
                            Date itv_p = parseador.parse(itv);
                            Date fec_cadu_calibracion_p = parseador.parse(fec_cadu_calibracion);
                            Date fec_baja_p;
                            if (fec_baja!=null){
                                fec_baja_p = parseador.parse(fec_baja);
                            } else {
                                fec_baja_p=null;
                            }


                            createNewRigido(matVehiculo,tipo_componente, itv_p,adr_p,tara,mma,chip,fec_baja_p,solo_gasoleo,bloqueado, carga_pesados, fec_cadu_calibracion_p, num_ejes, tResp);
                            anadirCompartimentos(matVehiculo, compartimentos, capacidad, tags);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(),"e.printStackTrace: " + e.toString(),Toast.LENGTH_LONG).show();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;

                    case "1":
                        try {
                            //Convierto la respuesta, de tipo String, a un JSONObject.
                            JSONObject jsonObject = new JSONObject(response);
                            //Cramos un JSONArray del objeto JSON "vehiculo"
                            JSONArray json = jsonObject.optJSONArray("vehiculo");

                            for (int i=0; i<json.length(); i++){

                                //tipo_componente = (json.optJSONObject(i).optString("id_tipo_componente"));
                                matVehiculo=(json.optJSONObject(i).optString("cod_matricula_real"));
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


                            createNewTractora(matVehiculo,tipo_componente, itv_p,adr_p,tara,mma,chip,fec_baja_p,solo_gasoleo,bloqueado);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(),"e.printStackTrace: " + e.toString(),Toast.LENGTH_LONG).show();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "2":
                        try {
                            //Convierto la respuesta, de tipo String, a un JSONObject.
                            JSONObject jsonObject = new JSONObject(response);
                            //Cramos un JSONArray del objeto JSON "vehiculo"
                            JSONArray json = jsonObject.optJSONArray("vehiculo");
                            JSONArray json1  = jsonObject.optJSONArray("compartimentos");

                            for (int i=0; i<json.length(); i++){

                                matVehiculo=(json.optJSONObject(i).optString("cod_matricula_real"));
                                tipo_componente = (json.optJSONObject(i).optString("id_tipo_componente"));
                                num_ejes = (json.optJSONObject(i).optInt("num_ejes"));
                                itv=(json.optJSONObject(i).optString("fec_cadu_itv"));
                                adr=(json.optJSONObject(i).optString("fec_cadu_adr"));
                                fec_cadu_calibracion = (json.optJSONObject(i).optString("fec_cadu_calibracion"));
                                carga_pesados = (json.optJSONObject(i).optString("ind_carga_pesados"));
                                cod_nacion = (json.optJSONObject(i).optString("cod_nacion"));
                                tara=(json.optJSONObject(i).optInt("can_tara"));
                                mma=(json.optJSONObject(i).optInt("can_peso_maximo"));
                                chip=(json.optJSONObject(i).optInt("num_chip"));
                                fec_baja=(json.optJSONObject(i).optString("fec_baja"));
                                //contador=(json.optJSONObject(i).opt)
                                solo_gasoleo=(json.optJSONObject(i).optString("ind_solo_gasoleo"));
                                bloqueado=(json.optJSONObject(i).optBoolean("ind_bloqueo"));
                            }

                            for(int i=0; i<json1.length(); i++){
                                compartimentos.add(json1.optJSONObject(i).optInt("cod_compartimento"));
                                tags.add(json1.optJSONObject(i).optString("cod_tag_cprt"));
                                capacidad.add(json1.optJSONObject(i).optInt("can_capacidad"));
                            }

                            Date adr_p = parseador.parse(adr);
                            Date itv_p = parseador.parse(itv);
                            Date fec_calibracion_p = parseador.parse(fec_cadu_calibracion);
                            Date fec_baja_p;
                            if (fec_baja!=null){
                                fec_baja_p = parseador.parse(fec_baja);
                            } else {
                                fec_baja_p=null;
                            }


                            createNewCisterna(matVehiculo,tipo_componente, num_ejes, itv_p,adr_p,fec_calibracion_p,carga_pesados,cod_nacion, tara,mma,chip,fec_baja_p,solo_gasoleo,bloqueado);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(),"e.printStackTrace: " + e.toString(),Toast.LENGTH_LONG).show();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;
                        default:
                            break;

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
                params.put("tipo_consulta", tipoVehiculo);  //0 - Rigido, 1 - Tractora, 2 - cisterna
                params.put("rigido", matriculaIntent);
                return params;
            }
        };

        VolleySingleton.getInstanciaVolley(getActivity()).addToRequestqueue(sr);
    }


    //** CRUD Actions **/
    private void createNewRigido(String matricula,String tipo_componente, Date itv, Date adr, int tara, int peso_maximo, int chip, Date fec_baja, String solo_gasoleos, boolean bloqueado, String carga_pesados, Date fec_cadu_calibracion, int num_ejes, String cod_transportista_resp){
        realm.beginTransaction();
        CARigidoBD rigido = new CARigidoBD(matricula);
        rigido.setTipo_componente(tipo_componente);
        rigido.setItv(itv);
        rigido.setAdr(adr);
        rigido.setTara(tara);
        rigido.setMma(peso_maximo);
        rigido.setChip(chip);
        rigido.setFec_baja(fec_baja);
        rigido.setSoloGasoelos(solo_gasoleos);
        rigido.setInd_carga_pesados(carga_pesados);
        rigido.setFec_cadu_calibracion(fec_cadu_calibracion);
        rigido.setEjes(num_ejes);
        rigido.setBloqueado(bloqueado);
        rigido.setCod_transportista_resp(cod_transportista_resp);
        realm.copyToRealmOrUpdate(rigido);
        realm.commitTransaction();

    }

    private void anadirCompartimentos(String matricula, List<Integer> compartimentos, List<Integer> capacidad, List<String> tags){
        caRigidoBD = realm.where(CARigidoBD.class).equalTo("matricula", matricula).findFirst();
        compartimentosBD = caRigidoBD.getCompartimentos();
        for (int i=0; i<compartimentos.size(); i++){
            realm.beginTransaction();
            CACompartimentosBD _compartimentosBD = new CACompartimentosBD(matricula);
            _compartimentosBD.setCod_compartimento(compartimentos.get(i));
            _compartimentosBD.setCan_capacidad(capacidad.get(i));
            _compartimentosBD.setCod_tag_cprt(tags.get(i));
            realm.copyToRealmOrUpdate(_compartimentosBD);
            realm.commitTransaction();
        }

        caCompartimentosBD = realm.where(CACompartimentosBD.class).findAll();
        Toast.makeText(getActivity(), "capacidad: " + caCompartimentosBD.get(0).getCan_capacidad(), Toast.LENGTH_SHORT).show();
    }


    //** CRUD Actions **/
    private void createNewTractora(String matricula,String tipo_componente, Date itv, Date adr, int tara, int peso_maximo, int chip, Date fec_baja, String solo_gasoleos, boolean bloqueado){
        realm.beginTransaction();
        CATractoraBD tractora = new CATractoraBD(matricula);
        tractora.setTipo_componente(tipo_componente);
        tractora.setItv(itv);
        tractora.setAdr(adr);
        tractora.setTara(tara);
        tractora.setMma(peso_maximo);
        tractora.setChip(chip);
        tractora.setFec_baja(fec_baja);
        tractora.setSoloGasoelos(solo_gasoleos);
        tractora.setBloqueado(bloqueado);
        realm.copyToRealmOrUpdate(tractora);
        realm.commitTransaction();

    }

    //** CRUD Actions **/
    private void createNewCisterna(String matricula, String tipo_componente, int ejes, Date itv, Date adr, Date fec_cadu_calibracion,String ind_carga_pesados,String cod_nacion, int tara, int peso_maximo, int chip, Date fec_baja, String solo_gasoleos, boolean bloqueado){
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

    public void renderVehiculo(String matVehiculo, String tipoVehiculo){
        matriculaIntent = matVehiculo.trim();
        tipoVehiculo = tipoVehiculo.trim();
        llamadavolley();
        switch (tipoVehiculo){
            case "0":
                rigidoBD = realm.where(CARigidoBD.class).findAll();
                adapterRigido = new ControlAccesoResultadoRigidoAdapter(getActivity(), rigidoBD, R.layout.ca_rigido_checking_listview_item);
                mListView.setAdapter(adapterRigido);
                break;
            case "1":
                tractoraBD = realm.where(CATractoraBD.class).findAll();
                adapterTractora = new ControlAccesoResultadoTractoraAdapter(getActivity(), tractoraBD, R.layout.ca_tractora_checking_listview_item);
                mListView.setAdapter(adapterTractora);
                break;
            case "2":
                cisternaBD = realm.where(CACisternaBD.class).findAll();
                adapterCisterna = new ControlAccesoResultadoCisternaAdapter(getActivity(), cisternaBD, R.layout.ca_cisterna_checking_listview_item);
                mListView.setAdapter(adapterCisterna);
                break;

            default:
                    break;
        }

    }
/*
    public void renderRigido(String rigido){
        matriculaIntent = rigido.trim();
        llamadavolley();
        rigidoBD = realm.where(CARigidoBD.class).findAll();
        rigidoBD.addChangeListener(this);

        adapter = new ControlAccesoResultadoRigidoAdapter(getActivity(), rigidoBD, R.layout.ca_rigido_checking_listview_item);
        mListView.setAdapter(adapter);

    }
*/



    public interface dataListener{
        void getVehiculoIntent(String rigido);
    }
}
