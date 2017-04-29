package com.mcdm.alejandro.myapplication.clases;

/**
 * Created by alejandro on 8/12/16.
 */

public class pagos {

    private Integer id;
    private double monto;
    private double resto;//LO QUE EL CLIENTE PAGARÁ
    private double total;
    private String fechaCobro;//ES CUANDO SE SUPONE QUE EL CLIENTE DEBE DE PAGAR
    private String fechaPago;//CUANDL EL CLIENTE REALIZA EL PAGO
    private boolean activo;
    private boolean sincronizado;

    public pagos(){

    }

    public pagos(Integer id, double monto, double resto, double total, String fechaCobro, String fechaPago, boolean activo, boolean sincronizado) {
        this.id = id;
        this.monto = monto;
        this.resto = resto;
        this.total = total;
        this.fechaCobro = fechaCobro;
        this.fechaPago = fechaPago;
        this.activo = activo;
        this.sincronizado = sincronizado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public double getResto() {
        return resto;
    }

    public void setResto(double resto) {
        this.resto = resto;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getFechaCobro() {
        return fechaCobro;
    }

    public void setFechaCobro(String fechaCobro) {
        this.fechaCobro = fechaCobro;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
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