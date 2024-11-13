package org.uade.blockbuster.controller.dto;

public class FuncionDto {
    private int funcionId;
    private String pelicula;
    private String fecha;
    private String horario;
    private int sala;

    public FuncionDto(int funcionId, String pelicula, String fecha, String horario, int sala) {
        this.funcionId = funcionId;
        this.pelicula = pelicula;
        this.fecha = fecha;
        this.horario = horario;
        this.sala = sala;
    }
}