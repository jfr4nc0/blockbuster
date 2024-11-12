package org.uade.blockbuster.model;

import java.util.Collection;

public class Sucursal {
    private int sucursalId;
    private String denominacion;
    private String direccion;
    private Collection<Sala> salas;

    public int getSucursalId() {
        return sucursalId;
    }
}
