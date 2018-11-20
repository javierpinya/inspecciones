package clh.inspecciones.com.inspecciones_v2.Clases;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by root on 20/09/18.
 */

public class CACompartimentosBD extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String inspeccion;

    private String matricula;
    private int cod_compartimento;
    private String tipo_componente;
    private int can_capacidad;
    private String cod_tag_cprt;
    private int can_cargada;

    public int getCan_cargada() {
        return can_cargada;
    }

    public void setCan_cargada(int can_cargada) {
        this.can_cargada = can_cargada;
    }

    public CACompartimentosBD(){}

    public CACompartimentosBD(String matricula, String inspeccion){
        this.id= InicializacionRealm.CACompartimentosBDId.incrementAndGet();
        this.matricula = matricula;
        this.inspeccion = inspeccion;
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

    public int getCod_compartimento() {
        return cod_compartimento;
    }

    public void setCod_compartimento(int cod_compartimento) {
        this.cod_compartimento = cod_compartimento;
    }

    public String getTipo_componente() {
        return tipo_componente;
    }

    public void setTipo_componente(String tipo_componente) {
        this.tipo_componente = tipo_componente;
    }

    public int getCan_capacidad() {
        return can_capacidad;
    }

    public void setCan_capacidad(int can_capacidad) {
        this.can_capacidad = can_capacidad;
    }

    public String getCod_tag_cprt() {
        return cod_tag_cprt;
    }

    public void setCod_tag_cprt(String cod_tag_cprt) {
        this.cod_tag_cprt = cod_tag_cprt;
    }

    public String getInspeccion() {
        return inspeccion;
    }

    public void setInspeccion(String inspeccion) {
        this.inspeccion = inspeccion;
    }
}
