package org.uade.blockbuster.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class FuncionDto {
    private int funcionId;
    private int peliculaId;
    private String peliculaNombre;
    private LocalDate fecha;
    private LocalTime horario;
    private int sucursalId;
    private int salaId;
    private double precioEntrada;
}