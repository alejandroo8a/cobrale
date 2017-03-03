package com.mcdm.alejandro.myapplication.clases;

/**
 * Created by alejandro on 8/12/16.
 */

public class prendas {
    private Integer idNube;
    private Integer idPrenda;
    private String descripccion;
    private String tipoPrenda;
    private Integer cantidad;
    private double costo;
    private boolean sincronizado;


    public prendas(){

    }

    public prendas(Integer idPrenda, String descripccion, String tipoPrenda, Integer cantidad, double costo, boolean sincronizado) {
        this.idPrenda = idPrenda;
        this.descripccion = descripccion;
        this.tipoPrenda = tipoPrenda;
        this.cantidad = cantidad;
        this.costo = costo;
        this.sincronizado = sincronizado;
    }

    public Integer getIdNube() {
        return idNube;
    }

    public void setIdNube(Integer idNube) {
        this.idNube = idNube;
    }

    public Integer getIdPrenda() {
        return idPrenda;
    }

    public void setIdPrenda(Integer idPrenda) {
        this.idPrenda = idPrenda;
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
