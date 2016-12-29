package com.mcdm.alejandro.myapplication.clases;

/**
 * Created by alejandro on 8/12/16.
 */

public class prendas {
    private String descripccion;
    private String tipoPrenda;
    private Integer cantidad;
    private double costo;
    private boolean sincronizado;


    public prendas(){

    }

    public prendas(String descripccion, String tipoPrenda, Integer cantidad, double costo, boolean sincronizado) {
        this.descripccion = descripccion;
        this.tipoPrenda = tipoPrenda;
        this.cantidad=cantidad;
        this.costo = costo;
        this.sincronizado = sincronizado;
    }

    public String getDescripccion() {
        return descripccion;
    }

    public void setDescripccion(String descripccion) {
        this.descripccion = descripccion;
    }

    public String getTipoPrenda() {
        return tipoPrenda;
    }

    public void setTipoPrenda(String tipoPrenda) {
        this.tipoPrenda = tipoPrenda;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public boolean isSincronizado() {
        return sincronizado;
    }

    public void setSincronizado(boolean sincronizado) {
        this.sincronizado = sincronizado;
    }
}
