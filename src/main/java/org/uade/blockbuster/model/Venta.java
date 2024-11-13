package org.uade.blockbuster.model;

import org.uade.blockbuster.model.enums.TipoTarjeta;

import java.time.LocalDate;
import java.util.List;

public class Venta {
    private int ventaId;
    private LocalDate fechaVenta;
    private List<Combo> combos;
    private Funcion funcion;
    private TarjetaDescuento tarjetaDescuento;
    private List<Entrada> entradas;

    public Venta(int ventaId, LocalDate fechaVenta, List<Combo> combos, Funcion funcion, TarjetaDescuento tarjetaDescuento, List<Entrada> entradas) {
        this.ventaId = ventaId;
        this.fechaVenta = fechaVenta;
        this.combos = combos;
        this.funcion = funcion;
        this.tarjetaDescuento = tarjetaDescuento;
        this.entradas = entradas;
    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public List<Entrada> getEntradas() {
        return entradas;
    }

    public void setEntradas(List<Entrada> entradas) {
        this.entradas = entradas;
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }

    public TarjetaDescuento getTarjetaDescuento() {
        return tarjetaDescuento;
    }

    public int getFuncionId() {
        //TODO
        return 0;
    }

    public Double getTotal() {
        //TODO
        return 0.0;
    }

    public int getPeliculaId() {
        //TODO
        return 0;
    }

    public TipoTarjeta getTipoTarjeta() {
        //TODO
        return null;
    }

    public List<Integer> getListaComboId() {
        return combos.stream()
                .map(Combo::getComboId)
                .toList();
    }

    public Double calcularMontoPorComboDeVenta() {
        //TODO
        return 0.0;
    }

    public Double calcularMontoDeLaVentaPorFuncionCombos() {
        //TODO
        return 0.0;
    }
}
