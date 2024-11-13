package org.uade.blockbuster.controller;

import org.uade.blockbuster.model.Combo;
import org.uade.blockbuster.model.CondicionesDescuento;
import org.uade.blockbuster.model.Entrada;
import org.uade.blockbuster.model.Venta;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public double procesadorDeDescuentosParaVenta(Venta venta) {
        double descuentoDiaDeSemanaAEntrada = getDescuentoDiaDeSemanaAEntrada(venta);

        Optional<CondicionesDescuento> descuento = getDescuentoPorTarjetaDescuento(venta);

        if (descuento.isPresent()) {
                CondicionesDescuento condicionesDescuento = descuento.get();
                List<Combo> combos = CombosController.getInstance().getCombosByCombosId(venta.getListaComboId());
                List<Entrada> entradas = venta.getEntradas();

                switch (condicionesDescuento.getTipoDescuento()){
                    case COMBO -> {
                        return aplicarDescuentos(combos, condicionesDescuento.getDescuento());
                    }
                    case ENTRADA -> {
                        return aplicarDescuentos(entradas, condicionesDescuento.getDescuento(), descuentoDiaDeSemanaAEntrada);
                    }
                    case TODO -> {
                        return aplicarDescuentos(entradas, combos, condicionesDescuento.getDescuento(), descuentoDiaDeSemanaAEntrada);
                    }
                }
        }

        return descuentoDiaDeSemanaAEntrada;
    }

    private Optional<CondicionesDescuento> getDescuentoPorTarjetaDescuento(Venta venta) {
        return descuentos.stream()
                .filter(condicionesDescuento ->
                        isDateInRange(condicionesDescuento.getFechaDesde(), condicionesDescuento.getFechaHasta(), venta.getFechaVenta())
                                && condicionesDescuento.getTarjetasDescuento().contains(venta.getTarjetaDescuento()))
                .findAny();
    }

    private double getDescuentoDiaDeSemanaAEntrada(Venta venta) {
        return descuentos.stream()
                .anyMatch(condicionesDescuento -> condicionesDescuento.getDiaSemana() == venta.getFechaVenta().getDayOfWeek().getValue()) ? 0.50 : 0.00;
    }

    private double aplicarDescuentos(List<Entrada> entradas, double descuentoTarjeta, double descuentoDiaDeSemanaAEntrada) {
        return entradas.stream()
                .mapToDouble(entrada -> this.aplicarDescuento(entrada, descuentoTarjeta + descuentoDiaDeSemanaAEntrada))
                .sum();
    }

    private double aplicarDescuentos(List<Combo> combos, double descuentoTarjeta) {
        return combos.stream()
                .mapToDouble(combo -> this.aplicarDescuento(combo, descuentoTarjeta))
                .sum();
    }

    private double aplicarDescuentos(List<Entrada> entradas, List<Combo> combos, double descuentoTarjeta, double descuentoDiaDeSemanaAEntrada) {
        return aplicarDescuentos(entradas, descuentoTarjeta, descuentoDiaDeSemanaAEntrada) + aplicarDescuentos(combos, descuentoTarjeta);
    }

    private static boolean isDateInRange(LocalDate startDate, LocalDate endDate, LocalDate targetDate) {
        return !targetDate.isBefore(startDate) && !targetDate.isAfter(endDate);
    }

    private double aplicarDescuento(Entrada entrada, double descuento) {
        if (descuento >= 1 || descuento < 0) throw new IllegalArgumentException("Descuento invalido, rango de descuento: 0 < descuento < 1");
        return entrada.getPrecio() * (1 - descuento);
    }

    private double aplicarDescuento(Combo combo, double descuento) {
        if (descuento >= 1 || descuento < 0) throw new IllegalArgumentException("Descuento invalido, rango de descuento: 0 < descuento < 1");
        return combo.getPrecio() * (1 - descuento);
    }
}
