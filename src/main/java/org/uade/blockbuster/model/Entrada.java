package org.uade.blockbuster.model;

public class Entrada {
    private Double precio;
    private int nroAsiento;
    private Funcion funcion;

    public Double getPrecio() {
        return precio;
    }

    public int getFuncionId() {
        //TODO
        return funcion.getFuncionId();
    }

    public int getPeliculaId() {
        //TODO
        return funcion.getPeliculaId();
    }
}
