package org.uade.blockbuster.controller.dto;

public class TarjetaDescuentoDto {
    private int tarjetaId;
    private String tipoTarjeta;

    public TarjetaDescuentoDto(int tarjetaId, String tipoTarjeta) {
        this.tarjetaId = tarjetaId;
        this.tipoTarjeta = tipoTarjeta;
    }
}
