/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.util.List;
import javax.json.JsonObject;
/**
 *
 * @author BoomEci
 */
public class PlanDeEstudios {
    
    String carrera;
    JsonObject grafo;
    int numeroDeCreditosTotales;
    int numeroDeAsignaturas;
    int numeroPlanDeEstudio;
    List<Materia> materiasActuales;
    List<Materia> materiasAprovadas;
    List<Materia> materiasPorVer;

    public int getNumeroDeAsignaturas() {
        return numeroDeAsignaturas;
    }

    public void setNumeroDeAsignaturas(int numeroDeAsignaturas) {
        this.numeroDeAsignaturas = numeroDeAsignaturas;
    }
    
    public int getNumeroDeCreditosTotales() {
        return numeroDeCreditosTotales;
    }

    public void setNumeroDeCreditosTotales(int numeroDeCreditosTotales) {
        this.numeroDeCreditosTotales = numeroDeCreditosTotales;
    }

    public List<Materia> getMateriasActuales() {
        return materiasActuales;
    }

    public void setMateriasActuales(List<Materia> materiasActuales) {
        this.materiasActuales = materiasActuales;
    }

    public List<Materia> getMateriasAprovadas() {
        return materiasAprovadas;
    }

    public void setMateriasAprovadas(List<Materia> materiasAprovadas) {
        this.materiasAprovadas = materiasAprovadas;
    }

    public List<Materia> getMateriasPorVer() {
        return materiasPorVer;
    }

    public void setMateriasPorVer(List<Materia> materiasPorVer) {
        this.materiasPorVer = materiasPorVer;
    }

    public int getNumeroPlanDeEstudio() {
        return numeroPlanDeEstudio;
    }

    public void setNumeroPlanDeEstudio(int numeroPlanDeEstudio) {
        this.numeroPlanDeEstudio = numeroPlanDeEstudio;
    }
    
    
}