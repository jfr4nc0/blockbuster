package org.uade.blockbuster.model;

import org.uade.blockbuster.model.enums.TipoDescuento;
import org.uade.blockbuster.model.enums.TipoTarjeta;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class CondicionesDescuento {
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    private int diaSemana;
    private Double porcentaje;
    private TipoTarjeta tipoTarjeta;
    private List<TarjetaDescuento> tarjetasDescuento;
    private TipoDescuento tipoDescuento;

    public CondicionesDescuento(LocalDate fechaDesde, LocalDate fechaHasta, int diaSemana, Double porcentaje, TipoTarjeta tipoTarjeta, List<TarjetaDescuento> tarjetasDescuento, TipoDescuento tipoDescuento) {
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

    public LocalDate getFechaDesde() {
        return fechaDesde;
    }

    public LocalDate getFechaHasta() {
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
        return this.porcentaje / 100.00;
    }

    public Double getDescuentoPorTarjeta(TipoTarjeta tipoTarjeta) {
        //TODO
        return null;
    }
}
