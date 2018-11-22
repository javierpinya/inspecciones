package clh.inspecciones.com.inspecciones_v2.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
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

import clh.inspecciones.com.inspecciones_v2.Clases.CACisternaBD;
import clh.inspecciones.com.inspecciones_v2.Clases.CACompartimentosBD;
import clh.inspecciones.com.inspecciones_v2.Clases.CARigidoBD;
import clh.inspecciones.com.inspecciones_v2.Clases.CATractoraBD;
import clh.inspecciones.com.inspecciones_v2.R;
import clh.inspecciones.com.inspecciones_v2.SingleTones.VolleySingleton;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class ControlAccesoCheckingFragment extends Fragment{

    private ListView mListView;
    //private Button btn;
    private dataListener callback;
    private String json_url2 = "http://pruebaalumnosandroid.esy.es/inspecciones/consultas_inspecciones_app.php";
    private Realm realm;
    private TextView tv;
    private String tipoVehiculo;
    private String tipoComponente;
    private String inspeccion;
    private String user;
    private String pass;

    private RealmResults<CARigidoBD> rigidoBD;
    private RealmResults<CACisternaBD> cisternaBD;
    private RealmResults<CATractoraBD> tractoraBD;

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


    public ControlAccesoCheckingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            callback =(dataListener)context;
        }catch(Exception e){
            throw new ClassCastException(context.toString() + " should implement dataListener");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_control_acceso_checking, container, false);

        compartimentos = new ArrayList<>();
        capacidad = new ArrayList<>();
        tags = new ArrayList<>();

        realm = Realm.getDefaultInstance();




        mListView = (ListView)view.findViewById(R.id.lv_controlaccesochecking);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String pulsada;
                pulsada = mListView.getItemAtPosition(position).toString();

                switch (tipoComponente){
                    case "R":
                        callback.itemPulsado(pulsada, tipoComponente);
                        break;
                    case "T":
                        callback.itemPulsado(pulsada, tipoComponente);
                        break;
                    case "C":
                        callback.itemPulsado(pulsada, tipoComponente);
                        break;
                    case "S":
                        if (position==0){
                            //Toast.makeText(getActivity(),pulsada + ", " + tipoComponente + position , Toast.LENGTH_SHORT).show();
                            callback.itemPulsado(pulsada, "T");
                        }else if (position==1){
                            callback.itemPulsado(pulsada, "C");
                        }
                        break;
                }
            }
        });

        //btn.setOnClickListener(this);

        return  view;
    }

    private void llamadaVolley(final String primerComponente, final String segundoComponente, final String tipoComponente, final String user, final String pass){

        StringRequest sr = new StringRequest(Request.Method.POST, json_url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();


                switch (tipoComponente){
                    case "R":   //RIGIDO

                        try {
                            //Convierto la respuesta, de tipo String, a un JSONObject.
                            JSONObject jsonObject = new JSONObject(response);
                            //Cramos un JSONArray del objeto JSON "vehiculo"
                            JSONArray json = jsonObject.optJSONArray("vehiculo");
                            JSONArray json1  = jsonObject.optJSONArray("compartimentos");

                            for (int i=0; i<json.length(); i++){

                                matVehiculo=(json.optJSONObject(i).optString("cod_matricula_real"));
                                tipo_componente=(json.optJSONObject(i).optString("id_tipo_componente"));
                                //tipo_componente=(response);
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

                    case "T":
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
                    case "C":
                        try {
                            //Convierto la respuesta, de tipo String, a un JSONObject.
                            JSONObject jsonObject = new JSONObject(response);
                            //Cramos un JSONArray del objeto JSON "vehiculo"
                            JSONArray json = jsonObject.optJSONArray("vehiculo");
                            JSONArray json1  = jsonObject.optJSONArray("compartimento");

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
                            anadirCompartimentos(matVehiculo,compartimentos,capacidad,tags);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(),"e.printStackTrace: " + e.toString(),Toast.LENGTH_LONG).show();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;

                    case "S":

                        try{
                            //Convierto la respuesta, de tipo String, a un JSONObject.
                            JSONObject jsonObject = new JSONObject(response);
                            //Cramos un JSONArray del objeto JSON "vehiculo"
                            JSONArray json = jsonObject.optJSONArray("tractora");
                            JSONArray json1  = jsonObject.optJSONArray("cisterna");
                            JSONArray json2  = jsonObject.optJSONArray("compartimentos");

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

                            for (int i=0; i<json1.length(); i++){

                                matVehiculo=(json1.optJSONObject(i).optString("cod_matricula_real"));
                                tipo_componente = (json1.optJSONObject(i).optString("id_tipo_componente"));
                                num_ejes = (json1.optJSONObject(i).optInt("num_ejes"));
                                itv=(json1.optJSONObject(i).optString("fec_cadu_itv"));
                                adr=(json1.optJSONObject(i).optString("fec_cadu_adr"));
                                fec_cadu_calibracion = (json1.optJSONObject(i).optString("fec_cadu_calibracion"));
                                carga_pesados = (json1.optJSONObject(i).optString("ind_carga_pesados"));
                                cod_nacion = (json1.optJSONObject(i).optString("cod_nacion"));
                                tara=(json1.optJSONObject(i).optInt("can_tara"));
                                mma=(json1.optJSONObject(i).optInt("can_peso_maximo"));
                                chip=(json1.optJSONObject(i).optInt("num_chip"));
                                fec_baja=(json1.optJSONObject(i).optString("fec_baja"));
                                //contador=(json1.optJSONObject(i).opt)
                                solo_gasoleo=(json1.optJSONObject(i).optString("ind_solo_gasoleo"));
                                bloqueado=(json1.optJSONObject(i).optBoolean("ind_bloqueo"));
                            }

                            for(int i=0; i<json2.length(); i++){
                                compartimentos.add(json2.optJSONObject(i).optInt("cod_compartimento"));
                                tags.add(json2.optJSONObject(i).optString("cod_tag_cprt"));
                                capacidad.add(json2.optJSONObject(i).optInt("can_capacidad"));
                            }

                            Date fec_calibracion_p = parseador.parse(fec_cadu_calibracion);
                            if (fec_baja!=null){
                                fec_baja_p = parseador.parse(fec_baja);
                            } else {
                                fec_baja_p=null;
                            }


                            createNewCisterna(matVehiculo,tipo_componente, num_ejes, itv_p,adr_p,fec_calibracion_p,carga_pesados,cod_nacion, tara,mma,chip,fec_baja_p,solo_gasoleo,bloqueado);
                            anadirCompartimentos(matVehiculo,compartimentos,capacidad,tags);

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

                        Toast.makeText(getActivity()," Error conexion: " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user", user);
                params.put("pass", pass);
                params.put("tipo_consulta", tipoComponente);  //R - Rigido, T - Tractora, C - cisterna
                params.put("primer_componente", primerComponente);
                params.put("segundo_componente", segundoComponente);
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



        for (int i=0; i<compartimentos.size(); i++){
            realm.beginTransaction();
            CACompartimentosBD _compartimentosBD = new CACompartimentosBD(matricula, inspeccion);
            _compartimentosBD.setCod_compartimento(compartimentos.get(i));
            _compartimentosBD.setCan_capacidad(capacidad.get(i));
            _compartimentosBD.setCod_tag_cprt(tags.get(i));
            realm.copyToRealmOrUpdate(_compartimentosBD);
            realm.commitTransaction();
        }
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

        tractoraBD = realm.where(CATractoraBD.class).findAll();
        if (tractoraBD.isEmpty()==true) {
            Toast.makeText(getActivity(), "tractoraBD entra en createNewTractora pero está vaciá", Toast.LENGTH_SHORT).show();
        }
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



    public void renderText(List<String> datos, String tipoVehiculo, String tipoTractora, String user, String pass, int nuevaInspeccion) {
        this.tipoVehiculo=tipoVehiculo.trim();
        this.tipoComponente =tipoTractora.trim();
        this.user = user.trim();
        this.pass = pass.trim();
        this.inspeccion = this.user + String.valueOf(nuevaInspeccion);

        if (datos.size()>1){
            llamadaVolley(datos.get(0).trim(), datos.get(1).trim(), this.tipoComponente, this.user, this.pass);
        }else{
            llamadaVolley(datos.get(0).trim(),"XXXXXX",this.tipoComponente, this.user, this.pass);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, datos);
        mListView.setAdapter(adapter);
    }

    public interface dataListener{
        void itemPulsado(String vehiculo, String tipoVehiculo);
        void inspecciones();
    }

}
