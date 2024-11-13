package org.uade.blockbuster.model;

public class Entrada {
    private Double precio;
    private int nroAsiento;
    private Funcion funcion;

    public int getNroAsiento() {
        return nroAsiento;
    }

    public Double getPrecio() {
        return precio;
    }

    public int getFuncionId() {
        return funcion.getFuncionId();
    }

    public int getPeliculaId() {
        return funcion.getPeliculaId();
    }
}