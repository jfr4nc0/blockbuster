package org.uade.blockbuster.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class VentaDto {
    private int ventaId;
    private LocalDate fechaVenta;
    private List<ComboDto> combos;
    private FuncionDto funcion;
    private TarjetaDescuentoDto tarjetaDescuento;
    private List<EntradaDto> entradas;

    public VentaDto(LocalDate fechaVenta, List<ComboDto> combos, FuncionDto funcion, TarjetaDescuentoDto tarjetaDescuento, List<EntradaDto> entradas) {
        this.fechaVenta = fechaVenta;
        this.combos = combos;
        this.funcion = funcion;
        this.tarjetaDescuento = tarjetaDescuento;
        this.entradas = entradas;
    }
}
