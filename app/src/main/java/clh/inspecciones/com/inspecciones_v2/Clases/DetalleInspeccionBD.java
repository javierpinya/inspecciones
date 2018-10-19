package clh.inspecciones.com.inspecciones_v2.Clases;

import java.util.Date;

import clh.inspecciones.com.inspecciones_v2.R;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class DetalleInspeccionBD extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String inspeccion;
    private String instalacion;
    private Date fechaInspeccion;
    private String transportista;
    private String conjunto;
    private String tractora;
    private String rigido;
    private Date fechaTablaCalRigido;
    private String cisterna;
    private Date fechaTablaCalCisterna;
    private String nombreConductor;
    private String conductor;
    private String suministrador;
    private String albaran;
    private Boolean permisoConducir;
    private Boolean adrConductor;
    private Boolean itvTractoraRigido;
    private Boolean itvCisterna;
    private Boolean adrTractoraRigido;
    private Boolean adrCisterna;
    private Boolean fichaSeguridad;
    private String tablaCalibracion;
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
    private String c1AlturaRealSonda;
    private String c196Teorica;
    private int c1CantCargada;
    private int c196ST;
    private int c1Diferencia;
    private Boolean c1Cumple;
    private String c2AlturaRealSonda;
    private String c296Teorica;
    private int c2CantCargada;
    private int c296ST;
    private int c2Diferencia;
    private Boolean c2Cumple;
    private String c3AlturaRealSonda;
    private String c396Teorica;
    private int c3CantCargada;
    private int c396ST;
    private int c3Diferencia;
    private Boolean c3Cumple;
    private String c4AlturaRealSonda;
    private String c496Teorica;
    private int c4CantCargada;
    private int c496ST;
    private int c4Diferencia;
    private Boolean c4Cumple;
    private String c5AlturaRealSonda;
    private String c596Teorica;
    private int c5CantCargada;
    private int c596ST;
    private int c5Diferencia;
    private Boolean c5Cumple;
    private String c6AlturaRealSonda;
    private String c696Teorica;
    private int c6CantCargada;
    private int c696ST;
    private int c6Diferencia;
    private Boolean c6Cumple;
    private String c7AlturaRealSonda;
    private String c796Teorica;
    private int c7CantCargada;
    private int c796ST;
    private int c7Diferencia;
    private Boolean c7Cumple;
    private String c8AlturaRealSonda;
    private String c896Teorica;
    private int c8CantCargada;
    private int c896ST;
    private int c8Diferencia;
    private Boolean c8Cumple;
    private Boolean estanqueidadCisterna;
    private Boolean estanqueidadValvulasAPI;
    private Boolean estanqueidadCajon;
    private Boolean estanqueidadValvulasFondo;
    private Boolean estanqueidadEquiposTrasiego;
    private Boolean recogerAlbaran;
    private String observaciones;   //comprobar la longitud maxima de un String
    private String numeroIncidencia1;
    private int pesoEntrada;
    private int pesoSalida;
    private int producto;
    private Date fechaArnes;
    private int c1VolTotalPlaca;
    private int c2VolTotalPlaca;
    private int c3VolTotalPlaca;
    private int c4VolTotalPlaca;
    private int c5VolTotalPlaca;
    private int c6VolTotalPlaca;
    private int c7VolTotalPlaca;
    private int c8VolTotalPlaca;
    private Boolean inspeccionada;
    private Boolean favorable;
    private Boolean desfavorable;
    private Date fechaDesfavorable;
    private Boolean bloqueo;
    private Date fechaBloqueo;
    private Boolean revisado;
    private Date fechaRevisado;
    private Boolean tc2;
    private Boolean montajeCorrectoTags;
    private Boolean bajadaTagPlanta; //6460729. EL435NP.
    private Boolean lecturaTagIsleta;
    private int c1Tag;
    private int c2Tag;
    private int c3Tag;
    private int c4Tag;
    private int c5Tag;
    private int c6Tag;
    private int c7Tag;
    private int c8Tag;
    private String tagObservaciones;

    public DetalleInspeccionBD(){}

    public DetalleInspeccionBD(String inspeccion){
        this.id=InicializacionRealm.CACisternaBDId.incrementAndGet();
        this.inspeccion=inspeccion;
        this.fechaArnes = new Date();
        this.fechaBloqueo = new Date();
        this.fechaDesfavorable = new Date();
        this.fechaInspeccion = new Date();
        this.fechaRevisado = new Date();
        this.fechaTablaCalCisterna = new Date();
        this.fechaTablaCalRigido = new Date();
        setAccDesconectadorBaterias(true);
        setFichaSeguridad(true);
        setTransponderTractora(true);
        setTransponderCisterna(true);
        setAccFrenoEstacionamientoMarchaCorta(true);
        setApagallamas(true);
        setBajadaTagPlanta(true);
        setAdrCisterna(true);
        setAdrConductor(true);
        setAdrTractoraRigido(true);
        setAdrTractoraRigido(true);
        setBloqueo(false);
        setConexionMangueraGases(true);
        setConexionTomaTierra(true);
        setDescTfnoMovil(true);
        setDesfavorable(false);
        setEstanqueidadCajon(true);
        setEstanqueidadCisterna(true);
        setEstanqueidadEquiposTrasiego(true);
        setEstanqueidadValvulasAPI(true);
        setEstanqueidadValvulasFondo(true);
        setFavorable(true);
        setInspeccionada(true);
        setInterrupEmergenciaYFuego(true);
        setItvCisterna(true);
        setItvTractoraRigido(true);
        setLecturaTagIsleta(true);
        setMontajeCorrectoTags(true);
        setPermisoConducir(true);
        setPosicionamientoAdecuadoEnIsleta(true);
        setPurgaCompartimentos(true);
        setRecogerAlbaran(true);
        setRevisado(false);
        setRopaSeguridad(true);
        setSuperficieSupAntideslizante(true);
        setTc2(true);


    }

    public int getId() {
        return id;
    }

    public String getInspeccion() {
        return inspeccion;
    }

    public void setInspeccion(String inspeccion) {
        this.inspeccion = inspeccion;
    }

    public String getInstalacion() {
        return instalacion;
    }

    public void setInstalacion(String instalacion) {
        this.instalacion = instalacion;
    }

    public Date getFechaInspeccion() {
        return fechaInspeccion;
    }

    public void setFechaInspeccion(Date fechaInspeccion) {
        this.fechaInspeccion = fechaInspeccion;
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

    public String getTractora() {
        return tractora;
    }

    public void setTractora(String tractora) {
        this.tractora = tractora;
    }

    public String getRigido() {
        return rigido;
    }

    public void setRigido(String rigido) {
        this.rigido = rigido;
    }

    public Date getFechaTablaCalRigido() {
        return fechaTablaCalRigido;
    }

    public void setFechaTablaCalRigido(Date fechaTablaCalRigido) {
        this.fechaTablaCalRigido = fechaTablaCalRigido;
    }

    public String getCisterna() {
        return cisterna;
    }

    public void setCisterna(String cisterna) {
        this.cisterna = cisterna;
    }

    public Date getFechaTablaCalCisterna() {
        return fechaTablaCalCisterna;
    }

    public void setFechaTablaCalCisterna(Date fechaTablaCalCisterna) {
        this.fechaTablaCalCisterna = fechaTablaCalCisterna;
    }

    public String getNombreConductor() {
        return nombreConductor;
    }

    public void setNombreConductor(String nombreConductor) {
        this.nombreConductor = nombreConductor;
    }

    public String getConductor() {
        return conductor;
    }

    public void setConductor(String conductor) {
        this.conductor = conductor;
    }

    public String getSuministrador() {
        return suministrador;
    }

    public void setSuministrador(String suministrador) {
        this.suministrador = suministrador;
    }

    public String getAlbaran() {
        return albaran;
    }

    public void setAlbaran(String albaran) {
        this.albaran = albaran;
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

    public String getTablaCalibracion() {
        return tablaCalibracion;
    }

    public void setTablaCalibracion(String tablaCalibracion) {
        this.tablaCalibracion = tablaCalibracion;
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

    public String getC1AlturaRealSonda() {
        return c1AlturaRealSonda;
    }

    public void setC1AlturaRealSonda(String c1AlturaRealSonda) {
        this.c1AlturaRealSonda = c1AlturaRealSonda;
    }

    public String getC196Teorica() {
        return c196Teorica;
    }

    public void setC196Teorica(String c196Teorica) {
        this.c196Teorica = c196Teorica;
    }

    public int getC1CantCargada() {
        return c1CantCargada;
    }

    public void setC1CantCargada(int c1CantCargada) {
        this.c1CantCargada = c1CantCargada;
    }

    public int getC196ST() {
        return c196ST;
    }

    public void setC196ST(int c196ST) {
        this.c196ST = c196ST;
    }

    public int getC1Diferencia() {
        return c1Diferencia;
    }

    public void setC1Diferencia(int c1Diferencia) {
        this.c1Diferencia = c1Diferencia;
    }

    public Boolean getC1Cumple() {
        return c1Cumple;
    }

    public void setC1Cumple(Boolean c1Cumple) {
        this.c1Cumple = c1Cumple;
    }

    public String getC2AlturaRealSonda() {
        return c2AlturaRealSonda;
    }

    public void setC2AlturaRealSonda(String c2AlturaRealSonda) {
        this.c2AlturaRealSonda = c2AlturaRealSonda;
    }

    public String getC296Teorica() {
        return c296Teorica;
    }

    public void setC296Teorica(String c296Teorica) {
        this.c296Teorica = c296Teorica;
    }

    public int getC2CantCargada() {
        return c2CantCargada;
    }

    public void setC2CantCargada(int c2CantCargada) {
        this.c2CantCargada = c2CantCargada;
    }

    public int getC296ST() {
        return c296ST;
    }

    public void setC296ST(int c296ST) {
        this.c296ST = c296ST;
    }

    public int getC2Diferencia() {
        return c2Diferencia;
    }

    public void setC2Diferencia(int c2Diferencia) {
        this.c2Diferencia = c2Diferencia;
    }

    public Boolean getC2Cumple() {
        return c2Cumple;
    }

    public void setC2Cumple(Boolean c2Cumple) {
        this.c2Cumple = c2Cumple;
    }

    public String getC3AlturaRealSonda() {
        return c3AlturaRealSonda;
    }

    public void setC3AlturaRealSonda(String c3AlturaRealSonda) {
        this.c3AlturaRealSonda = c3AlturaRealSonda;
    }

    public String getC396Teorica() {
        return c396Teorica;
    }

    public void setC396Teorica(String c396Teorica) {
        this.c396Teorica = c396Teorica;
    }

    public int getC3CantCargada() {
        return c3CantCargada;
    }

    public void setC3CantCargada(int c3CantCargada) {
        this.c3CantCargada = c3CantCargada;
    }

    public int getC396ST() {
        return c396ST;
    }

    public void setC396ST(int c396ST) {
        this.c396ST = c396ST;
    }

    public int getC3Diferencia() {
        return c3Diferencia;
    }

    public void setC3Diferencia(int c3Diferencia) {
        this.c3Diferencia = c3Diferencia;
    }

    public Boolean getC3Cumple() {
        return c3Cumple;
    }

    public void setC3Cumple(Boolean c3Cumple) {
        this.c3Cumple = c3Cumple;
    }

    public String getC4AlturaRealSonda() {
        return c4AlturaRealSonda;
    }

    public void setC4AlturaRealSonda(String c4AlturaRealSonda) {
        this.c4AlturaRealSonda = c4AlturaRealSonda;
    }

    public String getC496Teorica() {
        return c496Teorica;
    }

    public void setC496Teorica(String c496Teorica) {
        this.c496Teorica = c496Teorica;
    }

    public int getC4CantCargada() {
        return c4CantCargada;
    }

    public void setC4CantCargada(int c4CantCargada) {
        this.c4CantCargada = c4CantCargada;
    }

    public int getC496ST() {
        return c496ST;
    }

    public void setC496ST(int c496ST) {
        this.c496ST = c496ST;
    }

    public int getC4Diferencia() {
        return c4Diferencia;
    }

    public void setC4Diferencia(int c4Diferencia) {
        this.c4Diferencia = c4Diferencia;
    }

    public Boolean getC4Cumple() {
        return c4Cumple;
    }

    public void setC4Cumple(Boolean c4Cumple) {
        this.c4Cumple = c4Cumple;
    }

    public String getC5AlturaRealSonda() {
        return c5AlturaRealSonda;
    }

    public void setC5AlturaRealSonda(String c5AlturaRealSonda) {
        this.c5AlturaRealSonda = c5AlturaRealSonda;
    }

    public String getC596Teorica() {
        return c596Teorica;
    }

    public void setC596Teorica(String c596Teorica) {
        this.c596Teorica = c596Teorica;
    }

    public int getC5CantCargada() {
        return c5CantCargada;
    }

    public void setC5CantCargada(int c5CantCargada) {
        this.c5CantCargada = c5CantCargada;
    }

    public int getC596ST() {
        return c596ST;
    }

    public void setC596ST(int c596ST) {
        this.c596ST = c596ST;
    }

    public int getC5Diferencia() {
        return c5Diferencia;
    }

    public void setC5Diferencia(int c5Diferencia) {
        this.c5Diferencia = c5Diferencia;
    }

    public Boolean getC5Cumple() {
        return c5Cumple;
    }

    public void setC5Cumple(Boolean c5Cumple) {
        this.c5Cumple = c5Cumple;
    }

    public String getC6AlturaRealSonda() {
        return c6AlturaRealSonda;
    }

    public void setC6AlturaRealSonda(String c6AlturaRealSonda) {
        this.c6AlturaRealSonda = c6AlturaRealSonda;
    }

    public String getC696Teorica() {
        return c696Teorica;
    }

    public void setC696Teorica(String c696Teorica) {
        this.c696Teorica = c696Teorica;
    }

    public int getC6CantCargada() {
        return c6CantCargada;
    }

    public void setC6CantCargada(int c6CantCargada) {
        this.c6CantCargada = c6CantCargada;
    }

    public int getC696ST() {
        return c696ST;
    }

    public void setC696ST(int c696ST) {
        this.c696ST = c696ST;
    }

    public int getC6Diferencia() {
        return c6Diferencia;
    }

    public void setC6Diferencia(int c6Diferencia) {
        this.c6Diferencia = c6Diferencia;
    }

    public Boolean getC6Cumple() {
        return c6Cumple;
    }

    public void setC6Cumple(Boolean c6Cumple) {
        this.c6Cumple = c6Cumple;
    }

    public String getC7AlturaRealSonda() {
        return c7AlturaRealSonda;
    }

    public void setC7AlturaRealSonda(String c7AlturaRealSonda) {
        this.c7AlturaRealSonda = c7AlturaRealSonda;
    }

    public String getC796Teorica() {
        return c796Teorica;
    }

    public void setC796Teorica(String c796Teorica) {
        this.c796Teorica = c796Teorica;
    }

    public int getC7CantCargada() {
        return c7CantCargada;
    }

    public void setC7CantCargada(int c7CantCargada) {
        this.c7CantCargada = c7CantCargada;
    }

    public int getC796ST() {
        return c796ST;
    }

    public void setC796ST(int c796ST) {
        this.c796ST = c796ST;
    }

    public int getC7Diferencia() {
        return c7Diferencia;
    }

    public void setC7Diferencia(int c7Diferencia) {
        this.c7Diferencia = c7Diferencia;
    }

    public Boolean getC7Cumple() {
        return c7Cumple;
    }

    public void setC7Cumple(Boolean c7Cumple) {
        this.c7Cumple = c7Cumple;
    }

    public String getC8AlturaRealSonda() {
        return c8AlturaRealSonda;
    }

    public void setC8AlturaRealSonda(String c8AlturaRealSonda) {
        this.c8AlturaRealSonda = c8AlturaRealSonda;
    }

    public String getC896Teorica() {
        return c896Teorica;
    }

    public void setC896Teorica(String c896Teorica) {
        this.c896Teorica = c896Teorica;
    }

    public int getC8CantCargada() {
        return c8CantCargada;
    }

    public void setC8CantCargada(int c8CantCargada) {
        this.c8CantCargada = c8CantCargada;
    }

    public int getC896ST() {
        return c896ST;
    }

    public void setC896ST(int c896ST) {
        this.c896ST = c896ST;
    }

    public int getC8Diferencia() {
        return c8Diferencia;
    }

    public void setC8Diferencia(int c8Diferencia) {
        this.c8Diferencia = c8Diferencia;
    }

    public Boolean getC8Cumple() {
        return c8Cumple;
    }

    public void setC8Cumple(Boolean c8Cumple) {
        this.c8Cumple = c8Cumple;
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

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getNumeroIncidencia1() {
        return numeroIncidencia1;
    }

    public void setNumeroIncidencia1(String numeroIncidencia1) {
        this.numeroIncidencia1 = numeroIncidencia1;
    }

    public int getPesoEntrada() {
        return pesoEntrada;
    }

    public void setPesoEntrada(int pesoEntrada) {
        this.pesoEntrada = pesoEntrada;
    }

    public int getPesoSalida() {
        return pesoSalida;
    }

    public void setPesoSalida(int pesoSalida) {
        this.pesoSalida = pesoSalida;
    }

    public int getProducto() {
        return producto;
    }

    public void setProducto(int producto) {
        this.producto = producto;
    }

    public Date getFechaArnes() {
        return fechaArnes;
    }

    public void setFechaArnes(Date fechaArnes) {
        this.fechaArnes = fechaArnes;
    }

    public int getC1VolTotalPlaca() {
        return c1VolTotalPlaca;
    }

    public void setC1VolTotalPlaca(int c1VolTotalPlaca) {
        this.c1VolTotalPlaca = c1VolTotalPlaca;
    }

    public int getC2VolTotalPlaca() {
        return c2VolTotalPlaca;
    }

    public void setC2VolTotalPlaca(int c2VolTotalPlaca) {
        this.c2VolTotalPlaca = c2VolTotalPlaca;
    }

    public int getC3VolTotalPlaca() {
        return c3VolTotalPlaca;
    }

    public void setC3VolTotalPlaca(int c3VolTotalPlaca) {
        this.c3VolTotalPlaca = c3VolTotalPlaca;
    }

    public int getC4VolTotalPlaca() {
        return c4VolTotalPlaca;
    }

    public void setC4VolTotalPlaca(int c4VolTotalPlaca) {
        this.c4VolTotalPlaca = c4VolTotalPlaca;
    }

    public int getC5VolTotalPlaca() {
        return c5VolTotalPlaca;
    }

    public void setC5VolTotalPlaca(int c5VolTotalPlaca) {
        this.c5VolTotalPlaca = c5VolTotalPlaca;
    }

    public int getC6VolTotalPlaca() {
        return c6VolTotalPlaca;
    }

    public void setC6VolTotalPlaca(int c6VolTotalPlaca) {
        this.c6VolTotalPlaca = c6VolTotalPlaca;
    }

    public int getC7VolTotalPlaca() {
        return c7VolTotalPlaca;
    }

    public void setC7VolTotalPlaca(int c7VolTotalPlaca) {
        this.c7VolTotalPlaca = c7VolTotalPlaca;
    }

    public int getC8VolTotalPlaca() {
        return c8VolTotalPlaca;
    }

    public void setC8VolTotalPlaca(int c8VolTotalPlaca) {
        this.c8VolTotalPlaca = c8VolTotalPlaca;
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

    public Boolean getDesfavorable() {
        return desfavorable;
    }

    public void setDesfavorable(Boolean desfavorable) {
        this.desfavorable = desfavorable;
    }

    public Date getFechaDesfavorable() {
        return fechaDesfavorable;
    }

    public void setFechaDesfavorable(Date fechaDesfavorable) {
        this.fechaDesfavorable = fechaDesfavorable;
    }

    public Boolean getBloqueo() {
        return bloqueo;
    }

    public void setBloqueo(Boolean bloqueo) {
        this.bloqueo = bloqueo;
    }

    public Date getFechaBloqueo() {
        return fechaBloqueo;
    }

    public void setFechaBloqueo(Date fechaBloqueo) {
        this.fechaBloqueo = fechaBloqueo;
    }

    public Boolean getRevisado() {
        return revisado;
    }

    public void setRevisado(Boolean revisado) {
        this.revisado = revisado;
    }

    public Date getFechaRevisado() {
        return fechaRevisado;
    }

    public void setFechaRevisado(Date fechaRevisado) {
        this.fechaRevisado = fechaRevisado;
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

    public int getC1Tag() {
        return c1Tag;
    }

    public void setC1Tag(int c1Tag) {
        this.c1Tag = c1Tag;
    }

    public int getC2Tag() {
        return c2Tag;
    }

    public void setC2Tag(int c2Tag) {
        this.c2Tag = c2Tag;
    }

    public int getC3Tag() {
        return c3Tag;
    }

    public void setC3Tag(int c3Tag) {
        this.c3Tag = c3Tag;
    }

    public int getC4Tag() {
        return c4Tag;
    }

    public void setC4Tag(int c4Tag) {
        this.c4Tag = c4Tag;
    }

    public int getC5Tag() {
        return c5Tag;
    }

    public void setC5Tag(int c5Tag) {
        this.c5Tag = c5Tag;
    }

    public int getC6Tag() {
        return c6Tag;
    }

    public void setC6Tag(int c6Tag) {
        this.c6Tag = c6Tag;
    }

    public int getC7Tag() {
        return c7Tag;
    }

    public void setC7Tag(int c7Tag) {
        this.c7Tag = c7Tag;
    }

    public int getC8Tag() {
        return c8Tag;
    }

    public void setC8Tag(int c8Tag) {
        this.c8Tag = c8Tag;
    }

    public String getTagObservaciones() {
        return tagObservaciones;
    }

    public void setTagObservaciones(String tagObservaciones) {
        this.tagObservaciones = tagObservaciones;
    }
}
