package org.uade.blockbuster.controller;

import lombok.extern.slf4j.Slf4j;
import org.uade.blockbuster.controller.dto.TarjetaDescuentoDto;
import org.uade.blockbuster.model.Combo;
import org.uade.blockbuster.model.CondicionesDescuento;
import org.uade.blockbuster.model.Entrada;
import org.uade.blockbuster.model.TarjetaDescuento;
import org.uade.blockbuster.model.Venta;
import org.uade.blockbuster.model.enums.TipoDescuento;
import org.uade.blockbuster.model.enums.TipoTarjeta;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class DescuentoController {
    private static volatile DescuentoController INSTANCE;
    private Collection<CondicionesDescuento> descuentos;

    private DescuentoController() {
        this.descuentos = new ArrayList<CondicionesDescuento>();

        cargaInicial();
    }

    private void cargaInicial() {
        List<TarjetaDescuento> tarjetaDescuentos = List.of(
                new TarjetaDescuento(123, TipoTarjeta.LA_NACION),
                new TarjetaDescuento(124, TipoTarjeta.CLARIN_365),
                new TarjetaDescuento(125, TipoTarjeta.UADE)
        );

        agregarDescuento(
                null,
                null,
                1,
                50,
                null,
                null,
                TipoDescuento.ENTRADA);
        agregarDescuento(
                null,
                null,
                2,
                50,
                null,
                null,
                TipoDescuento.ENTRADA);
        agregarDescuento(
                null,
                null,
                3,
                50,
                null,
                null,
                TipoDescuento.ENTRADA);
        agregarDescuento(
                LocalDate.now().minusDays(1),
                LocalDate.now().plusDays(1),
                4,
                15,
                TipoTarjeta.LA_NACION,
                tarjetaDescuentos,
                TipoDescuento.TODO);
        agregarDescuento(
                LocalDate.now().minusDays(1),
                LocalDate.now().plusDays(1),
                5,
                20,
                TipoTarjeta.CLARIN_365,
                tarjetaDescuentos,
                TipoDescuento.COMBO);
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

    public int agregarDescuento(LocalDate fchDesde, LocalDate fchaHasta, int diaSemana, double porcentaje, TipoTarjeta tipoTarjeta, List<TarjetaDescuento> tarjetasDescuentos, TipoDescuento tipoDescuento) {
        int descuentoId = descuentos.size() + 1;
        CondicionesDescuento descuento = new CondicionesDescuento(descuentoId, fchDesde, fchaHasta, diaSemana, porcentaje, tipoTarjeta, tarjetasDescuentos, tipoDescuento);

        descuentos.add(descuento);
        log.info("Se agrego el descuento Id: " + descuentoId);

        return descuentoId;
    }

    public void modificarDescuento() {
        //TODO
    }

    public void eliminarDescuento() {
        //TODO
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
                                && existTarjetaDescuento(condicionesDescuento.getTarjetasDescuento(), venta.getTarjetaDescuento()))
                .findAny();
    }

    private boolean existTarjetaDescuento(List<TarjetaDescuento> tarjetasDescuento, TarjetaDescuento targetTarjetaDescuento) {
        if (Objects.isNull(tarjetasDescuento) || Objects.isNull(targetTarjetaDescuento)) return false;
        return tarjetasDescuento.stream()
                .anyMatch(tarjetaDescuento -> tarjetaDescuento.getTipoTarjeta().equals(targetTarjetaDescuento.getTipoTarjeta()) && tarjetaDescuento.getTarjetaId() == targetTarjetaDescuento.getTarjetaId());
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
        if (Objects.isNull(startDate) || Objects.isNull(endDate)) return true;
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

    public static TarjetaDescuento toModel(TarjetaDescuentoDto tarjetaDescuentoDto) {
        return new TarjetaDescuento(
                tarjetaDescuentoDto.getTarjetaId(),
                TipoTarjeta.valueOf(tarjetaDescuentoDto.getTipoTarjeta())
        );
    }
}
