package org.uade.blockbuster.model;

public class Combo {
    private int comboId;
    private String descripcion;
    private Double precio;
    private CondicionesDescuento contiene;

    public String getDescripcion() {
        return descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public int getComboId() {
        return comboId;
    }
}
