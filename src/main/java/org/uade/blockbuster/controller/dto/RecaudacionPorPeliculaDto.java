package org.uade.blockbuster.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecaudacionPorPeliculaDto {
    private PeliculaDto peliculaDto;
    private double recaudacionTotal;
}
