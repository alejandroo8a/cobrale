package com.mcdm.alejandro.myapplication.clases;

/**
 * Created by alejandro on 8/12/16.
 */

public class cliente {

    private Integer id;
    private String nombre;
    private String calle;
    private String colonia;
    private String telefono1;
    private String telefono2;
    private String razonSocial;
    private boolean activo;
    private boolean sincronizado;


    public cliente(){

    }

    public cliente(Integer id, String nombre, String calle,String colonia, String telefono1, String telefono2,boolean activo, String razonSocial,  boolean sincronizado) {
        this.id=id;
        this.nombre = nombre;
        this.calle = calle;
        this.colonia =colonia;
        this.telefono1 = telefono1;
        this.telefono2 = telefono2;
        this.razonSocial =razonSocial;
        this.activo = activo;
        this.sincronizado = sincronizado;
    }

    public cliente(String nombre, String calle,String colonia, String telefono1, String telefono2,boolean activo, String razonSocial,  boolean sincronizado) {
        this.nombre = nombre;
        this.calle = calle;
        this.colonia =colonia;
        this.telefono1 = telefono1;
        this.telefono2 = telefono2;
        this.razonSocial =razonSocial;
        this.activo = activo;
        this.sincronizado = sincronizado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean isSincronizado() {
        return sincronizado;
    }

    public void setSincronizado(boolean sincronizado) {
        this.sincronizado = sincronizado;
    }


}
