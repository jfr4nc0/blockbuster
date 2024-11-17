package org.uade.blockbuster.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;

@Getter
@AllArgsConstructor
public class PeliculaDto {
    private int peliculaId;
    private String genero;
    private String nombrePelicula;
    private int duracionEnMinutos;
    private String director;
    private Collection<String> actores;
    private String tipoProyeccion;

    public PeliculaDto(String genero, String nombrePelicula, int duracionEnMinutos, String director, Collection<String> actores, String tipoProyeccion) {
        this.genero = genero;
        this.nombrePelicula = nombrePelicula;
        this.duracionEnMinutos = duracionEnMinutos;
        this.director = director;
        this.actores = actores;
        this.tipoProyeccion = tipoProyeccion;
    }

    @Override
    public String toString() {
        return nombrePelicula +
                " | " + genero +
                " | " + tipoProyeccion;
    }
}
