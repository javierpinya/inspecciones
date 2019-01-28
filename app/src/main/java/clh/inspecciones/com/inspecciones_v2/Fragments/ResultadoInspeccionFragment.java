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
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import clh.inspecciones.com.inspecciones_v2.Adapters.CameraAdapter;
import clh.inspecciones.com.inspecciones_v2.Adapters.FotosAdapter;
import clh.inspecciones.com.inspecciones_v2.Clases.DetalleInspeccionBD;
import clh.inspecciones.com.inspecciones_v2.Clases.FotosBD;
import clh.inspecciones.com.inspecciones_v2.Clases.TemplatePDF;
import clh.inspecciones.com.inspecciones_v2.R;
import clh.inspecciones.com.inspecciones_v2.SingleTones.VolleySingleton;
import io.realm.Realm;
import io.realm.RealmResults;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;
import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;
import static android.support.v4.content.PermissionChecker.checkSelfPermission;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultadoInspeccionFragment extends Fragment implements View.OnClickListener {


    private Realm realm;

    private Button btnGuardar;
    private Button btnGenerarPDF;
    private Button btnEnviarPDF;


    private dataListener callback;
    private CheckBox cbInspeccionada;
    private CheckBox cbFavorable;
    private CheckBox cbBloqueo;

    private CheckBox cbRevisda;

    private EditText etComentarios;
    private String user;
    private String pass;
    private String inspector = user;
    private String inspeccion;
    private DetalleInspeccionBD detalleInspeccionBD;
    private RealmResults<FotosBD> fotosBD;
    private String inspeccionada;
    private String bloqueada;
    private String favorable;
    private String desfavorable;
    private String revisada;
    private String fechaFinInspeccion ="0";
    private String comentarios="0";
    private Date fechaFinInspeccionP =new Date();
    private String fecha_revisada = "0";
    private String fecha_bloqueo="0";
    private Date fechaRevisadaP = new Date();
    private Date fechaBloqueoP = new Date();
    private String url = "http://pruebaalumnosandroid.esy.es/inspecciones/registrar_inspeccion.php";


    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat parseador2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    /*
    Variables de fotosFragment
     */

    private GridView gridView;
    private List<ImageView> imageViews=new ArrayList<>();
    private Bitmap imagen;
    private List<Uri> path;
    private List<Bitmap> bitmaps;

    private FotosAdapter myAdapter;
    private CameraAdapter myCameraAdapter;
    private List<ImageView> mImageViews = new ArrayList<ImageView>();
    //private dataListener callbackFoto;
    private List<FotosBD> fotos = new ArrayList<>();
    private FloatingActionButton fabFoto;
    private FloatingActionButton fabCalculadora;
    private FloatingActionsMenu fabMenu;
    //private ImageView imagen;
    private String RUTA_IMAGEN="";
    //private String path;
    final int COD_SELECCION=10;
    static final int COD_CAMARA=20;
    private String imgString;
    private Bitmap bitmapConvertida2;
    private String urlFoto = "http://pruebaalumnosandroid.esy.es/inspecciones/registrar_fotos.php";
    private List<String> imgStringList;
    final Bitmap bitmapFinal=null;
    private int contadorFotosGuardadas=0;
    private int fotoSize;
    private List<String> rutaImagenes;


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

        cbInspeccionada = view.findViewById(R.id.cb_inspeccionada);
        cbFavorable = view.findViewById(R.id.cb_favorable);
        cbBloqueo = view.findViewById(R.id.cb_bloqueo);
        cbRevisda = view.findViewById(R.id.cb_revisado);
        etComentarios = view.findViewById(R.id.comentarios);
        btnGuardar = view.findViewById(R.id.btn_guardar);
        btnGenerarPDF = view.findViewById(R.id.generarPDF);
        btnEnviarPDF = view.findViewById(R.id.enviarPDF);

        cbInspeccionada.setChecked(true);
        cbFavorable.setChecked(true);
        cbRevisda.setChecked(false);
        cbBloqueo.setChecked(false);

        realm = Realm.getDefaultInstance();

        user = getArguments().getString("user", "no_user");
        pass = getArguments().getString("pass", "no_pass");
        inspeccion = getArguments().getString("inspeccion", "sin_inspeccion");
        /*
        fotoSize = getArguments().getInt("fotoSize", 0);
        rutaImagenes = new ArrayList<>();
        for(int i=0;i<fotoSize;i++){
            rutaImagenes.add(getArguments().getString("foto" + i, "sinRutaFoto"));
        }

        */



        cbInspeccionada.setOnClickListener(this);
        cbFavorable.setOnClickListener(this);
        cbBloqueo.setOnClickListener(this);
        cbRevisda.setOnClickListener(this);
        btnGuardar.setOnClickListener(this);
        btnGenerarPDF.setOnClickListener(this);
        btnEnviarPDF.setOnClickListener(this);

        gridView = view.findViewById(R.id.gridView);
        path = new ArrayList<>();
        bitmaps = new ArrayList<>();
        imgStringList = new ArrayList<>();

        fabFoto = view.findViewById(R.id.fabFoto);
        fabCalculadora = view.findViewById(R.id.fabCalculadora);
        fabMenu = view.findViewById(R.id.grupoFab);
        fabCalculadora.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                callback.abrirCalculadora();
                fabMenu.collapse();
            }
        });
        fabFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabMenu.collapse();
                cargarImagen();
            }
        });

        return view;
    }


    public void guardar(){
        Toast.makeText(getActivity(), "Guardando...", Toast.LENGTH_LONG).show();
        Date today = new Date();
        try {
            today = parseador2.parse(String.valueOf(Calendar.getInstance().getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String fechaFinInspeccion;
        fechaFinInspeccion = df.format(today);
        comentarios = etComentarios.getText().toString();

        inspeccionada = String.valueOf(cbInspeccionada.isChecked());
        favorable = String.valueOf(cbFavorable.isChecked());
        bloqueada = String.valueOf(cbBloqueo.isChecked());
        revisada = String.valueOf(cbRevisda.isChecked());

        detalleInspeccionBD = realm.where(DetalleInspeccionBD.class).equalTo("inspeccion", inspeccion).findFirst();
        realm.beginTransaction();
        detalleInspeccionBD.setInspeccionada(cbInspeccionada.isChecked());
        detalleInspeccionBD.setFavorable(cbFavorable.isChecked());
        detalleInspeccionBD.setFechaFinInspeccion(today);
        detalleInspeccionBD.setRevisado(cbRevisda.isChecked());
        detalleInspeccionBD.setBloqueo(cbBloqueo.isChecked());
        detalleInspeccionBD.setObservaciones(comentarios);
        realm.copyToRealmOrUpdate(detalleInspeccionBD);
        realm.commitTransaction();
        btnGenerarPDF.setEnabled(true);
        //registrar en BD Online
        guardarOnLine(user, pass,inspeccionada, favorable, revisada, bloqueada, inspeccion, fechaFinInspeccion, comentarios);

    }

    private void guardarOnLine(final String user, final String pass, final String inspeccionada, final String favorable, final String revisada, final String bloqueada,final  String inspeccion, final String fechaFinInspeccion, final String comentarios) {


        StringRequest sr = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(bitmaps.size()>0) {
                    guardarFotos();
                }else{
                    Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                    callback.inspeccionGuardada(true);
                }


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("user", user);
                params.put("pass", pass);
                params.put("inspeccion", inspeccion);
                params.put("inspeccionada", inspeccionada);
                params.put("favorable", favorable);
                params.put("revisada", revisada);
                params.put("bloqueada", bloqueada);
                params.put("fecha_fin_inspeccion", fechaFinInspeccion);
                params.put("comentarios", comentarios);
                return params;
            }
        };


        VolleySingleton.getInstanciaVolley(getContext()).addToRequestqueue(sr);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_guardar:
                guardar();
                break;
            case R.id.generarPDF:
                generarPDF();
                break;
            case R.id.enviarPDF:
                enviarPDF();
                break;
                default:
                    break;
        }

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
                        //Toast.makeText(getActivity(), "Tomando Foto", Toast.LENGTH_SHORT).show();
                        break;
                    case "Galeria":
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(Intent.createChooser(intent, "Seleccione la aplicacion"), COD_SELECCION);
                        break;
                    case "Cancelar":
                        dialog.dismiss();
                }
            }
        });

        alertOpciones.show();
    }

    private void tomarFoto() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = inspeccion + "_" + timeStamp + ".jpg";
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
        Bitmap bitmap=null;

        if(resultCode==RESULT_OK){
            switch (requestCode){
                case COD_SELECCION:
                    Uri miPath = data.getData();
                    path.add(miPath);

                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),miPath);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case COD_CAMARA:
                    MediaScannerConnection.scanFile(getContext(), new String[]{RUTA_IMAGEN}, null, new MediaScannerConnection.OnScanCompletedListener(){

                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("Ruta de almacenamiento", "Path: " + path);
                        }
                    });

                    bitmap = BitmapFactory.decodeFile(RUTA_IMAGEN);
                    break;
            }

            bitmaps.add(bitmap);
            imgString = convertirImgString(bitmap); //Quizá convendría hacerlo en un AsyncTask... hay que mirar cómo
            imgStringList.add(imgString);
            establecerImagenes(bitmaps);

        }
    }



    private void establecerImagenes(List<Bitmap> bitmap){
        myCameraAdapter = new CameraAdapter(getActivity(), R.layout.list_fotos, bitmap);
        gridView.setAdapter(myCameraAdapter);
        myCameraAdapter.notifyDataSetChanged();
    }

    private String convertirImgString(final Bitmap bitmap) {

        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, array);
        byte[] imagenByte = array.toByteArray();
        String imagenString = Base64.encodeToString(imagenByte, Base64.DEFAULT);

        return imagenString;
    }



    private void guardarFotoOnline(final String user, final String pass, final String inspeccion, final String nombreFoto, final String fotoString) {
        StringRequest sr = new StringRequest(StringRequest.Method.POST, urlFoto, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equalsIgnoreCase("guardada")) {
                    contadorFotosGuardadas = contadorFotosGuardadas + 1;
                    Toast.makeText(getActivity(), "Foto " + contadorFotosGuardadas + " guardada", Toast.LENGTH_LONG).show();

                }
                if(imgStringList.size()==contadorFotosGuardadas) {
                    callback.inspeccionGuardada(true);
                }else{
                    callback.inspeccionGuardada(false);
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("user", user);
                params.put("pass", pass);
                params.put("inspeccion", inspeccion);
                params.put("nombreFoto", nombreFoto);
                params.put("foto", fotoString);
                return params;
            }
        };

        sr.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getInstanciaVolley(getContext()).addToRequestqueue(sr);
    }


    public void guardarFotos() {
        //int currentTime = (int)System.currentTimeMillis();
        String nombreFoto;
        for (int i = 0; i < imgStringList.size(); i++) {
            nombreFoto = inspeccion + "_" + i; // + "_" + currentTime;
            guardarFotoOnline(user, pass, inspeccion, nombreFoto, imgStringList.get(i));
        }


    }

    public void generarPDF() {
        detalleInspeccionBD = realm.where(DetalleInspeccionBD.class).equalTo("inspeccion", inspeccion).findFirst();
        //Usamos Realm para generar el pdf
        String[] header = {"Id", "Nombre", "Apellido"};
        String resultado = "";
        String shortText = "Hola";
        String longText = "Vehículo inspeccionado con resultado ";
        String nombrePDF = "inspeccion_" + inspeccion;

        if (detalleInspeccionBD.getFavorable()) {
            resultado = "Favorable";
        } else {
            resultado = "Desfavorable";
        }

        longText = longText + resultado;

        TemplatePDF templatePDF = new TemplatePDF(getActivity(), nombrePDF);
        templatePDF.openDocument();
        templatePDF.addMetaData("Inspeccion: " + inspeccion, "Inspecciones", "" + inspector);
        templatePDF.addTitles("Inspección: " + inspeccion, "Vehiculo: " + detalleInspeccionBD.getTractora() + " - " + detalleInspeccionBD.getRigido() + " - " + detalleInspeccionBD.getCisterna(), detalleInspeccionBD.getFechaInspeccion().toString());
        //templatePDF.addParagraph(shortText);
        templatePDF.addSpacing10();
        templatePDF.createTable(header, getCabeceraInspeccion());
        templatePDF.addParagraph(longText);
        templatePDF.addSpacing10();
        templatePDF.closeDocuemnt();
        Toast.makeText(getActivity(), "PDFGenerado", Toast.LENGTH_SHORT).show();
        templatePDF.viewPDF();

    }

    private ArrayList<String[]> getCabeceraInspeccion() {
        ArrayList<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"Vehiculo: " + detalleInspeccionBD.getTractora() + " - " + detalleInspeccionBD.getRigido() + " - " + detalleInspeccionBD.getCisterna(), "Inspector: " + inspector, "Fecha Inspeccion: " + detalleInspeccionBD.getFechaInspeccion().toString()});
        rows.add(new String[]{"Albaran: " + detalleInspeccionBD.getAlbaran(), "Conductor: " + detalleInspeccionBD.getConductor(), "hola"});
        rows.add(new String[]{"Observaciones: " + detalleInspeccionBD.getObservaciones(), "hola2", "hola3"});

        return rows;
    }

    private void enviarPDF() {

    }

    /*
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        borrarFoto(view.getId());
        return true;
    }

    private void borrarFoto(int elemento){

        AlertDialog.Builder dialogo = new AlertDialog.Builder(getActivity());
        dialogo.setTitle("Permisos desactivados");
        dialogo.setMessage("Debe aceptar los permisos para continuar");
        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gridView.getAdapter().getView().
            }
        });
        dialogo.show();
    }

*/
    public interface dataListener {
        void inspeccionGuardada(Boolean finalizadaOk);
        void abrirCalculadora();
    }

}
