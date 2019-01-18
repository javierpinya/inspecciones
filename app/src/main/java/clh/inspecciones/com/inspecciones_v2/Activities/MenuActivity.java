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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import clh.inspecciones.com.inspecciones_v2.Fragments.BuscarInspeccionFragment;
import clh.inspecciones.com.inspecciones_v2.Fragments.MenuFragment;
import clh.inspecciones.com.inspecciones_v2.R;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;

public class MenuActivity extends AppCompatActivity implements
        MenuFragment.EleccionMenu{     //implements MenuFragment.EleccionMenu



    /*
    Esta activity viene precedida de LoginActivity.
    Es el menú principal, donde elegiremos la tarea a realizar.
    El fragment asociado es MenuFragment
    Los layouts asociados son fragment_menu.xml y activity_menu.xml
     */

    private SharedPreferences prefs;
    private String user;
    private String pass;
    private String rutaFoto = "ruta";
    private String nombre;
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

    private String tipoVehiculo;
    private String tipoInspeccion;
    private String tipoComponente;
    private String inspeccion;

    private String fotoPrueba = "/storage/emulated/0/DCIM/Camera/IMG_20190110_162420.jpg";
    private Fragment fragment;
    private String nombreFragment;
    Bundle args = new Bundle();

    private Realm realm;
    private String tractora;
    private String cisterna;
    private String conductor;


    private Boolean guardadoCabeceraOk=false;
    private Boolean guardadoCompartimentosOk=false;
    private Boolean inspeccionFinalizada=false;
    private String correo;
    private String movil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setToolbar();


        prefs = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        user = prefs.getString("user", "errorUser");
        pass = prefs.getString("pass", "errorPass");
        rutaFoto = prefs.getString("rutaFoto", "errorRutaFoto");
        nombre = prefs.getString("nombre", "errorNombre");
        correo = prefs.getString("correo", "errorCorreo");
        movil = prefs.getString("movil", "errorMovil");
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navview);
        View header = navigationView.getHeaderView(0);
        foto_perfil = header.findViewById(R.id.headerCircle);
        nombrePerfil = header.findViewById(R.id.nombrePerfil);

        if (rutaFoto.equals("ruta")) {
            foto_perfil.setImageResource(R.drawable.ic_launcher_background);
        } else {
            bm = BitmapFactory.decodeFile(rutaFoto);
            foto_perfil.setImageBitmap(bm);
        }


        nombrePerfil.setText(nombre);
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

        foto_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MenuActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                boolean fragmentTransaction = false;
                Fragment fragment = null;

                switch (item.getItemId()){
                    case R.id.menu:
                        fragment = new MenuFragment();
                        fragmentTransaction = true;
                        break;
                    case R.id.menu_altanueva:
                        altanueva();
                        break;
                    case R.id.menu_buscar:
                        fragment = new BuscarInspeccionFragment();
                        nombreFragment= "BuscarInspeccionFragment";
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
    }

    private void setToolbar(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setFragmentByDefault(){
        fragment = new MenuFragment();
        changeFragment(fragment, navigationView.getMenu().getItem(0));
    }

    private void changeFragment(Fragment fragment, MenuItem item){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
        item.setChecked(true);
        getSupportActionBar().setTitle(item.getTitle());
    }


    public void altanueva(){
        Intent intent = new Intent();
        intent.setClass(MenuActivity.this, AltaActivity.class);
        //nombreFragment = "AltaNuevaFragment";
        intent.putExtra("nombreFragment", "AltaNuevaFragment");
        startActivity(intent);
    }

    public void buscarInspeccion(){
        fragment = new BuscarInspeccionFragment();
        args.remove("fragmentActual");
        args.putString("fragmentActual", "BuscarInspeccionFragment");
        fragment.setArguments(args);
        nombreFragment = "BuscarInspeccionFragment";
        changeFragment(fragment, navigationView.getMenu().getItem(0));
    }


    private void logOut(){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void removeSharedPreferences(){
        prefs.edit().clear().apply();
    }

    @Override
    public void eleccionMenu(String seleccion) {

        if (seleccion.equals("altaNueva")) {
            altanueva();
        } else if (seleccion.equals("buscarInspeccion")) {
            buscarInspeccion();
        } else if (seleccion.equals("calculadora")) {
            abrirCuadroDialogoCalculadora();
        } else if (seleccion.equals("salir")) {
            logOut();
        }
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
                    Toast.makeText(MenuActivity.this, "Introducir valor", Toast.LENGTH_SHORT).show();
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
