package org.uade.blockbuster.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@AllArgsConstructor
public class Sucursal {
    private int sucursalId;
    private String denominacion;
    private String direccion;
    private Collection<Sala> salas;

    public Sucursal(int sucursalId, String denominacion, String direccion) {
        this.sucursalId = sucursalId;
        this.denominacion = denominacion;
        this.direccion = direccion;
        this.salas = new ArrayList<Sala>();
    }
}
