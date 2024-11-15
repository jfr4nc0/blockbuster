package org.uade.blockbuster.controller.dto;

import lombok.AllArgsConstructor;

import java.util.Collection;

@AllArgsConstructor
public class SucursalDto {
    private int sucursalId;
    private String denominacion;
    private String direccion;
    private Collection<SalaDto> salas;

    @Override
    public String toString() {
        return denominacion +
                ", direccion= " + direccion;
    }
}
