package org.uade.blockbuster.controller;

import org.uade.blockbuster.model.CondicionesDescuento;
import org.uade.blockbuster.model.Entrada;
import org.uade.blockbuster.model.TarjetaDescuento;
import org.uade.blockbuster.model.Venta;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DescuentoController {
    private static volatile DescuentoController INSTANCE;
    private Collection<CondicionesDescuento> descuentos;

    private DescuentoController() {
        this.descuentos = new ArrayList<CondicionesDescuento>();
    }

    public static DescuentoController getInstance() {
        DescuentoController result = INSTANCE;
        if (result != null) {
            return result;
        }
        synchronized (DescuentoController.class) {
            if (INSTANCE == null) {
                INSTANCE = new DescuentoController();
            }
            return INSTANCE;
        }
    }

    public Collection<CondicionesDescuento> getDescuentos() {
        return descuentos;
    }

    public double procesadorDeDescuentosParaVenta(Venta venta) {
        double descuentoTotal = 0.0;

        double descuentoDiaDeSemanaAEntrada = descuentos.stream()
                .anyMatch(condicionesDescuento -> condicionesDescuento.getDiaSemana() == venta.getFechaVenta().getDayOfWeek().getValue()) ? 0.50 : 0.00;

        descuentos.stream();


        return 0.00;
    }

    private double aplicarDescuento(Entrada entrada, double descuento) {
        return entrada.getPrecio() * (1 - descuento);
    }

    private double aplicarDescuento(List<Entrada> entrada, double descuento) {
        //return entrada.getPrecio() * (1 - descuento);
        return 0.00;
    }
}
