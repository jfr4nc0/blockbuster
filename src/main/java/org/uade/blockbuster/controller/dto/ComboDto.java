package org.uade.blockbuster.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ComboDto {
    private int comboId;
    private String descripcion;
    private double precio;
}
