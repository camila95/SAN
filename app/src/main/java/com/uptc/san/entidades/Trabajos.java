package com.uptc.san.entidades;

public class Trabajos {

    private Integer idTrabajo;
    private String nombreTrabajo;
    private Integer salario;

    public Trabajos(Integer idTrabajo, String nombreTrabajo, Integer salario) {
        this.idTrabajo = idTrabajo;
        this.nombreTrabajo = nombreTrabajo;
        this.salario = salario;
    }

    public Trabajos() {
    }

    public Integer getIdTrabajo() {
        return idTrabajo;
    }

    public void setIdTrabajo(Integer idTrabajo) {
        this.idTrabajo = idTrabajo;
    }

    public String getNombreTrabajo() {
        return nombreTrabajo;
    }

    public void setNombreTrabajo(String nombreTrabajo) {
        this.nombreTrabajo = nombreTrabajo;
    }

    public Integer getSalario() {
        return salario;
    }

    public void setSalario(Integer salario) {
        this.salario = salario;
    }
}