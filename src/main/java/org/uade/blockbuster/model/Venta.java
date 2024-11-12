package org.uade.blockbuster.model;

import org.uade.blockbuster.model.enums.TipoTarjeta;

import java.util.Date;
import java.util.List;

public class Venta {
    private int ventaId;
    private Date fechaVenta;
    private List<Combo> combos;
    private Funcion funcion;
    private TarjetaDescuento tarjetaDescuento;

    public Venta(int ventaId, Date fechaVenta, List<Combo> combos, Funcion funcion, TarjetaDescuento tarjetaDescuento) {
        this.ventaId = ventaId;
        this.fechaVenta = fechaVenta;
        this.combos = combos;
        this.funcion = funcion;
        this.tarjetaDescuento = tarjetaDescuento;
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

    public List<Combo> getListaComboId() {
        return combos;
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
