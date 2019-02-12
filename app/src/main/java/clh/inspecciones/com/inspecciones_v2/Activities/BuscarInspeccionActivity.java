package clh.inspecciones.com.inspecciones_v2.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import clh.inspecciones.com.inspecciones_v2.Clases.BuscarInspeccionClass;
import clh.inspecciones.com.inspecciones_v2.Clases.CACisternaBD;
import clh.inspecciones.com.inspecciones_v2.Clases.CACompartimentosBD;
import clh.inspecciones.com.inspecciones_v2.Clases.CARigidoBD;
import clh.inspecciones.com.inspecciones_v2.Clases.CATractoraBD;
import clh.inspecciones.com.inspecciones_v2.Clases.DetalleInspeccionBD;
import clh.inspecciones.com.inspecciones_v2.Clases.FotosBD;
import clh.inspecciones.com.inspecciones_v2.Fragments.BuscarInspeccionFragment;
import clh.inspecciones.com.inspecciones_v2.Fragments.VerCompartimentosFragment;
import clh.inspecciones.com.inspecciones_v2.Fragments.VerInspeccionFragment;
import clh.inspecciones.com.inspecciones_v2.R;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;

public class BuscarInspeccionActivity extends AppCompatActivity implements BuscarInspeccionFragment.dataListener,
    VerInspeccionFragment.dataListener,
        VerCompartimentosFragment.dataListener {

    private SharedPreferences prefs;
    private String user;
    private String pass;
    private String rutaFoto;
    private CircleImageView foto_perfil;
    private Bitmap bm = null;
    private TextView nombrePerfil;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    /*
    variables cuadro dialogo
     */
    private Double dato;
    private Integer datoInt;
    private String texto;

    private Fragment fragment;
    private String nombreFragment;
    Bundle args = new Bundle();
    private Realm realm;

    private String tipoVehiculo;
    private String tipoInspeccion;
    private String tipoComponente;
    private String inspeccion;
    private String tractora;
    private String cisterna;
    private String conductor;

    private Boolean guardadoCabeceraOk=false;
    private Boolean guardadoCompartimentosOk=false;
    private Boolean inspeccionFinalizada=false;
    private String nombre;
    private int numFotosDescargadas;
    private String matriculas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);
        setToolbar();

        Intent i = getIntent();
        nombreFragment = i.getStringExtra("nombreFragment");

        prefs = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        user = prefs.getString("user", "errorUser");
        pass = prefs.getString("pass", "errorPass");
        rutaFoto = prefs.getString("rutaFoto", "errorRutaFoto");
        nombre = prefs.getString("nombre", "errorNombre");

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navview);
        View header = navigationView.getHeaderView(0);
        foto_perfil = header.findViewById(R.id.headerCircle);
        nombrePerfil = header.findViewById(R.id.nombrePerfil);

        bm = BitmapFactory.decodeFile(rutaFoto);
        foto_perfil.setImageBitmap(bm);
        nombrePerfil.setText(nombre);

        borrarRealm();

        setFragmentByDefault();

        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                boolean fragmentTransaction = false;
                Fragment fragment = null;

                switch (item.getItemId()){
                    case R.id.menu:
                        Intent i = new Intent();
                        i.setClass(BuscarInspeccionActivity.this, MenuActivity.class);
                        startActivity(i);
                        break;
                    case R.id.menu_buscar:
                        fragment = new BuscarInspeccionFragment();
                        nombreFragment = "BuscarInspeccionFragment";
                        fragmentTransaction = true;
                        break;
                    case R.id.menu_calibrar:
                        abrirCuadroDialogoCalculadora();
                        break;
                    case R.id.menu_ajustes:
                        break;
                    case R.id.menu_logout:
                        removeSharedPreferences();
                        logOut();
                        break;
                }

                if(fragmentTransaction){
                    changeFragment(fragment, item);
                    drawerLayout.closeDrawers();
                }
                return false;
            }
        });

        foto_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(BuscarInspeccionActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setToolbar(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_siguiente, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            //case android.R.id.me
            case R.id.menu_siguiente:
                siguiente();
                return true;
            case R.id.menu_atras:
                volver();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setFragmentByDefault(){
        fragment = new BuscarInspeccionFragment();
        args.putString("user", user);
        args.putString("pass", pass);
        args.remove("fragmentActual");
        args.putString("fragmentActual", "BuscarInspeccionFragment");
        fragment.setArguments(args);
        changeFragment(fragment, navigationView.getMenu().getItem(1));
    }

    private void changeFragment(Fragment fragment, MenuItem item){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
        item.setChecked(true);
        getSupportActionBar().setTitle(item.getTitle());
    }


    private void siguiente() {
        switch (nombreFragment){
            case "BuscarInspeccionFragment":
                /*
                fragment = new IdentificacionVehiculoFragment();
                args.putString("tipoVehiculo", tipoVehiculo);
                args.putString("tipoInspeccion", tipoInspeccion);
                args.putString("tipoComponente", tipoComponente);
                args.putString("user", user);
                args.putString("pass", pass);
                args.remove("fragmentActual");
                args.putString("fragmentActual", "identificacionVehiculoFragment");
                fragment.setArguments(args);
                nombreFragment = "IdentificacionVehiculoFragment";
                changeFragment(fragment, navigationView.getMenu().getItem(1));
                */
                break;

            case "VerInspeccionFragment":
                fragment = new VerInspeccionFragment();
                args.clear();
                args.putString("user", user);
                args.putString("pass", pass);
                args.putString("inspeccion", inspeccion);
                //args.putString("numFotos", String.valueOf(numFotosDescargadas));
                args.remove("fragmentActual");
                args.putString("fragmentActual", "VerInspeccionFragment");
                fragment.setArguments(args);
                changeFragment(fragment, navigationView.getMenu().getItem(1));
                break;
            case "VerCompartimentosFragment":
                fragment = new VerCompartimentosFragment();
                args.clear();
                args.putString("user", user);
                args.putString("pass", pass);
                args.putString("inspeccion", inspeccion);
                args.putString("soloVer", "1");
                args.putString("matriculas", matriculas);
                args.putString("fragmentActual", "VerCompartimentosFragment");
                fragment.setArguments(args);
                changeFragment(fragment, navigationView.getMenu().getItem(1));
                break;
            default:
                break;
        }
    }


    public void borrarRealm(){
        realm = Realm.getDefaultInstance();
        if(realm.isEmpty() == false){
            realm.beginTransaction();
            realm.delete(BuscarInspeccionClass.class);
            realm.delete(DetalleInspeccionBD.class);
            realm.delete(CARigidoBD.class);
            realm.delete(CATractoraBD.class);
            realm.delete(CACisternaBD.class);
            realm.delete(CACompartimentosBD.class);
            realm.delete(FotosBD.class);
            realm.commitTransaction();
        }
    }

    private void logOut(){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void removeSharedPreferences(){
        prefs.edit().clear().apply();
    }


    private void abrirCuadroDialogoCalculadora(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Calculadora");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.calculadora_cuadro_dialogo, null);
        builder.setView(viewInflated);

        final EditText litros = viewInflated.findViewById(R.id.et_litrostotales);
        final TextView litros96 = viewInflated.findViewById(R.id.tv_resultado96);
        Button calcular = viewInflated.findViewById(R.id.calcular);



        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dato = Double.parseDouble(litros.getText().toString());
                dato = 0.96 * dato;

                datoInt = (int)Math.round(dato);
                texto = String.valueOf(datoInt);
                if(texto!="0"){

                    litros96.setText(datoInt.toString());
                }else{
                    Toast.makeText(BuscarInspeccionActivity.this, "Introducir valor", Toast.LENGTH_SHORT).show();
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public void verInspeccion(String inspeccion) {
        nombreFragment="VerInspeccionFragment";
        this.inspeccion = inspeccion;
        siguiente();
    }

    @Override
    public void compartimentos(String fragmentName, String matriculas) {
        nombreFragment=fragmentName;
        this.matriculas = matriculas;
        siguiente();
    }

    private void volver(){
        switch (nombreFragment){
            case "BuscarInspeccionFragment":
                /*
                fragment = new IdentificacionVehiculoFragment();
                args.putString("tipoVehiculo", tipoVehiculo);
                args.putString("tipoInspeccion", tipoInspeccion);
                args.putString("tipoComponente", tipoComponente);
                args.putString("user", user);
                args.putString("pass", pass);
                args.remove("fragmentActual");
                args.putString("fragmentActual", "identificacionVehiculoFragment");
                fragment.setArguments(args);
                nombreFragment = "IdentificacionVehiculoFragment";
                changeFragment(fragment, navigationView.getMenu().getItem(1));
                */
                break;

            case "VerInspeccionFragment":
                fragment = new BuscarInspeccionFragment();
                args.putString("user", user);
                args.putString("pass", pass);
                args.putString("numFotos", String.valueOf(numFotosDescargadas));
                args.remove("fragmentActual");
                args.putString("fragmentActual", "VerInspeccionFragment");
                fragment.setArguments(args);
                changeFragment(fragment, navigationView.getMenu().getItem(1));
                break;
            case "VerCompartimentosFragment":
                fragment = new VerInspeccionFragment();
                args.putString("user", user);
                args.putString("pass", pass);
                args.putString("inspeccion", inspeccion);
                args.remove("fragmentActual");
                args.putString("fragmentActual", "VerInspeccionFragment");
                fragment.setArguments(args);
                changeFragment(fragment, navigationView.getMenu().getItem(1));
                break;
            default:
                break;
        }
    }


    @Override
    public void abrirCalculadora() {
        abrirCuadroDialogoCalculadora();
    }

    /*
    private void abrirCalculadoraDialogo(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Calculadora");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.calculadora_cuadro_dialogo, null);
        builder.setView(viewInflated);

        final EditText litros = viewInflated.findViewById(R.id.et_litrostotales);
        final TextView litros96 = viewInflated.findViewById(R.id.tv_resultado96);
        Button calcular = viewInflated.findViewById(R.id.calcular);



        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dato = Double.parseDouble(litros.getText().toString());
                dato = 0.96 * dato;

                datoInt = (int)Math.round(dato);
                texto = String.valueOf(datoInt);


                if(texto!="0"){

                    litros96.setText(datoInt.toString());
                }else{
                    Toast.makeText(BuscarInspeccionActivity.this, "Introducir valor", Toast.LENGTH_SHORT).show();
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    */
}
