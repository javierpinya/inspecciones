package clh.inspecciones.com.inspecciones_v2.Clases;

import android.app.Application;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by root on 20/08/18.
 */

public class InicializacionRealm extends Application {

    public static AtomicInteger CATractoraBDId = new AtomicInteger();
    public static AtomicInteger CACisternaBDId = new AtomicInteger();
    public static AtomicInteger DetalleInspeccionBDId = new AtomicInteger();

    //Esta clase sirve para temas de configuración.
    //Se lanza antes que la activity principal.
    @Override
    public void onCreate() {

        super.onCreate();
        setUpRealmConfig();
        Realm realm = Realm.getDefaultInstance();
        CATractoraBDId = getIdByTable(realm, CATractoraBD.class);
        CACisternaBDId = getIdByTable(realm, CACisternaBD.class);
        DetalleInspeccionBDId = getIdByTable(realm, DetalleInspeccionBD.class);
        realm.close();

    }

    private void setUpRealmConfig(){

        Realm.init(getApplicationContext());

        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }

    //<T extends RealmObject> significa que no sabemos de qué clase es nuestro método pero que va a heredar de un RealmObject
    private <T extends RealmObject> AtomicInteger getIdByTable(Realm realm, Class<T> anyClass){
        RealmResults<T> result = realm.where(anyClass).findAll();   //al poner anyClass podemos reutilizar este método para todas las clases que extiendan de RealmObject
        //(result.size()>0 ) ? return true: return false
        return (result.size() > 0) ? new AtomicInteger(result.max("id").intValue()) : new AtomicInteger();

    }
}
