package clh.inspecciones.com.inspecciones_v2.Clases;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class DetalleInspeccionBD extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String matricula;

}
