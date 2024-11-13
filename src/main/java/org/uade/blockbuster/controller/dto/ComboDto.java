package org.uade.blockbuster.controller.dto;

public class ComboDto {
    private int comboId;
    private String descripcion;
    private double precio;

    public ComboDto(int comboId, String descripcion, double precio) {
        this.comboId = comboId;
        this.descripcion = descripcion;
        this.precio = precio;
    }
}
