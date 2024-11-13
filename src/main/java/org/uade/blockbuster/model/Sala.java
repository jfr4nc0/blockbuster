package org.uade.blockbuster.model;

public class Sala {
    private int salaId;
    private int sucursalId;
    private String denominacion;
    private int asientos;

    public Sala(int salaId, int sucursalId, String denominacion, int asientos) {
        this.salaId = salaId;
        this.sucursalId = sucursalId;
        this.denominacion = denominacion;
        this.asientos = asientos;
    }

    public int getSucursalId() {
        return sucursalId;
    }

    public int getSalaId() {
        return salaId;
    }

    public int getAsientos() {
        return asientos;
    }

    public String getDenominacion() {
        return denominacion;
    }
}
