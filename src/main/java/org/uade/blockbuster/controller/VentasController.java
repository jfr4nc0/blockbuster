package org.uade.blockbuster.controller;

import org.uade.blockbuster.exceptions.NotFoundException;
import org.uade.blockbuster.model.*;

import java.util.ArrayList;
import java.util.Collection;

public class VentasController {
    private static volatile VentasController INSTANCE;
    private Collection<Venta> ventas;

    private VentasController() {
        this.ventas = new ArrayList<Venta>();
    }

    public static VentasController getInstance() {
        VentasController result = INSTANCE;
        if (result != null) {
            return result;
        }
        synchronized (VentasController.class) {
            if (INSTANCE == null) {
                INSTANCE = new VentasController();
            }
            return INSTANCE;
        }
    }

    public Collection<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(Collection<Venta> ventas) {
        this.ventas = ventas;
    }

    public Double recaudacionPorFuncion(int funcionId) throws NotFoundException {
        Funcion funcion = FuncionController.getInstance().buscarFuncionById(funcionId);
        // se debe evaluar si el descuento aplica en la fecha de validez tmb

        /*
        Double totalEntradasConDescuento = ventas.stream()
                .filter(venta -> venta.getFuncion().equals(funcion))
                .flatMap(venta -> venta.getEntradas().stream()
                        .map(entrada -> new EntradaConFechaVentaConTarjetaDescuento(entrada, venta.getFechaVenta(), venta.getTarjetaDescuento())))
                .mapToDouble(data -> this.calcularPrecioConDescuentos(data, descuentos))
                .sum();
         */

        // aplicar descuentos sobre el subtotal, o el descuento necesario sobre el total de cada venta
        // lunes a miercoles -> 50% aplicado al precio normal de entrada -> ok
        // definicion de porcentajes de descuento por tarjetas -> variable


        double totalRecaudadoPorVenta = ventas.stream()
                .filter(venta -> venta.getFuncion().equals(funcion))
                .mapToDouble(venta -> DescuentoController.getInstance().procesadorDeDescuentosParaVenta(venta))
                .sum();

        return totalRecaudadoPorVenta;
    }
}
