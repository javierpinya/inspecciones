package clh.inspecciones.com.inspecciones_v2.Clases;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by root on 10/09/18.
 */

public class CATractoraBD extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String matricula;
    private String tipo_componente;
    private int chip;
    private Date adr;
    private Date itv;
    private int tara;
    private int mma;
    private String soloGasoelos;
    private String cod_transportista_resp;
    private boolean bloqueado;
    private Date fec_baja;


    public CATractoraBD(){}

    public CATractoraBD(String matricula){
        this.id= InicializacionRealm.CATractoraBDId.incrementAndGet();
        this.matricula=matricula;
        this.adr = new Date();
        this.itv = new Date();
        this.fec_baja = new Date();
    }



    public int getId() {
        return id;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getTipo_componente() {
        return tipo_componente;
    }

    public void setTipo_componente(String tipo_componente) {
        this.tipo_componente = tipo_componente;
    }

    public int getChip() {
        return chip;
    }

    public void setChip(int chip) {
        this.chip = chip;
    }

    public Date getAdr() {
        return adr;
    }

    public void setAdr(Date adr) {
        this.adr = adr;
    }

    public Date getItv() {
        return itv;
    }

    public void setItv(Date itv) {
        this.itv = itv;
    }

    public int getTara() {
        return tara;
    }

    public void setTara(int tara) {
        this.tara = tara;
    }

    public int getMma() {
        return mma;
    }

    public void setMma(int mma) {
        this.mma = mma;
    }

    public String getSoloGasoelos() {return soloGasoelos;    }

    public void setSoloGasoelos(String SoloGasoelos) {
        this.soloGasoelos = soloGasoelos;
    }

    public String getCod_transportista_resp() {
        return cod_transportista_resp;
    }

    public void setCod_transportista_resp(String cod_transportista_resp) {
        this.cod_transportista_resp = cod_transportista_resp;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public Date getFec_baja() {
        return fec_baja;
    }

    public void setFec_baja(Date fec_baja) {
        this.fec_baja = fec_baja;
    }

}
