package clh.inspecciones.com.inspecciones_v2.Clases;

import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by root on 20/09/18.
 */

public class CARigidoBD extends RealmObject {

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
    private boolean contador;
    private String soloGasoelos;
    private boolean bloqueado;
    private Date fec_baja;
    private Date fec_cadu_calibracion;
    private int ejes;
    private String ind_carga_pesados;
    private String cod_transportista_resp;



    private RealmList<CACompartimentosBD> compartimentos;



    public CARigidoBD(){}

    public CARigidoBD(String matricula){
        this.id= InicializacionRealm.CARigidoBDId.incrementAndGet();
        this.matricula = matricula;
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

    public void setMatricula(String matricula) {
        this.matricula = matricula;
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

    public boolean isContador() {
        return contador;
    }

    public void setContador(boolean contador) {
        this.contador = contador;
    }

    public String getSoloGasoelos() {
        return soloGasoelos;
    }

    public void setSoloGasoelos(String soloGasoelos) {
        this.soloGasoelos = soloGasoelos;
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

    public Date getFec_cadu_calibracion() {
        return fec_cadu_calibracion;
    }

    public void setFec_cadu_calibracion(Date fec_cadu_calibracion) {
        this.fec_cadu_calibracion = fec_cadu_calibracion;
    }

    public int getEjes() {
        return ejes;
    }

    public void setEjes(int ejes) {
        this.ejes = ejes;
    }

    public String getInd_carga_pesados() {
        return ind_carga_pesados;
    }

    public void setInd_carga_pesados(String ind_carga_pesados) {
        this.ind_carga_pesados = ind_carga_pesados;
    }

    public String getCod_transportista_resp() {
        return cod_transportista_resp;
    }

    public void setCod_transportista_resp(String cod_transportista_resp) {
        this.cod_transportista_resp = cod_transportista_resp;
    }

    public RealmList<CACompartimentosBD> getCompartimentos() {
        return compartimentos;
    }
}
