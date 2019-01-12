package clh.inspecciones.com.inspecciones_v2.Activities;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import clh.inspecciones.com.inspecciones_v2.Clases.CACisternaBD;
import clh.inspecciones.com.inspecciones_v2.Clases.CACompartimentosBD;
import clh.inspecciones.com.inspecciones_v2.Clases.CARigidoBD;
import clh.inspecciones.com.inspecciones_v2.Clases.CATractoraBD;
import clh.inspecciones.com.inspecciones_v2.Fragments.AltaNuevaFragment;
import clh.inspecciones.com.inspecciones_v2.Fragments.BuscarInspeccionFragment;
import clh.inspecciones.com.inspecciones_v2.Fragments.CabeceraInspeccionFragment;
import clh.inspecciones.com.inspecciones_v2.Fragments.CalculadoraFragment;
import clh.inspecciones.com.inspecciones_v2.Fragments.CompartimentosFragment;
import clh.inspecciones.com.inspecciones_v2.Fragments.ControlAccesoCheckingFragment;
import clh.inspecciones.com.inspecciones_v2.Fragments.IdentificacionVehiculoFragment;
import clh.inspecciones.com.inspecciones_v2.Fragments.MenuFragment;
import clh.inspecciones.com.inspecciones_v2.Fragments.ResultadoInspeccionFragment;
import clh.inspecciones.com.inspecciones_v2.R;
import io.realm.Realm;

public class MenuActivity extends AppCompatActivity implements
        ResultadoInspeccionFragment.dataListener,
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
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    private String tipoVehiculo;
    private String tipoInspeccion;
    private String tipoComponente;
    private String inspeccion;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setToolbar();


        prefs = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        user = prefs.getString("user", "errorUser");
        pass = prefs.getString("pass", "errorPass");
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView)findViewById(R.id.navview);

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
                        fragment = new MenuFragment();
                        fragmentTransaction = true;
                        break;
                    case R.id.menu_altanueva:
                        altanueva();
                        /*
                        fragment = new AltaNuevaFragment();
                        nombreFragment = "AltaNuevaFragment";
                        fragmentTransaction = true;
                        */
                        break;
                    case R.id.menu_buscar:
                        fragment = new BuscarInspeccionFragment();
                        nombreFragment= "BuscarInspeccionFragment";
                        fragmentTransaction = true;
                        break;
                    case R.id.menu_calibrar:
                        fragment = new CalculadoraFragment();
                        nombreFragment="CalculadoraFragment";
                        fragmentTransaction = true;
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
        toolbar = (Toolbar)findViewById(R.id.toolbar);
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

    @Override
    public void inspeccionGuardada(Boolean finalizadaOk) {
        inspeccionFinalizada = finalizadaOk;
    }


    public void altanueva(){
        Intent intent = new Intent();
        intent.setClass(MenuActivity.this, AltaActivity.class);
        nombreFragment = "AltaNuevaFragment";
        intent.putExtra("nombreFragment", nombreFragment);
        startActivity(intent);
    }

    public void buscarInspeccion(){

    }

    public void calculadora(){

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
        switch (seleccion){
            case "altaNueva":
                altanueva();
                break;
            case "buscarInspeccion":
                buscarInspeccion();
                break;
            case "calculadora":
                calculadora();
                break;
            case "salir":
                logOut();
                break;
                default:
                    break;
        }

    }
}
