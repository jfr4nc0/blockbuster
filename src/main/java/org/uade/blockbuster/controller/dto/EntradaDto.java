package org.uade.blockbuster.controller.dto;

public class EntradaDto {
    private double precio;
    private int nroAsiento;

    public EntradaDto(double precio, int nroAsiento) {
        this.precio = precio;
        this.nroAsiento = nroAsiento;
    }
}
