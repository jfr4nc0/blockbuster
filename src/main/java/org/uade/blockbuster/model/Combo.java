package org.uade.blockbuster.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Combo {
    private int comboId;
    private String descripcion;
    private double precio;
}
