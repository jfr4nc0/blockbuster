package org.uade.blockbuster.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Sala {
    private int salaId;
    private int sucursalId;
    private String denominacion;
    private int asientos;
}
