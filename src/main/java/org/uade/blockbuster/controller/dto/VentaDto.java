package org.uade.blockbuster.controller.dto;

import java.util.List;

public class VentaDto {
    private int ventaId;
    private String fechaVenta;
    private List<ComboDto> combos;
    private FuncionDto funcion;
    private TarjetaDescuentoDto tarjetaDescuento;
    private List<EntradaDto> entradas;

    public VentaDto(int ventaId, String fechaVenta, List<ComboDto> combos, FuncionDto funcion, TarjetaDescuentoDto tarjetaDescuento, List<EntradaDto> entradas) {
        this.ventaId = ventaId;
        this.fechaVenta = fechaVenta;
        this.combos = combos;
        this.funcion = funcion;
        this.tarjetaDescuento = tarjetaDescuento;
        this.entradas = entradas;
    }
}
