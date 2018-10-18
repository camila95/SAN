package com.uptc.san.entidades;

public class Empleados {
    private Integer identificacion;
    private String nombres;
    private String apellidos;
    private String numeroTelefono;
    private Integer idTrabajo;

    public Empleados(Integer identificacion, String nombres, String apellidos, String numeroTelefono, Integer idTrabajo) {
        this.identificacion = identificacion;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.numeroTelefono = numeroTelefono;
        this.idTrabajo = idTrabajo;
    }

    public Empleados() {
    }

    public Integer getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(Integer identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public Integer getIdTrabajo() {
        return idTrabajo;
    }

    public void setIdTrabajo(Integer idTrabajo) {
        this.idTrabajo = idTrabajo;
    }
}
