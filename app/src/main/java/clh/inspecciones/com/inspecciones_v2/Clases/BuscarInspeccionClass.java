package clh.inspecciones.com.inspecciones_v2.Clases;

import java.util.Date;

public class BuscarInspeccionClass {

    private String inspeccion;
    private String Instalacion;
    private String Tractora;
    private String Cisterna;
    private String fechaInicioInspeccion;
    private String albaran;
    private String conductor;
    private String transportista;
    private String conjunto;
    private String empresaTablaCalibracion;
    private String tipoComponente;
    private Boolean inspeccionada;
    private Boolean favorable;
    private Boolean revisada;
    private Boolean bloqueada;
    private String observaciones;
    private Date fechaFinInspeccion;

    public String getInspeccion() {
        return inspeccion;
    }

    public void setInspeccion(String inspeccion) {
        this.inspeccion = inspeccion;
    }

    public String getInstalacion() {
        return Instalacion;
    }

    public void setInstalacion(String instalacion) {
        Instalacion = instalacion;
    }

    public String getTractora() {
        return Tractora;
    }

    public void setTractora(String tractora) {
        Tractora = tractora;
    }

    public String getCisterna() {
        return Cisterna;
    }

    public void setCisterna(String cisterna) {
        Cisterna = cisterna;
    }

    public String getFechaInicioInspeccion() {
        return fechaInicioInspeccion;
    }

    public void setFechaInicioInspeccion(String fechaInicioInspeccion) {
        this.fechaInicioInspeccion = fechaInicioInspeccion;
    }

    public String getAlbaran() {
        return albaran;
    }

    public void setAlbaran(String albaran) {
        this.albaran = albaran;
    }

    public String getConductor() {
        return conductor;
    }

    public void setConductor(String conductor) {
        this.conductor = conductor;
    }

    public String getTransportista() {
        return transportista;
    }

    public void setTransportista(String transportista) {
        this.transportista = transportista;
    }

    public String getConjunto() {
        return conjunto;
    }

    public void setConjunto(String conjunto) {
        this.conjunto = conjunto;
    }

    public String getEmpresaTablaCalibracion() {
        return empresaTablaCalibracion;
    }

    public void setEmpresaTablaCalibracion(String empresaTablaCalibracion) {
        this.empresaTablaCalibracion = empresaTablaCalibracion;
    }

    public String getTipoComponente() {
        return tipoComponente;
    }

    public void setTipoComponente(String tipoComponente) {
        this.tipoComponente = tipoComponente;
    }

    public Boolean getInspeccionada() {
        return inspeccionada;
    }

    public void setInspeccionada(Boolean inspeccionada) {
        this.inspeccionada = inspeccionada;
    }

    public Boolean getFavorable() {
        return favorable;
    }

    public void setFavorable(Boolean favorable) {
        this.favorable = favorable;
    }

    public Boolean getRevisada() {
        return revisada;
    }

    public void setRevisada(Boolean revisada) {
        this.revisada = revisada;
    }

    public Boolean getBloqueada() {
        return bloqueada;
    }

    public void setBloqueada(Boolean bloqueada) {
        this.bloqueada = bloqueada;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Date getFechaFinInspeccion() {
        return fechaFinInspeccion;
    }

    public void setFechaFinInspeccion(Date fechaFinInspeccion) {
        this.fechaFinInspeccion = fechaFinInspeccion;
    }
}
