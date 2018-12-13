package clh.inspecciones.com.inspecciones_v2.Clases;

import android.graphics.Bitmap;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class FotosBD extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String inspeccion;
    private String nombreFoto;
    private Bitmap bitmap;

    public FotosBD(){}

    public FotosBD(String inspeccion,String nombreFoto, Bitmap bitmap) {
        this.id=InicializacionRealm.FotosBDId.incrementAndGet();
        this.inspeccion = inspeccion;
        this.nombreFoto = nombreFoto;
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getInspeccion() {
        return inspeccion;
    }

    public void setInspeccion(String inspeccion) {
        this.inspeccion = inspeccion;
    }

    public String getNombreFoto() {
        return nombreFoto;
    }

    public void setNombreFoto(String nombreFoto) {
        this.nombreFoto = nombreFoto;
    }
}
