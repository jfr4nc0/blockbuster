package org.uade.blockbuster.model;

import java.util.ArrayList;
import java.util.Collection;

public class Sucursal {
    private int sucursalId;
    private String denominacion;
    private String direccion;
    private Collection<Sala> salas;

    public Sucursal(int sucursalId, String denominacion, String direccion, Collection<Sala> salas) {
        this.sucursalId = sucursalId;
        this.denominacion = denominacion;
        this.direccion = direccion;
        this.salas = salas;
    }

    public Sucursal(int sucursalId, String denominacion, String direccion) {
        this.sucursalId = sucursalId;
        this.denominacion = denominacion;
        this.direccion = direccion;
        this.salas = new ArrayList<Sala>();
    }

    public int getSucursalId() {
        return sucursalId;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public Collection<Sala> getSalas() {
        return salas;
    }
}
