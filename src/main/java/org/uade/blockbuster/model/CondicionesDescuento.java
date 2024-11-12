package org.uade.blockbuster.model;

import org.uade.blockbuster.model.enums.TipoTarjeta;

import java.util.Date;
import java.util.List;

public class CondicionesDescuento {
    private Date fechaDesde;
    private Date fechaHasta;
    private int diaSemana;
    private Double porcentaje;
    private TipoTarjeta tipoTarjeta;
    private List<TarjetaDescuento> tarjetasDescuento;

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public int getDiaSemana() {
        return diaSemana;
    }

    public Double getPorcentaje() {
        return porcentaje;
    }

    public TipoTarjeta getTipoTarjeta() {
        return tipoTarjeta;
    }

    public List<TarjetaDescuento> getTarjetasDescuento() {
        return tarjetasDescuento;
    }

    public Double getDescuento() {
        //TODO
        return null;
    }

    public Double getDescuentoPorTarjeta(TipoTarjeta tipoTarjeta) {
        //TODO
        return null;
    }
}
