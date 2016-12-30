package com.mcdm.alejandro.myapplication.clases;

/**
 * Created by alejandro on 29/12/16.
 */

public class DEBEN {

    private String nombre;
    private String fecha;
    private String resto;

    public DEBEN() {
    }

    public DEBEN(String nombre, String fecha, String resto) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.resto = resto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getResto() {
        return resto;
    }

    public void setResto(String resto) {
        this.resto = resto;
    }
}
