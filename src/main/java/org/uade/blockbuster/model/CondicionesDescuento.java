package org.uade.blockbuster.model;

import org.uade.blockbuster.model.enums.TipoDescuento;
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
    private TipoDescuento tipoDescuento;

    public CondicionesDescuento(Date fechaDesde, Date fechaHasta, int diaSemana, Double porcentaje, TipoTarjeta tipoTarjeta, List<TarjetaDescuento> tarjetasDescuento, TipoDescuento tipoDescuento) {
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
        this.diaSemana = diaSemana;
        this.porcentaje = porcentaje;
        this.tipoTarjeta = tipoTarjeta;
        this.tarjetasDescuento = tarjetasDescuento;
        this.tipoDescuento = tipoDescuento;
    }

    public TipoDescuento getTipoDescuento() {
        return tipoDescuento;
    }

    public void setTipoDescuento(TipoDescuento tipoDescuento) {
        this.tipoDescuento = tipoDescuento;
    }

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
