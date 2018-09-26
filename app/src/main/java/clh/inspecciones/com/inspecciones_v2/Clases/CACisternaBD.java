package clh.inspecciones.com.inspecciones_v2.Clases;

import java.util.Date;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by root on 10/09/18.
 */

public class CACisternaBD extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String matricula;
    private String tipo_componente;
    private int chip;
    private Date adr;
    private Date itv;
    private int ejes;
    private int tara;
    private int mma;
    private Date fec_calibracion;
    private String ind_carga_pesados;
    private Date fec_baja;
    private String cod_nacion;
    private String soloGasoelos;
    private boolean contador;
    private boolean bloqueado;
    private Date fecha_bloqueo;
    private String motivo_bloqueo;
   // private List<CACompartimentosBD> compartimentos;

    public String getInd_carga_pesados() {
        return ind_carga_pesados;
    }

    public void setInd_carga_pesados(String ind_carga_pesados) {
        this.ind_carga_pesados = ind_carga_pesados;
    }

    public String getSoloGasoelos() {
        return soloGasoelos;
    }

    public void setSoloGasoelos(String soloGasoelos) {
        this.soloGasoelos = soloGasoelos;
    }

    public CACisternaBD(){}

    public CACisternaBD(String matricula){
        this.id=InicializacionRealm.CACisternaBDId.incrementAndGet();
        this.matricula=matricula;
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

    public int getEjes() {
        return ejes;
    }

    public void setEjes(int ejes) {
        this.ejes = ejes;
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

    public Date getFec_calibracion() {
        return fec_calibracion;
    }

    public void setFec_calibracion(Date fec_calibracion) {
        this.fec_calibracion = fec_calibracion;
    }

    public Date getFec_baja() {
        return fec_baja;
    }

    public void setFec_baja(Date fec_baja) {
        this.fec_baja = fec_baja;
    }

    public String getCod_nacion() {
        return cod_nacion;
    }

    public void setCod_nacion(String cod_nacion) {
        this.cod_nacion = cod_nacion;
    }

    public boolean isContador() {
        return contador;
    }

    public void setContador(boolean contador) {
        this.contador = contador;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public Date getFecha_bloqueo() {
        return fecha_bloqueo;
    }

    public void setFecha_bloqueo(Date fecha_bloqueo) {
        this.fecha_bloqueo = fecha_bloqueo;
    }

    public String getMotivo_bloqueo() {
        return motivo_bloqueo;
    }

    public void setMotivo_bloqueo(String motivo_bloqueo) {
        this.motivo_bloqueo = motivo_bloqueo;
    }
/*
    public List<CACompartimentosBD> getCompartimentos() {
        return compartimentos;
    }

    public void setCompartimentos(List<CACompartimentosBD> compartimentos) {
        this.compartimentos = compartimentos;
    }*/
}
