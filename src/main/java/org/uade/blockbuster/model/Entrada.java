package org.uade.blockbuster.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Entrada {
    private int nroAsiento;
    private double precio;
    private Funcion funcion;

    public Entrada(int nroAsiento, double precio) {
        this.nroAsiento = nroAsiento;
        this.precio = precio;
    }

    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }

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