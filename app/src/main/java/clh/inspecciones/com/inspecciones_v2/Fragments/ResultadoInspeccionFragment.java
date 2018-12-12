package clh.inspecciones.com.inspecciones_v2.Fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import clh.inspecciones.com.inspecciones_v2.Clases.DetalleInspeccionBD;
import clh.inspecciones.com.inspecciones_v2.R;
import clh.inspecciones.com.inspecciones_v2.SingleTones.VolleySingleton;
import io.realm.Realm;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;
import static android.os.Environment.getExternalStoragePublicDirectory;
import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;
import static android.support.v4.content.PermissionChecker.checkSelfPermission;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultadoInspeccionFragment extends Fragment implements View.OnClickListener{


    private Realm realm;

    private dataListener callback;
    private CheckBox cbInspeccionada;
    private CheckBox cbFavorable;
    private CheckBox cbDesfavorable;
    private EditText etFechaDesfavorable;
    private CheckBox cbBloqueo;
    private EditText etFechaBloqueo;
    private CheckBox cbRevisda;
    private EditText etFechaRevisada;
    private EditText etComentarios;
    private String user;
    private String pass;
    private String inspeccion;
    private DetalleInspeccionBD detalleInspeccionBD;
    private String inspeccionada;
    private String bloqueada;
    private String favorable;
    private String desfavorable;
    private String revisada;
    private String fecha_desfavorable="0";
    private String comentarios="0";
    private Date fechaDesfavorableP=new Date();
    private String fecha_revisada = "0";
    private String fecha_bloqueo="0";
    private Date fechaRevisadaP = new Date();
    private Date fechaBloqueoP = new Date();
    private String url = "http://pruebaalumnosandroid.esy.es/inspecciones/registrar_inspeccion.php";
    private ImageView imagen;
    private FloatingActionButton fab;
    private String RUTA_IMAGEN="";
    private String path;
    final int COD_SELECCION=10;
    static final int COD_CAMARA=20;


    private SimpleDateFormat parseador = new SimpleDateFormat("dd-MM-yyyy");


    public ResultadoInspeccionFragment() {
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
        View view = inflater.inflate(R.layout.fragment_resultado_inspeccion, container, false);

        cbInspeccionada = (CheckBox)view.findViewById(R.id.cb_inspeccionada);
        cbFavorable = (CheckBox)view.findViewById(R.id.cb_favorable);
        cbDesfavorable = (CheckBox)view.findViewById(R.id.cb_desfavorable);
        etFechaDesfavorable = (EditText)view.findViewById(R.id.et_fechadesfavorable);
        cbBloqueo = (CheckBox)view.findViewById(R.id.cb_bloqueo);
        etFechaBloqueo = (EditText)view.findViewById(R.id.et_fechabloqueo);
        cbRevisda = (CheckBox)view.findViewById(R.id.cb_revisado);
        etFechaRevisada = (EditText) view.findViewById(R.id.et_fecha_revisado);
        etComentarios = (EditText)view.findViewById(R.id.comentarios);
        imagen = (ImageView)view.findViewById(R.id.image1);

        fab = view.findViewById(R.id.fab);

        if(validaPermisos()){
            fab.show();
        }else{
            fab.hide();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarImagen();
            }
        });
        cbInspeccionada.setOnClickListener(this);
        cbFavorable.setOnClickListener(this);
        cbDesfavorable.setOnClickListener(this);
        cbBloqueo.setOnClickListener(this);
        cbRevisda.setOnClickListener(this);

        realm = Realm.getDefaultInstance();

        // Inflate the layout for this fragment
        return view;
    }

    private void cargarImagen() {
        final CharSequence[] opciones={"Camara", "Galeria", "Cancelar"};
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(getActivity());
        alertOpciones.setTitle("Seleccione una opción");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (opciones[which].toString()){
                    case "Camara":
                        try {
                            tomarFoto();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        Toast.makeText(getActivity(), "Tomando Foto", Toast.LENGTH_SHORT).show();
                        break;
                    case "Galeria":
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent, "Seleccione la aplicacion"), COD_SELECCION);
                        break;
                    case "Cancelar":
                        dialog.dismiss();
                }
            }
        });
        alertOpciones.show();
    }

    private void tomarFoto() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + ".jpg";
        String storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + imageFileName;
        File image = new File(storageDir);

        // Save a file: path for use with ACTION_VIEW intents
        RUTA_IMAGEN = image.getAbsolutePath();


        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            String authorities=getActivity().getApplicationContext().getPackageName()+ ".provider";
            Uri imageUri = FileProvider.getUriForFile(getActivity(), authorities, image);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        }else{
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(image));
        }
        startActivityForResult(intent, COD_CAMARA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
            switch (requestCode){
                case COD_SELECCION:
                    Uri miPath = data.getData();
                    imagen.setImageURI(miPath);
                    break;
                case COD_CAMARA:
                    MediaScannerConnection.scanFile(getContext(), new String[]{RUTA_IMAGEN}, null, new MediaScannerConnection.OnScanCompletedListener(){

                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("Ruta de almacenamiento", "Path: " + path);
                        }
                    });

                    Bitmap bitmap = BitmapFactory.decodeFile(RUTA_IMAGEN);
                    imagen.setImageBitmap(bitmap);
                    break;
            }
        }
    }

    public void renderResultadoInspeccion(String user, String pass, String inspeccion){
        this.user = user;
        this.pass = pass;
        this.inspeccion = inspeccion;
        cbInspeccionada.setChecked(true);
        cbFavorable.setChecked(true);
        cbRevisda.setChecked(false);
        cbBloqueo.setChecked(false);

    }

    public void guardar(String user, String pass, String inspeccion){
        this.user = user;
        this.pass = pass;
        this.inspeccion = inspeccion;
        comentarios = etComentarios.getText().toString();
        this.fecha_desfavorable = etFechaDesfavorable.getText().toString();
        this.fecha_revisada = etFechaRevisada.getText().toString();
        this.fecha_bloqueo = etFechaBloqueo.getText().toString();

        inspeccionada = String.valueOf(cbInspeccionada.isChecked());
        favorable = String.valueOf(cbFavorable.isChecked());
        desfavorable = String.valueOf(cbDesfavorable.isChecked());
        bloqueada = String.valueOf(cbBloqueo.isChecked());
        revisada = String.valueOf(cbRevisda.isChecked());

        try {
            this.fechaDesfavorableP = parseador.parse(this.fecha_desfavorable);
            this.fechaRevisadaP = parseador.parse(this.fecha_revisada);
            this.fechaBloqueoP = parseador.parse(this.fecha_bloqueo);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        detalleInspeccionBD = realm.where(DetalleInspeccionBD.class).equalTo("inspeccion", inspeccion).findFirst();
        realm.beginTransaction();
        detalleInspeccionBD.setInspeccionada(cbInspeccionada.isChecked());
        detalleInspeccionBD.setFavorable(cbFavorable.isChecked());
        detalleInspeccionBD.setDesfavorable(cbDesfavorable.isChecked());
        detalleInspeccionBD.setFechaDesfavorable(this.fechaDesfavorableP);
        detalleInspeccionBD.setRevisado(cbRevisda.isChecked());
        detalleInspeccionBD.setFechaRevisado(this.fechaRevisadaP);
        detalleInspeccionBD.setBloqueo(cbBloqueo.isChecked());
        detalleInspeccionBD.setFechaBloqueo(this.fechaBloqueoP);
        detalleInspeccionBD.setObservaciones(comentarios);
        realm.copyToRealmOrUpdate(detalleInspeccionBD);
        realm.commitTransaction();
        //registrar en BD Online
        guardarOnLine(user, pass,inspeccionada, favorable, desfavorable, revisada, bloqueada, inspeccion, fecha_desfavorable, fecha_revisada, fecha_bloqueo, comentarios);

    }

    private void guardarOnLine(final String user, final String pass, final String inspeccionada, final String favorable, final String desfavorable, final String revisada, final String bloqueada,final  String inspeccion, final String fecha_desfavorable, final String fecha_revisada, final String fecha_bloqueo, final String comentarios) {

        StringRequest sr = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                callback.guardada();

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user", user);
                params.put("pass", pass);
                params.put("inspeccion", inspeccion);
                params.put("inspeccionada", inspeccionada);
                params.put("favorable", favorable);
                params.put("desfavorable", desfavorable);
                params.put("revisada", revisada);
                params.put("bloqueada", bloqueada);
                params.put("fecha_desfavorable", fecha_desfavorable);
                params.put("fecha_revisada", fecha_revisada);
                params.put("fecha_bloqueo", fecha_bloqueo);
                params.put("comentarios", comentarios);
                return params;
            }
        };


        VolleySingleton.getInstanciaVolley(getContext()).addToRequestqueue(sr);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cb_favorable:
                cbFavorable.setChecked(true);
                cbDesfavorable.setChecked(false);
                etFechaDesfavorable.setEnabled(false);
                break;
            case R.id.cb_desfavorable:
                cbDesfavorable.setChecked(true);
                cbFavorable.setChecked(false);
                etFechaDesfavorable.setEnabled(true);
                break;
            case R.id.cb_bloqueo:
                etFechaBloqueo.setEnabled(cbBloqueo.isChecked());
                break;
            case R.id.cb_revisado:
                etFechaRevisada.setEnabled(cbRevisda.isChecked());
                default:
                    break;
        }

    }

    private boolean validaPermisos(){
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return true;
        }
        if((checkSelfPermission(getActivity(),CAMERA) == PackageManager.PERMISSION_GRANTED && (checkSelfPermission(getActivity(), WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED))){
            return true;
        }
        if((shouldShowRequestPermissionRationale(CAMERA)) || (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))){
            cargarDialogoRecomendacion();
        }else{
            requestPermissions(new String[] {WRITE_EXTERNAL_STORAGE, CAMERA}, 100);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100){
            if(grantResults.length==2 && grantResults[0] == PERMISSION_GRANTED && grantResults[1] == PERMISSION_GRANTED){
                fab.show();
            }else{
                solicitarPermisosManual();
            }
        }
    }

    private void solicitarPermisosManual() {
        final CharSequence[] opciones={"si", "no"};
        final AlertDialog.Builder alertOpciones= new AlertDialog.Builder(getActivity());
        alertOpciones.setTitle("¿Desea configurar los permisos de forma manual?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(opciones[which].equals("si")){
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package",getActivity().getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), "Los permisos no fueron aceptados", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            }
        });
    }

    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(getActivity());
        dialogo.setTitle("Permisos desactivados");
        dialogo.setMessage("Debe aceptar los permisos para tomar fotos");
        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestPermissions(new String[] {WRITE_EXTERNAL_STORAGE, CAMERA}, 100);;
            }
        });
        dialogo.show();
    }

    public interface dataListener{
        void guardada();
    }

}
