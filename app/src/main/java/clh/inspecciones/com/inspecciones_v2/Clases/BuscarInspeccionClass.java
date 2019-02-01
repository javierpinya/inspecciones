package clh.inspecciones.com.inspecciones_v2.Clases;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class BuscarInspeccionClass extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String inspeccion;
    private String Instalacion;
    private String Tractora;
    private String Cisterna;
    private Date fechaInicioInspeccion;
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
    private Boolean permisoConducir;
    private Boolean adrConductor;
    private Boolean itvTractoraRigido;
    private Boolean itvCisterna;
    private Boolean adrTractoraRigido;
    private Boolean adrCisterna;
    private Boolean fichaSeguridad;
    private Date fechaTablaCalibracion;
    private Boolean transponderTractora;
    private Boolean transponderCisterna;
    private Boolean superficieSupAntideslizante;
    private Boolean posicionamientoAdecuadoEnIsleta;
    private Boolean accFrenoEstacionamientoMarchaCorta;
    private Boolean accDesconectadorBaterias;
    private Boolean apagallamas;
    private Boolean descTfnoMovil;
    private Boolean interrupEmergenciaYFuego;
    private Boolean conexionTomaTierra;
    private Boolean conexionMangueraGases;
    private Boolean purgaCompartimentos;
    private Boolean ropaSeguridad;
    private Boolean estanqueidadCisterna;
    private Boolean estanqueidadValvulasAPI;
    private Boolean estanqueidadCajon;
    private Boolean estanqueidadValvulasFondo;
    private Boolean estanqueidadEquiposTrasiego;
    private Boolean recogerAlbaran;
    private Boolean tc2;
    private Boolean montajeCorrectoTags;
    private Boolean bajadaTagPlanta; //6460729. EL435NP.
    private Boolean lecturaTagIsleta;

    public BuscarInspeccionClass(){}

    public BuscarInspeccionClass(String inspeccion){
        this.id=InicializacionRealm.BuscarInspeccionId.incrementAndGet();
        this.inspeccion=inspeccion;
    }

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

    public Date getFechaInicioInspeccion() {
        return fechaInicioInspeccion;
    }

    public void setFechaInicioInspeccion(Date fechaInicioInspeccion) {
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

    public Boolean getPermisoConducir() {
        return permisoConducir;
    }

    public void setPermisoConducir(Boolean permisoConducir) {
        this.permisoConducir = permisoConducir;
    }

    public Boolean getAdrConductor() {
        return adrConductor;
    }

    public void setAdrConductor(Boolean adrConductor) {
        this.adrConductor = adrConductor;
    }

    public Boolean getItvTractoraRigido() {
        return itvTractoraRigido;
    }

    public void setItvTractoraRigido(Boolean itvTractoraRigido) {
        this.itvTractoraRigido = itvTractoraRigido;
    }

    public Boolean getItvCisterna() {
        return itvCisterna;
    }

    public void setItvCisterna(Boolean itvCisterna) {
        this.itvCisterna = itvCisterna;
    }

    public Boolean getAdrTractoraRigido() {
        return adrTractoraRigido;
    }

    public void setAdrTractoraRigido(Boolean adrTractoraRigido) {
        this.adrTractoraRigido = adrTractoraRigido;
    }

    public Boolean getAdrCisterna() {
        return adrCisterna;
    }

    public void setAdrCisterna(Boolean adrCisterna) {
        this.adrCisterna = adrCisterna;
    }

    public Boolean getFichaSeguridad() {
        return fichaSeguridad;
    }

    public void setFichaSeguridad(Boolean fichaSeguridad) {
        this.fichaSeguridad = fichaSeguridad;
    }

    public Date getFechaTablaCalibracion() {
        return fechaTablaCalibracion;
    }

    public void setFechaTablaCalibracion(Date fechaTablaCalibracion) {
        this.fechaTablaCalibracion = fechaTablaCalibracion;
    }

    public Boolean getTransponderTractora() {
        return transponderTractora;
    }

    public void setTransponderTractora(Boolean transponderTractora) {
        this.transponderTractora = transponderTractora;
    }

    public Boolean getTransponderCisterna() {
        return transponderCisterna;
    }

    public void setTransponderCisterna(Boolean transponderCisterna) {
        this.transponderCisterna = transponderCisterna;
    }

    public Boolean getSuperficieSupAntideslizante() {
        return superficieSupAntideslizante;
    }

    public void setSuperficieSupAntideslizante(Boolean superficieSupAntideslizante) {
        this.superficieSupAntideslizante = superficieSupAntideslizante;
    }

    public Boolean getPosicionamientoAdecuadoEnIsleta() {
        return posicionamientoAdecuadoEnIsleta;
    }

    public void setPosicionamientoAdecuadoEnIsleta(Boolean posicionamientoAdecuadoEnIsleta) {
        this.posicionamientoAdecuadoEnIsleta = posicionamientoAdecuadoEnIsleta;
    }

    public Boolean getAccFrenoEstacionamientoMarchaCorta() {
        return accFrenoEstacionamientoMarchaCorta;
    }

    public void setAccFrenoEstacionamientoMarchaCorta(Boolean accFrenoEstacionamientoMarchaCorta) {
        this.accFrenoEstacionamientoMarchaCorta = accFrenoEstacionamientoMarchaCorta;
    }

    public Boolean getAccDesconectadorBaterias() {
        return accDesconectadorBaterias;
    }

    public void setAccDesconectadorBaterias(Boolean accDesconectadorBaterias) {
        this.accDesconectadorBaterias = accDesconectadorBaterias;
    }

    public Boolean getApagallamas() {
        return apagallamas;
    }

    public void setApagallamas(Boolean apagallamas) {
        this.apagallamas = apagallamas;
    }

    public Boolean getDescTfnoMovil() {
        return descTfnoMovil;
    }

    public void setDescTfnoMovil(Boolean descTfnoMovil) {
        this.descTfnoMovil = descTfnoMovil;
    }

    public Boolean getInterrupEmergenciaYFuego() {
        return interrupEmergenciaYFuego;
    }

    public void setInterrupEmergenciaYFuego(Boolean interrupEmergenciaYFuego) {
        this.interrupEmergenciaYFuego = interrupEmergenciaYFuego;
    }

    public Boolean getConexionTomaTierra() {
        return conexionTomaTierra;
    }

    public void setConexionTomaTierra(Boolean conexionTomaTierra) {
        this.conexionTomaTierra = conexionTomaTierra;
    }

    public Boolean getConexionMangueraGases() {
        return conexionMangueraGases;
    }

    public void setConexionMangueraGases(Boolean conexionMangueraGases) {
        this.conexionMangueraGases = conexionMangueraGases;
    }

    public Boolean getPurgaCompartimentos() {
        return purgaCompartimentos;
    }

    public void setPurgaCompartimentos(Boolean purgaCompartimentos) {
        this.purgaCompartimentos = purgaCompartimentos;
    }

    public Boolean getRopaSeguridad() {
        return ropaSeguridad;
    }

    public void setRopaSeguridad(Boolean ropaSeguridad) {
        this.ropaSeguridad = ropaSeguridad;
    }

    public Boolean getEstanqueidadCisterna() {
        return estanqueidadCisterna;
    }

    public void setEstanqueidadCisterna(Boolean estanqueidadCisterna) {
        this.estanqueidadCisterna = estanqueidadCisterna;
    }

    public Boolean getEstanqueidadValvulasAPI() {
        return estanqueidadValvulasAPI;
    }

    public void setEstanqueidadValvulasAPI(Boolean estanqueidadValvulasAPI) {
        this.estanqueidadValvulasAPI = estanqueidadValvulasAPI;
    }

    public Boolean getEstanqueidadCajon() {
        return estanqueidadCajon;
    }

    public void setEstanqueidadCajon(Boolean estanqueidadCajon) {
        this.estanqueidadCajon = estanqueidadCajon;
    }

    public Boolean getEstanqueidadValvulasFondo() {
        return estanqueidadValvulasFondo;
    }

    public void setEstanqueidadValvulasFondo(Boolean estanqueidadValvulasFondo) {
        this.estanqueidadValvulasFondo = estanqueidadValvulasFondo;
    }

    public Boolean getEstanqueidadEquiposTrasiego() {
        return estanqueidadEquiposTrasiego;
    }

    public void setEstanqueidadEquiposTrasiego(Boolean estanqueidadEquiposTrasiego) {
        this.estanqueidadEquiposTrasiego = estanqueidadEquiposTrasiego;
    }

    public Boolean getRecogerAlbaran() {
        return recogerAlbaran;
    }

    public void setRecogerAlbaran(Boolean recogerAlbaran) {
        this.recogerAlbaran = recogerAlbaran;
    }

    public Boolean getTc2() {
        return tc2;
    }

    public void setTc2(Boolean tc2) {
        this.tc2 = tc2;
    }

    public Boolean getMontajeCorrectoTags() {
        return montajeCorrectoTags;
    }

    public void setMontajeCorrectoTags(Boolean montajeCorrectoTags) {
        this.montajeCorrectoTags = montajeCorrectoTags;
    }

    public Boolean getBajadaTagPlanta() {
        return bajadaTagPlanta;
    }

    public void setBajadaTagPlanta(Boolean bajadaTagPlanta) {
        this.bajadaTagPlanta = bajadaTagPlanta;
    }

    public Boolean getLecturaTagIsleta() {
        return lecturaTagIsleta;
    }

    public void setLecturaTagIsleta(Boolean lecturaTagIsleta) {
        this.lecturaTagIsleta = lecturaTagIsleta;
    }
}
